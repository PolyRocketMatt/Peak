package com.github.polyrocketmatt.peak.buffer.simulation.data

import com.github.polyrocketmatt.peak.buffer.simulation.AsyncSimulator
import com.github.polyrocketmatt.peak.buffer.simulation.particle.HydraulicParticleErosion
import com.github.polyrocketmatt.peak.types.NoiseEvaluator
import com.github.polyrocketmatt.peak.types.NoiseUtils
import com.github.polyrocketmatt.peak.types.bounded.PerlinNoise

data class HydraulicSimulationData(
    val seed: Int = 0,
    val iterations: Int = 1000000,
    val size: Int = 1,
    val radius: Int = 1,
    val inertia: Float = 0.05f,
    val sedimentCapacityMultiplier: Float = 4.0f,
    val minimalSedimentCapacity: Float = 0.01f,
    val erosionSpeed: Float = 0.3f,
    val depositSpeed: Float = 0.3f,
    val evaporateSpeed: Float = 0.01f,
    val gravity: Float = 4.0f,
    val maxParticleLifetime: Int = 30,
    val initialWaterVolume: Float = 1.0f,
    val initialSpeed: Float = 1.0f,
    val talusSlippageAngle: Float = 70.0f,
    val talusDepositionMultiplier: Float = 0.1f,
    val evaluator: NoiseEvaluator = PerlinNoise(seed, size, size, 0, NoiseUtils.InterpolationMethod.LINEAR)
) : SimulationData() {

    override fun simulator(): AsyncSimulator = HydraulicParticleErosion(this)
}