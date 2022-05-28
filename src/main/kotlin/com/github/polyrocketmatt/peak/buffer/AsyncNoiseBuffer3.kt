package com.github.polyrocketmatt.peak.buffer

import com.github.polyrocketmatt.game.math.f
import com.github.polyrocketmatt.game.math.statistics.max
import com.github.polyrocketmatt.game.math.statistics.min
import com.github.polyrocketmatt.peak.exception.NoiseException
import com.github.polyrocketmatt.peak.provider.base.SimpleNoiseProvider
import com.github.polyrocketmatt.peak.types.NoiseEvaluator
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.TimeUnit
import kotlin.random.Random

/**
 * Constructs a wrapper for a 3D array on which special noise-related
 * operations can be performed.
 *
 * @param buffer: the 3D array of floats, which represent the buffer
 */
class AsyncNoiseBuffer3(private val buffer: Array<Array<FloatArray>>, private val chunkSize: Int) : NoiseBuffer3, AsyncNoiseBuffer {

    private val chunksX: Int = buffer.size / chunkSize
    private val chunksY: Int = buffer[0].size / chunkSize
    private val chunksZ: Int = buffer[0][0].size / chunkSize
    private var chunks: List<AsyncNoiseBuffer.NoiseChunk3> = arrayListOf()
    private var update: Boolean = false
    private var threadCount: Int = 1
    private var maxThreads: Int = 1

    init { update() }

    /**
     * Constructor for an empty buffer of the given width and height.
     * The internal buffer will be initialized to 0.
     *
     * @param width: the width of the buffer
     * @param height: the height of the buffer
     * @param depth: the depth of the buffer
     */
    constructor(width: Int, height: Int, depth: Int, chunkSize: Int) : this(Array(width) { Array(height) { FloatArray(depth) { 0.0f } } }, chunkSize)

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
    constructor(width: Int, height: Int, depth: Int, chunkSize: Int, rng: Random) : this(Array(width) { Array(height) { FloatArray(depth) { rng.nextFloat() } } }, chunkSize)

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
    constructor(width: Int, height: Int, depth: Int, chunkSize: Int, value: Float) : this(Array(width) { Array(height) { FloatArray(depth) { value } } }, chunkSize)

    /**
     * Constructor for an empty buffer of the given size. The internal buffer will be initialized
     * to 0.
     * @param size: the size for the width and height of the buffer
     */
    constructor(size: Int, chunkSize: Int) : this(Array(size) { Array(size) { FloatArray(size) { 0.0f } } }, chunkSize)

    /**
     * Constructor for an empty buffer of the given size. The internal buffer will be initialized
     * to random value using the provided Random.
     * @param size: the size for the width and height of the buffer
     * @param rng: the Random used to populate the buffer
     */
    constructor(size: Int, chunkSize: Int, rng: Random) : this(Array(size) { Array(size) { FloatArray(size) { rng.nextFloat() } } }, chunkSize)

    /**
     * Constructor for an empty buffer of the given size. The internal buffer will be initialized
     * to a uniform value using the provided value.
     * @param size: the size for the width and height of the buffer
     * @param value: the value used to populate the buffer
     */
    constructor(size: Int, chunkSize: Int, value: Float) : this(Array(size) { Array(size) { FloatArray(size) { value } } }, chunkSize)

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
     * @throws NoiseException if the buffer took too long to compute
     * @return this noise buffer
     */
    override fun map(transform: (Float) -> Float): AsyncNoiseBuffer3 {
        if (update)
            update()

        val service = ForkJoinPool(this.chunks.size)
        val copy = copy()
        for (chunk in copy.chunks) {
            val cX = chunk.x * chunkSize
            val cY = chunk.y * chunkSize
            val cZ = chunk.z * chunkSize
            val task = AsyncNoiseBuffer.AsyncTask {
                for (x in cX until cX + chunkSize) for (y in cY until cY + chunkSize) for (z in cZ until cZ + chunkSize)
                    copy[x][y][z] = transform(copy[x][y][z])
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
    override fun mapIndexed(transform: (x: Int, y: Int, z: Int, Float) -> Float): AsyncNoiseBuffer3 {
        if (update)
            update()

        val service = ForkJoinPool(this.chunks.size)
        val newBuffer = Array(width()) { Array(height()) { FloatArray(depth()) { 0.0f } } }
        for (chunk in this.chunks) {
            val cX = chunk.x * chunkSize
            val cY = chunk.y * chunkSize
            val cZ = chunk.z * chunkSize
            val task = AsyncNoiseBuffer.AsyncTask {
                for (x in cX until cX + chunkSize) for (y in cY until cY + chunkSize) for (z in cZ until cZ + chunkSize)
                    newBuffer[x][y][z] = transform(x, y, z, buffer[x][y][z])
            }

            service.submit(task)
        }

        service.shutdown()

        val finish = service.awaitTermination(2, TimeUnit.MINUTES)
        if (finish)
            return AsyncNoiseBuffer3(newBuffer, chunkSize)
        else
            throw NoiseException("Buffer took too long to compute!")
    }

    /**
     * Get this buffer.
     *
     * @return this buffer
     */
    override fun content(): AsyncNoiseBuffer3 = this

    /**
     * Get a copy of the internal 2D array representation of the buffer.
     *
     * @return a copy of the 2D array representation of the buffer
     */
    override fun copy(): AsyncNoiseBuffer3 = AsyncNoiseBuffer3(buffer.map { it -> it.map { it.clone() }.toTypedArray() }.toTypedArray(), chunkSize)

    /**
     * Fill the buffer given a noise provider.
     *
     * @param provider: the provider to use when filling the buffer
     * @throws NoiseException if the buffer took too long to compute
     * @return this noise buffer
     */
    override fun fill(provider: SimpleNoiseProvider): AsyncNoiseBuffer3 {
        val service = ForkJoinPool(this.chunks.size)
        for (chunk in this.chunks) {
            val cX = chunk.x * chunkSize
            val cY = chunk.y * chunkSize
            val cZ = chunk.z * chunkSize
            val task = AsyncNoiseBuffer.AsyncTask {
                for (x in cX until cX + chunkSize) for (y in cY until cY + chunkSize) for (z in cZ until cZ + chunkSize)
                    this.buffer[x][y][z] = provider.noise(x, y, z)
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
     * @throws NoiseException if the buffer took too long to compute
     * @return this noise buffer
     */
    override fun fill(evaluator: NoiseEvaluator): AsyncNoiseBuffer3 {
        val service = ForkJoinPool(this.chunks.size)
        for (chunk in this.chunks) {
            val cX = chunk.x * chunkSize
            val cY = chunk.y * chunkSize
            val cZ = chunk.z * chunkSize
            val task = AsyncNoiseBuffer.AsyncTask {
                for (x in cX until cX + chunkSize) for (y in cY until cY + chunkSize) for (z in cZ until cZ + chunkSize)
                    this.buffer[x][y][z] = evaluator.noise(x.f(), y.f(), z.f())
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
     * Set the maximum number of threads this buffer can use.
     *
     * @param maxThreads: the maximum number of threads this buffer can use
     */
    override fun setMaxThreadCount(maxThreads: Int) { this.maxThreads = maxThreads }

    /**
     * Update the buffer.
     */
    override fun update() {
        val chunks = arrayListOf<AsyncNoiseBuffer.NoiseChunk3>()
        for (x in 0 until chunksX)
            for (y in 0 until chunksY)
                for (z in 0 until chunksZ) {
                    val cX = x * chunkSize
                    val cY = y * chunkSize
                    val cZ = z * chunkSize
                    val data = Array(chunkSize) { Array(chunkSize) { FloatArray(chunkSize) { 0.0f } } }
                    for ((indexX, row) in (cX until cX + chunkSize).withIndex())
                        for ((indexY, column) in (cY until cY + chunkSize).withIndex())
                            for ((indexZ, element) in (cZ until cZ + chunkSize).withIndex())
                            data[indexX][indexY][indexZ] = buffer[row][column][element]
                    chunks.add(AsyncNoiseBuffer.NoiseChunk3(x, y, z, data))
                }
        this.chunks = chunks
    }

    /**
     * Transform the buffer to a synchronous buffer.
     *
     * @return this buffer in a synchronous format
     */
    override fun toSync(): SyncNoiseBuffer = SyncNoiseBuffer3(this.buffer)

}