package com.github.polyrocketmatt.peak.types.bounded

import com.github.polyrocketmatt.peak.types.NoiseEvaluator
import com.github.polyrocketmatt.peak.types.simple.SimpleNoise

/**
 * Represents bounded noise.
 */
abstract class BoundedNoise(val width: Int, val height: Int, val depth: Int) : NoiseEvaluator() {

    /**
     * The types of bounded noise.
     */
    enum class BoundedNoiseType { POLYNOMIAL, PERLIN }

    /**
     * Get the type of bounded noise.
     *
     * @return the type of bounded noise.
     */
    abstract fun type(): BoundedNoiseType

}