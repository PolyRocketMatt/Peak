package com.github.polyrocketmatt.peak.buffer

import com.github.polyrocketmatt.peak.exception.BufferInitException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.random.Random
import kotlin.test.assertEquals

class AsyncBuffer2Test {

    @Test
    fun testDefaultConstructor() {
        val buffer = AsyncNoiseBuffer2(Array(64) { FloatArray(64) }, 16)

        assertEquals(16, buffer.threadCount())
        assertEquals(64, buffer.width())
        assertEquals(64, buffer.height())
        assertEquals(0.0f, buffer.min())
        assertEquals(0.0f, buffer.max())
    }

    @Test
    fun testSizedConstructor() {
        val buffer = AsyncNoiseBuffer2(64, 32, 16)

        assertEquals(8, buffer.threadCount())
        assertEquals(64, buffer.width())
        assertEquals(32, buffer.height())
        assertEquals(0.0f, buffer.min())
        assertEquals(0.0f, buffer.max())
    }

    @Test
    fun testSizedRandomConstructor() {
        val buffer = AsyncNoiseBuffer2(64, 32, 16, Random(0))

        buffer[0][0] = 0.0f
        buffer[0][1] = 1.0f

        assertEquals(8, buffer.threadCount())
        assertEquals(64, buffer.width())
        assertEquals(32, buffer.height())
        assertEquals(0.0f, buffer.min())
        assertEquals(1.0f, buffer.max())
    }

    @Test
    fun testSizedFixedConstructor() {
        val buffer = AsyncNoiseBuffer2(64, 32, 16, 0.27204f)

        assertEquals(8, buffer.threadCount())
        assertEquals(64, buffer.width())
        assertEquals(32, buffer.height())
        assertEquals(0.27204f, buffer.min())
        assertEquals(0.27204f, buffer.max())
    }

    @Test
    fun testSquareSizedConstructor() {
        val buffer = AsyncNoiseBuffer2(64, 16)

        assertEquals(16, buffer.threadCount())
        assertEquals(64, buffer.width())
        assertEquals(64, buffer.height())
        assertEquals(0.0f, buffer.min())
        assertEquals(0.0f, buffer.max())
    }

    @Test
    fun testSquareSizedRandomConstructor() {
        val buffer = AsyncNoiseBuffer2(64, 16, Random(0))

        buffer[0][0] = 0.0f
        buffer[0][1] = 1.0f

        assertEquals(16, buffer.threadCount())
        assertEquals(64, buffer.width())
        assertEquals(64, buffer.height())
        assertEquals(0.0f, buffer.min())
        assertEquals(1.0f, buffer.max())
    }

    @Test
    fun testSquareSizedFixedConstructor() {
        val buffer = AsyncNoiseBuffer2(64, 16, 0.27204f)

        assertEquals(16, buffer.threadCount())
        assertEquals(64, buffer.width())
        assertEquals(64, buffer.height())
        assertEquals(0.27204f, buffer.min())
        assertEquals(0.27204f, buffer.max())
    }

    @Test
    fun testFailingWidthConstructors() {
        assertThrows<NegativeArraySizeException> { AsyncNoiseBuffer2(-1, 16, 1) }
        assertThrows<NegativeArraySizeException> { AsyncNoiseBuffer2(-1, 16, 1, Random(0)) }
        assertThrows<NegativeArraySizeException> { AsyncNoiseBuffer2(-1, 16, 1, 0.0f) }
    }

    @Test
    fun testFailingHeightConstructors() {
        assertThrows<NegativeArraySizeException> { AsyncNoiseBuffer2(16, -1, 1) }
        assertThrows<NegativeArraySizeException> { AsyncNoiseBuffer2(16, -1, 1, Random(0)) }
        assertThrows<NegativeArraySizeException> { AsyncNoiseBuffer2(16, -1, 1, 0.0f) }
    }

    @Test
    fun testFailingSizeConstructors() {
        assertThrows<NegativeArraySizeException> { AsyncNoiseBuffer2(-1, 1) }
        assertThrows<NegativeArraySizeException> { AsyncNoiseBuffer2(-1, 1, Random(0)) }
        assertThrows<NegativeArraySizeException> { AsyncNoiseBuffer2(-1, 1, 0.0f) }
    }

    @Test
    fun testFailingChunkSizeConstructors() {
        assertThrows<BufferInitException> { AsyncNoiseBuffer2(16, 16, 0) }
        assertThrows<BufferInitException> { AsyncNoiseBuffer2(16, 16, 0, Random(0)) }
        assertThrows<BufferInitException> { AsyncNoiseBuffer2(16, 16, 0, 0.0f) }

        assertThrows<BufferInitException> { AsyncNoiseBuffer2(16, 0) }
        assertThrows<BufferInitException> { AsyncNoiseBuffer2(16, 0, Random(0)) }
        assertThrows<BufferInitException> { AsyncNoiseBuffer2(16, 0, 0.0f) }
    }

}