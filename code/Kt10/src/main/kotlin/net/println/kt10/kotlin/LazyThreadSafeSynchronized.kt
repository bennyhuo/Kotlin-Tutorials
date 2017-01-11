package net.println.kt10.kotlin

/**
 * Created by benny on 11/13/16.
 */
class LazyThreadSafeSynchronized private constructor() {
    companion object {
       private var instance: LazyThreadSafeSynchronized? = null

        @Synchronized
        fun get(): LazyThreadSafeSynchronized{
            if(instance == null) instance = LazyThreadSafeSynchronized()
            return instance!!
        }
    }

}