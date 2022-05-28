package com.github.polyrocketmatt.peak.types.pattern

import kotlin.math.min
import kotlin.math.sin

/**
 * Straight stripe noise implementation.
 */
class StraightStripeNoise(
    val scale: Float,
    val orientation: PatternOrientation,
) : PatternNoise() {

    /**
     * Sample noise at the given x- and y-coordinates.
     *
     * @param nX: the x-coordinate to sample noise from
     * @param nY: the y-coordinate to sample noise from
     * @return the sampled noise at the given x- and y-coordinates
     */
    override fun noise(nX: Float, nY: Float): Float = min(1.0f, when (orientation) {
            PatternOrientation.HORIZONTAL       -> sin(nX / scale)
            PatternOrientation.VERTICAL         -> sin(nY / scale)
            PatternOrientation.DIAGONAL         -> sin((nX + nY) / scale)
            else                                -> sin(nX / scale)
    } + 1)

    /**
     * Sample noise at the given x-, y- and z-coordinates.
     *
     * @param nX: the x-coordinate to sample noise from
     * @param nY: the y-coordinate to sample noise from
     * @param nZ: the z-coordinate to sample noise from
     * @return the sampled noise at the given x- and z-coordinates
     */
    override fun noise(nX: Float, nY: Float, nZ: Float): Float = min(1.0f, when (orientation) {
        PatternOrientation.HORIZONTAL       -> sin(nX / scale)
        PatternOrientation.VERTICAL         -> sin(nY / scale)
        PatternOrientation.DIAGONAL         -> sin((nX + nY) / scale)
        else                                -> sin(nX / scale)
    } + 1)

    override fun type(): PatternNoiseType = PatternNoiseType.STRAIGHT_STRIPE

    override fun orientation(): PatternOrientation = orientation

    override fun clone(): StraightStripeNoise = StraightStripeNoise(scale, orientation)

}