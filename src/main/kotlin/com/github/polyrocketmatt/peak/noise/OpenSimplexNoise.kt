/*
 * PEAK, Procedural Environment Algorithms for Kotlin
 * Copyright (C) Matthias Kovacic <matthias.kovacic@student.kuleuven.be>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.polyrocketmatt.peak.noise

import com.github.polyrocketmatt.game.Vec2f
import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.peak.Noise
import com.github.polyrocketmatt.peak.NoiseBuffer
import com.github.polyrocketmatt.peak.NoiseType

data class SimplexBuffer(
    var seed: Long = 0L
) : NoiseBuffer()

/**
 * OpenSimplexNoise implementation in Kotlin, based on
 * OpenSimplex Noise in Java. By Kurt Spencer, in the
 * public domain.
 *
 * This type of noise is suitable for large procedural
 * areas that need to be computed relatively fast.
 * If not in need of tiles, it's recommended to use
 * PolynomialNoise. If in need of derivatives, it's
 * recommended to use PerlinNoise.
 *
 * @author Kurt Spencer (original Java code)
 * @author Ulrik Guenther <hello@ulrik.is> (Kotlin port)
 * @author Matthias Kovacic <matthias.kovacic@gmail.com> (Kotlin port optimization for 2D)
 *
 *  @param buffer the buffer containing the noise parameters
 */
class OpenSimplexNoise(buffer: SimplexBuffer) : Noise {

    private val stretchConstant2D = -0.211324865405187
    private val squishConstant2D = 0.366025403784439
    private val normConstant2D = 47.0

    private var perm: IntArray

    init {
        var s = buffer.seed
        val source = IntArray(256)

        perm = IntArray(256)

        for (i in 0..255)
            source[i] = i

        s = s * 6364136223846793005L + 1442695040888963407L
        s = s * 6364136223846793005L + 1442695040888963407L
        s = s * 6364136223846793005L + 1442695040888963407L

        for (i in 255 downTo 0) {
            s = s * 6364136223846793005L + 1442695040888963407L

            var r = ((s + 31) % (i + 1)).toInt()
            if (r < 0)
                r += i + 1

            perm[i] = source[r]
            source[r] = source[i]
        }
    }

    override fun getType(): NoiseType = NoiseType.SIMPLEX

    override fun noise(pos: Vec2f): Float = noise(pos.x, pos.y)

    override fun noise(x: Float, y: Float, vararg floats: Float): Float {
        val stretchOffset = (x + y) * stretchConstant2D
        val xs = x + stretchOffset
        val ys = y + stretchOffset

        var xsb = fastFloor(xs)
        var ysb = fastFloor(ys)

        val squishOffset = (xsb + ysb) * squishConstant2D
        val xb = xsb + squishOffset
        val yb = ysb + squishOffset

        val xIns = xs - xsb
        val yIns = ys - ysb

        val inSum = xIns + yIns

        var dx0 = x - xb
        var dy0 = y - yb

        val dxExt: Double
        val dyExt: Double
        val xsvExt: Int
        val ysvExt: Int

        var value = 0.0

        val dx1 = dx0 - 1 - squishConstant2D
        val dy1 = dy0 - 0 - squishConstant2D
        var attn1 = 2.0 - dx1 * dx1 - dy1 * dy1
        if (attn1 > 0) {
            attn1 *= attn1
            value += attn1 * attn1 * extrapolate(xsb + 1, ysb + 0, dx1, dy1)
        }

        val dx2 = dx0 - 0 - squishConstant2D
        val dy2 = dy0 - 1 - squishConstant2D
        var attn2 = 2.0 - dx2 * dx2 - dy2 * dy2
        if (attn2 > 0) {
            attn2 *= attn2
            value += attn2 * attn2 * extrapolate(xsb + 0, ysb + 1, dx2, dy2)
        }

        if (inSum <= 1) {
            val zIns = 1 - inSum
            if (zIns > xIns || zIns > yIns) {
                if (xIns > yIns) {
                    xsvExt = xsb + 1
                    ysvExt = ysb - 1
                    dxExt = dx0 - 1
                    dyExt = dy0 + 1
                } else {
                    xsvExt = xsb - 1
                    ysvExt = ysb + 1
                    dxExt = dx0 + 1
                    dyExt = dy0 - 1
                }
            } else {
                xsvExt = xsb + 1
                ysvExt = ysb + 1
                dxExt = dx0 - 1 - 2 * squishConstant2D
                dyExt = dy0 - 1 - 2 * squishConstant2D
            }
        } else {
            val zIns = 2 - inSum
            if (zIns < xIns || zIns < yIns) {
                if (xIns > yIns) {
                    xsvExt = xsb + 2
                    ysvExt = ysb + 0
                    dxExt = dx0 - 2 - 2 * squishConstant2D
                    dyExt = dy0 + 0 - 2 * squishConstant2D
                } else {
                    xsvExt = xsb + 0
                    ysvExt = ysb + 2
                    dxExt = dx0 + 0 - 2 * squishConstant2D
                    dyExt = dy0 - 2 - 2 * squishConstant2D
                }
            } else {
                dxExt = dx0
                dyExt = dy0
                xsvExt = xsb
                ysvExt = ysb
            }
            xsb += 1
            ysb += 1
            dx0 = dx0 - 1 - 2 * squishConstant2D
            dy0 = dy0 - 1 - 2 * squishConstant2D
        }

        var attn0 = 2.0 - dx0 * dx0 - dy0 * dy0
        if (attn0 > 0) {
            attn0 *= attn0
            value += attn0 * attn0 * extrapolate(xsb, ysb, dx0, dy0)
        }

        var attnExt = 2.0 - dxExt * dxExt - dyExt * dyExt
        if (attnExt > 0) {
            attnExt *= attnExt
            value += attnExt * attnExt * extrapolate(xsvExt, ysvExt, dxExt, dyExt)
        }

        return (value / normConstant2D).f()
    }

    private fun extrapolate(xsb: Int, ysb: Int, dx: Double, dy: Double): Double {
        val index = perm[perm[xsb and 0xFF] + ysb and 0xFF] and 0x0E
        return gradients2D[index] * dx + gradients2D[index + 1] * dy
    }

    private fun fastFloor(x: Double): Int {
        val xi = x.toInt()
        return if (x < xi) xi - 1 else xi
    }

    companion object {
        private val gradients2D = byteArrayOf(
            5,  2,    2,  5,
            -5,  2,   -2,  5,
            5, -2,    2, -5,
            -5, -2,   -2, -5
        )
    }

}