package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.game.math.sqrt
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.buffer.operator.UnaryBufferOperator

/**
 * Operator that takes the square root of a buffer.
 */
class SquareRootOperator : UnaryBufferOperator {

    /**
     * Takes the square root of a buffer.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data: no additional data required for this operation
     * @return a new NoiseBuffer that contains the square-rooted elements of the buffer
     */
    override fun operate(buffer: NoiseBuffer, vararg data: Float): NoiseBuffer {
        buffer.map { fl -> fl.sqrt() }

        return buffer
    }

}