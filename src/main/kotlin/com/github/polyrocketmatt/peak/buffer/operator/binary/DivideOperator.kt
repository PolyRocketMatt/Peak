package com.github.polyrocketmatt.peak.buffer.operator.binary

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.buffer.operator.BinaryBufferOperator

/**
 * Operator that divides the first buffer with the second buffer.
 */
class DivideOperator : BinaryBufferOperator {

    /**
     *  Divides the elements of the first buffer with the elements of the second buffer.
     *
     *  @param first: the first buffer to perform the operation on
     *  @param second: the second buffer to perform the operation on
     *  @param data: no additional data required for this operation
     *  @return a new NoiseBuffer that contains the addition of the two buffers
     */
    override fun operate(first: NoiseBuffer, second: NoiseBuffer, vararg data: Float): NoiseBuffer =
        NoiseBuffer(first.array().mapIndexed { x, floats ->
            floats.mapIndexed { z, fl ->
                if (fl == 0.0f)
                    0.0f
                else if (second[x][z] == 0.0f)
                    0.0f
                else
                    fl / second[x][z]
            }.toFloatArray()
        }.toTypedArray())

}