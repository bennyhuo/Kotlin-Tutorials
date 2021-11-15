package com.bennyhuo.kotlin.coroutines

import kotlinx.coroutines.runBlocking

/**
 * Created by benny.
 */
fun a0() {
    
}

suspend fun a1() {
    
}

fun b0(): Int {
    TODO()
}

suspend fun b1(): Int {
    TODO()
}

fun Int.c0(block: (Int) -> String): String {
    TODO()
}

fun c00(i: Int, block: (Int) -> String): String {
    TODO()
}

suspend fun Int.c1(block: (Int) -> String): String {
    TODO()
}

suspend fun c11(i: Int, block: (Int) -> String): String {
    TODO()
}

fun x(block: suspend Int.((Int) -> String) -> String) {
    runBlocking { 
        block(1) {
            it.toString()
        }
    }
}

fun Int.println() {
    
}

fun main() {
    val typeA0 = ::a0 // () -> Unit
    val typeA1 = ::a1 // suspend () -> Unit
    
    val typeB0 = ::b0 // () -> Int
    val typeB1 = ::b1 // suspend () -> Int
    
    val typeC0 = Int::c0 // Int.((Int) -> String) -> String
    val typeC0x = 1::c0 // ((Int) -> String) -> String
    val typeC00 = ::c00 // (Int, (Int) -> String) -> String
    val typeC1 = Int::c1 // suspend Int.((Int) -> String) -> String
    val typeC11 = ::c11 // suspend (Int, (Int) -> String) -> String
    
    x(typeC1)
    x(typeC11)
    
    listOf(1,2,3).forEach(Int::println)
    listOf(1,2,3).forEach(::println)
}