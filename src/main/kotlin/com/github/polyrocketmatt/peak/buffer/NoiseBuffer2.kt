package com.github.polyrocketmatt.peak.buffer

import java.awt.image.BufferedImage

/**
 * Represents a 2D noise buffer.
 */
interface NoiseBuffer2 : NoiseBuffer {

    operator fun get(index: Int): FloatArray

    fun image(): BufferedImage

    /**
     * Transform each element in the buffer to another element.
     *
     * @param transform: the transform to perform on each element in the buffer
     */
    fun map(transform: (Float) -> Float): NoiseBuffer2

    fun mapIndexed(transform: (x: Int, y: Int, Float) -> Float): NoiseBuffer2

}