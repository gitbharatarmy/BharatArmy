package com.bharatarmy.Activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.bharatarmy.Adapter.ViewPagerSliderAdapter;
import com.bharatarmy.Models.GetWalkthroughModel;
import com.bharatarmy.Models.WalkthroughData;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.FixedSpeedScroller;
import com.bharatarmy.Utility.ParallaxRecyclerView;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityWalkthorughNewBinding;
import com.github.abdularis.piv.ScrollTransformImageView;
import com.github.abdularis.piv.transformer.HorizontalScaleTransformer;
import com.nineoldandroids.view.ViewHelper;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class WalkthorughNewActivity extends AppCompatActivity {
    ActivityWalkthorughNewBinding activityWalkthorughNewBinding;
    Context mContext;
    private ArrayList<Integer> layouts;
    private MyImageViewPagerAdapter myViewPagerAdapter;
    private List<WalkthroughData> walkthroughDataList;
    private ArrayList<Integer> imageSlider;

    int mCurrentSelectedScreen, mNextSelectedScreen;
    ScrollTransformImageView img;
    Timer timer;
    int currentPage;
    int DELAY_MS = 0, PERIOD_MS = 7000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWalkthorughNewBinding = DataBindingUtil.setContentView(this, R.layout.activity_walkthorugh_new);
        mContext = WalkthorughNewActivity.this;

        setListiner();
        setDataValue();
        callWalkThroughData();

    }

    public void setListiner() {
//        img = findViewById(R.id.image_view);
//        img.setViewTransformer(new HorizontalScaleTransformer(0.1f));

        imageSlider = new ArrayList<>();
        imageSlider.add(R.drawable.ic_profile);
        imageSlider.add(R.drawable.ic_home);
        imageSlider.add(R.drawable.ic_email);
        imageSlider.add(R.drawable.ic_avatar);
        imageSlider.add(R.drawable.ic_message);
    }

    public void setDataValue() {
    }


    private int getItem(int i) {
        return activityWalkthorughNewBinding.viewPager.getCurrentItem() + i;
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
//            addBottomDots(position);//position
//            activityWalkthorughNewBinding.imageView.setImageResource(imageSlider.get(position));
//
//            Animation a = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
//            a.reset();
//
//            activityWalkthorughNewBinding.headerTxt.clearAnimation();
//            activityWalkthorughNewBinding.headerTxt.startAnimation(a);
//            activityWalkthorughNewBinding.headerTxt.setText(walkthroughDataList.get(position).getHeaderText());

            if (position == layouts.size() - 1) {
                // last page. make button text to GOT IT
                activityWalkthorughNewBinding.btnNext.setText(getString(R.string.start));
                activityWalkthorughNewBinding.btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                activityWalkthorughNewBinding.btnNext.setText(getString(R.string.next));
                activityWalkthorughNewBinding.btnSkip.setVisibility(View.VISIBLE);
            }


            mCurrentSelectedScreen = position;
            mNextSelectedScreen = position;
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {


            if (arg0 == mCurrentSelectedScreen) {
                // We are moving to next screen on right side
                if (arg1 > 0.5) {
                    // Closer to next screen than to current
                    if (arg0 + 1 != mNextSelectedScreen) {
                        mNextSelectedScreen = arg0 + 1;
                    }
                } else {
                    // Closer to current screen than to next
                    if (arg0 != mNextSelectedScreen) {
                        mNextSelectedScreen = arg0;

                    }
                }
            } else {
                // We are moving to next screen left side
                if (arg1 > 0.5) {
                    // Closer to current screen than to next
                    if (arg0 + 1 != mNextSelectedScreen) {
                        mNextSelectedScreen = arg0 + 1;

                    }
                } else {
                    // Closer to next screen than to current
                    if (arg0 != mNextSelectedScreen) {
                        mNextSelectedScreen = arg0;

                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            if (ViewPager.SCROLL_STATE_DRAGGING == arg0) {

            }
        }
    };


    /**
     * View pager adapter
     */
    public class MyImageViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        ImageView imageView;
TextView header_txt;

        public MyImageViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.walkthrough_newslider, container, false);

            imageView = (ImageView) view.findViewById(R.id.imageView);
//            header_txt=(TextView)view.findViewById(R.id.header_txt);


//            Animation a = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
//            a.reset();
//
//            header_txt.clearAnimation();
//            header_txt.startAnimation(a);
//            header_txt.setText(walkthroughDataList.get(position).getHeaderText());
            imageView.setImageResource(layouts.get(position));


            container.addView(view);

            return view;
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


    // Api calling GetWalkthrough
    public void callWalkThroughData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), WalkthorughNewActivity.this);
            return;
        }


//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getWalkthrough(getwalkthrough(), new retrofit.Callback<GetWalkthroughModel>() {
            @Override
            public void success(GetWalkthroughModel getWalkthroughModel, Response response) {
//                Utils.dismissDialog();
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
                    if (getWalkthroughModel.getData() != null) {
                        // layouts of all welcome sliders
                        // add few more layouts if you want
                        layouts = new ArrayList<Integer>();
                        walkthroughDataList = new ArrayList<WalkthroughData>();

                        walkthroughDataList = getWalkthroughModel.getData();
                        layouts = new ArrayList<>();
                        for (int i = 0; i < getWalkthroughModel.getData().size(); i++) {
                            layouts.add(R.drawable.walk1);
                        }
                        Animation a = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
                        a.reset();

                        activityWalkthorughNewBinding.headerTxt.clearAnimation();
                        activityWalkthorughNewBinding.headerTxt.startAnimation(a);
                        activityWalkthorughNewBinding.headerTxt.setText(walkthroughDataList.get(0).getHeaderText());
                        activityWalkthorughNewBinding.imageView.setImageResource(imageSlider.get(0));

                        myViewPagerAdapter = new MyImageViewPagerAdapter();
                        activityWalkthorughNewBinding.viewPager.setAdapter(myViewPagerAdapter);
                        activityWalkthorughNewBinding.wormDotsIndicator.setViewPager(activityWalkthorughNewBinding.viewPager);
                        activityWalkthorughNewBinding.viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
//                        activityWalkthorughNewBinding.viewPager.setPageTransformer(true, new CrossfadePageTransformer());
                        activityWalkthorughNewBinding.viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
                            @Override
                            public void transformPage(@NonNull View view, float position) {


                                 int pageWidth = view.getWidth();


                                if (position < -1) { // [-Infinity,-1)
                                    // This page is way off-screen to the left.
                                    view.setAlpha(0);

                                } else if (position <= 1) { // [-1,1]

                                    activityWalkthorughNewBinding.imageView.setTranslationX(-position * (pageWidth / 4)); //Half the normal speed

                                } else { // (1,+Infinity]
                                    // This page is way off-screen to the right.
                                    view.setAlpha(0);
                                }

                            }
                        });

//                        activityWalkthorughNewBinding.bgLinear.setVisibility(View.VISIBLE);
                        activityWalkthorughNewBinding.bottomRel.setVisibility(View.VISIBLE);

                        activityWalkthorughNewBinding.btnSkip.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent login = new Intent(mContext, LoginNewActivity.class);
                                startActivity(login);
//                                overridePendingTransition(R.anim.slide_in_left,0);
                            }
                        });

                        activityWalkthorughNewBinding.btnNext.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // checking for last page
                                // if last page home screen will be launched
                                int current = getItem(+1);
                                if (current < layouts.size()) {
                                    // move to next screen
                                    activityWalkthorughNewBinding.viewPager.setCurrentItem(current);
                                } else {
                                    Intent login = new Intent(mContext, LoginNewActivity.class);
                                    startActivity(login);
//                                    overridePendingTransition(R.anim.slide_in_left,0);
                                }
                            }
                        });
//                        try {
//                            Field mScroller;
//                            mScroller = ViewPager.class.getDeclaredField("mScroller");
//                            mScroller.setAccessible(true);
//                            FixedSpeedScroller scroller = new FixedSpeedScroller(activityWalkthorughNewBinding.viewPager.getContext());
//                            // scroller.setFixedDuration(5000);
//                            mScroller.set(activityWalkthorughNewBinding.viewPager, scroller);
//                        } catch (NoSuchFieldException e) {
//                        } catch (IllegalArgumentException e) {
//                        } catch (IllegalAccessException e) {
//                        }
//
//
//                        final Handler handler = new Handler();
//                        final Runnable Update = new Runnable() {
//                            public void run() {
//                                if (currentPage == layouts.size()) {
//                                    currentPage = 0;
//                                }
//                                activityWalkthorughNewBinding.viewPager.setCurrentItem(currentPage++, true);
//                            }
//                        };
//
//                        timer = new Timer(); // This will create a new Thread
//
//                        timer.schedule(new TimerTask() { // task to be scheduled
//                            @Override
//                            public void run() {
//                                handler.post(Update);
//                            }
//                        }, DELAY_MS, PERIOD_MS);


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
}
