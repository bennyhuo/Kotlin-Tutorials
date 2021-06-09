package com.bennyhuo.kotlin.optin

import com.bennyhuo.kotlin.optinsample.SampleApi
import com.bennyhuo.kotlin.optinsample.UnsupportedApi


@UnsupportedApi
fun main() {
    val sampleApi = SampleApi()
    sampleApi.stableAndFreeToUse()
    sampleApi.unstableAndPayAttention()
    sampleApi.removedApiDontUse()
    sampleApi.newApiForDeprecated("a", "b")
}

@UnsupportedApi
fun main2() {
    main()
}