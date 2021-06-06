package com.bennyhuo.kotlin.scheduledtask

import io.reactivex.rxjava3.core.Observable
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.Executors

data class Config(val bannerId: String, val bannerUrl: String)

interface UpdateApi {

    @GET("/")
    suspend fun getConfigSuspend(): List<Config>

    @GET("/")
    fun getConfig(): Call<List<Config>>

    @GET("/")
    fun getConfigObservable(): Observable<List<Config>>
}

val updateApi by lazy {
    Retrofit.Builder()
        .client(
            OkHttpClient.Builder()
                .dispatcher(Dispatcher(Executors.newFixedThreadPool(3) {
                    Thread(it).also { it.isDaemon = true }
                }))
                .build()
        )
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://localhost:8080")
        .build()
        .create(UpdateApi::class.java)
}
