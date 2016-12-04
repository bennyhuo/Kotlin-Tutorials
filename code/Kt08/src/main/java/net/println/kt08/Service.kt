package net.println.kt08

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Created by benny on 11/1/16.
 */
interface GitHubService{


    @GET("/music/singer/follow?oper_type=list&qq=305779913")
    fun listFollowedSingers(): Call<BaseResult<List<SingerFollowInfo>>>

    @GET("/repos/enbandari/Kotlin-Tutorials/stargazers")
    fun getStarGazers(): Call<List<User>>
}

object Service{
    val gitHubService: GitHubService by lazy {
        Retrofit.Builder().baseUrl("http://content.wecar.map.qq.com").addConverterFactory(GsonConverterFactory.create()).build().create(GitHubService::class.java)
    }
}

fun main(args: Array<String>) {
    println(Service.gitHubService.listFollowedSingers().execute().body())
}
