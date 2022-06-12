package com.github.polyrocketmatt.peak.buffer.simulation.algorithms

import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.game.math.fastAbs
import com.github.polyrocketmatt.peak.buffer.AsyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.AsyncNoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.operator.add
import com.github.polyrocketmatt.peak.buffer.simulation.AsyncSimulator
import com.github.polyrocketmatt.peak.buffer.simulation.data.ThermalSimulationData
import com.github.polyrocketmatt.peak.math.toRadians
import kotlin.math.min
import kotlin.math.tan

class ThermalParticleErosion(data: ThermalSimulationData) : AsyncSimulator {

    private val iterations: Int = data.iterations
    private val talusAngle: Float = data.talusAngle
    private val cascade: Boolean = data.cascade
    private val cellSize: Float = data.cellSize
    private val talusAngleThreshold: Float = tan(talusAngle.toRadians())
    private val sedimentRemoval: Float = 0.05f
    private val sedimentRemovalMultiplier: Float = 5.0f
    private val thermalFalloff: Float = 0.98f
    private val roughness: Float = 0.0025f
    private val abrasion: Float = 0.05f
    private val settling: Float = 0.15f

    override fun simulate(buffer: AsyncNoiseBuffer2): AsyncNoiseBuffer2 = simulateThermalErosionSteepest(buffer)

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
                    val removedSediment = sedimentRemoval * steepestSlope * sedimentRemovalMultiplier

                    //  Erode away
                    map[x][y] -= removedSediment  * falloffMultiplier
                    sediment[xSteepest][ySteepest] += removedSediment
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