package net.println.kt10.kotlin

/**
 * Created by benny on 11/13/16.
 */
class LazyThreadSafeDoubleCheck private constructor(){
    companion object{
        val instance = lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            LazyThreadSafeDoubleCheck()
        }

        @Volatile var instance2: LazyThreadSafeDoubleCheck? = null

        fun getInstance() = {
            if(instance2 == null){
                synchronized(this){
                    if(instance2 == null)
                        instance2 = LazyThreadSafeDoubleCheck()
                }
            }
            instance2!!
        }
    }
}