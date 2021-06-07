package com.bennyhuo.kotlin.optinsample

@RequiresOptIn("This api is not stable!!", RequiresOptIn.Level.WARNING)
annotation class UnstableApi

@RequiresOptIn("This api is removed.", RequiresOptIn.Level.ERROR)
annotation class UnsupportedApi
