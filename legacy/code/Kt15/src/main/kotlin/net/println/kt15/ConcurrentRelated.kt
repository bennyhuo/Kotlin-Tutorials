package net.println.kt15

/**
 * Created by benny on 12/17/16.
 */
@Volatile var count: Int = 0

fun count(){
    synchronized(count){
        count++
    }
}
