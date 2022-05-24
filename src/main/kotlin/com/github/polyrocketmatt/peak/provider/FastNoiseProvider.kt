package com.github.polyrocketmatt.peak.provider

import com.github.polyrocketmatt.game.math.d
import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.peak.provider.data.FastProviderData
import com.github.polyrocketmatt.peak.provider.primitive.SimpleNoiseProvider
import com.github.polyrocketmatt.peak.types.FastNoise

/**
 * Represents a fast noise provider.
 */
class FastNoiseProvider(data: FastProviderData) : SimpleNoiseProvider() {

    private val noise: FastNoise

    init {
        noise = FastNoise(data.seed)
        noise.octaves = data.octaves
        noise.scale = data.scale
        noise.gain = data.gain
        noise.lacunarity = data.lacunarity
        noise.noiseType = data.type
        noise.method = data.interpolation
        noise.fractalType = data.fractal
        noise.cellularDistanceFunction = data.distanceFunction
        noise.cellularReturnType = data.cellularReturnType
    }

    override fun noise(x: Float, z: Float): Float = noise.noise(x, z)

    override fun noise(x: Double, z: Double): Double = noise(x.f(), z.f()).d()

    override fun noise(x: Int, z: Int): Float = noise(x.f(), z.f())

}