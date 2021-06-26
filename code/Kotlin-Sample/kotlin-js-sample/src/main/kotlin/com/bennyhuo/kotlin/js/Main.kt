package com.bennyhuo.kotlin.js

/**
 * Created by benny at 2021/6/26 8:36.
 */
fun main() {
    console.log("Hello Js")

    val duration = Duration(10)
    println(duration)
}

inline class Span(val value: Long)
value class Duration(val value: Long)