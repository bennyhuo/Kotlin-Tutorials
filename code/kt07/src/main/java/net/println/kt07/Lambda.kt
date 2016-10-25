package net.println.kt07

import rx.Observable
import java.io.File

fun main(args: Array<String>) {
    //1. 统计单词的个数
    //2. 统计字母的个数
    val text = File(ClassLoader.getSystemClassLoader().getResource("input").path).readText()
    println(text)

    Observable.from(text.split(Regex("""\s"""))).filter{
        it.matches(Regex("""[a-zA-z,.'-]+"""))
    }.flatMap { Observable.from(it.toCharArray().asIterable()) }.groupBy { it }.subscribe{
        observable ->observable.count().subscribe{
            println("${observable.key} -> $it")
        }
    }
}



