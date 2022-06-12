package com.github.polyrocketmatt.peak.buffer

import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.game.math.statistics.max
import com.github.polyrocketmatt.game.math.statistics.min
import com.github.polyrocketmatt.peak.exception.NoiseException
import com.github.polyrocketmatt.peak.provider.base.SimpleNoiseProvider
import com.github.polyrocketmatt.peak.types.NoiseEvaluator
import kotlin.random.Random

/**
 * Constructs a wrapper for a 3D array on which special noise-related
 * operations can be performed.
 *
 * @param buffer: the 3D array of floats, which represent the buffer
 */
@Deprecated("Use AsyncNoiseBuffer3 instead")
class SyncNoiseBuffer3(private val buffer: Array<Array<FloatArray>>) : NoiseBuffer3, SyncNoiseBuffer {

    /**
     * Constructor for an empty buffer of the given width and height.
     * The internal buffer will be initialized to 0.
     *
     * @param width: the width of the buffer
     * @param height: the height of the buffer
     * @param depth: the depth of the buffer
     */
    constructor(width: Int, height: Int, depth: Int) : this(Array(width) { Array(height) { FloatArray(depth) { 0.0f } } })

    /**
     * Constructor for an empty buffer of the given width and height.
     * The internal buffer will be initialized to a random value
     * using the provided Random.
     *
     * @param width: the width of the buffer
     * @param height: the height of the buffer
     * @param depth: the depth of the buffer
     * @param rng: the Random used to populate the buffer
     */
    constructor(width: Int, height: Int, depth: Int, rng: Random) : this(Array(width) { Array(height) { FloatArray(depth) { rng.nextFloat() } } })

    /**
     * Constructor for an empty buffer of the given width and height.
     * The internal buffer will be initialized to a uniform value
     * using the provided value.
     *
     * @param width: the width of the buffer
     * @param height: the height of the buffer
     * @param depth: the depth of the buffer
     * @param value: the value used to populate the buffer
     */
    constructor(width: Int, height: Int, depth: Int, value: Float) : this(Array(width) { Array(height) { FloatArray(depth) { value } } })

    /**
     * Constructor for an empty buffer of the given size. The internal buffer will be initialized
     * to 0.
     * @param size: the size for the width and height of the buffer
     */
    constructor(size: Int) : this(Array(size) { Array(size) { FloatArray(size) { 0.0f } } })

    /**
     * Constructor for an empty buffer of the given size. The internal buffer will be initialized
     * to random value using the provided Random.
     * @param size: the size for the width and height of the buffer
     * @param rng: the Random used to populate the buffer
     */
    constructor(size: Int, rng: Random) : this(Array(size) { Array(size) { FloatArray(size) { rng.nextFloat() } } })

    /**
     * Constructor for an empty buffer of the given size. The internal buffer will be initialized
     * to a uniform value using the provided value.
     * @param size: the size for the width and height of the buffer
     * @param value: the value used to populate the buffer
     */
    constructor(size: Int, value: Float) : this(Array(size) { Array(size) { FloatArray(size) { value } } })

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
    fun width(): Int = buffer.size

    /**
     * Get the height of the buffer.
     *
     * @return the height of the buffer
     */
    fun height(): Int = buffer[0].size

    /**
     * Get the depth of the buffer.
     *
     * @return the depth of the buffer
     */
    fun depth(): Int = buffer[0][0].size

    /**
     * Get the minimum value within the buffer.
     *
     * @return the minimum value within the buffer
     */
    override fun min(): Float = buffer.minOf { floats2 -> floats2.minOf { floats -> floats.min() } }

    /**
     * Get the maximum value within the buffer.
     *
     * @return the maximum value within the buffer
     */
    override fun max(): Float =  buffer.maxOf { floats2 -> floats2.maxOf { floats -> floats.max() } }

    /**
     * Perform an action on each element in the buffer.
     *
     * @param action: the action to perform on each element in the buffer
     */
    override fun forEach(action: (Float) -> Unit) { buffer.forEach { floats2 ->
        floats2.forEach { floats -> floats.forEach { value -> action(value) } } } }

    /**
     * Transform each element in the buffer to another element.
     *
     * @param transform: the transform to perform on each element in the buffer
     * @return this noise buffer
     */
    override fun map(transform: (Float) -> Float): SyncNoiseBuffer3 {
        val copy = copy()
        copy.buffer.forEachIndexed { x, floats2 ->
            floats2.forEachIndexed { y, floats -> floats.forEachIndexed { z, value -> buffer[x][y][z] = transform(value) } } }
        return this
    }

    /**
     * Transform each element in the buffer to another element.
     *
     * @param transform: the transform to perform on each element in the buffer
     * @return a new noise buffer with the mapped data
     */
    override fun mapIndexed(transform: (x: Int, y: Int, z: Int, Float) -> Float): SyncNoiseBuffer3 {
        val newBuffer = Array(width()) { Array(height()) {FloatArray(depth()) { 0.0f } } }
        for ((x, plane) in buffer.withIndex())
            for ((y, item) in plane.withIndex())
                for ((z, float) in item.withIndex())
                    newBuffer[x][y][z] = transform(x, y, z, float)
        return SyncNoiseBuffer3(newBuffer)
    }

    /**
     * Get this buffer.
     *
     * @return this buffer
     */
    override fun content(): SyncNoiseBuffer3 = this

    /**
     * Get a copy of the internal 2D array representation of the buffer.
     *
     * @return a copy of the 2D array representation of the buffer
     */
    override fun copy(): SyncNoiseBuffer3 = SyncNoiseBuffer3(buffer.map { it -> it.map { it.clone() }.toTypedArray() }.toTypedArray())

    /**
     * Fill the buffer given a noise provider.
     *
     * @param provider: the provider to use when filling the buffer
     * @return this noise buffer
     */
    override fun fill(provider: SimpleNoiseProvider, offsetX: Float, offsetY: Float, offsetZ: Float): SyncNoiseBuffer3 {
        for (x in 0 until width()) for (y in 0 until height()) for (z in 0 until depth())
            buffer[x][y][z] = provider.noise(x, y, z)
        return this
    }

    /**
     * Fill the buffer given a noise evaluator.
     *
     * @param evaluator: the evaluator to use when filling the buffer
     * @return this noise buffer
     */
    override fun fill(evaluator: NoiseEvaluator, offsetX: Float, offsetY: Float, offsetZ: Float): SyncNoiseBuffer3 {
        for (x in 0 until width()) for (y in 0 until height()) for (z in 0 until depth())
            buffer[x][y][z] = evaluator.noise(x.f(), y.f(), z.f())
        return this
    }

    /**
     * Transform the buffer to an asynchronous buffer.
     *
     * @param chunkSize: the chunk size to split the buffer in
     * @return this buffer in an asynchronous format
     */
    override fun toAsync(chunkSize: Int): AsyncNoiseBuffer {
        if (width() % chunkSize != 0 || height() % chunkSize != 0 || depth() % chunkSize != 0)
            throw NoiseException("Cannot transform buffer with irregular width and/or height")
        return AsyncNoiseBuffer3(this.buffer, chunkSize)
    }

}