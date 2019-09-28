package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.MediaController;

import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityAlbumImageVideoShowBinding;

import java.io.IOException;

public class AlbumImageVideoShowActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityAlbumImageVideoShowBinding activityAlbumImageVideoShowBinding;
    Context mContext;


    MediaController mediaController;
    String pathStr, mediaTypeStr, thumbStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAlbumImageVideoShowBinding = DataBindingUtil.setContentView(this, R.layout.activity_album_image_video_show);

        mContext = AlbumImageVideoShowActivity.this;

        init();
        setListiner();
    }

    public void init() {
        pathStr = getIntent().getStringExtra("AlbumImageVideoPath");
        mediaTypeStr = getIntent().getStringExtra("MediaType");
        thumbStr = getIntent().getStringExtra("AlbumImageThumb");

        if (mediaTypeStr != null) {
            if (mediaTypeStr.equalsIgnoreCase("1")) {
                setImage();
            } else {
                setVideo();
            }
        }
    }


    public void setListiner() {
        activityAlbumImageVideoShowBinding.backImg.setOnClickListener(this);
    }

    public void setImage() {
        activityAlbumImageVideoShowBinding.showAlbumImage.setVisibility(View.VISIBLE);
        activityAlbumImageVideoShowBinding.playAlbumvideo.setVisibility(View.GONE);
        activityAlbumImageVideoShowBinding.imageProgress.setVisibility(View.GONE);
        Utils.setImageInImageView(pathStr, activityAlbumImageVideoShowBinding.showAlbumImage, mContext);
    }

    public void setVideo() {
        activityAlbumImageVideoShowBinding.imageProgress.setVisibility(View.VISIBLE);
        activityAlbumImageVideoShowBinding.playAlbumvideo.setVisibility(View.VISIBLE);
        activityAlbumImageVideoShowBinding.showAlbumImage.setVisibility(View.GONE);
        activityAlbumImageVideoShowBinding.videoViewThumbnail.setVisibility(View.VISIBLE);
        Utils.setImageInImageView(thumbStr, activityAlbumImageVideoShowBinding.videoViewThumbnail, mContext);

        mediaController = new MediaController(mContext);
        mediaController.setAnchorView(activityAlbumImageVideoShowBinding.playAlbumvideo);
        activityAlbumImageVideoShowBinding.playAlbumvideo.setMediaController(mediaController);
        activityAlbumImageVideoShowBinding.playAlbumvideo.requestFocus();

        activityAlbumImageVideoShowBinding.playAlbumvideo.setVideoPath(pathStr);

        activityAlbumImageVideoShowBinding.playAlbumvideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                activityAlbumImageVideoShowBinding.videoViewThumbnail.setVisibility(View.GONE);
                activityAlbumImageVideoShowBinding.imageProgress.setVisibility(View.GONE);
                activityAlbumImageVideoShowBinding.playAlbumvideo.start();

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                AlbumImageVideoShowActivity.this.finish();
                break;
        }
    }

}
