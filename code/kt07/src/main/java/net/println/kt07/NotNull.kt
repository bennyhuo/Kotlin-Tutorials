package net.println.kt07

import kotlin.properties.Delegates

class User {
    var name: String by Delegates.notNull()

    fun init(name: String) {
        this.name = name
    }
}

fun main(args: Array<String>) {
    val user = User()
    // user.name -> IllegalStateException
    user.init("Carl")
    println(user.name)
}