package com.bennyhuo.kotlin.basics

import kotlinx.datetime.toInstant

/**
 * Created by benny.
 */
fun main() {
    val result = 2 `**` 3
    println(result)
    val history = History("2016-02-19T00:00:00.000Z".toInstant().toEpochMilliseconds(), "Kotlin 1.0 release!")
    println(history.`when`())
}

fun `fun`() {

}

@JvmName("classInJava")
fun `class`() {

}

@JvmName("This_is_a_test")
fun `This is a test`() {

}

infix fun Number.`**`(power: Number): Double {
    return Math.pow(this.toDouble(), power.toDouble())
}