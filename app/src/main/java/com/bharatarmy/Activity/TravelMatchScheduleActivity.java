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
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.bharatarmy.Utility.MyApplication;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityTravelMatchScheduleBinding;
import com.bharatarmy.databinding.TravelScheduleBannerListItemBinding;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.ads.AdsLoader;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.util.ErrorMessageProvider;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.Formatter;
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
    ArrayList<String> selectedtournamentteamId;
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

    /*video play in slider variable*/
    int videoplayposition = 0;
    String videopathStr;

    // Saved instance state keys.
    private static final String KEY_TRACK_SELECTOR_PARAMETERS = "track_selector_parameters";
    private static final String KEY_WINDOW = "window";
    private static final String KEY_POSITION = "position";
    private static final String KEY_AUTO_PLAY = "auto_play";

    private static final CookieManager DEFAULT_COOKIE_MANAGER;

    static {
        DEFAULT_COOKIE_MANAGER = new CookieManager();
        DEFAULT_COOKIE_MANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    int tapCount = 1;
    ProgressBar progressBar;
    private PlayerView playerView;
    private DataSource.Factory dataSourceFactory;
    private SimpleExoPlayer player;
    private FrameworkMediaDrm mediaDrm;
    private MediaSource mediaSource;
    private DefaultTrackSelector trackSelector;
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    private TrackGroupArray lastSeenTrackGroupArray;
    private TextView tvPlaybackSpeed, tvPlaybackSpeedSymbol;
    private boolean startAutoPlay;
    private int startWindow;

    // Fields used only for ad playback. The ads loader is loaded via reflection.
    private long startPosition;
    private AdsLoader adsLoader;
    private Uri loadedAdTagUri;
    //    FrameLayout frameLayout;
    private ImageView exoPlay;
    //    private ImageView exoPause;
    private Handler handler;
    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;


    // Activity lifecycle
    private static boolean isBehindLiveWindow(ExoPlaybackException e) {
        if (e.type != ExoPlaybackException.TYPE_SOURCE) {
            return false;
        }
        Throwable cause = e.getSourceException();
        while (cause != null) {
            if (cause instanceof BehindLiveWindowException) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataSourceFactory = buildDataSourceFactory();
        if (CookieHandler.getDefault() != DEFAULT_COOKIE_MANAGER) {
            CookieHandler.setDefault(DEFAULT_COOKIE_MANAGER);
        }

        activityTravelMatchScheduleBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_match_schedule);
        mContext = TravelMatchScheduleActivity.this;


        init();
        setListiner();

        if (savedInstanceState != null) {
            trackSelectorParameters = savedInstanceState.getParcelable(KEY_TRACK_SELECTOR_PARAMETERS);
            startAutoPlay = savedInstanceState.getBoolean(KEY_AUTO_PLAY);
            startWindow = savedInstanceState.getInt(KEY_WINDOW);
            startPosition = savedInstanceState.getLong(KEY_POSITION);
        } else {
            trackSelectorParameters = new DefaultTrackSelector.ParametersBuilder().build();
            clearStartPosition();
        }

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
        travelmatchscheduleGalleryList.add(new TravelModel("https://www.bharatarmy.com/Docs/Mobile/25cf4087-b.jpg", "Image"));
        travelmatchscheduleGalleryList.add(new TravelModel("https://www.bharatarmy.com/Docs/banner_app_02.jpg", "Image"));
        travelmatchscheduleGalleryList.add(new TravelModel("https://www.bharatarmy.com/Docs/Mobile/25cf4087-b.jpg", "Image"));
        travelmatchscheduleGalleryList.add(new TravelModel("https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_5mb.mp4", "Video"));
        travelmatchscheduleGalleryList.add(new TravelModel("https://www.bharatarmy.com/Docs/banner_app_02.jpg", "Image"));

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
        tournamentfilterteamwithcityDetailModel = new ArrayList<>();

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
        Log.d("filterteamcitiesData:", "" + tournamentfilterteamwithcityDetailModel.size());
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
                Utils.handleClickEvent(mContext, activityTravelMatchScheduleBinding.fabLinear);
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
            exoPlay = (ImageView)findViewById(R.id.exo_play);

            if (travelmatchscheduleGalleryList.get(position).getCityHotelAmenitiesName().equalsIgnoreCase("Image")) {
                travelScheduleBannerListItemBinding.detailGalleryImage.setVisibility(View.VISIBLE);
                travelScheduleBannerListItemBinding.baVideoRlv.setVisibility(View.GONE);
                travelScheduleBannerListItemBinding.volumeLinear.setVisibility(View.GONE);
                Utils.setImageInImageView(travelmatchscheduleGalleryList.get(position).getCityHotelAmenitiesImage(), travelScheduleBannerListItemBinding.detailGalleryImage, mContext);

                Log.d("HotelGalleeryAdapter : ", travelmatchscheduleGalleryList.get(position).getCityHotelAmenitiesImage());
            } else {
                travelScheduleBannerListItemBinding.detailGalleryImage.setVisibility(View.GONE);
                travelScheduleBannerListItemBinding.baVideoRlv.setVisibility(View.VISIBLE);

                Utils.setImageInImageView("http://devenv.bharatarmy.com//Docs/Media/Thumb/3b484b79-ad6f-4db2-838a-478b117fabf7-Thumb_20200210_BA121034.jpg",travelScheduleBannerListItemBinding.videoThumbnailImage,mContext);
                videopathStr = travelmatchscheduleGalleryList.get(position).getCityHotelAmenitiesImage();

                travelScheduleBannerListItemBinding.videoPlayImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        travelScheduleBannerListItemBinding.videoThumbnailImage.setVisibility(View.GONE);
                        travelScheduleBannerListItemBinding.frameLayoutMain.setVisibility(View.VISIBLE);
                        travelScheduleBannerListItemBinding.loading.setVisibility(View.VISIBLE);

                        travelScheduleBannerListItemBinding.sliderPlayerView.setVisibility(View.VISIBLE);
                        travelScheduleBannerListItemBinding.volumeLinear.setVisibility(View.VISIBLE);
                        playerView = travelScheduleBannerListItemBinding.sliderPlayerView;
                        progressBar = travelScheduleBannerListItemBinding.loading;
                        initializePlayer();
                    }
                });
                travelScheduleBannerListItemBinding.volumeLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (travelScheduleBannerListItemBinding.volmueVideoButton.isShown()){
                            travelScheduleBannerListItemBinding.volmueVideoButton.setVisibility(View.GONE);
                            travelScheduleBannerListItemBinding.muteVideoButton.setVisibility(View.VISIBLE);

                        }else{
                            travelScheduleBannerListItemBinding.volmueVideoButton.setVisibility(View.VISIBLE);
                            travelScheduleBannerListItemBinding.muteVideoButton.setVisibility(View.GONE);

                        }
                    }
                });
            }


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

    // Internal methods
    private void initializePlayer() {

        TrackSelection.Factory trackSelectionFactory;
        trackSelectionFactory = new AdaptiveTrackSelection.Factory();


        @DefaultRenderersFactory.ExtensionRendererMode int extensionRendererMode =
                ((MyApplication) getApplication()).useExtensionRenderers()
                        ? (true ? DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
                        : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON)
                        : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF;

        DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(this, null,
                DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER);

        trackSelector = new DefaultTrackSelector(trackSelectionFactory);
        trackSelector.setParameters(trackSelectorParameters);
        lastSeenTrackGroupArray = null;


        DefaultAllocator defaultAllocator = new DefaultAllocator(true, C.DEFAULT_VIDEO_BUFFER_SIZE);
        DefaultLoadControl defaultLoadControl = new DefaultLoadControl(defaultAllocator,
                DefaultLoadControl.DEFAULT_MIN_BUFFER_MS,
                DefaultLoadControl.DEFAULT_MAX_BUFFER_MS,
                DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_MS,
                DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS,
                DefaultLoadControl.DEFAULT_TARGET_BUFFER_BYTES,
                DefaultLoadControl.DEFAULT_PRIORITIZE_TIME_OVER_SIZE_THRESHOLDS
        );

        player = ExoPlayerFactory.newSimpleInstance(/* context= */ this, renderersFactory, trackSelector, defaultLoadControl);
        player.addListener(new PlayerEventListener());
        player.setPlayWhenReady(startAutoPlay);
        player.addAnalyticsListener(new EventLogger(trackSelector));
        playerView.setPlayer(player);


        mediaSource = buildMediaSource(Uri.parse("https://baappvideo.s3.ap-south-1.amazonaws.com/appvideo_1.mp4")); // videoUrlStr
//        https://baappvideo.s3.ap-south-1.amazonaws.com/74425094_140387590620474_1342703700878427324_n.mp4
//                https://www.bharatarmy.com//Docs/74425094_140387590620474_1342703700878427324_n.mp4
        player.prepare(mediaSource);
        updateButtonVisibilities();

    }

    private MediaSource buildMediaSource(Uri uri) {
        return buildMediaSource(uri, null);
    }

    @SuppressWarnings("unchecked")
    private MediaSource buildMediaSource(Uri uri, @Nullable String overrideExtension) {
        @C.ContentType int type = Util.inferContentType(uri, overrideExtension);
        switch (type) {
            case C.TYPE_DASH:
                Log.d("Dash", "Dash");
                return new DashMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(uri);
            case C.TYPE_SS:
                Log.d("SS", "SS");
                return new SsMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(uri);
            case C.TYPE_HLS:
                Log.d("HLS", "HLS");
                return new HlsMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(uri);
            case C.TYPE_OTHER:
                Log.d("Other", "Other");
                return new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            default: {
                throw new IllegalStateException("Unsupported type: " + type);
            }
        }
    }

    private void releasePlayer() {
        if (player != null) {
            updateTrackSelectorParameters();
            updateStartPosition();
            player.release();
            player = null;
            mediaSource = null;
            trackSelector = null;
        }
        releaseMediaDrm();
    }

    private void releaseMediaDrm() {
        if (mediaDrm != null) {
            mediaDrm.release();
            mediaDrm = null;
        }
    }

    private void releaseAdsLoader() {
        if (adsLoader != null) {
            adsLoader.release();
            adsLoader = null;
            loadedAdTagUri = null;
            playerView.getOverlayFrameLayout().removeAllViews();
        }
    }

    private void updateTrackSelectorParameters() {
        if (trackSelector != null) {
            trackSelectorParameters = trackSelector.getParameters();
        }
    }

    private void updateStartPosition() {
        if (player != null) {
            startAutoPlay = player.getPlayWhenReady();
            startWindow = player.getCurrentWindowIndex();
            startPosition = Math.max(0, player.getContentPosition());
        }
    }

    private void clearStartPosition() {
        startAutoPlay = true;
        startWindow = C.INDEX_UNSET;
        startPosition = C.TIME_UNSET;
    }

    /**
     * Returns a new DataSource factory.
     */
    private DataSource.Factory buildDataSourceFactory() {
        return ((MyApplication) getApplication()).buildDataSourceFactory();
    }


    private void updateButtonVisibilities() {
        if (player == null) {
            return;
        }

        MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
        if (mappedTrackInfo == null) {
            return;
        }

        for (int i = 0; i < mappedTrackInfo.getRendererCount(); i++) {
            TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i);
            if (trackGroups.length != 0) {
                int label;
                switch (player.getRendererType(i)) {
                    case C.TRACK_TYPE_AUDIO:
                        label = R.string.exo_track_selection_title_audio;
                        break;
                    case C.TRACK_TYPE_VIDEO:
                        label = R.string.exo_track_selection_title_video;
                        break;
                    case C.TRACK_TYPE_TEXT:
                        label = R.string.exo_track_selection_title_text;
                        break;
                    default:
                        continue;
                }
            }
        }
    }


    private void showToast(int messageId) {
        showToast(getString(messageId));
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private class PlayerEventListener implements Player.EventListener {

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            switch (playbackState) {
                case ExoPlayer.STATE_READY:
                    progressBar.setVisibility(View.GONE);

                    break;
                case ExoPlayer.STATE_BUFFERING:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
            }
            updateButtonVisibilities();
        }

        @Override
        public void onPositionDiscontinuity(@Player.DiscontinuityReason int reason) {
            if (player.getPlaybackError() != null) {
                // The user has performed a seek whilst in the error state. Update the resume position so
                // that if the user then retries, playback resumes from the position to which they seeked.
                updateStartPosition();
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException e) {
            if (isBehindLiveWindow(e)) {
                clearStartPosition();
                initializePlayer();
            } else {
                updateStartPosition();
                updateButtonVisibilities();
//                showControls();
            }
        }

        @Override
        @SuppressWarnings("ReferenceEquality")
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            updateButtonVisibilities();
            if (trackGroups != lastSeenTrackGroupArray) {
                MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
                if (mappedTrackInfo != null) {
                    if (mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO)
                            == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                        showToast(R.string.error_unsupported_video);
                    }
                    if (mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_AUDIO)
                            == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                        showToast(R.string.error_unsupported_audio);
                    }
                }
                lastSeenTrackGroupArray = trackGroups;
            }
        }
    }

    private class PlayerErrorMessageProvider implements ErrorMessageProvider<ExoPlaybackException> {

        @Override
        public Pair<Integer, String> getErrorMessage(ExoPlaybackException e) {
            String errorString = getString(R.string.error_generic);
            if (e.type == ExoPlaybackException.TYPE_RENDERER) {
                Exception cause = e.getRendererException();
                if (cause instanceof MediaCodecRenderer.DecoderInitializationException) {
                    // Special case for decoder initialization failures.
                    MediaCodecRenderer.DecoderInitializationException decoderInitializationException =
                            (MediaCodecRenderer.DecoderInitializationException) cause;
                    if (decoderInitializationException.decoderName == null) {
                        if (decoderInitializationException.getCause() instanceof MediaCodecUtil.DecoderQueryException) {
                            errorString = getString(R.string.error_querying_decoders);
                        } else if (decoderInitializationException.secureDecoderRequired) {
                            errorString =
                                    getString(
                                            R.string.error_no_secure_decoder, decoderInitializationException.mimeType);
                        } else {
                            errorString =
                                    getString(R.string.error_no_decoder, decoderInitializationException.mimeType);
                        }
                    } else {
                        errorString =
                                getString(
                                        R.string.error_instantiating_decoder,
                                        decoderInitializationException.decoderName);
                    }
                }
            }
            return Pair.create(0, errorString);
        }
    }




    @Override
    public void onNewIntent(Intent intent) {
        releasePlayer();
        releaseAdsLoader();
        clearStartPosition();
        setIntent(intent);
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        if (Util.SDK_INT > 23) {
//            initializePlayer();
//            if (playerView != null) {
//                playerView.onResume();
//            }
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (Util.SDK_INT <= 23 || player == null) {
//            initializePlayer();
//            if (playerView != null) {
//                playerView.onResume();
//            }
//        }
//    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            if (playerView != null) {
                playerView.onPause();
            }
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            if (playerView != null) {
                playerView.onPause();
            }
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseAdsLoader();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults.length == 0) {
            // Empty results are triggered if a permission is requested while another request was already
            // pending and can be safely ignored in this case.
            return;
        }
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initializePlayer();
        } else {
            showToast(R.string.storage_permission_denied);
            finish();
        }
    }

// Activity input

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        updateTrackSelectorParameters();
        updateStartPosition();
        outState.putParcelable(KEY_TRACK_SELECTOR_PARAMETERS, trackSelectorParameters);
        outState.putBoolean(KEY_AUTO_PLAY, startAutoPlay);
        outState.putInt(KEY_WINDOW, startWindow);
        outState.putLong(KEY_POSITION, startPosition);
    }

// OnClickListener methods

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // See whether the player view wants to handle media or DPAD keys events.
        return playerView.dispatchKeyEvent(event) || super.dispatchKeyEvent(event);
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

    public void updateteamflagAdapter() {
        if (selectedtournamentteamId != null && selectedtournamentteamId.size() != 0) {
            for (int i = 0; i < teamnameflagList.size(); i++) {
                for (int j = 0; j < selectedtournamentteamId.size(); j++) {
                    if (String.valueOf(teamnameflagList.get(i).getCountryId()).equalsIgnoreCase(selectedtournamentteamId.get(j))) {
                        teamnameflagList.get(i).setTeamSelected("1");
                    }
                }

            }
            teamNameFlagScheduleAdapter.notifyDataSetChanged();
        } else {
            for (int i = 0; i < teamnameflagList.size(); i++) {

                teamnameflagList.get(i).setTeamSelected("0");

            }
            teamNameFlagScheduleAdapter.notifyDataSetChanged();
        }
    }
}
