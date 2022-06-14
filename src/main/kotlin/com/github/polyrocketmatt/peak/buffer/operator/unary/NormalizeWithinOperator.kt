package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.game.math.normalize
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.operator.UnaryBufferOperator
import com.github.polyrocketmatt.peak.exception.BufferOperationException

/**
 * Operator that normalizes a buffer using a given minimum and maximum.
 */
class NormalizeWithinOperator : UnaryBufferOperator {

    /**
     * Normalizes a buffer using its minimum and maximum elements.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data:
     * min - the minimum value to normalize within.
     * max - the maximum value to normalize within.
     * @return a new NoiseBuffer that contains the normalized elements of the buffer
     */
    override fun operate(buffer: NoiseBuffer2, vararg data: Any): NoiseBuffer2 {
        if (data.size != 2)
            throw BufferOperationException("Incorrect number of arguments provided! Expected min and max argument!")
        if (data[0] !is Float)
            throw BufferOperationException("Expected min argument to be of type Float!")
        if (data[1] !is Float)
            throw BufferOperationException("Expected max argument to be of type Float!")
        val min = data[0] as Float
        val max = data[1] as Float

        return buffer.map { fl -> fl.normalize(min, max) }
    }

    /**
     * Normalizes a buffer using its minimum and maximum elements.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data:
     * min - the minimum value to normalize within.
     * max - the maximum value to normalize within.
     * @return a new NoiseBuffer that contains the normalized elements of the buffer
     */
    override fun operate(buffer: NoiseBuffer3, vararg data: Any): NoiseBuffer3 {
        val min = buffer.min()
        val max = buffer.max()

        return buffer.map { fl -> fl.normalize(min, max) }
    }

}