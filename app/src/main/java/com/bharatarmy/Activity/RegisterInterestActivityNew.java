package com.bharatarmy.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bharatarmy.Adapter.RegisterInterestFilterAdapter;
import com.bharatarmy.Adapter.RegisterIntrestAdapter;
import com.bharatarmy.Fragment.RegisterInterestFilterFragment;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.HomeTemplateDetailModel;
import com.bharatarmy.Models.HomeTemplateModel;
import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityRegisterInterestNewBinding;
import com.bharatarmy.databinding.RegisterInterestPagerItemBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterInterestActivityNew extends AppCompatActivity implements View.OnClickListener {

    ActivityRegisterInterestNewBinding activityRegisterInterestBinding;
    Context mContext;
    String titleNameStr = "", tournamentIdStr = "", nooftestStr = "", noofodiStr = "", nooft20Str = "",
            memberIdStr = "", nameStr = "", emailStr = "", mobilenoStr = "", countrydialcodeStr = "", countrycodeStr = "", matchidStr = "";
    BottomSheetDialogFragment bottomSheetDialogFragment;
    int countT20, countODI, countTEST;

    List<HomeTemplateDetailModel> tournamentDetailModel;
    ArrayList<HomeTemplateDetailModel> tournamentfilterDetailModel, tournamentfiltervenuewithteamDetailModel;

    List<String> selectedmatchId;

    RegisterIntrestAdapter registerIntrestAdapter;
    RegisterInterestFilterAdapter registerInterestFilterAdapter;
    RegisterIntrestFilterDataModel registerIntrestFilterDataModel;


    /*viewpager control*/
    private MyRegisterInterestGalleryViewPagerAdapter myRegisterInterestGalleryViewPagerAdapter;
    ArrayList<TravelModel> registerInterestGalleryList;
    private TextView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterInterestBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_interest_new);

        mContext = RegisterInterestActivityNew.this;


        init();
        setListiner();

    }

    public void init() {
        setSupportActionBar(activityRegisterInterestBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        fillViewpager();

        tournamentIdStr = getIntent().getStringExtra("tournamentId");

        titleNameStr = "schedule";

        activityRegisterInterestBinding.shimmerViewContainer.startShimmerAnimation();
        callRegisterTournamentInterestDetailData();
    }

    public void fillViewpager() {
        //        hotel gallery List
        registerInterestGalleryList = new ArrayList<TravelModel>();
        registerInterestGalleryList.add(new TravelModel("https://www.bharatarmy.com/docs/t20_detailbanner_app.jpg", "Hotel1"));
//        registerInterestGalleryList.add(new TravelModel("https://www.bharatarmy.com/Docs/t20-mens-01.jpg", "Hotel1"));
//        registerInterestGalleryList.add(new TravelModel("https://www.bharatarmy.com/Docs/T20-Banner.jpg", "Hotel1"));
        addBottomDots(0);
        myRegisterInterestGalleryViewPagerAdapter = new MyRegisterInterestGalleryViewPagerAdapter();
        activityRegisterInterestBinding.registerGalleryViewpager.setAdapter(myRegisterInterestGalleryViewPagerAdapter);
        activityRegisterInterestBinding.registerGalleryViewpager.addOnPageChangeListener(viewPagerPageChangeListener);


    }

    @SuppressLint("ResourceAsColor")
    private void addBottomDots(int currentPage) {
        dots = new TextView[registerInterestGalleryList.size()];
        List<String> colorsActiveList = new ArrayList<>();
        List<String> colorsInactive = new ArrayList<>();
        for (int i = 0; i < registerInterestGalleryList.size(); i++) {
            colorsActiveList.add(String.valueOf(getResources().getColor(R.color.splash_bg_color)));
            colorsInactive.add(String.valueOf(getResources().getColor(R.color.black)));
        }


        activityRegisterInterestBinding.viewPagerDotlinear.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            activityRegisterInterestBinding.viewPagerDotlinear.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Integer.parseInt(colorsActiveList.get(currentPage)));//colorsActive[currentPage]
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

    public void setListiner() {

        activityRegisterInterestBinding.backImg.setOnClickListener(this);
        activityRegisterInterestBinding.fabLinear.setOnClickListener(this);
        activityRegisterInterestBinding.submitLinear.setOnClickListener(this);

        activityRegisterInterestBinding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
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
                    activityRegisterInterestBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.heading_bg));
                    activityRegisterInterestBinding.toolbarTitleTxt.setText(tournamentDetailModel.get(0).getTourName());
                    activityRegisterInterestBinding.collapsingToolbar.setTitle("");
                    Typeface typeface = ResourcesCompat.getFont(mContext, R.font.helveticaneueltstdbdcn);
                    activityRegisterInterestBinding.collapsingToolbar.setCollapsedTitleTypeface(typeface);
                    activityRegisterInterestBinding.collapsingToolbar.setExpandedTitleTypeface(typeface);
                    activityRegisterInterestBinding.collapsingToolbar.setCollapsedTitleGravity(Gravity.CENTER_VERTICAL);
                    activityRegisterInterestBinding.collapsingToolbar.setExpandedTitleGravity(Gravity.START);
                    ViewGroup.LayoutParams params = activityRegisterInterestBinding.toolbarBottomLeftView.getLayoutParams();
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    activityRegisterInterestBinding.toolbarBottomLeftView.setLayoutParams(params);
                    isShow = true;
                } else if (isShow) {
                    activityRegisterInterestBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                    activityRegisterInterestBinding.toolbarTitleTxt.setText("");
                    activityRegisterInterestBinding.collapsingToolbar.setTitle(" ");
                    //                    ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.85f);
                    //                    activityRegisterInterestBinding.toolbarBottomLeftView.setLayoutParams(params);
                    ViewGroup.LayoutParams params = activityRegisterInterestBinding.toolbarBottomLeftView.getLayoutParams();
                    params.width = (int) 0.85f;
                    activityRegisterInterestBinding.toolbarBottomLeftView.setLayoutParams(params);
                    isShow = false;
                }

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                RegisterInterestActivityNew.this.finish();
                break;
            case R.id.fab_linear:
                bottomSheetDialogFragment = new RegisterInterestFilterFragment(registerIntrestFilterDataModel, new MorestoryClick() {
                    @Override
                    public void getmorestoryClick() {
                        activityRegisterInterestBinding.registerinterestRcv.setVisibility(View.GONE);
                        activityRegisterInterestBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                        setDataOnFilter();

                    }
                });
                //show it
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                break;
            case R.id.submit_linear:
                Utils.handleClickEvent(mContext, activityRegisterInterestBinding.submitLinear);
                ArrayList<String> selectedtournamentId = new ArrayList<>();
                for (int i = 0; i < tournamentDetailModel.size(); i++) {
                    if (tournamentDetailModel.get(i).getCheck().equalsIgnoreCase("1")) {
                        selectedtournamentId.add(String.valueOf(tournamentDetailModel.get(i).getTournamentMatchId()));
                        Log.d("selectedtournamentId :", selectedtournamentId.toString());
                    }
                }
                if (selectedtournamentId.size() != 0) {
                    String tournamentIdStr;
                    tournamentIdStr = "";
                    for (String s : selectedtournamentId) {
                        tournamentIdStr = tournamentIdStr + "," + s;
                    }
                    Log.d("tournamentIdStr", tournamentIdStr);
                    tournamentIdStr = tournamentIdStr.substring(1, tournamentIdStr.length());
                    Log.d("finalstatusStr", tournamentIdStr);
                    matchidStr = tournamentIdStr;
                    if (Utils.isMember(mContext, "Dashboard")) {
                        memberIdStr = String.valueOf(Utils.getAppUserId(mContext));

                        if (Utils.retriveLoginData(mContext).getName() != null) {
                            nameStr = nameStr = Utils.retriveLoginData(mContext).getName();
                        }
                        if (Utils.retriveLoginData(mContext).getEmail() != null) {
                            emailStr = Utils.retriveLoginData(mContext).getEmail();
                        }
                        if (Utils.retriveLoginData(mContext).getPhoneNo() != null &&
                                !Utils.retriveLoginData(mContext).getPhoneNo().equalsIgnoreCase("")) {
                            mobilenoStr = Utils.retriveLoginData(mContext).getPhoneNo();
                        }
                        if (Utils.retriveLoginData(mContext).getCountryISOCode() != null) {
                            if (!Utils.retriveLoginData(mContext).getCountryISOCode().equalsIgnoreCase("")) {
                                AppConfiguration.currentCountryISOCode = Utils.retriveLoginData(mContext).getCountryISOCode();
                            } else {
                                AppConfiguration.currentCountryISOCode = Utils.retriveCurrentLocationData(mContext).getIsoCode();
                            }
                        }
                        countrycodeStr = AppConfiguration.currentCountryISOCode;
//                        countrydialcodeStr = Utils.retriveLoginData(mContext).();
                        countrydialcodeStr = "";

                        Log.d("save register data :", " memberId :" + memberIdStr + "namestr :" + nameStr + "email :" + emailStr +
                                "mobile :" + mobilenoStr + "countrycode :" + countrycodeStr + "countrydialcode " + countrydialcodeStr);

                        if (!memberIdStr.equalsIgnoreCase("") && !nameStr.equalsIgnoreCase("") &&
                                !emailStr.equalsIgnoreCase("") && !mobilenoStr.equalsIgnoreCase("") &&
                                !mobilenoStr.equalsIgnoreCase("") /*&& !countrydialcodeStr.equalsIgnoreCase("") */ &&
                                !countrycodeStr.equalsIgnoreCase("")) {
                            callSaveInterestDetailData();
                        } else {
                            Utils.ping(mContext, "blank filed not allowed");
                        }
                    }
                } else {
                    Utils.ping(mContext, "Please select atleast one match");
                }

                break;
        }

    }


    // Api calling GetTournamentInterestDetailData
    public void callRegisterTournamentInterestDetailData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), RegisterInterestActivityNew.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getTournamentFixtures(getTournamentInterestDetailData(), new retrofit.Callback<HomeTemplateModel>() {
            @Override
            public void success(HomeTemplateModel tournamentmodel, Response response) {
                Utils.dismissDialog();
                if (tournamentmodel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (tournamentmodel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (tournamentmodel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (tournamentmodel.getIsValid() == 1) {

                    if (tournamentmodel.getData() != null) {
                        tournamentDetailModel = tournamentmodel.getData();
                        activityRegisterInterestBinding.shimmerViewContainer.stopShimmerAnimation();
                        activityRegisterInterestBinding.shimmerViewContainer.setVisibility(View.GONE);
                        activityRegisterInterestBinding.registerinterestRcv.setVisibility(View.VISIBLE);
                        activityRegisterInterestBinding.fabLinear.setVisibility(View.VISIBLE);
                        activityRegisterInterestBinding.submitLinear.setVisibility(View.VISIBLE);
//                        activityRegisterInterestBinding.matchTypeLinear.setVisibility(View.VISIBLE);


                        if (tournamentmodel.getOtherData() != null) {
                            registerIntrestFilterDataModel = tournamentmodel.getOtherData();
                        }
                        settournamentscheduleListValue("freshview");
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

    private Map<String, String> getTournamentInterestDetailData() {
        Map<String, String> map = new HashMap<>();
        map.put("TournamentId", tournamentIdStr);
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));
        return map;
    }

    public void settournamentscheduleListValue(String freshview) {
        for (int i = 0; i < tournamentDetailModel.size(); i++) {
            if (tournamentDetailModel.get(i).getStrMatchType().equalsIgnoreCase("T20")) {
                countT20++;
            }
            if (tournamentDetailModel.get(i).getStrMatchType().equalsIgnoreCase("TEST")) {
                countTEST++;
            }
            if (tournamentDetailModel.get(i).getStrMatchType().equalsIgnoreCase("ODI")) {
                countODI++;
            }
        }

        nooftestStr = String.valueOf(countTEST);
        noofodiStr = String.valueOf(countODI);
        nooft20Str = String.valueOf(countT20);

        Log.d("T20 : ", nooft20Str + "ODI : " + noofodiStr + "TEST : " + nooftestStr);

        if (freshview.equalsIgnoreCase("freshview")) {
            for (int k = 0; k < tournamentDetailModel.size(); k++) {
                tournamentDetailModel.get(k).setCheck("0");
            }
        }


        registerIntrestAdapter = new RegisterIntrestAdapter(mContext, tournamentDetailModel, registerIntrestFilterDataModel,
                titleNameStr, nooft20Str, noofodiStr, nooftestStr, new MorestoryClick() {
            @Override
            public void getmorestoryClick() {

                selectedmatchId = new ArrayList<>();
                for (int i = 0; i < tournamentDetailModel.size(); i++) {
                    if (tournamentDetailModel.get(i).getCheck().equalsIgnoreCase("1")) {
                        selectedmatchId.add(String.valueOf(tournamentDetailModel.get(i).getTournamentMatchId()));
                    } else {
                        selectedmatchId.remove(String.valueOf(tournamentDetailModel.get(i).getTournamentMatchId()));
                    }
                }
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        activityRegisterInterestBinding.registerinterestRcv.setLayoutManager(mLayoutManager);
        activityRegisterInterestBinding.registerinterestRcv.setItemAnimator(new DefaultItemAnimator());
        activityRegisterInterestBinding.registerinterestRcv.setAdapter(registerIntrestAdapter);

    }


    public void setDataOnFilter() {
        ArrayList<String> selectedtournamentteamId = new ArrayList<>();
        for (int i = 0; i < registerIntrestFilterDataModel.getCountries().size(); i++) {
            if (registerIntrestFilterDataModel.getCountries().get(i).getTeamSelected().equalsIgnoreCase("1")) {
                selectedtournamentteamId.add(String.valueOf(registerIntrestFilterDataModel.getCountries().get(i).getCountryId()));
                Log.d("selectedteamId :", selectedtournamentteamId.toString());
            }
        }
        String tournamentIdStr;
        tournamentIdStr = "";
        if (selectedtournamentteamId.size() != 0) {
            for (String s : selectedtournamentteamId) {
                tournamentIdStr = tournamentIdStr + "," + s;
            }
            Log.d("tournamentIdStr", tournamentIdStr);
            tournamentIdStr = tournamentIdStr.substring(1, tournamentIdStr.length());
            Log.d("finalstatusStr", tournamentIdStr);
        }

        ArrayList<String> selectedtournamentVenuename = new ArrayList<>();
        for (int i = 0; i < registerIntrestFilterDataModel.getStadiums().size(); i++) {
            if (registerIntrestFilterDataModel.getStadiums().get(i).getVenueSelected().equalsIgnoreCase("1")) {
                selectedtournamentVenuename.add(registerIntrestFilterDataModel.getStadiums().get(i).getLabel());
                Log.d("selectedVenueName :", selectedtournamentVenuename.toString());
            }
        }

        tournamentfilterDetailModel = new ArrayList<>();
        if (selectedtournamentteamId.size() != 0) {
            for (int k = 0; k < selectedtournamentteamId.size(); k++) {
                for (int j = 0; j < tournamentDetailModel.size(); j++) {
                    if (selectedtournamentteamId.get(k).trim().equalsIgnoreCase(String.valueOf(tournamentDetailModel.get(j).getFromCountryId()).trim())
                            || selectedtournamentteamId.get(k).trim().equalsIgnoreCase(String.valueOf(tournamentDetailModel.get(j).getToCountryId()).trim())) {
                        tournamentfilterDetailModel.add(tournamentDetailModel.get(j));
                    }
                }
            }
        }
        tournamentfiltervenuewithteamDetailModel = new ArrayList<>();
        if (selectedtournamentVenuename.size() != 0) {
            for (int k = 0; k < selectedtournamentVenuename.size(); k++) {
                if (tournamentfilterDetailModel.size() != 0) {
                    for (int i = 0; i < tournamentfilterDetailModel.size(); i++) {
                        if (selectedtournamentVenuename.get(k).trim().equalsIgnoreCase(tournamentfilterDetailModel.get(i).getStadiumName().trim())) {
                            tournamentfiltervenuewithteamDetailModel.add(tournamentfilterDetailModel.get(i));
                        }
                    }
                } else {
                    for (int i = 0; i < tournamentDetailModel.size(); i++) {
                        if (selectedtournamentVenuename.get(k).trim().equalsIgnoreCase(tournamentDetailModel.get(i).getStadiumName().trim())) {
                            tournamentfiltervenuewithteamDetailModel.add(tournamentDetailModel.get(i));
                        }
                    }
                }
            }
        } else {
            if (tournamentfilterDetailModel.size() != 0) {
//                for (int k = 0; k < tournamentfilterDetailModel.size(); k++) {
//                    tournamentfilterDetailModel.get(k).setCheck("0");
//                }
                activityRegisterInterestBinding.registerinterestRcv.setVisibility(View.VISIBLE);
                activityRegisterInterestBinding.shimmerViewContainer.setVisibility(View.GONE);
                registerInterestFilterAdapter = new RegisterInterestFilterAdapter(mContext, tournamentfilterDetailModel, registerIntrestFilterDataModel,
                        titleNameStr, nooft20Str, noofodiStr, nooftestStr, new MorestoryClick() {
                    @Override
                    public void getmorestoryClick() {
                        selectedmatchId = new ArrayList<>();
                        for (int i = 0; i < tournamentDetailModel.size(); i++) {
                            if (tournamentDetailModel.get(i).getCheck().equalsIgnoreCase("1")) {
                                selectedmatchId.add(String.valueOf(tournamentDetailModel.get(i).getTournamentMatchId()));
                            } else {
                                selectedmatchId.remove(String.valueOf(tournamentDetailModel.get(i).getTournamentMatchId()));
                            }
                        }
//                        if (selectedmatchId.size() > 0) {
//                            activityRegisterInterestBinding.submitLinear.setVisibility(View.VISIBLE);
//                            activityRegisterInterestBinding.registerinterestRcv.setPadding(0,0,0,180);
//                        } else {
//                            activityRegisterInterestBinding.submitLinear.setVisibility(View.GONE);
//                            activityRegisterInterestBinding.registerinterestRcv.setPadding(0,0,0,0);
//                        }
                    }
                });
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
                activityRegisterInterestBinding.registerinterestRcv.setLayoutManager(mLayoutManager);
                activityRegisterInterestBinding.registerinterestRcv.setItemAnimator(new DefaultItemAnimator());
                activityRegisterInterestBinding.registerinterestRcv.setAdapter(registerInterestFilterAdapter);
                registerInterestFilterAdapter.notifyDataSetChanged();
            }
        }
        Log.d("filterteamData :", "" + tournamentfilterDetailModel.size());
        Log.d("filterteamvenueData:", "" + tournamentfiltervenuewithteamDetailModel.size());

        if (tournamentfiltervenuewithteamDetailModel.size() != 0) {
//            for (int k = 0; k < tournamentfiltervenuewithteamDetailModel.size(); k++) {
//                tournamentfiltervenuewithteamDetailModel.get(k).setCheck("0");
//            }
            activityRegisterInterestBinding.registerinterestRcv.setVisibility(View.VISIBLE);
            activityRegisterInterestBinding.shimmerViewContainer.setVisibility(View.GONE);
            registerInterestFilterAdapter = new RegisterInterestFilterAdapter(mContext, tournamentfiltervenuewithteamDetailModel, registerIntrestFilterDataModel,
                    titleNameStr, nooft20Str, noofodiStr, nooftestStr, new MorestoryClick() {
                @Override
                public void getmorestoryClick() {
                    selectedmatchId = new ArrayList<>();
                    for (int i = 0; i < tournamentDetailModel.size(); i++) {
                        if (tournamentDetailModel.get(i).getCheck().equalsIgnoreCase("1")) {
                            selectedmatchId.add(String.valueOf(tournamentDetailModel.get(i).getTournamentMatchId()));
                        } else {
                            selectedmatchId.remove(String.valueOf(tournamentDetailModel.get(i).getTournamentMatchId()));
                        }
                    }
//                    if (selectedmatchId.size() > 0) {
//                        activityRegisterInterestBinding.submitLinear.setVisibility(View.VISIBLE);
//                        activityRegisterInterestBinding.registerinterestRcv.setPadding(0,0,0,180);
//                    } else {
//                        activityRegisterInterestBinding.submitLinear.setVisibility(View.GONE);
//                        activityRegisterInterestBinding.registerinterestRcv.setPadding(0,0,0,0);
//                    }
                }
            });
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
            activityRegisterInterestBinding.registerinterestRcv.setLayoutManager(mLayoutManager);
            activityRegisterInterestBinding.registerinterestRcv.setItemAnimator(new DefaultItemAnimator());
            activityRegisterInterestBinding.registerinterestRcv.setAdapter(registerInterestFilterAdapter);
            registerInterestFilterAdapter.notifyDataSetChanged();

        }

        if (tournamentfilterDetailModel.size() == 0 && tournamentfiltervenuewithteamDetailModel.size() == 0) {
            activityRegisterInterestBinding.shimmerViewContainer.setVisibility(View.GONE);
            activityRegisterInterestBinding.registerinterestRcv.setVisibility(View.VISIBLE);
            settournamentscheduleListValue("not");
        }

    }

    // Api calling SaveInterestDetailData
    public void callSaveInterestDetailData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), RegisterInterestActivityNew.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getSaveRegisterInterest(getSaveInterestDetailData(), new retrofit.Callback<LogginModel>() {
            @Override
            public void success(LogginModel tournamentmodel, Response response) {
                Utils.dismissDialog();
                if (tournamentmodel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (tournamentmodel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (tournamentmodel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (tournamentmodel.getIsValid() == 1) {

                    if (tournamentmodel.getData() != null) {

                        Utils.showThanyouDialog(RegisterInterestActivityNew.this, "sports");

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

    private Map<String, String> getSaveInterestDetailData() {
        Map<String, String> map = new HashMap<>();
        map.put("TournamentId", tournamentIdStr);
        map.put("MemberId", memberIdStr);
        map.put("Name", nameStr);
        map.put("Email", emailStr);
        map.put("MobileNo", mobilenoStr);
        map.put("CountryDialcode", countrydialcodeStr);
        map.put("CountryCode", countrycodeStr);
        map.put("MatchIds", matchidStr);
        return map;
    }


    public class MyRegisterInterestGalleryViewPagerAdapter extends PagerAdapter {
//        private LayoutInflater layoutInflater;
//        ImageView hotel_gallery_image;

        public MyRegisterInterestGalleryViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup parent, int position) {
//            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            View view = layoutInflater.inflate(R.layout.register_interest_pager_item, container, false);
//
//            hotel_gallery_image = (ImageView) view.findViewById(R.id.hotel_gallery_image);

            RegisterInterestPagerItemBinding registerInterestPagerItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.register_interest_pager_item,parent,false);

            Utils.setImageInImageView(registerInterestGalleryList.get(position).getCityHotelAmenitiesImage(),registerInterestPagerItemBinding.registerInterestGalleryImage, mContext);

            Log.d("HotelGalleeryAdapter : ", registerInterestGalleryList.get(position).getCityHotelAmenitiesImage());
            parent.addView(registerInterestPagerItemBinding.getRoot());

            return registerInterestPagerItemBinding.getRoot();
        }

        @Override
        public int getCount() {
            return registerInterestGalleryList.size();
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

