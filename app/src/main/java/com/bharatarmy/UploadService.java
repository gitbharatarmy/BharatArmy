package com.bharatarmy;

import android.app.Activity;
import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.DbHandler;
import com.bharatarmy.Utility.NetworkClient;
import com.bharatarmy.Utility.ProgressRequestBody;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.Utility.WebServices;
import com.bharatarmy.Utility.firebaseutils;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class UploadService extends IntentService implements ProgressRequestBody.UploadCallbacks {

    private int result = Activity.RESULT_CANCELED;
    public Uri filepath;
    String filePath;
    boolean connected;
    int counter = 0;
    final int NOTIFY_ID = 0; // ID of notification
    private NotificationManager notifManager;
    DbHandler db ;

    public UploadService() {
        super("UploadService");
    }


    // will be called asynchronously by Android
    @Override
    protected void onHandleIntent(Intent intent) {
        db= new DbHandler(getApplicationContext());
        if (firebaseutils.UpladingFiles == null) {
            firebaseutils.UpladingFiles = new ArrayList<>();
        }


        firebaseutils.UpladingFiles=db.getAllImageData();

        if (firebaseutils.UpladingFiles != null && firebaseutils.UpladingFiles.size() > 0) {
            UploadFiles(firebaseutils.UpladingFiles.get(0));
        }
    }

    public void UploadFiles(GalleryImageModel objfile) {
        filepath = Uri.parse(objfile.getImageUri());

        if (Utils.getPref(getApplicationContext(), "image/video").equalsIgnoreCase("video")) {
            filePath = String.valueOf(filepath);
        } else {
            filePath = Utils.getFilePathFromUri(this, filepath);
        }

        Log.d("filepath :",filePath);
        db.UpdateImageStatus(1, objfile.getId());

        File file = new File(filePath);
        ProgressRequestBody fileBody = new ProgressRequestBody(file, this);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), fileBody);
        Retrofit retrofit = NetworkClient.getRetrofitClient(getApplicationContext());
        WebServices uploadAPIs = retrofit.create(WebServices.class);

        Call<LogginModel> call = uploadAPIs.uploadfiles(body);

        call.enqueue(new Callback<LogginModel>() {

            @Override
            public void onResponse(Call<LogginModel> call, Response<LogginModel> response) {

                Log.d("response : ", response.message().toString());
                if (response.message().equalsIgnoreCase("OK")) {
                    result = Activity.RESULT_OK;
                    db.DeleteImage(objfile.getId());
                    firebaseutils.UpladingFiles.remove(objfile);
                    if (firebaseutils.UpladingFiles.size() > 0) {
                        UploadFiles(firebaseutils.UpladingFiles.get(0));
                    } else {
//                        notifManager.cancel(NOTIFY_ID);
                        //stopService();
                    }
                }

                Log.d("result", "" + result);
            }

            @Override
            public void onFailure(Call<LogginModel> call, Throwable t) {
                Log.d("error :", t.toString());
                db.UpdateImageStatus(2, objfile.getId());

                firebaseutils.UpladingFiles.remove(objfile);
                if (firebaseutils.UpladingFiles.size() > 0) {
                    UploadFiles(firebaseutils.UpladingFiles.get(0));
                } else {
                    if (notifManager == null) {
                        notifManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    }
                    notifManager.cancel(NOTIFY_ID);
                    //stopService();
                }
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
}