package com.bennyhuo.kotlin.js.kclassvalue

/**
 * Created by benny.
 */
fun testKClass() {
    val anyClass = Any::class
    val anyClass2 = Any::class

    println(anyClass === anyClass2)

    val personClass = Person::class
    val personClass2 = Person::class
    println(personClass === personClass2)
}

class Person