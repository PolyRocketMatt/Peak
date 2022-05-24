package com.github.polyrocketmatt.peak.provider.base

@FunctionalInterface
interface NoiseProviderD {

    fun noise(x: Double, z: Double): Double

}