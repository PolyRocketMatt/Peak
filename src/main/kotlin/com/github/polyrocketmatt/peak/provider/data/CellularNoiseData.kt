package com.github.polyrocketmatt.peak.provider.data

import com.github.polyrocketmatt.peak.provider.CellularNoiseProvider
import com.github.polyrocketmatt.peak.provider.base.SimpleNoiseProvider
import com.github.polyrocketmatt.peak.types.NoiseEvaluator
import com.github.polyrocketmatt.peak.types.cellular.CellularNoise

/**
 * Data for a cellular noise provider.
 */
class CellularNoiseData(
    val seed: Int,
    val frequency: Float,
    val distanceType: CellularNoise.DistanceType,
    val returnType: CellularNoise.ReturnType,
) : NoiseData() {

    /**
     * Get a cellular provider for the given data.
     *
     * @return a cellular noise provider based on the given data
     */
    override fun provider(): SimpleNoiseProvider = CellularNoiseProvider(this)
}