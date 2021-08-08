package com.bennyhuo.kotlin.coroutines

import kotlinx.coroutines.*
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

/**
 * Created by benny.
 */
suspend fun main() {

    log(1)

    val job = GlobalScope.launch(
        context = CoroutineName("Hello") + MyNewContinuationInterceptor2() + MyNewContinuationInterceptor1(),
        start = CoroutineStart.UNDISPATCHED
    ) {
        log(2)
        delay(1000)
        log(3)
        log(coroutineContext[ContinuationInterceptor])
    }

    log(4)

    job.join()

    log(5)
}

private class MyNewContinuationInterceptor1 : ContinuationInterceptor {

    companion object Key : CoroutineContext.Key<MyNewContinuationInterceptor1>

    override val key: CoroutineContext.Key<*> = MyNewContinuationInterceptor1

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return object : Continuation<T> {
            override val context: CoroutineContext = continuation.context

            override fun resumeWith(result: Result<T>) {
                log("<MyContinuation1> $result")
                thread(name = "MyMain1") {
                    continuation.resumeWith(result)
                }
            }
        }
    }
}

private class MyNewContinuationInterceptor2 : ContinuationInterceptor {

    companion object Key : CoroutineContext.Key<MyNewContinuationInterceptor2>

    override val key: CoroutineContext.Key<*> = MyNewContinuationInterceptor2

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return object : Continuation<T> {
            override val context: CoroutineContext = continuation.context

            override fun resumeWith(result: Result<T>) {
                log("<MyContinuation2> $result")
                thread(name = "MyMain2") {
                    continuation.resumeWith(result)
                }
            }
        }
    }
}

fun log(message: Any?) {
    println("[${Thread.currentThread().name}] $message")
}