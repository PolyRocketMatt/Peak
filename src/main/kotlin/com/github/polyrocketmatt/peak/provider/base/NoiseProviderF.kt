package com.github.polyrocketmatt.peak.provider.base

@FunctionalInterface
interface NoiseProviderF {

    fun noise(x: Float, z: Float): Float

}