package net.println.kt13

import dagger.Component
import net.println.kt13.module.RetrofitModule
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by benny on 12/11/16.
 */
@Singleton
@Component(modules = arrayOf(RetrofitModule::class))
interface RESTFulComponent {
    fun retrofit(): Retrofit
}