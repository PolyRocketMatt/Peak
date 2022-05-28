package com.github.polyrocketmatt.peak

import com.github.polyrocketmatt.peak.types.NoiseUtils
import com.github.polyrocketmatt.peak.types.simple.FractalNoise
import com.github.polyrocketmatt.peak.types.simple.SimpleNoise
import kotlin.random.Random

fun main(args: Array<String>) {

    val size = 8192
    val noise = FractalNoise(
        Random.nextInt(10000),
        NoiseUtils.InterpolationMethod.QUINTIC,
        8,
        0.01f,
        0.5f,
        2.0f,
        type = SimpleNoise.SimpleNoiseType.SIMPLEX)
}