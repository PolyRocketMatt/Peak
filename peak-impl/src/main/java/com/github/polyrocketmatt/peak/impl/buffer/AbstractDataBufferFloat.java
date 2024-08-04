package com.github.polyrocketmatt.peak.impl.buffer;

import com.github.polyrocketmatt.peak.api.buffer.DataBuffer;
import com.github.polyrocketmatt.peak.api.buffer.DataBufferType;
import com.github.polyrocketmatt.peak.api.buffer.DataContext;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class AbstractDataBufferFloat implements DataBuffer<Float> {

    protected final int size;
    protected final int chunkSize;
    protected final Float[] data;

    protected final boolean executeParallel;

    public AbstractDataBufferFloat(int size, DataContext context) {
        this.size = size;
        this.chunkSize = context.chunkSize();
        this.data = new Float[size];

        int chunks = size / chunkSize + 1;
        this.executeParallel = context.autoParallel() ? (chunks >= 8192) : context.parallel();
    }

    protected AbstractDataBufferFloat(int size, int chunkSize, Float[] data, boolean executeParallel) {
        this.size = size;
        this.chunkSize = chunkSize;
        this.data = data;
        this.executeParallel = executeParallel;
    }

    @Override
    public @NotNull Float get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        return data[index];
    }

    @Override
    public void set(int index, @NotNull Float value) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        data[index] = value;
    }

    @Override
    public void forEachIndexed(@NotNull BiConsumer<Integer, Float> action) {
        if (executeParallel)
            IntStream.range(0, size).parallel().forEach(i -> action.accept(i, data[i]));
        else
            IntStream.range(0, size).forEach(i -> action.accept(i, data[i]));
    }

    @Override
    public @NotNull DataBufferType getBufferType() {
        return DataBufferType.FLOAT_1D;
    }

    @Override
    public @NotNull Float min() {
        return (executeParallel) ?
                Arrays.stream(data).parallel().min(Float::compareTo).orElse(0f) :
                Arrays.stream(data).min(Float::compareTo).orElse(0f);
    }

    @Override
    public @NotNull Float max() {
        return (executeParallel) ?
                Arrays.stream(data).parallel().max(Float::compareTo).orElse(0f) :
                Arrays.stream(data).max(Float::compareTo).orElse(0f);
    }

    @Override
    public @NotNull Float sum() {
        return (executeParallel) ?
                Arrays.stream(data).parallel().reduce(0f, Float::sum) :
                Arrays.stream(data).reduce(0f, Float::sum);
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
    public Stream<Float> stream() {
        return Arrays.stream(data);
    }

    @Override
    public void map(@NotNull Function<Float, Float> function) {
        if (executeParallel)
            Arrays.parallelSetAll(data, i -> function.apply(data[i]));
        else
            Arrays.setAll(data, i -> function.apply(data[i]));
    }

    @Override
    public <R> @NotNull Stream<R> mapTo(@NotNull Function<Float, R> function) {
        return (executeParallel) ?
                Arrays.stream(data).parallel().map(function) :
                Arrays.stream(data).map(function);
    }

    @Override
    public boolean isSimilar(@NotNull DataBuffer<Float> buffer) {
        if (!(buffer instanceof DataBufferFloat1D buffer1d))
            return false;
        return size == buffer1d.size && chunkSize == buffer1d.chunkSize;
    }

    @Override
    public DataBufferFloat1D copy() {
        return new DataBufferFloat1D(size, chunkSize, Arrays.copyOf(data, size), executeParallel);
    }

    @Override
    public @NotNull DataBuffer<Float> add(@NotNull DataBuffer<Float> buffer) {
        if (!isSimilar(buffer))
            throw new IllegalArgumentException("Buffer is not similar to this buffer");
        forEachIndexed((i, value) -> set(i, value + buffer.get(i)));
        return this;
    }

    @Override
    public @NotNull DataBuffer<Float> add(@NotNull Float value) {
        forEachIndexed((i, v) -> set(i, v + value));
        return this;
    }

    @Override
    public @NotNull DataBuffer<Float> sub(@NotNull DataBuffer<Float> buffer) {
        if (!isSimilar(buffer))
            throw new IllegalArgumentException("Buffer is not similar to this buffer");
        forEachIndexed((i, value) -> set(i, value - buffer.get(i)));
        return this;
    }

    @Override
    public @NotNull DataBuffer<Float> sub(@NotNull Float value) {
        forEachIndexed((i, v) -> set(i, v - value));
        return this;
    }

    @Override
    public @NotNull DataBuffer<Float> mul(@NotNull DataBuffer<Float> buffer) {
        if (!isSimilar(buffer))
            throw new IllegalArgumentException("Buffer is not similar to this buffer");
        forEachIndexed((i, value) -> set(i, value * buffer.get(i)));
        return this;
    }

    @Override
    public @NotNull DataBuffer<Float> mul(@NotNull Float value) {
        forEachIndexed((i, v) -> set(i, v * value));
        return this;
    }

    @Override
    public @NotNull DataBuffer<Float> div(@NotNull DataBuffer<Float> buffer) {
        if (!isSimilar(buffer))
            throw new IllegalArgumentException("Buffer is not similar to this buffer");
        forEachIndexed((i, value) -> set(i, value / buffer.get(i)));
        return this;
    }

    @Override
    public @NotNull DataBuffer<Float> div(@NotNull Float value) {
        if (value == 0.0f)
            throw new IllegalArgumentException("Division by zero");
        forEachIndexed((i, v) -> set(i, v / value));
        return this;
    }

    @Override
    public @NotNull DataBuffer<Float> scale(@NotNull Float value) {
        return mul(value);
    }

    @Override
    public @NotNull DataBuffer<Float> shift(int offset, int axis, int direction, boolean circular) {
        throw new UnsupportedOperationException("Shifting is not yet supported for 1D buffers");
    }

}
