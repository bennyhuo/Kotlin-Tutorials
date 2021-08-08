package com.bennyhuo.kotlin.tailrec

import kotlin.time.ExperimentalTime
import kotlin.time.measureTime


/**
 * Created by benny.
 */
@OptIn(ExperimentalTime::class)
fun main() {
//    timeCost { factorial(10u) }
//    timeCost { factorialTR(10u) }
//    timeCost { factorialLoop(10u) }
//
//    timeCost { fibonacciTR(1000u) }
//    timeCost { fibonacciLoop(1000u) }
    timeCost { fibonacci(50u) }
}

@OptIn(ExperimentalTime::class)
inline fun timeCost(block: () -> Unit) {
    measureTime {
        block()
    }.let {
        println(it.inWholeMilliseconds)
    }
}

//region factorial
fun factorial(n: UInt): UInt {
    if (n == 0u) return 1u
    return n * factorial(n - 1u)
}

fun factorialTR(n: UInt): UInt {
    tailrec fun factorialInner(n: UInt, result: UInt): UInt {
        if (n <= 1u) return result
        return factorialInner(n - 1u, result * n)
    }
    return factorialInner(n, 1u)
}

fun factorialLoop(n: UInt): UInt {
    var result = 1u
    for (i in 1u..n) {
        result *= i
    }
    return result
}
//endregion

//region fibonacci
fun fibonacci(n: UInt): UInt {
    if (n < 2u) return n
    return fibonacci(n - 1u) + fibonacci(n - 2u)
}

fun fibonacciTR(n: UInt): UInt {
    if (n < 2u) return n

    tailrec fun fibonacciInner(n: UInt, a: UInt, b: UInt): UInt {
        if (n < 2u) return b
        return fibonacciInner(n - 1u, b, a + b)
    }
    return fibonacciInner(n, 0u, 1u)
}

fun fibonacciLoop(n: UInt): UInt {
    if (n < 2u) return n

    var a = 0u
    var b = 1u
    for (i in 2u..n) {
        val tmp = b
        b = a + b
        a = tmp

        //region same but confusing
        // b = a + b
        // a = b - a
        //endregion
    }
    return b
}
//endregion

//region List
sealed class List<out T> {
    object Nil : List<Nothing?>()
    class Cons<T>(val value: T, val next: List<T>) : List<T>()
}

tailrec fun <T> List<T>.forEach(block: (T) -> Unit) {
    when (this) {
        List.Nil -> return
        is List.Cons<T> -> {
            block(value)
            next.forEach(block)
        }
    }
}

fun <T> listOf(vararg values: T): List<T> {
    return values.reversedArray().fold(List.Nil as List<T>) { acc, t ->
        List.Cons(t, acc)
    }
}
//endregion