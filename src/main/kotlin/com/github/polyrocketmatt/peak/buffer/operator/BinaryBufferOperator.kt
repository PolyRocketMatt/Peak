package com.github.polyrocketmatt.peak.buffer.operator

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer

@FunctionalInterface
interface BinaryBufferOperator : BufferOperator {

    /**
     * Perform an operation on the buffers with the given data.
     *
     * @param first: the first buffer to perform an operation on
     * @param second: the second buffer to perform an operation on
     * @param data: the additional data to perform the operation
     * @return a new NoiseBuffer that contains the result of the operation
     */
    fun operate(first: NoiseBuffer, second: NoiseBuffer, vararg data: Float): NoiseBuffer

}