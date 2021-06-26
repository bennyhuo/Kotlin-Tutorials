package com.bennyhuo.kotlin.update15.sealedinterfaces.since1_1

/**
 * Created by benny.
 * For Kotlin 1.1 to 1.4.
 */
sealed class List<out T>
object Nil: List<Nothing?>()
class Cons<T>(val value: T, val next: List<T>): List<T>()

tailrec fun <T> List<T>.forEach(block: (T) -> Unit) {
    when(this) {
        Nil -> return
        is Cons<T> -> {
            block(value)
            next.forEach(block)
        }
    }
}

fun <T> listOf(vararg values: T): List<T> {
    return values.reversedArray().fold(Nil as List<T>) { acc, t ->
        Cons(t, acc)
    }
}

fun main() {
    listOf(1,2,3,4).forEach {
        println(it)
    }
}