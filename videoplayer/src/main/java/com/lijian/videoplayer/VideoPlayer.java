package com.lijian.videoplayer;

import android.os.Environment;
import android.util.Log;
import android.view.Surface;

import java.io.File;

/**
 * Created by lijian on 2017/6/14.
 */

public class VideoPlayer {
    static {
        System.loadLibrary("videoplayer");
    }

    public boolean play(final Surface surface) {
        //耗时操作，需要在非UI线程执行
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File("/storage/emulated/0/test.mp4");
                Log.d("VideoPlayer", "file.exists()" + file.exists());
                nativePlay(surface, Environment.getExternalStorageDirectory() + "/test.mp4");
            }
        }).start();
        return true;
    }

    public boolean stop() {
        return nativeStop();
    }

    native private int nativePlay(Surface surface, String fileName);

    native private boolean nativeStop();

}
