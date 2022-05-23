package com.github.polyrocketmatt.peak.noise.provider

import com.github.polyrocketmatt.peak.noise.FastNoise

/**
 * Data object which holds data for a noise provider to use.
 */
data class ProviderData(
    val seed: Int = 0,
    val octaves: Int = 1,
    val scale: Float = 1.0f,
    val gain: Float = 0.5f,
    val lacunarity: Float = 2.0f,
    val type: FastNoise.NoiseType = FastNoise.NoiseType.PERLIN,
    val interpolation: FastNoise.Method = FastNoise.Method.HERMITE,
    val fractal: FastNoise.FractalType = FastNoise.FractalType.FBM,
    val distanceFunction: FastNoise.CellularDistanceFunction = FastNoise.CellularDistanceFunction.EUCLIDEAN,
    val cellularReturnType: FastNoise.CellularReturnType = FastNoise.CellularReturnType.DISTANCE
) {

    /**
     * Get a provider for the given data.
     *
     * @return a noise provider based on the given data
     */
    fun provider(): NoiseProvider = NoiseProvider(this)

}