package com.github.polyrocketmatt.peak.provider.builder

import com.github.polyrocketmatt.peak.provider.data.CellularNoiseData
import com.github.polyrocketmatt.peak.types.cellular.CellularNoise
import java.lang.IllegalArgumentException

/**
 * Builder for a CellularNoiseProvider object.
 */
class CellularNoiseProviderDataBuilder : ProviderDataBuilder {

    private var seed: Int = 0
    private var frequency: Float = 1.0f
    private var distanceType: CellularNoise.DistanceType = CellularNoise.DistanceType.EUCLIDEAN
    private var returnType: CellularNoise.ReturnType = CellularNoise.ReturnType.DISTANCE

    /**
     * Build the seed of noise octaves.
     *
     * @param value: the seed of the noise
     * @return this builder with the set seed
     * @throws IllegalArgumentException if the value is less than 0
     */
    fun buildSeed(value: Int): CellularNoiseProviderDataBuilder {
        if (value < 0)
            throw IllegalArgumentException("Seed cannot be less than 0")

        this.seed = value
        return this
    }

    /**
     * Build the frequency of noise octaves.
     *
     * @param value: the frequency of the noise
     * @return this builder with the set frequency
     * @throws IllegalArgumentException if the value is less than 0
     */
    fun buildFrequency(value: Float): CellularNoiseProviderDataBuilder {
        if (value < 0)
            throw IllegalArgumentException("Seed cannot be less than 0")

        this.frequency = value
        return this
    }

    /**
     * Build the distance type for the noise.
     *
     * @param value the distance type of the noise
     * @return this builder with the set distance type
     */
    fun buildDistanceType(value: CellularNoise.DistanceType): CellularNoiseProviderDataBuilder {
        this.distanceType = value
        return this
    }

    /**
     * Build the return type for the noise.
     *
     * @param value the return type of the noise
     * @return this builder with the set return type
     */
    fun buildReturnType(value: CellularNoise.ReturnType): CellularNoiseProviderDataBuilder {
        this.returnType = value
        return this
    }

    /**
     * Build the noise data as SimpleNoiseData.
     */
    override fun build(): CellularNoiseData = CellularNoiseData(
        this.seed,
        this.frequency,
        this.distanceType,
        this.returnType,
    )
}