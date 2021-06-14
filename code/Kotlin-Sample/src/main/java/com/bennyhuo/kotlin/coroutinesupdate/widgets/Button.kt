package com.bennyhuo.kotlin.coroutinesupdate.widgets

fun interface OnClickListener {
    fun onClick(view: View)
}

open class View {

    private var onClickListener: OnClickListener? = null

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    fun click() {
        onClickListener?.onClick(this)
    }
}

class Button: View() {

}