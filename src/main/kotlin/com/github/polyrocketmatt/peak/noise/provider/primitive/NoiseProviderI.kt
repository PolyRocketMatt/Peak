package com.github.polyrocketmatt.peak.noise.provider.primitive

@FunctionalInterface
interface NoiseProviderI {

    fun noise(x: Int, z: Int): Float

}