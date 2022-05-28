package com.github.polyrocketmatt.peak.primitive

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer
import java.awt.image.BufferedImage

/**
 * A primitive is an object that can be seen as both an image
 * and noise buffer. It defines standardized ways of creating
 * these images and buffers. They can be used to construct
 * many buffers without the need of creating new NoiseProviders.
 */
interface Primitive {

    /**
     * Recompute the primitive.
     */
    fun recompute()

    /**
     * Get the noise buffer of the primitive.
     *
     * @return the noise buffer of the primitive
     */
    fun buffer(): NoiseBuffer

    /**
     * Get the image of the primitive.
     *
     * @return the image of the primitive
     */
    fun image(): BufferedImage

}