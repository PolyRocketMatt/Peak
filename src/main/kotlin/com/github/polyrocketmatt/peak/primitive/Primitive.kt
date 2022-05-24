package com.github.polyrocketmatt.peak.primitive

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
import java.awt.image.BufferedImage

interface Primitive {

    fun recompute()

    fun buffer(): NoiseBuffer

    fun image(): BufferedImage

}