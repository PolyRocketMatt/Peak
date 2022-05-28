package com.github.polyrocketmatt.peak.types.simple

import com.github.polyrocketmatt.peak.types.NoiseEvaluator

/**
 * Represents simple noise.
 */
abstract class SimpleNoise : NoiseEvaluator() {

    /**
     * The types of simple noise.
     */
    enum class SimpleNoiseType { VALUE, VALUE_FRACTAL, PERLIN, PERLIN_FRACTAL, SIMPLEX, SIMPLEX_FRACTAL, WHITE }

    /**
     * The types of fractals
     */
    enum class FractalType { FBM, BILLOW, RIGID }

    /**
     * Computes the fractal bound of the noise.
     */
    abstract fun calculateFractalBounding()

    /**
     * Get the type of simple noise.
     *
     * @return the type of simple noise.
     */
    abstract fun type(): SimpleNoiseType

    protected var fractalBounding: Float = 0.0f

    init { calculateFractalBounding() }

}