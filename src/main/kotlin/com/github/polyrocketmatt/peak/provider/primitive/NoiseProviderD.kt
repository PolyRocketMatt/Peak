package com.github.polyrocketmatt.peak.provider.primitive

@FunctionalInterface
interface NoiseProviderD {

    fun noise(x: Double, z: Double): Double

}