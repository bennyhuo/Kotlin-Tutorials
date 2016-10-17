package net.println.kt07

/**
 * Created by benny on 10/17/16.
 */
import kotlin.properties.Delegates

class ObservableUser {
    var name: String by Delegates.observable("no name") {
        d, old, new ->
        println("$old - $new")
    }
}

fun main(args: Array<String>) {
    val user = User()
    user.name = "Carl"
}