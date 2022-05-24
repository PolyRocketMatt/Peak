package com.github.polyrocketmatt.peak.provider.primitive

@FunctionalInterface
interface NoiseProviderF {

    fun noise(x: Float, z: Float): Float

}