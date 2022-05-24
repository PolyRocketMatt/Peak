package com.github.polyrocketmatt.peak.buffer.operator.binary

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.buffer.operator.BinaryBufferOperator
import kotlin.math.pow

/**
 * Operator that raises the first buffer to the second buffer (element-wise exponentiation).
 */
class CombinedPowerOperator : BinaryBufferOperator {

    /**
     *  Raises the elements of the first buffer to the power of the second buffer.
     *
     *  @param first: the first buffer
     *  @param second: the second buffer
     *  @param data: no additional data required for this operation
     *  @return a new NoiseBuffer that contains the elements of the first buffer raised to the power of the second buffer
     */
    override fun operate(first: NoiseBuffer, second: NoiseBuffer, vararg data: Float): NoiseBuffer =
        NoiseBuffer(first.array().mapIndexed { x, floats -> floats.mapIndexed { z, fl -> fl.pow(second[x][z]) }.toFloatArray() }.toTypedArray())

}