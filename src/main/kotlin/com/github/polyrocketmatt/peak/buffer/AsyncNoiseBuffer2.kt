package com.github.polyrocketmatt.peak.buffer

import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.game.math.statistics.max
import com.github.polyrocketmatt.game.math.statistics.min
import com.github.polyrocketmatt.peak.image.ImageUtils
import com.github.polyrocketmatt.peak.provider.base.SimpleNoiseProvider
import com.github.polyrocketmatt.peak.types.NoiseEvaluator
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.awt.image.BufferedImage
import kotlin.random.Random

/**
 * Constructs a wrapper for a 2D array on which special noise-related
 * operations can be performed.
 *
 * @param buffer: the 2D array of floats, which represent the buffer
 * @param chunkSize: the size of the chunks in all directions
 */
class AsyncNoiseBuffer2(private val buffer: Array<FloatArray>, private val chunkSize: Int) : AsyncNoiseBuffer {

    enum class Rotation2 { DEG_90, DEG_180 }

    private val chunksX: Int = buffer.size % chunkSize
    private val chunksY: Int = buffer[0].size % chunkSize
    private var chunks: List<AsyncNoiseBuffer.NoiseChunk2> = arrayListOf()
    private var update: Boolean = false

    init { update() }

    /**
     * Constructor for an empty buffer of the given width and height.
     * The internal buffer will be initialized to 0.
     *
     * @param width: the width of the buffer
     * @param height: the height of the buffer
     * @param chunkSize: the size of the chunks in all directions
     */
    constructor(width: Int, height: Int, chunkSize: Int) : this(Array(width) { FloatArray(height) { 0.0f } }, chunkSize)

    /**
     * Constructor for an empty buffer of the given width and height.
     * The internal buffer will be initialized to a random value
     * using the provided Random.
     *
     * @param width: the width of the buffer
     * @param height: the height of the buffer
     * @param chunkSize: the size of the chunks in all directions
     * @param rng: the Random used to populate the buffer
     */
    constructor(width: Int, height: Int, chunkSize: Int, rng: Random) : this(Array(width) { FloatArray(height) { rng.nextFloat() } }, chunkSize)

    /**
     * Constructor for an empty buffer of the given width and height.
     * The internal buffer will be initialized to a uniform value
     * using the provided value.
     *
     * @param width: the width of the buffer
     * @param height: the height of the buffer
     * @param chunkSize: the size of the chunks in all directions
     * @param value: the value used to populate the buffer
     */
    constructor(width: Int, height: Int, chunkSize: Int, value: Float) : this(Array(width) { FloatArray(height) { value } }, chunkSize)

    /**
     * Constructor for an empty buffer of the given size. The internal buffer will be initialized
     * to 0.
     * @param size: the size for the width and height of the buffer
     * @param chunkSize: the size of the chunks in all directions
     */
    constructor(size: Int, chunkSize: Int) : this(Array(size) { FloatArray(size) { 0.0f } }, chunkSize)

    /**
     * Constructor for an empty buffer of the given size. The internal buffer will be initialized
     * to random value using the provided Random.
     * @param size: the size for the width and height of the buffer
     * @param chunkSize: the size of the chunks in all directions
     * @param rng: the Random used to populate the buffer
     */
    constructor(size: Int, chunkSize: Int, rng: Random) : this(Array(size) { FloatArray(size) { rng.nextFloat() } }, chunkSize)

    /**
     * Constructor for an empty buffer of the given size. The internal buffer will be initialized
     * to a uniform value using the provided value.
     * @param size: the size for the width and height of the buffer
     * @param chunkSize: the size of the chunks in all directions
     * @param value: the value used to populate the buffer
     */
    constructor(size: Int, chunkSize: Int, value: Float) : this(Array(size) { FloatArray(size) { value } }, chunkSize)

    /**
     * Get an element from this buffer.
     *
     * @param index: the index at which to get an element from the buffer.
     * @return the float array at the given index.
     */
    operator fun get(index: Int): FloatArray = buffer[index]

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
    fun image(): BufferedImage = ImageUtils.bufferToImage(this)

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
    override fun map(transform: (Float) -> Float): AsyncNoiseBuffer2 {
        if (update)
            update()
        val copy = copy()

        runBlocking {
            for (chunk in copy.chunks) {
                val data = chunk.data

                launch {
                    data.forEachIndexed { x, floats -> floats.forEachIndexed { z, value -> copy[x][z] = transform(value) } }
                }
            }
        }

        return copy
    }

    /**
     * Transform each element in the buffer to another element.
     *
     * @param transform: the transform to perform on each element in the buffer
     * @return a new noise buffer with the mapped data
     */
    fun mapIndexed(transform: (x: Int, y: Int, Float) -> Float): AsyncNoiseBuffer2 {
        if (update)
            update()
        val newBuffer = Array(width()) { FloatArray(height()) { 0.0f } }

        runBlocking {
            for (chunk in chunks) {
                val cX = chunk.x * chunkSize
                val cY = chunk.y * chunkSize
                val data = chunk.data

                launch {
                    for ((indexX, x) in (cX until cX + chunkSize).withIndex())
                        for ((indexY, y) in (cY until cY + chunkSize).withIndex())
                            newBuffer[x][y] = transform(x, y, data[indexX][indexY])
                }
            }
        }

        return AsyncNoiseBuffer2(newBuffer, chunkSize)
    }

    /**
     * Get this buffer.
     *
     * @return this buffer
     */
    override fun content(): AsyncNoiseBuffer2 = this

    /**
     * Get a copy of the internal 2D array representation of the buffer.
     *
     * @return a copy of the 2D array representation of the buffer
     */
    override fun copy(): AsyncNoiseBuffer2 = AsyncNoiseBuffer2(buffer.map { it.clone() }.toTypedArray(), chunkSize)

    /**
     * Fill the buffer given a noise provider.
     *
     * @param provider: the provider to use when filling the buffer
     * @return this noise buffer
     */
    override fun fill(provider: SimpleNoiseProvider): AsyncNoiseBuffer2 {
        runBlocking {
            for (chunk in chunks) {
                val cX = chunk.x * chunkSize
                val cY = chunk.y * chunkSize

                launch {
                    for (x in cX until cX + chunkSize) for (y in cY until cY + chunkSize)
                        buffer[x][y] = provider.noise(x, y)
                }
            }
        }

        this.update = true
        return this
    }

    /**
     * Fill the buffer given a noise evaluator.
     *
     * @param evaluator: the evaluator to use when filling the buffer
     * @return this noise buffer
     */
    override fun fill(evaluator: NoiseEvaluator): AsyncNoiseBuffer2 {
        runBlocking {
            for (chunk in chunks) {
                val cX = chunk.x * chunkSize
                val cY = chunk.y * chunkSize

                launch {
                    for (x in cX until cX + chunkSize) for (y in cY until cY + chunkSize)
                        buffer[x][y] = evaluator.noise(x.f(), y.f())
                }
            }
        }

        this.update = true
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
     * Update the buffer.
     */
    override fun update() {
        val chunks = arrayListOf<AsyncNoiseBuffer.NoiseChunk2>()
        for (x in 0 until chunksX)
            for (y in 0 until chunksY) {
                val cX = x * chunkSize
                val cY = y * chunkSize
                val data = Array(chunkSize) { FloatArray(chunkSize) { 0.0f } }
                for ((indexX, row) in (cX until cX + chunkSize).withIndex())
                    for ((indexY, column) in (cY until cY + chunkSize).withIndex())
                        data[indexX][indexY] = buffer[row][column]
                chunks.add(AsyncNoiseBuffer.NoiseChunk2(x, y, data))
            }
        this.chunks = chunks
    }

    /**
     * Transform the buffer to a synchronous buffer.
     *
     * @return this buffer in a synchronous format
     */
    override fun toSync(): SyncNoiseBuffer = SyncNoiseBuffer2(this.buffer)

}