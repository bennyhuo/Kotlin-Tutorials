package net.println.kt12

/**
 * Created by benny on 12/1/16.
 */
data class BaseResult<Content>(val code: Int, val message: String, val content: Content)

data class Song(val id: Long, val name: String)

data class Singer(val id: Long, val name: String, val songs: List<Song>)
