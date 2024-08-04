package com.github.polyrocketmatt.peak.impl.buffer;

import com.github.polyrocketmatt.peak.api.buffer.DataBuffer;
import com.github.polyrocketmatt.peak.api.buffer.DataBuffer1D;
import com.github.polyrocketmatt.peak.api.buffer.DataBufferType;
import com.github.polyrocketmatt.peak.api.data.DataChunk;
import com.github.polyrocketmatt.peak.api.data.DataChunkContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.github.polyrocketmatt.peak.impl.PeakConstants.DEFAULT_CHUNK_SIZE_1D;

public class DataBufferFloat1D extends AbstractDataBufferFloat implements DataBuffer1D<Float> {

    protected DataBufferFloat1D(int size) {
        this(size, new DataChunkContext(DEFAULT_CHUNK_SIZE_1D, true, true));
    }

    protected DataBufferFloat1D(int size, DataChunkContext context) {
        super(size, context);
    }

    protected DataBufferFloat1D(int size, int chunkSize, List<DataChunk<Float>> data, boolean executeParallel) {
        super(size, chunkSize, data, executeParallel);
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
        if (!(buffer instanceof DataBufferFloat1D buffer1d))
            return false;
        return size == buffer1d.size && chunkSize == buffer1d.chunkSize;
    }

    @Override
    public DataBufferFloat1D copy() {
        List<DataChunk<Float>> dataCopy = new ArrayList<>(data.size());
        data.forEach(chunk -> dataCopy.add(chunk.copy()));
        return new DataBufferFloat1D(size, chunkSize, dataCopy, executeParallel);
    }

    @Override
    public @NotNull DataBufferFloat1D add(@NotNull DataBuffer<Float> buffer) {
        if (!isSimilar(buffer))
            throw new IllegalArgumentException("Buffer is not similar to this buffer");
        forEachChunk(buffer, DataChunk::add);
        return this;
    }

    @Override
    public @NotNull DataBufferFloat1D add(@NotNull Float value) {
        forEachChunk(value, DataChunk::add);
        return this;
    }

    @Override
    public @NotNull DataBufferFloat1D sub(@NotNull DataBuffer<Float> buffer) {
        if (!isSimilar(buffer))
            throw new IllegalArgumentException("Buffer is not similar to this buffer");
        forEachChunk(buffer, DataChunk::sub);
        return this;
    }

    @Override
    public @NotNull DataBufferFloat1D sub(@NotNull Float value) {
        forEachChunk(value, DataChunk::sub);
        return this;
    }

    @Override
    public @NotNull DataBufferFloat1D mul(@NotNull DataBuffer<Float> buffer) {
        if (!isSimilar(buffer))
            throw new IllegalArgumentException("Buffer is not similar to this buffer");
        forEachChunk(buffer, DataChunk::mul);
        return this;
    }

    @Override
    public @NotNull DataBufferFloat1D mul(@NotNull Float value) {
        forEachChunk(value, DataChunk::mul);
        return this;
    }

    @Override
    public @NotNull DataBufferFloat1D div(@NotNull DataBuffer<Float> buffer) {
        if (!isSimilar(buffer))
            throw new IllegalArgumentException("Buffer is not similar to this buffer");
        forEachChunk(buffer, DataChunk::div);
        return this;
    }

    @Override
    public @NotNull DataBufferFloat1D div(@NotNull Float value) {
        if (value == 0.0f)
            throw new IllegalArgumentException("Division by zero");
        forEachChunk(value, DataChunk::div);
        return this;
    }

}
