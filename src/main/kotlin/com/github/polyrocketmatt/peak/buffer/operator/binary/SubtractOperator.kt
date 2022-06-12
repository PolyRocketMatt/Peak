package com.github.polyrocketmatt.peak.buffer.operator.binary

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer3
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
    override fun operate(first: NoiseBuffer2, second: NoiseBuffer2, vararg data: Any): NoiseBuffer2 =
        first.mapIndexed { x, y, fl -> fl - second[x][y] }

    /**
     *  Subtracts the elements of the second buffer from the first buffer.
     *
     *  @param first: the first buffer to perform the operation on
     *  @param second: the second buffer to perform the operation on
     *  @param data: no additional data required for this operation
     *  @return a new NoiseBuffer that contains the subtraction of the second buffer from the first buffer
     */
    override fun operate(first: NoiseBuffer3, second: NoiseBuffer3, vararg data: Any): NoiseBuffer3 =
        first.mapIndexed { x, y, z, fl -> fl - second[x][y][z] }

}