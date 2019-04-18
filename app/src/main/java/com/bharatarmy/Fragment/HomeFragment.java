package com.bharatarmy.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.ViewSwitcher;

import com.bharatarmy.Activity.MoreStoryActivity;
import com.bharatarmy.Activity.VideoDetailActivity;
import com.bharatarmy.Adapter.BharatArmyStoriesAdapter;
import com.bharatarmy.Adapter.UpcomingDashboardAdapter;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.DashboardDataModel;
import com.bharatarmy.Models.DashboardModel;
import com.bharatarmy.Models.StoryDashboardData;
import com.bharatarmy.Models.UpcommingDashboardModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;


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
    List<UpcommingDashboardModel> upcommingDashboardModelList;
    List<StoryDashboardData> storyDashboardDataList;
    AlertDialog alertDialogAndroid;
    TextView close_btn, aboutuse_sub_title_txt;
    Button closeBtn;
    VideoView videoView;
    ProgressBar progressbar;
    ArrayList<String> image;

    private TransitionDrawable mTransition;
    private int animationCounter = 1;
    private Handler imageSwitcherHandler;

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

        return rootView;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
        } else {
            callDashboardData();
        }
        setListiner();


    }

    public void setListiner() {
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
//        Glide.with(mContext)
//                .load(R.drawable.ad)
//                .placeholder(R.drawable.progress_animation)
//                .into(fragmentHomeBinding.advImg);


        for (int i = 0; i < getDashboardDataModel.getUpcomming().size(); i++) {
            String data = getDashboardDataModel.getUpcomming().get(i).getCategoryTypes();
            if (!data.equalsIgnoreCase("")) {
                if (data.contains(",")) {
                    String[] splitStr = data.split(",");
                    getDashboardDataModel.getUpcomming().get(i).setStr1(splitStr[0]);
                    getDashboardDataModel.getUpcomming().get(i).setStr2(splitStr[1]);
                    getDashboardDataModel.getUpcomming().get(i).setStr3(splitStr[2]);
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
            bharatArmyStoriesAdapter = new BharatArmyStoriesAdapter(mContext, storyDashboardDataList, new MorestoryClick() {
                @Override
                public void getmorestoryClick() {
                }
            });
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            fragmentHomeBinding.armyStoryRcyList.setLayoutManager(mLayoutManager);
            fragmentHomeBinding.armyStoryRcyList.setItemAnimator(new DefaultItemAnimator());
            fragmentHomeBinding.armyStoryRcyList.setAdapter(bharatArmyStoriesAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.know_more:
//                DisplayAboutUs();
                Intent webviewIntent = new Intent(mContext, MoreStoryActivity.class);
                webviewIntent.putExtra("Story Heading", "Ab Jeetega India");
                webviewIntent.putExtra("StroyUrl", "http://ajif.in/");
                webviewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(webviewIntent);
                break;
            case R.id.adv_img:
                Intent videoIntent = new Intent(mContext, VideoDetailActivity.class);
                videoIntent.putExtra("videoData","https://s3.ap-south-1.amazonaws.com/balatestvideos/TestVideo1.mp4");
                videoIntent.putExtra("videoName", "TestVideo1.mp4");
                videoIntent.putExtra("WhereToVideoCome","Home");
                videoIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(videoIntent);
//                DisplayAdvertise();
                break;
        }
    }

    public void DisplayAdvertise() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.advertise_list, null);
//
//        // Specify alert dialog is not cancelable/not ignorable
//        builder.setCancelable(false);
//
//        // Set the custom layout as alert dialog view
//        builder.setView(dialogView);
//
//        // Create the alert dialog
//        final AlertDialog dialog = builder.create();
//
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        // Display the custom alert dialog on interface
//        dialog.show();


        LayoutInflater lInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = lInflater.inflate(R.layout.advertise_list, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity());
        alertDialogBuilderUserInput.setView(layout);

        alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.setCancelable(false);
        alertDialogAndroid.show();
        Window window = alertDialogAndroid.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER_HORIZONTAL;
        wlp.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        window.setAttributes(wlp);
        alertDialogAndroid.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialogAndroid.show();

        close_btn = layout.findViewById(R.id.close_btn);
        videoView = layout.findViewById(R.id.play_video);
        progressbar=layout.findViewById(R.id.progressbar);
        videoView.setVideoPath("https://s3.ap-south-1.amazonaws.com/balatestvideos/TestVideo1.mp4");
        progressbar.setVisibility(View.VISIBLE);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.start();
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int arg1,
                                                   int arg2) {
                        // TODO Auto-generated method stub
                        progressbar.setVisibility(View.GONE);
                        // start a video
                        videoView.start();
                        mp.start();
                    }
                });
            }
        });

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogAndroid.dismiss();
            }
        });


    }

    public void DisplayAboutUs() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogSlideAnim);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.know_more_list, null);

        // Specify alert dialog is not cancelable/not ignorable
        builder.setCancelable(false);

        // Set the custom layout as alert dialog view
        builder.setView(dialogView);

        // Create the alert dialog
        final AlertDialog dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        // Display the custom alert dialog on interface
        dialog.show();

        close_btn = (TextView) dialog.findViewById(R.id.close_txt);
        aboutuse_sub_title_txt = (TextView) dialog.findViewById(R.id.aboutuse_sub_title_txt);

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        aboutuse_sub_title_txt.setText(getDashboardDataModel.getCommonData().getPageHeaderDesc());
    }
}
