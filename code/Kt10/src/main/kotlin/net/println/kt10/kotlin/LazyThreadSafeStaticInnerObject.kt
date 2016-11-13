package net.println.kt10.kotlin

/**
 * Created by benny on 11/13/16.
 */
class LazyThreadSafeStaticInnerObject private constructor(){
    companion object{
        fun getInstance() = Holder.instance
    }

    private object Holder{
        val instance = LazyThreadSafeStaticInnerObject()
    }
}