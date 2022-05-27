package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.peak.exception.BufferOperationException
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer3
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
    override fun operate(buffer: SyncNoiseBuffer2, vararg data: Float): SyncNoiseBuffer2 {
        if (data.size != 1)
            throw BufferOperationException("Incorrect number of arguments provided! Expected scale argument!")
        val scalar = data[0]
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
    override fun operate(buffer: SyncNoiseBuffer3, vararg data: Float): SyncNoiseBuffer3 {
        if (data.size != 1)
            throw BufferOperationException("Incorrect number of arguments provided! Expected scale argument!")
        val scalar = data[0]
        return buffer.map { fl -> fl * scalar }
    }

}