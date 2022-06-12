package com.github.polyrocketmatt.peak

import com.github.polyrocketmatt.peak.buffer.AsyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2

class ArrayUtils {

    companion object {

        fun linearize(buffer: NoiseBuffer2): FloatArray {
            val array = FloatArray(buffer.width() * buffer.height())
            for (x in 0 until buffer.width()) for (y in 0 until buffer.height())
                array[x * buffer.height() + y] = buffer[x][y]
            return array
        }

        fun linearize(buffer: Array<FloatArray>): FloatArray {
            val width = buffer.size
            val height = buffer[0].size
            val array = FloatArray(width * height)
            for (x in 0 until width) for (y in 0 until height)
                array[x * height + y] = buffer[x][y]
            return array
        }

        fun deLinearize(array: FloatArray, width: Int, height: Int): AsyncNoiseBuffer2 {
            val buffer = AsyncNoiseBuffer2(width, height, 128)
            for ((idx, value) in array.withIndex()) {
                val x = idx / height
                val y = idx % height
                buffer[x][y] = value
            }
            return buffer
        }

        fun deLinearizeSimple(array: FloatArray, width: Int, height: Int): Array<FloatArray> {
            val buffer = Array(width) { FloatArray(height) { 0.0f } }
            for ((idx, value) in array.withIndex()) {
                val x = idx / height
                val y = idx % height
                buffer[x][y] = value
            }
            return buffer
        }

    }

}