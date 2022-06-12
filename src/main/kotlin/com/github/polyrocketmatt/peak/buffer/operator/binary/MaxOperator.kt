package com.github.polyrocketmatt.peak.buffer.operator.binary

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer3
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
    override fun operate(first: NoiseBuffer2, second: NoiseBuffer2, vararg data: Any): NoiseBuffer2 =
        first.mapIndexed { x, y, fl -> max(fl, second[x][y]) }

    /**
     *  Takes the maximum of the elements of the first and second buffer.
     *
     *  @param first: the first buffer to perform the operation on
     *  @param second: the second buffer to perform the operation on
     *  @param data: no additional data required for this operation
     *  @return a new NoiseBuffer that contains the maximum of the first and second buffer
     */
    override fun operate(first: NoiseBuffer3, second: NoiseBuffer3, vararg data: Any): NoiseBuffer3 =
        first.mapIndexed { x, y, z, fl -> max(fl, second[x][y][z]) }

}