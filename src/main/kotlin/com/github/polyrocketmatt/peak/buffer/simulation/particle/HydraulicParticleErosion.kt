package com.github.polyrocketmatt.peak.buffer.simulation.particle

import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.game.math.i
import com.github.polyrocketmatt.game.math.sqrt
import com.github.polyrocketmatt.peak.ArrayUtils
import com.github.polyrocketmatt.peak.buffer.AsyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.AsyncNoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.operator.normalize
import com.github.polyrocketmatt.peak.buffer.simulation.AsyncSimulator
import com.github.polyrocketmatt.peak.buffer.simulation.ErosionData
import com.github.polyrocketmatt.peak.buffer.simulation.data.HydraulicSimulationData
import com.github.polyrocketmatt.peak.exception.SimulationException
import com.github.polyrocketmatt.peak.types.NoiseEvaluator
import java.io.File
import java.lang.Exception
import javax.imageio.ImageIO
import kotlin.math.*
import kotlin.random.Random

class HydraulicParticleErosion(val data: HydraulicSimulationData) : AsyncSimulator {

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
    private val depositSpeed: Float = data.depositSpeed
    private val evaporateSpeed: Float = data.evaporateSpeed
    private val gravity: Float = data.gravity
    private val maxParticleLifetime: Int = data.maxParticleLifetime
    private val initialWaterVolume: Float = data.initialWaterVolume
    private val initialSpeed: Float = data.initialSpeed
    private val talusSlippageAngle: Float = data.talusSlippageAngle * 0.017453292f
    private val talusDepositionFactor: Float = data.talusDepositionMultiplier
    private val evaluator: NoiseEvaluator = data.evaluator
    private var erosionBrushIndices = Array(size * size) { IntArray(radius * radius * 4) { 0 } }
    private var erosionBrushWeights = Array(size * size) { FloatArray(radius * radius * 4) { 0f } }

    override fun simulate(buffer: AsyncNoiseBuffer2): AsyncNoiseBuffer2 = simulateHydraulicErosion(buffer)

    override fun simulate(buffer: AsyncNoiseBuffer3): AsyncNoiseBuffer3 = throw SimulationException("Hydraulic erosion is not supported for 3D buffers!")

    private fun simulateHydraulicErosion(simulationBuffer: AsyncNoiseBuffer2): AsyncNoiseBuffer2 {
        initBrushes()

        val map = ArrayUtils.linearize(simulationBuffer)
        val percentile = iterations / 10
        val sizeSq = size * size
        for (i in 0 until iterations) {
            var posX = rng.nextFloat() * size
            var posY = rng.nextFloat() * size
            var dirX = 0f
            var dirY = 0f
            var speed = initialSpeed
            var water = initialWaterVolume
            var sediment = 0f

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
                //val heightFactor = -(newHeight * newHeight  - 2f * newHeight + 1.0f) + 1.0f

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

                    //  Compute talus angle and perform slippage
                    val gradientAngleX = asin(deltaHeight / heightAndGradient.gradX)
                    val gradientAngleY = asin(deltaHeight / heightAndGradient.gradY)
                    val talusAngle = max(0.00001f, (gradientAngleX + gradientAngleY) / 2f)

                    //  TODO: Compute trigonometry tables
                    //  TODO: See if talus angle computation can be replaced with dot product between normal and sum of gradient
                    if (talusAngle > talusSlippageAngle) {
                        //  Compute value to distribute
                        val talusDeposition = deltaHeight * tan(talusAngle) + talusDepositionFactor * deltaHeight

                        //  Divide over lower neighbours, depending on height difference
                        for (x in -1..1) for (y in -1 until 1)
                            if (x != 0 && y != 0) {
                                val depositionIndex = (posX.i() + x) + (posY.i() + y) * size
                                if (depositionIndex in 0 until sizeSq) {
                                    map[depositionIndex] += talusDeposition
                                }
                            }
                    }
                } else {
                    // Erode a fraction of the droplet's current carry capacity.
                    // Clamp the erosion to the change in height so that it doesn't dig a hole in the terrain behind the droplet
                    val amountToErode = min ((sedimentCapacity - sediment) * erosionSpeed, -deltaHeight)

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
            }

            if (i % percentile == 0)
                println("   Simulated Water: ${i / iterations.f() * 100f}%")
        }

        val buff = ArrayUtils.deLinearize(map, simulationBuffer.width(), simulationBuffer.height())

        ImageIO.write(buff.normalize().image(), "png", File("output/water.png"))

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

}