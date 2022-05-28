package com.github.polyrocketmatt.peak

import com.github.polyrocketmatt.peak.buffer.AsyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.operator.normalize
import com.github.polyrocketmatt.peak.provider.builder.SimpleNoiseDataBuilder
import com.github.polyrocketmatt.peak.types.NoiseUtils
import com.github.polyrocketmatt.peak.types.complex.BuyaNoise
import com.github.polyrocketmatt.peak.types.simple.SimpleNoise
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {

    val size = 2048
    val noise = BuyaNoise(100, 0.1f)
    val buffer = AsyncNoiseBuffer2(size, 128).fill(noise).normalize().image()

    ImageIO.write(buffer, "png", File("output/test.png"))
}