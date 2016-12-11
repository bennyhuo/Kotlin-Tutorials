package net.println.kt13.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by benny on 12/11/16.
 */
@Module
class GsonModule {

    @Singleton @Provides fun gson(): Gson = Gson()
}