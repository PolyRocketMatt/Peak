package com.github.polyrocketmatt.peak.buffer.operator.binary

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.buffer.operator.BinaryBufferOperator

/**
 * Operator that subtracts the second buffer from the first one.
 */
class SubtractOperator : BinaryBufferOperator {

    /**
     *  Subtracts the elements of the second buffer from the first buffer.
     *
     *  @param first: the first buffer to perform the operation on
     *  @param second: the second buffer to perform the operation on
     *  @param data: no additional data required for this operation
     *  @return a new NoiseBuffer that contains the subtraction of the second buffer from the first buffer
     */
    override fun operate(first: NoiseBuffer, second: NoiseBuffer, vararg data: Float): NoiseBuffer
        = NoiseBuffer(first.array().mapIndexed { x, floats -> floats.mapIndexed { z, fl -> fl - second[x][z] }.toFloatArray() }.toTypedArray())

}