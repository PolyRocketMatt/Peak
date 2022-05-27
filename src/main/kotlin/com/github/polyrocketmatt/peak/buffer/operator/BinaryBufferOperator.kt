package com.github.polyrocketmatt.peak.buffer.operator

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
    fun operate(first: SyncNoiseBuffer2, second: SyncNoiseBuffer2, vararg data: Float): SyncNoiseBuffer2

    /**
     * Perform an operation on the buffers with the given data.
     *
     * @param first: the first buffer to perform an operation on
     * @param second: the second buffer to perform an operation on
     * @param data: the additional data to perform the operation
     * @return a new NoiseBuffer that contains the result of the operation
     */
    fun operate(first: SyncNoiseBuffer3, second: SyncNoiseBuffer3, vararg data: Float): SyncNoiseBuffer3

}