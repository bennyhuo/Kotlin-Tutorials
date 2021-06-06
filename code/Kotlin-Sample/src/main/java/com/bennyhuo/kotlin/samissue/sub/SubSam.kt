package com.bennyhuo.kotlin.samissue.sub

import com.bennyhuo.kotlin.samissue.View

fun main() {
    View().setOnSizeChangedListener { width, height ->
        println("w: $width, h: $height")
    }

}