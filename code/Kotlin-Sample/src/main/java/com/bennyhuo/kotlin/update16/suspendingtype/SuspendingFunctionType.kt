package com.bennyhuo.kotlin.update16.suspendingtype

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by benny.
 */
class MyClickAction0 : () -> Unit {
    override fun invoke() {
    }

}

class MyClickAction1 : suspend () -> Unit {
    override suspend fun invoke() {
        TODO()
    }
}

fun launchOnClick(action: suspend () -> Unit) {}

fun getSuspending(suspending: suspend () -> Unit) {}

fun suspending() {}

fun test(regular: () -> Unit) {
    getSuspending { }           // OK
    getSuspending(::suspending) // 1.4 OK
    getSuspending(regular)      // 1.6 OK
    
    val x: () -> Unit = { }
    val y: suspend () -> Unit = { }
    
    GlobalScope.launch { 
        
    }
    
    suspend { 
        
    }
}

fun main() {
    launchOnClick(MyClickAction0())
    launchOnClick(MyClickAction1())
    
    val clickAction1: suspend () -> Unit = MyClickAction1()
    // val clickAction0: suspend () -> Unit = MyClickAction0()
    
    val x: suspend () -> Unit = {  }
}