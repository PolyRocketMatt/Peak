package com.github.polyrocketmatt.peak.noise.buffer.operator.unary

import com.github.polyrocketmatt.game.math.lerp
import com.github.polyrocketmatt.peak.exception.BufferOperationException
import com.github.polyrocketmatt.peak.noise.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.noise.buffer.operator.UnaryBufferOperator

class LerpOperator : UnaryBufferOperator {

    override fun operate(buffer: NoiseBuffer, vararg data: Float): NoiseBuffer {
        if (data.size != 2)
            throw BufferOperationException("Incorrect number of arguments provided! Expected min and max arguments!")
        val min = data[0]
        val max = data[1]

        buffer.op { fl -> fl.lerp(min, max) }

        return buffer
    }

}