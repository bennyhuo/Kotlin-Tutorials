package net.println.kotlin.mybatis

import net.println.kotlin.mybatis.annotations.PoKo

/**
 * Created by benny on 3/25/17.
 */
@PoKo
data class User (var id: Int, var username: String = "", var age: Int, var passwd: String = "")

fun main(args: Array<String>) {
    //println(User::class.java.newInstance())
}