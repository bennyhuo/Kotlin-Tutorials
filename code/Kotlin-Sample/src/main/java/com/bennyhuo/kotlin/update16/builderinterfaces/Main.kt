package com.bennyhuo.kotlin.update16.builderinterfaces

/**
 * Created by benny.
 */
class Container<T> {
    fun setValue(value: T) {
        
    }
    
    fun getValue(): T = TODO()
}

fun <T> buildContainer(builder: Container<T>.() -> Unit) {
    
}

fun main() {
    buildContainer { 
        setValue(1)
    }
    
    buildList {
        add("Hello")
        add("World")
        set(1, "World 1")
    }
    
    buildMap { 
        put("Hello", 1)
    }
    
    buildSet { 
        add(1)
    }
    
    sequence { 
        yield(1)
    }
}