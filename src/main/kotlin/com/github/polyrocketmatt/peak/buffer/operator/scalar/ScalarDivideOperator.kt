package com.github.polyrocketmatt.peak.buffer.operator.scalar

import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.operator.ScalarBufferOperator

/**
 * Operator that divides a buffer with a scalar.
 */
class ScalarDivideOperator : ScalarBufferOperator {

    /**
     * Divide the provided data value to the buffer.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data: the data as divisor for each element
     * @return the noise buffer that contains the elements of the provided buffer with the divided data
     */
    override fun operate(buffer: SyncNoiseBuffer2, data: Float): SyncNoiseBuffer2 = buffer.map { fl -> if (fl == 0.0f || data == 0.0f) 0.0f else fl / data }

    /**
     * Multiply the provided data value to the buffer.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data: the data as divisor for each element
     * @return the noise buffer that contains the elements of the provided buffer with the divided data
     */
    override fun operate(buffer: SyncNoiseBuffer3, data: Float): SyncNoiseBuffer3 = buffer.map { fl -> if (fl == 0.0f || data == 0.0f) 0.0f else fl / data }

}