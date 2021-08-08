package com.bennyhuo.kotlin.android.sample

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory

class MainActivity : AppCompatActivity() {
    private val logger = LoggerFactory.getLogger(MainActivity::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            getTextViewPositionFlow()
                .collect {
                    logger.debug(it.toString())
                }
        }

        textView.setOnClickListener {
            ObjectAnimator.ofFloat(textView, "x", 0f, 1000f)
                .setDuration(1000)
                .start()

            ObjectAnimator.ofFloat(textView, "y", 0f, 500f)
                .setDuration(1000)
                .start()
        }
    }

    private fun getTextViewPositionFlow(): Flow<Pair<Int, Int>> {
        return callbackFlow<Pair<Int, Int>> {
            val listener =
                View.OnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
                    logger.debug("left: $left, top: $top")
                    trySendBlocking(top to left)
                }
            textView.addOnLayoutChangeListener(listener)

            awaitClose {
                textView.removeOnLayoutChangeListener(listener)
            }
        }
    }
}