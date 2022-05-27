package com.github.polyrocketmatt.peak.provider.builder

import com.github.polyrocketmatt.peak.provider.data.CellularNoiseData
import com.github.polyrocketmatt.peak.types.NoiseEvaluator
import com.github.polyrocketmatt.peak.types.cellular.CellularNoise
import java.lang.IllegalArgumentException

class CellularNoiseDataBuilder : DataBuilder {

    private var seed: Int = 0
    private var cellularType: CellularNoise.CellularType = CellularNoise.CellularType.VORONOI
    private var distanceType: CellularNoise.DistanceType = CellularNoise.DistanceType.EUCLIDEAN
    private var returnType: CellularNoise.ReturnType = CellularNoise.ReturnType.DISTANCE
    private var lookup: NoiseEvaluator? = null

    /**
     * Build the seed of noise octaves.
     *
     * @param value: the seed of the noise
     * @return this builder with the set seed
     * @throws IllegalArgumentException if the value is less than 0
     */
    fun buildSeed(value: Int): CellularNoiseDataBuilder {
        if (value < 0)
            throw IllegalArgumentException("Seed cannot be less than 0")

        this.seed = value
        return this
    }

    /**
     * Build the cellular type for the noise.
     *
     * @param value the cellular type of the noise
     * @return this builder with the set cellular type
     */
    fun buildCellularType(value: CellularNoise.CellularType): CellularNoiseDataBuilder {
        this.cellularType = value
        return this
    }

    /**
     * Build the distance type for the noise.
     *
     * @param value the distance type of the noise
     * @return this builder with the set distance type
     */
    fun buildDistanceType(value: CellularNoise.DistanceType): CellularNoiseDataBuilder {
        this.distanceType = value
        return this
    }

    /**
     * Build the return type for the noise.
     *
     * @param value the return type of the noise
     * @return this builder with the set return type
     */
    fun buildReturnType(value: CellularNoise.ReturnType): CellularNoiseDataBuilder {
        this.returnType = value
        return this
    }

    /**
     * Build the lookup noise for the noise.
     *
     * @param value the lookup noise of the noise
     * @return this builder with the set lookup noise
     */
    fun buildLookup(value: NoiseEvaluator?): CellularNoiseDataBuilder {
        this.lookup = value
        return this
    }

    /**
     * Build the noise data as SimpleNoiseData.
     */
    override fun build(): CellularNoiseData = CellularNoiseData(
        this.seed,
        this.cellularType,
        this.distanceType,
        this.returnType,
        this.lookup
    )
}