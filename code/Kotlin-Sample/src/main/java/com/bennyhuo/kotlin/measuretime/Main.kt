package com.bennyhuo.kotlin.measuretime

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.fixedRateTimer
import kotlin.time.*


inline fun timeCost(block: ()-> Unit): Long {
    val startTime = System.currentTimeMillis()
    block()
    return System.currentTimeMillis() - startTime
}

inline fun <T> timeCostReturns(block: () -> T): Pair<T, Long> {
    val startTime = System.currentTimeMillis()
    val result = block()
    return result to (System.currentTimeMillis() - startTime)
}

@OptIn(ExperimentalTime::class)
suspend fun main() {

    val duration = measureTime {
        Thread.sleep(100)
    }
    println(duration)

    val timeCost = timeCost {
        Thread.sleep(100)
    }
    println(timeCost)

//    val (value, duration2) = measureTimedValue {
//        Thread.sleep(500)
//
//        1000
//    }

    val (value, duration2) = timeCostReturns {
        Thread.sleep(500)

        1000
    }

    println(value)
    println(duration2)

    val scope = CoroutineScope(Dispatchers.IO)
    scope.launch {

        val duration = measureTime {
            delay(1000)
        }
        println(duration)

        val mark = TimeSource.Monotonic.markNow()
        delay(1000)
        println(mark.elapsedNow())
    }.join()



}