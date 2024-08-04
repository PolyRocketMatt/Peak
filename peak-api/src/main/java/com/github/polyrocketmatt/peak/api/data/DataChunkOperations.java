package com.github.polyrocketmatt.peak.api.data;

import com.github.polyrocketmatt.peak.api.exception.DataComputationException;
import org.jetbrains.annotations.NotNull;

public interface DataChunkOperations<T> {

    /**
     * Add the values of the given chunk to this chunk.
     *
     * @param chunk the chunk to add
     * @throws DataComputationException if an error occurs during computation
     */
    void add(@NotNull DataChunk<T> chunk) throws DataComputationException;

    /**
     * Add the given value to all values in this chunk.
     *
     * @param value the value to add
     * @throws DataComputationException if an error occurs during computation
     */
    void add(@NotNull T value) throws DataComputationException;

    /**
     * Subtract the values of the given chunk from this chunk.
     *
     * @param chunk the chunk to subtract
     * @throws DataComputationException if an error occurs during computation
     */
    void sub(@NotNull DataChunk<T> chunk) throws DataComputationException;

    /**
     * Subtract the given value from all values in this chunk.
     *
     * @param value the value to subtract
     * @throws DataComputationException if an error occurs during computation
     */
    void sub(@NotNull T value) throws DataComputationException;

    /**
     * Multiply the values of this chunk with the values of the given chunk.
     *
     * @param chunk the chunk to multiply
     * @throws DataComputationException if an error occurs during computation
     */
    void mul(@NotNull DataChunk<T> chunk) throws DataComputationException;

    /**
     * Multiply all values in this chunk with the given value.
     *
     * @param value the value to multiply
     * @throws DataComputationException if an error occurs during computation
     */
    void mul(@NotNull T value) throws DataComputationException;

    /**
     * Divide the values of this chunk by the values of the given chunk.
     *
     * @param chunk the chunk to divide
     * @throws DataComputationException if an error occurs during computation
     * @throws ArithmeticException if division by zero occurs
     */
    void div(@NotNull DataChunk<T> chunk) throws DataComputationException, ArithmeticException;

    /**
     * Divide all values in this chunk by the given value.
     *
     * @param value the value to divide
     * @throws DataComputationException if an error occurs during computation
     * @throws ArithmeticException if division by zero occurs
     */
    void div(@NotNull T value) throws DataComputationException, ArithmeticException;

    void scale(@NotNull T value) throws DataComputationException;

    void shiftRight(int offset, boolean circular) throws DataComputationException;

    void shiftLeft(int offset, boolean circular) throws DataComputationException;

}
