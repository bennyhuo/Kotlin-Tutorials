package net.println.kt13.module

import dagger.Module
import dagger.Provides
import net.println.kt13.config.Settings
import javax.inject.Singleton

/**
 * Created by benny on 12/11/16.
 */
@Module
class BaseUrlModule {
    companion object{
        //测试环境
        const val DEBUG_URL = "https://api.github.com"

        //线上环境
        const val RELEASE_URL = "https://api.github.com"
    }

    @Singleton @Provides fun baseUrl(): String = if(Settings.DEBUG) DEBUG_URL else RELEASE_URL
}