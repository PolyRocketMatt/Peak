package com.github.polyrocketmatt.peak.buffer.simulation.algorithms.particle

import com.github.polyrocketmatt.game.Vec2f
import com.github.polyrocketmatt.game.Vec2i
import com.github.polyrocketmatt.game.Vec3f
import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.game.math.fastAbs
import com.github.polyrocketmatt.game.math.i
import com.github.polyrocketmatt.game.math.sqrt
import com.github.polyrocketmatt.peak.ArrayUtils
import com.github.polyrocketmatt.peak.annotation.Ref
import com.github.polyrocketmatt.peak.buffer.AsyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.AsyncNoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.operator.add
import com.github.polyrocketmatt.peak.buffer.simulation.Simulation
import com.github.polyrocketmatt.peak.buffer.simulation.ErosionData
import com.github.polyrocketmatt.peak.buffer.simulation.data.AeolianSimulationData
import com.github.polyrocketmatt.peak.types.NoiseEvaluator
import java.lang.Exception
import kotlin.math.min
import kotlin.random.Random

/**
 * Simulation that simulates Aeolian erosion processes on a buffer.
 */
@Ref("Landscape synthesis achieved through erosion and deposition process simulation")
class AeolianParticleErosion(val data: AeolianSimulationData) : Simulation {

    private val size: Int = data.size
    private val iterations: Int = data.iterations
    private val rng: Random = Random(data.seed)
    private val inertia: Float = data.inertia
    private val gravity: Float = data.gravity
    private val suspension: Float = data.suspension
    private val abrasion: Float = data.abrasion
    private val roughness: Float = data.roughness
    private val settling: Float = data.settling
    private val maxParticleLifetime: Int = data.maxParticleLifetime
    private val initialSpeed: Vec3f = data.initialSpeed
    private val initialSediment: Float = data.initialSediment
    private val evaluator: NoiseEvaluator = data.evaluator
    private val sizeSq: Int = size * size

    /**
     * Perform a simulation on a 2D buffer.
     *
     * @param buffer: the buffer to perform the simulation on
     * @return a new buffer that contains the effect of the simulation on the buffer
     */
    override fun simulate(buffer: AsyncNoiseBuffer2): AsyncNoiseBuffer2 = simulateWindErosion(buffer)

    /**
     * Perform a simulation on a 3D buffer.
     *
     * @param buffer: the buffer to perform the simulation on
     * @return a new buffer that contains the effect of the simulation on the buffer
     */
    override fun simulate(buffer: AsyncNoiseBuffer3): AsyncNoiseBuffer3 { TODO("Not yet implemented") }

    private fun simulateWindErosion(simulationBuffer: AsyncNoiseBuffer2) : AsyncNoiseBuffer2 {
        val map = ArrayUtils.linearize(simulationBuffer)
        val sedimentMap = map.copyOf()
        val percentile = iterations / 10
        for (i in 0 until iterations) {
            var posX = rng.nextFloat() * size
            var posY = rng.nextFloat() * size
            var dirX = 0f
            var dirY = 0f
            var speed = initialSpeed
            var sediment = initialSediment

            for (j in 0 until maxParticleLifetime) {
                val nodeX = posX.i()
                val nodeY = posY.i()
                val particleIndex = nodeY * size + nodeX

                // Calculate particle's height and direction of flow with bilinear interpolation of surrounding heights
                val heightAndGradient = calculateHeightAndGradient(map, sedimentMap, posX, posY)
                val partialDerivativeX = Vec3f(1f, heightAndGradient.gradX, 0f)
                val partialDerivativeZ = Vec3f(0f, heightAndGradient.gradY, 1f)
                val n = partialDerivativeX.cross(partialDerivativeZ)

                // TODO: Do we need inertia?
                // Update the droplet's direction and position (move position 1 unit regardless of speed)
                dirX = dirX * inertia - heightAndGradient.gradX * (1f - inertia)
                dirY = dirY * inertia - heightAndGradient.gradY * (1f - inertia)

                // Normalize direction
                val len = (dirX * dirX + dirY * dirY).sqrt()
                if (len != 0f) {
                    dirX /= len
                    dirY /= len
                }

                posX += dirX
                posY += dirY

                // Stop simulating particle if it's not moving or has flowed over edge of map
                if ((dirX == 0f && dirY == 0f) || posX < 0 || posX >= size - 1 || posY < 0 || posY >= size - 1)
                    break

                // Find the particle's new height and calculate the deltaHeight
                val newHeight = calculateHeightAndGradient(map, sedimentMap, posX, posY).height
                val newIndex = posY.i() * size + posX.i()
                val pos = Vec2f(posX, posY)

                //println("Ok?")

                //  Collision
                if (newHeight <= map[newIndex] + sedimentMap[newIndex]) {
                    //println("    Collision")

                    val force = speed.magnitude() * (sedimentMap[newIndex] + map[newIndex] - heightAndGradient.height)
                    val suspensionAmount = suspension * force
                    val abrasionAmount = abrasion * force * sediment

                    //  Abrasion
                    if (sedimentMap[particleIndex] <= 0f) {
                        //println("        Should abrade")

                        sedimentMap[particleIndex] = 0f
                        map[particleIndex] -= abrasionAmount
                        sedimentMap[particleIndex] += abrasionAmount

                        //println("        Abraded")
                    }

                    //  Suspension
                    else if (sedimentMap[particleIndex] > suspensionAmount) {
                        //println("        Should suspend")
                        sedimentMap[particleIndex] -= suspensionAmount
                        sediment += suspensionAmount

                        cascade(pos, particleIndex, map, sedimentMap)

                        //println("        Suspended")
                    }

                    else
                        sedimentMap[particleIndex] = 0f
                }

                //  Flying
                else {
                    //println("    Flying")

                    val sedimentAmount = suspension * sediment

                    sediment -= sedimentAmount
                    sedimentMap[newIndex] += 0.5f * sedimentAmount
                    sedimentMap[particleIndex] -= 0.5f * sedimentAmount

                    cascade(pos, newIndex, map, sedimentMap)
                    cascade(pos, particleIndex, map, sedimentMap)

                    //println("        Flown")
                }

                //  Update speed according to state
                speed = if (heightAndGradient.height > map[particleIndex] + sedimentMap[particleIndex]) {
                    speed + Vec3f(0f, -gravity, 0f)         //  Flying
                } else {
                    //println("$n")

                    Vec3f.ZERO//speed + speed.cross(n).cross(n)                 //  Contact
                }

                //  Update general speed
                speed = speed + (initialSpeed - speed).scalarMultiplication(.1f)

                //println("Increased speed")

                //  Particle has no speed (equilibrium movement)
                if (speed.magnitude() < 0.01f)
                    break
            }

            if (i % percentile == 0)
                println("   Simulated Wind: ${i / iterations.f() * 100f}%")
        }

        return ArrayUtils.deLinearize(map, size, size).add(ArrayUtils.deLinearize(sedimentMap, size, size)) as AsyncNoiseBuffer2
    }

    private fun cascade(pos: Vec2f, i: Int, h: FloatArray, s: FloatArray) {
        //  Neighbour positions
        val nx = intArrayOf(-1, -1, -1, 0, 0, 1, 1, 1)
        val ny = intArrayOf(-1, 0, 1, -1, 1, -1, 0, 1)
        val n = intArrayOf(i - size - 1, i - size, i - size + 1, i - 1, i + 1, i + size - 1, i + size, i + size + 1)
        var iPos: Vec2i

        for (m in 0 until 8) {
            iPos = pos.toIntVector()

            if (n[m] < 0 || n[m] >= sizeSq)
                continue

            //  Bound check
            if (iPos.iX + nx[m] >= size || iPos.iY + ny[m] >= size)
                continue
            if (iPos.iX + nx[m] < 0 || iPos.iY + ny[m] < 0)
                continue

            //  Pile size difference
            val diff = (h[i] + s[i] - (h[n[m]] + s[n[m]]))
            val excess = diff.fastAbs() - roughness
            if (excess <= 0)
                continue

            //  Transfer mass
            val transfer = if (diff > 0f) min(s[i], excess / 2f) else -min(s[n[m]], excess / 2f)
            var settlingAmount = settling * transfer
            if (n[m] < 2 * size || n[m] >= (sizeSq - (2 * size)))
                settlingAmount = 0f

            s[i] -= settlingAmount
            s[n[m]] += settlingAmount
        }
    }

    private fun calculateHeightAndGradient(map: FloatArray, sediment: FloatArray, posX: Float, posY: Float): ErosionData {
        val coordX = posX.i()
        val coordY = posY.i()

        // Calculate droplet's offset inside the cell (0,0) = at NW node, (1,1) = at SE node
        val x = posX - coordX
        val y = posY - coordY

        // Calculate heights of the four nodes of the droplet's cell
        val nodeIndex = coordY * size + coordX
        val heightNW = try { map[nodeIndex] + sediment[nodeIndex] } catch (ex: Exception) { evaluator.noise(posX, posY) }
        val heightNE = try { map[nodeIndex + 1] + sediment[nodeIndex + 1]} catch (ex: Exception) { evaluator.noise(posX + 1, posY) }
        val heightSW = try { map[nodeIndex + size] + sediment[nodeIndex + size] } catch (ex: Exception) { evaluator.noise(posX, posY + 1) }
        val heightSE = try { map[nodeIndex + size + 1] + sediment[nodeIndex + size + 1] } catch (ex: Exception) { evaluator.noise(posX + 1, posY + 1) }

        // Calculate droplet's direction of flow with bilinear interpolation of height difference along the edges
        val gradientX = (heightNE - heightNW) * (1 - y) + (heightSE - heightSW) * y
        val gradientY = (heightSW - heightNW) * (1 - x) + (heightSE - heightNE) * x

        // Calculate height with bilinear interpolation of the heights of the nodes of the cell
        val height = heightNW * (1 - x) * (1 - y) + heightNE * x * (1 - y) + heightSW * (1 - x) * y + heightSE * x * y

        return ErosionData(height, gradientX, gradientY)
    }

}