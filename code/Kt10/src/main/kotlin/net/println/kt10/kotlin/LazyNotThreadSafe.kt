package net.println.kt10.kotlin

/**
 * Created by benny on 11/13/16.
 */
class LazyNotThreadSafe {
    companion object{
        val instance by lazy(LazyThreadSafetyMode.NONE) {
            LazyNotThreadSafe()
        }

        var instance2: LazyNotThreadSafe? = null

        fun getInstance() = {
            if(instance2 == null){
                instance2 = LazyNotThreadSafe()
            }
            instance2!!
        }
    }
}