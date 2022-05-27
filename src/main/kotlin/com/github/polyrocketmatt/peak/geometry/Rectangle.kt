package com.github.polyrocketmatt.peak.geometry

import com.github.polyrocketmatt.game.Vec2i
import kotlin.math.max
import kotlin.math.min

class Rectangle(a: Vec2i, b: Vec2i) : GeometryObject {

    private val min: Vec2i = Vec2i(min(a.iX, b.iX), min(a.iY, b.iY))
    private val max: Vec2i = Vec2i(max(a.iX, b.iX), max(a.iY, b.iY))

    fun center(): Vec2i = (min + max).scalarMultiplication(0.5f)

    override fun mask(): Array<Vec2i> {
        val vectors = arrayListOf<Vec2i>()
        for (x in min.iX .. max.iX) {
            vectors.add(Vec2i(x, min.iY))
            vectors.add(Vec2i(x, max.iY))
        }
        for (y in min.iY .. max.iY) {
            vectors.add(Vec2i(min.iX, y))
            vectors.add(Vec2i(max.iX, y))
        }
        return vectors.toTypedArray()
    }

}