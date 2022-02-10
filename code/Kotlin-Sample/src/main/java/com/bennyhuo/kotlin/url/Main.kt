package com.bennyhuo.kotlin.url

import okhttp3.HttpUrl

/**
 * Created by benny.
 */
fun appendQuery(url: String, name: String, value: String): String {
    val httpUrl = HttpUrl.parse(url)!!
    println(httpUrl.fragment())
    if (httpUrl.queryParameter(name) == null) {
        return httpUrl.newBuilder().addQueryParameter(name, value)
            .build().toString()
    } else {
        return url
    }
}

fun getUrl(): String {
    return "http://local.bennyhuo.com:8000/#/index?c=4"
}

fun main() {
    println(appendQuery("http://www.baidu.com", "a", "1"))
    println(appendQuery(getUrl(), "c", "3"))
}

