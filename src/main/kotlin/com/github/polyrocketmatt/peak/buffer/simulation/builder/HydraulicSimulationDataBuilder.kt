package com.github.polyrocketmatt.peak.buffer.simulation.builder

import com.github.polyrocketmatt.peak.buffer.simulation.data.HydraulicSimulationData
import com.github.polyrocketmatt.peak.buffer.simulation.data.SimulationData
import com.github.polyrocketmatt.peak.types.NoiseEvaluator
import com.github.polyrocketmatt.peak.types.NoiseUtils
import com.github.polyrocketmatt.peak.types.bounded.PerlinNoise

/**
 * Builder for hydraulic simulation data.
 */
class HydraulicSimulationDataBuilder : SimulationDataBuilder {

    private var seed: Int = 0
    private var iterations: Int = 1000000
    private var size: Int = 1
    private var radius: Int = 1
    private var inertia: Float = 0.05f
    private var sedimentCapacityMultiplier: Float = 4.0f
    private var minimalSedimentCapacity: Float = 0.01f
    private var erosionSpeed: Float = 0.3f
    private var downcutting: Float = 1.2f
    private var downcuttingMultiplier: Float = 0.98f
    private var depositSpeed: Float = 0.3f
    private var evaporationSpeed: Float = 0.01f
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

    /**
     * Build a seed value.
     *
     * @param value: the seed value to build
     * @return this builder with the set seed value
     */
    fun buildSeed(value: Int): HydraulicSimulationDataBuilder {
        this.seed = value
        return this
    }

    /**
     * Build an iterations value.
     *
     * @param value: the iterations value to build
     * @return this builder with the set iterations value
     */
    fun buildIterations(value: Int): HydraulicSimulationDataBuilder {
        this.iterations = value
        return this
    }

    /**
     * Build a size value.
     *
     * @param value: the size value to build
     * @return this builder with the set size value
     */
    fun buildSize(value: Int): HydraulicSimulationDataBuilder {
        this.size = value
        return this
    }

    /**
     * Build a radius value.
     *
     * @param value: the radius value to build
     * @return this builder with the set radius value
     */
    fun buildRadius(value: Int): HydraulicSimulationDataBuilder {
        this.radius = value
        return this
    }

    /**
     * Build an inertia value.
     *
     * @param value: the inertia value to build
     * @return this builder with the set inertia value
     */
    fun buildInertia(value: Float): HydraulicSimulationDataBuilder {
        this.inertia = value
        return this
    }

    /**
     * Build a sediment capacity multiplier value.
     *
     * @param value: the sediment capacity multiplier value to build
     * @return this builder with the set sediment capacity multiplier value
     */
    fun buildSedimentCapacityMultiplier(value: Float): HydraulicSimulationDataBuilder {
        this.sedimentCapacityMultiplier = value
        return this
    }

    /**
     * Build a minimal sediment capacity value.
     *
     * @param value: the minimal sediment capacity value to build
     * @return this builder with the set minimal sediment capacity value
     */
    fun buildMinimalSedimentCapacity(value: Float): HydraulicSimulationDataBuilder {
        this.minimalSedimentCapacity = value
        return this
    }

    /**
     * Build an erosion speed value.
     *
     * @param value: the erosion speed value to build
     * @return this builder with the set erosion speed value
     */
    fun buildErosionSpeed(value: Float): HydraulicSimulationDataBuilder {
        this.erosionSpeed = value
        return this
    }

    /**
     * Build a downcutting value.
     *
     * @param value: the downcutting value to build
     * @return this builder with the set downcutting value
     */
    fun buildDowncutting(value: Float): HydraulicSimulationDataBuilder {
        this.downcutting = value
        return this
    }

    /**
     * Build a downcutting multiplier value.
     *
     * @param value: the downcutting multiplier value to build
     * @return this builder with the set downcutting multiplier value
     */
    fun buildDowncuttingMultiplier(value: Float): HydraulicSimulationDataBuilder {
        this.downcuttingMultiplier = value
        return this
    }

    /**
     * Build a deposit speed value.
     *
     * @param value: the deposit speed value to build
     * @return this builder with the set deposit value
     */
    fun buildDepositSpeed(value: Float): HydraulicSimulationDataBuilder {
        this.depositSpeed = value
        return this
    }

    /**
     * Build an evaporation speed value.
     *
     * @param value: the evaporation speed value to build
     * @return this builder with the set evaporation speed value
     */
    fun buildEvaporationSpeed(value: Float): HydraulicSimulationDataBuilder {
        this.evaporationSpeed = value
        return this
    }

    /**
     * Build a gravity value.
     *
     * @param value: the gravity value to build
     * @return this builder with the set gravity value
     */
    fun buildGravity(value: Float): HydraulicSimulationDataBuilder {
        this.gravity = value
        return this
    }

    /**
     * Build a maximum particle lifetime value.
     *
     * @param value: the maximum particle lifetime value to build
     * @return this builder with the set maximum particle lifetime value
     */
    fun buildMaxParticleLifetime(value: Int): HydraulicSimulationDataBuilder {
        this.maxParticleLifetime = value
        return this
    }

    /**
     * Build an initial water volume value.
     *
     * @param value: the initial water volume value to build
     * @return this builder with the set initial water volume value
     */
    fun buildInitialWaterVolume(value: Float): HydraulicSimulationDataBuilder {
        this.initialWaterVolume = value
        return this
    }

    /**
     * Build an initial speed value.
     *
     * @param value: the initial speed value to build
     * @return this builder with the set initial speed value
     */
    fun buildInitialSpeed(value: Float): HydraulicSimulationDataBuilder {
        this.initialSpeed = value
        return this
    }

    /**
     * Build an evaluator value.
     *
     * @param value: the evaluator value to build
     * @return this builder with the set evaluator value
     */
    fun buildEvaluator(value: NoiseEvaluator): HydraulicSimulationDataBuilder {
        this.evaluator = value
        return this
    }

    /**
     * Build a cascade value.
     *
     * @param value: the cascade value to build
     * @return this builder with the set cascade value
     */
    fun buildCascade(value: Boolean): HydraulicSimulationDataBuilder {
        this.sedimentCascade = value
        return this
    }

    /**
     * Build a talus angle value.
     *
     * @param value: the talus angle value to build
     * @return this builder with the set talus angle value
     */
    fun buildTalusAngle(value: Float): HydraulicSimulationDataBuilder {
        this.sedimentTalus = value
        return this
    }

    /**
     * Build a roughness value.
     *
     * @param value: the roughness value to build
     * @return this builder with the set roughness value
     */
    fun buildRoughness(value: Float): HydraulicSimulationDataBuilder {
        this.sedimentRoughness = value
        return this
    }

    /**
     * Build an abrasion value.
     *
     * @param value: the abrasion value to build
     * @return this builder with the set abrasion value
     */
    fun buildAbrasion(value: Float): HydraulicSimulationDataBuilder {
        this.sedimentAbrasion = value
        return this
    }

    /**
     * Build a settling value.
     *
     * @param value: the settling value to build
     * @return this builder with the set settling value
     */
    fun buildSettling(value: Float): HydraulicSimulationDataBuilder {
        this.sedimentSettling = value
        return this
    }

    /**
     * Build a cell size value.
     *
     * @param value: the cell size value to build
     * @return this builder with the set cell size value
     */
    fun buildCellSize(value: Float): HydraulicSimulationDataBuilder {
        this.sedimentCellSize = value
        return this
    }

    /**
     * Build a cascade iterations value.
     *
     * @param value: the cascade iterations value to build
     * @return this builder with the set cascade iterations value
     */
    fun buildCascadeIterations(value: Int): HydraulicSimulationDataBuilder {
        this.sedimentCascadeIteration = value
        return this
    }

    /**
     * Build a cascade removal value.
     *
     * @param value: the cascade removal value to build
     * @return this builder with the set cascade removal value
     */
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
        this.downcutting,
        this.downcuttingMultiplier,
        this.depositSpeed,
        this.evaporationSpeed,
        this.gravity,
        this.maxParticleLifetime,
        this.initialWaterVolume,
        this.initialSpeed,
        this.sedimentCascade,
        this.sedimentTalus,
        this.sedimentRoughness,
        this.sedimentAbrasion,
        this.sedimentSettling,
        this.sedimentCellSize,
        this.sedimentCascadeIteration,
        this.sedimentCascadeRemoval,
        this.evaluator
    )
}