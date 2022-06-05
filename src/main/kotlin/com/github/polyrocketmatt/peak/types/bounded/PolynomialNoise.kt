package com.github.polyrocketmatt.peak.types.bounded

import com.github.polyrocketmatt.game.math.i
import com.github.polyrocketmatt.game.math.smoothStep
import com.github.polyrocketmatt.game.math.smootherStep
import com.github.polyrocketmatt.peak.annotation.Ref
import com.github.polyrocketmatt.peak.exception.NoiseException
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer2
import com.github.polyrocketmatt.peak.types.NoiseUtils
import kotlin.random.Random

/**
 * Polynomial noise implementation.
 */
@Ref("https://arxiv.org/pdf/1610.03525.pdf")
class PolynomialNoise(
    private val seed: Int,
    width: Int,
    height: Int,
    private val octaves: Int,
    private val gain: Float,
    private val interpolation: NoiseUtils.InterpolationMethod,
) : BoundedNoise(width, height, 0) {

    private var buffer: SyncNoiseBuffer2 = SyncNoiseBuffer2(width, height)
    private val rng: Random = Random(seed)

    init { recalculate() }

    fun buffer(): SyncNoiseBuffer2 = this.buffer

    /**
     * Recalculate the complex noise.
     */
    fun recalculate() {
        this.buffer = polynomial(SyncNoiseBuffer2(width, height))
    }

    /**
     * Sample noise at the given x- and y-coordinates.
     *
     * @param nX: the x-coordinate to sample noise from
     * @param nY: the y-coordinate to sample noise from
     * @return the sampled noise at the given x- and y-coordinates
     */
    override fun noise(nX: Float, nY: Float): Float = buffer[nX.i()][nY.i()]

    /**
     * Sample noise at the given x-, y- and z-coordinates.
     *
     * @param nX: the x-coordinate to sample noise from
     * @param nY: the y-coordinate to sample noise from
     * @param nZ: the z-coordinate to sample noise from
     * @return the sampled noise at the given x- and z-coordinates
     */
    override fun noise(nX: Float, nY: Float, nZ: Float): Float = buffer[nX.i()][nY.i()]

    private fun interpolate(x: Float) = when(interpolation) {
        NoiseUtils.InterpolationMethod.LINEAR -> x
        NoiseUtils.InterpolationMethod.HERMITE -> x.smoothStep()
        NoiseUtils.InterpolationMethod.QUINTIC -> x.smootherStep()
    }

    private fun polynomial(buffer: SyncNoiseBuffer2): SyncNoiseBuffer2 {
        if (width != height)
            throw NoiseException("Width must equal height for polynomial height")

        val boundary = SyncNoiseBuffer2(width, height, rng)

        var deltaX = 0.0f
        var deltaY = 0.0f
        var a = 0.0f
        var h00 = 0.0f
        var h01: Float
        var h10: Float
        var h11: Float
        var changeCell = true
        var amplitude = 1.0f
        var res = this.width

        for (i in 0 until octaves) {
            val delta = 1.0f / res
            var idx = 0
            var idx1 = 0
            var xRel = 0f

            for (x in 0 until this.width) {
                var idy = 0
                var idy1 = 0
                var yRel = 0f
                val smoothX = interpolate(xRel)

                if (x % res == 0) {
                    idx = idx1
                    idx1 += res - 1
                    changeCell = true
                }
                for (y in 0 until this.width) {
                    val smoothY = interpolate(yRel)

                    if (y % res == 0) {
                        idy = idy1
                        idy1 += res - 1
                        changeCell = true
                    }

                    if (changeCell) {
                        h00 = boundary[idx][idy]
                        h01 = boundary[idx][idy1]
                        h10 = boundary[idx1][idy]
                        h11 = boundary[idx1][idy1]
                        deltaX = h10 - h00
                        deltaY = h01 - h00
                        a = deltaX - h11 + h01
                        changeCell = false
                    }
                    //
                    val dh = h00 + smoothX * deltaX + smoothY * deltaY +
                            a * (xRel * yRel - smoothX * yRel - smoothY * xRel)
                    buffer[x][y] += amplitude * dh
                    yRel += delta
                    if (yRel >= 1f) yRel = 0f
                }

                xRel += delta
                if (xRel >= 1f) xRel = 0f
            }

            res /= 2
            amplitude /= (1.0f / gain)
        }

        return buffer
    }

    override fun type(): BoundedNoiseType = BoundedNoiseType.POLYNOMIAL

    override fun clone(): PolynomialNoise = PolynomialNoise(seed, width, height, octaves, gain, interpolation)

}