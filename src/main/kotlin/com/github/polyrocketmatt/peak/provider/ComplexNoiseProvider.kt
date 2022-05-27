package com.github.polyrocketmatt.peak.provider

/*
/**
 * Represents a complex noise provider.
 */
class ComplexNoiseProvider(data: ComplexProviderData) : SimpleNoiseProvider() {

    private val noise: PolynomialNoise

    init {
        noise = PolynomialNoise(
            data.seed,
            data.width,
            data.height,
            data.interpolation,
            data.type,
            data.octaves,
            data.gain,
            data.scale,
            data.force,
            data.lookup,
            data.provider,
            data.particles,
            data.iterations
        )
    }

    /**
     * Sample noise at the given x- and z-coordinates.
     *
     * @param x: the x-coordinate to sample noise from
     * @param z: the z-coordinate to sample noise from
     * @return the sampled noise at the given x- and z-coordinates
     */
    override fun noise(x: Float, z: Float): Float = noise.noise(x, z)

    /**
     * Sample noise at the given x- and z-coordinates.
     *
     * @param x: the x-coordinate to sample noise from
     * @param z: the z-coordinate to sample noise from
     * @return the sampled noise at the given x- and z-coordinates
     */
    override fun noise(x: Double, z: Double): Double = noise(x.f(), z.f()).d()

    /**
     * Sample noise at the given x- and z-coordinates.
     *
     * @param x: the x-coordinate to sample noise from
     * @param z: the z-coordinate to sample noise from
     * @return the sampled noise at the given x- and z-coordinates
     */
    override fun noise(x: Int, z: Int): Float = noise(x.f(), z.f())

    /**
     * Sample noise at the given x-, y- and z-coordinates.
     *
     * @param x: the x-coordinate to sample noise from
     * @param y: the y-coordinate to sample noise from
     * @param z: the z-coordinate to sample noise from
     * @return the sampled noise at the given x-, y- and z-coordinates
     */
    override fun noise(x: Float, y: Float, z: Float): Float = noise.noise(x, y, z)

    /**
     * Sample noise at the given x- and z-coordinates.
     *
     * @param x: the x-coordinate to sample noise from
     * @param y: the y-coordinate to sample noise from
     * @param z: the z-coordinate to sample noise from
     * @return the sampled noise at the given x-, y- and z-coordinates
     */
    override fun noise(x: Double, y: Double, z: Double): Double = noise(x.f(), y.f(), z.f()).d()

    /**
     * Sample noise at the given x- and z-coordinates.
     *
     * @param x: the x-coordinate to sample noise from
     * @param y: the y-coordinate to sample noise from
     * @param z: the z-coordinate to sample noise from
     * @return the sampled noise at the given x-, y- and z-coordinates
     */
    override fun noise(x: Int, y: Int, z: Int): Float = noise(x.f(), y.f(), z.f())

    fun buffer(): NoiseBuffer = this.noise.buffer()
}

 */