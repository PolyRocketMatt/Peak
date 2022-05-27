package com.github.polyrocketmatt.peak.primitive.noise

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.primitive.Primitive
import java.awt.image.BufferedImage

/**
 * Defines a noise-related primitive, based on a noise buffer.
 */
abstract class NoisePrimitive(private var buffer: NoiseBuffer2, var update: Boolean): Primitive {

    /**
     * Get the noise buffer of the primitive.
     *
     * @return the noise buffer of the primitive
     */
    override fun buffer(): NoiseBuffer2 {
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
    override fun image(): BufferedImage = buffer().image()

    protected fun update(update: NoiseBuffer2) { this.buffer = update }

}