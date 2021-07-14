tasks.register("hello") {
    println("Register task hello.")
    group = "test"
    doLast {
        println("Hello Task from precompiled task.")
    }
}