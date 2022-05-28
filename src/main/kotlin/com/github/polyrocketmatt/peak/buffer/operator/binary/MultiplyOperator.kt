package com.github.polyrocketmatt.peak.buffer.operator.binary

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.operator.BinaryBufferOperator

/**
 * Operator that multiplies the first buffer and the second buffer.
 */
class MultiplyOperator : BinaryBufferOperator {

    /**
     *  Multiplies the elements of the first buffer and the second buffer.
     *
     *  @param first: the first buffer to perform the operation on
     *  @param second: the second buffer to perform the operation on
     *  @param data: no additional data required for this operation
     *  @return a new NoiseBuffer that contains the multiplication of the two buffers
     */
    override fun operate(first: NoiseBuffer2, second: NoiseBuffer2, vararg data: Float): NoiseBuffer2 =
        first.mapIndexed { x, y, fl -> fl * second[x][y] }

    /**
     *  Multiplies the elements of the first buffer and the second buffer.
     *
     *  @param first: the first buffer to perform the operation on
     *  @param second: the second buffer to perform the operation on
     *  @param data: no additional data required for this operation
     *  @return a new NoiseBuffer that contains the multiplication of the two buffers
     */
    override fun operate(first: NoiseBuffer3, second: NoiseBuffer3, vararg data: Float): NoiseBuffer3 =
        first.mapIndexed { x, y, z, fl -> fl * second[x][y][z] }

}