package com.bharatarmy.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Adapter.GalleryImageDetailAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.SnapHelperOneByOne;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityGalleryImageDetailBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

import static androidx.core.content.FileProvider.getUriForFile;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

// remove extra code 26/12/2019 code backup in 26/12/2019
public class GalleryImageDetailActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityGalleryImageDetailBinding activityGalleryImageDetailBinding;
    public static Context mContext;
    GalleryImageDetailAdapter galleryImageDetailAdapter;
    ArrayList<String> imageList = new ArrayList<>();
    ArrayList<String> imageAddusername = new ArrayList<>();
    ArrayList<String> imageDuration = new ArrayList<>();
    ArrayList<String> imageId = new ArrayList<>();
    ArrayList<String> imageLike = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    String selectedPosition;
    int  showPositionImage;
    Uri uri;
    int positon = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGalleryImageDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_gallery_image_detail);
        mContext = GalleryImageDetailActivity.this;


        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
        setDataValue();
        setListiner();
    }

    public void setListiner() {
        activityGalleryImageDetailBinding.backImg.setOnClickListener(this);
             activityGalleryImageDetailBinding.shareArticle.setOnClickListener(this);

    }

    public void setDataValue() {
        activityGalleryImageDetailBinding.toolbarTitleTxt.setText("Image Gallery");
        selectedPosition = getIntent().getStringExtra("positon");
        final Bundle stringArrayList = getIntent().getExtras();
        imageList = stringArrayList.getStringArrayList("data");
        imageAddusername = stringArrayList.getStringArrayList("dataName");
        imageDuration = stringArrayList.getStringArrayList("dataDuration");
        imageId = stringArrayList.getStringArrayList("dataId");
        imageLike = stringArrayList.getStringArrayList("dataLike");
        Log.d("imageList", "" + imageList.size());
        for (int i = 0; i < imageList.size(); i++) {
            if (selectedPosition.equalsIgnoreCase(String.valueOf(imageList.get(i)))) {
                positon = i;
            }
        }
        galleryImageDetailAdapter = new GalleryImageDetailAdapter(mContext, GalleryImageDetailActivity.this,
                imageList, imageAddusername, imageDuration, imageId, imageLike, new image_click() {
            @Override
            public void image_more_click() {
                shareImage();
            }
        });//,onTouchListener
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(activityGalleryImageDetailBinding.imageDetailRcvList);

        activityGalleryImageDetailBinding.imageDetailRcvList.setLayoutManager(linearLayoutManager);
        activityGalleryImageDetailBinding.imageDetailRcvList.getLayoutManager().scrollToPosition(positon);

        activityGalleryImageDetailBinding.imageDetailRcvList.setItemAnimator(new DefaultItemAnimator());
        activityGalleryImageDetailBinding.imageDetailRcvList.setAdapter(galleryImageDetailAdapter);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                GalleryImageDetailActivity.this.finish();
                break;
        }
    }

    public void shareImage() {
        String imageUriStr = "";
        showPositionImage = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        for (int i = 0; i < imageList.size(); i++) {
            if (showPositionImage == i) {
                imageUriStr = imageList.get(showPositionImage);
                break;
            }
        }
        //Use for Internal Storage file

        File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "BharatArmy");
        if (!myDir.exists()) myDir.mkdirs();
        String fname ="IMG_"+Utils.imagesaveDate()+"_BA"+Utils.imagesavetime()+".jpg";
        File file = new File(myDir,fname ); //Utils.camerafilesavepath()
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


    @Override
    protected void onStop() {
        super.onStop();

    }
}
