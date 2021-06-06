package com.bennyhuo.kotlin.coroutinesupdate

import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class)
suspend fun main() {
    val jobInGlobal = GlobalScope.launch() {
        println("扶我起来！我还能再战两年！")
    }.join()
    GlobalScope.cancel()

    val scope = CoroutineScope(Dispatchers.IO)
    val job = scope.launch {

    }

    job.cancel()
    scope.cancel()
}