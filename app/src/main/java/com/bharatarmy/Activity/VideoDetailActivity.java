package com.bharatarmy.Activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bharatarmy.R;
import com.bharatarmy.VideoModule.FullscreenVideoView;

public class VideoDetailActivity extends BaseActivity implements View.OnClickListener {

    Context mContext;
    String videoUrlStr, videoNameStr, whereToComeStr,theWord;
    FullscreenVideoView fullscreenVideoView;
TextView toolbar_title_txt;
ImageView backImg;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);

        mContext = VideoDetailActivity.this;
        fullscreenVideoView = (FullscreenVideoView) findViewById(R.id.fullscreenVideoView);
//        toolbar_title_txt=(TextView)findViewById(R.id.toolbar_title_txt);
//        backImg=(ImageView)findViewById(R.id.back_img);

        setDataValue();
        setTitleText(videoNameStr);
        setBackButton(VideoDetailActivity.this);

    }


    public void setDataValue() {
        videoUrlStr = getIntent().getStringExtra("videoData");
        videoNameStr = getIntent().getStringExtra("videoName");
        whereToComeStr = getIntent().getStringExtra("WhereToVideoCome");

//        toolbar_title_txt.setText(videoNameStr);
        fullscreenVideoView.videoUrl(videoUrlStr)
                .enableAutoStart()
                .addSeekBackwardButton()
                .addSeekForwardButton();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                VideoDetailActivity.this.finish();
                break;
        }
    }
}
