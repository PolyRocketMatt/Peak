package com.github.polyrocketmatt.peak.buffer

import com.github.polyrocketmatt.game.math.statistics.max
import com.github.polyrocketmatt.game.math.statistics.min
import com.github.polyrocketmatt.peak.image.ImageUtils
import java.awt.image.BufferedImage
import kotlin.random.Random

/**
 * Constructs a wrapper for a 2D array on which special noise-related
 * operations can be performed.
 *
 * @param buffer: the 2D array of floats, which represent the buffer
 */
class NoiseBuffer(private val buffer: Array<FloatArray>) {

    /**
     * Constructor for an empty buffer of the given sizes.
     * The internal buffer will be initialized to 0.
     *
     * @param width: the width of the buffer
     * @param height: the height of the buffer
     */
    constructor(width: Int, height: Int) : this(Array(width) { FloatArray(height) { 0.0f } })

    /**
     * Constructor for an empty buffer of the given sizes.
     * The internal buffer will be initialized to a random value
     * using the provided Random.
     *
     * @param width: the width of the buffer
     * @param height: the height of the buffer
     * @param rng: the Random used to populate the buffer
     */
    constructor(width: Int, height: Int, rng: Random) : this(Array(width) { FloatArray(height) { rng.nextFloat() } })

    /**
     * Get an element from this buffer.
     *
     * @param index: the index at which to get an element from the buffer.
     * @return the float array at the given index.
     */
    operator fun get(index: Int) = buffer[index]

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
    fun min(): Float = buffer.minOf { floats -> floats.min() }

    /**
     * Get the maximum value within the buffer.
     *
     * @return the maximum value within the buffer
     */
    fun max(): Float =  buffer.maxOf { floats -> floats.max() }

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
    fun forEach(action: (Float) -> Unit) { buffer.forEach { floats -> floats.forEach { value -> action(value) } } }

    /**
     * Transform each element in the buffer to another element.
     *
     * @param transform: the transform to perform on each element in the buffer
     */
    fun map(transform: (Float) -> Float) { buffer.forEachIndexed { x, floats -> floats.forEachIndexed { z, value -> buffer[x][z] = transform(value) } } }

    /**
     * Transform each element in the buffer to another element.
     *
     * @param transform: the transform to perform on each element in the buffer
     */
    fun <R> mapIndexed(transform: (index: Int, FloatArray) -> R): List<R> = mapIndexedTo(ArrayList(buffer.size), transform)

    private fun <R, C : MutableCollection<in R>>mapIndexedTo(destination: C, transform: (index: Int, FloatArray) -> R): C {
        for ((index, item) in buffer.withIndex())
            destination.add(transform(index, item))
        return destination
    }

    /**
     * Get a copy of the internal 2D array representation of the buffer.
     *
     * @return a copy of the 2D array representation of the buffer
     */
    fun array(): NoiseBuffer = NoiseBuffer(buffer.map { it.clone() }.toTypedArray())

}