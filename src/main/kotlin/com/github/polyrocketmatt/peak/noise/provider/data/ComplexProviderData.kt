package com.github.polyrocketmatt.peak.noise.provider.data

import com.github.polyrocketmatt.peak.noise.provider.ComplexNoiseProvider
import com.github.polyrocketmatt.peak.noise.provider.primitive.SimpleNoiseProvider
import com.github.polyrocketmatt.peak.noise.types.ComplexNoise

/**
 * Data object which holds data for a fast complex provider to use.
 */
data class ComplexProviderData(
    val seed: Int = 0,
    val sX: Int = 0,
    val sZ: Int = 0,
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
    override fun provider(): SimpleNoiseProvider = ComplexNoiseProvider(this)

}