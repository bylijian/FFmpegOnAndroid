package com.lijian.ffmpegonandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.lijian.videoplayer.VideoPlayer;

/**
 * Created by lijian on 2017/10/24.
 */

public class SimpleVideoPlayerActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private VideoPlayer mPlayer;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_video_player);
        mPlayer = new VideoPlayer();
        mSurfaceView = (SurfaceView) findViewById(R.id.surface);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mPlayer.play(holder.getSurface());
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mPlayer.stop();
    }
}
