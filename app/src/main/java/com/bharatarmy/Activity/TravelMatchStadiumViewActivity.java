package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityTravelMatchStadiumViewBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class TravelMatchStadiumViewActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityTravelMatchStadiumViewBinding activityTravelMatchStadiumViewBinding;
    Context mContext;
    String locationMap;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelMatchStadiumViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_match_stadium_view);
        mContext = TravelMatchStadiumViewActivity.this;

//        Utils.setImageInImageView(String.valueOf(R.drawable.stadium_map),activityTravelMatchStadiumViewBinding.imageFull,mContext);

        activityTravelMatchStadiumViewBinding.imageFull.getPositionAnimator().enter(activityTravelMatchStadiumViewBinding.imageDetailImg, false);
        setListiner();
    }

    public void setListiner(){
        activityTravelMatchStadiumViewBinding.toolbarTitleTxt.setText("View Stadium Map");
        activityTravelMatchStadiumViewBinding.backImg.setOnClickListener(this);
        activityTravelMatchStadiumViewBinding.shareImg.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_img:
                finish();
                break;
            case R.id.share_img:
                Utils.handleClickEvent(mContext,activityTravelMatchStadiumViewBinding.shareImg);
                //Use for Internal Storage file
                File myDir = new File(getExternalCacheDir(), "camera");
                myDir.mkdirs();
                Random generator = new Random();
                int n = 10000;
                n = generator.nextInt(n);
                String fname = "Image-" + n + ".jpg";
                File file = new File(myDir, fname);
                Log.i("file", "" + file);
                if (file.exists())
                    file.delete();
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    Utils.DrawableToBitMap(R.drawable.stadium_map,mContext).compress(Bitmap.CompressFormat.JPEG, 90, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //share image from other application
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, AppConfiguration.SHARETEXT);
//                shareIntent.putExtra(Intent.EXTRA_STREAM,uri=getUriForFile(mContext, getPackageName() + ".fileprovider",file));
                shareIntent.setType("image/*");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, "Share It"));
                break;


        }
    }

}
