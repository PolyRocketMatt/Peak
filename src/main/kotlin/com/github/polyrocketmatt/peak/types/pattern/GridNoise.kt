package com.github.polyrocketmatt.peak.types.pattern

import com.github.polyrocketmatt.game.math.i

/**
 * Grid noise implementation.
 */
class GridNoise(
    val scale: Float
) : PatternNoise() {

    /**
     * Sample noise at the given x- and y-coordinates.
     *
     * @param nX: the x-coordinate to sample noise from
     * @param nY: the y-coordinate to sample noise from
     * @return the sampled noise at the given x- and y-coordinates
     */
    override fun noise(nX: Float, nY: Float): Float {
        val iX = (nX.i() + 1) % scale.i()
        val iY = (nY.i() + 1) % scale.i()
        return if (iX != 0 && iY != 0) 1.0f else 0.0f
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
        val iX = (nX.i() + 1) % scale.i()
        val iY = (nY.i() + 1) % scale.i()
        val iZ = (nZ.i() + 1) % scale.i()
        return if (iX != 0 && iY != 0 && iZ != 0) 1.0f else 0.0f
    }

    override fun type(): PatternNoiseType = PatternNoiseType.GRID

    override fun orientation(): PatternOrientation = PatternOrientation.NOT_AVAILABLE

    override fun clone(): GridNoise = GridNoise(scale)

}