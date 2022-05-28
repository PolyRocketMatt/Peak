package com.github.polyrocketmatt.peak.types.complex

import com.github.polyrocketmatt.peak.types.NoiseUtils
import com.github.polyrocketmatt.peak.types.cellular.CellularNoise
import com.github.polyrocketmatt.peak.types.simple.FractalNoise
import com.github.polyrocketmatt.peak.types.simple.SimpleNoise
import kotlin.math.pow

class BuyaNoise(
    private val seed: Int,
    private val scale: Float
) : ComplexNoise() {

    private val multiCellular1 = CellularNoise(seed, 0.5f * scale, CellularNoise.DistanceType.EUCLIDEAN, CellularNoise.ReturnType.DISTANCE_2_MUL)
    private val multiCellular2 = CellularNoise(seed + 31, 0.5f * scale, CellularNoise.DistanceType.EUCLIDEAN, CellularNoise.ReturnType.DISTANCE_2_MUL)
    private val fractal = FractalNoise(seed, NoiseUtils.InterpolationMethod.HERMITE, 12, scale * 5f, 0.5f, 1.95f, SimpleNoise.FractalType.FBM, SimpleNoise.SimpleNoiseType.SIMPLEX_FRACTAL)
    private val blendFractal = FractalNoise(seed + 31, NoiseUtils.InterpolationMethod.HERMITE, 12, scale * 10f, 0.5f, 1.95f, SimpleNoise.FractalType.FBM, SimpleNoise.SimpleNoiseType.SIMPLEX_FRACTAL)

    /**
     * Sample noise at the given x- and y-coordinates.
     *
     * @param nX: the x-coordinate to sample noise from
     * @param nY: the y-coordinate to sample noise from
     * @return the sampled noise at the given x- and y-coordinates
     */
    override fun noise(nX: Float, nY: Float): Float {
        val fractalValue = fractal.noise(nX, nY)
        val cellularValue = multiCellular1.noise(nX, nY) * multiCellular2.noise(nX, nY)

        return (fractalValue * cellularValue + blendFractal.noise(nX, nY) * 0.1f).pow(2f)
    }

    /**
     * Sample noise at the given x-, y- and z-coordinates.
     *
     * @param nX: the x-coordinate to sample noise from
     * @param nY: the y-coordinate to sample noise from
     * @param nZ: the z-coordinate to sample noise from
     * @return the sampled noise at the given x- and z-coordinates
     */
    override fun noise(nX: Float, nY: Float, nZ: Float): Float {
        val fractalValue = fractal.noise(nX, nY, nZ)
        val cellularValue = multiCellular1.noise(nX, nY, nZ) * multiCellular2.noise(nX, nY, nZ)

        return (fractalValue * cellularValue + blendFractal.noise(nX, nY, nZ) * 0.1f).pow(2f)
    }

    override fun type(): ComplexNoiseType = ComplexNoiseType.BUYA

    override fun clone(): BuyaNoise = BuyaNoise(seed, scale)

}