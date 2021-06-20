package com.bennyhuo.kotlin.ide

interface Api {
    val state: Int

    fun start()

    fun stop()
}

class ApiImpl : Api {
    override val state: Int
        get() = 0

    override fun start() {
    }

    override fun stop() {
    }


}