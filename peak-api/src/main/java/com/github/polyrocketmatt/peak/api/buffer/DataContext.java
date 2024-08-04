package com.github.polyrocketmatt.peak.api.buffer;

/**
 * Object that contains context on how to construct data chunks.
 * <b>NOTE</b> If {@code autoParallel} is set to {@code true}, the {@code parallel} value will be ignored.
 *
 * @param chunkSize The size of the chunk
 * @param autoParallel Whether to automatically parallelize computations for chunks
 * @param parallel Whether to parallelize computations for chunks
 *
 * @author Matthias Kovacic
 * @since 0.0.1
 * @version 0.0.1
 */
public record DataContext(int chunkSize, boolean autoParallel, boolean parallel) {

    public static final DataContext DEFAULT = new DataContext(1024, true, true);

}
