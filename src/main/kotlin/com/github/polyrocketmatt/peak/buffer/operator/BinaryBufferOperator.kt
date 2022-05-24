package com.github.polyrocketmatt.peak.buffer.operator

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer

@FunctionalInterface
interface BinaryBufferOperator : BufferOperator {

    fun operate(first: NoiseBuffer, second: NoiseBuffer, vararg data: Float): NoiseBuffer

}