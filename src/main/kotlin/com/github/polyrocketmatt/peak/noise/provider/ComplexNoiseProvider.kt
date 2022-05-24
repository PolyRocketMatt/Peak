package com.github.polyrocketmatt.peak.noise.provider

import com.github.polyrocketmatt.peak.noise.provider.data.ComplexProviderData
import com.github.polyrocketmatt.peak.noise.provider.primitive.SimpleNoiseProvider
import com.github.polyrocketmatt.peak.noise.types.ComplexNoise

/**
 * Represents a complex noise provider.
 */
class ComplexNoiseProvider(data: ComplexProviderData) : SimpleNoiseProvider() {

    private val noise: ComplexNoise

    init {
        noise = ComplexNoise(data.seed, data.sX, data.sZ)
        noise.octaves = data.octaves
        noise.gain = data.gain
        noise.type = data.type
        noise.method = data.interpolation
    }

    override fun noise(x: Double, z: Double): Double {
        TODO("Not yet implemented")
    }

    override fun noise(x: Float, z: Float): Float {
        TODO("Not yet implemented")
    }

    override fun noise(x: Int, z: Int): Float {
        TODO("Not yet implemented")
    }
}