package com.github.polyrocketmatt.peak.provider.builder

import com.github.polyrocketmatt.peak.provider.data.FastProviderData
import com.github.polyrocketmatt.peak.types.FastNoise
import java.lang.IllegalArgumentException

/**
 * Builder for a FastProviderData object.
 */
class FastProviderDataBuilder : ProviderDataBuilder {

    private var seed: Int = 0
    private var octaves: Int = 1
    private var scale: Float = 1.0f
    private var gain: Float = 0.5f
    private var lacunarity: Float = 2.0f
    private var type: FastNoise.NoiseType = FastNoise.NoiseType.PERLIN
    private var interpolation: FastNoise.Method = FastNoise.Method.HERMITE
    private var fractal: FastNoise.FractalType = FastNoise.FractalType.FBM
    private var distanceFunction: FastNoise.CellularDistanceFunction = FastNoise.CellularDistanceFunction.EUCLIDEAN
    private var cellularReturnType: FastNoise.CellularReturnType = FastNoise.CellularReturnType.DISTANCE

    /**
     * Build the seed of noise octaves.
     *
     * @param value: the seed of the noise
     * @return this builder with the set seed
     * @throws IllegalArgumentException if the value is less than 0
     */
    fun buildSeed(value: Int): FastProviderDataBuilder {
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
    fun buildOctaves(value: Int): FastProviderDataBuilder {
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
    fun buildScale(value: Float): FastProviderDataBuilder {
        this.scale = value
        return this
    }

    /**
     * Build a gain parameter for the noise.
     *
     * @param value: the gain of the noise
     * @return this builder with the set gain
     */
    fun buildGain(value: Float): FastProviderDataBuilder {
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
    fun buildLacunarity(value: Float): FastProviderDataBuilder {
        this.lacunarity = value
        return this
    }

    /**
     * Build the noise type for the noise.
     *
     * @param value: the type of the noise
     * @return this builder with the set type
     */
    fun buildType(value: FastNoise.NoiseType): FastProviderDataBuilder {
        this.type = value
        return this
    }

    /**
     * Build the interpolation method for the noise.
     *
     * @param value: the interpolation method of the noise
     * @return this builder with the set method
     */
    fun buildInterpolation(value: FastNoise.Method): FastProviderDataBuilder {
        this.interpolation = value
        return this
    }

    /**
     * Build the fractal type for the noise.
     *
     * @param value: the fractal type of the noise
     * @return this builder with the set fractal type
     */
    fun buildFractal(value: FastNoise.FractalType): FastProviderDataBuilder {
        this.fractal = value
        return this
    }

    /**
     * Build the distance function for cellular noise.
     *
     * @param value: the distance function of the noise
     * @return this builder with the set distance function
     */
    fun buildDistanceFunction(value: FastNoise.CellularDistanceFunction): FastProviderDataBuilder {
        this.distanceFunction = value
        return this
    }

    /**
     * Build the return type for cellular noise.
     *
     * @param value: the return type of the noise
     * @return this builder with the set return type
     */
    fun buildReturnType(value: FastNoise.CellularReturnType): FastProviderDataBuilder {
        this.cellularReturnType = value
        return this
    }

    /**
     * Build the FastProviderData.
     */
    override fun build(): FastProviderData = FastProviderData(
        seed,
        octaves,
        scale,
        gain,
        lacunarity,
        type,
        interpolation,
        fractal,
        distanceFunction,
        cellularReturnType
    )

}