package com.github.polyrocketmatt.peak.buffer.operator.scalar

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.operator.ScalarBufferOperator

class ScalarSubtractOperator : ScalarBufferOperator {

    /**
     * Subtracts the provided data value to the buffer.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data: the data to subtract from element
     * @return the noise buffer that contains the elements of the provided buffer with the subtracted data
     */
    override fun operate(buffer: NoiseBuffer2, data: Float): NoiseBuffer2 = buffer.map { fl -> fl - data }

    /**
     * Subtracts the provided data value to the buffer.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data: the data to subtract from each element
     * @return the noise buffer that contains the elements of the provided buffer with the subtracted data
     */
    override fun operate(buffer: NoiseBuffer3, data: Float): NoiseBuffer3 = buffer.map { fl -> fl - data }

}