package com.github.polyrocketmatt.peak.buffer.operator.scalar

import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.operator.ScalarBufferOperator

/**
 * Operator that multiplies a scalar with a buffer.
 */
class ScalarMultiplyOperator : ScalarBufferOperator {

    /**
     * Multiply the provided data value to the buffer.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data: the data to multiply with each element
     * @return the noise buffer that contains the elements of the provided buffer with the multiplied data
     */
    override fun operate(buffer: SyncNoiseBuffer2, data: Float): SyncNoiseBuffer2 = buffer.map { fl -> fl * data }

    /**
     * Multiply the provided data value to the buffer.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data: the data to multiply with each element
     * @return the noise buffer that contains the elements of the provided buffer with the multiplied data
     */
    override fun operate(buffer: SyncNoiseBuffer3, data: Float): SyncNoiseBuffer3 = buffer.map { fl -> fl * data }

}