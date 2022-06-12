package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.game.math.normalize
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.operator.UnaryBufferOperator

/**
 * Operator that normalizes a buffer using its minimum and maximum elements.
 */
class NormalisationOperator : UnaryBufferOperator {

    /**
     * Normalizes a buffer using its minimum and maximum elements.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data: no additional data required for this operation
     * @return a new NoiseBuffer that contains the normalized elements of the buffer
     */
    override fun operate(buffer: NoiseBuffer2, vararg data: Any): NoiseBuffer2 {
        val min = buffer.min()
        val max = buffer.max()

        return buffer.map { fl -> fl.normalize(min, max) }
    }

    /**
     * Normalizes a buffer using its minimum and maximum elements.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data: no additional data required for this operation
     * @return a new NoiseBuffer that contains the normalized elements of the buffer
     */
    override fun operate(buffer: NoiseBuffer3, vararg data: Any): NoiseBuffer3 {
        val min = buffer.min()
        val max = buffer.max()

        return buffer.map { fl -> fl.normalize(min, max) }
    }

}