package com.github.polyrocketmatt.peak.buffer.operator

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer3

interface BinaryBufferOperator : BufferOperator {

    /**
     * Perform an operation on the buffers with the given data.
     *
     * @param first: the first buffer to perform an operation on
     * @param second: the second buffer to perform an operation on
     * @param data: the additional data to perform the operation
     * @return a new NoiseBuffer that contains the result of the operation
     */
    fun operate(first: NoiseBuffer2, second: NoiseBuffer2, vararg data: Float): NoiseBuffer2

    /**
     * Perform an operation on the buffers with the given data.
     *
     * @param first: the first buffer to perform an operation on
     * @param second: the second buffer to perform an operation on
     * @param data: the additional data to perform the operation
     * @return a new NoiseBuffer that contains the result of the operation
     */
    fun operate(first: NoiseBuffer3, second: NoiseBuffer3, vararg data: Float): NoiseBuffer3

}