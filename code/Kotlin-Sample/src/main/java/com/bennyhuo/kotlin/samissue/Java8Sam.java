package com.bennyhuo.kotlin.samissue;

public class Java8Sam {
    public static void main(String[] args) {
        View view = new View();
        view.setOnSizeChangedListener((width, height) -> {
            System.out.println("w: " + width + ", h: " + height);
        });

        view.setOnSizeChangedListener(new View.OnSizeChangedListener() {
            @Override
            public void onSizeChanged(int width, int height) {
                System.out.println("w: " + width + ", h: " + height);
            }
        });
    }
}
