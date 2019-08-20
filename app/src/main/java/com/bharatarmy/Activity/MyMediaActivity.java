package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityManager;
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
import com.bharatarmy.UploadServiceReturn;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityMyMediaBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
                    Toast.makeText(mContext, "Upload failed",
                            Toast.LENGTH_LONG).show();
                    Utils.setPref(mContext, "failedtoupload", "true");
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
        AppConfiguration.files = new ArrayList<>();
        setDataList();
        setListiner();
    }

    public void setDataList() {
        storearray = new ArrayList<GalleryImageModel>();
        Type arrayListType = new TypeToken<ArrayList<String>>() {
        }.getType();
        Gson gson = new Gson();
        List<String> yourList = gson.fromJson(Utils.getPref(mContext, "uploadcompletefile"), arrayListType);

        Log.d("uploadfilearray :", "" + yourList);

        Type arrayListType1 = new TypeToken<ArrayList<GalleryImageModel>>() {
        }.getType();
        Gson gson1 = new Gson();
        galleryimage = gson1.fromJson(Utils.getPref(mContext, "gallerylist"), arrayListType1);//List<GalleryImageModel> galleryimage
        Log.d("galleryimage :", "" + galleryimage);
        if (galleryimage != null && galleryimage.size() > 0) {
//            if (Utils.getPref(mContext, "cometonotification").equalsIgnoreCase("returnuploadservice")) {
            if (yourList != null) {
                for (int i = 0; i < galleryimage.size(); i++) {
                    for (int j = 0; j < yourList.size(); j++) {
                        if (!galleryimage.get(i).getUploadcompelet().equalsIgnoreCase("1")) {
                            if (Utils.getPref(getApplicationContext(), "image/video").equalsIgnoreCase("video")) {
                                if (galleryimage.get(i).getImageUri().equalsIgnoreCase(yourList.get(j))) {
                                    galleryimage.get(i).setUploadcompelet("1");
                                }
                            } else {
                                if (Utils.getFilePathFromUri(mContext, Uri.parse(galleryimage.get(i).getImageUri())).equalsIgnoreCase(yourList.get(j))) {
                                    galleryimage.get(i).setUploadcompelet("1");
                                }
                            }
                        }
                    }

                }
            } else {
                for (int i = 0; i < galleryimage.size(); i++) {
                    if (!galleryimage.get(i).getUploadcompelet().equalsIgnoreCase("1")) {
                        if (!Utils.isMyServiceRunning(mContext)) {
                            galleryimage.get(i).setUploadcompelet("");
                        } else {
                            galleryimage.get(i).setUploadcompelet("2");
                        }
                    }
//                                || !galleryimage.get(i).getUploadcompelet().equalsIgnoreCase("2")) {
//                            galleryimage.get(i).setUploadcompelet("");
                }
            }

//            } else {
//                if (yourList != null) {
//                    for (int i = 0; i < galleryimage.size(); i++) {
//                        for (int j = 0; j < yourList.size(); j++) {
//                            if (Utils.getPref(getApplicationContext(), "image/video").equalsIgnoreCase("video")) {
//                                if (galleryimage.get(i).getImageUri().equalsIgnoreCase(yourList.get(j))) {
//                                    galleryimage.get(i).setUploadcompelet("1");
//                                }
//                            } else {
//                                if (Utils.getFilePathFromUri(mContext, Uri.parse(galleryimage.get(i).getImageUri())).equalsIgnoreCase(yourList.get(j))) {
//                                    galleryimage.get(i).setUploadcompelet("1");
//                                }
//                            }
//                        }
//                    }
//                } else {
//                    for (int i = 0; i < galleryimage.size(); i++) {
//                        if (!galleryimage.get(i).getUploadcompelet().equalsIgnoreCase("1")) {
//                            galleryimage.get(i).setUploadcompelet("");
//                        }
//                    }
//                }
//            }

            Log.d("galleryimage :", galleryimage.toString());

            Log.d("updategallerylist :", galleryimage.toString());
        } else {
            Utils.ping(mContext, "No media available");
        }


        if (galleryimage != null) {
            AppConfiguration.files = new ArrayList<>();
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
                    for (int i = 0; i < galleryimage.size(); i++) {
                        if (i == selectedposition) {
                            galleryimage.get(i).setUploadcompelet("2");
                            myMediaAdapter.notifyDataSetChanged();
                        }
                    }
//                    Log.d("servicerunning :", "" + Utils.isMyServiceRunning(mContext));

//                    if (!Utils.isMyServiceRunning(mContext)) {
//                    if (AppConfiguration.files != null && AppConfiguration.files.size() > 0) {
//                        for (int i = 0; i < galleryimage.size(); i++) {
//                            if (!galleryimage.get(i).getUploadcompelet().equalsIgnoreCase("1")) {
//                                galleryimage.get(i).setUploadcompelet("2");
//                            }
//                        }
//                        myMediaAdapter.notifyDataSetChanged();

                    AppConfiguration.files.add(Uri.parse(getSelectedImagetoupload));

                    Intent intent = new Intent(mContext, UploadService.class);
                    startService(intent);

//                    createNotification("Uploading Image", mContext, progress);
//                    }

//                    }
                }
            });//,onTouchListener
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
            gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
            activityMyMediaBinding.showMediaRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
            activityMyMediaBinding.showMediaRcv.setAdapter(myMediaAdapter);
            myMediaAdapter.notifyDataSetChanged();

//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                myMediaAdapter.notifyDataSetChanged();
//            }
//        }, 60000);

        }

    }

    public void setListiner() {
        activityMyMediaBinding.backImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (Utils.getPref(mContext, "cometonotification").equalsIgnoreCase("service")) {
            for (int i = 0; i < galleryimage.size(); i++) {
                if (!galleryimage.get(i).getUploadcompelet().equalsIgnoreCase("1") ||
                        !galleryimage.get(i).getUploadcompelet().equalsIgnoreCase("2")) {
                    AppConfiguration.files.add(Uri.parse(galleryimage.get(i).getImageUri()));
                }
            }
            Log.d("remainimage :", AppConfiguration.files.toString());
//        }
        registerReceiver(receiver, new IntentFilter(
                UploadService.NOTIFICATION));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

//    public void createNotification(String aMessage, Context context, int progress) {
//
//        String id = context.getString(R.string.default_notification_channel_id); // default_channel_id
//        String title = context.getString(R.string.default_notification_channel_title); // Default Channel
//        Intent intent;
//        PendingIntent pendingIntent;
//        NotificationCompat.Builder builder;
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
//                R.drawable.proflie);
//
//        if (notifManager == null) {
//            notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
//            if (mChannel == null) {
//                mChannel = new NotificationChannel(id, title, importance);
//                mChannel.enableVibration(true);
//                mChannel.setVibrationPattern(new long[]{-1});
//                notifManager.createNotificationChannel(mChannel);
//            }
//            builder = new NotificationCompat.Builder(context, id);
//            intent = new Intent(context, MyMediaActivity.class);
//            Utils.setPref(getApplicationContext(), "cometonotification", "cometonotification");
//            intent.putExtra("image/video", Utils.getPref(getApplicationContext(), "image/video"));
//            intent.putExtra("cometonotification", Utils.getPref(getApplicationContext(), "cometonotification"));
//            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//            builder.setContentTitle(aMessage)                            // required
//                    .setSmallIcon(R.drawable.app_logo)   // required
//                    .setContentText(context.getString(R.string.app_name)) // required
//                    .setAutoCancel(true)
//                    .setDefaults(Notification.DEFAULT_VIBRATE)
//                    .setVibrate(new long[]{-1}) //new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400}
//                    .setOngoing(true)
//                    .setContentIntent(pendingIntent)
//                    .setTicker(aMessage)
//                    .setProgress(100, progress, false)
//                    .setPriority(Notification.PRIORITY_HIGH);
//        } else {
//            builder = new NotificationCompat.Builder(context, id);
//            intent = new Intent(context, MyMediaActivity.class);
//            Utils.setPref(getApplicationContext(), "cometonotification", "cometonotification");
//            intent.putExtra("image/video", Utils.getPref(getApplicationContext(), "image/video"));
//            intent.putExtra("cometonotification", Utils.getPref(getApplicationContext(), "cometonotification"));
//            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//            builder.setContentTitle(aMessage)                            // required
//                    .setSmallIcon(R.drawable.app_logo)   // required
//                    .setContentText(context.getString(R.string.app_name)) // required
//                    .setOngoing(true)
//                    .setAutoCancel(true)
//                    .setDefaults(Notification.DEFAULT_VIBRATE)
//                    .setVibrate(new long[]{-1}) //new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400}
//                    .setContentIntent(pendingIntent)
//                    .setTicker(aMessage)
//                    .setProgress(100, progress, false)
//
//                    .setPriority(Notification.PRIORITY_HIGH);
//        }
//        Notification notification = builder.build();
//        notification.flags |= Notification.FLAG_ONGOING_EVENT; //Notification.FLAG_AUTO_CANCEL|
//        notifManager.notify(NOTIFY_ID, notification);
//
//    }
}
