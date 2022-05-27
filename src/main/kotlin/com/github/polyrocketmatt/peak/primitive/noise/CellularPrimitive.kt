package com.github.polyrocketmatt.peak.primitive.noise

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.operator.Operators
import com.github.polyrocketmatt.peak.provider.CellularNoiseProvider
import com.github.polyrocketmatt.peak.provider.builder.CellularNoiseDataBuilder
import com.github.polyrocketmatt.peak.types.cellular.CellularNoise

/**
 * Defines a primitive for cellular noise. A buffer is constructed
 * from the given width and height values.
 *
 * @param width: the width of the buffer
 * @param height: the height of the buffer
 */
class CellularPrimitive(width: Int, height: Int) : NoisePrimitive(NoiseBuffer2(width, height), true) {

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
     * The frequency of the primitive.
     */
    var frequency: Float = 1.0f
        set(value) {
            this.update = true
            field = value
        }

    /**
     * The distance type of the primitive.
     */
    var distanceType: CellularNoise.DistanceType = CellularNoise.DistanceType.EUCLIDEAN
        set(value) {
            this.update = true
            field = value
        }

    /**
     * The return type of the primitive
     */
    var returnType: CellularNoise.ReturnType = CellularNoise.ReturnType.DISTANCE
        set(value) {
            this.update = true
            field = value
        }

    private var noise: CellularNoiseProvider = CellularNoiseProvider(CellularNoiseDataBuilder().build())

    init { recompute() }

    /**
     * Recompute cellular noise in the buffer.
     */
    override fun recompute() {
        this.update = false
        this.noise = CellularNoiseProvider(
            CellularNoiseDataBuilder()
                .buildSeed(this.seed)
                .buildFrequency(this.frequency)
                .buildDistanceType(this.distanceType)
                .buildReturnType(this.returnType)
                .build()
        )

        this.update(Operators.NORMALIZE.operate(this.buffer().fill(this.noise)))
    }

}