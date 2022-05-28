package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.game.math.lerp
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer3
import com.github.polyrocketmatt.peak.exception.BufferOperationException
import com.github.polyrocketmatt.peak.buffer.operator.UnaryBufferOperator

/**
 * Operator that linearly interpolates a buffer to a provided min and max.
 */
class LerpOperator : UnaryBufferOperator {

    /**
     * Linearly interpolates a buffer to a provided min and max.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data:
     * min - the minimum value to interpolate to.
     * max - the maximum value to interpolate to.
     * @throws BufferOperationException if there are no min / max values provided
     * @return a new NoiseBuffer that contains the linearly interpolated elements of the buffer
     */
    override fun operate(buffer: NoiseBuffer2, vararg data: Float): NoiseBuffer2 {
        if (data.size != 2)
            throw BufferOperationException("Incorrect number of arguments provided! Expected min and max arguments!")
        val min = data[0]
        val max = data[1]

        return buffer.map { fl -> fl.lerp(min, max) }
    }

    /**
     * Linearly interpolates a buffer to a provided min and max.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data:
     * min - the minimum value to interpolate to.
     * max - the maximum value to interpolate to.
     * @throws BufferOperationException if there are no min / max values provided
     * @return a new NoiseBuffer that contains the linearly interpolated elements of the buffer
     */
    override fun operate(buffer: NoiseBuffer3, vararg data: Float): NoiseBuffer3 {
        if (data.size != 2)
            throw BufferOperationException("Incorrect number of arguments provided! Expected min and max arguments!")
        val min = data[0]
        val max = data[1]

        return buffer.map { fl -> fl.lerp(min, max) }
    }


}