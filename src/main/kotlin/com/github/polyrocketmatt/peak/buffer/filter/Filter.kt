package com.github.polyrocketmatt.peak.buffer.filter

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3

abstract class Filter {

    /**
     * Perform a filter operation on the buffer with the given data.
     *
     * @param buffer: the buffer to perform an operation on
     * @return a new NoiseBuffer that contains the result of the operation
     */
    abstract fun operate(buffer: NoiseBuffer2): NoiseBuffer2

    /**
     * Perform an operation on the buffer with the given data.
     *
     * @param buffer: the buffer to perform an operation on
     * @return a new NoiseBuffer that contains the result of the operation
     */
    abstract fun operate(buffer: NoiseBuffer3): NoiseBuffer3

}