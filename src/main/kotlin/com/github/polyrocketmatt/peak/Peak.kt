package com.github.polyrocketmatt.peak

import com.github.polyrocketmatt.peak.buffer.AsyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.SyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.operator.*
import com.github.polyrocketmatt.peak.types.NoiseUtils
import com.github.polyrocketmatt.peak.types.simple.FractalNoise
import com.github.polyrocketmatt.peak.types.simple.SimpleNoise
import kotlinx.coroutines.runBlocking
import java.io.File
import javax.imageio.ImageIO
import kotlin.random.Random

fun main(args: Array<String>) {

    runBlocking {
        val size = 8192
        val noise = FractalNoise(
            Random.nextInt(10000),
            NoiseUtils.InterpolationMethod.QUINTIC,
            8,
            0.01f,
            0.5f,
            2.0f,
            type = SimpleNoise.SimpleNoiseType.SIMPLEX)
        val syncBuffer = SyncNoiseBuffer2(size)
        val asyncBuffer = AsyncNoiseBuffer2(size, 16, 1.0f)

        var start = System.currentTimeMillis()
        syncBuffer.fill(noise).normalize()
        println("Sync -> Took ${System.currentTimeMillis() - start}ms!")

        start = System.currentTimeMillis()
        asyncBuffer.fill(noise)
        println("Async -> Took ${System.currentTimeMillis() - start}ms!")


        //ImageIO.write(syncBuffer.image(), "png", File("img/test.png"))
    }

}