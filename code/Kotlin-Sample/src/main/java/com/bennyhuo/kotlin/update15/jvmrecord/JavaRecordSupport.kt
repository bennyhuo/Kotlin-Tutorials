package com.bennyhuo.kotlin.update15.jvmrecord

/**
 * Created by benny at 2021/6/20 9:35.
 */
open class Super
@JvmRecord
data class KotlinPerson(val name: String, val age: Int) {

}

fun main() {
    val person = JavaRecordSample.Person("bennyhuo", 1)

}