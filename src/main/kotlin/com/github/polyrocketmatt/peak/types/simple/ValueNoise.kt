package com.github.polyrocketmatt.peak.types.simple

import com.github.polyrocketmatt.game.math.fastFloor
import com.github.polyrocketmatt.game.math.lerp
import com.github.polyrocketmatt.game.math.smoothStep
import com.github.polyrocketmatt.game.math.smootherStep
import com.github.polyrocketmatt.peak.types.NoiseUtils
import com.github.polyrocketmatt.peak.types.NoiseUtils.Companion.valCoord2d
import com.github.polyrocketmatt.peak.types.NoiseUtils.Companion.valCoord3d

/**
 * Value noise implementation.
 */
class ValueNoise(
    private val seed: Int,
    private val interpolation: NoiseUtils.InterpolationMethod,
) : SimpleNoise() {

    /**
     * Sample noise at the given x- and y-coordinates.
     *
     * @param nX: the x-coordinate to sample noise from
     * @param nY: the y-coordinate to sample noise from
     * @return the sampled noise at the given x- and y-coordinates
     */
    override fun noise(x: Float, y: Float): Float {
        val nX = x * 0.05f
        val nY = y * 0.05f

        val x0 = nX.fastFloor()
        val y0 = nY.fastFloor()
        val x1 = x0 + 1
        val y1 = y0 + 1
        var xs = 0.0f
        var ys = 0.0f
        when (interpolation) {
            NoiseUtils.InterpolationMethod.LINEAR    -> {
                xs = nX - x0
                ys = nY - y0
            }

            NoiseUtils.InterpolationMethod.HERMITE   -> {
                xs = (nX - x0).smoothStep()
                ys = (nY - y0).smoothStep()
            }

            NoiseUtils.InterpolationMethod.QUINTIC   -> {
                xs = (nX - x0).smootherStep()
                ys = (nY - y0).smootherStep()
            }
        }

        val xf0 = xs.lerp(valCoord2d(seed, x0, y0), valCoord2d(seed, x1, y0))
        val xf1 = xs.lerp(valCoord2d(seed, x0, y1), valCoord2d(seed, x1, y1))

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
        val x0 = nX.fastFloor()
        val y0 = nY.fastFloor()
        val z0 = nZ.fastFloor()
        val x1 = x0 + 1
        val y1 = y0 + 1
        val z1 = z0 + 1
        var xs = 0.0f
        var ys = 0.0f
        var zs = 0.0f
        when (interpolation) {
            NoiseUtils.InterpolationMethod.LINEAR    -> {
                xs = nX - x0
                ys = nY - y0
                zs = nZ - z0
            }

            NoiseUtils.InterpolationMethod.HERMITE   -> {
                xs = (nX - x0).smoothStep()
                ys = (nY - y0).smoothStep()
                zs = (nZ - z0).smoothStep()
            }

            NoiseUtils.InterpolationMethod.QUINTIC   -> {
                xs = (nX - x0).smootherStep()
                ys = (nY - y0).smootherStep()
                zs = (nZ - z0).smootherStep()
            }
        }

        val xf00 = xs.lerp(valCoord3d(seed, x0, y0, z0), valCoord3d(seed, x1, y0, z0))
        val xf10 = xs.lerp(valCoord3d(seed, x0, y1, z0), valCoord3d(seed, x1, y1, z0))
        val xf01 = xs.lerp(valCoord3d(seed, x0, y0, z1), valCoord3d(seed, x1, y0, z1))
        val xf11 = xs.lerp(valCoord3d(seed, x0, y1, z1), valCoord3d(seed, x1, y1, z1))

        val yf0 = ys.lerp(xf00, xf10)
        val yf1 = ys.lerp(xf01, xf11)

        return zs.lerp(yf0, yf1)
    }

    override fun type(): SimpleNoiseType = SimpleNoiseType.VALUE

    override fun calculateFractalBounding() {}

    override fun clone(): ValueNoise = ValueNoise(seed, interpolation)

}