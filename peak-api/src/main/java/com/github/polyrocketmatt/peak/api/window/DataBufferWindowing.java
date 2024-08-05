package com.github.polyrocketmatt.peak.api.window;

import com.github.polyrocketmatt.peak.api.buffer.DataBuffer;
import com.github.polyrocketmatt.peak.api.exception.DataComputationException;
import org.jetbrains.annotations.NotNull;

/**
 * Provides windowing operations for a {@link DataBuffer}.
 *
 * @param <T> the type of value in the buffer
 *
 * @author Matthias Kovacic
 * @since 0.0.1
 * @version 0.0.1
 * @see DataBuffer
 */
public interface DataBufferWindowing<T> {

    /**
     * Apply the given window function to the buffer with the given context.
     *
     * @param window the window function to apply
     * @param ctx the context for the window function
     * @return the buffer with the window function applied
     * @throws DataComputationException if an error occurs during computation
     */
    @NotNull DataBuffer<T> window(WindowFunction window, WindowContext<T> ctx) throws DataComputationException;

    /**
     * Apply the given window function to the buffer.
     *
     * @param window the window function to apply
     * @return the buffer with the window function applied
     * @throws DataComputationException if an error occurs during computation
     */
    @NotNull DataBuffer<T> window(WindowFunction window) throws DataComputationException;

}
