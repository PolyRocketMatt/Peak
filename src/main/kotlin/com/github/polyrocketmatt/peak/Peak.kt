package com.github.polyrocketmatt.peak

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.operator.blend
import com.github.polyrocketmatt.peak.buffer.operator.multiply
import com.github.polyrocketmatt.peak.primitive.noise.MultiFractalPrimitive
import com.github.polyrocketmatt.peak.provider.PatternNoiseProvider
import com.github.polyrocketmatt.peak.types.pattern.PatternNoise
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {

    val size = 512

    val radial = PatternNoiseProvider(size, PatternNoise.PatternType.RADIAL_STRIPE, 16.5f, PatternNoise.PatternOrientation.DIAGONAL)
    val noise = MultiFractalPrimitive(size)

    noise.seed = 1000
    noise.octaves = 8
    noise.scale = 0.1f

    val bufferRad = NoiseBuffer2(size).fill(radial)
    val bufferStr = noise.buffer()
    val buffer = bufferRad.blend(bufferStr, 0.25f)

    ImageIO.write(buffer.image(), "png", File("img/test.png"))

}