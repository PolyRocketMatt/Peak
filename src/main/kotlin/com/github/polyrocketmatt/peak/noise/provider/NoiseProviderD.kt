package com.github.polyrocketmatt.peak.noise.provider

@FunctionalInterface
interface NoiseProviderD {

    fun noise(x: Double, z: Double): Double

}