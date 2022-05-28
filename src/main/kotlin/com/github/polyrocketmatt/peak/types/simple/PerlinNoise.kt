package com.github.polyrocketmatt.peak.types.simple

import com.github.polyrocketmatt.game.math.fastFloor
import com.github.polyrocketmatt.game.math.lerp
import com.github.polyrocketmatt.game.math.smoothStep
import com.github.polyrocketmatt.game.math.smootherStep
import com.github.polyrocketmatt.peak.types.NoiseUtils
import com.github.polyrocketmatt.peak.types.NoiseUtils.Companion.gradCoord2d
import com.github.polyrocketmatt.peak.types.NoiseUtils.Companion.gradCoord3d

/**
 * Perlin noise implementation.
 */
class PerlinNoise(
    private val seed: Int,
    private val interpolation: NoiseUtils.InterpolationMethod,
) : SimpleNoise() {

    override fun noise(nX: Float, nY: Float): Float {
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

        val xd0 = nX - x0
        val yd0 = nY - y0
        val xd1 = xd0 - 1
        val yd1 = yd0 - 1

        val xf0 = xs.lerp(gradCoord2d(seed, x0, y0, xd0, yd0), gradCoord2d(seed, x1, y0, xd1, yd0))
        val xf1 = xs.lerp(gradCoord2d(seed, x0, y1, xd0, yd1), gradCoord2d(seed, x1, y1, xd1, yd1))

        return ys.lerp(xf0, xf1)
    }

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

        val xd0 = nX - x0
        val yd0 = nY - y0
        val zd0 = nZ - z0
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

    override fun type(): SimpleNoiseType = SimpleNoiseType.PERLIN

    override fun calculateFractalBounding() {}

    override fun clone(): PerlinNoise = PerlinNoise(seed, interpolation)

}