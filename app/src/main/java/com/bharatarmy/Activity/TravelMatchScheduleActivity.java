package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bharatarmy.Adapter.TravelMatchHotelAdapter;
import com.bharatarmy.Adapter.TravelMatchScheduleAdapter;
import com.bharatarmy.Adapter.TravelMatchTeamNameFlagScheduleAdapter;
import com.bharatarmy.Appguide.ShowCaseBuilder;
import com.bharatarmy.Appguide.ShowCaseContentPosition;
import com.bharatarmy.Appguide.ShowCaseDialog;
import com.bharatarmy.Appguide.ShowCaseObject;
import com.bharatarmy.Appguide.ViewHelper;
import com.bharatarmy.Fragment.MatchScheduleAdvanceFragment;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.HomeTemplateDetailModel;
import com.bharatarmy.Models.HomeTemplateModel;
import com.bharatarmy.Models.InquiryStatusModel;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityTravelMatchScheduleBinding;
import com.bharatarmy.databinding.TravelScheduleBannerListItemBinding;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class TravelMatchScheduleActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTravelMatchScheduleBinding activityTravelMatchScheduleBinding;
    Context mContext;
    String toolbarTitleStr;

    /*Flag and Name List*/
    List<InquiryStatusModel> teamnameflagList;


    /*Schedule List*/
    ArrayList<HomeTemplateDetailModel> tournamentDetailModel;
    RegisterIntrestFilterDataModel tournamentotherDataModel;

    /*Filter List*/
    ArrayList<HomeTemplateDetailModel> tournamentfilterDetailModel;
    ArrayList<HomeTemplateDetailModel> tournamentadvancefilterteamDetailModel;
    ArrayList<HomeTemplateDetailModel> tournamentfiltervenuewithteamDetailModel;
    ArrayList<HomeTemplateDetailModel> tournamentfilterteamwithcityDetailModel;
    ArrayList<String> selectedtournamentteamId ;
    ArrayList<String> selectedtournamentVenuename;
    ArrayList<String> selectedtournamentCityname;
    String checkornotStr;
    int selectedposition = -1;

    /*viewpager control*/
    private MyTravelMatchScheduleGalleryViewPagerAdapter myTravelMatchScheduleGalleryViewPagerAdapter;
    ArrayList<TravelModel> travelmatchscheduleGalleryList;
    private ImageView[] dots;

    /*Adapter List*/
    TravelMatchTeamNameFlagScheduleAdapter teamNameFlagScheduleAdapter;
    TravelMatchScheduleAdapter travelMatchScheduleAdapter;
    LinearLayoutManager teamflagmLayoutManager, schedulemLayoutManager;

    /*Advance Filter*/
    BottomSheetDialogFragment bottomSheetDialogFragment;

    /*App Guider*/
    private ShowCaseDialog showCaseDialog;
    public static final String SHOWCASE_TAG = "sample_showcase_tag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelMatchScheduleBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_match_schedule);
        mContext = TravelMatchScheduleActivity.this;


        init();
        setListiner();
    }


    public void init() {

        setSupportActionBar(activityTravelMatchScheduleBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        if (getIntent().getStringExtra("tourtitle") != null) {
            toolbarTitleStr = getIntent().getStringExtra("tourtitle");
        }
        activityTravelMatchScheduleBinding.shimmerViewContainerTeamflagInfo.startShimmerAnimation();
        activityTravelMatchScheduleBinding.shimmerViewContainer.startShimmerAnimation();


        //        travel match schedule gallery List
        travelmatchscheduleGalleryList = new ArrayList<TravelModel>();
        travelmatchscheduleGalleryList.add(new TravelModel("https://www.bharatarmy.com/Docs/Mobile/25cf4087-b.jpg", "Hotel1"));
        travelmatchscheduleGalleryList.add(new TravelModel("https://www.bharatarmy.com/Docs/banner_app_02.jpg", "Hotel2"));
        travelmatchscheduleGalleryList.add(new TravelModel("https://www.bharatarmy.com/Docs/Mobile/25cf4087-b.jpg", "Hotel1"));
        travelmatchscheduleGalleryList.add(new TravelModel("https://www.bharatarmy.com/Docs/banner_app_02.jpg", "Hotel2"));

        addBottomDots(0);
        myTravelMatchScheduleGalleryViewPagerAdapter = new MyTravelMatchScheduleGalleryViewPagerAdapter();
        activityTravelMatchScheduleBinding.travelMatchScheduleGalleryViewpager.setAdapter(myTravelMatchScheduleGalleryViewPagerAdapter);
        activityTravelMatchScheduleBinding.travelMatchScheduleGalleryViewpager.addOnPageChangeListener(viewPagerPageChangeListener);


        callTravelMatchScheduleDetailData();
    }

    @SuppressLint("ResourceAsColor")
    private void addBottomDots(int currentPage) {
        dots = new ImageView[travelmatchscheduleGalleryList.size()];
        List<String> colorsActiveList = new ArrayList<>();
        List<String> colorsInactive = new ArrayList<>();
        for (int i = 0; i < travelmatchscheduleGalleryList.size(); i++) {
            colorsActiveList.add(String.valueOf(getResources().getColor(R.color.splash_bg_color)));
            colorsInactive.add(String.valueOf(getResources().getColor(R.color.gray)));
        }

        activityTravelMatchScheduleBinding.viewPagerDotlinear.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);

            dots[i].setImageResource(R.drawable.unselected_new);
            dots[i].setPadding(0, 0, 10, 0);
            activityTravelMatchScheduleBinding.viewPagerDotlinear.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setImageResource(R.drawable.selected_new);
//            dots[currentPage].setTextColor(Integer.parseInt(colorsActiveList.get(currentPage)));//colorsActive[currentPage]
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

        activityTravelMatchScheduleBinding.backImg.setOnClickListener(this);
        activityTravelMatchScheduleBinding.fabLinear.setOnClickListener(this);
        activityTravelMatchScheduleBinding.alertLinear.setOnClickListener(this);

        activityTravelMatchScheduleBinding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
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
                    activityTravelMatchScheduleBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.heading_bg));
                    activityTravelMatchScheduleBinding.toolbarTitleTxt.setText(toolbarTitleStr);
                    activityTravelMatchScheduleBinding.toolbarSubtitleTxt.setVisibility(View.VISIBLE);
                    activityTravelMatchScheduleBinding.toolbarTimedateLinear.setVisibility(View.VISIBLE);
                    activityTravelMatchScheduleBinding.collapsingToolbar.setTitle("");
                    Typeface typeface = ResourcesCompat.getFont(mContext, R.font.helveticaneueltstdbdcn);
                    activityTravelMatchScheduleBinding.collapsingToolbar.setCollapsedTitleTypeface(typeface);
                    activityTravelMatchScheduleBinding.collapsingToolbar.setExpandedTitleTypeface(typeface);
                    activityTravelMatchScheduleBinding.collapsingToolbar.setCollapsedTitleGravity(Gravity.CENTER_VERTICAL);
                    activityTravelMatchScheduleBinding.collapsingToolbar.setExpandedTitleGravity(Gravity.START);
                    activityTravelMatchScheduleBinding.toolbarBottomView.setVisibility(View.VISIBLE);
                    activityTravelMatchScheduleBinding.alertRel.setVisibility(View.GONE);
                    isShow = true;
                } else if (isShow) {
                    activityTravelMatchScheduleBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                    activityTravelMatchScheduleBinding.toolbarSubtitleTxt.setVisibility(View.GONE);
                    activityTravelMatchScheduleBinding.toolbarTimedateLinear.setVisibility(View.GONE);
                    activityTravelMatchScheduleBinding.toolbarTitleTxt.setText("");
                    activityTravelMatchScheduleBinding.collapsingToolbar.setTitle("");
                    activityTravelMatchScheduleBinding.alertRel.setVisibility(View.GONE);
                    activityTravelMatchScheduleBinding.toolbarBottomView.setVisibility(View.GONE);
                    isShow = false;
                }

            }
        });
    }

    public void setDataInList() {


        /*fill country flag with name list*/
        teamnameflagList = tournamentotherDataModel.getCountries();
        for (int i = 0; i < teamnameflagList.size(); i++) {
            if (teamnameflagList.get(i).getTeamSelected() != null) {
                if (teamnameflagList.get(i).getTeamSelected().equalsIgnoreCase("1")) {
                    teamnameflagList.get(i).setTeamSelected("1");
                } else {
                    teamnameflagList.get(i).setTeamSelected("0");
                }
            } else {
                teamnameflagList.get(i).setTeamSelected("0");
            }

        }

        teamNameFlagScheduleAdapter = new TravelMatchTeamNameFlagScheduleAdapter(mContext, selectedposition, teamnameflagList, new MorestoryClick() {
            @Override
            public void getmorestoryClick() {
                checkornotStr = teamNameFlagScheduleAdapter.checkornot();

                activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setVisibility(View.GONE);
                activityTravelMatchScheduleBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                activityTravelMatchScheduleBinding.shimmerViewContainer.startShimmerAnimation();

                setDataFilter();
            }
        });
        teamflagmLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        activityTravelMatchScheduleBinding.teamnameTeamflagRcv.setLayoutManager(teamflagmLayoutManager);
        activityTravelMatchScheduleBinding.teamnameTeamflagRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchScheduleBinding.teamnameTeamflagRcv.setAdapter(teamNameFlagScheduleAdapter);


        /*fill match schedule list*/
        travelMatchScheduleAdapter = new TravelMatchScheduleAdapter(mContext, tournamentDetailModel);
        schedulemLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setLayoutManager(schedulemLayoutManager);
        activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setAdapter(travelMatchScheduleAdapter);

        /*Guide the user for filter*/
//        setappguide();
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (teamNameFlagScheduleAdapter != null && travelMatchScheduleAdapter != null) {
////                    activityTravelMatchScheduleBinding.alertLinear.performClick();
//
////                    disableScroll();
////                    setTooltip();
//                }
//            }
//        }, 400);

    }

    public void setDataFilter() {
        ArrayList<String> selectedtournamentteamId = new ArrayList<>();
        for (int i = 0; i < tournamentotherDataModel.getCountries().size(); i++) {
            if (tournamentotherDataModel.getCountries().get(i).getTeamSelected().equalsIgnoreCase("1")) {
                selectedtournamentteamId.add(String.valueOf(tournamentotherDataModel.getCountries().get(i).getCountryId()));
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

        Log.d("tournamentfilter :", "" + tournamentfilterDetailModel.size());
        if (tournamentfilterDetailModel.size() != 0) {
            activityTravelMatchScheduleBinding.noRecordrel.setVisibility(View.GONE);
            activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setVisibility(View.VISIBLE);
            activityTravelMatchScheduleBinding.shimmerViewContainer.stopShimmerAnimation();
            activityTravelMatchScheduleBinding.shimmerViewContainer.setVisibility(View.GONE);
            travelMatchScheduleAdapter = new TravelMatchScheduleAdapter(mContext, tournamentfilterDetailModel);
            RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setLayoutManager(linearLayoutManager);
            activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setItemAnimator(new DefaultItemAnimator());
            activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setAdapter(travelMatchScheduleAdapter);
        } else {
            Log.d("checkornot :", checkornotStr);
            if (checkornotStr.equalsIgnoreCase("selected")) {
                activityTravelMatchScheduleBinding.shimmerViewContainer.stopShimmerAnimation();
                activityTravelMatchScheduleBinding.shimmerViewContainer.setVisibility(View.GONE);
                activityTravelMatchScheduleBinding.noRecordrel.setVisibility(View.VISIBLE);
            } else if (checkornotStr.equalsIgnoreCase("Unselected")) {
                activityTravelMatchScheduleBinding.noRecordrel.setVisibility(View.GONE);
                activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setVisibility(View.VISIBLE);
                activityTravelMatchScheduleBinding.shimmerViewContainer.stopShimmerAnimation();
                activityTravelMatchScheduleBinding.shimmerViewContainer.setVisibility(View.GONE);
                travelMatchScheduleAdapter = new TravelMatchScheduleAdapter(mContext, tournamentDetailModel);
                RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setLayoutManager(linearLayoutManager);
                activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setItemAnimator(new DefaultItemAnimator());
                activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setAdapter(travelMatchScheduleAdapter);
            }

        }
    }



    
    
    
    public void setAdvanceFilter() {
        /*tournament team filter*/
         selectedtournamentteamId = new ArrayList<>();
        for (int i = 0; i < tournamentotherDataModel.getCountries().size(); i++) {
            if (tournamentotherDataModel.getCountries().get(i).getTeamSelected().equalsIgnoreCase("1")) {
                selectedtournamentteamId.add(String.valueOf(tournamentotherDataModel.getCountries().get(i).getCountryId()));
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

        /*tournament venue filter*/
         selectedtournamentVenuename = new ArrayList<>();
        for (int i = 0; i < tournamentotherDataModel.getStadiums().size(); i++) {
            if (tournamentotherDataModel.getStadiums().get(i).getVenueSelected().equalsIgnoreCase("1")) {
                selectedtournamentVenuename.add(tournamentotherDataModel.getStadiums().get(i).getLabel());
                Log.d("selectedVenueName :", selectedtournamentVenuename.toString());
            }
        }
//        /*tournament cities filter*/
//        selectedtournamentCityname = new ArrayList<>();
//        for (int i = 0; i < tournamnetcitylist.size(); i++) {
//            if (tournamnetcitylist.get(i).getCityHotelAmenitiesName().equalsIgnoreCase("1")) {
//                selectedtournamentCityname.add(tournamnetcitylist.get(i).getCityHotelAmenitiesImage());
//                Log.d("selectedCityName :", selectedtournamentCityname.toString());
//            }
//        }


        tournamentadvancefilterteamDetailModel = new ArrayList<>();
        tournamentfiltervenuewithteamDetailModel = new ArrayList<>();
        tournamentfilterteamwithcityDetailModel =new ArrayList<>();
        
        if (selectedtournamentteamId.size() != 0) {
            if (tournamentfilterDetailModel != null) {
                if (tournamentfilterDetailModel.size() != 0) {
                    for (int k = 0; k < selectedtournamentteamId.size(); k++) {
                        for (int j = 0; j < tournamentfilterDetailModel.size(); j++) {
                            if (selectedtournamentteamId.get(k).trim().equalsIgnoreCase(String.valueOf(tournamentfilterDetailModel.get(j).getFromCountryId()).trim())
                                    || selectedtournamentteamId.get(k).trim().equalsIgnoreCase(String.valueOf(tournamentfilterDetailModel.get(j).getToCountryId()).trim())) {
                                tournamentadvancefilterteamDetailModel.add(tournamentfilterDetailModel.get(j));
                            }
                        }
                    }
                } else {
                    for (int k = 0; k < selectedtournamentteamId.size(); k++) {
                        for (int j = 0; j < tournamentDetailModel.size(); j++) {
                            if (selectedtournamentteamId.get(k).trim().equalsIgnoreCase(String.valueOf(tournamentDetailModel.get(j).getFromCountryId()).trim())
                                    || selectedtournamentteamId.get(k).trim().equalsIgnoreCase(String.valueOf(tournamentDetailModel.get(j).getToCountryId()).trim())) {
                                tournamentadvancefilterteamDetailModel.add(tournamentDetailModel.get(j));
                            }
                        }
                    }
                }
            } else {
                for (int k = 0; k < selectedtournamentteamId.size(); k++) {
                    for (int j = 0; j < tournamentDetailModel.size(); j++) {
                        if (selectedtournamentteamId.get(k).trim().equalsIgnoreCase(String.valueOf(tournamentDetailModel.get(j).getFromCountryId()).trim())
                                || selectedtournamentteamId.get(k).trim().equalsIgnoreCase(String.valueOf(tournamentDetailModel.get(j).getToCountryId()).trim())) {
                            tournamentadvancefilterteamDetailModel.add(tournamentDetailModel.get(j));
                        }
                    }
                }
            }


        }
     
        if (selectedtournamentVenuename.size() != 0) {
            for (int k = 0; k < selectedtournamentVenuename.size(); k++) {
                if (tournamentadvancefilterteamDetailModel.size() != 0) {
                    for (int i = 0; i < tournamentadvancefilterteamDetailModel.size(); i++) {
                        if (selectedtournamentVenuename.get(k).trim().equalsIgnoreCase(tournamentadvancefilterteamDetailModel.get(i).getStadiumName().trim())) {
                            tournamentfiltervenuewithteamDetailModel.add(tournamentadvancefilterteamDetailModel.get(i));
                        }
                    }
                } else {
                    if (tournamentfilterDetailModel != null) {
                        if (tournamentfilterDetailModel.size() == 0) {
                            for (int i = 0; i < tournamentDetailModel.size(); i++) {
                                if (selectedtournamentVenuename.get(k).trim().equalsIgnoreCase(tournamentDetailModel.get(i).getStadiumName().trim())) {
                                    tournamentfiltervenuewithteamDetailModel.add(tournamentDetailModel.get(i));
                                }
                            }
                        } else {
                            for (int i = 0; i < tournamentfilterDetailModel.size(); i++) {
                                if (selectedtournamentVenuename.get(k).trim().equalsIgnoreCase(tournamentfilterDetailModel.get(i).getStadiumName().trim())) {
                                    tournamentfiltervenuewithteamDetailModel.add(tournamentfilterDetailModel.get(i));
                                }
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
            }
        } else {
            if (tournamentadvancefilterteamDetailModel.size() != 0) {
                activityTravelMatchScheduleBinding.noRecordrel.setVisibility(View.GONE);
                activityTravelMatchScheduleBinding.shimmerViewContainer.setVisibility(View.GONE);
                activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setVisibility(View.VISIBLE);
                travelMatchScheduleAdapter = new TravelMatchScheduleAdapter(mContext, tournamentadvancefilterteamDetailModel);
                RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setLayoutManager(linearLayoutManager);
                activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setItemAnimator(new DefaultItemAnimator());
                activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setAdapter(travelMatchScheduleAdapter);

             updateteamflagAdapter();

            }
        }
        Log.d("filterteamData :", "" + tournamentadvancefilterteamDetailModel.size());
        Log.d("filterteamvenueData:", "" + tournamentfiltervenuewithteamDetailModel.size());
        Log.d("filterteamcitiesData:",""+ tournamentfilterteamwithcityDetailModel.size());
        if (selectedtournamentVenuename.size() != 0) {
            if (tournamentfiltervenuewithteamDetailModel.size() != 0) {
                activityTravelMatchScheduleBinding.noRecordrel.setVisibility(View.GONE);
                activityTravelMatchScheduleBinding.shimmerViewContainer.setVisibility(View.GONE);
                activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setVisibility(View.VISIBLE);
                travelMatchScheduleAdapter = new TravelMatchScheduleAdapter(mContext, tournamentfiltervenuewithteamDetailModel);
                RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setLayoutManager(linearLayoutManager);
                activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setItemAnimator(new DefaultItemAnimator());
                activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setAdapter(travelMatchScheduleAdapter);
                updateteamflagAdapter();
            } else {
                activityTravelMatchScheduleBinding.noRecordrel.setVisibility(View.VISIBLE);
                activityTravelMatchScheduleBinding.shimmerViewContainer.setVisibility(View.GONE);
                activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setVisibility(View.GONE);
                updateteamflagAdapter();
            }

        }


        if (tournamentadvancefilterteamDetailModel.size() == 0 && tournamentfiltervenuewithteamDetailModel.size() == 0) {
            if (selectedtournamentteamId.size() == 0 && selectedtournamentVenuename.size() == 0) {
                activityTravelMatchScheduleBinding.noRecordrel.setVisibility(View.GONE);
                activityTravelMatchScheduleBinding.shimmerViewContainer.setVisibility(View.GONE);
                activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setVisibility(View.VISIBLE);
                travelMatchScheduleAdapter = new TravelMatchScheduleAdapter(mContext, tournamentDetailModel);
                RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setLayoutManager(linearLayoutManager);
                activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setItemAnimator(new DefaultItemAnimator());
                activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setAdapter(travelMatchScheduleAdapter);
                updateteamflagAdapter();
            } else {
                activityTravelMatchScheduleBinding.shimmerViewContainer.setVisibility(View.GONE);
                activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setVisibility(View.GONE);
                activityTravelMatchScheduleBinding.noRecordrel.setVisibility(View.VISIBLE);
                updateteamflagAdapter();
            }


        }
    }

    public void setTooltip() {
        final ArrayList<ShowCaseObject> showCaseList = new ArrayList<>();

        int completelyVisiblePosition = teamflagmLayoutManager.findFirstCompletelyVisibleItemPosition();
        View itemView = teamflagmLayoutManager.findViewByPosition(completelyVisiblePosition);

        if (itemView != null) {
            // use background white
            showCaseList.add(new ShowCaseObject(
                    itemView,
                    "Normal Filter",
                    "Filter schedule according team",
                    ShowCaseContentPosition.UNDEFINED,
                    Color.WHITE));
        }


        int completelyVisiblePosition1 = schedulemLayoutManager.findFirstCompletelyVisibleItemPosition();
        View itemView1 = schedulemLayoutManager.findViewByPosition(completelyVisiblePosition1);

        if (itemView1 != null) {
            // use background white
            showCaseList.add(new ShowCaseObject(
                    itemView1,
                    "Match Detail",
                    "When you show match detail then click on item.",
                    ShowCaseContentPosition.UNDEFINED,
                    Color.WHITE));

//                    showCaseList.add(new ShowCaseObject(
//                            itemView1.findViewById(R.id.match_image),
//                            null,
//                            "Match Image"));
//
//                    showCaseList.add(new ShowCaseObject(
//                            itemView1.findViewById(R.id.match_date_time_txt),
//                            "null",
//                            "Match Date and Time",
//                            ShowCaseContentPosition.UNDEFINED,
//                            Color.WHITE));

        }

        int[] location = new int[2];
        activityTravelMatchScheduleBinding.fabLinear.getLocationInWindow(location);

        int xStart = location[0];
        int yStart = location[1] - ViewHelper.getStatusBarHeight(this);
        int xEnd = location[0] + activityTravelMatchScheduleBinding.fabLinear.getWidth();
        int yEnd = location[1] + activityTravelMatchScheduleBinding.fabLinear.getHeight() - ViewHelper.getStatusBarHeight(this);
        int xCenter = (xStart + xEnd) / 2;
        int yCenter = (yStart + yEnd) / 2;
        int radius = activityTravelMatchScheduleBinding.fabLinear.getWidth() * 2 / 3;

        showCaseList.add(
                new ShowCaseObject(
                        findViewById(android.R.id.content),
                        "This is advance filter",
                        "You can filter schedule according team and stadium.",
                        ShowCaseContentPosition.UNDEFINED,
                        Color.WHITE)
                        .withCustomTarget(new int[]{xCenter, yCenter}
                                , radius));

//        showCaseList.add(
//                new ShowCaseObject(
//                        findViewById(android.R.id.content),
//                        "Show case using rectangle custom target",
//                        "This is highlighted using custom target",
//                        ShowCaseContentPosition.UNDEFINED,
//                        Color.WHITE)
//                        .withCustomTarget(new int[]{ xStart - 20, yStart - 20, xEnd + 20, yEnd + 20}) );

        // make the dialog show
        showCaseDialog.show(TravelMatchScheduleActivity.this, SHOWCASE_TAG, showCaseList);
    }

    public void setappguide() {
        showCaseDialog = new ShowCaseBuilder()
                .setPackageName(getPackageName())
                .titleTextColorRes(android.R.color.white)
                .textColorRes(android.R.color.white)
                .shadowColorRes(R.color.shadow)
                .titleTextSizeRes(R.dimen.text_title)
                .textSizeRes(R.dimen.text_normal)
                .spacingRes(R.dimen.spacing_normal)
                .backgroundContentColorRes(R.color.blue)
                .circleIndicatorBackgroundDrawableRes(R.drawable.showcaseview_indicator)
                .prevStringRes(R.string.previous)
                .nextStringRes(R.string.next)
                .finishStringRes(R.string.finish)
                .useCircleIndicator(true)
                .clickable(true)
                .useArrow(true)
                .useSkipWord(true)
                .build();

        showCaseDialog.setShowCaseStepListener(new ShowCaseDialog.OnShowCaseStepListener() {
            @Override
            public boolean onShowCaseGoTo(int previousStep, int nextStep, ShowCaseObject showCaseObject) {
                Log.d("ToolTipNext :", "" + nextStep + "ToolTipPrevious :" + "" + previousStep);
                if (nextStep == 0 && previousStep == 1) {
                    enableScroll();
                } else if (nextStep == 2 && previousStep == 1) {
                    enableScroll();
                } else if (nextStep == 0 && previousStep == -1) {
                    enableScroll();
                }

                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.fab_linear:
                Utils.handleClickEvent(mContext,activityTravelMatchScheduleBinding.fabLinear);
                bottomSheetDialogFragment = new MatchScheduleAdvanceFragment(tournamentotherDataModel, new MorestoryClick() {
                    @Override
                    public void getmorestoryClick() {
                        activityTravelMatchScheduleBinding.noRecordrel.setVisibility(View.GONE);
                        activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setVisibility(View.GONE);
                        activityTravelMatchScheduleBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                        activityTravelMatchScheduleBinding.shimmerViewContainer.startShimmerAnimation();
                        setAdvanceFilter();

                    }
                });
                //show it
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                break;

            case R.id.alert_linear:

                break;
        }
    }


    public class MyTravelMatchScheduleGalleryViewPagerAdapter extends PagerAdapter {
        public MyTravelMatchScheduleGalleryViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup parent, int position) {
            TravelScheduleBannerListItemBinding travelScheduleBannerListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.travel_schedule_banner_list_item, parent, false);


            Utils.setImageInImageView(travelmatchscheduleGalleryList.get(position).getCityHotelAmenitiesImage(), travelScheduleBannerListItemBinding.backgroundImage, mContext);

            parent.addView(travelScheduleBannerListItemBinding.getRoot());

            return travelScheduleBannerListItemBinding.getRoot();
        }

        @Override
        public int getCount() {
            return travelmatchscheduleGalleryList.size();
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


    // Api calling GetTravelMatchScheduleDetailData
    public void callTravelMatchScheduleDetailData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), TravelMatchScheduleActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getTournamentFixtures(getTravelMatchScheduleDetailData(), new retrofit.Callback<HomeTemplateModel>() {
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
//                        tournamentDetailModel = tournamentmodel.getData();
                        tournamentDetailModel = new ArrayList<>();
                        for (int i = 0; i < tournamentmodel.getData().size(); i++) {
                            if (tournamentmodel.getData().get(i).getMatchNo() > 12) {
                                tournamentDetailModel.add(tournamentmodel.getData().get(i));
                            }
                        }

                        activityTravelMatchScheduleBinding.shimmerViewContainer.stopShimmerAnimation();
                        activityTravelMatchScheduleBinding.shimmerViewContainer.setVisibility(View.GONE);
                        activityTravelMatchScheduleBinding.shimmerViewContainerTeamflagInfo.stopShimmerAnimation();
                        activityTravelMatchScheduleBinding.shimmerViewContainerTeamflagInfo.setVisibility(View.GONE);
                        activityTravelMatchScheduleBinding.travelMatchScheduleRcv.setVisibility(View.VISIBLE);
                        activityTravelMatchScheduleBinding.teamnameTeamflagRcv.setVisibility(View.VISIBLE);
                        activityTravelMatchScheduleBinding.fabLinear.setVisibility(View.VISIBLE);

                        if (tournamentmodel.getOtherData() != null) {
                            tournamentotherDataModel = tournamentmodel.getOtherData();
                        }
                        setDataInList();
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

    private Map<String, String> getTravelMatchScheduleDetailData() {
        Map<String, String> map = new HashMap<>();
        map.put("TournamentId", "11");
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));
        return map;
    }


    public void enableScroll() {
        final AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams)
                activityTravelMatchScheduleBinding.collapsingToolbar.getLayoutParams();
        params.setScrollFlags(
                AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                        | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
        );
        activityTravelMatchScheduleBinding.collapsingToolbar.setLayoutParams(params);
    }

    public void disableScroll() {
        final AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams)
                activityTravelMatchScheduleBinding.collapsingToolbar.getLayoutParams();
        params.setScrollFlags(0);
        activityTravelMatchScheduleBinding.collapsingToolbar.setLayoutParams(params);
    }

    public void updateteamflagAdapter(){
        if (selectedtournamentteamId != null && selectedtournamentteamId.size() != 0) {
            for (int i = 0; i < teamnameflagList.size(); i++) {
                for (int j = 0; j < selectedtournamentteamId.size(); j++) {
                    if (String.valueOf(teamnameflagList.get(i).getCountryId()).equalsIgnoreCase(selectedtournamentteamId.get(j))) {
                        teamnameflagList.get(i).setTeamSelected("1");
                    }
                }

            }
            teamNameFlagScheduleAdapter.notifyDataSetChanged();
        }else{
            for (int i = 0; i < teamnameflagList.size(); i++) {

                teamnameflagList.get(i).setTeamSelected("0");

            }
            teamNameFlagScheduleAdapter.notifyDataSetChanged();
        }
    }
}
