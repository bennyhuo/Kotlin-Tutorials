package com.bennyhuo.kotlin.update16.typeinferring

/**
 * Created by benny.
 */
abstract class AbsBuilder<B : AbsBuilder<B>> {
    val self: B = this as B

    private var id: Int? = null

    fun setId(id: Int): B {
        this.id = id
        return self
    }
}

open class MyBuilder1<B: MyBuilder1<B>> : AbsBuilder<B>() {

    private var name: String? = null

    fun setName(name: String): B {
        this.name = name
        return self
    }
}

class MyBuilder2 : MyBuilder1<MyBuilder2>() {
    private var age: Int? = null

    fun setAge(age: Int): MyBuilder2 {
        this.age = age
        return this
    }
}

fun main() {
    MyBuilder1().setId(1).setName("Hello") // error < 1.6 
    
    MyBuilder2().setId(2).setAge(10)
}