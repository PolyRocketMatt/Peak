package com.github.polyrocketmatt.peak.buffer.operator.binary

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.buffer.operator.BinaryBufferOperator

/**
 * Operator that multiplies the first buffer and the second buffer.
 */
class MultiplyOperator : BinaryBufferOperator {

    /**
     *  Multiplies the elements of the first buffer and the second buffer.
     *
     *  @param first: the first buffer
     *  @param second: the second buffer
     *  @param data: no additional data required for this operation
     *  @return a new NoiseBuffer that contains the multiplication of the two buffers
     */
    override fun operate(first: NoiseBuffer, second: NoiseBuffer, vararg data: Float): NoiseBuffer
        = NoiseBuffer(first.array().mapIndexed { x, floats -> floats.mapIndexed { z, fl -> fl * second[x][z] }.toFloatArray() }.toTypedArray())

}