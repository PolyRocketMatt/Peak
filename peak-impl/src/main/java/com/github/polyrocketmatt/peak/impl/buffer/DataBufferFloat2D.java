package com.github.polyrocketmatt.peak.impl.buffer;

import com.github.polyrocketmatt.peak.api.buffer.DataBuffer2D;
import com.github.polyrocketmatt.peak.api.buffer.DataBufferType;
import com.github.polyrocketmatt.peak.api.data.DataChunk;
import com.github.polyrocketmatt.peak.api.data.DataChunkContext;
import com.github.polyrocketmatt.peak.impl.data.SimpleDataChunkFloat;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class DataBufferFloat2D implements DataBuffer2D<Float> {

    private final int width;
    private final int height;
    private final int size;
    private final int chunkSize;
    private final List<SimpleDataChunkFloat> data;

    private final boolean executeParallel;

    protected DataBufferFloat2D(int width, int height) {
        this(width, height, DataChunkContext.DEFAULT);
    }

    protected DataBufferFloat2D(int width, int height, DataChunkContext context) {
        this.chunkSize = context.chunkSize();
        int chunks = (width * height) / chunkSize + 1;
        this.width = width;
        this.height = height;
        this.size = width * height;
        this.data = new ArrayList<>();
        this.executeParallel = context.autoParallel() ? (chunks >= 8192) : context.parallel();

        for (int i = 0; i < chunks; i++) {
            int chunkSizeActual = Math.min(chunkSize, size - i * chunkSize);
            if (chunkSizeActual <= 0)
                break;
            data.add(new SimpleDataChunkFloat(chunkSizeActual));
        }
    }

    @Override
    public @NotNull Float get(int i, int j) {
        return data.get((i * width + j) / chunkSize).get((i * width + j) % chunkSize);
    }

    @Override
    public void set(int i, int j, @NotNull Float value) {
        data.get((i * width + j) / chunkSize).set((i * width + j) % chunkSize, value);
    }

    @Override
    public @NotNull DataBufferType getBufferType() {
        return DataBufferType.FLOAT_2D;
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

}
