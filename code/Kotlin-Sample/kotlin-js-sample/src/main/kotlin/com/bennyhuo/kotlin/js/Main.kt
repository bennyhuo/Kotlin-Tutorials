package com.bennyhuo.kotlin.js

import com.bennyhuo.kotlin.js.deepcopy.DeepCopyable
import com.bennyhuo.kotlin.js.deepcopy.deepCopy
import com.bennyhuo.kotlin.js.kclassvalue.testKClass

/**
 * Created by benny at 2021/6/26 8:36.
 */
fun main() {
    console.log("Hello Js")

    val duration = Duration(10)
    println(duration)

    testKClass()

    val talk = Talk(
        "如何优雅地使用数据类",
        Speaker(
            "bennyhuo 不是算命的",
            1,
            Company(
                "猿辅导",
                Location(39.9, 116.3),
                District("北京郊区")
            )
        )
    )

    val copiedTalk = talk.deepCopy()
//        copiedTalk.name = "Kotlin 编译器插件：我们不期待"
//        copiedTalk.speaker.company = Company(
//            "猿辅导",
//            Location(39.9, 116.3),
//            District("华鼎世家对面")
//        )

    println(talk.speaker !== copiedTalk.speaker)
    println(talk.speaker.company !== copiedTalk.speaker.company)
    println(talk.speaker.company.location === copiedTalk.speaker.company.location)
}

data class District(var name: String)

data class Location(var lat: Double, var lng: Double)

data class Company(var name: String, var location: Location, var district: District): DeepCopyable

data class Speaker(var name: String, var age: Int, var company: Company): DeepCopyable

data class Talk(var name: String, var speaker: Speaker): DeepCopyable


inline class Span(val value: Long)
value class Duration(val value: Long)