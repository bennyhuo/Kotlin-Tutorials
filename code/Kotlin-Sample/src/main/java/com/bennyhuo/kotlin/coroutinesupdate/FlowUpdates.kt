package com.bennyhuo.kotlin.coroutinesupdate

import android.os.Handler
import android.os.HandlerThread
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.concurrent.thread

suspend fun main() {
    flowSamples()
}

suspend fun flowSamples() {
    flow {
        emit(1)
        emit(2)
    }.collect {
        println(it)
    }

    channelFlow {
        send(1)
        withContext(Dispatchers.IO) {
            send(2)
        }

        thread {
            trySendBlocking(3)
        }.join()
    }.collect {
        println(it)
    }

    callbackFlow {
        send(1)
        withContext(Dispatchers.IO) {
            send(2)
        }

        awaitClose {
            // nothing to do.
            println("closed")
        }
    }.collect {
        println(it)
    }
}

suspend fun callbackFlowSample() {
    val application = Application()
    application.start()


    val eventFlow = callbackFlow {
        val callback = Application.Callback {
            trySendBlocking(it)
        }
        application.registerCallback(callback)

        awaitClose {
            application.unregisterCallback(callback)
        }
    }

    val scope = CoroutineScope(Dispatchers.IO)
    scope.launch {
        eventFlow.collect {
            println(it)
        }
    }

    delay(5000)
    scope.cancel()

    delay(10_000)
    application.stop()
}


class Application {

    fun interface Callback {
        fun onCallBack(value: Int)
    }

    private val handlerThread = HandlerThread("App")

    private val handler by lazy {
        handlerThread.start()
        Handler(handlerThread.looper)
    }

    private val callbacks = CopyOnWriteArrayList<Callback>()

    fun registerCallback(callback: Callback) {
        this.callbacks += callback
    }

    fun unregisterCallback(callback: Callback) {
        this.callbacks -= callback
    }

    fun start() {
        runApp()
    }

    fun stop() {
        handlerThread.quit()
    }

    private var eventId = 0

    private fun runApp() {
        this.callbacks.forEach {
            it.onCallBack(eventId)
        }

        eventId++
        handler.postDelayed(::runApp, 1000)
    }

}

