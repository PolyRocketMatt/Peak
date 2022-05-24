package com.github.polyrocketmatt.peak.noise.provider.primitive

@FunctionalInterface
interface NoiseProviderD {

    fun noise(x: Double, z: Double): Double

}