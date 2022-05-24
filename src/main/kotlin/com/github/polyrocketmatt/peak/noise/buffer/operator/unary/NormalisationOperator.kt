package com.github.polyrocketmatt.peak.noise.buffer.operator.unary

import com.github.polyrocketmatt.game.math.normalize
import com.github.polyrocketmatt.game.math.statistics.max
import com.github.polyrocketmatt.game.math.statistics.min
import com.github.polyrocketmatt.peak.noise.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.noise.buffer.operator.UnaryBufferOperator

class NormalisationOperator : UnaryBufferOperator {

    override fun operate(buffer: NoiseBuffer, vararg data: Float): NoiseBuffer {
        val min = buffer.min()
        val max = buffer.max()

        buffer.op { fl -> fl.normalize(min, max) }

        return buffer
    }

}