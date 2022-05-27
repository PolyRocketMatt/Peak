package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.game.math.normalize
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer3
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
    override fun operate(buffer: SyncNoiseBuffer2, vararg data: Float): SyncNoiseBuffer2 {
        val min = buffer.min()
        val max = buffer.max()

        val buf = buffer.map { fl -> fl.normalize(min, max) }

        val bmin = buf.min()
        val bmax = buf.max()

        return buf
    }

    /**
     * Normalizes a buffer using its minimum and maximum elements.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data: no additional data required for this operation
     * @return a new NoiseBuffer that contains the normalized elements of the buffer
     */
    override fun operate(buffer: SyncNoiseBuffer3, vararg data: Float): SyncNoiseBuffer3 {
        val min = buffer.min()
        val max = buffer.max()

        return buffer.map { fl -> fl.normalize(min, max) }
    }

}