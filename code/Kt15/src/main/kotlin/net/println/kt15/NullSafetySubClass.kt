package net.println.kt15

import java.util.*

/**
 * Created by benny on 12/17/16.
 */
class NullSafetySubClass : NullSafetyAbsClass(){
    override fun formatDate(date: Date): String {
        return date.toString()
    }
}

fun main(args: Array<String>) {
    val nullSafetySubClass = NullSafetySubClass()
    println(nullSafetySubClass.formatDate(Date()))
    println(nullSafetySubClass.formatTime(Date()))
}