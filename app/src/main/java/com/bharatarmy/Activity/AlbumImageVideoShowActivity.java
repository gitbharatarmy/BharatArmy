package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.MediaController;

import com.bharatarmy.Adapter.AlbumImageVideoAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.SnapHelperOneByOne;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityAlbumImageVideoShowBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static androidx.core.content.FileProvider.getUriForFile;

public class AlbumImageVideoShowActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityAlbumImageVideoShowBinding activityAlbumImageVideoShowBinding;
    Context mContext;
    ArrayList<String> albumImageUrl = new ArrayList<>();
    ArrayList<String> albumImageThumbUrl = new ArrayList<>();
    ArrayList<String> albumMediaType = new ArrayList<>();
    ArrayList<String> albumLike=new ArrayList<>();
    ArrayList<String> albumDuration=new ArrayList<>();
    ArrayList<String> albumId=new ArrayList<>();
    ArrayList<String> albumAddUser=new ArrayList<>();

    MediaController mediaController;
    String pathStr, mediaTypeStr, thumbStr;

    AlbumImageVideoAdapter albumImageVideoAdapter;
    LinearLayoutManager linearLayoutManager;
    String selectedItem;
    int positon;


    int showPositionImage;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAlbumImageVideoShowBinding = DataBindingUtil.setContentView(this, R.layout.activity_album_image_video_show);

        mContext = AlbumImageVideoShowActivity.this;
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
        init();
        setListiner();
    }

    public void init() {
        pathStr = getIntent().getStringExtra("AlbumImageVideoPath");
        mediaTypeStr = getIntent().getStringExtra("MediaType");
        thumbStr = getIntent().getStringExtra("AlbumImageThumb");
        final Bundle stringArrayList = getIntent().getExtras();
        albumImageUrl = stringArrayList.getStringArrayList("AlbumUrldata");
        albumImageThumbUrl = stringArrayList.getStringArrayList("AlbumThumbUrldata");
        albumMediaType = stringArrayList.getStringArrayList("AlbumMediaType");
        albumLike=stringArrayList.getStringArrayList("AlbumLike");
        albumDuration=stringArrayList.getStringArrayList("AlbumDuration");
        albumAddUser=stringArrayList.getStringArrayList("AlbumAddUser");
        albumId=stringArrayList.getStringArrayList("AlbumId");
        selectedItem =getIntent().getStringExtra("positon");

        if (mediaTypeStr != null) {
            if (mediaTypeStr.equalsIgnoreCase("1")) {
                setImage();
            } else if (mediaTypeStr.equalsIgnoreCase("2")) {
                setVideo();
            } else if (mediaTypeStr.equalsIgnoreCase("Album")) {
                setImageandVideo();
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
        activityAlbumImageVideoShowBinding.albumDetailRcvList.setVisibility(View.GONE);
        Utils.setImageInImageView(pathStr, activityAlbumImageVideoShowBinding.showAlbumImage, mContext);
    }

    public void setVideo() {
        activityAlbumImageVideoShowBinding.imageProgress.setVisibility(View.VISIBLE);
        activityAlbumImageVideoShowBinding.playAlbumvideo.setVisibility(View.VISIBLE);
        activityAlbumImageVideoShowBinding.showAlbumImage.setVisibility(View.GONE);
        activityAlbumImageVideoShowBinding.videoViewThumbnail.setVisibility(View.VISIBLE);
        activityAlbumImageVideoShowBinding.albumDetailRcvList.setVisibility(View.GONE);
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

    public void setImageandVideo() {
        activityAlbumImageVideoShowBinding.showAlbumImage.setVisibility(View.GONE);
        activityAlbumImageVideoShowBinding.playAlbumvideo.setVisibility(View.GONE);
        activityAlbumImageVideoShowBinding.imageProgress.setVisibility(View.GONE);
        activityAlbumImageVideoShowBinding.videoViewThumbnail.setVisibility(View.GONE);
        activityAlbumImageVideoShowBinding.albumDetailRcvList.setVisibility(View.VISIBLE);

        for (int i=0;i<albumImageUrl.size();i++){
            if (selectedItem.equalsIgnoreCase(albumImageUrl.get(i))){
                positon = i;
            }
        }


        albumImageVideoAdapter=new AlbumImageVideoAdapter(mContext,AlbumImageVideoShowActivity.this,albumImageUrl,
                albumImageThumbUrl,albumMediaType,albumLike,albumDuration,albumAddUser,albumId, new image_click() {
            @Override
            public void image_more_click() {
                shareImage();
            }
        });
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(activityAlbumImageVideoShowBinding.albumDetailRcvList);

        activityAlbumImageVideoShowBinding.albumDetailRcvList.setLayoutManager(linearLayoutManager);
        activityAlbumImageVideoShowBinding.albumDetailRcvList.getLayoutManager().scrollToPosition(positon);

        activityAlbumImageVideoShowBinding.albumDetailRcvList.setItemAnimator(new DefaultItemAnimator());
        activityAlbumImageVideoShowBinding.albumDetailRcvList.setAdapter(albumImageVideoAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                AlbumImageVideoShowActivity.this.finish();
                break;
        }
    }
    public void shareImage() {
        String imageUriStr = "";
        showPositionImage = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        for (int i = 0; i < albumImageUrl.size(); i++) {
            if (showPositionImage == i) {
                imageUriStr = albumImageUrl.get(showPositionImage);
                break;
            }
        }
        Log.d("imageUriStr :",imageUriStr);
        //Use for Internal Storage file
        File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "BharatArmy");
        if (!myDir.exists()) myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
//        String fname = "Image-" + n + ".jpg";
        String fname ="IMG_"+Utils.imagesaveDate()+"_BA"+Utils.imagesavetime()+".jpg";
        File file = new File(myDir,fname);
        Log.i("file", "" + file);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            Utils.StringToBitMap(imageUriStr).compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //share image from other application
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, AppConfiguration.SHARETEXT);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri = getUriForFile(mContext, getPackageName() + ".provider", file));
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Share It"));
    }
}
