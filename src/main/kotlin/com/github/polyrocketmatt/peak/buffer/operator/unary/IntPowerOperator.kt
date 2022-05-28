package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.game.math.i
import com.github.polyrocketmatt.game.math.intPow
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3
import com.github.polyrocketmatt.peak.exception.BufferOperationException
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.operator.UnaryBufferOperator

/**
 * Operator that raises a buffer to a provided integer power.
 */
class IntPowerOperator : UnaryBufferOperator {

    /**
     * Raises a buffer to a provided integer power.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data:
     * exp - the exponent to raise to.
     * @throws BufferOperationException if there are no exponent provided
     * @return a new NoiseBuffer that contains the raised elements of the buffer to the provided integer power
     */
    override fun operate(buffer: NoiseBuffer2, vararg data: Float): NoiseBuffer2 {
        if (data.size != 1)
            throw BufferOperationException("Incorrect number of arguments provided! Expected exponent argument!")
        val exp = data[0].i()

        return buffer.map { fl -> fl.intPow(exp) }
    }

    /**
     * Raises a buffer to a provided integer power.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data:
     * exp - the exponent to raise to.
     * @throws BufferOperationException if there are no exponent provided
     * @return a new NoiseBuffer that contains the raised elements of the buffer to the provided integer power
     */
    override fun operate(buffer: NoiseBuffer3, vararg data: Float): NoiseBuffer3 {
        if (data.size != 1)
            throw BufferOperationException("Incorrect number of arguments provided! Expected exponent argument!")
        val exp = data[0].i()

        return buffer.map { fl -> fl.intPow(exp) }
    }

}