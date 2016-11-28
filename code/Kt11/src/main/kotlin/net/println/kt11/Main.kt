package net.println.kt11

/**
 * Created by benny on 11/27/16.
 */
fun main(args: Array<String>) {
    val player: Player = Player()
    player.play("http://ws.stream.qqmusic.qq.com/C2000012Ppbd3hjGOK.m4a")
    player.pause()
    player.resume()
    player.seekTo(30000)
    player.stop()
}