package com.github.polyrocketmatt.peak.noise.primitive

import com.github.polyrocketmatt.peak.noise.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.noise.buffer.operator.Operators
import com.github.polyrocketmatt.peak.noise.provider.FastNoiseProvider
import com.github.polyrocketmatt.peak.noise.provider.builder.FastProviderDataBuilder
import com.github.polyrocketmatt.peak.noise.types.FastNoise
import java.awt.image.BufferedImage

class PerlinPrimitive(sX: Int, sZ: Int) : NoisePrimitive(NoiseBuffer(sX, sZ)) {

    var seed: Int = 1
        set(value) {
            update = true
            field = value
        }
    var octaves: Int = 8
        set(value) {
            update = true
            field = value
        }
    var scale: Float = 1.0f
        set(value) {
            update = true
            field = value
        }
    var gain: Float = 0.5f
        set(value) {
            update = true
            field = value
        }
    var lacunarity: Float = 2.0f
        set(value) {
            update = true
            field = value
        }
    var fractal: FastNoise.FractalType = FastNoise.FractalType.FBM
        set(value) {
            update = true
            field = value
        }

    private var noise: FastNoiseProvider = FastNoiseProvider(FastProviderDataBuilder().build())
    private var update: Boolean = false

    init { recompute() }

    override fun recompute() {
        this.update = false
        this.noise = FastNoiseProvider(
            FastProviderDataBuilder()
                .buildType(FastNoise.NoiseType.PERLIN_FRACTAL)
                .buildInterpolation(FastNoise.Method.HERMITE)
                .buildSeed(this.seed)
                .buildOctaves(this.octaves)
                .buildScale(this.scale)
                .buildGain(this.gain)
                .buildLacunarity(this.lacunarity)
                .buildFractal(this.fractal)
                .build()
        )
        val width = this.buffer.width()
        val height = this.buffer.height()

        for (x in 0 until width) for (z in 0 until height)
            this.buffer[x][z] = this.noise.noise(x, z)

        Operators.NORMALIZE.operate(this.buffer)
    }

    override fun buffer(): NoiseBuffer {
        if (update)
            recompute()
        return this.buffer
    }

    override fun image(): BufferedImage = buffer.image()

}