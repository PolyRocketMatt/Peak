package com.github.polyrocketmatt.peak.buffer.operator.binary

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.operator.BinaryBufferOperator
import kotlin.math.min

/**
 * Operator that takes the minimum of the two buffers.
 */
class MinOperator : BinaryBufferOperator {

    /**
     *  Takes the minimum of the elements of the first and second buffer.
     *
     *  @param first: the first buffer to perform the operation on
     *  @param second: the second buffer to perform the operation on
     *  @param data: no additional data required for this operation
     *  @return a new NoiseBuffer that contains the minimum of the first and second buffer
     */
    override fun operate(first: NoiseBuffer2, second: NoiseBuffer2, vararg data: Float): NoiseBuffer2 =
        first.mapIndexed { x, y, fl -> min(fl, second[x][y]) }

    /**
     *  Takes the minimum of the elements of the first and second buffer.
     *
     *  @param first: the first buffer to perform the operation on
     *  @param second: the second buffer to perform the operation on
     *  @param data: no additional data required for this operation
     *  @return a new NoiseBuffer that contains the minimum of the first and second buffer
     */
    override fun operate(first: NoiseBuffer3, second: NoiseBuffer3, vararg data: Float): NoiseBuffer3 =
        first.mapIndexed { x, y, z, fl -> min(fl, second[x][y][z]) }

}