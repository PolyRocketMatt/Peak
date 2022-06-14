package com.github.polyrocketmatt.peak.buffer.simulation.builder

import com.github.polyrocketmatt.game.Vec3f
import com.github.polyrocketmatt.peak.buffer.simulation.data.SimulationData
import com.github.polyrocketmatt.peak.buffer.simulation.data.AeolianSimulationData
import com.github.polyrocketmatt.peak.types.NoiseEvaluator
import com.github.polyrocketmatt.peak.types.NoiseUtils
import com.github.polyrocketmatt.peak.types.bounded.PerlinNoise

/**
 * Builder for aeolian simulation data.
 */
class AeolianSimulationDataBuilder : SimulationDataBuilder {

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

    /**
     * Build a seed value.
     *
     * @param value: the seed value to build
     * @return this builder with the set seed value
     */
    fun buildSeed(value: Int): AeolianSimulationDataBuilder {
        this.seed = value
        return this
    }

    /**
     * Build an iterations value.
     *
     * @param value: the iterations value to build
     * @return this builder with the set iterations value
     */
    fun buildIterations(value: Int): AeolianSimulationDataBuilder {
        this.seed = value
        return this
    }

    /**
     * Build a size value.
     *
     * @param value: the size value to build
     * @return this builder with the set size value
     */
    fun buildSize(value: Int): AeolianSimulationDataBuilder {
        this.seed = value
        return this
    }

    /**
     * Build an inertia value.
     *
     * @param value: the inertia value to build
     * @return this builder with the set inertia value
     */
    fun buildInertia(value: Float): AeolianSimulationDataBuilder {
        this.inertia = value
        return this
    }

    /**
     * Build a gravity value.
     *
     * @param value: the gravity value to build
     * @return this builder with the set gravity value
     */
    fun buildGravity(value: Float): AeolianSimulationDataBuilder {
        this.gravity = value
        return this
    }

    /**
     * Build a suspension value.
     *
     * @param value: the suspension value to build
     * @return this builder with the set suspension value
     */
    fun buildSuspension(value: Float): AeolianSimulationDataBuilder {
        this.suspension = value
        return this
    }

    /**
     * Build an abrasion value.
     *
     * @param value: the abrasion value to build
     * @return this builder with the set abrasion value
     */
    fun buildAbrasion(value: Float): AeolianSimulationDataBuilder {
        this.abrasion = value
        return this
    }

    /**
     * Build a roughness value.
     *
     * @param value: the roughness value to build
     * @return this builder with the set roughness value
     */
    fun buildRoughness(value: Float): AeolianSimulationDataBuilder {
        this.roughness = value
        return this
    }

    /**
     * Build a settling value.
     *
     * @param value: the settling value to build
     * @return this builder with the set settling value
     */
    fun buildSettling(value: Float): AeolianSimulationDataBuilder {
        this.settling = value
        return this
    }

    /**
     * Build a maximum particle lifetime value.
     *
     * @param value: the maximum particle lifetime value to build
     * @return this builder with the set maximum particle lifetime value
     */
    fun buildMaxParticleLifetime(value: Int): AeolianSimulationDataBuilder {
        this.maxParticleLifetime = value
        return this
    }

    /**
     * Build an initial speed value.
     *
     * @param value: the initial speed value to build
     * @return this builder with the set initial speed value
     */
    fun buildInitialSpeed(value: Vec3f): AeolianSimulationDataBuilder {
        this.initialSpeed = value
        return this
    }

    /**
     * Build an initial sediment value.
     *
     * @param value: the initial sediment value to build
     * @return this builder with the set initial sediment value
     */
    fun buildInitialSediment(value: Float): AeolianSimulationDataBuilder {
        this.initialSediment = value
        return this
    }

    /**
     * Build an evaluator value.
     *
     * @param value: the evaluator value to build
     * @return this builder with the set evaluator value
     */
    fun buildEvaluator(value: NoiseEvaluator): AeolianSimulationDataBuilder {
        this.evaluator = value
        return this
    }

    override fun build(): SimulationData = AeolianSimulationData(
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