package com.bharatarmy.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bharatarmy.Models.GetWalkthroughModel;
import com.bharatarmy.Models.WalkthroughData;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.WalkthroughBinding;
import com.bharatarmy.databinding.WelcomeSlide1Binding;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;


// remove comment code 07-06-2019
public class WalkThrough extends AppCompatActivity {

    WalkthroughBinding walkthroughBinding;
    private MyViewPagerAdapter myViewPagerAdapter;
    private ImageView[] dots;
    private ArrayList<Integer> layouts;
    private List<WalkthroughData> walkthroughDataList;
    Context mContext;
    PrefManager prefManager;
    public String isUpdateAvailable, isForceUpdateAvailable, currentVersionStr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = WalkThrough.this;

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        walkthroughBinding = DataBindingUtil.setContentView(this, R.layout.walkthrough);


        Utils.setPref(mContext, "IsSkipLogin", "0");
        Utils.setPref(mContext, "IsLoginUser", "0");
        Utils.setPref(mContext, "IsFirstTime", "1");
        callWalkThroughData();
    }


    @SuppressLint("ResourceAsColor")
    private void addBottomDots(int currentPage) {
        dots = new ImageView[layouts.size()];

//        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
//        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        List<String> colorsActiveList = new ArrayList<>();
        List<String> colorsInactive = new ArrayList<>();
        for (int i = 0; i < layouts.size(); i++) {
//            colorsActiveList.add(String.valueOf(getResources().getColor(R.color.Active_dot_color)));
//            colorsInactive.add(String.valueOf(getResources().getColor(R.color.NotActive_dot_color)));
            colorsActiveList.add(String.valueOf(R.drawable.ball_scroll_primary));
            colorsInactive.add(String.valueOf(R.drawable.ball_scroll));
        }

        int width = getResources().getDimensionPixelSize(R.dimen.viewpager_dots_size);
        int height = getResources().getDimensionPixelSize(R.dimen.viewpager_dots_size);

        walkthroughBinding.layoutDots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
//            dots[i].setText(Html.fromHtml("&#8226;"));
//            dots[i].setTextSize(35);
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width, height);
            parms.setMargins(5, 5, 0, 0);
            dots[i].setLayoutParams(parms);
            dots[i].setImageResource(Integer.parseInt(colorsInactive.get(currentPage)));//   colorsInactive[currentPage]

            walkthroughBinding.layoutDots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setImageResource(Integer.parseInt(colorsActiveList.get(currentPage)));//colorsActive[currentPage]
    }

    private int getItem(int i) {
        return walkthroughBinding.viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(WalkThrough.this, Splash_Screen.class));
        finish();
    }




    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);//position


            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.size() - 1) {
                // last page. make button text to GOT IT
                walkthroughBinding.btnNext.setText(getString(R.string.start));
                walkthroughBinding.btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                walkthroughBinding.btnNext.setText(getString(R.string.next));
                walkthroughBinding.btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    // Api calling GetWalkthrough
    public void callWalkThroughData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), WalkThrough.this);
            return;
        }

        ApiHandler.getApiService().getWalkthrough(getwalkthrough(), new retrofit.Callback<GetWalkthroughModel>() {
            @Override
            public void success(GetWalkthroughModel getWalkthroughModel, Response response) {

                if (getWalkthroughModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (getWalkthroughModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (getWalkthroughModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (getWalkthroughModel.getIsValid() == 1) {
                    isUpdateAvailable = String.valueOf(getWalkthroughModel.getIsUpdateAvailable());
                    isForceUpdateAvailable = String.valueOf(getWalkthroughModel.getIsForceUpdate());
//                    isForceUpdateAvailable = "0";
                    currentVersionStr = String.valueOf(getWalkthroughModel.getCurrentVersion());
                    if (isUpdateAvailable.equalsIgnoreCase("1")) {
                        Utils.checkupdateApplication(mContext,WalkThrough.this, isForceUpdateAvailable, currentVersionStr);
                    }
                    if (getWalkthroughModel.getData() != null) {
                        layouts = new ArrayList<Integer>();
                        walkthroughDataList = new ArrayList<WalkthroughData>();

                        walkthroughDataList = getWalkthroughModel.getData();
                        for (int i = 0; i < getWalkthroughModel.getData().size(); i++) {
                            layouts.add(getWalkthroughModel.getData().size());
                        }

                        // adding bottom dots
                        addBottomDots(0);

                        // making notification bar transparent
                        changeStatusBarColor();

                        myViewPagerAdapter = new MyViewPagerAdapter();
                        walkthroughBinding.viewPager.setAdapter(myViewPagerAdapter);

                        walkthroughBinding.btnNext.setVisibility(View.VISIBLE);
                        walkthroughBinding.btnSkip.setVisibility(View.VISIBLE);

                        walkthroughBinding.viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

                        walkthroughBinding.btnSkip.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Utils.goToLogin(mContext, "walkThrough");
                                finish();
                            }
                        });

                        walkthroughBinding.btnNext.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // checking for last page
                                // if last page home screen will be launched
                                int current = getItem(+1);
                                if (current < layouts.size()) {
                                    // move to next screen
                                    walkthroughBinding.viewPager.setCurrentItem(current);
                                } else {
                                    Utils.goToLogin(mContext, "walkThrough");
                                    finish();
                                }
                            }
                        });
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

    private Map<String, String> getwalkthrough() {
        Map<String, String> map = new HashMap<>();
        return map;
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup parent, int position) {
            WelcomeSlide1Binding welcomeSlide1Binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.welcome_slide1, parent, false);

            Utils.setImageInImageView(walkthroughDataList.get(position).getBannerImageURL(), welcomeSlide1Binding.walkthroughBannerImg, mContext);

            welcomeSlide1Binding.headingTxt.setText(walkthroughDataList.get(position).getHeaderText());

            parent.addView(welcomeSlide1Binding.getRoot());

            return welcomeSlide1Binding.getRoot();
        }

        @Override
        public int getCount() {
            return layouts.size();
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
}