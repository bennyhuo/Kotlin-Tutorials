package com.bennyhuo.kotlin.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun main() {
    println(runTaskSuspend())

    try {
        println(sendRequestSuspend())
    } catch (e: Exception) {
        println("send request: $e")
    }

    val scope = CoroutineScope(Dispatchers.Default)
    scope.launch {
        try {
            println(sendRequestCancellableSuspend())
        } catch (e: Exception) {
            println("send request cancellable: $e")
        }
    }.join()

    scope.launch {
        startTaskAsFlow().collect {
            when(it) {
                OnComplete -> println("Done")
                is OnError -> println("Error: ${it.t}")
                is OnProgress -> println("Progress: ${it.value}")
                is OnResult<*> -> println("Result: ${it.value}")
            }
        }
    }.join()

//    delay(100)
//    scope.cancel()
}

//region single method
/**
 * Delayed task, like handler.post
 */
fun interface SingleMethodCallback {
    fun onCallback(value: String)
}

fun runTask(callback: SingleMethodCallback) {
   thread {
       Thread.sleep(100)
       callback.onCallback("runTask result")
   }
}

//region suspend
suspend fun runTaskSuspend() = suspendCoroutine<String> { continuation ->
    runTask {
        continuation.resume(it)
    }
}
//endregion
//endregion

//region success or failure
/**
 * Http request callback
 * Dialog yes/no
 */
interface SuccessOrFailureCallback {
    fun onSuccess(value: String)

    fun onError(t: Throwable)
}

fun sendRequest(callback: SuccessOrFailureCallback) {
    thread {
        try {
            Thread.sleep(100)
            callback.onSuccess("Success")
        } catch (e: Exception) {
            callback.onError(e)
        }
    }
}

//region suspend
suspend fun sendRequestSuspend() = suspendCoroutine<String> {
    continuation ->
    sendRequest(object: SuccessOrFailureCallback {
        override fun onSuccess(value: String) {
            continuation.resume(value)
        }

        override fun onError(t: Throwable) {
            continuation.resumeWithException(t)
        }
    })
}
//endregion

fun interface Cancellable {
    fun cancel()
}

fun sendRequestCancellable(callback: SuccessOrFailureCallback):Cancellable {
    val t = thread {
        try {
            Thread.sleep(1000)
            callback.onSuccess("Success")
        } catch (e: Exception) {
            callback.onError(e)
        }
    }
    return Cancellable {
        t.interrupt()
    }
}

//region cancellable suspend
suspend fun sendRequestCancellableSuspend() = suspendCancellableCoroutine<String> {
        continuation ->
    val cancellable = sendRequestCancellable(object: SuccessOrFailureCallback {
        override fun onSuccess(value: String) {
            continuation.resume(value)
        }

        override fun onError(t: Throwable) {
            continuation.resumeWithException(t)
        }
    })
    continuation.invokeOnCancellation {
        cancellable.cancel()
    }
}
//endregion
//endregion

//region multi path
/**
 * Download task callback
 */
interface MultiPathsCallback<T> {

    fun onProgress(value: Int)

    fun onResult(value: T)

    fun onError(t: Throwable)

    fun onComplete()

}

fun startTask(callback: MultiPathsCallback<String>): Cancellable {
    val t = thread {
        try {
            (0..100).forEach {
                Thread.sleep(10)
                callback.onProgress(it)
            }
            callback.onResult("Done")
            callback.onComplete()
        } catch (e: Exception) {
            callback.onError(e)
        }
    }
    return Cancellable {
        t.interrupt()
    }
}

//region suspend
sealed interface Event
class OnProgress(val value: Int): Event
class OnError(val t: Throwable): Event
class OnResult<T>(val value: T): Event
object OnComplete: Event

fun startTaskAsFlow() = callbackFlow {
    val cancellable = startTask(object: MultiPathsCallback<String> {
        override fun onProgress(value: Int) {
            trySendBlocking(OnProgress(value))
        }

        override fun onResult(value: String) {
            trySendBlocking(OnResult(value))
        }

        override fun onError(t: Throwable) {
            trySendBlocking(OnError(t))
        }

        override fun onComplete() {
            trySendBlocking(OnComplete)
        }
    })

    awaitClose {
        cancellable.cancel()
    }
}.conflate()
//endregion
//endregion