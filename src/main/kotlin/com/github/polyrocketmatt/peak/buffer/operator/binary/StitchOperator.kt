package com.github.polyrocketmatt.peak.buffer.operator.binary

import com.github.polyrocketmatt.game.math.*
import com.github.polyrocketmatt.peak.buffer.AsyncNoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer2
import com.github.polyrocketmatt.peak.buffer.NoiseBuffer3
import com.github.polyrocketmatt.peak.buffer.operator.BinaryBufferOperator
import com.github.polyrocketmatt.peak.exception.BufferOperationException
import kotlin.math.abs
import kotlin.math.pow

class StitchOperator : BinaryBufferOperator {

    enum class StitchDirection { LEFT, RIGHT, TOP, BOTTOM, }

    override fun operate(first: NoiseBuffer2, second: NoiseBuffer2, vararg data: Any): NoiseBuffer2 {
        if (data.size != 3)
            throw BufferOperationException("Incorrect number of arguments provided! Expected stitch direction max distance and power arguments!")
        if (data[0] !is StitchDirection)
            throw BufferOperationException("Expected stitch direction argument to be of type StitchDirection!")
        if (data[1] !is Int)
            throw BufferOperationException("Expected max distance argument to be of type Int!")
        if (data[2] !is Float)
            throw BufferOperationException("Expected power argument to be of type Float!")
        val direction = data[0] as StitchDirection
        val maxDistance = data[1] as Int
        val power = data[2] as Float

        val rowsFirst = first.width()
        val rowsSecond = second.width()
        val columnsFirst = first.height()
        val columnsSecond = second.height()

        when (direction) {
            StitchDirection.LEFT        -> {
                if (columnsFirst != columnsSecond)
                    throw BufferOperationException("Stitching heightmaps requires both heightmaps to have the same dimension in the stitching direction!")

                val stitchedBuffer = AsyncNoiseBuffer2(rowsFirst + rowsSecond, columnsFirst, 16)
                val invertedMaxDistance = rowsFirst - maxDistance

                for (row in 0 until rowsFirst) {
                    for (column in 0 until columnsSecond) {
                        val currentHeight = second[row][column]
                        if (row >= invertedMaxDistance) {
                            //  Find average
                            val opposite = first[rowsFirst - row - 1][column]
                            val average = (currentHeight + opposite) / 2.0f

                            val normalizedDistance = row.f().normalize(invertedMaxDistance.f(), rowsFirst.f())
                            val weight = normalizedDistance.smootherStep().pow(power)

                            stitchedBuffer[row][column] = currentHeight + (average - currentHeight) * weight
                        } else
                            stitchedBuffer[row][column] = currentHeight
                    }

                    for (column in 0 until columnsFirst) {
                        val currentHeight = first[row][column]
                        if (row <= maxDistance) {
                            //  Find average
                            val opposite = second[rowsSecond - row - 1][column]
                            val average = (currentHeight + opposite) / 2f

                            val normalizedDistance = row.f().normalize(0f, maxDistance.f())
                            val weight = 1.0f - normalizedDistance.smootherStep().pow(power)

                            stitchedBuffer[row + rowsFirst][column] = currentHeight + (average - currentHeight) * weight
                        } else
                            stitchedBuffer[row + rowsFirst][column] = currentHeight
                    }
                }

                return stitchedBuffer
            }

            StitchDirection.RIGHT       -> {
                if (columnsFirst != columnsSecond)
                    throw BufferOperationException("Stitching heightmaps requires both heightmaps to have the same dimension in the stitching direction!")

                val stitchedBuffer = AsyncNoiseBuffer2(rowsFirst + rowsSecond, columnsFirst, 16)
                val invertedMaxDistance = rowsFirst - maxDistance

                for (row in 0 until rowsFirst) {
                    for (column in 0 until columnsFirst) {
                        val currentHeight = first[row][column]
                        if (row >= invertedMaxDistance) {
                            //  Find average
                            val opposite = second[rowsSecond - row - 1][column]
                            val average = (currentHeight + opposite) / 2.0f

                            val normalizedDistance = row.f().normalize(invertedMaxDistance.f(), rowsFirst.f())
                            val weight = normalizedDistance.smootherStep().pow(power)

                            stitchedBuffer[row][column] = currentHeight + (average - currentHeight) * weight
                        } else
                            stitchedBuffer[row][column] = currentHeight
                    }

                    for (column in 0 until columnsSecond) {
                        val currentHeight = second[row][column]
                        if (row <= maxDistance) {
                            //  Find average
                            val opposite = first[rowsFirst - row - 1][column]
                            val average = (currentHeight + opposite) / 2f

                            val normalizedDistance = row.f().normalize(0f, maxDistance.f())
                            val weight = 1.0f - normalizedDistance.smootherStep().pow(power)

                            stitchedBuffer[row + rowsFirst][column] = currentHeight + (average - currentHeight) * weight
                        } else
                            stitchedBuffer[row + rowsFirst][column] = currentHeight
                    }
                }

                return stitchedBuffer
            }

            StitchDirection.TOP         -> {
                if (rowsFirst != rowsSecond)
                    throw BufferOperationException("Stitching heightmaps requires both heightmaps to have the same dimension in the stitching direction!")

                val stitchedBuffer = AsyncNoiseBuffer2(rowsFirst, columnsFirst + columnsSecond, 16)
                val invertedMaxDistance = columnsFirst - maxDistance

                for (column in 0 until columnsFirst) {
                    for (row in 0 until rowsSecond) {
                        val currentHeight = second[row][column]
                        if (column >= invertedMaxDistance) {
                            //  Find average
                            val opposite = first[row][columnsFirst - column - 1]
                            val average = (currentHeight + opposite) / 2f

                            val normalizedDistance = column.f().normalize(invertedMaxDistance.f(), columnsFirst.f())
                            val weight = normalizedDistance.smootherStep().pow(power)

                            stitchedBuffer[row][column] = currentHeight + (average - currentHeight) * weight
                        } else
                            stitchedBuffer[row][column] = currentHeight
                    }

                    for (row in 0 until rowsFirst) {
                        val currentHeight = first[row][column]
                        if (column <=  maxDistance) {
                            //  Find average
                            val opposite = second[row][columnsSecond - column - 1]
                            val average = (currentHeight + opposite) / 2f

                            val normalizedDistance = column.f().normalize(0f, maxDistance.f())
                            val weight = 1.0f - normalizedDistance.smootherStep().pow(power)

                            stitchedBuffer[row][column + columnsFirst] = currentHeight + (average - currentHeight) * weight
                        } else
                            stitchedBuffer[row][column + columnsFirst] = currentHeight
                    }
                }

                return stitchedBuffer
            }

            StitchDirection.BOTTOM      -> {
                if (rowsFirst != rowsSecond)
                    throw BufferOperationException("Stitching heightmaps requires both heightmaps to have the same dimension in the stitching direction!")

                val stitchedBuffer = AsyncNoiseBuffer2(rowsFirst, columnsFirst + columnsSecond, 16)
                val invertedMaxDistance = columnsFirst - maxDistance

                for (column in 0 until columnsFirst) {
                    for (row in 0 until rowsFirst) {
                        val currentHeight = first[row][column]
                        if (column >= invertedMaxDistance) {
                            //  Find average
                            val opposite = second[row][columnsSecond - column - 1]
                            val average = (currentHeight + opposite) / 2f

                            val normalizedDistance = column.f().normalize(invertedMaxDistance.f(), columnsFirst.f())
                            val weight = normalizedDistance.smootherStep().pow(power)

                            stitchedBuffer[row][column] = currentHeight + (average - currentHeight) * weight
                        } else
                            stitchedBuffer[row][column] = currentHeight
                    }

                    for (row in 0 until rowsSecond) {
                        val currentHeight = second[row][column]
                        if (column <= maxDistance) {
                            //  Find average
                            val opposite = first[row][columnsFirst - column - 1]
                            val average = (currentHeight + opposite) / 2f

                            val normalizedDistance = column.f().normalize(0f, maxDistance.f())
                            val weight = 1.0f - normalizedDistance.smootherStep().pow(power)

                            stitchedBuffer[row][column + columnsFirst] = currentHeight + (average - currentHeight) * weight
                        } else
                            stitchedBuffer[row][column + columnsFirst] = currentHeight
                    }
                }

                return stitchedBuffer
            }
        }
    }

    override fun operate(first: NoiseBuffer3, second: NoiseBuffer3, vararg data: Any): NoiseBuffer3 {
        TODO("Not yet implemented")
    }

    companion object {

        fun computeHealMask(direction: StitchDirection, width: Int, height: Int, distance: Int): AsyncNoiseBuffer2 {
            val stitchedBuffer = AsyncNoiseBuffer2(width, height, 16)
            when (direction) {
                StitchDirection.RIGHT,
                StitchDirection.LEFT        -> {
                    val breakDistance = width / 2
                    for (x in 0 until width) for (y in 0 until height) {
                        val distanceToBreak = abs(x - breakDistance)

                        if (distanceToBreak < distance) {
                            val ratio = distanceToBreak / distance.f()
                            val influence = 1.0f - ratio.smootherStep()

                            stitchedBuffer[x][y] = influence
                        }
                    }
                }

                StitchDirection.TOP,
                StitchDirection.BOTTOM      -> {
                    val breakDistance = width / 2
                    for (x in 0 until width) for (y in 0 until height) {
                        val distanceToBreak = abs(y - breakDistance)

                        if (distanceToBreak < distance) {
                            val ratio = distanceToBreak / distance.f()
                            val influence = 1.0f - ratio.smootherStep()

                            stitchedBuffer[x][y] = influence
                        }
                    }
                }
            }

            return stitchedBuffer
        }

    }

}