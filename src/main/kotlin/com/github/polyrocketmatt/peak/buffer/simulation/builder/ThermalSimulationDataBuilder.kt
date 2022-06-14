package com.github.polyrocketmatt.peak.buffer.simulation.builder

import com.github.polyrocketmatt.peak.buffer.simulation.data.ThermalSimulationData

/**
 * Builder for thermal simulation data.
 */
class ThermalSimulationDataBuilder : SimulationDataBuilder {

    private var iterations: Int = 50
    private var talusAngle: Float = 45.0f
    private var sedimentTalusAngle: Float = 25.0f
    private var cascade: Boolean = true
    private var cellSize: Float = 0.01f
    private var sedimentRemoval: Float = 0.05f
    private var sedimentCarry: Float = 0.05f
    private var sedimentRemovalMultiplier: Float = 5.0f
    private var thermalFalloff: Float = 0.98f
    private var roughness: Float = 0.0025f
    private var abrasion: Float = 0.05f
    private var settling: Float = 0.15f
    private var depositBelow: Float = 0.5f
    private var sedimentParticleLifetime: Int = 50

    /**
     * Build an iterations value.
     *
     * @param value: the iterations value to build
     * @return this builder with the set iterations value
     */
    fun buildIterations(value: Int): ThermalSimulationDataBuilder {
        this.iterations = value
        return this
    }

    /**
     * Build a talus angle value.
     *
     * @param value: the talus angle value to build
     * @return this builder with the set talus angle value
     */
    fun buildTalusAngle(value: Float): ThermalSimulationDataBuilder {
        this.talusAngle = value
        return this
    }

    /**
     * Build a sediment talus angle value.
     *
     * @param value: the sediment talus angle value to build
     * @return this builder with the set sediment talus angle value
     */
    fun buildSedimentTalusAngle(value: Float): ThermalSimulationDataBuilder {
        this.sedimentTalusAngle = value
        return this
    }

    /**
     * Build a cascade value.
     *
     * @param value: the cascade value to build
     * @return this builder with the set cascade value
     */
    fun buildCascade(value: Boolean): ThermalSimulationDataBuilder {
        this.cascade = value
        return this
    }

    /**
     * Build a cell size value.
     *
     * @param value: the cell size value to build
     * @return this builder with the set cell size value
     */
    fun buildCellSize(value: Float): ThermalSimulationDataBuilder {
        this.cellSize = value
        return this
    }

    /**
     * Build a sediment removal value.
     *
     * @param value: the sediment removal value to build
     * @return this builder with the set sediment removal value
     */
    fun buildSedimentRemoval(value: Float): ThermalSimulationDataBuilder {
        this.sedimentRemoval = value
        return this
    }

    /**
     * Build a sediment carry value.
     *
     * @param value: the sediment carry value to build
     * @return this builder with the set sediment carry value
     */
    fun buildSedimentCarry(value: Float): ThermalSimulationDataBuilder {
        this.sedimentCarry = value
        return this
    }

    /**
     * Build a sediment removal multiplier value.
     *
     * @param value: the sediment removal multiplier value to build
     * @return this builder with the set sediment removal multiplier value
     */
    fun buildSedimentRemovalMultiplier(value: Float): ThermalSimulationDataBuilder {
        this.sedimentRemovalMultiplier = value
        return this
    }

    /**
     * Build a thermal falloff value.
     *
     * @param value: the thermal falloff value to build
     * @return this builder with the set thermal falloff value
     */
    fun buildThermalFalloff(value: Float): ThermalSimulationDataBuilder {
        this.thermalFalloff = value
        return this
    }

    /**
     * Build a roughness value.
     *
     * @param value: the roughness value to build
     * @return this builder with the set roughness value
     */
    fun buildRoughness(value: Float): ThermalSimulationDataBuilder {
        this.roughness = value
        return this
    }

    /**
     * Build an abrasion value.
     *
     * @param value: the abrasion value to build
     * @return this builder with the set abrasion value
     */
    fun buildAbrasion(value: Float): ThermalSimulationDataBuilder {
        this.abrasion = value
        return this
    }

    /**
     * Build a settling value.
     *
     * @param value: the settling value to build
     * @return this builder with the set settling value
     */
    fun buildSettling(value: Float): ThermalSimulationDataBuilder {
        this.settling = value
        return this
    }

    /**
     * Build a deposit below value.
     *
     * @param value: the deposit below value to build
     * @return this builder with the set deposit below value
     */
    fun buildDepositBelow(value: Float): ThermalSimulationDataBuilder {
        this.depositBelow = value
        return this
    }

    /**
     * Build a sediment particle lifetime value.
     *
     * @param value: the sediment particle lifetime value to build
     * @return this builder with the set sediment particle lifetime value
     */
    fun buildSedimentParticleLifetime(value: Int): ThermalSimulationDataBuilder {
        this.sedimentParticleLifetime = value
        return this
    }

    override fun build(): ThermalSimulationData = ThermalSimulationData(
        this.iterations,
        this.talusAngle,
        this.sedimentTalusAngle,
        this.cascade,
        this.cellSize,
        this.sedimentRemoval,
        this.sedimentCarry,
        this.sedimentRemovalMultiplier,
        this.thermalFalloff,
        this.roughness,
        this.abrasion,
        this.settling,
        this.depositBelow,
        this.sedimentParticleLifetime
    )
}