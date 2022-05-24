package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.game.math.normalize
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
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
    override fun operate(buffer: NoiseBuffer, vararg data: Float): NoiseBuffer {
        val min = buffer.min()
        val max = buffer.max()

        buffer.map { fl -> fl.normalize(min, max) }

        return buffer
    }

}