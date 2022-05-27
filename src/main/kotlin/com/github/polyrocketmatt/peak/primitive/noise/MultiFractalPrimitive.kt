package com.github.polyrocketmatt.peak.primitive.noise

import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.operator.Operators
import com.github.polyrocketmatt.peak.provider.builder.SimpleNoiseDataBuilder
import com.github.polyrocketmatt.peak.types.NoiseUtils
import com.github.polyrocketmatt.peak.types.simple.SimpleNoise

/**
 * Defines a primitive from Multi-layer Simplex noise. A buffer is constructed
 * from the given width and height values.
 *
 * @param width: the width of the buffer
 * @param height: the height of the buffer
 */
class MultiFractalPrimitive(width: Int, height: Int) : NoisePrimitive(SyncNoiseBuffer2(width, height), true) {

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
    var fractal: SimpleNoise.FractalType = SimpleNoise.FractalType.FBM
        set(value) {
            this.update = true
            field = value
        }

    private var noise: com.github.polyrocketmatt.peak.provider.SimpleNoiseProvider = com.github.polyrocketmatt.peak.provider.SimpleNoiseProvider(SimpleNoiseDataBuilder().build())

    init { recompute() }

    /**
     * Recompute Multi-layer Simplex noise in the buffer.
     */
    override fun recompute() {
        this.update = false
        this.noise = com.github.polyrocketmatt.peak.provider.SimpleNoiseProvider(
            SimpleNoiseDataBuilder()
                .buildType(SimpleNoise.SimpleNoiseType.SIMPLEX_FRACTAL)
                .buildInterpolation(NoiseUtils.InterpolationMethod.HERMITE)
                .buildSeed(seed)
                .buildOctaves(octaves)
                .buildScale(scale)
                .buildGain(gain)
                .buildLacunarity(lacunarity)
                .buildFractal(fractal)
                .build()
        )

        this.update(Operators.NORMALIZE.operate(this.buffer().fill(this.noise)))
    }

}