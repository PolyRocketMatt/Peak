package com.github.polyrocketmatt.peak.noise

import com.github.polyrocketmatt.game.math.matrix.FloatMatrix2
import com.github.polyrocketmatt.game.noise.NoiseType
import com.github.polyrocketmatt.game.noise.OpenSimplexNoise
import com.github.polyrocketmatt.game.noise.PerlinNoise
import java.lang.IllegalArgumentException

class NoiseFactory {

    companion object {

        fun fbm(buffer: NoiseDataBuffer): Array<FloatArray> {
            val size = buffer.size
            val map = Array(size) { FloatArray(size) }
            val noise = when (buffer.type) {
                NoiseType.PERLIN -> PerlinNoise(buffer.seed)
                NoiseType.SIMPLEX -> OpenSimplexNoise(buffer.seed)
                NoiseType.POLYNOMIAL -> throw IllegalArgumentException("Polynomial noise does not support fractal Brownian motion!")
                else -> TODO("Not Yet Implemented!")
            }

            val octaves = buffer.octaves
            val scale = buffer.scale
            val persistence = buffer.persistence
            val lacunarity = buffer.lacunarity
            for (x in 0 until size)
                for (y in 0 until size) {
                    var amplitude = 1.0f
                    var frequency = 1.0f

                    for (i in 0 until octaves) {
                        val sX = x / scale * frequency
                        val sY = y / scale * frequency

                        map[x][y] += noise.noise(sX, sY) * amplitude
                        amplitude *= persistence
                        frequency *= lacunarity
                    }
                }

            return if (buffer.normalize) FloatMatrix2(map).normalize() else map
        }

    }

}