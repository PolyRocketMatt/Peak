package com.github.polyrocketmatt.peak.types.pattern

import com.github.polyrocketmatt.game.math.i

/**
 * Checkerboard noise implementation.
 */
class CheckerboardNoise(
    val scale: Float
) : PatternNoise() {

    private val scale2: Int = scale.i() * 2

    override fun noise(nX: Float, nY: Float): Float {
        val iX = (nX.i() % scale2) / scale.i()
        val iY = (nY.i() % scale2) / scale.i()
        return if (iX == iY) 1.0f else 0.0f
    }

    override fun noise(nX: Float, nY: Float, nZ: Float): Float {
        val iX = (nX.i() % scale2) / scale.i()
        val iY = (nY.i() % scale2) / scale.i()
        val iZ = (nZ.i() % scale2) / scale.i()
        return if (iX == iY && iY == iZ) 1.0f else 0.0f
    }

    override fun type(): PatternNoiseType = PatternNoiseType.CHECKERBOARD

    override fun orientation(): PatternOrientation = PatternOrientation.NOT_AVAILABLE

    override fun clone(): CheckerboardNoise = CheckerboardNoise(scale)

}