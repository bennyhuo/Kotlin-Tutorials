package com.bennyhuo.kotlin.nonfinalfunction

import kotlin.random.Random

/**
 * Created by benny.
 */
open class Base {

    open val name = "Base"

    val id = generateId()

    init {
        println("Base: id: $id, name: ${xyz()}")
    }

    open fun generateId(): Int {
        return Random(System.currentTimeMillis()).nextInt(10000000)
    }

    fun xyz(): String {
        generateId()
        return this.name
    }

}

class Derived: Base() {

    override val name = "Derived"

    init {
        println("Derived: id: $id, name: $name")
    }

    override fun generateId(): Int {
        return 1
    }

}

fun main() {
    val d = Derived()
}
