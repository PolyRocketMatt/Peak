package com.github.polyrocketmatt.peak.noise.buffer.operator.unary

import com.github.polyrocketmatt.peak.exception.BufferOperationException
import com.github.polyrocketmatt.peak.noise.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.noise.buffer.operator.UnaryBufferOperator
import kotlin.math.pow

class PowerOperator : UnaryBufferOperator {

    override fun operate(buffer: NoiseBuffer, vararg data: Float): NoiseBuffer {
        if (data.size != 1)
            throw BufferOperationException("Incorrect number of arguments provided! Expected exponent argument!")
        val exp = data[0]

        buffer.op { fl -> fl.pow(exp) }

        return buffer
    }

}