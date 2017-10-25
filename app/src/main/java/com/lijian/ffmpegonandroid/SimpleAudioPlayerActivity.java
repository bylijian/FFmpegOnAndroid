package com.lijian.ffmpegonandroid;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lijian.videoplayer.AudioPlayer;

/**
 * Created by lijian on 2017/10/25.
 */

public class SimpleAudioPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_audio_player);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AudioPlayer audioPlayer = new AudioPlayer();
        audioPlayer.playMp3Audio(Environment.getExternalStorageDirectory() + "/test.mp3");
    }
}
