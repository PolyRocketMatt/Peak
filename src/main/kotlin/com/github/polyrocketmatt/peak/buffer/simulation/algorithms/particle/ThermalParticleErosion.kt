package com.github.polyrocketmatt.peak.buffer.simulation.algorithms.particle

import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.game.math.fastAbs
import com.github.polyrocketmatt.peak.buffer.AsyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.AsyncNoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.operator.add
import com.github.polyrocketmatt.peak.buffer.simulation.Simulation
import com.github.polyrocketmatt.peak.buffer.simulation.data.ThermalSimulationData
import com.github.polyrocketmatt.peak.math.toRadians
import kotlin.math.min
import kotlin.math.tan

/**
 * Simulation that simulates thermal erosion processes on a buffer.
 */
class ThermalParticleErosion(data: ThermalSimulationData) : Simulation {

    private val iterations: Int = data.iterations
    private val talusAngle: Float = data.talusAngle
    private val sedimentTalusAngle: Float = data.sedimentTalus
    private val cascade: Boolean = data.cascade
    private val cellSize: Float = data.cellSize
    private val talusAngleThreshold: Float = tan(talusAngle.toRadians())
    private val sedimentTalusAngleThreshold: Float = tan(sedimentTalusAngle.toRadians())
    private val sedimentRemoval: Float = data.sedimentRemoval
    private val sedimentCarry: Float = data.sedimentCarry
    private val sedimentRemovalMultiplier: Float = data.sedimentRemovalMultiplier
    private val thermalFalloff: Float = data.thermalFalloff
    private val roughness: Float = data.roughness
    private val abrasion: Float = data.abrasion
    private val settling: Float = data.settling
    private val depositBelow: Float = data.depositBelow
    private val sedimentParticleLifetime: Int = data.sedimentParticleLifetime

    /**
     * Perform a simulation on a 2D buffer.
     *
     * @param buffer: the buffer to perform the simulation on
     * @return a new buffer that contains the effect of the simulation on the buffer
     */
    override fun simulate(buffer: AsyncNoiseBuffer2): AsyncNoiseBuffer2 = simulateThermalErosionSteepest(buffer)

    /**
     * Perform a simulation on a 3D buffer.
     *
     * @param buffer: the buffer to perform the simulation on
     * @return a new buffer that contains the effect of the simulation on the buffer
     */
    override fun simulate(buffer: AsyncNoiseBuffer3): AsyncNoiseBuffer3 {
        TODO("Not yet implemented")
    }

    //  TODO: Linearize for speed
    private fun simulateThermalErosionSteepest(simulationBuffer: AsyncNoiseBuffer2) : AsyncNoiseBuffer2 {
        val width = simulationBuffer.width()
        val height = simulationBuffer.height()
        val map = simulationBuffer.copy()
        val sediment = AsyncNoiseBuffer2(width, height, 16, 0.0f)
        val percentile = iterations / 10
        var falloffMultiplier = 1.0f
        for (i in 0 until iterations) {
            for (x in 0 until width) for (y in 0 until height) {
                val value = map[x][y]
                var xSteepest = -1
                var ySteepest = -1
                var steepestSlope = -1.0f

                //  Check neighbourhood
                for (oX in -1 .. 1) for (oY in -1 .. 1) {
                    val iX = x + oX
                    val iY = y + oY

                    //  Boundary
                    if (iX in 0 until width && iY in 0 until height && iX != x && iY != y) {
                        val valueDiff = value - map[iX][iY]


                        //  If current is higher than neighbour
                        if (valueDiff > 0.0f && valueDiff > steepestSlope) {
                            steepestSlope = valueDiff
                            xSteepest = iX
                            ySteepest = iY
                        }
                    }
                }

                if (steepestSlope / cellSize > talusAngleThreshold) {
                    //  Compute sediment removal based on slope
                    val removedSediment = sedimentRemoval * steepestSlope * sedimentRemovalMultiplier * falloffMultiplier
                    val depositSediment = sedimentCarry * steepestSlope * sedimentRemovalMultiplier * falloffMultiplier

                    //  Erode away
                    map[x][y] -= removedSediment

                    if (sediment[xSteepest][ySteepest] < depositBelow) {
                        //  DO NOT INCREASE WITH MORE THAN REMOVED HEIGHT
                        sediment[xSteepest][ySteepest] += depositSediment

                        var sX = xSteepest
                        var sY = ySteepest
                        var xSedimentSteepest = -1
                        var ySedimentSteepest = -1
                        var sedimentSteepest = -1.0f

                        for (j in 1 until sedimentParticleLifetime) {
                            for (oX in -1 .. 1) for (oY in -1 .. 1) {
                                val iX = sX + oX
                                val iY = sY + oY

                                //  Boundary
                                if (iX in 0 until width && iY in 0 until height && iX != x && iY != y) {
                                    val valueDiff = value - sediment[iX][iY]


                                    //  If current is higher than neighbour
                                    if (valueDiff > 0.0f && valueDiff > steepestSlope) {
                                        sedimentSteepest = valueDiff
                                        xSedimentSteepest = iX
                                        ySedimentSteepest = iY
                                    }
                                }
                            }

                            if (sedimentSteepest / cellSize > sedimentTalusAngleThreshold) {
                                cascade(xSedimentSteepest, ySedimentSteepest, map, sediment, width, height)
                            }

                            sX = xSedimentSteepest
                            sY = ySedimentSteepest
                        }
                    }
                }

                if (cascade) {
                    cascade(x, y, map, sediment, width, height)

                    if (xSteepest in 0 until width && ySteepest in 0 until height && xSteepest != x && ySteepest != y)
                        cascade(xSteepest, ySteepest, map, sediment, width, height)
                }
            }

            falloffMultiplier *= thermalFalloff

            if (i % percentile == 0)
                println("   Simulated Thermal: ${i / iterations.f() * 100f}%")
        }

        return (map add sediment) as AsyncNoiseBuffer2
    }

    private fun cascade(x: Int, y: Int, h: AsyncNoiseBuffer2, s: AsyncNoiseBuffer2, width: Int, height: Int) {
        for (oX in -1 .. 1) for (oY in -1 .. 1) {
            val iX = oX + x
            val iY = oY + y

            //  Bound check
            if (iX !in 0 until width || iY !in 0 until height)
                continue

            //  Pile size difference
            val diff = (h[x][y] + s[x][y] - (h[iX][iY] + s[iX][iY]))
            val excess = diff.fastAbs() - roughness
            if (excess <= 0)
                continue

            //  Transfer mass
            val transfer = if (diff > 0f) min(s[x][y], excess / 2f) else -min(s[iX][iY], excess / 2f)
            val settlingAmount = settling * transfer
            val abrasionAmount = abrasion * transfer

            s[x][y] -= abrasionAmount
            s[iX][iY] += settlingAmount
        }
    }

}