package com.github.polyrocketmatt.peak.api.buffer;

import com.github.polyrocketmatt.peak.api.exception.DataComputationException;
import org.jetbrains.annotations.NotNull;

public interface DataBufferOperations<T> {

    /**
     * Add the values of the given buffer to this buffer.
     *
     * @param buffer the buffer to add
     * @return the buffer with the added values
     */
    @NotNull
    DataBuffer<T> add(@NotNull DataBuffer<T> buffer) throws IllegalArgumentException, DataComputationException;

    /**
     * Add the given value to all values in this buffer.
     *
     * @param value the value to add
     * @return the buffer with the added values
     */
    @NotNull DataBuffer<T> add(@NotNull T value) throws IllegalArgumentException, DataComputationException;

    /**
     * Subtract the values of the given buffer from this buffer.
     *
     * @param buffer the buffer to subtract
     * @return the buffer with the subtracted values
     */
    @NotNull DataBuffer<T> sub(@NotNull DataBuffer<T> buffer) throws IllegalArgumentException, DataComputationException;

    /**
     * Subtract the given value from all values in this buffer.
     *
     * @param value the value to subtract
     * @return the buffer with the subtracted values
     */
    @NotNull DataBuffer<T> sub(@NotNull T value) throws IllegalArgumentException, DataComputationException;

    /**
     * Multiply the values of the given buffer with this buffer.
     *
     * @param buffer the buffer to multiply
     * @return the buffer with the multiplied values
     */
    @NotNull DataBuffer<T> mul(@NotNull DataBuffer<T> buffer) throws IllegalArgumentException, DataComputationException;

    /**
     * Multiply all values in this buffer with the given value.
     *
     * @param value the value to multiply
     * @return the buffer with the multiplied values
     */
    @NotNull DataBuffer<T> mul(@NotNull T value) throws IllegalArgumentException, DataComputationException;

    /**
     * Divide the values of the given buffer by this buffer.
     *
     * @param buffer the buffer to divide
     * @return the buffer with the divided values
     */
    @NotNull DataBuffer<T> div(@NotNull DataBuffer<T> buffer) throws IllegalArgumentException, DataComputationException;

    /**
     * Divide all values in this buffer by the given value.
     *
     * @param value the value to divide
     * @return the buffer with the divided values
     */
    @NotNull DataBuffer<T> div(@NotNull T value) throws IllegalArgumentException, DataComputationException;

}
