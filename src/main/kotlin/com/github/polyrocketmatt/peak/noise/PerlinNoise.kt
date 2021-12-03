/*
 * GAMELib, Geometric And Mathematical Extension Library
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
import com.github.polyrocketmatt.game.Vec3f
import com.github.polyrocketmatt.game.math.smootherStep
import com.github.polyrocketmatt.peak.Noise
import com.github.polyrocketmatt.peak.NoiseBuffer
import com.github.polyrocketmatt.peak.NoiseType
import kotlin.math.floor
import kotlin.random.Random

data class PerlinBuffer(
    var seed: Long = 0L,
    var tables: Int = 1
) : NoiseBuffer()

/**
 * Classic implementation of Improved Perlin Noise
 * by Ken Perlin.
 *
 * This type of noise is suitable for small patches
 * of noise and when in need of derivatives or
 * tileable noise. If not in need of tiles,
 * it's recommended to use PolynomialNoise.
 * If many evaluations needed, it's recommended
 * to use OpenSimplexNoise.
 *
 * @param buffer the buffer containing the noise parameters
 */
class PerlinNoise(buffer: PerlinBuffer) : Noise {

    private val preShuffledPermutation = (0 until 512).map { i -> i % 256 }.toTypedArray()
    private val permutations = Array(buffer.tables) { preShuffledPermutation }
    private val primaryPermutation = permutations[0]

    init {
        for (i in 0 until buffer.tables)
            permutations[i].shuffle(Random(buffer.seed + i))
    }

    override fun getType(): NoiseType = NoiseType.PERLIN

    override fun noise(pos: Vec2f): Float = noise(pos.x, pos.y)

    override fun noise(x: Float, y: Float, vararg floats: Float): Float {
        //  This is similar to the regular noise, but we also add the derivatives
        //  Find unit square that contains point
        val iX = floor(x).toInt() and 255
        val iY = floor(y).toInt() and 255

        //  Find relative x, y of point in cube
        val fX = x - floor(x)
        val fY = y - floor(y)

        //  Compute 'fade' values
        val u = fX.smootherStep()
        val v = fY.smootherStep()

        //  Get hash values
        val h00 = primaryPermutation[primaryPermutation[iX] + iY]
        val h10 = primaryPermutation[primaryPermutation[iX + 1] + iY]
        val h01 = primaryPermutation[primaryPermutation[iX] + iY + 1]
        val h11 = primaryPermutation[primaryPermutation[iX + 1] + iY + 1]

        //  Compute gradients
        val g00 = grad(h00)
        val g10 = grad(h10)
        val g01 = grad(h01)
        val g11 = grad(h11)

        //  Compute dot products
        val a = fX * g00.x + fY * g00.y
        val b = (fX - 1) * g10.x + fY * g10.y
        val c = fX * g01.x + (fY - 1) * g01.y
        val d = (fX - 1) * g11.x + (fY - 1) * g11.y

        //  Don't use lerp here, function calls are expensive
        val k0 = 1 - u - v + u * v
        val k1 = u - u * v
        val k2 = v - u * v
        val k3 = u * v

        return a * k0 + b * k1 + c * k2 + d * k3
    }

    fun noiseDeriv(x: Float, y: Float, seed: Int): Vec3f {
        val permutation = permutations[seed]

        //  This is similar to the regular noise, but we also add the derivatives
        //  Find unit square that contains point
        val iX = floor(x).toInt() and 255
        val iY = floor(y).toInt() and 255

        //  Find relative x, y of point in cube
        val fX = x - floor(x)
        val fY = y - floor(y)

        //  Compute 'fade' values
        val u = fX.smootherStep()
        val v = fY.smootherStep()

        //  Get hash values
        val h00 = permutation[permutation[iX] + iY]
        val h10 = permutation[permutation[iX + 1] + iY]
        val h01 = permutation[permutation[iX] + iY + 1]
        val h11 = permutation[permutation[iX + 1] + iY + 1]

        //  Compute gradients
        val g00 = grad(h00)
        val g10 = grad(h10)
        val g01 = grad(h01)
        val g11 = grad(h11)

        // Get the derivative smoother-step
        val du = 30.0f * fX * fX * (fX - 1) * (fX - 1)
        val dv = 30.0f * fY * fY * (fY - 1) * (fY - 1)

        //  Compute dot products
        val a = fX * g00.x + fY * g00.y
        val b = (fX - 1) * g10.x + fY * g10.y
        val c = fX * g01.x + (fY - 1) * g01.y
        val d = (fX - 1) * g11.x + (fY - 1) * g11.y

        //  Don't use lerp here, function calls are expensive
        val k0 = 1 - u - v + u * v
        val k1 = u - u * v
        val k2 = v - u * v
        val k3 = u * v

        val n = a * k0 + b * k1 + c * k2 + d * k3
        val dx = g00.x * k0 + g10.x * k1 + g01.x * k2 + g11.x * k3 + du * (b - a + (a - b - c + d) * v)
        val dy = g00.y * k0 + g10.y * k1 + g01.y * k2 + g11.y * k3 + dv * (c - a + (a - b - c + d) * u)

        return Vec3f(n, dx, dy)
    }

    private fun grad(hash: Int): Vec2f = when (hash and 3) {
        0 -> Vec2f(1.0f, 1.0f)
        1 -> Vec2f(1.0f, -1.0f)
        2 -> Vec2f(-1.0f, 1.0f)
        else -> Vec2f(-1.0f, -1.0f)
    }

}

