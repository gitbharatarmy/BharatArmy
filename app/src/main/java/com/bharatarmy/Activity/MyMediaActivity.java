package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
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
    public List<GalleryImageModel> storearray=new ArrayList<>();
    MyMediaAdapter myMediaAdapter;

    private BroadcastReceiver receiver1 = new BroadcastReceiver() {

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
        AppConfiguration.returnuploadfiles = new ArrayList<>();
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
        List<GalleryImageModel> galleryimage = gson1.fromJson(Utils.getPref(mContext, "gallerylist"), arrayListType1);

        if (Utils.getPref(mContext, "cometonotification").equalsIgnoreCase("returnuploadservice")) {
            if (yourList != null) {
                for (int i = 0; i < galleryimage.size(); i++) {
                    for (int j = 0; j < yourList.size(); j++) {
                        if (!galleryimage.get(i).getUploadcompelet().equalsIgnoreCase("1")) {
                            if (Utils.getPref(getApplicationContext(), "image/video").equalsIgnoreCase("video")){
                                if (galleryimage.get(i).getImageUri().equalsIgnoreCase(yourList.get(j))) {
                                    galleryimage.get(i).setUploadcompelet("1");
                                }
                            }else {
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
                        galleryimage.get(i).setUploadcompelet("");
                    }


                }
            }

        } else {
            if (yourList != null) {
                for (int i = 0; i < galleryimage.size(); i++) {
                    for (int j = 0; j < yourList.size(); j++) {
                        if (Utils.getPref(getApplicationContext(), "image/video").equalsIgnoreCase("video")) {
                            if (galleryimage.get(i).getImageUri().equalsIgnoreCase(yourList.get(j))) {
                                galleryimage.get(i).setUploadcompelet("1");
                            }
                        }else{
                            if (Utils.getFilePathFromUri(mContext, Uri.parse(galleryimage.get(i).getImageUri())).equalsIgnoreCase(yourList.get(j))) {
                                galleryimage.get(i).setUploadcompelet("1");
                            }
                        }
                    }
                }
            }else {
                for (int i = 0; i < galleryimage.size(); i++) {
                    if (!galleryimage.get(i).getUploadcompelet().equalsIgnoreCase("1")) {
                        galleryimage.get(i).setUploadcompelet("");
                    }


                }
            }
        }
        Log.d("galleryimage :", galleryimage.toString());

        Log.d("updategallerylist :", galleryimage.toString());

        Gson gson2 = new Gson();
        String valuesString = gson2.toJson(galleryimage);
        Utils.setPref(mContext, "gallerylist", valuesString);
        Log.d("gallerylist", valuesString.toString());

//storearray=galleryimage;
//
//        Gson gsonstore = new Gson();
//        String valuesstoreString = gsonstore.toJson(galleryimage);
//        Utils.setPref(mContext, "storelist", valuesstoreString);
//        Log.d("storelist", valuesString.toString());



        if (galleryimage != null) {
            myMediaAdapter = new MyMediaAdapter(mContext, galleryimage, new image_click() {
                @Override
                public void image_more_click() {


                    String getSelectedImagetoupload = myMediaAdapter.getDatas().toString();
                    Log.d("getImagetoupload", getSelectedImagetoupload);
                    getSelectedImagetoupload = getSelectedImagetoupload.substring(1, getSelectedImagetoupload.length() - 1);
                    Log.d("getreturnuploadimage", getSelectedImagetoupload);

                    AppConfiguration.returnuploadfiles.add(Uri.parse(getSelectedImagetoupload));

                    Log.d("servicerunning :", "" + Utils.isMyServiceRunning(mContext));

                    if (!Utils.isMyServiceRunning(mContext)) {
                        Intent intent = new Intent(mContext, UploadServiceReturn.class);
                        startService(intent);

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

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver1, new IntentFilter(
                UploadServiceReturn.NOTIFICATION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver1);
    }
}
