package com.bharatarmy.Activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityLocationMapBinding;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import static android.support.v4.content.FileProvider.getUriForFile;

public class LocationMapActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityLocationMapBinding locationMapBinding;
    Context mContext;
    String locationMap;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationMapBinding = DataBindingUtil.setContentView(this, R.layout.activity_location_map);
        mContext = LocationMapActivity.this;

//        setBackButton(LocationMapActivity.this);
//        setTitleText("View Stadium Map");
//        setShareBtn(LocationMapActivity.this,);

        Picasso.with(mContext)
                .load(R.drawable.first_match_map)
                .placeholder(R.drawable.progress_animation)
                .into(locationMapBinding.imageFull);


        locationMapBinding.imageFull.getPositionAnimator().enter(locationMapBinding.imageDetailImg, false);
        setListiner();
    }

    public void setListiner(){
        locationMapBinding.toolbarTitleTxt.setText("View Stadium Map");
        locationMapBinding.backImg.setOnClickListener(this);
        locationMapBinding.shareImg.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_img:
                LocationMapActivity.this.finish();
                break;
            case R.id.share_img:
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
                    Utils.DrawableToBitMap(R.drawable.first_match_map,mContext).compress(Bitmap.CompressFormat.JPEG, 90, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //share image from other application
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, AppConfiguration.SHARETEXT);
                shareIntent.putExtra(Intent.EXTRA_STREAM,uri=getUriForFile(mContext, getPackageName() + ".provider",file));
                shareIntent.setType("image/*");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, "Share It"));
                break;


        }
    }

}
