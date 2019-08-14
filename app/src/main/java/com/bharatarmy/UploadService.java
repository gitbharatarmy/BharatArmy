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
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.bharatarmy.Activity.ImageVideoUploadActivity;
import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.NetworkClient;
import com.bharatarmy.Utility.ProgressRequestBody;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.Utility.WebServices;

import java.io.File;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UploadService extends IntentService implements ProgressRequestBody.UploadCallbacks {

    private int result = Activity.RESULT_CANCELED;
    public static final String URL = "urlpath";
    public static String FILENAME = "filename";
    public static final String FILEPATH = "filepath";
    public static final String RESULT = "result";
    public static final String NOTIFICATION = "com.bharatarmy";
    public  Uri filepath;
boolean connected;
int counter=0;
    final int NOTIFY_ID = 0; // ID of notification
    private NotificationManager notifManager;
    int progress=1;
    int div;
    public UploadService() {
        super("UploadService");

//        setIntentRedelivery(true);
    }

    // will be called asynchronously by Android
    @Override
    protected void onHandleIntent(Intent intent) {
//
//
//            MediaType mediaType = MediaType.parse("");//Based on the Postman logs,it's not specifying Content-Type, this is why I've made this empty content/mediaType
//            MultipartBody.Part[] fileParts = new MultipartBody.Part[AppConfiguration.files.size()];
//            for (int i = 0; i < AppConfiguration.files.size(); i++) {
//                File file = new File(AppConfiguration.files.get(i).getPath());
//                RequestBody fileBody = RequestBody.create(mediaType, file);
//                //Setting the file name as an empty string here causes the same issue, which is sending the request successfully without saving the files in the backend, so don't neglect the file name parameter.
//                fileParts[i] = MultipartBody.Part.createFormData(String.format(Locale.ENGLISH, "file[%d]", i), file.getName(), fileBody);
//            }
//            RequestBody FileTypeId = RequestBody.create(MediaType.parse("text/plain"), "2");
//            Retrofit retrofit = NetworkClient.getRetrofitClient(this);
//            WebServices uploadAPIs = retrofit.create(WebServices.class);
//
//
//            Call<LogginModel> call = uploadAPIs.uploadfiles(fileParts);
//
//            call.enqueue(new Callback<LogginModel>() {
//
//                @Override
//                public void onResponse(Call<LogginModel> call, Response<LogginModel> response) {
//
//                    Log.d("response : ",response.message().toString());
//                    FILENAME=response.message().toString();
//                    if (response.message().equalsIgnoreCase("OK")){
//                        result=Activity.RESULT_OK;
//                        publishResults(FILENAME, result);
//                    }
//
//                    Log.d("result",""+result);
//                }
//
//                @Override
//                public void onFailure(Call<LogginModel> call, Throwable t) {
//                    Log.d("error : ",t.toString());
////                    result=Activity.RESULT_CANCELED;
////                    publishResults(FILENAME, result);
//                    Utils.ping(getApplicationContext(),"Upload failed");
//                    UploadService.this.onHandleIntent(intent);
//
//                }
//            });

        if (AppConfiguration.files != null) {
//
            Log.d("listis",""+AppConfiguration.files.toString());
        }
        connected= Utils.checkNetwork(getApplicationContext());
        Log.d("connected :",""+connected);

        div=100/AppConfiguration.files.size();
        Log.d("progress",""+div);
        for (int i=0;i<AppConfiguration.files.size();i++){
            if (counter==i && counter<AppConfiguration.files.size()){
                filepath= AppConfiguration.files.get(i);
            }
        }

        String filePath = Utils.getFilePathFromUri(this,filepath);
        File file= new File(filePath);
        ProgressRequestBody fileBody = new ProgressRequestBody(file, this);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), fileBody);
        Retrofit retrofit = NetworkClient.getRetrofitClient(this);
        WebServices uploadAPIs = retrofit.create(WebServices.class);
        Call<LogginModel> call = uploadAPIs.uploadfiles(body);

            call.enqueue(new Callback<LogginModel>() {

                @Override
                public void onResponse(Call<LogginModel> call, Response<LogginModel> response) {

                    Log.d("response : ",response.message().toString());
                    FILENAME=response.message().toString();
                    if (response.message().equalsIgnoreCase("OK")){
                        result=Activity.RESULT_OK;
//                        publishResults(FILENAME, result);
         AppConfiguration.uploadcompletefilename.add(filePath);
         Log.d("uploadcompletefile :",AppConfiguration.uploadcompletefilename.toString());
                        progress=progress+div;
                        createNotification("Uploading",getApplicationContext(),progress);
                        Log.d("counter : ",""+counter + "arraysize :"+AppConfiguration.files.size());
                        if (counter!=AppConfiguration.files.size()-1){
                            counter++;

                            UploadService.this.onHandleIntent(intent);

                        }else
                        {
                            stopService(intent);
                            Utils.ping(getApplicationContext(),"Upload complete");
                        }

                    }

                    Log.d("result",""+result);
                }

                @Override
                public void onFailure(Call<LogginModel> call, Throwable t) {
                    Log.d("error : ",t.toString());
//                    result=Activity.RESULT_CANCELED;
//                    publishResults(FILENAME, result);
                    AppConfiguration.uploadcompletefilename.add(filePath);
                    Log.d("uploadfailfile :",AppConfiguration.uploadcompletefilename.toString());
                    Utils.ping(getApplicationContext(),"Upload failed");
                    if (counter!=AppConfiguration.files.size()){
                        UploadService.this.onHandleIntent(intent);
                    }else
                    {
                        stopService(intent);
                        notifManager.cancelAll();
                    }

                }
            });
    }

    private void publishResults(String outputPath, int result) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(FILEPATH, outputPath);
        intent.putExtra(RESULT, result);
        sendBroadcast(intent);
    }


    @Override
    public void onProgressUpdate(int percentage) {
//Log.d("progress",""+percentage);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {

    }



    public void createNotification(String aMessage, Context context,int progress) {

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
            intent = new Intent(context, ImageVideoUploadActivity.class);
            intent.putExtra("image/video",Utils.getPref(getApplicationContext(),"image/video"));
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
//                    .setLargeIcon(bitmap)
//                    .setStyle(new NotificationCompat.BigPictureStyle()
//                            .bigPicture(bitmap)
//                            .bigLargeIcon(bitmap))
                    .setProgress(100, progress, false)
            .setPriority(Notification.PRIORITY_HIGH);
        } else {
            builder = new NotificationCompat.Builder(context, id);
            intent = new Intent(context, ImageVideoUploadActivity.class);
            intent.putExtra("image/video",Utils.getPref(getApplicationContext(),"image/video"));
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
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
//                    .setLargeIcon(bitmap)
//                    .setStyle(new NotificationCompat.BigPictureStyle()
//                            .bigPicture(bitmap)
//                            .bigLargeIcon(bitmap))
                    .setProgress(100, progress, false)

                    .setPriority(Notification.PRIORITY_HIGH);
        }
        Notification notification = builder.build();
        notification.flags|=Notification.FLAG_AUTO_CANCEL|Notification.FLAG_ONGOING_EVENT;
        notifManager.notify(NOTIFY_ID, notification);

//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                notifManager.cancel(NOTIFY_ID);
//            }
//        }, 60000);
    }
}