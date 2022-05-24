package com.github.polyrocketmatt.peak.buffer.operator

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer

@FunctionalInterface
interface UnaryBufferOperator {

    fun operate(buffer: NoiseBuffer, vararg data: Float): NoiseBuffer

}