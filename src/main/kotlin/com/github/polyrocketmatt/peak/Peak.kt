package com.github.polyrocketmatt.peak

import com.github.polyrocketmatt.peak.buffer.AsyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.operator.blend
import com.github.polyrocketmatt.peak.buffer.operator.normalize
import com.github.polyrocketmatt.peak.buffer.operator.scale
import com.github.polyrocketmatt.peak.buffer.simulation.particle.HydraulicParticleErosion
import com.github.polyrocketmatt.peak.buffer.simulation.particle.WindParticleErosion
import com.github.polyrocketmatt.peak.types.NoiseUtils
import com.github.polyrocketmatt.peak.types.complex.BuyaNoise
import com.github.polyrocketmatt.peak.types.simple.*
import org.imgscalr.Scalr
import java.io.File
import javax.imageio.ImageIO
import kotlin.random.Random

fun main(args: Array<String>) {

    val size = 256
    val seed = Random.nextInt(Int.MAX_VALUE)
    val fractal = FractalNoise(seed, NoiseUtils.InterpolationMethod.QUINTIC, 8, 0.2f, 0.5f, 2.0f, SimpleNoise.FractalType.FBM, SimpleNoise.SimpleNoiseType.SIMPLEX)
    val perlin = PerlinNoise(seed, NoiseUtils.InterpolationMethod.QUINTIC)
    val simplex = SimplexNoise(seed)
    val smooth = SmoothDetailFractalNoise(seed, NoiseUtils.InterpolationMethod.QUINTIC, 8, 0.2f, 0.5f, 2.0f, SimpleNoise.FractalType.FBM, SimpleNoise.SimpleNoiseType.SIMPLEX, intArrayOf(2, 3, 4, 5))
    val value = ValueNoise(seed, NoiseUtils.InterpolationMethod.QUINTIC)
    val white = WhiteNoise(seed)

    //ImageIO.write(AsyncNoiseBuffer2(size, 16).fill(fractal).normalize().image(), "png", File("img/fractal.png"))
    ImageIO.write(AsyncNoiseBuffer2(size, 16).fill(perlin).normalize().image(), "png", File("img/perlin.png"))
    //ImageIO.write(AsyncNoiseBuffer2(size, 16).fill(simplex).normalize().image(), "png", File("img/simplex.png"))
    //ImageIO.write(AsyncNoiseBuffer2(size, 16).fill(smooth).normalize().image(), "png", File("img/smooth.png"))
    ImageIO.write(AsyncNoiseBuffer2(size, 16).fill(value).normalize().image(), "png", File("img/value.png"))
    ImageIO.write(AsyncNoiseBuffer2(size, 16).fill(white).normalize().image(), "png", File("img/white.png"))

    /*
    val seed = Random.nextInt(Int.MAX_VALUE)
    val size = 512
    val buya = BuyaNoise(seed, 0.01f)
    val fractal = FractalNoise(seed, NoiseUtils.InterpolationMethod.HERMITE, 8, 0.5f, 0.5f, 2.0f, type = SimpleNoise.SimpleNoiseType.SIMPLEX_FRACTAL)
    val buyaBuffer = AsyncNoiseBuffer2(size, 128).fill(buya).normalize()
    val fractalBuffer = AsyncNoiseBuffer2(size, 128).fill(fractal).normalize()
    val buffer = (fractalBuffer.blend(buyaBuffer, 0.8f)).normalize() as AsyncNoiseBuffer2

    println("Seed: $seed")
    println("Finished calculations...")
    println("Eroding...")

     */

    /*
    val windErosion = WindParticleErosion(size, 500000, size, fractal)
    val hydraulicErosion = HydraulicParticleErosion(Random.nextInt(Int.MAX_VALUE), 1000000, size, 3, fractal)
    val windEroded = windErosion.simulate(buffer).normalize().scale(0.7f)
    val waterEroded = hydraulicErosion.simulate(buffer).normalize()
    val eroded = (waterEroded.blend(windEroded, 0.75f)).normalize().scale(0.7f)
    val erodedImg = Scalr.resize(eroded.image(), Scalr.Method.ULTRA_QUALITY, size * 16, size * 16, Scalr.OP_GRAYSCALE)

    ImageIO.write(buffer.scale(0.7f).image(), "png", File("output/buffer.png"))
    ImageIO.write(erodedImg, "png", File("output/erosion.png"))

     */
}