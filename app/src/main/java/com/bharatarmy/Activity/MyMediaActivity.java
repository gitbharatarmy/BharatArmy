package com.bharatarmy.Activity;

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
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityMyMediaBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyMediaActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    ActivityMyMediaBinding activityMyMediaBinding;
    public List<GalleryImageModel> storearray = new ArrayList<>();
    MyMediaAdapter myMediaAdapter;
    final int NOTIFY_ID = 0; // ID of notification
    private NotificationManager notifManager;
    int progress = 1;
    int selectedposition;
    List<GalleryImageModel> galleryimage;
    Intent intent;
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                int resultCode = bundle.getInt(UploadService.RESULT);

                if (resultCode == -1) {

                    Toast.makeText(mContext,
                            "Upload complete.",
                            Toast.LENGTH_LONG).show();
                    setDataList();
                } else {
                    setDataList();
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMyMediaBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_media);

        mContext = MyMediaActivity.this;


        setDataList();
        setListiner();
        setHandler();
    }

    public void setDataList() {
        Type arrayListType1 = new TypeToken<ArrayList<GalleryImageModel>>() {}.getType();
        Gson gson1 = new Gson();
        galleryimage = gson1.fromJson(Utils.getPref(mContext, "gallerylist"), arrayListType1);
        Log.d("galleryimage :", "" + galleryimage);
        if (galleryimage != null && galleryimage.size() > 0) {
            Log.d("galleryimage :", galleryimage.toString());

        }
        if (galleryimage != null) {
            Log.d("service :", "" + !Utils.isMyServiceRunning(mContext));

            myMediaAdapter = new MyMediaAdapter(mContext, galleryimage, new image_click() {
                @Override
                public void image_more_click() {
                    String getSelectedImagetoupload = myMediaAdapter.getDatas().toString();
                    Log.d("getImagetoupload", getSelectedImagetoupload);


                    getSelectedImagetoupload = getSelectedImagetoupload.substring(1, getSelectedImagetoupload.length() - 1);
                    Log.d("getreturnuploadimage", getSelectedImagetoupload);
                    String[] split = getSelectedImagetoupload.split("\\|");
                    getSelectedImagetoupload = split[0];
                    Log.d("finaluploadimage", getSelectedImagetoupload);


                    selectedposition = Integer.parseInt(split[1]);
                    boolean connected = Utils.checkNetwork(mContext);
                    if (connected == true) {
                    for (int i = 0; i < galleryimage.size(); i++) {
                        if (i == selectedposition) {
                            galleryimage.get(i).setUploadcompelet("0");
                            myMediaAdapter.notifyDataSetChanged();
                        }
                    }

                    Gson gsonupdate = new Gson();
                    String valuesString = gsonupdate.toJson(galleryimage);
                    Utils.setPref(mContext, "gallerylist", valuesString);
                    Log.d("valuesString", valuesString);
                    Log.d("mymediafiles", AppConfiguration.files.toString());

                    AppConfiguration.files.add(Uri.parse(getSelectedImagetoupload));

                        intent = new Intent(mContext, UploadService.class);
                        startService(intent);
                        createNotification(AppConfiguration.notificationtitle, mContext, Utils.getIntPref(mContext,"uploadprocess"));
                    }else{
                        Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), MyMediaActivity.this);
                    }


                }
            });//,onTouchListener
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
            gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
            activityMyMediaBinding.showMediaRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
            activityMyMediaBinding.showMediaRcv.setAdapter(myMediaAdapter);
            myMediaAdapter.notifyDataSetChanged();


        }

    }

    public void setListiner() {
        activityMyMediaBinding.backImg.setOnClickListener(this);


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
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(
                UploadService.NOTIFICATION));

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

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    public void setHandler() {
        final Handler handler = new Handler();
        final int delay = 2000; //1000 milliseconds = 1 sec
        handler.postDelayed(new Runnable() {
            public void run() {
                Type arrayListType1 = new TypeToken<ArrayList<GalleryImageModel>>() {}.getType();
                Gson gson1 = new Gson();
                galleryimage = gson1.fromJson(Utils.getPref(mContext, "gallerylist"), arrayListType1);

                Gson gsonupdate = new Gson();
                String valuesString = gsonupdate.toJson(galleryimage);
                Utils.setPref(mContext, "gallerylist", valuesString);
                Log.d("valuesString", valuesString);
                Type arrayListType2 = new TypeToken<ArrayList<GalleryImageModel>>() {}.getType();
                Gson gson2 = new Gson();
                galleryimage = gson2.fromJson(Utils.getPref(mContext, "gallerylist"), arrayListType2);


                myMediaAdapter.timer(galleryimage);


                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    public void createNotification(String aMessage, Context context, int progress) {

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
                    .setProgress(0,0,true)
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
                    .setProgress(0,0,true)
                    .setPriority(Notification.PRIORITY_HIGH);
        }
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_ONGOING_EVENT; //Notification.FLAG_AUTO_CANCEL|
        notifManager.notify(NOTIFY_ID, notification);
    }


}
