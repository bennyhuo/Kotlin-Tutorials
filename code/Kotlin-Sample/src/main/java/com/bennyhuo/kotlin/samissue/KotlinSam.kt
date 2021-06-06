package com.bennyhuo.kotlin.samissue

fun main() {
    View().setOnSizeChangedListener { width, height ->
        println("w: $width, h: $height")
    }
}