package com.github.polyrocketmatt.peak.provider

import com.github.polyrocketmatt.game.math.d
import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.peak.provider.data.FastProviderData
import com.github.polyrocketmatt.peak.provider.base.SimpleNoiseProvider
import com.github.polyrocketmatt.peak.types.FastNoise

/**
 * Represents a fast noise provider.
 */
class FastNoiseProvider(data: FastProviderData) : SimpleNoiseProvider() {

    private val noise: FastNoise

    init {
        noise = FastNoise(
            data.seed,
            data.interpolation,
            data.type,
            data.scale,
            data.frequency,
            data.octaves,
            data.lacunarity,
            data.gain,
            data.fractal,
            data.distanceFunction,
            data.cellularReturnType,
            data.lookup
        )
    }

    /**
     * Sample noise at the given x- and z-coordinates.
     *
     * @param x: the x-coordinate to sample noise from
     * @param z: the z-coordinate to sample noise from
     * @return the sampled noise at the given x- and z-coordinates
     */
    override fun noise(x: Float, z: Float): Float = noise.noise(x, z)

    /**
     * Sample noise at the given x- and z-coordinates.
     *
     * @param x: the x-coordinate to sample noise from
     * @param z: the z-coordinate to sample noise from
     * @return the sampled noise at the given x- and z-coordinates
     */
    override fun noise(x: Double, z: Double): Double = noise(x.f(), z.f()).d()

    /**
     * Sample noise at the given x- and z-coordinates.
     *
     * @param x: the x-coordinate to sample noise from
     * @param z: the z-coordinate to sample noise from
     * @return the sampled noise at the given x- and z-coordinates
     */
    override fun noise(x: Int, z: Int): Float = noise(x.f(), z.f())

}