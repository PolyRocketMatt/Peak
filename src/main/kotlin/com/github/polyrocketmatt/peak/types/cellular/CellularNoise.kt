package com.github.polyrocketmatt.peak.types.cellular

import com.github.polyrocketmatt.game.math.fastAbs
import com.github.polyrocketmatt.game.math.fastRound
import com.github.polyrocketmatt.peak.types.NoiseEvaluator
import com.github.polyrocketmatt.peak.types.NoiseUtils.Companion.CELL_2D
import com.github.polyrocketmatt.peak.types.NoiseUtils.Companion.CELL_3D
import com.github.polyrocketmatt.peak.types.NoiseUtils.Companion.hash2d
import com.github.polyrocketmatt.peak.types.NoiseUtils.Companion.hash3d

/**
 * Cellular noise implementation.
 */
class CellularNoise(
    private val seed: Int,
    private val frequency: Float,
    private val distanceType: DistanceType,
    private val returnType: ReturnType,
) : NoiseEvaluator {

    enum class DistanceType { EUCLIDEAN, MANHATTAN, NATURAL }
    enum class ReturnType { DISTANCE, DISTANCE_2, DISTANCE_2_ADD, DISTANCE_2_SUB, DISTANCE_2_MUL, DISTANCE_2_DIV }

    /**
     * Sample noise at the given x- and z-coordinates.
     *
     * @param nX: the x-coordinate to sample noise from
     * @param nY: the z-coordinate to sample noise from
     * @return the sampled noise at the given x- and z-coordinates
     */
    override fun noise(nX: Float, nY: Float): Float = singleCellular2Edge(nX * frequency, nY * frequency)

    /**
     * Sample noise at the given x-, y- and z-coordinates.
     *
     * @param nX: the x-coordinate to sample noise from
     * @param nY: the y-coordinate to sample noise from
     * @param nZ: the z-coordinate to sample noise from
     * @return the sampled noise at the given x- and z-coordinates
     */
    override fun noise(nX: Float, nY: Float, nZ: Float): Float = singleCellular2Edge(nX * frequency, nY * frequency, nZ * frequency)

    private fun singleCellular2Edge(x: Float, y: Float): Float {
        val xr = x.fastRound()
        val yr = y.fastRound()

        var distance = 999999f
        var distance2 = 999999f

        when (distanceType) {
            DistanceType.EUCLIDEAN      -> {
                for (xi in xr - 1..xr + 1) {
                    for (yi in yr - 1..yr + 1) {
                        val (x1, y1) = CELL_2D[hash2d(seed, xi, yi) and 255]
                        val vecX = xi - x + x1
                        val vecY = yi - y + y1
                        val newDistance = vecX * vecX + vecY * vecY
                        distance2 = distance2.coerceAtMost(newDistance).coerceAtLeast(distance)
                        distance = distance.coerceAtMost(newDistance)
                    }
                }
            }

            DistanceType.MANHATTAN      -> {
                for (xi in xr - 1..xr + 1) {
                    for (yi in yr - 1..yr + 1) {
                        val (x1, y1) = CELL_2D[hash2d(seed, xi, yi) and 255]
                        val vecX = xi - x + x1
                        val vecY = yi - y + y1
                        val newDistance = vecX.fastAbs() + vecY.fastAbs()
                        distance2 = distance2.coerceAtMost(newDistance).coerceAtLeast(distance)
                        distance = distance.coerceAtMost(newDistance)
                    }
                }
            }

            DistanceType.NATURAL        -> {
                for (xi in xr - 1..xr + 1) {
                    for (yi in yr - 1..yr + 1) {
                        val (x1, y1) = CELL_2D[hash2d(seed, xi, yi) and 255]
                        val vecX = xi - x + x1
                        val vecY = yi - y + y1
                        val newDistance = vecX.fastAbs() + vecY.fastAbs() + (vecX * vecX + vecY * vecY)
                        distance2 = distance2.coerceAtMost(newDistance).coerceAtLeast(distance)
                        distance = distance.coerceAtMost(newDistance)
                    }
                }
            }
        }

        return when (returnType) {
            ReturnType.DISTANCE         -> distance
            ReturnType.DISTANCE_2       -> distance2 - 1
            ReturnType.DISTANCE_2_ADD   -> distance2 + distance - 1
            ReturnType.DISTANCE_2_SUB   -> distance2 - distance - 1
            ReturnType.DISTANCE_2_MUL   -> distance2 * distance - 1
            ReturnType.DISTANCE_2_DIV   -> distance2 / distance - 1
        }
    }

    private fun singleCellular2Edge(x: Float, y: Float, z: Float): Float {
        val xr = x.fastRound()
        val yr = y.fastRound()
        val zr = z.fastRound()

        var distance = 999999f
        var distance2 = 999999f

        when (distanceType) {
            DistanceType.EUCLIDEAN      -> {
                for (xi in xr - 1..xr + 1) {
                    for (yi in yr - 1..yr + 1) {
                        for (zi in zr - 1..zr + 1) {
                            val (x1, y1, z1) = CELL_3D[hash3d(seed, xi, yi, zi) and 255]
                            val vecX = xi - x + x1
                            val vecY = yi - y + y1
                            val vecZ = zi - z + z1
                            val newDistance = vecX * vecX + vecY * vecY + vecZ * vecZ
                            distance2 = distance2.coerceAtMost(newDistance).coerceAtLeast(distance)
                            distance = distance.coerceAtMost(newDistance)
                        }
                    }
                }
            }

            DistanceType.MANHATTAN      -> {
                for (xi in xr - 1..xr + 1) {
                    for (yi in yr - 1..yr + 1) {
                        for (zi in zr - 1..zr + 1) {
                            val (x1, y1, z1) = CELL_3D[hash3d(seed, xi, yi, zi) and 255]
                            val vecX = xi - x + x1
                            val vecY = yi - y + y1
                            val vecZ = zi - z + z1
                            val newDistance = vecX.fastAbs() + vecY.fastAbs() + vecZ.fastAbs()
                            distance2 = distance2.coerceAtMost(newDistance).coerceAtLeast(distance)
                            distance = distance.coerceAtMost(newDistance)
                        }
                    }
                }
            }

            DistanceType.NATURAL        -> {
                for (xi in xr - 1..xr + 1) {
                    for (yi in yr - 1..yr + 1) {
                        for (zi in zr - 1..zr + 1) {
                            val (x1, y1, z1) = CELL_3D[hash3d(seed, xi, yi, zi) and 255]
                            val vecX = xi - x + x1
                            val vecY = yi - y + y1
                            val vecZ = zi - z + z1
                            val newDistance =
                                vecX.fastAbs() + vecY.fastAbs() + vecZ.fastAbs() + (vecX * vecX + vecY * vecY + vecZ * vecZ)
                            distance2 = distance2.coerceAtMost(newDistance).coerceAtLeast(distance)
                            distance = distance.coerceAtMost(newDistance)
                        }
                    }
                }
            }
        }

        return when (returnType) {
            ReturnType.DISTANCE         -> distance
            ReturnType.DISTANCE_2       -> distance2 - 1
            ReturnType.DISTANCE_2_ADD   -> distance2 + distance - 1
            ReturnType.DISTANCE_2_SUB   -> distance2 - distance - 1
            ReturnType.DISTANCE_2_MUL   -> distance2 * distance - 1
            ReturnType.DISTANCE_2_DIV   -> distance2 / distance2 - 1
        }
    }

}