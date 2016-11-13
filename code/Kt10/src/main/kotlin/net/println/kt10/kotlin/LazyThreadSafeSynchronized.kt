package net.println.kt10.kotlin

/**
 * Created by benny on 11/13/16.
 */
class LazyThreadSafeSynchronized private constructor(){
    companion object{
        var instance: LazyThreadSafeSynchronized? = null

        @Synchronized
        fun getInstance() = {
            if(instance == null) instance = LazyThreadSafeSynchronized()
            instance!!
        }
    }
}