package com.github.polyrocketmatt.peak.buffer

import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.game.math.statistics.max
import com.github.polyrocketmatt.game.math.statistics.min
import com.github.polyrocketmatt.peak.exception.BufferInitException
import com.github.polyrocketmatt.peak.exception.NoiseException
import com.github.polyrocketmatt.peak.image.ImageUtils
import com.github.polyrocketmatt.peak.provider.base.SimpleNoiseProvider
import com.github.polyrocketmatt.peak.types.NoiseEvaluator
import java.awt.image.BufferedImage
import java.util.concurrent.*
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

/**
 * Constructs a wrapper for a 2D array on which special noise-related
 * operations can be performed.
 *
 * @param buffer: the 2D array of floats, which represent the buffer
 * @param chunkSize: the size of the chunks in all directions
 */
class AsyncNoiseBuffer2(private val buffer: Array<FloatArray>, private val chunkSize: Int) : NoiseBuffer2, AsyncNoiseBuffer {

    enum class Rotation2 { DEG_90, DEG_180 }

    private val chunksX: Int = buffer.size / max(1, chunkSize)
    private val chunksY: Int = buffer[0].size / max(1, chunkSize)
    private var chunks: List<AsyncNoiseBuffer.NoiseChunk2> = arrayListOf()
    private var update: Boolean = false
    private var threadCount: Int = 1
    private var maxThreads: Int = 64

    init {
        if (chunkSize < 1)
            throw BufferInitException("Buffer chunk size cannot be less than 1!")
        else
            update()
    }

    /**
     * Constructor for an empty buffer of the given width and height.
     * The internal buffer will be initialized to 0.
     *
     * @param width: the width of the buffer
     * @param height: the height of the buffer
     * @param chunkSize: the size of the chunks in all directions
     * @throws NegativeArraySizeException if the width or height is less than 0
     * @throws BufferInitException if the chunk size is less than 1
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
     * @throws BufferInitException if the width or height is less than 0
     * @throws BufferInitException if the chunk size is less than 1
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
     * @throws BufferInitException if the width or height is less than 0
     * @throws BufferInitException if the chunk size is less than 1
     */
    constructor(width: Int, height: Int, chunkSize: Int, value: Float) : this(Array(width) { FloatArray(height) { value } }, chunkSize)

    /**
     * Constructor for an empty buffer of the given size. The internal buffer will be initialized
     * to 0.
     * @param size: the size for the width and height of the buffer
     * @param chunkSize: the size of the chunks in all directions
     * @throws BufferInitException if the size is less than 0
     * @throws BufferInitException if the chunk size is less than 1
     */
    constructor(size: Int, chunkSize: Int) : this(Array(size) { FloatArray(size) { 0.0f } }, chunkSize)

    /**
     * Constructor for an empty buffer of the given size. The internal buffer will be initialized
     * to random value using the provided Random.
     * @param size: the size for the width and height of the buffer
     * @param chunkSize: the size of the chunks in all directions
     * @param rng: the Random used to populate the buffer
     * @throws BufferInitException if the size is less than 0
     * @throws BufferInitException if the chunk size is less than 1
     */
    constructor(size: Int, chunkSize: Int, rng: Random) : this(Array(size) { FloatArray(size) { rng.nextFloat() } }, chunkSize)

    /**
     * Constructor for an empty buffer of the given size. The internal buffer will be initialized
     * to a uniform value using the provided value.
     * @param size: the size for the width and height of the buffer
     * @param chunkSize: the size of the chunks in all directions
     * @param value: the value used to populate the buffer
     * @throws BufferInitException if the size is less than 0
     * @throws BufferInitException if the chunk size is less than 1
     */
    constructor(size: Int, chunkSize: Int, value: Float) : this(Array(size) { FloatArray(size) { value } }, chunkSize)

    /**
     * Get an element from this buffer.
     *
     * @param index: the index at which to get an element from the buffer.
     * @return the float array at the given index.
     */
    override operator fun get(index: Int): FloatArray = buffer[index]

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
     * @throws NoiseException if the buffer took too long to compute
     * @return this noise buffer
     */
    override fun map(transform: (Float) -> Float): AsyncNoiseBuffer2 {
        if (update)
            update()

        val service = ForkJoinPool(this.chunks.size)
        val copy = copy()
        for (chunk in copy.chunks) {
            val cX = chunk.x * chunkSize
            val cY = chunk.y * chunkSize
            val task = AsyncNoiseBuffer.AsyncTask {
                for (x in cX until cX + chunkSize) for (y in cY until cY + chunkSize)
                    copy[x][y] = transform(copy[x][y])
            }

            service.submit(task)
        }

        service.shutdown()

        val finish = service.awaitTermination(2, TimeUnit.MINUTES)
        if (finish)
            return copy
        else
            throw NoiseException("Buffer took too long to compute!")
    }

    /**
     * Transform each element in the buffer to another element.
     *
     * @param transform: the transform to perform on each element in the buffer
     * @throws NoiseException if the buffer took too long to compute
     * @return a new noise buffer with the mapped data
     */
    override fun mapIndexed(transform: (x: Int, y: Int, Float) -> Float): AsyncNoiseBuffer2 {
        if (update)
            update()

        val service = ForkJoinPool(this.chunks.size)
        val newBuffer = Array(width()) { FloatArray(height()) { 0.0f } }
        for (chunk in this.chunks) {
            val cX = chunk.x * chunkSize
            val cY = chunk.y * chunkSize
            val task = AsyncNoiseBuffer.AsyncTask {
                for (x in cX until cX + chunkSize) for (y in cY until cY + chunkSize)
                    newBuffer[x][y] = transform(x, y, buffer[x][y])
            }

            service.submit(task)
        }

        service.shutdown()

        val finish = service.awaitTermination(2, TimeUnit.MINUTES)
        if (finish)
            return AsyncNoiseBuffer2(newBuffer, chunkSize)
        else
            throw NoiseException("Buffer took too long to compute!")
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
     * @param offsetX: the x-coordinate offset to use in the evaluator
     * @param offsetY: the y-coordinate offset to use in the evaluator
     * @param offsetZ: the z-coordinate offset to use in the evaluator
     * @throws NoiseException if the buffer took too long to compute
     * @return this noise buffer
     */
    override fun fill(provider: SimpleNoiseProvider, offsetX: Float, offsetY: Float, offsetZ: Float): AsyncNoiseBuffer2 {
        val service = ForkJoinPool(this.chunks.size)
        for (chunk in this.chunks) {
            val cX = chunk.x * chunkSize
            val cY = chunk.y * chunkSize
            val task = AsyncNoiseBuffer.AsyncTask {
                for (x in cX until cX + chunkSize) for (y in cY until cY + chunkSize)
                    this.buffer[x][y] = provider.noise(x + offsetX, y + offsetY)
            }

            service.submit(task)
        }

        service.shutdown()

        val finish = service.awaitTermination(2, TimeUnit.MINUTES)
        if (finish) {
            this.update = true
            return this
        } else
            throw NoiseException("Buffer took too long to compute!")
    }

    /**
     * Fill the buffer given a noise evaluator.
     *
     * @param evaluator: the evaluator to use when filling the buffer
     * @param offsetX: the x-coordinate offset to use in the evaluator
     * @param offsetY: the y-coordinate offset to use in the evaluator
     * @param offsetZ: the z-coordinate offset to use in the evaluator
     * @throws NoiseException if the buffer took too long to compute
     * @return this noise buffer
     */
    override fun fill(evaluator: NoiseEvaluator, offsetX: Float, offsetY: Float, offsetZ: Float): AsyncNoiseBuffer2 {
        val service = ForkJoinPool(this.chunks.size)
        for (chunk in this.chunks) {
            val cX = chunk.x * chunkSize
            val cY = chunk.y * chunkSize
            val task = AsyncNoiseBuffer.AsyncTask {
                for (x in cX until cX + chunkSize) for (y in cY until cY + chunkSize)
                    this.buffer[x][y] = evaluator.noise(x.f() + offsetX, y.f() + offsetY)
            }

            service.submit(task)
        }

        service.shutdown()

        val finish = service.awaitTermination(2, TimeUnit.MINUTES)
        if (finish) {
            this.update = true
            return this
        } else
            throw NoiseException("Buffer took too long to compute!")
    }

    /**
     * Rotate the buffer given the rotation.
     *
     * @param rotation: the rotation on how to rotate the buffer
     * @return the rotated buffer according to the given rotation
     */
    fun rotate(rotation: Rotation2): AsyncNoiseBuffer2 {
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

        return AsyncNoiseBuffer2(rotated, chunkSize)
    }

    /**
     * Get the amount of threads currently allocated to the buffer.
     *
     * @return the amount of threads allocated to the buffer
     */
    override fun threadCount(): Int = this.threadCount

    /**
     * Set the maximum number of threads this buffer can use.
     *
     * @param maxThreads: the maximum number of threads this buffer can use
     */
    override fun setMaxThreadCount(maxThreads: Int) { this.maxThreads = max(63, maxThreads) }

    /**
     * Update the buffer.
     *
     * TODO: Update async
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
        this.update = false
        this.threadCount = this.chunks.size

        if (this.maxThreads < this.threadCount)
            throw BufferInitException("Too many threads allocated to buffer!")
    }


}