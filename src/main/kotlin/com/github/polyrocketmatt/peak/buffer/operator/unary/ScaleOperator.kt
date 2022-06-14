package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3
import com.github.polyrocketmatt.peak.exception.BufferOperationException
import com.github.polyrocketmatt.peak.buffer.operator.UnaryBufferOperator

/**
 * Operator that scales a buffer with a provided scalar.
 */
class ScaleOperator : UnaryBufferOperator {

    /**
     * Scales a buffer with a provided scalar.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data:
     * scalar - the scalar to scale with.
     * @throws BufferOperationException if there are no scalar provided
     * @return a new NoiseBuffer that contains the scaled elements of the buffer with the provided scalar
     */
    override fun operate(buffer: NoiseBuffer2, vararg data: Any): NoiseBuffer2 {
        if (data.size != 1)
            throw BufferOperationException("Incorrect number of arguments provided! Expected scale argument!")
        if (data[0] !is Float)
            throw BufferOperationException("Expected scale argument to be of type float!")

        val scalar = data[0] as Float
        return buffer.map { fl -> fl * scalar }
    }

    /**
     * Scales a buffer with a provided scalar.
     *
     * @param buffer: the buffer to perform the operation on
     * @param data:
     * scalar - the scalar to scale with.
     * @throws BufferOperationException if there are no scalar provided
     * @return a new NoiseBuffer that contains the scaled elements of the buffer with the provided scalar
     */
    override fun operate(buffer: NoiseBuffer3, vararg data: Any): NoiseBuffer3 {
        if (data.size != 1)
            throw BufferOperationException("Incorrect number of arguments provided! Expected scale argument!")
        if (data[0] !is Float)
            throw BufferOperationException("Expected scale argument to be of type float!")

        val scalar = data[0] as Float
        return buffer.map { fl -> fl * scalar }
    }

}