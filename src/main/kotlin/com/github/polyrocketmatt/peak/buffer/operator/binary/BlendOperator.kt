package com.github.polyrocketmatt.peak.buffer.operator.binary

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3
import com.github.polyrocketmatt.peak.exception.BufferOperationException
import com.github.polyrocketmatt.peak.buffer.operator.BinaryBufferOperator

/**
 * Operator that blends two buffers together.
 */
class BlendOperator : BinaryBufferOperator {

    /**
     *  Blend the first and second buffer together.
     *
     *  @param first: the first buffer to perform the operation on
     *  @param second: the second buffer to perform the operation on
     *  @param data: ratio - the ratio at which the first buffer will have an impact.
     *  the second buffer will have an impact of 1 - ratio.
     *  @throws BufferOperationException if the ratio is not provided
     *  @throws BufferOperationException if the ratio is not contained within [0:1]
     *  @return a new NoiseBuffer that contains the blend of the two buffers
     */
    override fun operate(first: NoiseBuffer2, second: NoiseBuffer2, vararg data: Any): NoiseBuffer2 {
        if (data.size != 1)
            throw BufferOperationException("Incorrect number of arguments provided! Expected ratio arguments!")
        if (data[0] !is Float)
            throw BufferOperationException("Expected ratio argument to be of type float!")

        val ratio = data[0] as Float
        if (ratio < 0.0f || ratio > 1.0f)
            throw BufferOperationException("Ratio for combine was not within [0:1] bounds")
        val inverseRatio = 1.0f - ratio

        return first.mapIndexed { x, y, fl -> fl * ratio + second[x][y] * inverseRatio }
    }

    /**
     *  Blend the first and second buffer together.
     *
     *  @param first: the first buffer to perform the operation on
     *  @param second: the second buffer to perform the operation on
     *  @param data: ratio - the ratio at which the first buffer will have an impact.
     *  the second buffer will have an impact of 1 - ratio.
     *  @throws BufferOperationException if the ratio is not provided
     *  @throws BufferOperationException if the ratio is not contained within [0:1]
     *  @return a new NoiseBuffer that contains the blend of the two buffers
     */
    override fun operate(first: NoiseBuffer3, second: NoiseBuffer3, vararg data: Any): NoiseBuffer3 {
        if (data.size != 1)
            throw BufferOperationException("Incorrect number of arguments provided! Expected ratio arguments!")
        if (data[0] !is Float)
            throw BufferOperationException("Expected ratio argument to be of type float!")

        val ratio = data[0] as Float
        if (ratio < 0.0f || ratio > 1.0f)
            throw BufferOperationException("Ratio for combine was not within [0:1] bounds")
        val inverseRatio = 1.0f - ratio

        return first.mapIndexed { x, y, z, fl -> fl * ratio + second[x][y][z] * inverseRatio }
    }

}