package com.bharatarmy.Activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.bharatarmy.Adapter.MyMediaAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.R;
import com.bharatarmy.UploadService;
import com.bharatarmy.Utility.DbHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityMyMediaBinding;

import java.util.ArrayList;
import java.util.List;

public class MyMediaActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    ActivityMyMediaBinding activityMyMediaBinding;
    public List<GalleryImageModel> updatearray;
    MyMediaAdapter myMediaAdapter;

    List<GalleryImageModel> galleryimage;
    List<GalleryImageModel> retryUploadimageList;

    // Database
    DbHandler dbHandler;
    Handler timerHandler;
    Runnable timerRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMyMediaBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_media);

        mContext = MyMediaActivity.this;

        init();
        timerHandler = new Handler();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                // Here you can update your adapter data
                refreshView();
                timerHandler.postDelayed(this, 500); //run every second
            }
        };

        timerHandler.postDelayed(timerRunnable, 500); //Start timer after 1 sec

        setListiner();

    }

    public void init() {
        dbHandler = new DbHandler(mContext);
        galleryimage = new ArrayList<>();

        if (dbHandler.getMediaImageData() != null && dbHandler.getMediaImageData().size() > 0) {
            galleryimage = dbHandler.getMediaImageData();
            Log.d("galleryimage :", "" + galleryimage.size());
            if (galleryimage != null && galleryimage.size() > 0) {
                activityMyMediaBinding.showMediaRcv.setVisibility(View.VISIBLE);
                activityMyMediaBinding.noRecordrel.setVisibility(View.GONE);
                setDataList();
            } else {
                activityMyMediaBinding.showMediaRcv.setVisibility(View.GONE);
                activityMyMediaBinding.retryLinear.setVisibility(View.GONE);
                activityMyMediaBinding.noRecordrel.setVisibility(View.VISIBLE);
//                Utils.ping(mContext, "No media available");
            }
        } else {
            activityMyMediaBinding.showMediaRcv.setVisibility(View.GONE);
            activityMyMediaBinding.retryLinear.setVisibility(View.GONE);
//            Utils.ping(mContext, "No media available");
            activityMyMediaBinding.noRecordrel.setVisibility(View.VISIBLE);
        }

    }

    public void setDataList() {
        if (galleryimage != null) {
            Log.d("service :", "" + !Utils.isMyServiceRunning(mContext));


            myMediaAdapter = new MyMediaAdapter(mContext, galleryimage, new image_click() {
                @Override
                public void image_more_click() {
                }
            });//,onTouchListener
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
            gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
            activityMyMediaBinding.showMediaRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
            activityMyMediaBinding.showMediaRcv.setAdapter(myMediaAdapter);


        }

    }

    public void setListiner() {
        activityMyMediaBinding.backImg.setOnClickListener(this);
        activityMyMediaBinding.refreshImg.setOnClickListener(this);
        activityMyMediaBinding.retryBtn.setOnClickListener(this);
    }

    public void refreshView() {
        if (dbHandler.getMediaImageData() != null && dbHandler.getMediaImageData().size() > 0) {
            updatearray = dbHandler.getMediaImageData();
            if (galleryimage != null && galleryimage.size() > 0) {

                if (updatearray.size() == galleryimage.size()) {
                    for (int j = 0; j < updatearray.size(); j++) {
                        for (int i = 0; i < galleryimage.size(); i++) {
                            if (!updatearray.get(j).getUploadcompelet().equalsIgnoreCase(galleryimage.get(i).getUploadcompelet())) {
                                myMediaAdapter.notifyItemChanged(j, updatearray.get(j).getUploadcompelet());
                            } else {
                                myMediaAdapter.notifyItemChanged(i, updatearray.get(i).getUploadcompelet());
                            }
                        }
                    }
                } else {
                    init();
                }
            } else {
                activityMyMediaBinding.showMediaRcv.setVisibility(View.GONE);
                activityMyMediaBinding.noRecordrel.setVisibility(View.VISIBLE);
            }
        } else {
            activityMyMediaBinding.showMediaRcv.setVisibility(View.GONE);
            activityMyMediaBinding.noRecordrel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.refresh_img:
                break;
            case R.id.retry_btn:
                retryUploadImage();
                break;
        }
    }

    public void retryUploadImage() {
        if (dbHandler.getMediaImageData() != null && dbHandler.getMediaImageData().size() > 0) {
            retryUploadimageList = dbHandler.getMediaImageData();
            Log.d("retryUploadimageList :", "" + galleryimage.size());
            if (retryUploadimageList != null && retryUploadimageList.size() > 0) {
                for (int i = 0; i < retryUploadimageList.size(); i++) {
                    if (retryUploadimageList.get(i).getUploadcompelet().equalsIgnoreCase("2")) {
                        dbHandler.DeleteImage(galleryimage.get(i).getId());
                        dbHandler.insertImageDetails(retryUploadimageList.get(i).getImageUri(), retryUploadimageList.get(i).getImageSize(),
                                "0", retryUploadimageList.get(i).getVideolength(),
                                retryUploadimageList.get(i).getFileType(), retryUploadimageList.get(i).getVideoTitle(),
                                retryUploadimageList.get(i).getVideoDesc(), retryUploadimageList.get(i).getVideoHeight(),
                                retryUploadimageList.get(i).getVideoWidth(), retryUploadimageList.get(i).getPrivacySetting(), mContext);
                    }
                }

                Intent intent = new Intent(mContext, UploadService.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startService(intent);
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
