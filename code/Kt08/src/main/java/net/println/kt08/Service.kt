package net.println.kt08

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by benny on 11/1/16.
 */
interface GitHubService{

    @GET("/repos/{user}/{repo}/stargazers")
    fun getStarGazers(@Path("user") user: String, @Path("repo") repo: String): Call<List<User>>
}

object Service{
    val gitHub: GitHubService by lazy {
        Retrofit.Builder().baseUrl("https://api.github.com").addConverterFactory(GsonConverterFactory.create()).build().create(GitHubService::class.java)
    }
}

fun main(args: Array<String>) {
    Service.gitHub.getStarGazers("enbandari", "Kotlin-Tutorials").execute().body().map(::println)
}

