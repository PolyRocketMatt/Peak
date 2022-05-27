package com.github.polyrocketmatt.peak.image

import com.github.polyrocketmatt.game.math.i
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB

/**
 * Contains various utilities to construct and manipulate
 * images from noise buffers.
 */
class ImageUtils {

    companion object {

        /**
         * Creates a grayscale image from a buffer.
         *
         * @param buffer: the noise buffer to create a grayscale image of
         * @return the grayscale image corresponding to the buffer
         */
        fun bufferToImage(buffer: NoiseBuffer2): BufferedImage {
            val width = buffer.width()
            val height = buffer.height()
            val image = BufferedImage(width, height, TYPE_INT_RGB)
            for (x in 0 until width) for (z in 0 until height)
                image.setRGB(x, z, toColor(buffer[x][z]))
            return image
        }

        /**
         * Create a noise buffer from a grayscale image.
         *
         * @param image: the grayscale image to create a noise buffer of
         * @return the noise buffer corresponding to the buffer
         */
        fun imageToBuffer(image: BufferedImage): NoiseBuffer2 {
            val width = image.width
            val height = image.height
            val buffer = NoiseBuffer2(width, height)

            for (x in 0 until width) for (z in 0 until height)
                buffer[x][z] = fromGrayscaleColor(image.getRGB(x, z))
            return buffer
        }

        private fun toColor(value: Float): Int {
            val rgb = (value * 255.0f).i()
            return Color(rgb, rgb, rgb).rgb
        }

        private fun fromGrayscaleColor(rgb: Int): Float = (rgb and 0xff) / 255.0f

    }

}