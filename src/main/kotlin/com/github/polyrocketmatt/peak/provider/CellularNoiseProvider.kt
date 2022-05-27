package com.github.polyrocketmatt.peak.provider

import com.github.polyrocketmatt.game.math.d
import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.peak.provider.base.SimpleNoiseProvider
import com.github.polyrocketmatt.peak.provider.data.CellularNoiseData
import com.github.polyrocketmatt.peak.types.cellular.CellularNoise
import com.github.polyrocketmatt.peak.types.pattern.*

/**
 * Represents a cellular noise provider
 */
class CellularNoiseProvider(data: CellularNoiseData) : SimpleNoiseProvider() {

    private val noise: CellularNoise

    init {
        this.noise = CellularNoise(data.seed, data.cellularType, data.distanceType, data.returnType, data.lookup)
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

    /**
     * Sample noise at the given x-, y- and z-coordinates.
     *
     * @param x: the x-coordinate to sample noise from
     * @param y: the y-coordinate to sample noise from
     * @param z: the z-coordinate to sample noise from
     * @return the sampled noise at the given x-, y- and z-coordinates
     */
    override fun noise(x: Float, y: Float, z: Float): Float = noise.noise(x, y, z)

    /**
     * Sample noise at the given x- and z-coordinates.
     *
     * @param x: the x-coordinate to sample noise from
     * @param y: the y-coordinate to sample noise from
     * @param z: the z-coordinate to sample noise from
     * @return the sampled noise at the given x-, y- and z-coordinates
     */
    override fun noise(x: Double, y: Double, z: Double): Double = noise(x.f(), y.f(), z.f()).d()

    /**
     * Sample noise at the given x- and z-coordinates.
     *
     * @param x: the x-coordinate to sample noise from
     * @param y: the y-coordinate to sample noise from
     * @param z: the z-coordinate to sample noise from
     * @return the sampled noise at the given x-, y- and z-coordinates
     */
    override fun noise(x: Int, y: Int, z: Int): Float = noise(x.f(), y.f(), z.f())

}