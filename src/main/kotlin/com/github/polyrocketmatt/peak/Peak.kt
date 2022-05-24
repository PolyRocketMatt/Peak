package com.github.polyrocketmatt.peak

import com.github.polyrocketmatt.peak.buffer.operator.*
import com.github.polyrocketmatt.peak.primitive.noise.CellularPrimitive
import com.github.polyrocketmatt.peak.primitive.noise.PerlinPrimitive
import com.github.polyrocketmatt.peak.types.FastNoise
import java.io.File
import javax.imageio.ImageIO
import kotlin.random.Random

fun main(args: Array<String>) {

    val size = 2048
    val perlin = PerlinPrimitive(size, size)
    val cellular = CellularPrimitive(size, size)

    perlin.seed = Random.nextInt(0, 100000)
    perlin.scale = 0.1f
    perlin.octaves = 11
    cellular.seed = Random.nextInt(0, 100000)
    cellular.returnType = FastNoise.CellularReturnType.DISTANCE_2_MUL

    val perlinBuffer = perlin.buffer()
    val cellularBuffer = cellular.buffer()

    val buffer = (perlinBuffer subtract  cellularBuffer).normalize() //((perlinBuffer pow cellularBuffer) add (perlinBuffer.blend(cellularBuffer, 0.85f))).normalize()

    ImageIO.write(buffer.image(), "png", File("img/test.png"))

}