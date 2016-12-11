package net.println.kt13.module

import dagger.Module
import dagger.Provides
import okhttp3.Cache
import java.io.File
import javax.inject.Singleton

/**
 * Created by benny on 12/11/16.
 */
@Module
class CacheModule {
    companion object{
        const val CACHE_DIR = "./cache"
        const val CACHE_SIZE = 10 * 1024 * 1024L
    }

    @Singleton @Provides fun cache(): Cache = Cache(File(CACHE_DIR), CACHE_SIZE)
}