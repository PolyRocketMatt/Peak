package com.github.polyrocketmatt.peak.noise.types

interface NoiseEvaluator {

    fun noise(nX: Float, nY: Float): Float

    fun noise(nX: Float, nY: Float, nZ: Float): Float

}