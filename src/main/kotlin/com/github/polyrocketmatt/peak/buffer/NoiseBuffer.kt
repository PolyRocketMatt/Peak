package com.github.polyrocketmatt.peak.buffer

import com.github.polyrocketmatt.peak.provider.base.SimpleNoiseProvider
import com.github.polyrocketmatt.peak.types.NoiseEvaluator
import java.awt.image.BufferedImage

/**
 * Represents a noise buffer.
 */
interface NoiseBuffer {

    /**
     * Get the minimum value within the buffer.
     *
     * @return the minimum value within the buffer
     */
    fun min(): Float

    /**
     * Get the maximum value within the buffer.
     *
     * @return the maximum value within the buffer
     */
    fun max(): Float

    /**
     * Perform an action on each element in the buffer.
     *
     * @param action: the action to perform on each element in the buffer
     */
    fun forEach(action: (Float) -> Unit)

    /**
     * Get this buffer.
     *
     * @return this buffer
     */
    fun content(): NoiseBuffer

    /**
     * Get a copy of the internal 2D array representation of the buffer.
     *
     * @return a copy of the 2D array representation of the buffer
     */
    fun copy(): NoiseBuffer

    /**
     * Fill the buffer given a noise provider.
     *
     * @param provider: the provider to use when filling the buffer
     * @param offsetX: the x-coordinate offset to use in the provider
     * @param offsetY: the y-coordinate offset to use in the provider
     * @param offsetZ: the z-coordinate offset to use in the provider
     * @return this noise buffer
     */
    fun fill(provider: SimpleNoiseProvider, offsetX: Float, offsetY: Float, offsetZ: Float): NoiseBuffer

    /**
     * Fill the buffer given a noise evaluator.
     *
     * @param evaluator: the evaluator to use when filling the buffer
     * @param offsetX: the x-coordinate offset to use in the provider
     * @param offsetY: the y-coordinate offset to use in the provider
     * @param offsetZ: the z-coordinate offset to use in the provider
     * @return this noise buffer
     */
    fun fill(evaluator: NoiseEvaluator, offsetX: Float, offsetY: Float, offsetZ: Float): NoiseBuffer

}