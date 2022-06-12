package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.operator.UnaryBufferOperator


/**
 * Operator that inverts a buffer using its maximum element.
 */
class InvertOperator : UnaryBufferOperator {

    /**
     * Inverts a buffer using its maximum element.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data: no additional data required for this operator
     * @return a new NoiseBuffer that contains the inverted elements of the buffer
     */
    override fun operate(buffer: NoiseBuffer2, vararg data: Any): NoiseBuffer2 {
        val max = buffer.max()
        return buffer.map { fl -> max - fl }
    }

    /**
     * Inverts a buffer using its maximum element.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data: no additional data required for this operator
     * @return a new NoiseBuffer that contains the inverted elements of the buffer
     */
    override fun operate(buffer: NoiseBuffer3, vararg data: Any): NoiseBuffer3 {
        val max = buffer.max()
        return buffer.map { fl -> max - fl }
    }

}