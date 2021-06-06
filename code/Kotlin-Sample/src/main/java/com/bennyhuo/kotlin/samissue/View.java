package com.bennyhuo.kotlin.samissue;

public class View {
    // Single Abstract method
    public interface OnSizeChangedListener {
        void onSizeChanged(int width, int height);
    }

    public void setOnSizeChangedListener(OnSizeChangedListener onSizeChangedListener) {

    }
}
