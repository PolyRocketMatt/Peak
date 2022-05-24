package com.github.polyrocketmatt.peak.noise.buffer.operator

import com.github.polyrocketmatt.peak.noise.buffer.NoiseBuffer

@FunctionalInterface
interface UnaryBufferOperator {

    fun operate(buffer: NoiseBuffer, vararg data: Float): NoiseBuffer

}