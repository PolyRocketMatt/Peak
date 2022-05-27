package com.github.polyrocketmatt.peak.types.pattern

import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.game.math.i
import kotlin.math.floor

class GridNoise(
    val scale: Float
) : PatternNoise() {

    override fun noise(nX: Float, nY: Float): Float {
        val iX = (nX.i() + 1) % scale.i()
        val iY = (nY.i() + 1) % scale.i()
        return if (iX != 0 && iY != 0) 1.0f else 0.0f
    }

    override fun noise(nX: Float, nY: Float, nZ: Float): Float = ((floor(2.0f * nX).i() + floor(2.0f * nY).i() + floor(2.0f * nZ).i()) % 2).f()

    override fun type(): PatternType = PatternType.GRID

    override fun orientation(): PatternOrientation = PatternOrientation.NOT_AVAILABLE

}