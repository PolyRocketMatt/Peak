package com.github.polyrocketmatt.peak.provider.primitive

@FunctionalInterface
interface NoiseProviderI {

    fun noise(x: Int, z: Int): Float

}