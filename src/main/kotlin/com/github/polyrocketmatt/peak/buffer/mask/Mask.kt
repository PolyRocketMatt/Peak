package com.github.polyrocketmatt.peak.buffer.mask

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer

@FunctionalInterface
interface Mask {

    /**
     * Apply the mask on the given buffer.
     *
     * @param buffer: the buffer to apply the mask to
     * @return the buffer with the mask applied to it
     */
    fun mask(buffer: NoiseBuffer): NoiseBuffer

}