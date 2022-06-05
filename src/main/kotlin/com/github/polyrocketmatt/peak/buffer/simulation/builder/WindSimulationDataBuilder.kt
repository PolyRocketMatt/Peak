package com.github.polyrocketmatt.peak.buffer.simulation.builder

import com.github.polyrocketmatt.game.Vec3f
import com.github.polyrocketmatt.peak.buffer.simulation.data.SimulationData
import com.github.polyrocketmatt.peak.buffer.simulation.data.WindSimulationData
import com.github.polyrocketmatt.peak.types.NoiseEvaluator
import com.github.polyrocketmatt.peak.types.NoiseUtils
import com.github.polyrocketmatt.peak.types.bounded.PerlinNoise

class WindSimulationDataBuilder : SimulationDataBuilder {

    private var seed: Int = 0
    private var iterations: Int = 1000000
    private var size: Int = 1
    private var inertia: Float = 0.05f
    private var gravity: Float = 0.5f
    private var suspension: Float = 0.5f
    private var abrasion: Float = 0.1f
    private var roughness: Float = 0.005f
    private var settling: Float = 0.25f
    private var maxParticleLifetime: Int = 30
    private var initialSpeed: Vec3f = Vec3f(-1f, 0f, 1f).normalized().scalarMultiplication(2f)
    private var initialSediment: Float = 0.00001f
    private var evaluator: NoiseEvaluator = PerlinNoise(this.seed, size, size, 0, NoiseUtils.InterpolationMethod.LINEAR)

    fun buildSeed(value: Int): WindSimulationDataBuilder {
        this.seed = value
        return this
    }

    fun buildIterations(value: Int): WindSimulationDataBuilder {
        this.seed = value
        return this
    }

    fun buildSize(value: Int): WindSimulationDataBuilder {
        this.seed = value
        return this
    }

    fun buildInertia(value: Float): WindSimulationDataBuilder {
        this.inertia = value
        return this
    }

    fun buildGravity(value: Float): WindSimulationDataBuilder {
        this.gravity = value
        return this
    }

    fun buildSuspension(value: Float): WindSimulationDataBuilder {
        this.suspension = value
        return this
    }

    fun buildAbrasion(value: Float): WindSimulationDataBuilder {
        this.abrasion = value
        return this
    }

    fun buildRoughness(value: Float): WindSimulationDataBuilder {
        this.roughness = value
        return this
    }

    fun buildSettling(value: Float): WindSimulationDataBuilder {
        this.settling = value
        return this
    }

    fun buildMaxParticleLifetime(value: Int): WindSimulationDataBuilder {
        this.maxParticleLifetime = value
        return this
    }

    fun buildInitialSpeed(value: Vec3f): WindSimulationDataBuilder {
        this.initialSpeed = value
        return this
    }

    fun buildInitialSediment(value: Float): WindSimulationDataBuilder {
        this.initialSediment = value
        return this
    }

    fun buildEvaluator(value: NoiseEvaluator): WindSimulationDataBuilder {
        this.evaluator = value
        return this
    }

    override fun build(): SimulationData = WindSimulationData(
        this.seed,
        this.iterations,
        this.size,
        this.inertia,
        this.gravity,
        this.suspension,
        this.abrasion,
        this.roughness,
        this.settling,
        this.maxParticleLifetime,
        this.initialSpeed,
        this.initialSediment,
        this.evaluator
    )
}