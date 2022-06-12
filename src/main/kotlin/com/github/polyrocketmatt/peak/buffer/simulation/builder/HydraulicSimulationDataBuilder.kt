package com.github.polyrocketmatt.peak.buffer.simulation.builder

import com.github.polyrocketmatt.peak.buffer.simulation.data.HydraulicSimulationData
import com.github.polyrocketmatt.peak.buffer.simulation.data.SimulationData
import com.github.polyrocketmatt.peak.math.toRadians
import com.github.polyrocketmatt.peak.types.NoiseEvaluator
import com.github.polyrocketmatt.peak.types.NoiseUtils
import com.github.polyrocketmatt.peak.types.bounded.PerlinNoise
import kotlin.math.tan

class HydraulicSimulationDataBuilder : SimulationDataBuilder {

    private var seed: Int = 0
    private var iterations: Int = 1000000
    private var size: Int = 1
    private var radius: Int = 1
    private var inertia: Float = 0.05f
    private var sedimentCapacityMultiplier: Float = 4.0f
    private var minimalSedimentCapacity: Float = 0.01f
    private var erosionSpeed: Float = 0.3f
    private var depositSpeed: Float = 0.3f
    private var evaporateSpeed: Float = 0.01f
    private var gravity: Float = 4.0f
    private var maxParticleLifetime: Int = 30
    private var initialWaterVolume: Float = 1.0f
    private var initialSpeed: Float = 1.0f
    private var evaluator: NoiseEvaluator = PerlinNoise(seed, size, size, 0, NoiseUtils.InterpolationMethod.LINEAR)
    private var sedimentCascade: Boolean = true
    private var sedimentTalus: Float =  5.0f
    private var sedimentRoughness: Float = 0.005f
    private var sedimentAbrasion: Float = 0.1f
    private var sedimentSettling: Float = 0.75f
    private var sedimentCellSize: Float = 0.01f
    private var sedimentCascadeIteration: Int = 1
    private var sedimentCascadeRemoval: Float = 0.15f

    fun buildSeed(value: Int): HydraulicSimulationDataBuilder {
        this.seed = value
        return this
    }

    fun buildIterations(value: Int): HydraulicSimulationDataBuilder {
        this.iterations = value
        return this
    }

    fun buildSize(value: Int): HydraulicSimulationDataBuilder {
        this.size = value
        return this
    }

    fun buildRadius(value: Int): HydraulicSimulationDataBuilder {
        this.radius = value
        return this
    }

    fun buildInertia(value: Float): HydraulicSimulationDataBuilder {
        this.inertia = value
        return this
    }

    fun buildSedimentCapacityMultiplier(value: Float): HydraulicSimulationDataBuilder {
        this.sedimentCapacityMultiplier = value
        return this
    }

    fun buildMinimalSedimentCapacity(value: Float): HydraulicSimulationDataBuilder {
        this.minimalSedimentCapacity = value
        return this
    }

    fun buildErosionSpeed(value: Float): HydraulicSimulationDataBuilder {
        this.erosionSpeed = value
        return this
    }

    fun buildDepositSpeed(value: Float): HydraulicSimulationDataBuilder {
        this.depositSpeed = value
        return this
    }

    fun buildEvaporateSpeed(value: Float): HydraulicSimulationDataBuilder {
        this.evaporateSpeed = value
        return this
    }

    fun buildGravity(value: Float): HydraulicSimulationDataBuilder {
        this.gravity = value
        return this
    }

    fun buildMaxParticleLifetime(value: Int): HydraulicSimulationDataBuilder {
        this.maxParticleLifetime = value
        return this
    }

    fun buildInitialWaterVolume(value: Float): HydraulicSimulationDataBuilder {
        this.initialWaterVolume = value
        return this
    }

    fun buildInitialSpeed(value: Float): HydraulicSimulationDataBuilder {
        this.initialSpeed = value
        return this
    }

    fun buildEvaluator(value: NoiseEvaluator): HydraulicSimulationDataBuilder {
        this.evaluator = value
        return this
    }

    fun buildCascade(value: Boolean): HydraulicSimulationDataBuilder {
        this.sedimentCascade = value
        return this
    }

    fun buildTalusAngle(value: Float): HydraulicSimulationDataBuilder {
        this.sedimentTalus = value
        return this
    }

    fun buildRoughness(value: Float): HydraulicSimulationDataBuilder {
        this.sedimentRoughness = value
        return this
    }

    fun buildAbrasion(value: Float): HydraulicSimulationDataBuilder {
        this.sedimentAbrasion = value
        return this
    }

    fun buildSettling(value: Float): HydraulicSimulationDataBuilder {
        this.sedimentSettling = value
        return this
    }

    fun buildCellSize(value: Float): HydraulicSimulationDataBuilder {
        this.sedimentCellSize = value
        return this
    }

    fun buildCascadeIterations(value: Int): HydraulicSimulationDataBuilder {
        this.sedimentCascadeIteration = value
        return this
    }

    fun buildCascadeRemoval(value: Float): HydraulicSimulationDataBuilder {
        this.sedimentCascadeRemoval = value
        return this
    }

    override fun build(): SimulationData = HydraulicSimulationData(
        this.seed,
        this.iterations,
        this.size,
        this.radius,
        this.inertia,
        this.sedimentCapacityMultiplier,
        this.minimalSedimentCapacity,
        this.erosionSpeed,
        this.depositSpeed,
        this.evaporateSpeed,
        this.gravity,
        this.maxParticleLifetime,
        this.initialWaterVolume,
        this.initialSpeed,
        evaluator = this.evaluator
    )
}