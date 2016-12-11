package net.println.kt13.module

import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * Created by benny on 12/11/16.
 */
@Module(includes = arrayOf(CacheModule::class))
class OkHttpClientModule {
    @Singleton @Provides fun okHttpClient(cache: Cache): OkHttpClient
            = OkHttpClient.Builder().cache(cache).build()
}