package com.bennyhuo.kotlin.kclassvalue

import kotlin.concurrent.thread

/**
 * Created by benny.
 */
fun main() {
//    val anyClass = Any::class
//    val anyClass2 = Any::class
//    println(anyClass == anyClass2)
//    println(anyClass === anyClass2)

    testConcurrentIssue()
}

//region concurrent issue.
var concurrentValue = 0

fun safeIncrement() {
    synchronized(Any::class) {
        concurrentValue++
    }
}

fun testConcurrentIssue() {
    List(100) {
        thread(start = false) {
            for (i in 0 until 1000) {
                safeIncrement()
            }
        }
    }.onEach(Thread::start)
        .forEach(Thread::join)

    println(concurrentValue)
}
//endregion