package com.bharatarmy.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.ViewSwitcher;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bharatarmy.Activity.DashboardActivity;
import com.bharatarmy.Activity.MoreStoryActivity;
import com.bharatarmy.Activity.Splash_Screen;
import com.bharatarmy.Activity.VideoDetailActivity;
import com.bharatarmy.Activity.WalkThrough;
import com.bharatarmy.Adapter.BharatArmyStoriesAdapter;
import com.bharatarmy.Adapter.MainPageChildAdapter;
import com.bharatarmy.Adapter.MainPageDealsAdapter;
import com.bharatarmy.Adapter.MyBgpageAdapter;
import com.bharatarmy.Adapter.MyPagerAdapter;
import com.bharatarmy.Adapter.UpcomingDashboardAdapter;
import com.bharatarmy.AlphaPageTransformer;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.DashboardDataModel;
import com.bharatarmy.Models.DashboardModel;
import com.bharatarmy.Models.HomeTemplateDetailModel;
import com.bharatarmy.Models.HomeTemplateModel;
import com.bharatarmy.Models.StoryDashboardData;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.Models.UpcommingDashboardModel;
import com.bharatarmy.R;
import com.bharatarmy.CountDownClockHome;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.SnapHelperOneByOne;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentHomeBinding;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.leinardi.android.speeddial.SpeedDialView;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;


// change the code backup 22/07/2019  & 27/07/2019
public class HomeFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentHomeBinding fragmentHomeBinding;
    private View rootView;
    private Context mContext;
    DashboardDataModel getDashboardDataModel;
    UpcomingDashboardAdapter upcomingDashboardAdapter;
    BharatArmyStoriesAdapter bharatArmyStoriesAdapter;
    List<HomeTemplateDetailModel> homeTemplateDetailModelList;
    String categoryIdStr, categoryNameStr, wheretocome;
    List<UpcommingDashboardModel> upcommingDashboardModelList;
    List<StoryDashboardData> storyDashboardDataList;
    private TransitionDrawable mTransition;
    private int animationCounter = 1;
    private Handler imageSwitcherHandler;
    FloatingActionButton fab;
    SpeedDialView speedDial;
    int mNextSelectedScreen, mCurrentSelectedScreen = 0;
    public static OnItemClick mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        rootView = fragmentHomeBinding.getRoot();
        mContext = getActivity().getApplicationContext();
        AppConfiguration.position = 0;
        fab = getActivity().findViewById(R.id.fab);
        fab.hide();
        speedDial = getActivity().findViewById(R.id.speedDial);
        speedDial.setVisibility(View.GONE);
        return rootView;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
        } else {
            fragmentHomeBinding.shimmerViewContainerhome.startShimmerAnimation();
//            Utils.showUpdateDialog(getActivity());
            callHomeBannerData();
            callDashboardData();
        }
        setListiner();


    }

    public void setListiner() {
        //Countdown Timer
        Date endDate = new Date();
        final long[] diffInMilis = new long[1];
        final Date startDate = new Date();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
            String dateToStr = format.format(startDate);
            Log.d("Todaytime", dateToStr);
            SimpleDateFormat formatendDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");

            endDate = formatendDate.parse("30/05/2019 03:00:00 PM");


            final Date finalEndDate = endDate;
//                    Calculate the difference in millisecond between two dates
            diffInMilis[0] = finalEndDate.getTime() - startDate.getTime();
        } catch (ParseException ex) {

        }

        fragmentHomeBinding.timerProgramCountdown.startCountDown(diffInMilis[0]);
        fragmentHomeBinding.timerProgramCountdown.setCountdownListener(new CountDownClockHome.CountdownCallBack() {
            @Override
            public void countdownAboutToFinish() {

            }

            @Override
            public void countdownFinished() {
                fragmentHomeBinding.timerProgramCountdown.resetCountdownTimer();
            }
        });

        //  use for Advertisement image
        Animation in = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(mContext, R.anim.fade_out);
        fragmentHomeBinding.advImg.setInAnimation(in);
        fragmentHomeBinding.advImg.setOutAnimation(out);
        fragmentHomeBinding.advImg.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(mContext);
                myView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                myView.setLayoutParams(new
                        ImageSwitcher.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                return myView;
            }
        });

        imageSwitcherHandler = new Handler(Looper.getMainLooper());
        imageSwitcherHandler.post(new Runnable() {
            @Override
            public void run() {
                switch (animationCounter++) {
                    case 1:
                        fragmentHomeBinding.advImg.setImageResource(R.drawable.ad);
                        break;
                    case 2:
                        fragmentHomeBinding.advImg.setImageResource(R.drawable.ad2);
                        break;

                }
                animationCounter %= 4;
                if (animationCounter == 0) animationCounter = 1;

                imageSwitcherHandler.postDelayed(this, 10000);
            }
        });


        fragmentHomeBinding.subTitleTxt.setVisibility(View.GONE);
        fragmentHomeBinding.titleTxt.setVisibility(View.GONE);
        fragmentHomeBinding.knowMore.setVisibility(View.GONE);
        fragmentHomeBinding.shimmerViewContainer.startShimmerAnimation();
        fragmentHomeBinding.upcomingRcyList.showShimmerAdapter();
        fragmentHomeBinding.armyStoryRcyList.showShimmerAdapter();

        fragmentHomeBinding.knowMore.setOnClickListener(this);
        fragmentHomeBinding.advImg.setOnClickListener(this);
    }

    // Api calling GetDashboardData
    public void callDashboardData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getDashboard(getDashboardData(), new retrofit.Callback<DashboardModel>() {
            @Override
            public void success(DashboardModel getDashboardModel, Response response) {
                Utils.dismissDialog();
                if (getDashboardModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (getDashboardModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (getDashboardModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (getDashboardModel.getIsValid() == 1) {
                    if (getDashboardModel.getData() != null) {
                        getDashboardDataModel = getDashboardModel.getData();
                        fragmentHomeBinding.shimmerViewContainer.stopShimmerAnimation();
                        fragmentHomeBinding.shimmerViewContainer.setVisibility(View.GONE);
                        fragmentHomeBinding.subTitleTxt.setVisibility(View.VISIBLE);
                        fragmentHomeBinding.titleTxt.setVisibility(View.VISIBLE);
                        fragmentHomeBinding.knowMore.setVisibility(View.VISIBLE);
                        FillDashboardData();

                        fragmentHomeBinding.upcomingRcyList.hideShimmerAdapter();
                        fragmentHomeBinding.armyStoryRcyList.hideShimmerAdapter();

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

    private Map<String, String> getDashboardData() {
        Map<String, String> map = new HashMap<>();
        map.put("AppUserId", Utils.getPref(mContext, "AppUserId"));
        return map;
    }

    public void FillDashboardData() {
        fragmentHomeBinding.titleTxt.setText(getDashboardDataModel.getCommonData().getPageHeaderText());
        fragmentHomeBinding.subTitleTxt.setText(getDashboardDataModel.getCommonData().getPageHeaderDesc());

        for (int i = 0; i < getDashboardDataModel.getUpcomming().size(); i++) {
            String data = getDashboardDataModel.getUpcomming().get(i).getCategoryTypes();
            if (!data.equalsIgnoreCase("")) {
                if (data.contains(",")) {
                    String[] splitStr = data.split(",");

                    if (splitStr.length == 3) {
                        getDashboardDataModel.getUpcomming().get(i).setStr1(splitStr[0]);
                        getDashboardDataModel.getUpcomming().get(i).setStr2(splitStr[1]);
                        getDashboardDataModel.getUpcomming().get(i).setStr3(splitStr[2]);
                    } else {
                        getDashboardDataModel.getUpcomming().get(i).setStr1(splitStr[0]);
                        getDashboardDataModel.getUpcomming().get(i).setStr2(splitStr[1]);
                    }


                } else {
                    getDashboardDataModel.getUpcomming().get(i).setStr1(data);
                    getDashboardDataModel.getUpcomming().get(i).setStr2("1");
                    getDashboardDataModel.getUpcomming().get(i).setStr3("1");
                }
            }
        }

        if (getDashboardDataModel.getUpcomming() != null) {

            upcommingDashboardModelList = getDashboardDataModel.getUpcomming();
            upcomingDashboardAdapter = new UpcomingDashboardAdapter(mContext, upcommingDashboardModelList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            fragmentHomeBinding.upcomingRcyList.setLayoutManager(mLayoutManager);
            fragmentHomeBinding.upcomingRcyList.setItemAnimator(new DefaultItemAnimator());
            fragmentHomeBinding.upcomingRcyList.setAdapter(upcomingDashboardAdapter);

        }
        if (getDashboardDataModel.getStories() != null) {
            storyDashboardDataList = getDashboardDataModel.getStories();
            bharatArmyStoriesAdapter = new BharatArmyStoriesAdapter(mContext, storyDashboardDataList, new image_click() {
                @Override
                public void image_more_click() {
                    String getCategoryData = String.valueOf(bharatArmyStoriesAdapter.getDatas());

                    String[] splitString = getCategoryData.split("\\|");

                    categoryIdStr = splitString[0];
                    categoryNameStr = splitString[1].substring(0, splitString[1].length() - 1);

                    Log.d("categoryIdSTr :", categoryIdStr + " categoryNameStr :" + categoryNameStr);

                    // slide-up animation
                    Animation slideUp = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_right);
                    fragmentHomeBinding.armyStoryRcyList.startAnimation(slideUp);

                    fragmentHomeBinding.armyStoryRcyList.setVisibility(View.GONE);
                    wheretocome = "home";
                    mListener.onStoryCategory(categoryIdStr, categoryNameStr, wheretocome);
                }
            });
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            fragmentHomeBinding.armyStoryRcyList.setLayoutManager(mLayoutManager);
            fragmentHomeBinding.armyStoryRcyList.setItemAnimator(new DefaultItemAnimator());
            fragmentHomeBinding.armyStoryRcyList.setAdapter(bharatArmyStoriesAdapter);
        }


        fragmentHomeBinding.mainPageDealsRcv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


    }

    // Api calling GetHomeBannerData
    public void callHomeBannerData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getHomeBanners(getHomeBannerData(), new retrofit.Callback<HomeTemplateModel>() {
            @Override
            public void success(HomeTemplateModel homeTemplateModel, Response response) {
                Utils.dismissDialog();
                if (homeTemplateModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (homeTemplateModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (homeTemplateModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (homeTemplateModel.getIsValid() == 1) {
                    if (homeTemplateModel.getData() != null) {
                        homeTemplateDetailModelList = homeTemplateModel.getData();
                        fragmentHomeBinding.shimmerViewContainerhome.stopShimmerAnimation();
                        fragmentHomeBinding.shimmerViewContainerhome.setVisibility(View.GONE);

                        fragmentHomeBinding.homebannerLayout.setVisibility(View.VISIBLE);
                        fillHomeBanner();

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

    private Map<String, String> getHomeBannerData() {
        Map<String, String> map = new HashMap<>();
        map.put("MemberId", "0");
        return map;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.know_more:
//                DisplayAboutUs();
                Intent webviewIntent = new Intent(mContext, MoreStoryActivity.class);
                webviewIntent.putExtra("Story Heading", "Ab Jeetega India");
                webviewIntent.putExtra("StroyUrl", "http://ajif.in/");
                webviewIntent.putExtra("whereTocome", "aboutus");
                webviewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(webviewIntent);
                break;
            case R.id.adv_img:
                Intent videoIntent = new Intent(mContext, VideoDetailActivity.class);
                videoIntent.putExtra("videoData", "https://s3.ap-south-1.amazonaws.com/balatestvideos/TestVideo1.mp4");
                videoIntent.putExtra("videoName", "TestVideo1.mp4");
                videoIntent.putExtra("WhereToVideoCome", "Home");
                videoIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(videoIntent);
                break;
        }
    }


    public void fillHomeBanner() {
        fragmentHomeBinding.mainPageDealsRcv.setAdapter(new MyBgpageAdapter(homeTemplateDetailModelList, mContext));

        fragmentHomeBinding.cardViewPager.setOffscreenPageLimit(3);
        fragmentHomeBinding.cardViewPager.setPageMargin(10);
        fragmentHomeBinding.cardViewPager.setPageTransformer(true, new AlphaPageTransformer());
        fragmentHomeBinding.cardViewPager.setAdapter(new MyPagerAdapter(homeTemplateDetailModelList, mContext));

        fragmentHomeBinding.cardViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int i1) {
                if (position == mCurrentSelectedScreen) {
                    // We are moving to next screen on right side
                    if (positionOffset > 0.5) {
                        // Closer to next screen than to current
                        if (position + 1 != mNextSelectedScreen) {
                            mNextSelectedScreen = position + 1;
                            fragmentHomeBinding.mainPageDealsRcv.setCurrentItem(mNextSelectedScreen, true);
                        }
                    } else {
                        // Closer to current screen than to next
                        if (position != mNextSelectedScreen) {
                            mNextSelectedScreen = position;
                            fragmentHomeBinding.mainPageDealsRcv.setCurrentItem(mNextSelectedScreen, true);
                        }
                    }
                } else {
                    // We are moving to next screen left side
                    if (positionOffset > 0.5) {
                        // Closer to current screen than to next
                        if (position + 1 != mNextSelectedScreen) {
                            mNextSelectedScreen = position + 1;
                            fragmentHomeBinding.mainPageDealsRcv.setCurrentItem(mNextSelectedScreen, true);
                        }
                    } else {
                        // Closer to next screen than to current
                        if (position != mNextSelectedScreen) {
                            mNextSelectedScreen = position;
                            fragmentHomeBinding.mainPageDealsRcv.setCurrentItem(mNextSelectedScreen, true);
                        }
                    }
                }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemClick) {
            mListener = (OnItemClick) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnItemClick {
        void onStoryCategory(String categoryId, String categoryName, String wheretocome);


    }
}
