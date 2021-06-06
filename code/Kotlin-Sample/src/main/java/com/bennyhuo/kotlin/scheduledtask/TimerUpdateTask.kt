package com.bennyhuo.kotlin.scheduledtask

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class TimerUpdateTask : UpdateTask {

    private var timer: Timer? = null

    override fun scheduleUpdate(interval: Long) {
        cancel()

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
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
            }
        }, 0L, interval)

        this.timer = timer
    }

    override fun cancel() {
        timer?.cancel()
        timer = null
    }
}