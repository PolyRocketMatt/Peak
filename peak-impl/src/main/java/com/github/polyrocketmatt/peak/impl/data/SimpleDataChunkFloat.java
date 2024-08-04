package com.github.polyrocketmatt.peak.impl.data;

import com.github.polyrocketmatt.peak.api.data.DataChunk;
import com.github.polyrocketmatt.peak.api.exception.DataComputationException;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class SimpleDataChunkFloat implements DataChunk<Float> {

    private final int index;
    private final int size;
    private final Float[] data;

    public SimpleDataChunkFloat(int index, int size) {
        if (index <= 0)
            throw new IllegalArgumentException("Index must be greater than or equal to 0");
        if (size <= 0)
            throw new IllegalArgumentException("Size must be greater than 0");
        this.index = index;
        this.size = size;
        this.data = new Float[size];

        Arrays.fill(data, 0f);
    }

    private SimpleDataChunkFloat(int index, int size, Float[] data) {
        this.index = index;
        this.size = size;
        this.data = data;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public int getSize() {
        return size;
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
    public @NotNull Float min() throws NoSuchElementException {
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

    @Override
    public void fill(@NotNull Float value) {
        Arrays.fill(data, value);
    }

    @Override
    public void rand() {
        for (int i = 0; i < size; i++)
            data[i] = (float) Math.random();
    }

    @Override
    public @NotNull SimpleDataChunkFloat copy() {
        return new SimpleDataChunkFloat(index, size, data);
    }

    @Override
    public void add(@NotNull DataChunk<Float> chunk) throws DataComputationException {
        for (int i = 0; i < size; i++)
            data[i] += chunk.get(i);
    }

    @Override
    public void add(@NotNull Float value) throws DataComputationException {
        for (int i = 0; i < size; i++)
            data[i] += value;
    }

    @Override
    public void sub(@NotNull DataChunk<Float> chunk) throws DataComputationException {
        for (int i = 0; i < size; i++)
            data[i] -= chunk.get(i);
    }

    @Override
    public void sub(@NotNull Float value) throws DataComputationException {
        for (int i = 0; i < size; i++)
            data[i] -= value;
    }

    @Override
    public void mul(@NotNull DataChunk<Float> chunk) throws DataComputationException {
        for (int i = 0; i < size; i++)
            data[i] *= chunk.get(i);
    }

    @Override
    public void mul(@NotNull Float value) throws DataComputationException {
        for (int i = 0; i < size; i++)
            data[i] *= value;
    }

    @Override
    public void div(@NotNull DataChunk<Float> chunk) throws DataComputationException, ArithmeticException {
        for (int i = 0; i < size; i++) {
            float value = chunk.get(i);
            if (value == 0)
                throw new ArithmeticException("Division by zero");
            data[i] /= chunk.get(i);
        }
    }

    @Override
    public void div(@NotNull Float value) throws DataComputationException {
        if (value == 0)
            throw new ArithmeticException("Division by zero");
        for (int i = 0; i < size; i++)
            data[i] /= value;
    }

    @Override
    public void scale(@NotNull Float value) throws DataComputationException {
        mul(value);
    }

    @Override
    public void shiftLeft(int offset, boolean circular) throws DataComputationException {
        if (offset < 0)
            shiftRight(-offset, circular);
        else {

            Float[] tmp = Arrays.copyOf(data, size);
            if (circular)
                for (int i = 0; i < size; i++)
                    data[i] = tmp[(i + offset) % size];
            else
                for (int i = 0; i < size; i++)
                    data[i] = i + offset < size ? tmp[i + offset] : 0;
        }
    }

    @Override
    public void shiftRight(int offset, boolean circular) throws DataComputationException {
        if (offset < 0)
            shiftLeft(-offset, circular);
        else {
            Float[] tmp = Arrays.copyOf(data, size);
            if (circular)
                for (int i = 0; i < size; i++)
                    data[i] = tmp[(i - offset + size) % size];
            else
                for (int i = 0; i < size; i++)
                    data[i] = i - offset >= 0 ? tmp[i - offset] : 0;
        }
    }

    @Override
    public String toString() {
        return "SimpleDataChunkFloat{" +
                "index=" + index +
                ", size=" + size +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
