package com.github.polyrocketmatt.peak.types.bounded

import com.github.polyrocketmatt.peak.types.NoiseEvaluator

/**
 * Represents bounded noise.
 */
abstract class BoundedNoise(val width: Int, val height: Int) : NoiseEvaluator() {

    /**
     * The types of bounded noise.
     */
    enum class BoundedNoiseType { POLYNOMIAL }

}