package com.github.polyrocketmatt.peak.impl.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleDataChunkFloatTest {

    @Test void testConstructorIllegalIndex() {
        assertThrows(IllegalArgumentException.class, () -> new SimpleDataChunkFloat(-1, 1));
    }

    @Test void testConstructorIllegalSize() {
        assertThrows(IllegalArgumentException.class, () -> new SimpleDataChunkFloat(1, 0));
    }

    @Test void testConstructor() {
        SimpleDataChunkFloat chunk = new SimpleDataChunkFloat(1, 1000);

        assertEquals(1, chunk.getIndex());
        assertEquals(1000, chunk.getSize());
    }

    @Test void testIllegalGet() {
        SimpleDataChunkFloat chunk = new SimpleDataChunkFloat(1, 1000);

        assertThrows(IndexOutOfBoundsException.class, () -> chunk.get(-1));
    }

    @Test void testIllegalSet() {
        SimpleDataChunkFloat chunk = new SimpleDataChunkFloat(1, 1000);

        assertThrows(IndexOutOfBoundsException.class, () -> chunk.set(-1, 0.0f));
    }

    @Test void testGetSet() {
        SimpleDataChunkFloat chunk = new SimpleDataChunkFloat(1, 1000);

        chunk.set(0, 1.0f);
        assertEquals(1.0f, chunk.get(0));
    }

    @Test void testMap() {
        SimpleDataChunkFloat chunk = new SimpleDataChunkFloat(1, 10);

        assertEquals(0.0f, chunk.sum());
        chunk.map(value -> value + 1.0f);
        assertEquals(10.0f, chunk.sum());
    }

    @Test void testMin() {
        SimpleDataChunkFloat chunk = new SimpleDataChunkFloat(1, 2);

        assertEquals(0.0f, chunk.min());
        chunk.set(0, -1.0f);
        assertEquals(-1.0f, chunk.min());
    }

    @Test void testMax() {
        SimpleDataChunkFloat chunk = new SimpleDataChunkFloat(1, 2);

        assertEquals(0.0f, chunk.max());
        chunk.set(0, 1.0f);
        assertEquals(1.0f, chunk.max());
    }

    @Test void testSum() {
        SimpleDataChunkFloat chunk = new SimpleDataChunkFloat(1, 4);

        assertEquals(0.0f, chunk.sum());
        for (int i = 0; i < 4; i++)
            chunk.set(i, (float) (i + 1));
        assertEquals(10.0f, chunk.sum());
    }

    @Test void testAverage() {
        SimpleDataChunkFloat chunk = new SimpleDataChunkFloat(1, 4);

        assertEquals(0.0f, chunk.average());
        for (int i = 0; i < 4; i++)
            chunk.set(i, (float) (i + 1));
        assertEquals(2.5f, chunk.average());
    }

    @Test void testFill() {
        SimpleDataChunkFloat chunk = new SimpleDataChunkFloat(1, 4);

        chunk.fill(1.0f);
        assertEquals(4.0f, chunk.sum());
    }

    @Test void testRand() {
        SimpleDataChunkFloat chunk = new SimpleDataChunkFloat(1, 4);

        chunk.rand();
        assertNotEquals(0.0f, chunk.sum());
    }

    @Test void testCopy() {
        SimpleDataChunkFloat chunk = new SimpleDataChunkFloat(1, 4);

        for (int i = 0; i < 4; i++)
            chunk.set(i, (float) (i + 1));
        assertEquals(10.0f, chunk.sum());

        SimpleDataChunkFloat copy = chunk.copy();
        assertEquals(1, copy.getIndex());
        assertEquals(4, copy.getSize());
        assertEquals(10.0f, copy.sum());
    }

    @Test void testAddChunk() {
        SimpleDataChunkFloat chunk = new SimpleDataChunkFloat(1, 4);
        SimpleDataChunkFloat buffer = new SimpleDataChunkFloat(1, 4);

        for (int i = 0; i < 4; i++)
            buffer.set(i, (float) (i + 1));
        chunk.add(buffer);

        assertEquals(10.0f, chunk.sum());
    }

    @Test void testAddConstant() {
        SimpleDataChunkFloat chunk = new SimpleDataChunkFloat(1, 4);

        chunk.add(1.0f);

        assertEquals(4.0f, chunk.sum());
    }

    @Test void testSubChunk() {
        SimpleDataChunkFloat chunk = new SimpleDataChunkFloat(1, 4);
        SimpleDataChunkFloat buffer = new SimpleDataChunkFloat(1, 4);

        for (int i = 0; i < 4; i++)
            buffer.set(i, (float) (i + 1));
        chunk.sub(buffer);

        assertEquals(-10.0f, chunk.sum());
    }

    @Test void testSubConstant() {
        SimpleDataChunkFloat chunk = new SimpleDataChunkFloat(1, 4);

        chunk.sub(1.0f);

        assertEquals(-4.0f, chunk.sum());
    }

    @Test void testMulChunk() {
        SimpleDataChunkFloat chunk = new SimpleDataChunkFloat(1, 4);
        SimpleDataChunkFloat buffer = new SimpleDataChunkFloat(1, 4);

        chunk.fill(1.0f);
        for (int i = 0; i < 4; i++)
            buffer.set(i, (float) (i + 1));
        chunk.mul(buffer);

        assertEquals(10.0f, chunk.sum());
    }

    @Test void testMulConstant() {
        SimpleDataChunkFloat chunk = new SimpleDataChunkFloat(1, 4);

        chunk.mul(2.0f);

        assertEquals(0.0f, chunk.sum());
    }

    @Test void testDivChunk() {
        SimpleDataChunkFloat chunk = new SimpleDataChunkFloat(1, 4);
        SimpleDataChunkFloat buffer = new SimpleDataChunkFloat(1, 4);

        for (int i = 0; i < 4; i++)
            buffer.set(i, (float) (i + 1));
        chunk.div(buffer);

        assertEquals(0.0f, chunk.sum());
    }

    @Test void testDivConstant() {
        SimpleDataChunkFloat chunk = new SimpleDataChunkFloat(1, 4);

        chunk.div(2.0f);

        assertEquals(0.0f, chunk.sum());
    }

    @Test void testDivByZeroChunk() {
        SimpleDataChunkFloat chunk = new SimpleDataChunkFloat(1, 4);
        SimpleDataChunkFloat buffer = new SimpleDataChunkFloat(1, 4);

        assertThrows(ArithmeticException.class, () -> chunk.div(buffer));
    }

    @Test void testDivByZeroConstant() {
        SimpleDataChunkFloat chunk = new SimpleDataChunkFloat(1, 4);

        assertThrows(ArithmeticException.class, () -> chunk.div(0.0f));
    }

}
