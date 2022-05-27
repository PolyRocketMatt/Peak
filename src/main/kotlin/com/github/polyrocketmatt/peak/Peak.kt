package com.github.polyrocketmatt.peak

import com.github.polyrocketmatt.peak.buffer.operator.*
import com.github.polyrocketmatt.peak.primitive.noise.CellularPrimitive
import com.github.polyrocketmatt.peak.primitive.noise.MultiFractalPrimitive
import com.github.polyrocketmatt.peak.types.cellular.CellularNoise
import com.github.polyrocketmatt.peak.types.simple.SimpleNoise
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {

    val size = 2048
    val noise = MultiFractalPrimitive(size)
    val cells = CellularPrimitive(size)
    val detail = MultiFractalPrimitive(size)

    noise.seed = 10//Random.nextInt(10000)
    noise.octaves = 10
    noise.scale = 0.1f
    noise.fractal = SimpleNoise.FractalType.BILLOW

    cells.seed = 10//Random.nextInt(10000)
    cells.frequency = 0.01f
    cells.returnType = CellularNoise.ReturnType.DISTANCE_2_MUL

    detail.seed = 10 * 31
    detail.octaves = 12
    noise.scale = 0.1f
    noise.fractal = SimpleNoise.FractalType.RIGID

    val buffer = (((noise.buffer().blend(cells.buffer(), 0.3f))
        .push(0.0f, 0.9f, 10f)
        .normalize()) multiply  detail.buffer()).normalize()

    ImageIO.write(buffer.image(), "png", File("img/test.png"))

}