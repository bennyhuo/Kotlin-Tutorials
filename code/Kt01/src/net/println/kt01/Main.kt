package net.println.kt01

/**
 * Created by benny on 10/11/16.
 */
fun main(args: Array<out String>){
    println("Hello World!!")
    println(Main("Bennyhuo", 0))
}

class Main(val title: String, val id: Int){
    override fun toString(): String {
        return "$id - $title"
    }
}