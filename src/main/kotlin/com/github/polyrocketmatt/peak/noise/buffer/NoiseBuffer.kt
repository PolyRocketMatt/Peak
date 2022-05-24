package com.github.polyrocketmatt.peak.noise.buffer

import com.github.polyrocketmatt.game.math.statistics.max
import com.github.polyrocketmatt.game.math.statistics.min
import com.github.polyrocketmatt.peak.image.ImageUtils
import java.awt.image.BufferedImage
import kotlin.random.Random

class NoiseBuffer(private val buffer: Array<FloatArray>) {

    constructor(sX: Int, sZ: Int) : this(Array(sX) { FloatArray(sZ) { 0.0f } })

    constructor(sX: Int, sZ: Int, rng: Random) : this(Array(sX) { FloatArray(sZ) { rng.nextFloat() } })

    fun width(): Int = buffer.size

    fun height(): Int = buffer[0].size

    fun min(): Float = buffer.minOf { floats -> floats.min() }

    fun max(): Float =  buffer.maxOf { floats -> floats.max() }

    fun image(): BufferedImage = ImageUtils.bufferToImage(this)

    operator fun get(x: Int) = buffer[x]

    fun forEach(action: (Float) -> Unit) { buffer.forEach { floats -> floats.forEach { value -> action(value) } } }

    fun op(action: (Float) -> Float) { buffer.forEachIndexed { x, floats -> floats.forEachIndexed { z, value -> buffer[x][z] = action(value) } } }

    fun array(): Array<FloatArray> = buffer

}