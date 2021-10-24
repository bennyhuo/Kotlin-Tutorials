package com.bennyhuo.kotlin.other.currying

import cn.tursom.core.curry.currying

/**
 * Created by benny.
 */

enum class LoggerLevel {
    DEBUG,
    INFO,
    WARN,
    ERROR
}

fun log(context: Any?, level: LoggerLevel, message: Any?) {
    println("$level - [$context]: $message")
}

fun main() {
    ::log.currying()(1234)(LoggerLevel.DEBUG)("Hello Curried from Kotlin !!!!!!!!!!")
}