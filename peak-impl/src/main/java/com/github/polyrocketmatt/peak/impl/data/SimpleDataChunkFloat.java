package com.github.polyrocketmatt.peak.impl.data;

import com.github.polyrocketmatt.peak.api.data.DataChunk;
import com.github.polyrocketmatt.peak.api.exception.DataComputationException;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class SimpleDataChunkFloat implements DataChunk<Float> {

    private final int size;
    private final Float[] data;

    public SimpleDataChunkFloat(int size) {
        if (size <= 0)
            throw new IllegalArgumentException("Size must be greater than 0");
        this.size = size;
        this.data = new Float[size];
    }

    @Override
    public @NotNull Float get(int index) {
        if (index < 0 || index >= data.length)
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        return data[index];
    }

    @Override
    public void set(int index, @NotNull Float value) {
        if (index < 0 || index >= data.length)
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        data[index] = value;
    }

    @Override
    public void map(@NotNull Function<Float, Float> function) throws DataComputationException {
        try {
            for (int i = 0; i < size; i++)
                data[i] = function.apply(data[i]);
        } catch (Exception ex) {
            throw new DataComputationException(ex);
        }
    }

    @Override
    public @NotNull Float min() throws NoSuchElementException{
        return Arrays.stream(data).min(Float::compareTo).orElseThrow();
    }

    @Override
    public @NotNull Float max() throws NoSuchElementException {
        return Arrays.stream(data).max(Float::compareTo).orElseThrow();
    }

    @Override
    public @NotNull Float sum() {
        return Arrays.stream(data).reduce(0f, Float::sum);
    }

    @Override
    public @NotNull Float average() {
        return sum() / size;
    }
}
