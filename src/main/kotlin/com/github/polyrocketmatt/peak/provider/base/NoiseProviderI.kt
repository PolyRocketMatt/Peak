package com.github.polyrocketmatt.peak.provider.base

@FunctionalInterface
interface NoiseProviderI {

    /**
     * Sample noise at the given x- and z-coordinates.
     *
     * @param x: the x-coordinate to sample noise from
     * @param z: the z-coordinate to sample noise from
     * @return the sampled noise at the given x- and z-coordinates
     */
    fun noise(x: Int, z: Int): Float

}