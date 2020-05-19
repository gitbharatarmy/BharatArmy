package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bharatarmy.Adapter.BAAllQuizListAdapter;
import com.bharatarmy.Models.ExpolringCategoryModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.Models.QuizDetailModel;
import com.bharatarmy.Models.QuizMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityBAQuizListBinding;
import com.bharatarmy.databinding.BaQuizGalleryListItemBinding;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class BAQuizListActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityBAQuizListBinding activityBAQuizListBinding;
    Context mContext;
    List<QuizDetailModel> recommendedquizList;
    List<QuizDetailModel> quizModelList;
    BAAllQuizListAdapter baAllQuizListAdapter;
    GridLayoutManager gridLayoutManager;
    MyBAQuizListGalleryViewPagerAdapter myBAQuizListGalleryViewPagerAdapter;
    private TextView[] dots;
    public String isUpdateAvailable, isForceUpdateAvailable, currentVersionStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBAQuizListBinding = DataBindingUtil.setContentView(this, R.layout.activity_b_a_quiz_list);
        mContext = BAQuizListActivity.this;

        init();
        setListiner();
    }


    public void init() {
        activityBAQuizListBinding.shimmerViewContainerQuizlist.setVisibility(View.VISIBLE);
        activityBAQuizListBinding.shimmerViewContainerQuizlist.startShimmerAnimation();
        activityBAQuizListBinding.quizListRcv.setVisibility(View.GONE);
        activityBAQuizListBinding.quizlistGalleryViewpager.setVisibility(View.GONE);
        callBAQuizData();

    }

    public void setActivityBAQuizListBinding() {
        recommendedquizList = new ArrayList<>();
        for (int i = 0; i < quizModelList.size(); i++) {
            if (quizModelList.get(i).getDisplayInListingBanner().equals(1)) {
                recommendedquizList.add(quizModelList.get(i));
            }
        }

        addBottomDots(0);
        myBAQuizListGalleryViewPagerAdapter = new MyBAQuizListGalleryViewPagerAdapter();
        activityBAQuizListBinding.quizlistGalleryViewpager.setAdapter(myBAQuizListGalleryViewPagerAdapter);
        activityBAQuizListBinding.quizlistGalleryViewpager.addOnPageChangeListener(viewPagerPageChangeListener);


        gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
        activityBAQuizListBinding.quizListRcv.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

        baAllQuizListAdapter = new BAAllQuizListAdapter(mContext, quizModelList);
        activityBAQuizListBinding.quizListRcv.setItemAnimator(new DefaultItemAnimator());
        activityBAQuizListBinding.quizListRcv.setAdapter(baAllQuizListAdapter);

    }


    public void setListiner() {
        setSupportActionBar(activityBAQuizListBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        activityBAQuizListBinding.backImg.setOnClickListener(this);

        activityBAQuizListBinding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(final AppBarLayout appBarLayout, int verticalOffset) {
                //Initialize the size of the scroll
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                //Check if the view is collapsed
                if (scrollRange + verticalOffset == 0) {
                    activityBAQuizListBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.heading_bg));
                    activityBAQuizListBinding.collapsingToolbar.setTitle(getResources().getString(R.string.quiz_header));
                    Typeface typeface = ResourcesCompat.getFont(mContext, R.font.helveticaneueltstdbdcn);
                    activityBAQuizListBinding.collapsingToolbar.setCollapsedTitleTypeface(typeface);
                    activityBAQuizListBinding.collapsingToolbar.setExpandedTitleTypeface(typeface);
                    activityBAQuizListBinding.collapsingToolbar.setCollapsedTitleGravity(Gravity.START);
                    activityBAQuizListBinding.collapsingToolbar.setExpandedTitleGravity(Gravity.START);

                    isShow = true;
                } else if (isShow) {
                    activityBAQuizListBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                    activityBAQuizListBinding.collapsingToolbar.setTitle(" ");
                    isShow = false;
                }

            }
        });
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);//position

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                backActivity();
                break;
        }
    }

    public void backActivity() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                backActivity();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("ResourceAsColor")
    private void addBottomDots(int currentPage) {
        dots = new TextView[recommendedquizList.size()];
        List<String> colorsActiveList = new ArrayList<>();
        List<String> colorsInactive = new ArrayList<>();
        for (int i = 0; i < recommendedquizList.size(); i++) {
            colorsActiveList.add(String.valueOf(getResources().getColor(R.color.splash_bg_color)));
            colorsInactive.add(String.valueOf(getResources().getColor(R.color.black)));
        }


        activityBAQuizListBinding.viewPagerDotlinear.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            activityBAQuizListBinding.viewPagerDotlinear.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Integer.parseInt(colorsActiveList.get(currentPage)));//colorsActive[currentPage]
    }

    private int getItem(int i) {
        return activityBAQuizListBinding.quizlistGalleryViewpager.getCurrentItem() + i;
    }

    public class MyBAQuizListGalleryViewPagerAdapter extends PagerAdapter {

        public MyBAQuizListGalleryViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup parent, int position) {
            BaQuizGalleryListItemBinding baQuizGalleryListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.ba_quiz_gallery_list_item, parent, false);


            Utils.setImageInImageView(recommendedquizList.get(position).getQuizMobileBannerUrl(), baQuizGalleryListItemBinding.allQuizMainBannerImg, mContext);

            baQuizGalleryListItemBinding.allQuizMainTitleTxt.setText(recommendedquizList.get(position).getQuizHeaderText());
            baQuizGalleryListItemBinding.allQuizDateTxt.setText(recommendedquizList.get(position).getStrDisplayDate());
            baQuizGalleryListItemBinding.allQuizTypeTxt.setText(recommendedquizList.get(position).getBACategoryName());

            parent.addView(baQuizGalleryListItemBinding.getRoot());

            return baQuizGalleryListItemBinding.getRoot();
        }

        @Override
        public int getCount() {
            return recommendedquizList.size();
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

    // Api calling GetBAQuizData
    public void callBAQuizData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), BAQuizListActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getQuizListing(getBAQuizData(), new retrofit.Callback<QuizMainModel>() {
            @Override
            public void success(QuizMainModel quizMainModel, Response response) {
                Utils.dismissDialog();
                if (quizMainModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (quizMainModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (quizMainModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (quizMainModel.getIsValid() == 1) {
                    isUpdateAvailable = String.valueOf(quizMainModel.getIsUpdateAvailable());
                    isForceUpdateAvailable = String.valueOf(quizMainModel.getIsForceUpdate());
                    currentVersionStr = String.valueOf(quizMainModel.getCurrentVersion());
                    if (isUpdateAvailable.equalsIgnoreCase("1")) {
                        Utils.checkupdateApplication(mContext, BAQuizListActivity.this, isForceUpdateAvailable, currentVersionStr);
                    }
                    if (quizMainModel.getData() != null) {
                        activityBAQuizListBinding.shimmerViewContainerQuizlist.stopShimmerAnimation();
                        activityBAQuizListBinding.shimmerViewContainerQuizlist.setVisibility(View.GONE);
                        activityBAQuizListBinding.quizListRcv.setVisibility(View.VISIBLE);
                        activityBAQuizListBinding.quizlistGalleryViewpager.setVisibility(View.VISIBLE);

                        quizModelList = quizMainModel.getData();
                        setActivityBAQuizListBinding();
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

    private Map<String, String> getBAQuizData() {
        Map<String, String> map = new HashMap<>();
        return map;
    }
}
