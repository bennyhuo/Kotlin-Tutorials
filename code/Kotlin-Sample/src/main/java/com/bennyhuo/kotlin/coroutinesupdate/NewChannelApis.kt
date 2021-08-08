package com.bennyhuo.kotlin.coroutinesupdate

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.withContext

suspend fun main() {
    val channel = Channel<Int>()
//    channel.trySend(1).isSuccess // offer
//    channel.tryReceive().getOrNull() // poll
//
//    channel.trySendBlocking(1) // sendBlocking

    withContext(Dispatchers.Default) {
        val producer = launch {
            (0 .. 5).forEach {
                channel.send(it)
            }
            channel.close()
        }

        val consumer = launch {
            while (isActive && !channel.isClosedForReceive) {
                select<Unit> {
                    channel.onReceiveCatching {
                        println("$it - ${it.isClosed}")
                    }
                }

                // or
                // val value = channel.onReceiveCatching()
            }
        }

        select<Unit> {
            consumer.onJoin
        }


        val consumer1 = produce<Int> {

        }

        val producer1 = actor<Int> {

        }


        BroadcastChannel<Int>(1)
        ConflatedBroadcastChannel<Int>()

    }
}