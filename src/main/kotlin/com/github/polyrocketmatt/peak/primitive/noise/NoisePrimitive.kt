package com.github.polyrocketmatt.peak.primitive.noise

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.primitive.Primitive
import java.awt.image.BufferedImage

/**
 * Defines a noise-related primitive, based on a noise buffer.
 */
abstract class NoisePrimitive(private val buffer: NoiseBuffer, var update: Boolean): Primitive {

    /**
     * Get the noise buffer of the primitive.
     *
     * @return the noise buffer of the primitive
     */
    override fun buffer(): NoiseBuffer {
        if (this.update) {
            this.update = false
            recompute()
        }
        return this.buffer
    }

    /**
     * Get the image of the primitive.
     *
     * @return the image of the primitive
     */
    override fun image(): BufferedImage = this.buffer.image()

}