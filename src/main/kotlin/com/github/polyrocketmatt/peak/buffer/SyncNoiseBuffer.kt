package com.github.polyrocketmatt.peak.buffer

/**
 * Defines synchronized buffer that holds a noise map on which synchronous operations
 * can be performed.
 */
interface SyncNoiseBuffer {

    /**
     * Transform the buffer to an asynchronous buffer.
     *
     * @param chunkSize: the chunk size to split the buffer in
     * @return this buffer in an asynchronous format
     */
    fun toAsync(chunkSize: Int): AsyncNoiseBuffer

}