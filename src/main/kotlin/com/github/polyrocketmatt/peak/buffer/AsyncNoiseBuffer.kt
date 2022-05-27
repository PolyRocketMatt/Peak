package com.github.polyrocketmatt.peak.buffer

/**
 * Defines buffer that holds a noise map on which operations
 * can be performed in an asynchronous manner.
 */
interface AsyncNoiseBuffer : NoiseBuffer {

    interface NoiseChunk

    /**
     * Represents a chunk of noise created by an asynchronous buffer.
     *
     * @param x: the chunk index in the x-direction
     * @param y: the chunk index in the y-direction
     * @param data: the data contained within this chunk
     */
    data class NoiseChunk2(val x: Int, val y: Int, val data: Array<FloatArray> = emptyArray()) : NoiseChunk {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as NoiseChunk2

            if (!data.contentDeepEquals(other.data)) return false

            return true
        }

        override fun hashCode(): Int = data.contentDeepHashCode()
    }

    /**
     * Represents a chunk of noise created by an asynchronous buffer.
     *
     * @param x: the chunk index in the x-direction
     * @param y: the chunk index in the y-direction
     * @param z: the chunk index in the z-direction
     * @param data: the data contained within this chunk
     */
    data class NoiseChunk3(val x: Int, val y: Int, val z: Int, val data: Array<Array<FloatArray>> = emptyArray()) : NoiseChunk {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as NoiseChunk2

            if (!data.contentDeepEquals(other.data)) return false

            return true
        }

        override fun hashCode(): Int = data.contentDeepHashCode()
    }

    /**
     * Update the buffer.
     */
    fun update()

    /**
     * Transform the buffer to a synchronous buffer.
     *
     * @return this buffer in a synchronous format
     */
    fun toSync(): SyncNoiseBuffer

}