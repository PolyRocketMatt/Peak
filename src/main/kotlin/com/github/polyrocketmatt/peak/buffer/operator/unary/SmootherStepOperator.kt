package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.game.math.smootherStep
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.operator.UnaryBufferOperator

/**
 * Operator that performs the smooth-step function on a buffer.
 */
class SmootherStepOperator : UnaryBufferOperator {

    /**
     * Performs the smooth-step function on a buffer.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data: no additional data required for this operation
     * @return a new NoiseBuffer that contains the smooth-stepped elements of the buffer
     */
    override fun operate(buffer: SyncNoiseBuffer2, vararg data: Float): SyncNoiseBuffer2 = buffer.map { fl -> fl.smootherStep() }

    /**
     * Performs the smooth-step function on a buffer.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data: no additional data required for this operation
     * @return a new NoiseBuffer that contains the smooth-stepped elements of the buffer
     */
    override fun operate(buffer: SyncNoiseBuffer3, vararg data: Float): SyncNoiseBuffer3 = buffer.map { fl -> fl.smootherStep() }

}