package com.github.polyrocketmatt.peak.math

import kotlin.random.Random

class StatisticUtils {

    companion object {

        fun cdf(pdf: FloatArray): FloatArray {
            var sum = 0.0f
            val result = FloatArray(pdf.size) { 0.0f }
            pdf.forEachIndexed { i,_ ->
                run {
                    sum += pdf[i]
                    result[i] = sum
                }
            }

            return result
        }

        //  This comes from https://stackoverflow.com/questions/42941091/sample-from-custom-distribution-in-r
        fun samplePDF(pdf: FloatArray): Float {
            val cdf = cdf(pdf)
            val random = Random.nextFloat()

            //  Find first float in CDF greater than random
            return cdf.firstOrNull { fl -> fl >= random } ?: cdf.last()
        }

    }

}