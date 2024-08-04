package com.github.polyrocketmatt.peak.impl.buffer;

import com.github.polyrocketmatt.peak.api.buffer.DataBuffer;
import com.github.polyrocketmatt.peak.api.buffer.DataBuffer2D;
import com.github.polyrocketmatt.peak.api.buffer.DataBufferType;
import com.github.polyrocketmatt.peak.api.data.DataChunk;
import com.github.polyrocketmatt.peak.api.data.DataChunkContext;
import com.github.polyrocketmatt.peak.impl.data.SimpleDataChunkFloat;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class DataBufferFloat2D extends AbstractDataBufferFloat implements DataBuffer2D<Float> {

    private final int width;
    private final int height;

    protected DataBufferFloat2D(int width, int height) {
        this(width, height, DataChunkContext.DEFAULT);
    }

    protected DataBufferFloat2D(int width, int height, DataChunkContext context) {
        super(width * height, context);

        this.width = width;
        this.height = height;
    }

    protected DataBufferFloat2D(int width, int height, int chunkSize, List<DataChunk<Float>> data, boolean executeParallel) {
        super(width * height, chunkSize, data, executeParallel);

        this.width = width;
        this.height = height;
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
    public @NotNull DataChunk<Float> getChunk(int index) {
        if (index < 0 || index >= data.size())
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        return data.get(index);
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
    public boolean isSimilar(@NotNull DataBuffer<Float> buffer) {
        if (!(buffer instanceof DataBufferFloat2D buffer2d))
            return false;
        return width == buffer2d.width && height == buffer2d.height && size == buffer2d.size && chunkSize == buffer2d.chunkSize;
    }

    @Override
    public DataBufferFloat2D copy() {
        List<DataChunk<Float>> dataCopy = new ArrayList<>(data.size());
        data.forEach(chunk -> dataCopy.add(chunk.copy()));
        return new DataBufferFloat2D(width, height, chunkSize, dataCopy, executeParallel);
    }

    @Override
    public @NotNull DataBufferFloat2D add(@NotNull DataBuffer<Float> buffer) {
        if (!isSimilar(buffer))
            throw new IllegalArgumentException("Buffer is not similar to this buffer");
        forEachChunk(buffer, DataChunk::add);
        return this;
    }

    @Override
    public @NotNull DataBufferFloat2D add(@NotNull Float value) {
        forEachChunk(value, DataChunk::add);
        return this;
    }

    @Override
    public @NotNull DataBufferFloat2D sub(@NotNull DataBuffer<Float> buffer) {
        if (!isSimilar(buffer))
            throw new IllegalArgumentException("Buffer is not similar to this buffer");
        forEachChunk(buffer, DataChunk::sub);
        return this;
    }

    @Override
    public @NotNull DataBufferFloat2D sub(@NotNull Float value) {
        forEachChunk(value, DataChunk::sub);
        return this;
    }

    @Override
    public @NotNull DataBufferFloat2D mul(@NotNull DataBuffer<Float> buffer) {
        if (!isSimilar(buffer))
            throw new IllegalArgumentException("Buffer is not similar to this buffer");
        forEachChunk(buffer, DataChunk::mul);
        return this;
    }

    @Override
    public @NotNull DataBufferFloat2D mul(@NotNull Float value) {
        forEachChunk(value, DataChunk::mul);
        return this;
    }

    @Override
    public @NotNull DataBufferFloat2D div(@NotNull DataBuffer<Float> buffer) {
        if (!isSimilar(buffer))
            throw new IllegalArgumentException("Buffer is not similar to this buffer");
        forEachChunk(buffer, DataChunk::div);
        return this;
    }

    @Override
    public @NotNull DataBufferFloat2D div(@NotNull Float value) {
        if (value == 0.0f)
            throw new IllegalArgumentException("Division by zero");
        forEachChunk(value, DataChunk::div);
        return this;
    }

}
