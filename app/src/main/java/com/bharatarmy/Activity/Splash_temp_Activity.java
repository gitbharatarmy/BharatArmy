package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.bharatarmy.R;
import com.bharatarmy.databinding.ActivitySplashTempBinding;

public class Splash_temp_Activity extends AppCompatActivity {

    ActivitySplashTempBinding splashTempBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashTempBinding= DataBindingUtil.setContentView(this,R.layout.activity_splash_temp_);

        splashTempBinding.videoViewRelative.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.comp);
        splashTempBinding.videoViewRelative.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                splashTempBinding.videoViewRelative.start();
            }
        });
    }
}
