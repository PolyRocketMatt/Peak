package com.github.polyrocketmatt.peak.buffer.mask

import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer
import java.awt.image.BufferedImage

class ImageMask(
    private val mask: BufferedImage
) : Mask {

    override fun mask(buffer: SyncNoiseBuffer): SyncNoiseBuffer {
        TODO("Not yet implemented")
    }

}