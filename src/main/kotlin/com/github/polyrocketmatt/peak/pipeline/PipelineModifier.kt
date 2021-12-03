package com.github.polyrocketmatt.peak.pipeline

import com.github.polyrocketmatt.game.tuple.NTuple

/**
 * An abstract pipeline modifier that receives an N-Tuple input and
 * produces an N-Tuple output.
 */
abstract class PipelineModifier<I : NTuple, O: NTuple>(val input: I) {

    abstract fun modify(): O

}