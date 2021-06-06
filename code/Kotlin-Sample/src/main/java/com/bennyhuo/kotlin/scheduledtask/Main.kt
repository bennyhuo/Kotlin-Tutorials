package com.bennyhuo.kotlin.scheduledtask

import kotlinx.coroutines.delay

suspend fun main() {

   val taskClasses = listOf(
      TimerUpdateTask::class,
      RxJavaUpdateTask::class,
      HandlerUpdateTask::class,
      CoroutineUpdateTask::class
   )

   taskClasses.forEach {
      val task = it.java.newInstance()
      println("${it.simpleName} start")
      task.scheduleUpdate(1000)
      delay(5000)
      task.cancel()
      println("${it.simpleName} cancel")
   }

}