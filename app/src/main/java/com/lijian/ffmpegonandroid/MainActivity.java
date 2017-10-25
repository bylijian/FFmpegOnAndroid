package com.lijian.ffmpegonandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mSimpleVideoPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSimpleVideoPlay = (Button) findViewById(R.id.simple_video_player);
        mSimpleVideoPlay.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.simple_video_player:
                gotoSimpleVideoPlayer();
                break;
        }
    }

    private void gotoSimpleVideoPlayer() {
        Intent intent = new Intent(this, SimpleVideoPlayerActivity.class);
        startActivity(intent);
    }
}
