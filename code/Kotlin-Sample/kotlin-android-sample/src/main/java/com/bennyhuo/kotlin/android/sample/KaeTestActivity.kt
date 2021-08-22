package com.bennyhuo.kotlin.android.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.slf4j.LoggerFactory

class KaeTestActivity : AppCompatActivity() {
    private val logger = LoggerFactory.getLogger(KaeTestActivity::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView.text = "hello"
        textView.setOnClickListener {
            logger.debug("clicked.")
        }

        textView?.setOnClickListener {

        }
    }
}