package com.github.polyrocketmatt.peak.buffer.operator.binary

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.buffer.operator.BinaryBufferOperator
import kotlin.math.pow

class CombinedPowerOperator : BinaryBufferOperator {

    override fun operate(first: NoiseBuffer, second: NoiseBuffer, vararg data: Float): NoiseBuffer =
        NoiseBuffer(
            first.array().mapIndexed { x, floats -> floats.mapIndexed { z, fl -> fl.pow(second[x][z]) }.toFloatArray() }
                .toTypedArray()
        )

}