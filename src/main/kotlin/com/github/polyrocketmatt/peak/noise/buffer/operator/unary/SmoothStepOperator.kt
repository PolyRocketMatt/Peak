package com.github.polyrocketmatt.peak.noise.buffer.operator.unary

import com.github.polyrocketmatt.game.math.smoothStep
import com.github.polyrocketmatt.peak.noise.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.noise.buffer.operator.UnaryBufferOperator

class SmoothStepOperator : UnaryBufferOperator {

    override fun operate(buffer: NoiseBuffer, vararg data: Float): NoiseBuffer {
        buffer.op { fl -> fl.smoothStep() }

        return buffer
    }

}