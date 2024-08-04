package com.github.polyrocketmatt.peak.api.data;

import com.github.polyrocketmatt.peak.api.exception.DataComputationException;
import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;
import java.util.function.Function;

/**
 * Represents a chunk of data that can be manipulated.
 *
 * @param <T> the type of data in this chunk
 *
 * @author Matthias Kovacic
 * @since 0.0.1
 * @version 0.0.1
 */
public interface DataChunk<T> {

    int getIndex();

    /**
     * Get the value at the given index.
     *
     * @param index the index
     * @return the value
     */
    @NotNull T get(int index);

    /**
     * Set the value at the given index.
     *
     * @param index the index
     * @param value the value
     */
    void set(int index, @NotNull T value);

    /**
     * Map the values in this chunk to a new value using the given function.
     *
     * @param function the function to map the values
     * @throws DataComputationException if an error occurs during computation
     */
    void map(@NotNull Function<T, T> function) throws DataComputationException;

    /**
     * Get the minimum value in this chunk.
     *
     * @return the minimum value
     * @throws NoSuchElementException if the chunk is empty
     */
    @NotNull T min() throws NoSuchElementException;

    /**
     * Get the maximum value in this chunk.
     *
     * @return the maximum value
     * @throws NoSuchElementException if the chunk is empty
     */
    @NotNull T max() throws NoSuchElementException;

    /**
     * Get the sum of all values in this chunk.
     *
     * @return the sum of all values
     */
    @NotNull T sum();

    /**
     * Get the average of all values in this chunk.
     *
     * @return the average of all values
     */
    @NotNull T average();

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

    /**
     * Create a copy of this chunk.
     *
     * @return the copy
     */
    @NotNull DataChunk<T> copy();

}
