package com.github.polyrocketmatt.peak.provider

import com.github.polyrocketmatt.game.math.d
import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.peak.provider.data.ComplexProviderData
import com.github.polyrocketmatt.peak.provider.base.SimpleNoiseProvider
import com.github.polyrocketmatt.peak.types.ComplexNoise

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

    override fun noise(x: Float, z: Float): Float = noise.noise(x, z)

    override fun noise(x: Double, z: Double): Double = noise(x.f(), z.f()).d()

    override fun noise(x: Int, z: Int): Float = noise(x.f(), z.f())
}