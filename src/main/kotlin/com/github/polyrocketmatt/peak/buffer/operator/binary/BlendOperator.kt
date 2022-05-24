package com.github.polyrocketmatt.peak.buffer.operator.binary

import com.github.polyrocketmatt.peak.exception.BufferOperationException
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.buffer.operator.BinaryBufferOperator

/**
 * Operator that blends two buffers together.
 */
class BlendOperator : BinaryBufferOperator {

    /**
     *  Blend the first and second buffer together.
     *
     *  @param first: the first buffer
     *  @param second: the second buffer
     *  @param data: ratio - the ratio at which the first buffer will have an impact.
     *  the second buffer will have an impact of 1 - ratio.
     *  @throws BufferOperationException if the ratio is not provided
     *  @throws BufferOperationException if the ratio is not contained within [0:1]
     *  @return a new NoiseBuffer that contains the blend of the two buffers
     */
    override fun operate(first: NoiseBuffer, second: NoiseBuffer, vararg data: Float): NoiseBuffer {
        if (data.size != 1)
            throw BufferOperationException("Incorrect number of arguments provided! Expected ratio arguments!")
        val ratio = data[0]
        if (ratio < 0.0f || ratio > 1.0f)
            throw BufferOperationException("Ratio for combine was not within [0:1] bounds")
        val secondRatio = 1.0f - ratio
        return NoiseBuffer(first.array().mapIndexed { x, floats -> floats.mapIndexed { z, fl -> fl * ratio + second[x][z] * secondRatio }.toFloatArray() }.toTypedArray())
    }

}