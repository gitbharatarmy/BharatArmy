package com.bharatarmy.Fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bharatarmy.Adapter.BharatArmyStoriesAdapter;
import com.bharatarmy.Adapter.UpcomingDashboardAdapter;
import com.bharatarmy.Models.DashboardDataModel;
import com.bharatarmy.Models.DashboardModel;
import com.bharatarmy.Models.StoryDashboardData;
import com.bharatarmy.Models.UpcommingDashboardModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
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


        return rootView;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListiner();
        callDashboardData();
    }

    public void setListiner() {
        fragmentHomeBinding.subTitleTxt.setVisibility(View.GONE);
        fragmentHomeBinding.titleTxt.setVisibility(View.GONE);
        fragmentHomeBinding.knowMore.setVisibility(View.GONE);
        fragmentHomeBinding.shimmerViewContainer.startShimmerAnimation();
        fragmentHomeBinding.upcomingRcyList.showShimmerAdapter();
        fragmentHomeBinding.armyStoryRcyList.showShimmerAdapter();

        fragmentHomeBinding.knowMore.setOnClickListener(this);
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
            bharatArmyStoriesAdapter = new BharatArmyStoriesAdapter(mContext, storyDashboardDataList);
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
                DisplayAboutUs();
                break;
        }
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
