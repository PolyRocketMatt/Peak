package com.github.polyrocketmatt.peak.types.pattern

import com.github.polyrocketmatt.game.math.intPow
import com.github.polyrocketmatt.game.math.sqrt
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

/**
 * Radial noise implementation.
 */
class RadialNoise(
    val size: Int,
    val scale: Float
) : PatternNoise() {

    private val halfSize = size / 2.0f

    /**
     * Sample noise at the given x- and y-coordinates.
     *
     * @param nX: the x-coordinate to sample noise from
     * @param nY: the y-coordinate to sample noise from
     * @return the sampled noise at the given x- and y-coordinates
     */
    override fun noise(nX: Float, nY: Float): Float = max(0.0f, min(1.0f, sin(((nX - halfSize).intPow(2) + (nY - halfSize).intPow(2)).sqrt() / scale)))

    /**
     * Sample noise at the given x-, y- and z-coordinates.
     *
     * @param nX: the x-coordinate to sample noise from
     * @param nY: the y-coordinate to sample noise from
     * @param nZ: the z-coordinate to sample noise from
     * @return the sampled noise at the given x- and z-coordinates
     */
    override fun noise(nX: Float, nY: Float, nZ: Float): Float = max(0.0f, min(1.0f, sin(((nX - halfSize).intPow(2) + (nY - halfSize).intPow(2) + (nZ - halfSize).intPow(2)).sqrt() / scale)))

    override fun type(): PatternNoiseType = PatternNoiseType.RADIAL_STRIPE

    override fun orientation(): PatternOrientation = PatternOrientation.NOT_AVAILABLE

    override fun clone(): RadialNoise = RadialNoise(size, scale)

}