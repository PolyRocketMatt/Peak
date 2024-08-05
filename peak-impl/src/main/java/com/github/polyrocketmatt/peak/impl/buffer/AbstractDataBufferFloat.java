package com.github.polyrocketmatt.peak.impl.buffer;

import com.github.polyrocketmatt.peak.api.buffer.DataBuffer;
import com.github.polyrocketmatt.peak.api.buffer.DataBufferDimension;
import com.github.polyrocketmatt.peak.api.buffer.DataBufferType;
import com.github.polyrocketmatt.peak.api.buffer.DataContext;
import com.github.polyrocketmatt.peak.api.window.WindowContext;
import com.github.polyrocketmatt.peak.api.window.WindowFunction;
import com.github.polyrocketmatt.peak.impl.window.ctx.EmptyContext;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
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

        fill(0.0f);
    }

    protected AbstractDataBufferFloat(int size, int chunkSize, Float[] data, boolean executeParallel) {
        this.size = size;
        this.chunkSize = chunkSize;
        this.data = data;
        this.executeParallel = executeParallel;
    }

    @Override
    public String toString() {
        return "DataBufferFloat1D{" +
                "size=" + size +
                ", chunkSize=" + chunkSize +
                ", data=" + Arrays.toString(data) +
                ", executeParallel=" + executeParallel +
                '}';
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
    public void mapIndexed(@NotNull BiFunction<Integer, Float, Float> function) {
        if (executeParallel)
            IntStream.range(0, size).parallel().forEach(i -> data[i] = function.apply(i, data[i]));
        else
            IntStream.range(0, size).forEach(i -> data[i] = function.apply(i, data[i]));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public @NotNull DataBufferType getBufferType() {
        return DataBufferType.FLOAT;
    }

    @Override
    public @NotNull DataBufferDimension getBufferDimension() {
        return DataBufferDimension.ONE_DIMENSIONAL;
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
    public @NotNull DataBuffer<Float> rand() {
        if (executeParallel)
            Arrays.parallelSetAll(data, i -> (float) Math.random());
        else
            Arrays.setAll(data, i -> (float) Math.random());
        return this;
    }

    @Override
    public @NotNull DataBuffer<Float> fill(@NotNull Float value) {
        if (executeParallel)
            Arrays.parallelSetAll(data, i -> value);
        else
            Arrays.fill(data, value);
        return this;
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
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof AbstractDataBufferFloat that)) return false;
        return size == that.size && chunkSize == that.chunkSize && executeParallel == that.executeParallel && Objects.deepEquals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, chunkSize, Arrays.hashCode(data), executeParallel);
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
    public @NotNull DataBuffer<Float> multIndexedBy(@NotNull BiFunction<Integer, Float, Float> function) {
        mapIndexed((idx, val) -> val * function.apply(idx, val));
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
    public @NotNull DataBuffer<Float> abs() {
        map(Math::abs);
        return this;
    }

    @Override
    public @NotNull DataBuffer<Float> normalize() {
        float min = min();
        float max = max();
        float range = max - min;
        if (range == 0.0f)
            return this;
        map(val -> (val - min) / range);
        return this;
    }

    @Override
    public @NotNull DataBuffer<Float> window(WindowFunction window, WindowContext<Float> ctx) {
        window.applyFloat(this, ctx);
        return this;
    }

    @Override
    public @NotNull DataBuffer<Float> window(WindowFunction window) {
        window.applyFloat(this, new EmptyContext<>());
        return this;
    }
}
