package com.github.polyrocketmatt.peak.provider.base

@FunctionalInterface
interface NoiseProviderI {

    fun noise(x: Int, z: Int): Float

}