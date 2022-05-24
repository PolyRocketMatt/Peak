package com.github.polyrocketmatt.peak.noise.provider.primitive

@FunctionalInterface
interface NoiseProviderF {

    fun noise(x: Float, z: Float): Float

}