package com.github.polyrocketmatt.peak.types

abstract class NoiseEvaluator : Cloneable {

    /**
     * Sample noise at the given x- and z-coordinates.
     *
     * @param nX: the x-coordinate to sample noise from
     * @param nY: the z-coordinate to sample noise from
     * @return the sampled noise at the given x- and z-coordinates
     */
    abstract fun noise(nX: Float, nY: Float): Float

    /**
     * Sample noise at the given x-, y- and z-coordinates.
     *
     * @param nX: the x-coordinate to sample noise from
     * @param nY: the y-coordinate to sample noise from
     * @param nZ: the z-coordinate to sample noise from
     * @return the sampled noise at the given x- and z-coordinates
     */
    abstract fun noise(nX: Float, nY: Float, nZ: Float): Float

    public abstract override fun clone(): NoiseEvaluator

}