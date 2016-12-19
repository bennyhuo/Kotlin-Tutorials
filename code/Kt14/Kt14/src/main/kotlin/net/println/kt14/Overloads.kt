package net.println.kt14

/**
 * Created by benny on 12/18/16.
 */
class Overloads {
    @JvmOverloads
    fun overloaded(a: Int, b: Int = 0, c: Int = 1){
        println("$a, $b, $c")
    }
}