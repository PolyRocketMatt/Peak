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
     * Create a copy of this chunk.
     *
     * @return the copy
     */
    @NotNull DataChunk<T> copy();

}
