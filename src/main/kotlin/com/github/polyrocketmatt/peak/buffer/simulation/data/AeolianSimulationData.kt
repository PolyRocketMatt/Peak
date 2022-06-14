package com.github.polyrocketmatt.peak.buffer.simulation.data

import com.github.polyrocketmatt.game.Vec3f
import com.github.polyrocketmatt.peak.buffer.simulation.Simulation
import com.github.polyrocketmatt.peak.buffer.simulation.algorithms.particle.AeolianParticleErosion
import com.github.polyrocketmatt.peak.types.NoiseEvaluator

/**
 * Data class that holds all parameters for aeolian erosion simulation.
 */
data class AeolianSimulationData (
    val seed: Int,
    val iterations: Int = 100000,
    val size: Int,
    val inertia: Float = 0.05f,
    val gravity: Float = 0.5f,
    val suspension: Float = 0.2f,
    val abrasion: Float = 0.1f,
    val roughness: Float = 0.005f,
    val settling: Float = 0.25f,
    val maxParticleLifetime: Int = 30,
    val initialSpeed: Vec3f = Vec3f(-1f, 0f, 1f).normalized().scalarMultiplication(2f),
    val initialSediment: Float = 0.00001f,
    val evaluator: NoiseEvaluator,
) : SimulationData() {

    /**
     * Get a new instance of an aeolian erosion simulator for this data.
     */
    override fun simulator(): Simulation = AeolianParticleErosion(this)

}