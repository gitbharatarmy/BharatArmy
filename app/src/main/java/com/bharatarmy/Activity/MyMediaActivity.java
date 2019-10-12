package com.bharatarmy.Activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.bharatarmy.Adapter.MyMediaAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.R;
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
    final int NOTIFY_ID = 0; // ID of notification
    private NotificationManager notifManager;
    int progress = 1;
    List<GalleryImageModel> galleryimage;

    int selectedItemPosition = -1;
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
            if (galleryimage != null && galleryimage.size() > 0) {
                activityMyMediaBinding.showMediaRcv.setVisibility(View.VISIBLE);
                setDataList();
            } else {
                activityMyMediaBinding.showMediaRcv.setVisibility(View.GONE);
                Utils.ping(mContext, "No media available");
            }
        } else {
            activityMyMediaBinding.showMediaRcv.setVisibility(View.GONE);
            Utils.ping(mContext, "No media available");
        }

    }

    public void setDataList() {
        if (galleryimage != null) {
            Log.d("service :", "" + !Utils.isMyServiceRunning(mContext));


            myMediaAdapter = new MyMediaAdapter(mContext, galleryimage, new image_click() {
                @Override
                public void image_more_click() {
//                    selectedItemPosition = myMediaAdapter.SelectedPosition();
//                    GalleryImageModel image = galleryimage.get(myMediaAdapter.SelectedPosition());
//                    Log.d("selectedposition : ", "" + selectedItemPosition);
//                    boolean connected = Utils.checkNetwork(mContext);
//                    if (connected == true) {
//                        dbHandler.UpdateImageStatus("0", image.getId(), mContext);
//                        if (!Utils.isMyServiceRunning(mContext)) {
//                            Intent intent = new Intent(mContext, UploadService.class);
//                            startService(intent);
//                        }
////                        createNotification(AppConfiguration.notificationtitle, getApplicationContext());
//                    } else {
//                        Utils.ping(mContext, "No internet available");
//                    }
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
            }
        } else {
            activityMyMediaBinding.showMediaRcv.setVisibility(View.GONE);
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
        }
    }


    @Override
    public void onBackPressed() {
        finish();
    }

}
