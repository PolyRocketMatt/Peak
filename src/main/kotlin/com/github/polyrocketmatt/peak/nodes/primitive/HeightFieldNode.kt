package com.github.polyrocketmatt.peak.nodes.primitive

import com.github.polyrocketmatt.game.math.matrix.FloatMatrix2
import com.github.polyrocketmatt.peak.nodes.PrimitiveNode

/**
 * The Height-Field-node is used to describe a height-field.
 * It contains a two-dimensional array storing values between
 * 0 and 1 representing the height of a certain particle. It
 * stores the field inside a float-matrix from GAME.
 */
class HeightFieldNode(val size: Int) : PrimitiveNode {

    private val field = FloatMatrix2(Array(size) { FloatArray(size) { 0.0f } })

}