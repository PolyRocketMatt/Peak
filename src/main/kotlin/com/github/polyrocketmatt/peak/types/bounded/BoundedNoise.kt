package com.github.polyrocketmatt.peak.types.bounded

import com.github.polyrocketmatt.peak.types.NoiseEvaluator

abstract class BoundedNoise(val width: Int, val height: Int) : NoiseEvaluator {

    enum class BoundedNoiseType { POLYNOMIAL }

}