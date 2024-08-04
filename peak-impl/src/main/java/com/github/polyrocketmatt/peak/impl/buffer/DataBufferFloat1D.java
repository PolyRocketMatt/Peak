package com.github.polyrocketmatt.peak.impl.buffer;

import com.github.polyrocketmatt.peak.api.buffer.DataBuffer;
import com.github.polyrocketmatt.peak.api.buffer.DataBuffer1D;
import com.github.polyrocketmatt.peak.api.buffer.DataBufferType;
import com.github.polyrocketmatt.peak.api.data.DataChunk;
import com.github.polyrocketmatt.peak.api.data.DataChunkContext;
import com.github.polyrocketmatt.peak.impl.data.SimpleDataChunkFloat;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.github.polyrocketmatt.peak.impl.PeakConstants.DEFAULT_CHUNK_SIZE_1D;

public class DataBufferFloat1D implements DataBuffer1D<Float> {

    private final int size;
    private final int chunkSize;
    private final List<DataChunk<Float>> data;

    private final boolean executeParallel;

    protected DataBufferFloat1D(int size) {
        this(size, new DataChunkContext(DEFAULT_CHUNK_SIZE_1D, true, true));
    }

    protected DataBufferFloat1D(int size, DataChunkContext context) {
        this.chunkSize = context.chunkSize();
        int chunks = size / chunkSize + 1;
        this.size = size;
        this.data = new ArrayList<>();
        this.executeParallel = context.autoParallel() ? (chunks >= 8192) : context.parallel();

        for (int i = 0; i < chunks; i++) {
            int chunkSizeActual = Math.min(chunkSize, size - i * chunkSize);
            if (chunkSizeActual <= 0)
                break;
            data.add(new SimpleDataChunkFloat(chunkSizeActual));
        }
    }

    private DataBufferFloat1D(int size, int chunkSize, List<DataChunk<Float>> data, boolean executeParallel) {
        this.size = size;
        this.chunkSize = chunkSize;
        this.data = data;
        this.executeParallel = executeParallel;
    }

    @Override
    public @NotNull Float get(int i) {
        return data.get(i / chunkSize).get(i % chunkSize);
    }

    @Override
    public void set(int i, @NotNull Float value) {
        data.get(i / chunkSize).set(i % chunkSize, value);
    }

    @Override
    public @NotNull DataBufferType getBufferType() {
        return DataBufferType.FLOAT_1D;
    }

    @Override
    public @NotNull Float min() {
        return mapTo(DataChunk::min).min(Float::compareTo).orElseThrow();
    }

    @Override
    public @NotNull Float max() {
        return mapTo(DataChunk::max).max(Float::compareTo).orElseThrow();
    }

    @Override
    public @NotNull Float sum() {
        return mapTo(DataChunk::sum).reduce(0f, Float::sum);
    }

    @Override
    public @NotNull Float average() {
        return sum() / size;
    }

    @Override
    public void rand() {
        map(value -> (float) Math.random());
    }

    @Override
    public void map(@NotNull Function<Float, Float> function) {
        if (executeParallel)
            data.parallelStream().forEach(chunk -> chunk.map(function));
        else
            data.forEach(chunk -> chunk.map(function));
    }

    @Override
    public <R> @NotNull Stream<R> mapTo(@NotNull Function<DataChunk<Float>, R> function) {
        return (executeParallel) ? data.parallelStream().map(function) : data.stream().map(function);
    }

    @Override
    public DataBuffer<Float> copy() {
        List<DataChunk<Float>> dataCopy = new ArrayList<>(data.size());
        data.forEach(chunk -> dataCopy.add(chunk.copy()));
        return new DataBufferFloat1D(size, chunkSize, dataCopy, executeParallel);
    }
}
