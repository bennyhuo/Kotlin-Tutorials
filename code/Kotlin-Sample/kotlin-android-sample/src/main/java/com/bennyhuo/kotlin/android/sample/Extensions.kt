package com.bennyhuo.kotlin.android.sample

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.widget.Toast

context(View)
val Float.dp: Float
    get() = this * resources.displayMetrics.density

context(View)
val Int.dp: Float
    get() = this * resources.displayMetrics.density

class MyView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        1.dp
    }
}

context(Context)
fun String.toastShort() {
    Toast.makeText(this@Context, this, Toast.LENGTH_SHORT).show()
}

class MyActivity: Activity() {
    override fun onResume() {
        super.onResume()

        "onResume called".toastShort()
    }
}