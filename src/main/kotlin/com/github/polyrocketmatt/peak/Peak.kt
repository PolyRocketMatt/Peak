package com.github.polyrocketmatt.peak

import com.github.polyrocketmatt.peak.buffer.operator.max
import com.github.polyrocketmatt.peak.buffer.operator.scale
import com.github.polyrocketmatt.peak.primitive.MultiFractalPrimitive
import com.github.polyrocketmatt.peak.primitive.PerlinPrimitive
import com.github.polyrocketmatt.peak.types.FastNoise
import java.io.File
import javax.imageio.ImageIO
import kotlin.random.Random

fun main(args: Array<String>) {

    val size = 2048
    val perlin = PerlinPrimitive(size, size)
    val simplex = MultiFractalPrimitive(size, size)

    perlin.scale = 0.1f
    perlin.octaves = 11
    perlin.seed = Random.nextInt(0, 100000)
    simplex.octaves = 8
    simplex.scale = 0.1f
    simplex.fractal = FastNoise.FractalType.BILLOW
    simplex.seed = Random.nextInt(0, 100000)

    val buffer = (perlin.buffer() max  simplex.buffer().scale(0.55f)).scale(0.8f)

    ImageIO.write(buffer.image(), "png", File("img/test.png"))

}