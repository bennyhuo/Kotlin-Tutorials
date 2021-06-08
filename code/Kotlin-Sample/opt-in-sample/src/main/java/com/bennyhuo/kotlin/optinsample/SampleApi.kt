package com.bennyhuo.kotlin.optinsample

class SampleApi {

    fun stableAndFreeToUse() {

    }

    @UnstableApi
    fun unstableAndPayAttention() {

    }

    @Deprecated(
        message = "Use stableAndFreeToUse instead.",
        ReplaceWith("stableAndFreeToUse()")
    )
    @UnsupportedApi
    fun removedApiDontUse(): Unit = error("removed.")

    fun newApiForDeprecated(key: String, value: String) {

    }

    @Deprecated(
        message = "Use newApiForDeprecated instead.",
        replaceWith = ReplaceWith("newApiForDeprecated(key, value)")
    )
    fun deprecatedDontUse(key: String, value: String) {

    }

}