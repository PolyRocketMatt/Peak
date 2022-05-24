package com.github.polyrocketmatt.peak.types

interface NoiseEvaluator {

    fun noise(nX: Float, nY: Float): Float

    fun noise(nX: Float, nY: Float, nZ: Float): Float

}