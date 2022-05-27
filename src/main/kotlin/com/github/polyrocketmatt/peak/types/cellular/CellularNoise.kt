package com.github.polyrocketmatt.peak.types.cellular

import com.github.polyrocketmatt.game.math.fastAbs
import com.github.polyrocketmatt.game.math.fastRound
import com.github.polyrocketmatt.peak.types.NoiseEvaluator
import com.github.polyrocketmatt.peak.types.NoiseUtils.Companion.CELL_2D
import com.github.polyrocketmatt.peak.types.NoiseUtils.Companion.CELL_3D
import com.github.polyrocketmatt.peak.types.NoiseUtils.Companion.hash2d
import com.github.polyrocketmatt.peak.types.NoiseUtils.Companion.hash3d
import com.github.polyrocketmatt.peak.types.NoiseUtils.Companion.valCoord2d
import com.github.polyrocketmatt.peak.types.NoiseUtils.Companion.valCoord3d

class CellularNoise(
    private val seed: Int,
    private val cellularType: CellularType,
    private val distanceType: DistanceType,
    private val returnType: ReturnType,
    private val lookup: NoiseEvaluator?
) : NoiseEvaluator {

    enum class CellularType { VORONOI, EDGE_BASED }
    enum class DistanceType { EUCLIDEAN, MANHATTAN, NATURAL }
    enum class ReturnType { CELL_VALUE, NOISE_LOOKUP, DISTANCE, DISTANCE_2, DISTANCE_2_ADD, DISTANCE_2_SUB, DISTANCE_2_MUL, DISTANCE_2_DIV }

    /**
     * Sample noise at the given x- and z-coordinates.
     *
     * @param nX: the x-coordinate to sample noise from
     * @param nY: the z-coordinate to sample noise from
     * @return the sampled noise at the given x- and z-coordinates
     */
    override fun noise(nX: Float, nY: Float): Float = when (cellularType) {
            CellularType.VORONOI        -> singleCellular(nX, nY)
            CellularType.EDGE_BASED     -> singleCellular2Edge(nX, nY)
    }

    /**
     * Sample noise at the given x-, y- and z-coordinates.
     *
     * @param nX: the x-coordinate to sample noise from
     * @param nY: the y-coordinate to sample noise from
     * @param nZ: the z-coordinate to sample noise from
     * @return the sampled noise at the given x- and z-coordinates
     */
    override fun noise(nX: Float, nY: Float, nZ: Float): Float = when (cellularType) {
        CellularType.VORONOI        -> singleCellular(nX, nY, nZ)
        CellularType.EDGE_BASED     -> singleCellular2Edge(nX, nY, nZ)
    }

    private fun singleCellular(x: Float, y: Float): Float {
        val xr = x.fastRound()
        val yr = y.fastRound()

        var distance = 999999f
        var xc = 0
        var yc = 0

        when (distanceType) {
            DistanceType.EUCLIDEAN -> {
                for (xi in xr - 1..xr + 1) {
                    for (yi in yr - 1..yr + 1) {
                        val (x1, y1) = CELL_2D[hash2d(seed, xi, yi) and 255]
                        val vecX = xi - x + x1
                        val vecY = yi - y + y1
                        val newDistance = vecX * vecX + vecY * vecY
                        if (newDistance < distance) {
                            distance = newDistance
                            xc = xi
                            yc = yi
                        }
                    }
                }
            }

            DistanceType.MANHATTAN -> {
                for (xi in xr - 1..xr + 1) {
                    for (yi in yr - 1..yr + 1) {
                        val (x1, y1) = CELL_2D[hash2d(seed, xi, yi) and 255]
                        val vecX = xi - x + x1
                        val vecY = yi - y + y1
                        val newDistance = vecX.fastAbs() + vecY.fastAbs()
                        if (newDistance < distance) {
                            distance = newDistance
                            xc = xi
                            yc = yi
                        }
                    }
                }
            }

            DistanceType.NATURAL -> {
                for (xi in xr - 1..xr + 1) {
                    for (yi in yr - 1..yr + 1) {
                        val (x1, y1) = CELL_2D[hash2d(seed, xi, yi) and 255]
                        val vecX = xi - x + x1
                        val vecY = yi - y + y1
                        val newDistance = vecX.fastAbs() + vecY.fastAbs() + (vecX * vecX + vecY * vecY)
                        if (newDistance < distance) {
                            distance = newDistance
                            xc = xi
                            yc = yi
                        }
                    }
                }
            }
        }

        return when (returnType) {
            ReturnType.CELL_VALUE       -> valCoord2d(0, xc, yc)
            ReturnType.NOISE_LOOKUP     -> {
                val vec = CELL_2D[hash2d(seed, xc, yc) and 255]
                lookup?.noise(xc + vec.x, yc + vec.y) ?: 0.0f
            }
            ReturnType.DISTANCE         -> distance - 1
            else                                -> 0.0f
        }
    }

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
            ReturnType.DISTANCE_2           -> distance2 - 1
            ReturnType.DISTANCE_2_ADD       -> distance2 + distance - 1
            ReturnType.DISTANCE_2_SUB       -> distance2 - distance - 1
            ReturnType.DISTANCE_2_MUL       -> distance2 * distance - 1
            ReturnType.DISTANCE_2_DIV       -> distance2 / distance - 1
            else                                -> 0.0f
        }
    }

    private fun singleCellular(x: Float, y: Float, z: Float): Float {
        val xr = x.fastRound()
        val yr = y.fastRound()
        val zr = z.fastRound()

        var distance = 999999f
        var xc = 0
        var yc = 0
        var zc = 0
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
                            if (newDistance < distance) {
                                distance = newDistance
                                xc = xi
                                yc = yi
                                zc = zi
                            }
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
                            if (newDistance < distance) {
                                distance = newDistance
                                xc = xi
                                yc = yi
                                zc = zi
                            }
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
                            val newDistance = vecX.fastAbs() + vecY.fastAbs() + vecZ.fastAbs() + (vecX * vecX + vecY * vecY + vecZ * vecZ)
                            if (newDistance < distance) {
                                distance = newDistance
                                xc = xi
                                yc = yi
                                zc = zi
                            }
                        }
                    }
                }
            }
        }

        return when (returnType) {
            ReturnType.CELL_VALUE       -> valCoord3d(0, xc, yc, zc)
            ReturnType.DISTANCE         -> distance - 1
            ReturnType.NOISE_LOOKUP     -> {
                val vec = CELL_3D[hash3d(seed, xc, yc, zc) and 255]

                lookup?.noise(xc + vec.x, yc + vec.y,  zc + vec.z) ?: 0.0f
            }

            else                        -> 0f
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
            ReturnType.DISTANCE_2       -> distance2 - 1
            ReturnType.DISTANCE_2_ADD   -> distance2 + distance - 1
            ReturnType.DISTANCE_2_SUB   -> distance2 - distance - 1
            ReturnType.DISTANCE_2_MUL   -> distance2 * distance - 1
            ReturnType.DISTANCE_2_DIV   -> distance2 / distance2 - 1
            else                        -> 0.0f
        }
    }

}