package com.github.polyrocketmatt.peak

import com.github.polyrocketmatt.peak.buffer.AsyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.operator.*
import com.github.polyrocketmatt.peak.types.NoiseUtils
import com.github.polyrocketmatt.peak.types.simple.FractalNoise
import com.github.polyrocketmatt.peak.types.simple.SimpleNoise
import java.io.File
import javax.imageio.ImageIO
import kotlin.random.Random

fun main(args: Array<String>) {
    val seed = Random.nextInt(Int.MAX_VALUE)
    val size = 512
    val fractal = FractalNoise(seed, NoiseUtils.InterpolationMethod.QUINTIC, 12, 0.2f, 0.5f, 2.0f, SimpleNoise.FractalType.FBM, SimpleNoise.SimpleNoiseType.SIMPLEX)
    val buffer = AsyncNoiseBuffer2(size, size , 16).fill(fractal, 0f, 0f, 0f).normalize() as AsyncNoiseBuffer2

    ImageIO.write(buffer.normalize().scale(0.7f).image(), "png", File("output/init.png"))
}