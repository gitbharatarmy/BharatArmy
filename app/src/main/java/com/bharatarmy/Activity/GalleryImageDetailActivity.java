package com.bharatarmy.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bharatarmy.Adapter.GalleryImageDetailAdapter;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.SnapHelperOneByOne;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityGalleryImageDetailBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.http.Url;

import static android.support.v4.content.FileProvider.getUriForFile;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.bharatarmy.Activity.ImagePickerActivity.REQUEST_GALLERY_IMAGE;


public class GalleryImageDetailActivity extends BaseActivity implements View.OnClickListener {
    ActivityGalleryImageDetailBinding activityGalleryImageDetailBinding;
    Context mContext;
    GalleryImageDetailAdapter galleryImageDetailAdapter;
    ArrayList<String> imageList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    String selectedPosition;
    int positon = 0;
    boolean programaticallyScrolled;
    int currentVisibleItem, showPositionImage;
    Uri uri;
    String imageNameStr;
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
        activityGalleryImageDetailBinding.prevImg.setOnClickListener(this);
        activityGalleryImageDetailBinding.nextImg.setOnClickListener(this);
        activityGalleryImageDetailBinding.shareImg.setOnClickListener(this);
    }

    public void setDataValue() {
        activityGalleryImageDetailBinding.toolbarTitleTxt.setText("Image Gallery");
        selectedPosition = getIntent().getStringExtra("positon");
        final Bundle stringArrayList = getIntent().getExtras();
        imageList = stringArrayList.getStringArrayList("data");

        Log.d("imageList", "" + imageList.size());


        for (int i = 0; i < imageList.size(); i++) {
            if (selectedPosition.equalsIgnoreCase(String.valueOf(imageList.get(i)))) {
                positon = i;
            }
            Log.d("position", "" + positon);
            if (selectedPosition.equals(imageList.get(0))) {
                activityGalleryImageDetailBinding.prevImg.setClickable(false);
                activityGalleryImageDetailBinding.prevImg.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            } else {
                activityGalleryImageDetailBinding.prevImg.setClickable(true);
                activityGalleryImageDetailBinding.prevImg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_back, 0, 0, 0);
            }

            if (positon == imageList.size() - 1) {
                activityGalleryImageDetailBinding.nextImg.setClickable(false);
                activityGalleryImageDetailBinding.nextImg.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            } else {
                activityGalleryImageDetailBinding.nextImg.setClickable(true);
                activityGalleryImageDetailBinding.nextImg.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_next, 0);
            }
        }

        galleryImageDetailAdapter = new GalleryImageDetailAdapter(mContext, imageList);//,onTouchListener
        linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false);
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(activityGalleryImageDetailBinding.imageDetailRcvList);
        activityGalleryImageDetailBinding.imageDetailRcvList.setLayoutManager(linearLayoutManager);
        activityGalleryImageDetailBinding.imageDetailRcvList.getLayoutManager().scrollToPosition(positon);
        activityGalleryImageDetailBinding.imageDetailRcvList.setItemAnimator(new DefaultItemAnimator());
        activityGalleryImageDetailBinding.imageDetailRcvList.setAdapter(galleryImageDetailAdapter);

        activityGalleryImageDetailBinding.imageDetailRcvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case SCROLL_STATE_DRAGGING:
                        //Indicated that user scrolled.
                        programaticallyScrolled = false;
                        break;
                    case SCROLL_STATE_IDLE:
                        if (!programaticallyScrolled) {
                            currentVisibleItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                            handleWritingViewNavigationArrows(false);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void handleWritingViewNavigationArrows(boolean scroll) {
        if (currentVisibleItem == (activityGalleryImageDetailBinding.imageDetailRcvList.getAdapter().getItemCount() - 1)) {
            activityGalleryImageDetailBinding.prevImg.setClickable(true);
            activityGalleryImageDetailBinding.nextImg.setClickable(false);
            activityGalleryImageDetailBinding.nextImg.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            activityGalleryImageDetailBinding.prevImg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_back, 0, 0, 0);
        } else if (currentVisibleItem != 0) {
            activityGalleryImageDetailBinding.nextImg.setClickable(true);
            activityGalleryImageDetailBinding.prevImg.setClickable(true);
            activityGalleryImageDetailBinding.nextImg.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_next, 0);
            activityGalleryImageDetailBinding.prevImg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_back, 0, 0, 0);
        } else if (currentVisibleItem == 0) {
            activityGalleryImageDetailBinding.prevImg.setClickable(false);
            activityGalleryImageDetailBinding.nextImg.setClickable(true);
            activityGalleryImageDetailBinding.prevImg.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            activityGalleryImageDetailBinding.nextImg.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_next, 0);
        }
        if (scroll) {
            activityGalleryImageDetailBinding.imageDetailRcvList.smoothScrollToPosition(currentVisibleItem);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                GalleryImageDetailActivity.this.finish();
                break;

            case R.id.next_img:

                showPositionImage = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                for (int i = 0; i < imageList.size(); i++) {
                    if (showPositionImage == i) {
                        imageNameStr = imageList.get(showPositionImage);
                        break;
                    }
                }

                String []splitStr=imageNameStr.split("\\/");
                Log.d("stringName",splitStr[0]+" "+splitStr[1]+" "+splitStr[2]+" "+splitStr[3]+" "+splitStr[4]);
                activityGalleryImageDetailBinding.prevImg.setClickable(true);
                activityGalleryImageDetailBinding.prevImg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_back, 0, 0, 0);
                if (isLastVisible()) {
                    activityGalleryImageDetailBinding.nextImg.setClickable(false);
                    activityGalleryImageDetailBinding.nextImg.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                } else {
                    activityGalleryImageDetailBinding.nextImg.setClickable(true);
                    activityGalleryImageDetailBinding.nextImg.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_next, 0);
                }
                activityGalleryImageDetailBinding.imageDetailRcvList.getLayoutManager().scrollToPosition(linearLayoutManager.findLastVisibleItemPosition() + 1);
                break;

            case R.id.prev_img:
                activityGalleryImageDetailBinding.nextImg.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_next, 0);
                LinearLayoutManager layoutManager = ((LinearLayoutManager) activityGalleryImageDetailBinding.imageDetailRcvList.getLayoutManager());
                int position = layoutManager.findFirstCompletelyVisibleItemPosition();
                if (position != 1) {
                    activityGalleryImageDetailBinding.prevImg.setClickable(true);
                    activityGalleryImageDetailBinding.prevImg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_back, 0, 0, 0);
                } else {
                    activityGalleryImageDetailBinding.prevImg.setClickable(false);
                    activityGalleryImageDetailBinding.prevImg.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }
                activityGalleryImageDetailBinding.imageDetailRcvList.getLayoutManager().scrollToPosition(linearLayoutManager.findFirstVisibleItemPosition() - 1);
                break;

            case R.id.share_img:
                String imageUriStr = "";
                showPositionImage = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                for (int i = 0; i < imageList.size(); i++) {
                    if (showPositionImage == i) {
                        imageUriStr = imageList.get(showPositionImage);
                        break;
                    }
                }
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
                shareIntent.putExtra(Intent.EXTRA_STREAM,uri=getUriForFile(mContext, getPackageName() + ".provider",file));
                shareIntent.setType("image/*");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, "Share It"));
                break;
        }
    }



    boolean isLastVisible() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) activityGalleryImageDetailBinding.imageDetailRcvList.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = galleryImageDetailAdapter.getItemCount();
        Log.d("positon", String.valueOf(pos >= numItems - 2));
        return (pos >= numItems - 2);
    }



}
