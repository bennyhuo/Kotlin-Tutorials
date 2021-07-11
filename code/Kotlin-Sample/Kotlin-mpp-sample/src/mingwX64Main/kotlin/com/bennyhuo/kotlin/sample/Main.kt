package com.bennyhuo.kotlin.sample

/**
 * Created by benny.
 */

interface DeepCopyable

data class District(var name: String)

data class Location(var lat: Double, var lng: Double)

data class Company(var name: String, var location: Location, var district: District): DeepCopyable

data class Speaker(var name: String, var age: Int, var company: Company): DeepCopyable

data class Talk(var name: String, var speaker: Speaker): DeepCopyable


fun main() {
    println("Hello")

    val district = District("x")
}