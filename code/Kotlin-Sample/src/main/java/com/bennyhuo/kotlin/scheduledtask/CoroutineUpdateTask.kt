package com.bennyhuo.kotlin.scheduledtask

import com.bennyhuo.kotlin.api.updateApi
import kotlinx.coroutines.*

class CoroutineUpdateTask : UpdateTask {

    private var scope: CoroutineScope? = null

    override fun scheduleUpdate(interval: Long) {
        cancel()
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            while (isActive) {
                try {
                    updateApi.getConfigSuspend().let(::println)
                } catch (e: Exception) {
                    e.printStackTrace()
                    if (e is CancellationException) throw e
                }
                delay(interval)
            }
        }

        this.scope = scope
    }

    override fun cancel() {
        scope?.cancel()
        scope = null
    }
}