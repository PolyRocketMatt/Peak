package com.github.polyrocketmatt.peak.primitive.noise

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.buffer.operator.Operators
import com.github.polyrocketmatt.peak.provider.FastNoiseProvider
import com.github.polyrocketmatt.peak.provider.builder.FastProviderDataBuilder
import com.github.polyrocketmatt.peak.types.FastNoise

/**
 * Defines a primitive from Multi-layer Perlin noise. A buffer is constructed
 * from the given width and height values.
 *
 * @param width: the width of the buffer
 * @param height: the height of the buffer
 */
class PerlinPrimitive(width: Int, height: Int) : NoisePrimitive(NoiseBuffer(width, height), true) {

    /**
     * Constructor for a primitive with equal width and height buffer.
     *
     * @param size: the width and height of the buffer
     */
    constructor(size: Int) : this(size, size)

    /**
     * The seed of the primitive.
     */
    var seed: Int = 1
        set(value) {
            this.update = true
            field = value
        }

    /**
     * The amount of octaves for the primitive.
     */
    var octaves: Int = 8
        set(value) {
            this.update = true
            field = value
        }

    /**
     * The scale of the primitive.
     */
    var scale: Float = 1.0f
        set(value) {
            this.update = true
            field = value
        }

    /**
     * The gain of the primitive.
     */
    var gain: Float = 0.5f
        set(value) {
            this.update = true
            field = value
        }

    /**
     * The lacunarity of the primitive
     */
    var lacunarity: Float = 2.0f
        set(value) {
            this.update = true
            field = value
        }

    /**
     * The fractal type of the primitive.
     */
    var fractal: FastNoise.FractalType = FastNoise.FractalType.FBM
        set(value) {
            this.update = true
            field = value
        }

    private var noise: FastNoiseProvider = FastNoiseProvider(FastProviderDataBuilder().build())

    init { recompute() }

    /**
     * Recompute Multi-layer Perlin noise in the buffer.
     */
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
        val width = this.buffer().width()
        val height = this.buffer().height()

        for (x in 0 until width) for (z in 0 until height)
            this.buffer()[x][z] = this.noise.noise(x, z)

        Operators.NORMALIZE.operate(this.buffer())
    }

}