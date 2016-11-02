package net.println.kt08

/**
 * Created by benny on 11/1/16.
 */
data class User(val login: String, val id: Long, val avatar_url: String){
    override fun toString(): String {
        return login
    }
}