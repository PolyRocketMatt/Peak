package com.github.polyrocketmatt.peak.buffer

import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.game.math.statistics.max
import com.github.polyrocketmatt.game.math.statistics.min
import com.github.polyrocketmatt.peak.exception.NoiseException
import com.github.polyrocketmatt.peak.image.ImageUtils
import com.github.polyrocketmatt.peak.provider.base.SimpleNoiseProvider
import com.github.polyrocketmatt.peak.types.NoiseEvaluator
import java.awt.image.BufferedImage
import kotlin.random.Random

/**
 * Constructs a wrapper for a 2D array on which special noise-related
 * operations can be performed.
 *
 * @param buffer: the 2D array of floats, which represent the buffer
 */
class SyncNoiseBuffer2(private val buffer: Array<FloatArray>) : NoiseBuffer2, SyncNoiseBuffer {

    enum class Rotation2 { DEG_90, DEG_180 }

    /**
     * Constructor for an empty buffer of the given width and height.
     * The internal buffer will be initialized to 0.
     *
     * @param width: the width of the buffer
     * @param height: the height of the buffer
     */
    constructor(width: Int, height: Int) : this(Array(width) { FloatArray(height) { 0.0f } })

    /**
     * Constructor for an empty buffer of the given width and height.
     * The internal buffer will be initialized to a random value
     * using the provided Random.
     *
     * @param width: the width of the buffer
     * @param height: the height of the buffer
     * @param rng: the Random used to populate the buffer
     */
    constructor(width: Int, height: Int, rng: Random) : this(Array(width) { FloatArray(height) { rng.nextFloat() } })

    /**
     * Constructor for an empty buffer of the given width and height.
     * The internal buffer will be initialized to a uniform value
     * using the provided value.
     *
     * @param width: the width of the buffer
     * @param height: the height of the buffer
     * @param value: the value used to populate the buffer
     */
    constructor(width: Int, height: Int, value: Float) : this(Array(width) { FloatArray(height) { value } })

    /**
     * Constructor for an empty buffer of the given size. The internal buffer will be initialized
     * to 0.
     * @param size: the size for the width and height of the buffer
     */
    constructor(size: Int) : this(Array(size) { FloatArray(size) { 0.0f } })

    /**
     * Constructor for an empty buffer of the given size. The internal buffer will be initialized
     * to random value using the provided Random.
     * @param size: the size for the width and height of the buffer
     * @param rng: the Random used to populate the buffer
     */
    constructor(size: Int, rng: Random) : this(Array(size) { FloatArray(size) { rng.nextFloat() } })

    /**
     * Constructor for an empty buffer of the given size. The internal buffer will be initialized
     * to a uniform value using the provided value.
     * @param size: the size for the width and height of the buffer
     * @param value: the value used to populate the buffer
     */
    constructor(size: Int, value: Float) : this(Array(size) { FloatArray(size) { value } })

    /**
     * Get an element from this buffer.
     *
     * @param index: the index at which to get an element from the buffer.
     * @return the float array at the given index.
     */
    override operator fun get(index: Int) = buffer[index]

    /**
     * Get the width of the buffer.
     *
     * @return the width of the buffer
     */
    override fun width(): Int = buffer.size

    /**
     * Get the height of the buffer.
     *
     * @return the height of the buffer
     */
    override fun height(): Int = buffer[0].size

    /**
     * Get the minimum value within the buffer.
     *
     * @return the minimum value within the buffer
     */
    override fun min(): Float = buffer.minOf { floats -> floats.min() }

    /**
     * Get the maximum value within the buffer.
     *
     * @return the maximum value within the buffer
     */
    override fun max(): Float =  buffer.maxOf { floats -> floats.max() }

    /**
     * Construct a grayscale image from this buffer.
     *
     * @return the grayscale image constructed from this buffer
     */
    override fun image(): BufferedImage = ImageUtils.bufferToImage(this)

    /**
     * Perform an action on each element in the buffer.
     *
     * @param action: the action to perform on each element in the buffer
     */
    override fun forEach(action: (Float) -> Unit) { buffer.forEach { floats -> floats.forEach { value -> action(value) } } }

    /**
     * Transform each element in the buffer to another element.
     *
     * @param transform: the transform to perform on each element in the buffer
     * @return this noise buffer
     */
    override fun map(transform: (Float) -> Float): SyncNoiseBuffer2 {
        val copy = copy()
        copy.buffer.forEachIndexed { x, floats -> floats.forEachIndexed { z, value -> copy[x][z] = transform(value) } }
        return copy
    }

    /**
     * Transform each element in the buffer to another element.
     *
     * @param transform: the transform to perform on each element in the buffer
     * @return a new noise buffer with the mapped data
     */
    override fun mapIndexed(transform: (x: Int, y: Int, Float) -> Float): SyncNoiseBuffer2 {
        val newBuffer = Array(width()) { FloatArray(height()) { 0.0f } }
        for ((x, item) in buffer.withIndex())
            for ((y, float) in item.withIndex())
                newBuffer[x][y] = transform(x, y, float)
        return SyncNoiseBuffer2(newBuffer)
    }

    /**
     * Get this buffer.
     *
     * @return this buffer
     */
    override fun content(): SyncNoiseBuffer2 = this

    /**
     * Get a copy of the internal 2D array representation of the buffer.
     *
     * @return a copy of the 2D array representation of the buffer
     */
    override fun copy(): SyncNoiseBuffer2 = SyncNoiseBuffer2(buffer.map { it.clone() }.toTypedArray())

    /**
     * Fill the buffer given a noise provider.
     *
     * @param provider: the provider to use when filling the buffer
     * @return this noise buffer
     */
    override fun fill(provider: SimpleNoiseProvider): SyncNoiseBuffer2 {
        for (x in 0 until width()) for (z in 0 until height())
            buffer[x][z] = provider.noise(x, z)
        return this
    }

    /**
     * Fill the buffer given a noise evaluator.
     *
     * @param evaluator: the evaluator to use when filling the buffer
     * @return this noise buffer
     */
    override fun fill(evaluator: NoiseEvaluator): SyncNoiseBuffer2 {
        for (x in 0 until width()) for (z in 0 until height())
            buffer[x][z] = evaluator.noise(x.f(), z.f())
        return this
    }

    /**
     * Rotate the buffer given the rotation.
     *
     * @param rotation: the rotation on how to rotate the buffer
     * @return the rotated buffer according to the given rotation
     */
    fun rotate(rotation: Rotation2): SyncNoiseBuffer2 {
        val rotated = when (rotation) {
            Rotation2.DEG_90 -> {
                //  Columns -> Rows
                val rotated = Array(height()) { FloatArray(width()) { 0.0f } }
                for ((index, row) in buffer.withIndex())
                    for ((idx, element) in row.withIndex())
                        rotated[index][idx] = element

                rotated
            }

            Rotation2.DEG_180 -> {
                val rotated = Array(width()) { FloatArray(height()) { 0.0f } }
                for ((index, row) in buffer.withIndex())
                    rotated[index] = row.reversedArray()

                rotated
            }
        }

        return SyncNoiseBuffer2(rotated)
    }

    /**
     * Transform the buffer to an asynchronous buffer.
     *
     * @param chunkSize: the chunk size to split the buffer in
     * @return this buffer in an asynchronous format
     * @throws NoiseException if the width or height is not divisible by the provided chunk size
     */
    override fun toAsync(chunkSize: Int): AsyncNoiseBuffer {
        if (width() % chunkSize != 0 || height() % chunkSize != 0)
            throw NoiseException("Cannot transform buffer with irregular width and/or height")
        return AsyncNoiseBuffer2(this.buffer, chunkSize)
    }

}