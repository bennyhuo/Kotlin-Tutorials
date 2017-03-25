package net.println.kotlin.mybatis

import sun.misc.Unsafe

/**
 * Created by benny on 3/26/17.
 */

class Test{
    init {
        println("init")
    }

    companion object{
        init {
            println("cinit")
        }
    }
}

fun main(args: Array<String>) {
    val field = Unsafe::class.java.getDeclaredField("theUnsafe")
    field.isAccessible = true
    val unsafe = field.get(null) as Unsafe
    unsafe.allocateInstance(Test::class.java)

}