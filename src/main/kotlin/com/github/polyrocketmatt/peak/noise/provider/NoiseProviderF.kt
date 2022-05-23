package com.github.polyrocketmatt.peak.noise.provider

@FunctionalInterface
interface NoiseProviderF {

    fun noise(x: Float, z: Float): Float

}