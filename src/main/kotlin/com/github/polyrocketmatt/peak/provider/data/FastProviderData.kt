package com.github.polyrocketmatt.peak.provider.data

import com.github.polyrocketmatt.peak.provider.FastNoiseProvider
import com.github.polyrocketmatt.peak.types.FastNoise

/**
 * Data object which holds data for a fast noise provider to use.
 */
data class FastProviderData(
    val seed: Int = 0,
    val octaves: Int = 1,
    val scale: Float = 1.0f,
    val frequency: Float = 0.01f,
    val gain: Float = 0.5f,
    val lacunarity: Float = 2.0f,
    val type: FastNoise.NoiseType = FastNoise.NoiseType.PERLIN,
    val interpolation: FastNoise.Method = FastNoise.Method.HERMITE,
    val fractal: FastNoise.FractalType = FastNoise.FractalType.FBM,
    val distanceFunction: FastNoise.CellularDistanceFunction = FastNoise.CellularDistanceFunction.EUCLIDEAN,
    val cellularReturnType: FastNoise.CellularReturnType = FastNoise.CellularReturnType.DISTANCE,
    val lookup: FastNoise? = null
) : ProviderData() {

    /**
     * Get a fast provider for the given data.
     *
     * @return a fast noise provider based on the given data
     */
    override fun provider(): FastNoiseProvider = FastNoiseProvider(this)

}