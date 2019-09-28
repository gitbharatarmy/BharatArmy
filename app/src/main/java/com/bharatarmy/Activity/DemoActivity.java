package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import com.bharatarmy.R;
import com.bharatarmy.VideoModule.FullscreenVideoView;
import com.google.android.material.appbar.CollapsingToolbarLayout;


public class DemoActivity extends AppCompatActivity {

Context mContext;
String videoUrlStr;
    FullscreenVideoView fullscreenVericaleVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_demo);

        mContext=DemoActivity.this;

        videoUrlStr="http://devenv.bharatarmy.com//Docs/Media/f49be7b4-dac9-4888-90c5-df694b5ca936-VID-20190923-WA0053.mp4";


        fullscreenVericaleVideoView = (FullscreenVideoView) findViewById(R.id.fullscreenVerticalVideoView);
        fullscreenVericaleVideoView.videoUrl(videoUrlStr)
                .enableAutoStart()
                .addSeekBackwardButton()
                .addSeekForwardButton();
    }
}
