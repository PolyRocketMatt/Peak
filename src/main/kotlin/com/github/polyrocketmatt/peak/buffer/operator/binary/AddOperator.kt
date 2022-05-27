package com.github.polyrocketmatt.peak.buffer.operator.binary

import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.operator.BinaryBufferOperator

/**
 * Operator that adds the first buffer and the second buffer.
 */
class AddOperator : BinaryBufferOperator {

    /**
     *  Adds the elements of the first buffer and the second buffer.
     *
     *  @param first: the first buffer to perform the operation on
     *  @param second: the second buffer to perform the operation on
     *  @param data: no additional data required for this operation
     *  @return a new NoiseBuffer that contains the addition of the two buffers
     */
    override fun operate(first: SyncNoiseBuffer2, second: SyncNoiseBuffer2, vararg data: Float): SyncNoiseBuffer2 = first.mapIndexed { x, y, fl -> fl + second[x][y] }

    /**
     *  Adds the elements of the first buffer and the second buffer.
     *
     *  @param first: the first buffer to perform the operation on
     *  @param second: the second buffer to perform the operation on
     *  @param data: no additional data required for this operation
     *  @return a new NoiseBuffer that contains the addition of the two buffers
     */
    override fun operate(first: SyncNoiseBuffer3, second: SyncNoiseBuffer3, vararg data: Float): SyncNoiseBuffer3 = second.mapIndexed { x, y, z, fl -> fl + second[x][y][z] }

}