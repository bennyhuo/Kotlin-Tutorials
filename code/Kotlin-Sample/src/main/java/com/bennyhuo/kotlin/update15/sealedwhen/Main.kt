package com.bennyhuo.kotlin.update15.sealedwhen

/**
 * Created by benny.
 */

enum class State {
    Idle, Playing, Error
}

class Song

sealed interface PlayState {
    object Idle: PlayState
    class Playing(val song: Song): PlayState
    class Error(val throwable: Throwable): PlayState
}

fun main() {
    val state = State.Idle
    when (state) {
        State.Idle -> {

        }

        State.Playing -> {

        }
    }

    val playState: PlayState = PlayState.Idle
    when (playState) {
        PlayState.Idle -> {

        }
    }


}