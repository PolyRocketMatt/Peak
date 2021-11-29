package com.github.polyrocketmatt.peak.noise

import com.github.polyrocketmatt.game.noise.NoiseType
import kotlin.random.Random

data class NoiseDataBuffer(
    val type: NoiseType = NoiseType.PERLIN,
    val size: Int = 0,
    val seed: Long = Random.nextLong(),
    val octaves: Int = 8,
    val scale: Float = 1.0f,
    val persistence: Float = 0.5f,
    val lacunarity: Float = 2.0f,
    val normalize: Boolean = true
)