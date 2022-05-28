package com.github.polyrocketmatt.peak.provider.data

import com.github.polyrocketmatt.peak.provider.SimpleNoiseProvider
import com.github.polyrocketmatt.peak.types.NoiseUtils
import com.github.polyrocketmatt.peak.types.simple.SimpleNoise

/**
 * Data for a simple noise provider.
 */
data class SimpleNoiseData(
    val seed: Int = 0,
    val octaves: Int = 1,
    val scale: Float = 1.0f,
    val gain: Float = 0.5f,
    val lacunarity: Float = 2.0f,
    val type: SimpleNoise.SimpleNoiseType = SimpleNoise.SimpleNoiseType.PERLIN,
    val interpolation: NoiseUtils.InterpolationMethod = NoiseUtils.InterpolationMethod.HERMITE,
    val fractal: SimpleNoise.FractalType = SimpleNoise.FractalType.FBM
) : NoiseData() {

    /**
     * Get a simple provider for the given data.
     *
     * @return a simple noise provider based on the given data
     */
    override fun provider(): SimpleNoiseProvider = SimpleNoiseProvider(this)

}