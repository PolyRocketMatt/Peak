package com.github.polyrocketmatt.peak.provider.data

import com.github.polyrocketmatt.peak.provider.ComplexNoiseProvider
import com.github.polyrocketmatt.peak.types.ComplexNoise

/**
 * Data object which holds data for a fast complex provider to use.
 */
data class ComplexProviderData(
    val seed: Int = 0,
    val width: Int = 0,
    val height: Int = 0,
    val octaves: Int = 1,
    val gain: Float = 0.5f,
    val type: ComplexNoise.NoiseType = ComplexNoise.NoiseType.POLYNOMIAL,
    val interpolation: ComplexNoise.Method = ComplexNoise.Method.HERMITE,
) : ProviderData() {

    /**
     * Get a complex provider for the given data.
     *
     * @return a complex noise provider based on the given data
     */
    override fun provider(): ComplexNoiseProvider = ComplexNoiseProvider(this)

}