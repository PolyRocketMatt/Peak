package com.github.polyrocketmatt.peak.math

import com.github.polyrocketmatt.game.Vec2f
import com.github.polyrocketmatt.game.math.sqrt
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class PoissonDiscSampling {

    companion object {
        private const val pi = 3.141592653589793

        private fun ceilToInt(value: Float): Int = if ((value.toInt()) < value) (value.toInt() + 1) else value.toInt()

        fun sample(radius: Float, samplesBeforeRejection: Int, size: Int): List<Vec2f> {
            val cellSize = radius / 2.0f.sqrt()
            val arrSize = ceilToInt(size / cellSize)
            val grid = Array(arrSize) { IntArray(arrSize) }
            val points: java.util.ArrayList<FloatArray> = arrayListOf()
            val spawnPoints: java.util.ArrayList<FloatArray> = arrayListOf()

            spawnPoints.add(floatArrayOf(size * 0.5f, size * 0.5f))

            while (spawnPoints.isNotEmpty()) {
                val spawnIndex = Random.nextInt(spawnPoints.size)
                val center = spawnPoints[spawnIndex]
                var accepted = false

                for (i in 0..samplesBeforeRejection) {
                    val angle = Math.random() * pi * 2.0f
                    val multiplier = radius + Random.nextFloat()  * (2.0f * radius - radius)
                    val dir = floatArrayOf((sin(angle) * multiplier).toFloat(), (cos(angle) * multiplier).toFloat())
                    val candidate = floatArrayOf(center[0] + dir[0], center[1] + dir[1])

                    if (isValidPoissonSample(candidate, size, cellSize, radius, points, grid)) {
                        points.add(candidate)
                        spawnPoints.add(candidate)

                        val gridIndexX = (candidate[0] / cellSize).toInt()
                        val gridIndexY = (candidate[1] / cellSize).toInt()

                        grid[gridIndexX][gridIndexY] = points.size
                        accepted = true

                        break
                    }
                }

                if (!accepted)
                    spawnPoints.removeAt(spawnIndex)
            }

            return points.map { floats -> Vec2f(floats[0], floats[1]) }
        }

        private fun isValidPoissonSample(
            candidate: FloatArray, size: Int, cellSize: Float, radius: Float,
            points: java.util.ArrayList<FloatArray>, grid: Array<IntArray>
        ): Boolean {
            val cX = candidate[0]
            val cY = candidate[1]

            if (cX >= 0.0f && cX <= size && cY >= 0.0f && cY <= size) {
                val cellX = (cX / cellSize).toInt()
                val cellY = (cY / cellSize).toInt()

                val searchStartX = Integer.max(0, cellX - 2)
                val searchEndX = Integer.min(cellX + 2, grid.size - 1)
                val searchStartY = Integer.max(0, cellY - 2)
                val searchEndY = Integer.min(cellY + 2, grid[0].size - 1)

                for (x in searchStartX..searchEndX) {
                    for (y in searchStartY..searchEndY) {
                        val pointIndex = grid[x][y] - 1

                        if (pointIndex != -1) {
                            val point = points[pointIndex]
                            val vector = arrayOf(candidate[0] - point[0], candidate[1] - point[1])
                            val sqDist = vector[0] * vector[0] + vector[1] * vector[1]

                            if (sqDist < radius * radius)
                                return false
                        }
                    }
                }

                return true
            }

            return false
        }
    }

}