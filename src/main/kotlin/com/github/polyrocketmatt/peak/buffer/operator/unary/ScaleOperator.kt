package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.peak.exception.BufferOperationException
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
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
    override fun operate(buffer: NoiseBuffer, vararg data: Float): NoiseBuffer {
        if (data.size != 1)
            throw BufferOperationException("Incorrect number of arguments provided! Expected scale argument!")
        val scalar = data[0]

        buffer.map { fl -> fl * scalar }

        return buffer
    }

}