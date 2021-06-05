package net.println.kt08

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Created by benny on 11/1/16.
 */
interface GitHubService{

    @GET("/repos/enbandari/Kotlin-Tutorials/stargazers")
    fun getStarGazers(): Call<List<User>>
}

object Service{
    val gitHubService: GitHubService by lazy {
        Retrofit.Builder().baseUrl("https://api.github.com").addConverterFactory(GsonConverterFactory.create()).build().create(GitHubService::class.java)
    }
}

fun main(args: Array<String>) {
    Service.gitHubService.getStarGazers().execute().body().map(::println)
}