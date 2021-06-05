package net.println.kt12

import java.io.File

/**
 * Created by benny on 12/1/16.
 */
fun main(args: Array<String>) {
    val json = File("result_singer_field_loss.json").readText()
    val result : BaseResult<Singer> = ApiFactory.api.getSingerFromJson(json)
    println(result.content.name.isEmpty())
}