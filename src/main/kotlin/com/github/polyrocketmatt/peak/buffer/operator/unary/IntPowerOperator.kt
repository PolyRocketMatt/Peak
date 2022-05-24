package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.game.math.i
import com.github.polyrocketmatt.game.math.intPow
import com.github.polyrocketmatt.peak.exception.BufferOperationException
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
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
    override fun operate(buffer: NoiseBuffer, vararg data: Float): NoiseBuffer {
        if (data.size != 1)
            throw BufferOperationException("Incorrect number of arguments provided! Expected exponent argument!")
        val exp = data[0].i()

        buffer.map { fl -> fl.intPow(exp) }

        return buffer
    }


}