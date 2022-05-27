package com.github.polyrocketmatt.peak.buffer.operator.binary

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.operator.BinaryBufferOperator
import kotlin.math.pow

/**
 * Operator that raises the first buffer to the second buffer (element-wise exponentiation).
 */
class CombinedPowerOperator : BinaryBufferOperator {

    /**
     *  Raises the elements of the first buffer to the power of the second buffer.
     *
     *  @param first: the first buffer to perform the operation on
     *  @param second: the second buffer to perform the operation on
     *  @param data: no additional data required for this operation
     *  @return a new NoiseBuffer that contains the elements of the first buffer raised to the power of the second buffer
     */
    override fun operate(first: NoiseBuffer2, second: NoiseBuffer2, vararg data: Float): NoiseBuffer2 =
        first.mapIndexed { x, y, fl -> fl.pow(second[x][y]) }

    /**
     *  Raises the elements of the first buffer to the power of the second buffer.
     *
     *  @param first: the first buffer to perform the operation on
     *  @param second: the second buffer to perform the operation on
     *  @param data: no additional data required for this operation
     *  @return a new NoiseBuffer that contains the elements of the first buffer raised to the power of the second buffer
     */
    override fun operate(first: NoiseBuffer3, second: NoiseBuffer3, vararg data: Float): NoiseBuffer3 =
        first.mapIndexed { x, y, z, fl -> fl.pow(second[x][y][z]) }

}