package com.github.polyrocketmatt.peak.provider.data

import com.github.polyrocketmatt.peak.provider.PatternNoiseProvider
import com.github.polyrocketmatt.peak.provider.base.SimpleNoiseProvider
import com.github.polyrocketmatt.peak.types.pattern.PatternNoise

/**
 * Data for a pattern noise provider.
 */
data class PatternNoiseData(
    val type: PatternNoise.PatternNoiseType,
    val size: Int,
    val scale: Float,
    val orientation: PatternNoise.PatternOrientation
) : NoiseData() {

    /**
     * Get a pattern provider for the given data.
     *
     * @return a pattern noise provider based on the given data
     */
    override fun provider(): SimpleNoiseProvider = PatternNoiseProvider(this)
}