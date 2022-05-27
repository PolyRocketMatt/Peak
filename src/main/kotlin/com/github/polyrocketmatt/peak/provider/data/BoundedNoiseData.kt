package com.github.polyrocketmatt.peak.provider.data

import com.github.polyrocketmatt.peak.provider.BoundedNoiseProvider
import com.github.polyrocketmatt.peak.provider.base.SimpleNoiseProvider
import com.github.polyrocketmatt.peak.types.NoiseUtils
import com.github.polyrocketmatt.peak.types.bounded.BoundedNoise

/**
 * Data for a bounded noise provider.
 */
class BoundedNoiseData(
    val seed: Int,
    val width: Int,
    val height: Int,
    val octaves: Int,
    val gain: Float,
    val type: BoundedNoise.BoundedNoiseType,
    val method: NoiseUtils.InterpolationMethod,
) : NoiseData() {

    /**
     * Get a bounded provider for the given data.
     *
     * @return a bounded noise provider based on the given data
     */
    override fun provider(): SimpleNoiseProvider = BoundedNoiseProvider(this)
}