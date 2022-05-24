package com.github.polyrocketmatt.peak.buffer.operator.binary

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.buffer.operator.BinaryBufferOperator
import kotlin.math.max

/**
 * Operator that takes the maximum of the two buffers.
 */
class MaxOperator : BinaryBufferOperator {

    /**
     *  Takes the maximum of the elements of the first and second buffer.
     *
     *  @param first: the first buffer to perform the operation on
     *  @param second: the second buffer to perform the operation on
     *  @param data: no additional data required for this operation
     *  @return a new NoiseBuffer that contains the maximum of the first and second buffer
     */
    override fun operate(first: NoiseBuffer, second: NoiseBuffer, vararg data: Float): NoiseBuffer
        = NoiseBuffer(first.array().mapIndexed { x, floats -> floats.mapIndexed { z, fl -> max(fl, second[x][z]) }.toFloatArray() }.toTypedArray())

}