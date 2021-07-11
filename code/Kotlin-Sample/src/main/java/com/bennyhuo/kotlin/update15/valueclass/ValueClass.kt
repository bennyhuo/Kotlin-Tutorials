package com.bennyhuo.kotlin.update15.valueclass

/**
 * Created by benny.
 */

inline class DurationForLegacy(val value: Long)

@JvmInline
value class DurationForNew(val value: Long)