package com.github.polyrocketmatt.peak

import com.github.polyrocketmatt.peak.buffer.AsyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.operator.blend
import com.github.polyrocketmatt.peak.buffer.operator.normalize
import com.github.polyrocketmatt.peak.buffer.operator.scale
import com.github.polyrocketmatt.peak.buffer.simulation.data.AeolianSimulationData
import com.github.polyrocketmatt.peak.buffer.simulation.data.HydraulicSimulationData
import com.github.polyrocketmatt.peak.buffer.simulation.particle.AeolianParticleErosion
import com.github.polyrocketmatt.peak.buffer.simulation.particle.HydraulicParticleErosion
import com.github.polyrocketmatt.peak.types.NoiseUtils
import com.github.polyrocketmatt.peak.types.complex.BuyaNoise
import com.github.polyrocketmatt.peak.types.simple.FractalNoise
import com.github.polyrocketmatt.peak.types.simple.SimpleNoise
import java.io.File
import javax.imageio.ImageIO
import kotlin.random.Random

fun main(args: Array<String>) {

    val seed = Random.nextInt(Int.MAX_VALUE)
    val size = 256
    val buya = BuyaNoise(seed, 0.01f)
    val fractal = FractalNoise(seed, NoiseUtils.InterpolationMethod.HERMITE, 8, 0.5f, 0.5f, 2.0f, type = SimpleNoise.SimpleNoiseType.SIMPLEX_FRACTAL)
    val buyaBuffer = AsyncNoiseBuffer2(size, 128).fill(buya).normalize()
    val fractalBuffer = AsyncNoiseBuffer2(size, 128).fill(fractal).normalize()
    val buffer = (fractalBuffer.blend(buyaBuffer, 0.8f)).normalize() as AsyncNoiseBuffer2

    println("Seed: $seed")
    println("Finished calculations...")
    println("Eroding...")

    val windErosion = AeolianParticleErosion(AeolianSimulationData(size, 100000, size, suspension = 0.001f, abrasion = 0.8f, evaluator = fractal))
    //val hydraulicErosion = HydraulicParticleErosion(HydraulicSimulationData(seed, 500000, size, 3, depositSpeed = 0.5f, erosionSpeed = 0.1f))
    //val windEroded = windErosion.simulate(buffer).normalize().scale(0.7f)
    val eroded = windErosion.simulate(buffer).normalize().scale(0.7f).image()
    //val eroded = (waterEroded.blend(windEroded, 0.75f)).normalize().scale(0.7f)
    //val erodedImg = Scalr.resize(eroded.image(), Scalr.Method.ULTRA_QUALITY, size * 16, size * 16, Scalr.OP_GRAYSCALE)

    ImageIO.write(buffer.scale(0.7f).image(), "png", File("img/aeolian_erosion_before.png"))
    ImageIO.write(eroded, "png", File("img/aeolian_erosion_after.png"))
}