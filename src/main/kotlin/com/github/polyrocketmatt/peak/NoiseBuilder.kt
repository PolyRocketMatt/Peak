package com.github.polyrocketmatt.peak

import com.github.polyrocketmatt.peak.noise.PerlinBuffer
import com.github.polyrocketmatt.peak.noise.PolynomialBuffer
import com.github.polyrocketmatt.peak.noise.SimplexBuffer

fun PerlinBuffer.setSeed(bufferSeed: Long): PerlinBuffer {
    this.seed = bufferSeed

    return this
}

fun PerlinBuffer.setOctaves(bufferOctaves: Int): PerlinBuffer {
    this.tables = bufferOctaves

    return this
}

fun SimplexBuffer.setSeed(bufferSeed: Long): SimplexBuffer {
    this.seed = bufferSeed

    return this
}

fun PolynomialBuffer.setSize(bufferSize: Int): PolynomialBuffer {
    this.size = bufferSize

    return this
}

fun PolynomialBuffer.setOctaves(bufferOctaves: Int): PolynomialBuffer {
    this.octaves = bufferOctaves

    return this
}

fun PolynomialBuffer.setPersistence(bufferPersistence: Float): PolynomialBuffer {
    this.lacunarity = bufferPersistence

    return this
}

class NoiseBuilder {

    companion object {

        fun newBuffer(type: NoiseType): NoiseBuffer = when (type) {
            NoiseType.PERLIN -> PerlinBuffer()
            NoiseType.SIMPLEX -> SimplexBuffer()
            NoiseType.POLYNOMIAL -> PolynomialBuffer()
        }

    }


}