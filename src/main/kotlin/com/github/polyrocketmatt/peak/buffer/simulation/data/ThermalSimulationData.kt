package com.github.polyrocketmatt.peak.buffer.simulation.data

import com.github.polyrocketmatt.peak.buffer.simulation.AsyncSimulator
import com.github.polyrocketmatt.peak.buffer.simulation.algorithms.ThermalParticleErosion

data class ThermalSimulationData(
    val iterations: Int,
    val talusAngle: Float,
    val cascade: Boolean,
    val cellSize: Float,
    val sedimentRemoval: Float = 0.05f,
    val sedimentRemovalMultiplier: Float = 5.0f,
    val thermalFalloff: Float = 0.98f,
    val roughness: Float = 0.0025f,
    val abrasion: Float = 0.05f,
    val settling: Float = 0.15f,
) : SimulationData() {

    override fun simulator(): AsyncSimulator = ThermalParticleErosion(this)
}