package com.bharatarmy.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bharatarmy.Adapter.GalleryImageDetailAdapter;
import com.bharatarmy.R;
import com.bharatarmy.Utility.SnapHelperOneByOne;
import com.bharatarmy.databinding.ActivityGalleryImageDetailBinding;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;


public class GalleryImageDetailActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityGalleryImageDetailBinding activityGalleryImageDetailBinding;
    Context mContext;
    GalleryImageDetailAdapter galleryImageDetailAdapter;
    public List<String> imageList = new ArrayList<>();

    LinearLayoutManager linearLayoutManager;
    String selectedPosition;
    int positon = 0;

    boolean programaticallyScrolled;
    int currentVisibleItem;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGalleryImageDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_gallery_image_detail);
        mContext = GalleryImageDetailActivity.this;

        setListiner();
        setDataValue();
    }

    public void setListiner() {
        selectedPosition = getIntent().getStringExtra("positon");
        activityGalleryImageDetailBinding.backImg.setOnClickListener(this);
        activityGalleryImageDetailBinding.prevImg.setOnClickListener(this);
        activityGalleryImageDetailBinding.nextImg.setOnClickListener(this);



    }

    public void setDataValue() {
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/1.jpg");
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/2.jpg");
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/3.jpg");
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/4.jpeg");
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/5.jpeg");
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/6.jpeg");
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/7.jpeg");
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/8.jpeg");
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/9.jpeg");


        for (int i = 0; i < imageList.size(); i++) {
            if (selectedPosition.equalsIgnoreCase(String.valueOf(imageList.get(i)))) {
                positon = i;
            }

            if (selectedPosition.equals(imageList.get(0))){
                activityGalleryImageDetailBinding.prevImg.setClickable(false);
                activityGalleryImageDetailBinding.prevImg.setCompoundDrawablesWithIntrinsicBounds(0, 0,0, 0);
            }else{
                activityGalleryImageDetailBinding.prevImg.setClickable(true);
                activityGalleryImageDetailBinding.prevImg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_back, 0, 0, 0);
            }

            if (positon==imageList.size()-1){
                activityGalleryImageDetailBinding.nextImg.setClickable(false);
                activityGalleryImageDetailBinding.nextImg.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }else{
                activityGalleryImageDetailBinding.nextImg.setClickable(true);
                activityGalleryImageDetailBinding.nextImg.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_next, 0);
            }
        }
        Log.d("position", "" + positon);


//        activityGalleryImageDetailBinding.imageDetailRcvList.setLayoutFrozen(true);
//        RecyclerViewDisabler disabler = new RecyclerViewDisabler(true);
//        activityGalleryImageDetailBinding.imageDetailRcvList.addOnItemTouchListener(disabler);
//
//// TO ENABLE/DISABLE JUST USE THIS
//        disabler.setEnable(false);


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
                activityGalleryImageDetailBinding.prevImg.setClickable(true);
                activityGalleryImageDetailBinding.prevImg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_back, 0, 0, 0);
                if (isLastVisible()) {
                    activityGalleryImageDetailBinding.nextImg.setClickable(false);
                    activityGalleryImageDetailBinding.nextImg.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }else{
                    activityGalleryImageDetailBinding.nextImg.setClickable(true);
                    activityGalleryImageDetailBinding.nextImg.setCompoundDrawablesWithIntrinsicBounds( 0, 0, R.drawable.ic_next, 0);
                }
                activityGalleryImageDetailBinding.imageDetailRcvList.getLayoutManager().scrollToPosition(linearLayoutManager.findLastVisibleItemPosition() + 1);
                break;

            case R.id.prev_img:
                activityGalleryImageDetailBinding.nextImg.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_next, 0);
                LinearLayoutManager layoutManager = ((LinearLayoutManager) activityGalleryImageDetailBinding.imageDetailRcvList.getLayoutManager());
                int position = layoutManager.findFirstCompletelyVisibleItemPosition();
                if(position != 1){
                    activityGalleryImageDetailBinding.prevImg.setClickable(true);
                    activityGalleryImageDetailBinding.prevImg.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_back, 0, 0, 0);
                }else {
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
        return (pos >= numItems -2);
    }
//    boolean isFirstVisible() {
//        LinearLayoutManager layoutManager = ((LinearLayoutManager) activityGalleryImageDetailBinding.imageDetailRcvList.getLayoutManager());
//        int pos = layoutManager.findFirstCompletelyVisibleItemPosition();
//        int numItems = galleryImageDetailAdapter.getItemCount();
//        Log.d("positon", String.valueOf(pos <= numItems - 2));
//        return (pos <= numItems -2);
//    }

}
