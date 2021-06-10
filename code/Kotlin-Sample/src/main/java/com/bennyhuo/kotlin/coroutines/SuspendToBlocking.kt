package com.bennyhuo.kotlin.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.future.future
import kotlin.coroutines.EmptyCoroutineContext

suspend fun suspendableApi(): String {
    if(Math.random() > 0.5) delay(1000)

    return "bilibli 关注我：bennyhuo 不是算命的"
}

fun futureMain() {
    val scope = CoroutineScope(Dispatchers.IO)

    scope.future {
        suspendableApi()
    }.join()
        .let(::println)
}

fun main() {
    val value = runBlocking {
        suspendableApi()
    }
    println(value)

    futureMain()
}

//suspend fun main() {
//    val value = suspendableApi()
//    println(value)
//}