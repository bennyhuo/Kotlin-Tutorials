package com.bennyhuo.kotlin.deprecatedsample

object Api {

    fun newApiForDeprecated(key: String, value: String) {

    }

    @Deprecated(
        message = "Use newApiForDeprecated instead.",
        replaceWith = ReplaceWith(
            expression = "NewApi.newApiForDeprecated(key, value, default)",
            imports = ["com.bennyhuo.kotlin.deprecatedsample.v2.NewApi"]
        )
    )
    fun deprecatedDontUse(key: String, value: String) {

    }

}