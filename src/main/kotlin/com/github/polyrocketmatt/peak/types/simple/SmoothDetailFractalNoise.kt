package com.github.polyrocketmatt.peak.types.simple

import com.github.polyrocketmatt.game.math.fastAbs
import com.github.polyrocketmatt.peak.types.NoiseUtils
import com.github.polyrocketmatt.peak.types.bounded.PerlinNoise

class SmoothDetailFractalNoise(
    private val seed: Int,
    private val interpolation: NoiseUtils.InterpolationMethod,
    private val octaves: Int,
    private val scale: Float,
    private val gain: Float,
    private val lacunarity: Float,
    private val fractalType: FractalType = FractalType.FBM,
    private val type: SimpleNoiseType = SimpleNoiseType.SIMPLEX,
    private val skippedOctaves: IntArray
) : SimpleNoise() {

    private val noise: SimpleNoise = when(type) {
        SimpleNoiseType.VALUE       -> ValueNoise(seed, interpolation)
        SimpleNoiseType.SIMPLEX     -> SimplexNoise(seed)

        else                        -> ValueNoise(seed, interpolation)
    }

    /**
     * Sample noise at the given x- and y-coordinates.
     *
     * @param nX: the x-coordinate to sample noise from
     * @param nY: the y-coordinate to sample noise from
     * @return the sampled noise at the given x- and y-coordinates
     */
    override fun noise(nX: Float, nY: Float): Float {
        val x = nX * 0.01f
        val y = nY * 0.01f

        when(fractalType) {
            FractalType.FBM       -> {
                var sum = noise.noise(x * scale, y * scale)
                var amp = 1.0f
                var sX = x
                var sY = y

                for (i in 1 until octaves) {
                    if (!skippedOctaves.contains(i - 1)) {
                        sX *= lacunarity
                        sY *= lacunarity

                        amp *= gain
                        sum += noise.noise(sX * scale, sY * scale) * amp
                    }
                }

                return sum * fractalBounding
            }

            FractalType.BILLOW    -> {
                var sum = noise.noise(x * scale, y * scale).fastAbs() * 2 - 1
                var amp = 1.0f
                var sX = x
                var sY = y

                for (i in 1 until octaves) {
                    if (!skippedOctaves.contains(i - 1)) {
                        sX *= lacunarity
                        sY *= lacunarity

                        amp *= gain
                        sum += (noise.noise(sX * scale, sY * scale).fastAbs() * 2 - 1) * amp
                    }
                }

                return sum * fractalBounding
            }

            FractalType.RIGID     -> {
                var sum = noise.noise(x * scale, y * scale).fastAbs() * 2 - 1
                var amp = 1.0f
                var sX = x
                var sY = y

                for (i in 1 until octaves) {
                    if (!skippedOctaves.contains(i - 1)) {
                        sX *= lacunarity
                        sY *= lacunarity

                        amp *= gain
                        sum += (1.0f - noise.noise(sX * scale, sY * scale).fastAbs())
                    }
                }

                return sum * fractalBounding
            }
        }
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
        val x = nX * 0.01f
        val y = nY * 0.01f
        val z = nZ * 0.01f

        when(fractalType) {
            FractalType.FBM         -> {
                var sum = noise.noise(x * scale, y * scale, z * scale)
                var amp = 1.0f
                var sX = x
                var sY = y
                var sZ = z

                for (i in 1 until octaves) {
                    amp *= gain

                    if (!skippedOctaves.contains(i - 1)) {
                        sX *= lacunarity
                        sY *= lacunarity
                        sZ *= lacunarity
                        sum += noise.noise(sX * scale, sY * scale, sZ * scale) * amp
                    }
                }

                return sum * fractalBounding
            }

            FractalType.BILLOW      -> {
                var sum = noise.noise(x * scale, y * scale, z * scale).fastAbs() * 2 - 1
                var amp = 1.0f
                var sX = x
                var sY = y
                var sZ = z

                for (i in 1 until octaves) {
                    amp *= gain

                    if (!skippedOctaves.contains(i - 1)) {
                        sX *= lacunarity
                        sY *= lacunarity
                        sZ *= lacunarity
                        sum += (noise.noise(sX * scale, sY * scale, sZ * scale).fastAbs() * 2 - 1) * amp
                    }
                }

                return sum * fractalBounding
            }

            FractalType.RIGID       -> {
                var sum = 1.0f - noise.noise(x * scale, y * scale, z * scale).fastAbs()
                var amp = 1.0f
                var sX = x
                var sY = y
                var sZ = z

                for (i in 1 until octaves) {
                    amp *= gain

                    if (!skippedOctaves.contains(i - 1)) {
                        sX *= lacunarity
                        sY *= lacunarity
                        sZ *= lacunarity
                        sum += (1.0f - noise.noise(sX * scale, sY * scale, sZ * scale).fastAbs()) * amp
                    }
                }

                return sum * fractalBounding
            }
        }
    }

    override fun calculateFractalBounding() {
        var amp = gain
        var ampFractal = 1.0f

        for (i in 1 until octaves) {
            if (!skippedOctaves.contains(i)) {
                ampFractal += amp
            }

            amp *= gain
        }

        fractalBounding = 1.0f / ampFractal
    }

    override fun type(): SimpleNoiseType = when(type) {
        SimpleNoiseType.VALUE           -> SimpleNoiseType.VALUE_SKIPPED_FRACTAL
        SimpleNoiseType.SIMPLEX         -> SimpleNoiseType.SIMPLEX_SKIPPED_FRACTAL
        else                            -> SimpleNoiseType.VALUE_SKIPPED_FRACTAL
    }

    override fun clone(): FractalNoise = FractalNoise(seed, interpolation, octaves, scale, gain, lacunarity, fractalType, type)

}