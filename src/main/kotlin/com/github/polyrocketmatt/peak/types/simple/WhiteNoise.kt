package com.github.polyrocketmatt.peak.types.simple

import com.github.polyrocketmatt.peak.types.NoiseUtils.Companion.valCoord2d
import com.github.polyrocketmatt.peak.types.NoiseUtils.Companion.valCoord3d

private fun Float.bitCast(): Int {
    val i = this.toRawBits()
    return i xor (i shr 16)
}

/**
 * White noise implementation.
 */
class WhiteNoise(
    private val seed: Int
) : SimpleNoise() {

    override fun noise(nX: Float, nY: Float): Float {
        val iX = nX.bitCast()
        val iY = nY.bitCast()
        return valCoord2d(seed, iX, iY)
    }

    override fun noise(nX: Float, nY: Float, nZ: Float): Float {
        val iX = nX.bitCast()
        val iY = nY.bitCast()
        val iZ = nZ.bitCast()
        return valCoord3d(seed, iX, iY, iZ)
    }

    override fun type(): SimpleNoiseType = SimpleNoiseType.WHITE

    override fun calculateFractalBounding() {}

    override fun clone(): WhiteNoise = WhiteNoise(seed)

}