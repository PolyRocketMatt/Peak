package com.github.polyrocketmatt.peak.buffer.operator.scalar

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.operator.ScalarBufferOperator

/**
 * Operator that adds a scalar to a buffer.
 */
class ScalarAddOperator : ScalarBufferOperator {

    /**
     * Adds the provided data value to the buffer.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data: the data to add to each element
     * @return the noise buffer that contains the elements of the provided buffer with the added data
     */
    override fun operate(buffer: NoiseBuffer2, data: Float): NoiseBuffer2 = buffer.map { fl -> fl + data }

    /**
     * Adds the provided data value to the buffer.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data: the data to add to each element
     * @return the noise buffer that contains the elements of the provided buffer with the added data
     */
    override fun operate(buffer: NoiseBuffer3, data: Float): NoiseBuffer3 = buffer.map { fl -> fl + data }

}