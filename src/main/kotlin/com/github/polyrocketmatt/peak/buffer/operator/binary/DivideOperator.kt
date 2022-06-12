package com.github.polyrocketmatt.peak.buffer.operator.binary

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer3
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
    override  fun operate(first: NoiseBuffer2, second: NoiseBuffer2, vararg data: Any): NoiseBuffer2 =
        first.mapIndexed { x, y, fl -> kotlin.run {
            val divisor = second[x][y]
            if (fl == 0.0f || divisor == 0.0f)
                0.0f
            else
                fl / divisor
        } }

    /**
     *  Divides the elements of the first buffer with the elements of the second buffer.
     *
     *  @param first: the first buffer to perform the operation on
     *  @param second: the second buffer to perform the operation on
     *  @param data: no additional data required for this operation
     *  @return a new NoiseBuffer that contains the addition of the two buffers
     */
    override  fun operate(first: NoiseBuffer3, second: NoiseBuffer3, vararg data: Any): NoiseBuffer3 =
        first.mapIndexed { x, y, z, fl -> kotlin.run {
            val divisor = second[x][y][z]
            if (fl == 0.0f || divisor == 0.0f)
                0.0f
            else
                fl / divisor
        } }

}