package com.github.polyrocketmatt.peak.provider.builder

import com.github.polyrocketmatt.peak.provider.data.SimpleNoiseData
import com.github.polyrocketmatt.peak.types.NoiseUtils
import com.github.polyrocketmatt.peak.types.simple.SimpleNoise
import java.lang.IllegalArgumentException

/**
 * Builder for a SimpleNoiseProvider object.
 */
class SimpleNoiseProviderDataBuilder : ProviderDataBuilder {

    private var seed: Int = 0
    private var octaves: Int = 1
    private var scale: Float = 1.0f
    private var gain: Float = 0.5f
    private var lacunarity: Float = 2.0f
    private var type: SimpleNoise.SimpleNoiseType = SimpleNoise.SimpleNoiseType.PERLIN
    private var interpolation: NoiseUtils.InterpolationMethod = NoiseUtils.InterpolationMethod.HERMITE
    private var fractal: SimpleNoise.FractalType = SimpleNoise.FractalType.FBM
    private var skippedOctaves: IntArray = intArrayOf()

    /**
     * Build the seed of noise octaves.
     *
     * @param value: the seed of the noise
     * @return this builder with the set seed
     * @throws IllegalArgumentException if the value is less than 0
     */
    fun buildSeed(value: Int): SimpleNoiseProviderDataBuilder {
        if (value < 0)
            throw IllegalArgumentException("Seed cannot be less than 0")

        this.seed = value
        return this
    }

    /**
     * Build an amount of noise octaves.
     *
     * @param value: the amount of noise octaves
     * @return this builder with the set noise octaves
     * @throws IllegalArgumentException if the value is less than 1
     */
    fun buildOctaves(value: Int): SimpleNoiseProviderDataBuilder {
        if (value < 1)
            throw IllegalArgumentException("Octaves cannot be less than 1")

        this.octaves = value
        return this
    }

    /**
     * Build a scale for the noise.
     *
     * @param value: the scale of the noise
     * @return this builder with the set scale
     */
    fun buildScale(value: Float): SimpleNoiseProviderDataBuilder {
        this.scale = value
        return this
    }

    /**
     * Build a gain parameter for the noise.
     *
     * @param value: the gain of the noise
     * @return this builder with the set gain
     */
    fun buildGain(value: Float): SimpleNoiseProviderDataBuilder {
        this.gain = value
        return this
    }

    /**
     * Build a lacunarity parameter for the noise. This
     * parameter is also known as the granularity.
     *
     * @param value: the lacunarity of the noise
     * @return this builder with the set lacunarity
     */
    fun buildLacunarity(value: Float): SimpleNoiseProviderDataBuilder {
        this.lacunarity = value
        return this
    }

    /**
     * Build the noise type for the noise.
     *
     * @param value: the type of the noise
     * @return this builder with the set type
     */
    fun buildType(value: SimpleNoise.SimpleNoiseType): SimpleNoiseProviderDataBuilder {
        this.type = value
        return this
    }

    /**
     * Build the interpolation method for the noise.
     *
     * @param value: the interpolation method of the noise
     * @return this builder with the set method
     */
    fun buildInterpolation(value: NoiseUtils.InterpolationMethod): SimpleNoiseProviderDataBuilder {
        this.interpolation = value
        return this
    }

    /**
     * Build the fractal type for the noise.
     *
     * @param value: the fractal type of the noise
     * @return this builder with the set fractal type
     */
    fun buildFractal(value: SimpleNoise.FractalType): SimpleNoiseProviderDataBuilder {
        this.fractal = value
        return this
    }

    /**
     * Build the skipped octaves for the noise.
     *
     * @param value: the skipped octaves for the noise
     * @return this builder with the set skipped octaves
     */
    fun buildSkippedOctaves(value: IntArray): SimpleNoiseProviderDataBuilder {
        this.skippedOctaves = value
        return this
    }

    /**
     * Build the skipped octaves for the noise.
     *
     * @param value: the skipped octaves for the noise
     * @return this builder with the set skipped octaves
     */
    @JvmName("buildSkippedOctavesVararg")
    fun buildSkippedOctaves(vararg value: Int): SimpleNoiseProviderDataBuilder {
        this.skippedOctaves = value.toTypedArray().toIntArray()
        return this
    }


    /**
     * Build the noise data as SimpleNoiseData.
     */
    override fun build(): SimpleNoiseData = SimpleNoiseData(
        this.seed,
        this.octaves,
        this.scale,
        this.gain,
        this.lacunarity,
        this.type,
        this.interpolation,
        this.fractal,
        this.skippedOctaves
    )

}