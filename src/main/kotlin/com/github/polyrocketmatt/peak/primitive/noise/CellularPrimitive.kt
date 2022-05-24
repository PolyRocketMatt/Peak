package com.github.polyrocketmatt.peak.primitive.noise

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.buffer.operator.Operators
import com.github.polyrocketmatt.peak.provider.FastNoiseProvider
import com.github.polyrocketmatt.peak.provider.builder.FastProviderDataBuilder
import com.github.polyrocketmatt.peak.types.FastNoise

/**
 * Defines a primitive from Cellular noise. A buffer is constructed
 * from the given width and height values.
 *
 * @param width: the width of the buffer
 * @param height: the height of the buffer
 */
class CellularPrimitive(width: Int, height: Int) : NoisePrimitive(NoiseBuffer(width, height), true) {

    /**
     * Constructor for a primitive with equal width and height buffer.
     *
     * @param size: the width and height of the buffer
     */
    constructor(size: Int) : this(size, size)

    /**
     * The seed of the primitive.
     */
    var seed: Int = 1
        set(value) {
            this.update = true
            field = value
        }

    /**
     * The distance type of the primitive.
     */
    var distanceType: FastNoise.CellularDistanceFunction = FastNoise.CellularDistanceFunction.EUCLIDEAN
        set(value) {
            this.update = true
            field = value
        }

    /**
     * The return type of the primitive
     */
    var returnType: FastNoise.CellularReturnType = FastNoise.CellularReturnType.DISTANCE
        set(value) {
            this.update = true
            field = value
        }

    /**
     * The lookup of the primitive.
     */
    var lookup: FastNoise? = null
        set(value) {
            this.update = true
            field = value
        }

    private var noise: FastNoiseProvider = FastNoiseProvider(FastProviderDataBuilder().build())

    init { recompute() }

    /**
     * Recompute Multi-layer Perlin noise in the buffer.
     */
    override fun recompute() {
        this.update = false
        this.noise = FastNoiseProvider(
            FastProviderDataBuilder()
                .buildSeed(this.seed)
                .buildType(FastNoise.NoiseType.CELLULAR)
                .buildDistanceFunction(this.distanceType)
                .buildReturnType(this.returnType)
                .buildLookup(this.lookup)
                .build()
        )
        val width = this.buffer().width()
        val height = this.buffer().height()

        for (x in 0 until width) for (z in 0 until height)
            this.buffer()[x][z] = this.noise.noise(x, z)

        Operators.NORMALIZE.operate(this.buffer())
    }

}