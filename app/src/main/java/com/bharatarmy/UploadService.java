package com.bharatarmy;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.bharatarmy.Activity.ImageEditProfilePickerActivity;
import com.bharatarmy.Activity.MyMediaActivity;
import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.DbHandler;
import com.bharatarmy.Utility.NetworkClient;
import com.bharatarmy.Utility.ProgressRequestBody;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.Utility.WebServices;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.VideoTrimmer.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class UploadService extends IntentService implements ProgressRequestBody.UploadCallbacks {
    private int result = Activity.RESULT_CANCELED;
    String filePath;
    final int NOTIFY_ID = 0; // ID of notification
    private NotificationManager notifManager;
    DbHandler db;
    String fileTypeId;
    RequestBody appuserId, filetypeId, memberName, videoLength, videoTitle, videoDesc, videoHeight, videoWidth,videoprivacysetting;
    Call<LogginModel> call;

    public UploadService() {
        super("UploadService");
    }

    // will be called asynchronously by Android
    @Override
    protected void onHandleIntent(Intent intent) {

        db = new DbHandler(getApplicationContext());
        if (Utils.UpladingFiles == null) {
            Utils.UpladingFiles = new ArrayList<>();
        }
        Utils.UpladingFiles = db.getAllImageData();

        if (Utils.UpladingFiles != null && Utils.UpladingFiles.size() > 0) {

            createNotification(AppConfiguration.notificationtitle, getApplicationContext());
            UploadFiles(Utils.UpladingFiles.get(0)); //, intent
        } else {
            stopSelf();
        }
    }

    public void UploadFiles(GalleryImageModel objfile) { //, Intent intent
        filePath = objfile.getImageUri();

        Log.d("filepath :", filePath);

        db.UpdateImageStatus("1", objfile.getId(), getApplicationContext());

        filetypeId = RequestBody.create(MediaType.parse("text/plain"), objfile.getFileType());
        memberName = RequestBody.create(MediaType.parse("text/plain"), Utils.retriveLoginData(getApplicationContext()).getName());
        appuserId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(Utils.getAppUserId(getApplicationContext())));
        videoLength = RequestBody.create(MediaType.parse("text/plain"), objfile.getVideolength());
        videoTitle = RequestBody.create(MediaType.parse("text/plain"), objfile.getVideoTitle());
        videoDesc = RequestBody.create(MediaType.parse("text/plain"), objfile.getVideoDesc());
        videoHeight = RequestBody.create(MediaType.parse("text/plain"), objfile.getVideoHeight());
        videoWidth = RequestBody.create(MediaType.parse("text/plain"), objfile.getVideoWidth());
        videoprivacysetting=RequestBody.create(MediaType.parse("text/plain"),objfile.getPrivacySetting());
        Retrofit retrofit = NetworkClient.getRetrofitClient(getApplicationContext());
        WebServices uploadAPIs = retrofit.create(WebServices.class);
        if (objfile.getFileType().equalsIgnoreCase("2")) {
            Utils.videoFile = new ArrayList<>();

//            create thumbnail and store in folder
//            String thumbnailpath = Utils.saveToInternalStorage(Utils.createThumbnailAtTime(objfile.getImageUri())).toString();

//            Log.d("videothumbnailpath", thumbnailpath);
//            Utils.videoFile.add(objfile.getImageUri());
//            Utils.videoFile.add(objfile.getThumbnail());

            //            create thumbnail and store in folder
            String thumbnailpath = Utils.saveToInternalStorage(Utils.createThumbnailAtTime(objfile.getImageUri())).toString();

            Log.d("videothumbnailpath", thumbnailpath);
            Utils.videoFile.add(objfile.getImageUri());
            Utils.videoFile.add(thumbnailpath);

            MediaType mediaType = MediaType.parse("");//Based on the Postman logs,it's not specifying Content-Type, this is why I've made this empty content/mediaType
            MultipartBody.Part[] fileParts = new MultipartBody.Part[Utils.videoFile.size()];
            for (int i = 0; i < Utils.videoFile.size(); i++) {
                File file = new File(Utils.videoFile.get(i));
                RequestBody fileBody = RequestBody.create(mediaType, file);
                //Setting the file name as an empty string here causes the same issue, which is sending the request successfully without saving the files in the backend, so don't neglect the file name parameter.
                fileParts[i] = MultipartBody.Part.createFormData(String.format(Locale.ENGLISH, "file[%d]", i), file.getName(), fileBody);
            }
            call = uploadAPIs.uploadvideo(fileParts, filetypeId, appuserId, memberName, videoLength, videoTitle, videoDesc, videoHeight, videoWidth,videoprivacysetting);
        } else {
            Utils.videoFile = new ArrayList<>();
//            create thumbnail and store in folder
//            String thumbnailpath = Utils.saveToInternalStorage(Utils.createImageThumbNail(Utils.getBitmap(objfile.getImageUri()))).toString();
//            Log.d("Imagethumbnailpath", thumbnailpath);

            //            create thumbnail and store in folder
            String thumbnailpath = Utils.saveToInternalStorage(Utils.createImageThumbNail(Utils.getBitmap(objfile.getImageUri()))).toString();
            Log.d("Imagethumbnailpath", thumbnailpath);

            Utils.videoFile.add(objfile.getImageUri());
            Utils.videoFile.add(thumbnailpath);


            MediaType mediaType = MediaType.parse("");//Based on the Postman logs,it's not specifying Content-Type, this is why I've made this empty content/mediaType
            MultipartBody.Part[] fileParts = new MultipartBody.Part[Utils.videoFile.size()];
            for (int i = 0; i < Utils.videoFile.size(); i++) {
                File file = new File(Utils.videoFile.get(i));
                RequestBody fileBody = RequestBody.create(mediaType, file);
                //Setting the file name as an empty string here causes the same issue, which is sending the request successfully without saving the files in the backend, so don't neglect the file name parameter.
                fileParts[i] = MultipartBody.Part.createFormData(String.format(Locale.ENGLISH, "file[%d]", i), file.getName(), fileBody);
            }


            call = uploadAPIs.uploadvideo(fileParts, filetypeId, appuserId, memberName, videoLength, videoTitle, videoDesc, videoHeight, videoWidth,videoprivacysetting);
        }

        Log.d("File", "" + call);
        call.enqueue(new Callback<LogginModel>() {

            @Override
            public void onResponse(Call<LogginModel> call, Response<LogginModel> response) {

                Log.d("response : ", response.message().toString());
                if (response.message().equalsIgnoreCase("OK")) {
                    Log.d("result", "" + result);
                    result = Activity.RESULT_OK;
                    db.DeleteImage(objfile.getId());
                    Utils.UpladingFiles.remove(objfile);

                    if (Utils.UpladingFiles.size() > 0) {
                        UploadFiles(Utils.UpladingFiles.get(0));
                        createNotification(AppConfiguration.notificationtitle, getApplicationContext());
                    } else {
                        Utils.UpladingFiles = db.getAllImageData();
                        if (Utils.UpladingFiles != null && Utils.UpladingFiles.size() > 0) {
                            UploadFiles(Utils.UpladingFiles.get(0)); //, intent
                            createNotification(AppConfiguration.notificationtitle, getApplicationContext());
                        } else {
                            if (notifManager == null) {
                                notifManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                            }
                            notifManager.cancel(NOTIFY_ID);
                            stopSelf();
                        }

                    }
                }
                /*else{
                    if (notifManager == null) {
                        notifManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    }
                    notifManager.cancel(NOTIFY_ID);
                    stopSelf();
                }*/

            }

            @Override
            public void onFailure(Call<LogginModel> call, Throwable t) {
                Log.d("error :", t.toString());
                Log.d("error :", t.getClass().getSimpleName());
//                if (!t.getClass().getSimpleName().equalsIgnoreCase("FileNotFoundException")) {
                db.UpdateImageStatus("2", objfile.getId(), getApplicationContext());
                Utils.UpladingFiles.remove(objfile);
                if (Utils.UpladingFiles.size() > 0) {
                    UploadFiles(Utils.UpladingFiles.get(0));//, intent
                } else {
                    if (notifManager == null) {
                        notifManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    }
                    notifManager.cancel(NOTIFY_ID);
                    stopSelf();
                }
//                } else {
//                    db.DeleteImage(objfile.getId());
//                }

            }
        });
    }

    @Override
    public void onProgressUpdate(int percentage) {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {

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
                mChannel.enableVibration(false);
                mChannel.setVibrationPattern(new long[]{-1});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, MyMediaActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("image/video", Utils.getPref(context, "image/video"));
            intent.putExtra("cometonotification", Utils.getPref(context, "cometonotification"));
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(R.drawable.app_logo_new)   // required
                    .setContentText(context.getString(R.string.app_name)) // required
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setVibrate(new long[]{-1}) //new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400}
                    .setOngoing(true)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setProgress(0, 0, true)
                    .setPriority(Notification.PRIORITY_HIGH);

        } else {
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, MyMediaActivity.class);
            intent.putExtra("image/video", Utils.getPref(context, "image/video"));
            intent.putExtra("cometonotification", Utils.getPref(context, "cometonotification"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentTitle(aMessage)                            // required
                    .setSmallIcon(R.drawable.app_logo_new)   // required
                    .setContentText(context.getString(R.string.app_name)) // required
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setVibrate(new long[]{-1}) //new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400}
                    .setOngoing(true)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setProgress(0, 0, true)
//                    .setProgress(100, progress, false)
                    .setPriority(Notification.PRIORITY_HIGH);
        }
        Notification notification = builder.build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL | Notification.FLAG_ONGOING_EVENT;//Notification.FLAG_AUTO_CANCEL |
        notifManager.notify(NOTIFY_ID, notification);

    }
}