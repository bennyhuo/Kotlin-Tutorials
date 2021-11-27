package com.bennyhuo.kotlin.update16.annotations

import kotlin.reflect.full.findAnnotations

/**
 * Created by benny.
 */
annotation class X

@Target(AnnotationTarget.TYPE_PARAMETER)
annotation class BoxContent

class Box<@BoxContent T> {}

@Repeatable
annotation class Tag(val value: String)

annotation class TagContainer(val value: Array<Tag1>)

@JvmRepeatable(TagContainer::class)
annotation class Tag1(val value: String)

@Tag("hello")
@Tag("world")
@Tag("world")
@Tag("world")
@Tag("world")
@Tag("world")
class Abc

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    val x = X()
    
    Abc::class.findAnnotations(Tag::class).forEach { println(it.value) }
}