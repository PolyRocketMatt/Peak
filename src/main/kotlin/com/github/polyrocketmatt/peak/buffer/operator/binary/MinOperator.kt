package com.github.polyrocketmatt.peak.buffer.operator.binary

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.buffer.operator.BinaryBufferOperator
import kotlin.math.min

/**
 * Operator that takes the minimum of the two buffers.
 */
class MinOperator : BinaryBufferOperator {

    /**
     *  Takes the minimum of the elements of the first and second buffer.
     *
     *  @param first: the first buffer
     *  @param second: the second buffer
     *  @param data: no additional data required for this operation
     *  @return a new NoiseBuffer that contains the minimum of the first and second buffer
     */
    override fun operate(first: NoiseBuffer, second: NoiseBuffer, vararg data: Float): NoiseBuffer
        = NoiseBuffer(first.array().mapIndexed { x, floats -> floats.mapIndexed { z, fl -> min(fl, second[x][z]) }.toFloatArray() }.toTypedArray())

}