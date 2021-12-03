package com.github.polyrocketmatt.peak.noise

import com.github.polyrocketmatt.game.Vec2f
import com.github.polyrocketmatt.game.math.i
import com.github.polyrocketmatt.game.math.intPow
import com.github.polyrocketmatt.game.math.matrix.FloatMatrix2
import com.github.polyrocketmatt.peak.Noise
import com.github.polyrocketmatt.peak.NoiseBuffer
import com.github.polyrocketmatt.peak.NoiseType
import java.lang.IllegalArgumentException
import kotlin.math.floor
import kotlin.random.Random

data class PolynomialBuffer(
    var size: Int = 1,
    var octaves: Int = 6,
    var lacunarity: Float = 2.0f
) : NoiseBuffer()

class PolynomialNoise(buffer: PolynomialBuffer) : Noise {

    private val size = buffer.size
    private val terrain: Array<FloatArray>

    init {
        val octaves = buffer.octaves
        val persistence = buffer.lacunarity

        val map = Array(size) { FloatArray(size) { 0.0f } }
        val genHMapPair = genHMap(size, octaves)
        val h = genHMapPair.first
        val minRes = genHMapPair.second
        var res = size
        var step = floor(size / minRes).toInt()
        var changeCell = true
        var amp = persistence

        var h00 = 0.0f
        var h01: Float
        var h10: Float
        var h11: Float
        var dX = 0.0f
        var dY = 0.0f
        var a = 0.0f

        for (i in 0 until octaves) {
            val delta = 1.0f / res
            var xRel = 0.0f

            for (x in 0 until size) {
                var yRel = 0.0f
                val x2 = xRel * xRel
                val smoothX = 3.0f * x2 - 2.0f * xRel * x2

                for (y in 0 until size) {
                    val y2 = yRel * yRel
                    val smoothY = 3.0f * y2 - 2.0f * yRel * y2
                    val diagTerm = xRel * yRel - smoothX * yRel - smoothY * xRel

                    if (changeCell) {
                        val idx0 = (x / res) * step
                        val idy0 = (y / res) * step
                        val idx1 = idx0 + step
                        val idy1 = idy0 + step

                        h00 = h[idx0][idy0]
                        h01 = h[idx0][idy1]
                        h10 = h[idx1][idy0]
                        h11 = h[idx1][idy1]

                        dX = h10 - h00
                        dY = h01 - h00
                        a = dX - h11 + h01

                        changeCell = false
                    }

                    val dH = h00 + smoothX * dX + smoothY * dY + a * diagTerm

                    map[x][y] += amp * dH
                    yRel += delta

                    if (yRel >= 1) {
                        changeCell = true
                        yRel = 0.0f
                    }
                }

                xRel += delta

                if (xRel >= 1) {
                    changeCell = true
                    xRel = 0.0f
                }
            }

            res = floor(res / 2.0f).toInt()
            step = floor(res / minRes).toInt()
            amp /= persistence
        }

        terrain = FloatMatrix2(map).normalize()
    }

    override fun getType(): NoiseType = NoiseType.POLYNOMIAL

    override fun noise(pos: Vec2f): Float = noise(pos.x, pos.y)

    override fun noise(x: Float, y: Float, vararg floats: Float): Float {
        val iX = x.i()
        val iY = y.i()

        if (iX !in 0 until size || iY !in 0 until size)
            throw IllegalArgumentException("Sample coordinate for polynomial noise should be within size bounds!")

        return terrain[iX][iY]
    }

    /**
     * Wtf is this doing...
     */
    private fun genHMap(size: Int, octaves: Int): Pair<Array<FloatArray>, Float> {
        val minRes = size / 2.0f.intPow(octaves - 1)
        val hMapSize = floor(size / minRes).toInt() + 1
        val h = Array(hMapSize) { FloatArray(hMapSize) { Random.nextFloat() } }

        val rIdx = hMapSize - 1

        //  Top
        for (x in 0 until hMapSize)
            h[x][0] = Random.nextFloat()

        //  Left
        for (y in 0 until hMapSize)
            h[0][y] = Random.nextFloat()

        //  Bottom
        for (x in 0 until hMapSize)
            h[x][rIdx] = Random.nextFloat()

        //  Right
        for (y in 0 until hMapSize)
            h[rIdx][y] = Random.nextFloat()

        h[0][0] = Random.nextFloat()
        h[rIdx][rIdx] = Random.nextFloat()
        h[0][rIdx] = Random.nextFloat()
        h[rIdx][0] = Random.nextFloat()

        return Pair(h, minRes)
    }
}