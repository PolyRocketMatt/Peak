package com.github.polyrocketmatt.peak.noise.buffer.operator.binary

import com.github.polyrocketmatt.peak.noise.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.noise.buffer.operator.BinaryBufferOperator

class SubtractOperator : BinaryBufferOperator {

    override fun operate(first: NoiseBuffer, second: NoiseBuffer, vararg data: Float): NoiseBuffer
        = NoiseBuffer(first.array().mapIndexed { x, floats -> floats.mapIndexed { z, fl -> fl - second[x][z] }.toFloatArray() }.toTypedArray())

}