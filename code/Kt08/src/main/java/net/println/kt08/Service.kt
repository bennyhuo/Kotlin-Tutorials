package net.println.kt08

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by benny on 10/28/16.
 */

interface GithubService {

    @GET("/repos/{user}/{repo}/stargazers")
    fun getStarGazers(@Path("user") user: String, @Path("repo") repo: String): Call<List<User>>
}

object ServiceFactory {
    val gitHub: GithubService by lazy {
        Retrofit.Builder().baseUrl("https://api.gitHub.com").addConverterFactory(GsonConverterFactory.create()).build().create(GithubService::class.java)
    }
}

fun main(args: Array<String>) {
    ServiceFactory.gitHub.getStarGazers("enbandari", "Kotlin-Tutorials").execute().body().map(::println)
}