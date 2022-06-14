package com.github.polyrocketmatt.peak.buffer.simulation.data

import com.github.polyrocketmatt.peak.buffer.simulation.Simulation
import com.github.polyrocketmatt.peak.buffer.simulation.algorithms.particle.ThermalParticleErosion

/**
 * Data class that holds all parameters for a thermal erosion simulation.
 */
data class ThermalSimulationData(
    val iterations: Int = 50,
    val talusAngle: Float,
    val sedimentTalusAngle: Float,
    val cascade: Boolean,
    val cellSize: Float,
    val sedimentRemoval: Float = 0.05f,
    val sedimentCarry: Float = 0.05f,
    val sedimentRemovalMultiplier: Float = 5.0f,
    val thermalFalloff: Float = 0.98f,
    val roughness: Float = 0.0025f,
    val abrasion: Float = 0.05f,
    val settling: Float = 0.15f,
    val depositBelow: Float = 0.5f,
    val sedimentParticleLifetime: Int = 50
) : SimulationData() {

    /**
     * Get a new instance of a thermal erosion simulator for this data.
     */
    override fun simulator(): Simulation = ThermalParticleErosion(this)
}