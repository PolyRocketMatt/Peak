package com.github.polyrocketmatt.peak.image

import com.github.polyrocketmatt.game.math.i
import com.github.polyrocketmatt.peak.buffer.AsyncNoiseBuffer2
import org.imgscalr.Scalr
import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB

fun BufferedImage.scale(scaleX: Int, scaleY: Int): BufferedImage {
    val width = this.width
    val height = this.height
    return Scalr.resize(this, Scalr.Method.ULTRA_QUALITY, width * scaleX, height * scaleY, Scalr.OP_GRAYSCALE)
}

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
        fun bufferToImage(buffer: AsyncNoiseBuffer2): BufferedImage {
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
        fun imageToBufferAsync(image: BufferedImage): AsyncNoiseBuffer2 {
            val width = image.width
            val height = image.height
            val buffer = AsyncNoiseBuffer2(width, height)

            for (x in 0 until width) for (z in 0 until height)
                buffer[x][z] = fromGrayscaleColor(image.getRGB(x, z))
            return buffer
        }

        fun scale(image: BufferedImage, scaleX: Int, scaleY: Int): BufferedImage {
            val width = image.width
            val height = image.height
            return Scalr.resize(image, Scalr.Method.ULTRA_QUALITY, width * scaleX, height * scaleY, Scalr.OP_GRAYSCALE)
        }

        private fun toColor(value: Float): Int {
            val rgb = (value * 255.0f).i()
            return Color(rgb, rgb, rgb).rgb
        }

        private fun fromGrayscaleColor(rgb: Int): Float = (rgb and 0xff) / 255.0f

    }

}