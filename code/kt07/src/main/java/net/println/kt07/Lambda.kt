package net.println.kt07

import rx.Observable
import java.io.File
import java.util.concurrent.Executors

fun main(args: Array<String>) {
    val text = File(ClassLoader.getSystemResource("input").path).readText()
    Observable.from(text.toCharArray().asIterable()).filter { !it.isWhitespace() }.groupBy { it }.subscribe {
        o -> o.count().subscribe{
            println("${o.key} -> $it")
        }
    }
}




