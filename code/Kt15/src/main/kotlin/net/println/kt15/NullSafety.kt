package net.println.kt15

/**
 * Created by benny on 12/17/16.
 */
fun main(args: Array<String>) {
    val nullSafetyJava = NullSafetyJava()
    val data: String = nullSafetyJava.data
    val dataCanBeNull: String? = nullSafetyJava.data
    println(data)
}