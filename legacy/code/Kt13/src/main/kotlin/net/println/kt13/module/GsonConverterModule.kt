package net.println.kt13.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by benny on 12/11/16.
 */
@Module(includes = arrayOf(GsonModule::class))
class GsonConverterModule {
    @Singleton @Provides fun converter(gson: Gson): Converter.Factory = GsonConverterFactory.create(gson)
}