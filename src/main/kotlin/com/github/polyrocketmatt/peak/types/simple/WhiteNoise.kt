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

    /**
     * Sample noise at the given x- and y-coordinates.
     *
     * @param nX: the x-coordinate to sample noise from
     * @param nY: the y-coordinate to sample noise from
     * @return the sampled noise at the given x- and y-coordinates
     */
    override fun noise(nX: Float, nY: Float): Float {
        val iX = nX.bitCast()
        val iY = nY.bitCast()
        return valCoord2d(seed, iX, iY)
    }

    /**
     * Sample noise at the given x-, y- and z-coordinates.
     *
     * @param nX: the x-coordinate to sample noise from
     * @param nY: the y-coordinate to sample noise from
     * @param nZ: the z-coordinate to sample noise from
     * @return the sampled noise at the given x- and z-coordinates
     */
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