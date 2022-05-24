package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.game.math.i
import com.github.polyrocketmatt.game.math.intPow
import com.github.polyrocketmatt.peak.exception.BufferOperationException
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.buffer.operator.UnaryBufferOperator

class IntPowerOperator : UnaryBufferOperator {

    override fun operate(buffer: NoiseBuffer, vararg data: Float): NoiseBuffer {
        if (data.size != 1)
            throw BufferOperationException("Incorrect number of arguments provided! Expected exponent argument!")
        val exp = data[0].i()

        buffer.op { fl -> fl.intPow(exp) }

        return buffer
    }


}