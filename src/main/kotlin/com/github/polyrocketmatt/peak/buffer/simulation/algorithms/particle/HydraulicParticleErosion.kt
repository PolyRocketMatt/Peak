package com.github.polyrocketmatt.peak.buffer.simulation.algorithms.particle

import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.game.math.fastAbs
import com.github.polyrocketmatt.game.math.i
import com.github.polyrocketmatt.game.math.sqrt
import com.github.polyrocketmatt.peak.ArrayUtils
import com.github.polyrocketmatt.peak.buffer.AsyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.AsyncNoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.simulation.Simulation
import com.github.polyrocketmatt.peak.buffer.simulation.ErosionData
import com.github.polyrocketmatt.peak.buffer.simulation.data.HydraulicSimulationData
import com.github.polyrocketmatt.peak.exception.SimulationException
import com.github.polyrocketmatt.peak.math.toRadians
import com.github.polyrocketmatt.peak.types.NoiseEvaluator
import java.lang.Exception
import kotlin.math.*
import kotlin.random.Random

/**
 * Simulation that simulates hydraulic erosion processes on a buffer.
 */
class HydraulicParticleErosion(val data: HydraulicSimulationData) : Simulation {

    /**
     * Parameters for particle based
     */
    private val size: Int = data.size
    private val iterations: Int = data.iterations
    private val radius: Int = data.radius
    private val rng: Random = Random(data.seed)
    private val inertia: Float = data.inertia
    private val sedimentCapacityMultiplier: Float = data.sedimentCapacityMultiplier
    private val minSedimentCapacity: Float = data.minimalSedimentCapacity
    private val erosionSpeed: Float = data.erosionSpeed
    private val downcutting: Float = data.downcutting
    private val downcuttingMultiplier: Float = data.downcuttingMultiplier
    private val depositSpeed: Float = data.depositSpeed
    private val evaporateSpeed: Float = data.evaporateSpeed
    private val gravity: Float = data.gravity
    private val maxParticleLifetime: Int = data.maxParticleLifetime
    private val initialWaterVolume: Float = data.initialWaterVolume
    private val initialSpeed: Float = data.initialSpeed
    private val evaluator: NoiseEvaluator = data.evaluator
    private val sedimentCascade: Boolean = data.sedimentCascade
    private val sedimentTalus: Float =  tan(data.sedimentTalus.toRadians())
    private val sedimentRoughness: Float = data.sedimentRoughness
    private val sedimentAbrasion: Float = data.sedimentAbrasion
    private val sedimentSettling: Float = data.sedimentSettling
    private val sedimentCellSize: Float = data.sedimentCellSize
    private val sedimentCascadeIterations: Int = data.sedimentCascadeIteration
    private val sedimentCascadeRemoval: Float = data.sedimentCascadeRemoval
    private var erosionBrushIndices = Array(size * size) { IntArray(radius * radius * 4) { 0 } }
    private var erosionBrushWeights = Array(size * size) { FloatArray(radius * radius * 4) { 0f } }

    /**
     * Perform a simulation on a 2D buffer.
     *
     * @param buffer: the buffer to perform the simulation on
     * @return a new buffer that contains the effect of the simulation on the buffer
     */
    override fun simulate(buffer: AsyncNoiseBuffer2): AsyncNoiseBuffer2 = simulateHydraulicErosion(buffer)

    /**
     * Perform a simulation on a 3D buffer.
     *
     * @param buffer: the buffer to perform the simulation on
     * @return a new buffer that contains the effect of the simulation on the buffer
     */
    override fun simulate(buffer: AsyncNoiseBuffer3): AsyncNoiseBuffer3 = throw SimulationException("Hydraulic erosion is not supported for 3D buffers!")

    private fun simulateHydraulicErosion(simulationBuffer: AsyncNoiseBuffer2): AsyncNoiseBuffer2 {
        initBrushes()

        val width = simulationBuffer.width()
        val height = simulationBuffer.height()
        val map = ArrayUtils.linearize(simulationBuffer)
        val percentile = iterations / 10
        for (i in 0 until iterations) {
            var posX = rng.nextFloat() * size
            var posY = rng.nextFloat() * size
            var dirX = 0f
            var dirY = 0f
            var speed = initialSpeed
            var water = initialWaterVolume
            var sediment = 0f
            var sedimentDownCut = downcutting

            for (lifetime in 0 until maxParticleLifetime) {
                val nodeX = posX.i()
                val nodeY = posY.i()
                val dropletIndex = nodeY * size + nodeX

                // Calculate droplet's offset inside the cell (0,0) = at NW node, (1,1) = at SE node
                val cellOffsetX = posX - nodeX
                val cellOffsetY = posY - nodeY

                // Calculate droplet's height and direction of flow with bilinear interpolation of surrounding heights
                val heightAndGradient = calculateHeightAndGradient(map, posX, posY)

                // Update the droplet's direction and position (move position 1 unit regardless of speed)
                dirX = dirX * inertia - heightAndGradient.gradX * (1f - inertia)
                dirY = dirY * inertia - heightAndGradient.gradY * (1f - inertia)

                //  Compute gradient effect
                //  Generates a very nice noise distribution!
                //val gradientFactor = heightAndGradient.gradX + heightAndGradient.gradY
                //val gradientFactor = (heightAndGradient.gradX * heightAndGradient.gradY)

                // Normalize direction
                val len = (dirX * dirX + dirY * dirY).sqrt()
                if (len != 0f) {
                    dirX /= len
                    dirY /= len
                }

                posX += dirX
                posY += dirY

                // Stop simulating droplet if it's not moving or has flowed over edge of map
                if ((dirX == 0f && dirY == 0f) || posX < 0 || posX >= size - 2 || posY < 0 || posY >= size - 2)
                    break

                // Find the droplet's new height and calculate the deltaHeight
                val newHeight = calculateHeightAndGradient(map, posX, posY).height
                val deltaHeight = newHeight - heightAndGradient.height

                // Calculate the droplet's sediment capacity (higher when moving fast down a slope and contains lots of water)
                val sedimentCapacity: Float = max(-deltaHeight * speed * water * sedimentCapacityMultiplier, minSedimentCapacity)

                // If carrying more sediment than capacity, or if flowing uphill:
                if (sediment > sedimentCapacity || deltaHeight > 0) {
                    // If moving uphill (deltaHeight > 0) try fill up to the current height, otherwise deposit a fraction of the excess sediment
                    val amountToDeposit = (if (deltaHeight > 0) min(deltaHeight, sediment) else (sediment - sedimentCapacity) * depositSpeed)
                    sediment -= amountToDeposit

                    // Add the sediment to the four nodes of the current cell using bilinear interpolation
                    // Deposition is not distributed over a radius (like erosion) so that it can fill small pits
                    map[dropletIndex] += amountToDeposit * (1 - cellOffsetX) * (1 - cellOffsetY)
                    map[dropletIndex + 1] += amountToDeposit * cellOffsetX * (1 - cellOffsetY)
                    map[dropletIndex + size] += amountToDeposit * (1 - cellOffsetX) * cellOffsetY
                    map[dropletIndex + size + 1] += amountToDeposit * cellOffsetX * cellOffsetY

                    //  Check cascade
                    if (sedimentCascade && sediment < 0.5f * sedimentCapacity) {
                        var x = posX.i()
                        var y = posY.i()
                        var xSteepest = -1
                        var ySteepest = -1
                        var steepestSlope = -1.0f
                        var performed = true
                        var iteration = 0

                        while (performed && iteration < sedimentCascadeIterations) {
                            //  Check neighbourhood
                            for (oX in -1 .. 1) for (oY in -1 .. 1) {
                                val iX = x + oX
                                val iY = y + oY

                                //  Boundary
                                if (iX in 2 until size - 2 && iY in 2 until size - 2 && iX != x && iY != y) {
                                    val neighbourIndex = iY * size + iX
                                    val valueDiff = map[dropletIndex] - map[neighbourIndex]


                                    //  If current is higher than neighbour
                                    if (valueDiff > 0.0f && valueDiff > steepestSlope) {
                                        steepestSlope = valueDiff
                                        xSteepest = iX
                                        ySteepest = iY
                                    }
                                }
                            }

                            // Sediment talus angle exceeded
                            if (steepestSlope / sedimentCellSize > sedimentTalus) {
                                //  Leave some sediment
                                val deposit = (amountToDeposit * sedimentCascadeRemoval) / 4

                                map[dropletIndex] += deposit * (1 - cellOffsetX) * (1 - cellOffsetY)
                                map[dropletIndex + 1] += deposit * cellOffsetX * (1 - cellOffsetY)
                                map[dropletIndex + size] += deposit * (1 - cellOffsetX) * cellOffsetY
                                map[dropletIndex + size + 1] += deposit * cellOffsetX * cellOffsetY

                                cascade(x + 1, y, map, width, height)
                                cascade(x, y + 1, map, width, height)
                                cascade(x - 1, y, map, width, height)
                                cascade(x, y - 1, map, width, height)
                                cascade(x, y, map, width, height)

                                if (xSteepest in 0 until width && ySteepest in 0 until height && xSteepest != x && ySteepest != y)
                                    cascade(xSteepest, ySteepest, map, width, height)

                                x = xSteepest
                                y = ySteepest
                            } else
                                performed = false

                            iteration++
                        }
                    }
                } else {
                    // Erode a fraction of the droplet's current carry capacity.
                    // Clamp the erosion to the change in height so that it doesn't dig a hole in the terrain behind the droplet
                    val amountToErode = min ((sedimentCapacity - sediment) * erosionSpeed, -deltaHeight) * sedimentDownCut

                    // Use erosion brush to erode from all nodes inside the droplet's erosion radius
                    for (brushPointIndex in 0 until erosionBrushIndices[dropletIndex].size) {
                        val nodeIndex = erosionBrushIndices[dropletIndex][brushPointIndex]
                        val weighedErodeAmount = amountToErode * erosionBrushWeights[dropletIndex][brushPointIndex]
                        val deltaSediment = if (map[nodeIndex] < weighedErodeAmount) map[nodeIndex] else weighedErodeAmount

                        map[nodeIndex] -= deltaSediment
                        sediment += deltaSediment
                    }
                }

                // Update droplet's speed and water content
                speed = (speed * speed + deltaHeight * gravity).sqrt()
                water *= (1f - evaporateSpeed)

                //  Update cut
                sedimentDownCut *= downcuttingMultiplier
            }

            if (i % percentile == 0)
                println("   Simulated Water: ${i / iterations.f() * 100f}%")
        }

        return ArrayUtils.deLinearize(map, simulationBuffer.width(), simulationBuffer.height())
    }

    private fun calculateHeightAndGradient(map: FloatArray, posX: Float, posY: Float): ErosionData {
        val coordX = posX.i()
        val coordY = posY.i()

        // Calculate droplet's offset inside the cell (0,0) = at NW node, (1,1) = at SE node
        val x = posX - coordX
        val y = posY - coordY

        // Calculate heights of the four nodes of the droplet's cell
        val nodeIndex = coordY * size + coordX
        val heightNW = try { map[nodeIndex] } catch (ex: Exception) { evaluator.noise(posX, posY) }
        val heightNE = try { map[nodeIndex + 1] } catch (ex: Exception) { evaluator.noise(posX + 1, posY) }
        val heightSW = try { map[nodeIndex + size] } catch (ex: Exception) { evaluator.noise(posX, posY + 1) }
        val heightSE = try { map[nodeIndex + size + 1] } catch (ex: Exception) { evaluator.noise(posX + 1, posY + 1) }

        // Calculate droplet's direction of flow with bilinear interpolation of height difference along the edges
        val gradientX = (heightNE - heightNW) * (1 - y) + (heightSE - heightSW) * y
        val gradientY = (heightSW - heightNW) * (1 - x) + (heightSE - heightNE) * x

        // Calculate height with bilinear interpolation of the heights of the nodes of the cell
        val height = heightNW * (1 - x) * (1 - y) + heightNE * x * (1 - y) + heightSW * (1 - x) * y + heightSE * x * y

        return ErosionData(height, gradientX, gradientY)
    }

    private fun initBrushes() {
        erosionBrushIndices = Array(size * size) { IntArray(radius * radius * 4) { 0 } }
        erosionBrushWeights = Array(size * size) { FloatArray(radius * radius * 4) { 0f } }

        val radiusSq = radius * radius
        val xOffsets = IntArray(radiusSq * 4)
        val yOffsets = IntArray(radiusSq * 4)
        val weights = FloatArray(radiusSq * 4)
        var weightSum = 0.0f
        var addIndex = 0

        for (i in erosionBrushIndices.indices) {
            val centreX = i % size
            val centreY = i / size

            if (centreY <= radius || centreY >= size - radius || centreX <= radius + 1 || centreX >= size - radius ) {
                weightSum = 0.0f
                addIndex = 0

                for (y in -radius..radius) for (x in -radius .. radius) {
                    val distSq = x * x + y * y
                    if (distSq < radiusSq) {
                        val coordX = centreX + x
                        val coordY = centreY + y

                        if (coordX in 0 until size && coordY in 0 until size) {
                            val weight = 1.0f - (distSq / radiusSq).sqrt()

                            weightSum += weight
                            weights[addIndex] = weight
                            xOffsets[addIndex] = x
                            yOffsets[addIndex] = y
                            addIndex++
                        }
                    }
                }
            }

            val numEntries = addIndex
            erosionBrushIndices[i] = IntArray(numEntries)
            erosionBrushWeights[i] = FloatArray(numEntries)

            for (j in 0 until numEntries) {
                erosionBrushIndices[i][j] = (yOffsets[j] + centreY) * size + xOffsets[j] + centreX
                erosionBrushWeights[i][j] = weights[j] / weightSum
            }
        }
    }

    private fun cascade(x: Int, y: Int, h: FloatArray, width: Int, height: Int) {
        val base = y * width + x
        for (oX in -1 .. 1) for (oY in -1 .. 1) {
            val iX = oX + x
            val iY = oY + y

            //  Bound check
            if (iX !in 0 until width || iY !in 0 until height)
                continue

            val index = iY * width + iX

            //  Pile size difference
            val diff = (h[base] - (h[index]))
            val excess = diff.fastAbs() - sedimentRoughness
            if (excess <= 0)
                continue

            //  Transfer mass
            val transfer = if (diff > 0f) min(h[base], excess / 2f) else -min(h[index], excess / 2f)
            val settlingAmount = sedimentSettling * transfer
            val abrasionAmount = sedimentAbrasion * transfer

            h[base] -= abrasionAmount
            h[index] += settlingAmount
        }
    }


}