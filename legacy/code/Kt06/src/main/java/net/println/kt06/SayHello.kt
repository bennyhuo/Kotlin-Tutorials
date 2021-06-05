package net.println.kt06

/**
 * Created by benny on 10/15/16.
 */

enum class Lang(val hello: String){
    ENGLISH("Hello"),
    CHINESE("你好"),
    JAPANESE("こんにちは"),
    KOREAN("안녕하새요");

    fun sayHello(){
        println(hello)
    }

    companion object{
        fun parse(name: String):Lang{
            return Lang.valueOf(name.toUpperCase())
        }
    }
}

fun main(args: Array<String>) {
    if(args.size == 0) return
    val lang = Lang.parse(args[0])
    println(lang)
    lang.sayHello()
    lang.sayBye()
}

fun Lang.sayBye(){
    val bye = when(this){
        Lang.ENGLISH ->"Bye"
        Lang.CHINESE ->"再见"
        Lang.JAPANESE ->"さようなら"
        Lang.KOREAN -> "안녕히가세요"
    }
    println(bye)
}
