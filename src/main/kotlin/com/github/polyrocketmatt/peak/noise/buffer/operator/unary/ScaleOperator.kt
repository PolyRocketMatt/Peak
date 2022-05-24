package com.github.polyrocketmatt.peak.noise.buffer.operator.unary

import com.github.polyrocketmatt.peak.exception.BufferOperationException
import com.github.polyrocketmatt.peak.noise.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.noise.buffer.operator.UnaryBufferOperator

class ScaleOperator : UnaryBufferOperator {

    override fun operate(buffer: NoiseBuffer, vararg data: Float): NoiseBuffer {
        if (data.size != 1)
            throw BufferOperationException("Incorrect number of arguments provided! Expected scale argument!")
        val scale = data[0]

        buffer.op { fl -> fl * scale }

        return buffer
    }

}