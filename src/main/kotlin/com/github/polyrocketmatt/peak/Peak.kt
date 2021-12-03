package com.github.polyrocketmatt.peak

import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.game.math.i
import com.github.polyrocketmatt.game.math.matrix.FloatMatrix2
import com.github.polyrocketmatt.peak.noise.*
import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.io.File
import javax.imageio.ImageIO
import kotlin.random.Random

fun main(args: Array<String>) {
    val size = 512
    val img = BufferedImage(size, size, TYPE_INT_RGB)
    val perlin = PolynomialNoise(PolynomialBuffer(size, 8, 2.0f))
    val map = Array(size) { FloatArray(size) { 0.0f } }
    val octaves = 8
    val scale = 1.0f

    for (x in 0 until size)
        for (y in 0 until size) {
            map[x][y] = perlin.noise(x.f(), y.f())

            /*
            val sX = x / size.f()
            val sY = y / size.f()

            var frequency = 1.0f
            var amplitude = 1.0f
            for (i in 0 until octaves) {
                val tX = sX / scale * frequency
                val tY = sY / scale * frequency

                map[x][y] += perlin.noise(tX, tY) * amplitude
                amplitude *= 0.5f
                frequency *= 2.0f
            }

             */
        }

    val nmap = FloatMatrix2(map).normalize()
    for (x in 0 until size)
        for (y in 0 until size) {
            val g = (255.0f * map[x][y]).i()

            img.setRGB(x, y, Color(g, g, g).rgb)
        }


    ImageIO.write(img, "png", File("output/polynomial.png"))
}