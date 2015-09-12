package net.println.kt03

/**
 * Created by benny on 10/11/16.
 */
fun main(args: Array<out String>){
    val user = User(0, "bennyhuo")
    println(user)

    HelloKotlin::class.constructors.map(::println)
}

class HelloKotlin{
    fun hello(){

    }
}