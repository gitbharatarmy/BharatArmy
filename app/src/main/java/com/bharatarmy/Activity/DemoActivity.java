package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alexvasilkov.gestures.views.GestureImageView;
import com.bharatarmy.Adapter.ImageListAdapter;
import com.bharatarmy.Interfaces.PageCurl;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.GetWalkthroughModel;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.Models.WalkthroughData;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.VideoModule.FullscreenVideoView;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;


public class DemoActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private List<ImageDetailModel> walkthroughDataList;

    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = DemoActivity.this;

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_demo);

        viewPager = (ViewPager) findViewById(R.id.demo_pager);
callImageGalleryData();

    }


    // Api calling GetImageGalleryData
    public void callImageGalleryData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), DemoActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getBAGallery(getImageGalleryData(), new retrofit.Callback<ImageMainModel>() {
            @Override
            public void success(ImageMainModel imageMainModel, Response response) {
                Utils.dismissDialog();
                if (imageMainModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (imageMainModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (imageMainModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (imageMainModel.getIsValid() == 1) {

                    if (imageMainModel.getData() != null) {

                        walkthroughDataList=imageMainModel.getData();
                        fillImageGallery();
                    }

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });


    }

    private Map<String, String> getImageGalleryData() {
        Map<String, String> map = new HashMap<>();
        map.put("PageIndex", "0");
        map.put("PageSize", "15");
        map.put("MemberId",String.valueOf(Utils.getAppUserId(mContext)));
        return map;
    }

    public void fillImageGallery() {
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
       viewPager.setPageTransformer(false, new PageCurlPageTransformer());
    }







    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        private ImageView banner_img;
        private TextView header_txt;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.demo_item_list, container, false);


            banner_img=(GestureImageView)view.findViewById(R.id.image_full);



            Utils.setImageInImageView(walkthroughDataList.get(position).getGalleryURL(), banner_img, mContext);



            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return walkthroughDataList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }
    }


    public class PageCurlPageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {

            if (page instanceof PageCurl) {
                if (position > -1.0F && position < 1.0F) {
                    // hold the page steady and let the views do the work
                    page.setTranslationX(-position * page.getWidth());
                } else {
                    page.setTranslationX(0.0F);
                }
                if (position <= 1.0F && position >= -1.0F) {
                    ((PageCurl) page).setCurlFactor(position);
                }
            }
        }
    }
}