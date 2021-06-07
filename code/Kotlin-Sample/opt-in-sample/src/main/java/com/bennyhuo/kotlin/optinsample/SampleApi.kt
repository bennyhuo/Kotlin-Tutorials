package com.bennyhuo.kotlin.optinsample

class SampleApi {

    fun stableAndFreeToUse() {

    }

    @UnstableApi
    fun unstableAndPayAttention() {

    }

    @UnsupportedApi
    fun removedApiDontUse(): Unit = error("removed.")

}