package com.github.polyrocketmatt.peak.types.bounded

import com.github.polyrocketmatt.game.math.*
import com.github.polyrocketmatt.peak.types.NoiseUtils
import com.github.polyrocketmatt.peak.types.NoiseUtils.Companion.gradCoord2d
import com.github.polyrocketmatt.peak.types.NoiseUtils.Companion.gradCoord3d

/**
 * Perlin noise implementation.
 */
class PerlinNoise(
    private val seed: Int,
    width: Int,
    height: Int,
    depth: Int,
    private val interpolation: NoiseUtils.InterpolationMethod,
) : BoundedNoise(width, height, depth) {

    /**
     * Sample noise at the given x- and y-coordinates.
     *
     * @param nX: the x-coordinate to sample noise from
     * @param nY: the y-coordinate to sample noise from
     * @return the sampled noise at the given x- and y-coordinates
     */
    override fun noise(nX: Float, nY: Float): Float {
        val x = (nX % width) / width
        val y = (nY % height) / height

        val x0 = x.fastFloor()
        val y0 = y.fastFloor()
        val x1 = x0 + 1
        val y1 = y0 + 1
        var xs = 0.0f
        var ys = 0.0f
        when (interpolation) {
            NoiseUtils.InterpolationMethod.LINEAR    -> {
                xs = x - x0
                ys = y - y0
            }

            NoiseUtils.InterpolationMethod.HERMITE   -> {
                xs = (x - x0).smoothStep()
                ys = (y - y0).smoothStep()
            }

            NoiseUtils.InterpolationMethod.QUINTIC   -> {
                xs = (x - x0).smootherStep()
                ys = (y - y0).smootherStep()
            }
        }

        val xd0 = x - x0
        val yd0 = y - y0
        val xd1 = xd0 - 1
        val yd1 = yd0 - 1

        val xf0 = xs.lerp(gradCoord2d(seed, x0, y0, xd0, yd0), gradCoord2d(seed, x1, y0, xd1, yd0))
        val xf1 = xs.lerp(gradCoord2d(seed, x0, y1, xd0, yd1), gradCoord2d(seed, x1, y1, xd1, yd1))

        return ys.lerp(xf0, xf1)
    }

    /**
     * Sample noise at the given x-, y- and z-coordinates.
     *
     * @param nX: the x-coordinate to sample noise from
     * @param nY: the y-coordinate to sample noise from
     * @param nZ: the z-coordinate to sample noise from
     * @return the sampled noise at the given x- and z-coordinates
     */
    override fun noise(nX: Float, nY: Float, nZ: Float): Float {
        val x = (nX % width) / width
        val y = (nY % height) / height
        val z = (nZ % depth) / depth

        val x0 = x.fastFloor()
        val y0 = y.fastFloor()
        val z0 = z.fastFloor()
        val x1 = x0 + 1
        val y1 = y0 + 1
        val z1 = z0 + 1
        var xs = 0.0f
        var ys = 0.0f
        var zs = 0.0f
        when (interpolation) {
            NoiseUtils.InterpolationMethod.LINEAR    -> {
                xs = x - x0
                ys = y - y0
                zs = z - z0
            }

            NoiseUtils.InterpolationMethod.HERMITE   -> {
                xs = (x - x0).smoothStep()
                ys = (y - y0).smoothStep()
                zs = (z - z0).smoothStep()
            }

            NoiseUtils.InterpolationMethod.QUINTIC   -> {
                xs = (x - x0).smootherStep()
                ys = (y - y0).smootherStep()
                zs = (z - z0).smootherStep()
            }
        }

        val xd0 = x - x0
        val yd0 = y - y0
        val zd0 = z - z0
        val xd1 = xd0 - 1
        val yd1 = yd0 - 1
        val zd1 = zd0 - 1

        val xf00 = xs.lerp(
            gradCoord3d(seed, x0, y0, z0, xd0, yd0, zd0),
            gradCoord3d(seed, x1, y0, z0, xd1, yd0, zd0)
        )
        val xf10 = xs.lerp(
            gradCoord3d(seed, x0, y1, z0, xd0, yd1, zd0),
            gradCoord3d(seed, x1, y1, z0, xd1, yd1, zd0)
        )
        val xf01 = xs.lerp(
            gradCoord3d(seed, x0, y0, z1, xd0, yd0, zd1),
            gradCoord3d(seed, x1, y0, z1, xd1, yd0, zd1)
        )
        val xf11 = xs.lerp(
            gradCoord3d(seed, x0, y1, z1, xd0, yd1, zd1),
            gradCoord3d(seed, x1, y1, z1, xd1, yd1, zd1)
        )

        val yf0 = ys.lerp(xf00, xf10)
        val yf1 = ys.lerp(xf01, xf11)

        return zs.lerp(yf0, yf1)
    }

    override fun type(): BoundedNoiseType = BoundedNoiseType.PERLIN

    override fun clone(): PerlinNoise = PerlinNoise(seed, width, height, depth, interpolation)

}