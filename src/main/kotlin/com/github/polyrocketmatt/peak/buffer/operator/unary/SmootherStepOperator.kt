package com.github.polyrocketmatt.peak.buffer.operator.unary

import com.github.polyrocketmatt.game.math.smootherStep
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.buffer.operator.UnaryBufferOperator

class SmootherStepOperator : UnaryBufferOperator {

    override fun operate(buffer: NoiseBuffer, vararg data: Float): NoiseBuffer {
        buffer.op { fl -> fl.smootherStep() }

        return buffer
    }

}