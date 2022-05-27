package com.github.polyrocketmatt.peak.provider.base

@FunctionalInterface
interface NoiseProviderD {

    /**
     * Sample noise at the given x- and z-coordinates.
     *
     * @param x: the x-coordinate to sample noise from
     * @param z: the z-coordinate to sample noise from
     * @return the sampled noise at the given x- and z-coordinates
     */
    fun noise(x: Double, z: Double): Double

    /**
     * Sample noise at the given x-, y- and z- coordinates.
     *
     * @param x: the x-coordinate to sample noise from
     * @param y: the y-coordinate to sample noise from
     * @param z: the z-coordinate to sample noise from
     *
     * @return the sampled noise at the given x-, y- and z-coordinates
     */
    fun noise(x: Double, y: Double, z: Double): Double

}