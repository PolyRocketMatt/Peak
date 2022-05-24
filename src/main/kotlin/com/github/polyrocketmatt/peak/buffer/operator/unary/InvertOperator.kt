package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
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
    override fun operate(buffer: NoiseBuffer, vararg data: Float): NoiseBuffer {
        val max = buffer.max()
        buffer.map { fl -> max - fl }

        return buffer
    }

}