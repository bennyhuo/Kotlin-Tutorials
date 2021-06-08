package com.bennyhuo.kotlin.optin

import com.bennyhuo.kotlin.deprecatedsample.UnsupportedApi

@UnsupportedApi
fun main() {
    val sampleApi = Api()
    sampleApi.stableAndFreeToUse()
    sampleApi.unstableAndPayAttention()
    sampleApi.removedApiDontUse()
    sampleApi.newApiForDeprecated("a", "b")
}

@UnsupportedApi
fun main2() {
    main()
}