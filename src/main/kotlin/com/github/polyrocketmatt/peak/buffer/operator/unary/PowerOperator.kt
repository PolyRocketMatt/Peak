package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3
import com.github.polyrocketmatt.peak.exception.BufferOperationException
import com.github.polyrocketmatt.peak.buffer.operator.UnaryBufferOperator
import kotlin.math.pow

/**
 * Operator that raises a buffer to a provided power.
 */
class PowerOperator : UnaryBufferOperator {

    /**
     * Raises a buffer to a provided power.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data:
     * exp - the exponent to raise to.
     * @throws BufferOperationException if there are no exponent provided
     * @return a new NoiseBuffer that contains the raised elements of the buffer to the provided power
     */
    override fun operate(buffer: NoiseBuffer2, vararg data: Any): NoiseBuffer2 {
        if (data.size != 1)
            throw BufferOperationException("Incorrect number of arguments provided! Expected exponent argument!")
        if (data[0] !is Float)
            throw BufferOperationException("Expected exponent argument to be of type float!")

        val exp = data[0] as Float
        return buffer.map { fl -> fl.pow(exp) }
    }

    /**
     * Raises a buffer to a provided power.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data:
     * exp - the exponent to raise to.
     * @throws BufferOperationException if there are no exponent provided
     * @return a new NoiseBuffer that contains the raised elements of the buffer to the provided power
     */
    override fun operate(buffer: NoiseBuffer3, vararg data: Any): NoiseBuffer3 {
        if (data.size != 1)
            throw BufferOperationException("Incorrect number of arguments provided! Expected exponent argument!")
        if (data[0] !is Float)
            throw BufferOperationException("Expected exponent argument to be of type float!")

        val exp = data[0] as Float
        return buffer.map { fl -> fl.pow(exp) }
    }

}