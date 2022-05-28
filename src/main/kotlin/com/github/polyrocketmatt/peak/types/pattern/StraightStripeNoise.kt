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

    override fun noise(nX: Float, nY: Float): Float = min(1.0f, when (orientation) {
            PatternOrientation.HORIZONTAL       -> sin(nX / scale)
            PatternOrientation.VERTICAL         -> sin(nY / scale)
            PatternOrientation.DIAGONAL         -> sin((nX + nY) / scale)
            else                                -> sin(nX / scale)
    } + 1)

    override fun noise(nX: Float, nY: Float, nZ: Float): Float {
        TODO("Not yet implemented")
    }

    override fun type(): PatternNoiseType = PatternNoiseType.STRAIGHT_STRIPE

    override fun orientation(): PatternOrientation = orientation

    override fun clone(): StraightStripeNoise = StraightStripeNoise(scale, orientation)

}