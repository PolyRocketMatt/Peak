package com.github.polyrocketmatt.peak.buffer.operator

import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer3

interface ScalarBufferOperator : BufferOperator {

    /**
     * Perform an operation on the buffer with the given data.
     *
     * @param buffer: the buffer to perform an operation on
     * @param data: the additional data to perform the operation
     * @return a new NoiseBuffer that contains the result of the operation
     */
    fun operate(buffer: SyncNoiseBuffer2, data: Float): SyncNoiseBuffer2

    /**
     * Perform an operation on the buffer with the given data.
     *
     * @param buffer: the buffer to perform an operation on
     * @param data: the additional data to perform the operation
     * @return a new NoiseBuffer that contains the result of the operation
     */
    fun operate(buffer: SyncNoiseBuffer3, data: Float): SyncNoiseBuffer3

}