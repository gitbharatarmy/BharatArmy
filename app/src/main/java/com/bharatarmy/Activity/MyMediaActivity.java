package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bharatarmy.Adapter.MyMediaAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.R;
import com.bharatarmy.UploadService;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.DbHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.Utility.firebaseutils;
import com.bharatarmy.databinding.ActivityMyMediaBinding;

import java.util.ArrayList;
import java.util.Collections;
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
                    selectedItemPosition = myMediaAdapter.SelectedPosition();
                    GalleryImageModel image = galleryimage.get(myMediaAdapter.SelectedPosition());
                    Log.d("selectedposition : ", "" + selectedItemPosition);
                    boolean connected = Utils.checkNetwork(mContext);
                    if (connected == true) {
                        dbHandler.UpdateImageStatus("0", image.getId(),mContext);
                        createNotification(AppConfiguration.notificationtitle, getApplicationContext());
                    } else {
                        Utils.ping(mContext, "No internet available");
                    }


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

                if (updatearray.size()== galleryimage.size()) {
                    for (int j = 0; j < updatearray.size(); j++) {
                        for (int i = 0; i < galleryimage.size(); i++) {
                            if (!updatearray.get(j).getUploadcompelet().equalsIgnoreCase(galleryimage.get(i).getUploadcompelet())) {
                                myMediaAdapter.notifyItemChanged(j, updatearray.get(j).getUploadcompelet());
                            } else {
                                myMediaAdapter.notifyItemChanged(i, updatearray.get(i).getUploadcompelet());
                            }
                        }
                    }
                }else{
                   init();
                }
            } else {
                activityMyMediaBinding.showMediaRcv.setVisibility(View.GONE);
//                Utils.ping(mContext, "No media available");
            }
        } else {
            activityMyMediaBinding.showMediaRcv.setVisibility(View.GONE);
//            Utils.ping(mContext, "No media available");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(mContext, DashboardActivity.class);
                        startActivity(intent);
                    }
                }, 50);
                break;
            case R.id.refresh_img:
                break;
        }
    }


    @Override
    public void onBackPressed() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(mContext, DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }, 50);

    }


    public void createNotification(String aMessage, Context context) {

        String id = context.getString(R.string.default_notification_channel_id); // default_channel_id
        String title = context.getString(R.string.default_notification_channel_title); // Default Channel
        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.proflie);

        if (notifManager == null) {
            notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, title, importance);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{-1});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, MyMediaActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Utils.setPref(getApplicationContext(), "cometonotification", "cometonotification");
            intent.putExtra("image/video", Utils.getPref(getApplicationContext(), "image/video"));
            intent.putExtra("cometonotification", Utils.getPref(getApplicationContext(), "cometonotification"));
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(R.drawable.app_logo)   // required
                    .setContentText(context.getString(R.string.app_name)) // required
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setVibrate(new long[]{-1}) //new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400}
                    .setOngoing(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
//                    .setProgress(100, progress, false)
                    .setProgress(0, 0, true)
                    .setPriority(Notification.PRIORITY_HIGH);
        } else {
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, MyMediaActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Utils.setPref(getApplicationContext(), "cometonotification", "cometonotification");
            intent.putExtra("image/video", Utils.getPref(getApplicationContext(), "image/video"));
            intent.putExtra("cometonotification", Utils.getPref(getApplicationContext(), "cometonotification"));
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(R.drawable.app_logo)   // required
                    .setContentText(context.getString(R.string.app_name)) // required
                    .setOngoing(true)
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setVibrate(new long[]{-1}) //new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400}
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
//                    .setProgress(100, progress, false)
                    .setProgress(0, 0, true)
                    .setPriority(Notification.PRIORITY_HIGH);
        }
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_ONGOING_EVENT; //Notification.FLAG_AUTO_CANCEL|
        notifManager.notify(NOTIFY_ID, notification);
    }


}
