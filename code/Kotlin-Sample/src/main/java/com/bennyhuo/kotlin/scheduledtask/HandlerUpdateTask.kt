package com.bennyhuo.kotlin.scheduledtask

import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import com.bennyhuo.kotlin.api.Config
import com.bennyhuo.kotlin.api.updateApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HandlerUpdateTask : UpdateTask {

    private var handlerThread: HandlerThread? = null
    private var handler: Handler? = null

    private fun Handler.triggerUpdateNext(delay: Long) {
        val message = Message.obtain(handler) {
            updateApi.getConfig().enqueue(object : Callback<List<Config>> {
                override fun onResponse(
                    call: Call<List<Config>>,
                    response: Response<List<Config>>
                ) {
                    println(response.body())
                }

                override fun onFailure(call: Call<List<Config>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
            triggerUpdateNext(delay)
        }
        message.obj = this@HandlerUpdateTask
        sendMessageDelayed(message, delay)
    }

    override fun scheduleUpdate(interval: Long) {
        cancel()

        val handlerThread = HandlerThread("handler-thread")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        handler.triggerUpdateNext(interval)

        this.handlerThread = handlerThread
        this.handler = handler
    }

    override fun cancel() {
        handler?.removeCallbacksAndMessages(this)
        handler = null
        handlerThread?.quit()
        handlerThread = null
    }
}