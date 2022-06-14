package com.github.polyrocketmatt.peak.buffer.simulation.data

import com.github.polyrocketmatt.peak.buffer.simulation.Simulation
import com.github.polyrocketmatt.peak.buffer.simulation.algorithms.particle.HydraulicParticleErosion
import com.github.polyrocketmatt.peak.types.NoiseEvaluator
import com.github.polyrocketmatt.peak.types.NoiseUtils
import com.github.polyrocketmatt.peak.types.bounded.PerlinNoise

/**
 * Data class that holds all parameters for a hydraulic erosion simulation.
 */
data class HydraulicSimulationData(
    val seed: Int = 0,
    val iterations: Int = 1000000,
    val size: Int = 1,
    val radius: Int = 3,
    val inertia: Float = 0.05f,
    val sedimentCapacityMultiplier: Float = 4.0f,
    val minimalSedimentCapacity: Float = 0.01f,
    val erosionSpeed: Float = 0.3f,
    val downcutting: Float = 1.2f,
    val downcuttingMultiplier: Float = 0.98f,
    val depositSpeed: Float = 0.3f,
    val evaporationSpeed: Float = 0.01f,
    val gravity: Float = 4.0f,
    val maxParticleLifetime: Int = 30,
    val initialWaterVolume: Float = 1.0f,
    val initialSpeed: Float = 1.0f,
    val sedimentCascade: Boolean = true,
    val sedimentTalus: Float = 10.0f,
    val sedimentRoughness: Float = 0.005f,
    val sedimentAbrasion: Float = 0.1f,
    val sedimentSettling: Float = 0.75f,
    val sedimentCellSize: Float = 0.01f,
    val sedimentCascadeIteration: Int = 1,
    val sedimentCascadeRemoval: Float = 0.15f,
    val evaluator: NoiseEvaluator = PerlinNoise(seed, size, size, 0, NoiseUtils.InterpolationMethod.LINEAR)
) : SimulationData() {

    /**
     * Get a new instance of a hydraulic erosion simulator for this data.
     */
    override fun simulator(): Simulation = HydraulicParticleErosion(this)

}