package com.bennyhuo.kotlin.update16.contextreceiver

import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow

interface Logger {
    fun log(message: Any?)
}

object StdOutLogger : Logger {
    override fun log(message: Any?) {
        println(message)
    }
}

object StdErrLogger : Logger {
    override fun log(message: Any?) {
        System.err.println(message)
    }
}

object JsonLogger : Logger {
    override fun log(message: Any?) {
        println(Gson().toJson(message))
    }
}

fun <E> List<E>.loggerOnEach(logger: Logger): List<E> {
    forEach {
        logger.log(it)
    }
    return this
}

context(Logger)
fun <E> List<E>.loggerOnEach(): List<E> {
    forEach {
        log(it)
    }
    return this
}

class User(val id: Int, val name: String)

fun main() {

    listOf(1, 2, 3).loggerOnEach(StdOutLogger).asFlow()
    listOf("1", "2", "3").loggerOnEach(StdOutLogger)
    "Hello".toList().loggerOnEach(StdOutLogger)

    with(StdErrLogger) {
        listOf(1, 2, 3).loggerOnEach()
        listOf("1", "2", "3").loggerOnEach()
        "Hello".toList().loggerOnEach()
    }

    with(JsonLogger) {
        listOf(
            User(0, "benny"),
            User(1, "BENNY"),
            User(2, "Benny")
        ).loggerOnEach()
    }

}
