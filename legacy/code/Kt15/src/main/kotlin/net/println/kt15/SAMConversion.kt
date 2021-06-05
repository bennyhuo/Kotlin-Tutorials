package net.println.kt15

/**
 * Created by benny on 12/17/16.
 */
fun main(args: Array<String>) {
    val samInJava = SAMInJava()
    val lambda = {
        println("Hello")
    }
    samInJava.addTask(lambda)
    samInJava.addTask(lambda)
    samInJava.addTask(lambda)
    samInJava.addTask(lambda)
    samInJava.addTask(lambda)

    samInJava.removeTask(lambda)
    samInJava.removeTask(lambda)
    samInJava.removeTask(lambda)
    samInJava.removeTask(lambda)
    samInJava.removeTask(lambda)
}