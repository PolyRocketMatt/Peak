package com.github.polyrocketmatt.peak.noise.buffer.operator.binary

import com.github.polyrocketmatt.peak.exception.BufferOperationException
import com.github.polyrocketmatt.peak.noise.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.noise.buffer.operator.BinaryBufferOperator

class BlendOperator : BinaryBufferOperator {

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