package com.bennyhuo.kotlin.scheduledtask

interface UpdateTask {

    fun scheduleUpdate(interval: Long)

    fun cancel()

}

