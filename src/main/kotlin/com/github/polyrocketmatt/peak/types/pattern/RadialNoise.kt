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

    override fun noise(nX: Float, nY: Float): Float = max(0.0f, min(1.0f, sin(((nX - halfSize).intPow(2) + (nY - halfSize).intPow(2)).sqrt() / scale)))

    override fun noise(nX: Float, nY: Float, nZ: Float): Float {
        TODO("Not yet implemented")
    }

    override fun type(): PatternNoiseType = PatternNoiseType.RADIAL_STRIPE

    override fun orientation(): PatternOrientation = PatternOrientation.NOT_AVAILABLE

    override fun clone(): RadialNoise = RadialNoise(size, scale)

}