package com.github.polyrocketmatt.peak.geometry

import com.github.polyrocketmatt.game.Vec2i

interface GeometryObject {

    fun mask(): Array<Vec2i>

}