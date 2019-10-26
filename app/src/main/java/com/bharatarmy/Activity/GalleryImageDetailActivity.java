package com.bharatarmy.Activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Adapter.GalleryImageDetailAdapter;
import com.bharatarmy.Interfaces.FragmentRefreshListener;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.SnapHelperOneByOne;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityGalleryImageDetailBinding;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;

import static androidx.core.content.FileProvider.getUriForFile;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;


public class GalleryImageDetailActivity extends BaseActivity implements View.OnClickListener {
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
//                        imageNameStr=imageAddusername.get(showPositionImage);
                        break;
                    }
                }
                Log.d("imageNameStr :", imageNameStr);

//                String[] splitStr = imageNameStr.split("\\/");
//                Log.d("stringName", splitStr[0] + " " + splitStr[1] + " " + splitStr[2] + " " + splitStr[3] + " " + splitStr[4]);
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

        }
    }


    boolean isLastVisible() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) activityGalleryImageDetailBinding.imageDetailRcvList.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = galleryImageDetailAdapter.getItemCount();
        Log.d("positon", String.valueOf(pos >= numItems - 2));
        return (pos >= numItems - 2);
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
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri = getUriForFile(mContext, getPackageName() + ".provider", file));
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Share It"));
    }

}
