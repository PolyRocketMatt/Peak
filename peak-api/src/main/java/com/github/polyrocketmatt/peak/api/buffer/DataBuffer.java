package com.github.polyrocketmatt.peak.api.buffer;

import com.github.polyrocketmatt.peak.api.window.DataBufferWindowing;
import org.jetbrains.annotations.NotNull;

import javax.xml.crypto.Data;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Represents a buffer of values.
 *
 * @param <T> the type of value in the buffer
 *
 * @author Matthias Kovacic
 * @since 0.0.1
 * @version 0.0.1
 */
public interface DataBuffer<T> extends DataBufferOperations<T>, DataBufferWindowing<T> {

    /**
     * Get the size of the buffer.
     *
     * @return the size
     */
    int size();

    /**
     * Get the {@link DataBufferType} of this buffer.
     *
     * @return the buffer type
     * @see DataBufferType
     */
    @NotNull DataBufferType getBufferType();

    /**
     * Get the {@link DataBufferDimension} of this buffer.
     *
     * @return the buffer dimension
     * @see DataBufferDimension
     */
    @NotNull DataBufferDimension getBufferDimension();

    /**
     * Get the element at the given index.
     *
     * @param index the index
     * @return the element
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    @NotNull T get(int index) throws IndexOutOfBoundsException;

    /**
     * Set the element at the given index.
     *
     * @param index the index
     * @param value the value
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    void set(int index, @NotNull T value) throws IndexOutOfBoundsException;

    /**
     * Get the minimum value in the buffer.
     *
     * @return the minimum value
     */
    @NotNull T min();

    /**
     * Get the maximum value in the buffer.
     *
     * @return the maximum value
     */
    @NotNull T max();

    /**
     * Get the sum of all values in the buffer.
     *
     * @return the sum of all values
     */
    @NotNull T sum();

    /**
     * Get the average of all values in the buffer.
     *
     * @return the average of all values
     */
    @NotNull T average();

    /**
     * Fill the buffer with random values between 0 and 1.
     */
    @NotNull DataBuffer<T> rand();

    /**
     * Fill the buffer with the given value.
     *
     * @param value the value to fill the buffer with
     */
    @NotNull DataBuffer<T> fill(@NotNull T value);

    /**
     * Fill the buffer with the given function that takes the index as an argument.
     *
     * @param function the function to fill the buffer with
     * @return the buffer
     */
    @NotNull DataBuffer<T> fillIndexed(@NotNull Function<Integer, T> function);

    /**
     * Get a stream of the buffer values.
     *
     * @return the stream
     */
    Stream<T> stream();

    /**
     * For each index in the buffer, execute the given action.
     *
     * @param action the action to execute
     * @return the buffer
     */
    @NotNull DataBuffer<T> forEachIndexed(@NotNull BiConsumer<Integer, T> action);

    /**
     * For each index in the buffer, map the value to a new value using the given function.
     *
     * @param function the function to map the values
     * @return the buffer
     */
    @NotNull DataBuffer<T> mapIndexed(@NotNull BiFunction<Integer, T, T> function);

    /**
     * Map the buffer values to their index.
     *
     * @return the buffer
     */
    @NotNull DataBuffer<T> mapToIndex();

    /**
     * Map the buffer values to a new value using the given function.
     *
     * @param function the function to map the values
     * @return the buffer
     */
    @NotNull DataBuffer<T> map(@NotNull Function<T, T> function);

    /**
     * Map the buffer values to a new value using the given function.
     *
     * @param function the function to map the values
     * @return a stream of the mapped values
     */
    <R> @NotNull Stream<R> mapTo(@NotNull Function<T, R> function);

    /**
     * Check if this buffer is similar in both type and dimensions to the given buffer.
     *
     * @param buffer the buffer to compare
     * @return true if the buffers are similar, false otherwise
     */
    boolean isSimilar(@NotNull DataBuffer<T> buffer);

    /**
     * Create a copy of this buffer.
     *
     * @return the copy
     */
    DataBuffer<T> copy();

    String prettyPrint();

}
