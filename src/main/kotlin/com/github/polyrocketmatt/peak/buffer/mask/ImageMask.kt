package com.github.polyrocketmatt.peak.buffer.mask

import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
import java.awt.image.BufferedImage

class ImageMask(
    private val mask: BufferedImage
) : Mask {

    override fun mask(buffer: NoiseBuffer) {
        TODO("Not yet implemented")
    }

}