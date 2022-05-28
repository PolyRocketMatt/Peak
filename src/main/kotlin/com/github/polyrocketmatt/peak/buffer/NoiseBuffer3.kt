package com.github.polyrocketmatt.peak.buffer

/**
 * Represents a 3D noise buffer.
 */
interface NoiseBuffer3 : NoiseBuffer {

    operator fun get(index: Int): Array<FloatArray>

    /**
     * Transform each element in the buffer to another element.
     *
     * @param transform: the transform to perform on each element in the buffer
     */
    fun map(transform: (Float) -> Float): NoiseBuffer3

    fun mapIndexed(transform: (x: Int, y: Int, z: Int, Float) -> Float): NoiseBuffer3

}