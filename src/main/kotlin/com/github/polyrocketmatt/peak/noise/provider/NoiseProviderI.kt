package com.github.polyrocketmatt.peak.noise.provider

@FunctionalInterface
interface NoiseProviderI {

    fun noise(x: Int, z: Int): Float

}