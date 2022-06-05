package com.github.polyrocketmatt.peak.math

class IntTuple(vararg values: Int) : Tuple<Int>(values.toTypedArray())

fun intTupleOf(vararg values: Int) = IntTuple(*values)

@JvmName("intTupleOfArray")
fun intTupleOf(values: IntArray) = IntTuple(*values)

class FloatTuple(vararg values: Float) : Tuple<Float>(values.toTypedArray())

fun floatTupleOf(vararg values: Float) = FloatTuple(*values)

@JvmName("floatTupleOfArray")
fun floatTupleOf(values: FloatArray) = FloatTuple(*values)

class LongTuple(vararg values: Long) : Tuple<Long>(values.toTypedArray())

fun longTupleOf(vararg values: Long) = LongTuple(*values)

@JvmName("longTupleOfArray")
fun longTupleOf(values: LongArray) = LongTuple(*values)

open class Tuple<T>(val values: List<T>) {

    constructor(values: Array<T>) : this(values.asList())

    fun size(): Int = values.size

    companion object {

        fun <T> join(a: Tuple<T>, b: Tuple<T>) = a + b

    }

    operator fun plus(other: Tuple<T>): Tuple<T> = Tuple(values + other.values)

    operator fun minus(other: Tuple<T>): Tuple<T> {
        val difference = arrayListOf<T>()
        values.forEach { value -> if (!other.values.contains(value)) difference.add(value) }

        return Tuple(difference)
    }

    operator fun get(index: Int): T = values[index]

}