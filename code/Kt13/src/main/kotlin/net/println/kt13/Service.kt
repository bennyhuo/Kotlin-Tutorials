package net.println.kt08

import net.println.kt13.DaggerRESTFulComponent
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by benny on 11/1/16.
 */
interface GitHubService{

    @GET("/repos/enbandari/Kotlin-Tutorials/stargazers")
    fun getStarGazers(@Query("page") page: Int = 1, @Query("per_page") pageSize: Int = 20): Call<List<User>>
}

object Service {
    val api: GitHubService by lazy {
        DaggerRESTFulComponent
                .builder()
                .build()
                .retrofit()
                .create(GitHubService::class.java)
    }
}

fun main(args: Array<String>) {
    Service.api.getStarGazers().execute().body().map(::println)
}