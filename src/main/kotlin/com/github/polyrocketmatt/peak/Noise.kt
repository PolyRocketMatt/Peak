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

package com.github.polyrocketmatt.peak

import com.github.polyrocketmatt.game.Vec2f

/**
 * All types of noise available in PEAK.
 */
enum class NoiseType {

    PERLIN,
    POLYNOMIAL,
    SIMPLEX,

}

/**
 * Noise with it's default evaluation operations.
 */
interface Noise {

    /**
     * Get the type of noise.
     *
     * @return the type of noise
     */
    fun getType(): NoiseType

    /**
     * Compute the noise value at the given vector position.
     *
     * @param pos the vector position
     * @return the noise value computed
     */
    fun noise(pos: Vec2f): Float

    /**
     * Compute the noise value at the given x- and y-coordinates
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the noise value computed
     */
    fun noise(x: Float, y: Float, vararg floats: Float): Float

}