package com.penthouse_bogmjary.common;

import android.util.Log;

public class FPSCounter {
    int frames = 0;
    long startTime = System.nanoTime();

    public void logFrame() {
        this.frames++;
        if (System.nanoTime() - this.startTime >= 1000000000) {
            Log.i("FPSCounter", "fps: " + this.frames);
            this.frames = 0;
            this.startTime = System.nanoTime();
        }
    }
}