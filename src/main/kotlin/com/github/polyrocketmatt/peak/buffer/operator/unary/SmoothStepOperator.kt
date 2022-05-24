package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.game.math.smoothStep
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.buffer.operator.UnaryBufferOperator

/**
 * Operator that performs the smoother-step function on a buffer.
 */
class SmoothStepOperator : UnaryBufferOperator {

    /**
     * Performs the smoother-step function on a buffer.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data: no additional data required for this operation
     * @return a new NoiseBuffer that contains the smoother-stepped elements of the buffer
     */
    override fun operate(buffer: NoiseBuffer, vararg data: Float): NoiseBuffer {
        buffer.map { fl -> fl.smoothStep() }

        return buffer
    }

}