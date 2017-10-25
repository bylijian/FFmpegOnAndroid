package com.lijian.videoplayer;

import android.util.Log;

/**
 * Created by lijian on 2017/10/25.
 */

public class AudioPlayer {
    static {
        System.loadLibrary("audioplayer");
    }

    public boolean playMp3Audio(final String fileName) {
        Log.d("AudioPlayer",fileName);

        new Thread(new Runnable() {
            @Override
            public void run() {
                nativePlayAudio(fileName);
            }
        }).start();
        return true;
    }

    private native void nativePlayAudio(String fileName);
}
