package com.github.polyrocketmatt.peak.provider.builder

import com.github.polyrocketmatt.peak.provider.data.ComplexProviderData
import com.github.polyrocketmatt.peak.types.ComplexNoise
import java.lang.IllegalArgumentException

/**
 * Builder for a ComplexProviderData object.
 */
class ComplexProviderDataBuilder : ProviderDataBuilder {

    private var seed: Int = 0
    private var width: Int = 0
    private var height: Int = 0
    private var octaves: Int = 1
    private var gain: Float = 0.5f
    private var type: ComplexNoise.NoiseType = ComplexNoise.NoiseType.POLYNOMIAL
    private var interpolation: ComplexNoise.Method = ComplexNoise.Method.HERMITE

    /**
     * Build the seed of noise octaves.
     *
     * @param value: the seed of the noise
     * @return this builder with the set seed
     * @throws IllegalArgumentException if the value is less than 0
     */
    fun buildSeed(value: Int): ComplexProviderDataBuilder {
        if (value < 0)
            throw IllegalArgumentException("Octaves cannot be less than 0")

        this.seed = value
        return this
    }

    /**
     * Build the x resolution of noise.
     *
     * @param value: the x-resolution of the noise
     * @return this builder with the set x-resolution
     * @throws IllegalArgumentException if the value is less than 0
     */
    fun buildX(value: Int): ComplexProviderDataBuilder {
        if (value < 0)
            throw IllegalArgumentException("Octaves cannot be less than 0")
        this.width = value
        return this
    }

    /**
     * Build the z resolution of noise.
     *
     * @param value: the z-resolution of the noise
     * @return this builder with the set z-resolution
     * @throws IllegalArgumentException if the value is less than 0
     */
    fun buildZ(value: Int): ComplexProviderDataBuilder {
        if (value < 0)
            throw IllegalArgumentException("Octaves cannot be less than 0")
        this.height = value
        return this
    }

    /**
     * Build an amount of noise octaves.
     *
     * @param value: the amount of noise octaves
     * @return this builder with the set noise octaves
     * @throws IllegalArgumentException if the value is less than 1
     */
    fun buildOctaves(value: Int): ComplexProviderDataBuilder {
        if (value < 1)
            throw IllegalArgumentException("Octaves cannot be less than 1")

        this.octaves = value
        return this
    }

    /**
     * Build a gain parameter for the noise.
     *
     * @param value: the gain of the noise
     * @return this builder with the set gain
     */
    fun buildGain(value: Float): ComplexProviderDataBuilder {
        this.gain = value
        return this
    }

    /**
     * Build the noise type for the noise.
     *
     * @param value: the type of the noise
     * @return this builder with the set type
     */
    fun buildType(value: ComplexNoise.NoiseType): ComplexProviderDataBuilder {
        this.type = value
        return this
    }

    /**
     * Build the interpolation method for the noise.
     *
     * @param value: the interpolation method of the noise
     * @return this builder with the set method
     */
    fun buildInterpolation(value: ComplexNoise.Method): ComplexProviderDataBuilder {
        this.interpolation = value
        return this
    }

    /**
     * Build the ComplexProviderData.
     */
    override fun build(): ComplexProviderData = ComplexProviderData(
        seed,
        width,
        height,
        octaves,
        gain,
        type,
        interpolation,
    )

}