package com.github.polyrocketmatt.peak.provider

import com.github.polyrocketmatt.game.math.d
import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.peak.provider.data.SimpleNoiseData
import com.github.polyrocketmatt.peak.provider.base.SimpleNoiseProvider
import com.github.polyrocketmatt.peak.types.simple.*

/**
 * Represents a simple noise provider.
 */
class SimpleNoiseProvider(data: SimpleNoiseData) : SimpleNoiseProvider() {

    private val noise: SimpleNoise

    init {
        this.noise = when (data.type) {
            SimpleNoise.SimpleNoiseType.PERLIN          -> PerlinNoise(data.seed, data.interpolation)
            SimpleNoise.SimpleNoiseType.VALUE           -> ValueNoise(data.seed, data.interpolation)
            SimpleNoise.SimpleNoiseType.SIMPLEX         -> SimplexNoise(data.seed)
            SimpleNoise.SimpleNoiseType.WHITE           -> WhiteNoise(data.seed)
            SimpleNoise.SimpleNoiseType.PERLIN_FRACTAL  ->
                FractalNoise(data.seed, data.interpolation, data.octaves, data.scale, data.frequency, data.gain, data.lacunarity, data.fractal, SimpleNoise.SimpleNoiseType.PERLIN)
            SimpleNoise.SimpleNoiseType.VALUE_FRACTAL  ->
                FractalNoise(data.seed, data.interpolation, data.octaves, data.scale, data.frequency, data.gain, data.lacunarity, data.fractal, SimpleNoise.SimpleNoiseType.VALUE)
            SimpleNoise.SimpleNoiseType.SIMPLEX_FRACTAL  ->
                FractalNoise(data.seed, data.interpolation, data.octaves, data.scale, data.frequency, data.gain, data.lacunarity, data.fractal, SimpleNoise.SimpleNoiseType.SIMPLEX)
        }
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