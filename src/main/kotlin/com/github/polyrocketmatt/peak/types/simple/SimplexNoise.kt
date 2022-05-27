package com.github.polyrocketmatt.peak.types.simple

import com.github.polyrocketmatt.game.math.fastFloor
import com.github.polyrocketmatt.peak.types.NoiseUtils.Companion.F2
import com.github.polyrocketmatt.peak.types.NoiseUtils.Companion.F3
import com.github.polyrocketmatt.peak.types.NoiseUtils.Companion.G2
import com.github.polyrocketmatt.peak.types.NoiseUtils.Companion.G3
import com.github.polyrocketmatt.peak.types.NoiseUtils.Companion.G33
import com.github.polyrocketmatt.peak.types.NoiseUtils.Companion.gradCoord2d
import com.github.polyrocketmatt.peak.types.NoiseUtils.Companion.gradCoord3d

class SimplexNoise(
    private val seed: Int,
) : SimpleNoise() {

    override fun noise(nX: Float, nY: Float): Float {
        var t = (nX + nY) * F2
        val i = (nX + t).fastFloor()
        val j = (nY + t).fastFloor()

        t = (i + j) * G2
        val x0 = nX - (i - t)
        val y0 = nY - (j - t)

        val i1: Int
        val j1: Int
        if (x0 > y0) {
            i1 = 1
            j1 = 0
        } else {
            i1 = 0
            j1 = 1
        }

        val x1 = x0 - i1 + G2
        val y1 = y0 - j1 + G2
        val x2 = x0 - 1.0f + 2.0f * G2
        val y2 = y0 - 1.0f + 2.0f * G2

        val n0: Float
        val n1: Float
        val n2: Float

        t = 0.5f - x0 * x0 - y0 * y0
        if (t < 0.0f)
            n0 = 0.0f
        else {
            t *= t
            n0 = t * t * gradCoord2d(seed, i, j, x0, y0)
        }

        t = 0.5f - x1 * x1 - y1 * y1
        if (t < 0.0f)
            n1 = 0.0f
        else {
            t *= t
            n1 = t * t * gradCoord2d(seed, i + i1, j + j1, x1, y1)
        }

        t = 0.5f - x2 * x2 - y2 * y2
        if (t < 0.0f)
            n2 = 0.0f
        else {
            t *= t
            n2 = t * t * gradCoord2d(seed, i + 1, j + 1, x2, y2)
        }

        return 50.0f * (n0 + n1 + n2)
    }

    override fun noise(nX: Float, nY: Float, nZ: Float): Float {
        var t = (nX + nY + nZ) * F3
        val i = (nX + t).fastFloor()
        val j = (nY + t).fastFloor()
        val k = (nZ + t).fastFloor()

        t = (i + j + k) * G3
        val x0 = nX - (i - t)
        val y0 = nY - (j - t)
        val z0 = nZ - (k - t)

        val i1: Int
        val j1: Int
        val k1: Int
        val i2: Int
        val j2: Int
        val k2: Int
        if(x0 >= y0) {
            if(y0 >= z0) {
                i1 = 1
                j1 = 0
                k1 = 0
                i2 = 1
                j2 = 1
                k2 = 0
            } else if(x0 >= z0) {
                i1 = 1
                j1 = 0
                k1 = 0
                i2 = 1
                j2 = 0
                k2 = 1
            } else {
                i1 = 0
                j1 = 0
                k1 = 1
                i2 = 1
                j2 = 0
                k2 = 1
            }
        } else {
            if(y0 < z0) {
                i1 = 0
                j1 = 0
                k1 = 1
                i2 = 0
                j2 = 1
                k2 = 1
            } else if(x0 < z0) {
                i1 = 0
                j1 = 1
                k1 = 0
                i2 = 0
                j2 = 1
                k2 = 1
            } else {
                i1 = 0
                j1 = 1
                k1 = 0
                i2 = 1
                j2 = 1
                k2 = 0
            }
        }

        val x1 = x0 - i1 + G3
        val y1 = y0 - j1 + G3
        val z1 = z0 - k1 + G3
        val x2 = x0 - i2 + F3
        val y2 = y0 - j2 + F3
        val z2 = z0 - k2 + F3
        val x3 = x0 + G33
        val y3 = y0 + G33
        val z3 = z0 + G33

        val n0: Float
        val n1: Float
        val n2: Float
        val n3: Float
        t = 0.6f - x0 * x0 - y0 * y0 - z0 * z0

        if (t < 0)
            n0 = 0.0f
        else {
            t *= t
            n0 = t * t * gradCoord3d(seed, i, j, k, x0, y0, z0)
        }

        t = 0.6f - x1 * x1 - y1 * y1 - z1 * z1
        if (t < 0)
            n1 = 0.0f
        else {
            t *= t
            n1 = t * t * gradCoord3d(seed, i + i1, j + j1, k + k1, x1, y1, z1)
        }

        t = 0.6f - x2 * x2 - y2 * y2 - z2 * z2
        if (t < 0)
            n2 = 0.0f
        else {
            t *= t
            n2 = t * t * gradCoord3d(seed, i + i2, j + j2, k + k2, x2, y2, z2)
        }

        t = 0.6f - x3 * x3 - y3 * y3 - z3 * z3
        if (t < 0)
            n3 = 0.0f
        else {
            t *= t
            n3 = t * t * gradCoord3d(seed, i + 1, j + 1, k + 1, x3, y3, z3)
        }

        return 32.0f * (n0 + n1 + n2 + n3)
    }

    override fun type(): SimpleNoiseType = SimpleNoiseType.SIMPLEX

    override fun calculateFractalBounding() {}
}