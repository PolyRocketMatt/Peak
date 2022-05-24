package com.github.polyrocketmatt.peak.image

import com.github.polyrocketmatt.game.math.i
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB

class ImageUtils {

    companion object {

        fun bufferToImage(buffer: NoiseBuffer): BufferedImage {
            val width = buffer.width()
            val height = buffer.height()
            val image = BufferedImage(width, height, TYPE_INT_RGB)

            for (x in 0 until width) for (z in 0 until height)
                image.setRGB(x, z, toColor(buffer[x][z]))

            return image
        }

        private fun toColor(value: Float): Int {
            val rgb = (value * 255.0f).i()
            return Color(rgb, rgb, rgb).rgb
        }

    }

}