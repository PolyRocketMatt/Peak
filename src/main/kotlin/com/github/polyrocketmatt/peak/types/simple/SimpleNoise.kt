package com.github.polyrocketmatt.peak.types.simple

import com.github.polyrocketmatt.peak.types.NoiseEvaluator

abstract class SimpleNoise : NoiseEvaluator {

    enum class SimpleNoiseType { VALUE, VALUE_FRACTAL, PERLIN, PERLIN_FRACTAL, SIMPLEX, SIMPLEX_FRACTAL, WHITE }
    enum class FractalType { FBM, BILLOW, RIGID }

    abstract fun calculateFractalBounding()

    abstract fun type(): SimpleNoiseType

    protected var fractalBounding: Float = 0.0f

    init { calculateFractalBounding() }

}