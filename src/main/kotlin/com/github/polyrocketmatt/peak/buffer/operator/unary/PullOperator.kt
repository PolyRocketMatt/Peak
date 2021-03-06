package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.game.math.clip
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.operator.UnaryBufferOperator
import com.github.polyrocketmatt.peak.exception.BufferOperationException
import com.github.polyrocketmatt.peak.math.smoothStepLerp

/**
 * Operator that pulls a buffer upward from a provided min.
 */
class PullOperator : UnaryBufferOperator {

    /**
     * Pulls a buffer upward after the provided max. Between min and max,
     * the buffer gets pulled up linearly.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data:
     * min - the minimum value from where to start pulling.
     * max - the maximum value from where to keep pulling.
     * increase - the increase value after max
     * @throws BufferOperationException if there are no min / max / increase values provided
     * @return a new NoiseBuffer that contains the pulled elements of the buffer
     */
    override fun operate(buffer: NoiseBuffer2, vararg data: Any): NoiseBuffer2 {
        if (data.size != 3)
            throw BufferOperationException("Incorrect number of arguments provided! Expected min, max and increase arguments!")
        if (data[0] !is Float)
            throw BufferOperationException("Expected min argument to be of type float!")
        if (data[1] !is Float)
            throw BufferOperationException("Expected max argument to be of type float!")
        if (data[2] !is Float)
            throw BufferOperationException("Expected increase argument to be of type float!")

        val min = data[0] as Float
        val max = data[1] as Float
        val inc = data[2] as Float

        return buffer.map { fl -> kotlin.run {
            val smoothStepped = fl.clip(min, max).smoothStepLerp(min, max)

            fl + inc * smoothStepped
        } }
    }

    /**
     * Pulls a buffer upward after the provided max. Between min and max,
     * the buffer gets pulled up linearly.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data:
     * min - the minimum value from where to start pulling.
     * max - the maximum value from where to keep pulling.
     * increase - the increase value after max
     * @throws BufferOperationException if there are no min / max / increase values provided
     * @return a new NoiseBuffer that contains the pulled elements of the buffer
     */
    override fun operate(buffer: NoiseBuffer3, vararg data: Any): NoiseBuffer3 {
        if (data.size != 3)
            throw BufferOperationException("Incorrect number of arguments provided! Expected min, max and increase arguments!")
        if (data[0] !is Float)
            throw BufferOperationException("Expected exponent argument to be of type float!")
        if (data[1] !is Float)
            throw BufferOperationException("Expected max argument to be of type float!")
        if (data[2] !is Float)
            throw BufferOperationException("Expected increase argument to be of type float!")

        val min = data[0] as Float
        val max = data[1] as Float
        val inc = data[2] as Float

        return buffer.map { fl -> kotlin.run {
            val smoothStepped = fl.smoothStepLerp(min, max)

            fl + inc * smoothStepped
        } }
    }
}