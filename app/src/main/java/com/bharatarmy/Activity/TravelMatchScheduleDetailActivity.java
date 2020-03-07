package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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

import com.bharatarmy.Adapter.TravelMatchScheduleHospitalityDetailAdapter;
import com.bharatarmy.Adapter.TravelMatchScheduleHotelDetailAdapter;
import com.bharatarmy.Adapter.TravelMatchSchedulePackageDetailAdapter;
import com.bharatarmy.Adapter.TravelMatchScheduleSightSeeingDetailAdapter;
import com.bharatarmy.Adapter.TravelMatchScheduleStadiumDetailAdapter;
import com.bharatarmy.Adapter.TravelMatchScheduleTicketDetailAdapter;
import com.bharatarmy.CallOneAnimationCartAddItemMethod;
import com.bharatarmy.CallTwoAnimationCartAddItemMethod;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.MyApplication;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityTravelMatchScheduleDetailBinding;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class TravelMatchScheduleDetailActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTravelMatchScheduleDetailBinding activityTravelMatchScheduleDetailBinding;
    Context mContext;

    /*viewpager control*/
    private MyTravelMatchScheduleDetailGalleryViewPagerAdapter myTravelMatchScheduleDetailGalleryViewPagerAdapter;
    ArrayList<TravelModel> travelmatchscheduledetailGalleryList;
    private ImageView[] dots;

    /*Schedule detail variable*/
    String firstcounrtyNameStr, secondcountryNameStr, firstcountryFlagStr, secondcountryFlagStr, groundLocationStr, matchdatescheduleStr;

    /*Match Sub list variable*/
    ArrayList<TravelModel> travelticketList;
    ArrayList<TravelModel> travelhotelList;
    ArrayList<TravelModel> travelpackageList;
    ArrayList<TravelModel> travelhospitalityList;
    ArrayList<TravelModel> travelsightseeingList;
    ArrayList<TravelModel> travelstadiumList;

    /*Schedule detail adapter*/
    TravelMatchScheduleTicketDetailAdapter travelMatchScheduleTicketDetailAdapter;
    TravelMatchScheduleHotelDetailAdapter travelMatchScheduleHotelDetailAdapter;
    TravelMatchSchedulePackageDetailAdapter travelMatchSchedulePackageDetailAdapter;
    TravelMatchScheduleHospitalityDetailAdapter travelMatchScheduleHospitalityDetailAdapter;
    TravelMatchScheduleSightSeeingDetailAdapter travelMatchScheduleSightSeeingDetailAdapter;
    TravelMatchScheduleStadiumDetailAdapter travelMatchScheduleStadiumDetailAdapter;

    /*Adapter Layout Manger variable*/
    GridLayoutManager gridLayoutManager, gridLayoutManager1;
    StaggeredGridLayoutManager staggeredGridLayoutManager, staggeredGridLayoutManager1;
    LinearLayoutManager sightseenLayoutManager, stadiumLayoutManager;

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
    String videopathStr;
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


        activityTravelMatchScheduleDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_match_schedule_detail);
        mContext = TravelMatchScheduleDetailActivity.this;

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

        setDataValue();
        setticketsListValue();
    }

    public void init() {
        setSupportActionBar(activityTravelMatchScheduleDetailBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        Utils.addCartItemCount(mContext, activityTravelMatchScheduleDetailBinding.addcarticon.cartCountItemTxt);

        /*Get Intent value */
        firstcounrtyNameStr = getIntent().getStringExtra("firstcountryName");
        firstcountryFlagStr = getIntent().getStringExtra("firstscountryFlag");
        secondcountryNameStr = getIntent().getStringExtra("secondcountryName");
        secondcountryFlagStr = getIntent().getStringExtra("secondcountryFlag");
        groundLocationStr = getIntent().getStringExtra("groundLocation");
        matchdatescheduleStr = getIntent().getStringExtra("matchdatetime");



        /*set the value of schedule detail*/
        activityTravelMatchScheduleDetailBinding.matchDateTimeTxt.setText(matchdatescheduleStr);
        activityTravelMatchScheduleDetailBinding.toolbarMatchDateTimeTxt.setText(matchdatescheduleStr);
        activityTravelMatchScheduleDetailBinding.matchGroundLocationTxt.setText(groundLocationStr);
        activityTravelMatchScheduleDetailBinding.toolbarMatchGroundLocationTxt.setText(groundLocationStr);
        activityTravelMatchScheduleDetailBinding.firstCountryNameTxt.setText(firstcounrtyNameStr);
        activityTravelMatchScheduleDetailBinding.toolbarFirstCountryNameTxt.setText(firstcounrtyNameStr);
        activityTravelMatchScheduleDetailBinding.secondCountryNameTxt.setText(secondcountryNameStr);
        activityTravelMatchScheduleDetailBinding.toolbarSecondCountryNameTxt.setText(secondcountryNameStr);

        if (firstcountryFlagStr != null) {
            Utils.setImageInImageView(firstcountryFlagStr, activityTravelMatchScheduleDetailBinding.firstCountryflagImage, mContext);
        }
        if (secondcountryFlagStr != null) {
            Utils.setImageInImageView(secondcountryFlagStr, activityTravelMatchScheduleDetailBinding.secondCountryflagImage, mContext);
        }


    }

    public void setListiner() {
        activityTravelMatchScheduleDetailBinding.backImg.setOnClickListener(this);

        activityTravelMatchScheduleDetailBinding.ticketsMainLinear.setOnClickListener(this);
        activityTravelMatchScheduleDetailBinding.hotelMainLinear.setOnClickListener(this);
        activityTravelMatchScheduleDetailBinding.packageMainLinear.setOnClickListener(this);
        activityTravelMatchScheduleDetailBinding.hospitalityMainLinear.setOnClickListener(this);
        activityTravelMatchScheduleDetailBinding.siteseenMainLinear.setOnClickListener(this);
        activityTravelMatchScheduleDetailBinding.locationDetailLinear.setOnClickListener(this);

        activityTravelMatchScheduleDetailBinding.tabAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
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
                    activityTravelMatchScheduleDetailBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.heading_bg));

                    activityTravelMatchScheduleDetailBinding.toolbarMatchCountryDetailLinear.setVisibility(View.VISIBLE);
                    activityTravelMatchScheduleDetailBinding.toolbarMatchDateTimeTxt.setVisibility(View.VISIBLE);
                    activityTravelMatchScheduleDetailBinding.toolbarLocationDetailLinear.setVisibility(View.VISIBLE);
                    activityTravelMatchScheduleDetailBinding.addcarticon.cartLayout.setVisibility(View.VISIBLE);

                    activityTravelMatchScheduleDetailBinding.tabCollapseToolbar.setTitle("");
                    Typeface typeface = ResourcesCompat.getFont(mContext, R.font.helveticaneueltstdbdcn);
                    activityTravelMatchScheduleDetailBinding.tabCollapseToolbar.setCollapsedTitleTypeface(typeface);
                    activityTravelMatchScheduleDetailBinding.tabCollapseToolbar.setExpandedTitleTypeface(typeface);
                    activityTravelMatchScheduleDetailBinding.tabCollapseToolbar.setCollapsedTitleGravity(Gravity.CENTER_VERTICAL);
                    activityTravelMatchScheduleDetailBinding.tabCollapseToolbar.setExpandedTitleGravity(Gravity.START);
                    activityTravelMatchScheduleDetailBinding.toolbarBottomView.setVisibility(View.GONE);
                    activityTravelMatchScheduleDetailBinding.toolbarBottomLeftView.setVisibility(View.GONE);

                    isShow = true;
                } else if (isShow) {
                    activityTravelMatchScheduleDetailBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                    activityTravelMatchScheduleDetailBinding.toolbarMatchCountryDetailLinear.setVisibility(View.GONE);
                    activityTravelMatchScheduleDetailBinding.toolbarMatchDateTimeTxt.setVisibility(View.GONE);
                    activityTravelMatchScheduleDetailBinding.toolbarLocationDetailLinear.setVisibility(View.GONE);
                    activityTravelMatchScheduleDetailBinding.addcarticon.cartLayout.setVisibility(View.GONE);

                    activityTravelMatchScheduleDetailBinding.tabCollapseToolbar.setTitle("");
                    activityTravelMatchScheduleDetailBinding.toolbarBottomLeftView.setVisibility(View.VISIBLE);
                    activityTravelMatchScheduleDetailBinding.toolbarBottomView.setVisibility(View.GONE);
                    isShow = false;
                }

            }
        });

        activityTravelMatchScheduleDetailBinding.addcarticon.cartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addcartItemIntent = new Intent(mContext, CartItemShowActivity.class);
                startActivity(addcartItemIntent);
            }
        });
    }

    public void setDataValue() {
        //        travel match schedule detail gallery List
        travelmatchscheduledetailGalleryList = new ArrayList<TravelModel>();
        travelmatchscheduledetailGalleryList.add(new TravelModel("https://www.bharatarmy.com/Docs/Mobile/25cf4087-b.jpg", "Image"));
        travelmatchscheduledetailGalleryList.add(new TravelModel("https://www.bharatarmy.com/Docs/banner_app_02.jpg", "Image"));
        travelmatchscheduledetailGalleryList.add(new TravelModel("https://www.bharatarmy.com/Docs/Mobile/25cf4087-b.jpg", "Image"));
        travelmatchscheduledetailGalleryList.add(new TravelModel("https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_5mb.mp4", "Video"));
        travelmatchscheduledetailGalleryList.add(new TravelModel("https://www.bharatarmy.com/Docs/banner_app_02.jpg", "Image"));

        addBottomDots(0);
        myTravelMatchScheduleDetailGalleryViewPagerAdapter = new MyTravelMatchScheduleDetailGalleryViewPagerAdapter();
        activityTravelMatchScheduleDetailBinding.travelMatchScheduleDetailGalleryViewpager.setAdapter(myTravelMatchScheduleDetailGalleryViewPagerAdapter);
        activityTravelMatchScheduleDetailBinding.travelMatchScheduleDetailGalleryViewpager.addOnPageChangeListener(viewPagerPageChangeListener);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.tickets_main_linear:
                activityTravelMatchScheduleDetailBinding.ticktesSelectedLinear.setBackground(getResources().getDrawable(R.drawable.bg_gradiant_line));
                activityTravelMatchScheduleDetailBinding.packageSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.hotelSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.siteseenSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.hospitalitySelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));

                setticketsListValue();
                break;
            case R.id.package_main_linear:
                activityTravelMatchScheduleDetailBinding.packageSelectedLinear.setBackground(getResources().getDrawable(R.drawable.bg_gradiant_line));
                activityTravelMatchScheduleDetailBinding.ticktesSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.hotelSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.siteseenSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.hospitalitySelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));

                setpackageListValue();
                break;
            case R.id.hotel_main_linear:
                activityTravelMatchScheduleDetailBinding.hotelSelectedLinear.setBackground(getResources().getDrawable(R.drawable.bg_gradiant_line));
                activityTravelMatchScheduleDetailBinding.ticktesSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.packageSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.siteseenSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.hospitalitySelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));

                sethotelListValue();
                break;
            case R.id.siteseen_main_linear:
                activityTravelMatchScheduleDetailBinding.siteseenSelectedLinear.setBackground(getResources().getDrawable(R.drawable.bg_gradiant_line));
                activityTravelMatchScheduleDetailBinding.hotelSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.ticktesSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.packageSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.hospitalitySelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));

                setsightseensListValue();
                break;
            case R.id.hospitality_main_linear:
                activityTravelMatchScheduleDetailBinding.hospitalitySelectedLinear.setBackground(getResources().getDrawable(R.drawable.bg_gradiant_line));
                activityTravelMatchScheduleDetailBinding.hotelSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.ticktesSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.packageSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));
                activityTravelMatchScheduleDetailBinding.siteseenSelectedLinear.setBackground(getResources().getDrawable(R.drawable.line_shape));

                sethospitalityListValue();
                break;

            case R.id.location_detail_linear:
                Intent locationIntent = new Intent(mContext, TravelMatchStadiumViewActivity.class);
                startActivity(locationIntent);
                break;
        }
    }

    @SuppressLint("ResourceAsColor")
    private void addBottomDots(int currentPage) {
        dots = new ImageView[travelmatchscheduledetailGalleryList.size()];
        List<String> colorsActiveList = new ArrayList<>();
        List<String> colorsInactive = new ArrayList<>();
        for (int i = 0; i < travelmatchscheduledetailGalleryList.size(); i++) {
            colorsActiveList.add(String.valueOf(getResources().getColor(R.color.splash_bg_color)));
            colorsInactive.add(String.valueOf(getResources().getColor(R.color.gray)));
        }

        activityTravelMatchScheduleDetailBinding.viewPagerDotlinear.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);

            dots[i].setImageResource(R.drawable.unselected_new);
            dots[i].setPadding(0, 0, 10, 0);
            activityTravelMatchScheduleDetailBinding.viewPagerDotlinear.addView(dots[i]);
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

    public class MyTravelMatchScheduleDetailGalleryViewPagerAdapter extends PagerAdapter {
        public MyTravelMatchScheduleDetailGalleryViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup parent, int position) {
            TravelScheduleBannerListItemBinding travelScheduleBannerListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.travel_schedule_banner_list_item, parent, false);

            exoPlay = (ImageView)findViewById(R.id.exo_play);

            if (travelmatchscheduledetailGalleryList.get(position).getCityHotelAmenitiesName().equalsIgnoreCase("Image")) {
                travelScheduleBannerListItemBinding.detailGalleryImage.setVisibility(View.VISIBLE);
                travelScheduleBannerListItemBinding.baVideoRlv.setVisibility(View.GONE);
                travelScheduleBannerListItemBinding.volumeLinear.setVisibility(View.GONE);
                Utils.setImageInImageView(travelmatchscheduledetailGalleryList.get(position).getCityHotelAmenitiesImage(), travelScheduleBannerListItemBinding.detailGalleryImage, mContext);

                Log.d("HotelGalleeryAdapter : ", travelmatchscheduledetailGalleryList.get(position).getCityHotelAmenitiesImage());
            } else {
                travelScheduleBannerListItemBinding.detailGalleryImage.setVisibility(View.GONE);
                travelScheduleBannerListItemBinding.baVideoRlv.setVisibility(View.VISIBLE);

Utils.setImageInImageView("http://devenv.bharatarmy.com//Docs/Media/Thumb/3b484b79-ad6f-4db2-838a-478b117fabf7-Thumb_20200210_BA121034.jpg",travelScheduleBannerListItemBinding.videoThumbnailImage,mContext);
                videopathStr = travelmatchscheduledetailGalleryList.get(position).getCityHotelAmenitiesImage();

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
            return travelmatchscheduledetailGalleryList.size();
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

    public void setticketsListValue() {
        travelticketList = new ArrayList<TravelModel>();
        travelticketList.add(new TravelModel("http://devenv.bharatarmy.com/Docs/Mobile/25cf4087-b.jpg", "Ticket",
                "Category A", "Lorem Ipsum is simply dummy text.", "Extra 10% off* with Hotel.",
                "₹ 700", "1", "ticket", "0"));


        travelticketList.add(new TravelModel("http://devenv.bharatarmy.com//Docs/e35eee60-7.jpg", "Ticket",
                "Category B", "Lorem Ipsum is simply dummy text of the printing.", "",
                "₹ 650", "1", "ticket", "0"));


        travelticketList.add(new TravelModel("http://devenv.bharatarmy.com//Docs/5c6783ff-d.jpg", "Ticket",
                "Category D", "Lorem Ipsum is simply dummy text of the printing.", "Extra 20% off* with Hotel.",
                "₹ 400", "3", "ticket", "0"));

        travelticketList.add(new TravelModel("http://devenv.bharatarmy.com/Docs/Mobile/25cf4087-b.jpg", "Ticket",
                "Category C", "Lorem Ipsum is simply dummy text of the printing.", "",
                "₹ 550", "2", "ticket", "0"));


        travelMatchScheduleTicketDetailAdapter = new TravelMatchScheduleTicketDetailAdapter(mContext, travelticketList, new MorestoryClick() {
            @Override
            public void getmorestoryClick() {
                int addTocartposition = travelMatchScheduleTicketDetailAdapter.adptercartAddPosition();
                Utils.animationAdd(mContext, activityTravelMatchScheduleDetailBinding.addcarticon.cartLayout,
                        activityTravelMatchScheduleDetailBinding.toolbar, activityTravelMatchScheduleDetailBinding.addcarticon.cartImage,
                        activityTravelMatchScheduleDetailBinding.addcarticon.cartCountItemTxt,
                        activityTravelMatchScheduleDetailBinding.htabMaincontent,
                        null, null, addTocartposition, "travelmatchscheduleticket");
            }
        }, new image_click() {
            @Override
            public void image_more_click() {
                Utils.removeCartItemCount(mContext, activityTravelMatchScheduleDetailBinding.addcarticon.cartCountItemTxt);
            }
        });
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        activityTravelMatchScheduleDetailBinding.detailRcv.setLayoutManager(staggeredGridLayoutManager);
        activityTravelMatchScheduleDetailBinding.detailRcv.setAdapter(travelMatchScheduleTicketDetailAdapter);

    }

    public void sethotelListValue() {
        travelhotelList = new ArrayList<>();
        travelhotelList.add(new TravelModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSCtpFvQulc666pbmJhIdodJxCSFhPlACieZOemcK3qb5w95acjRQ&s",
                "PAN PACIFIC PERTH", "Bharat Army Experience Package with Accommodation Stay.",
                4, "₹ 10000", "Geelong", "Australia"));

        travelhotelList.add(new TravelModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDXmTnFIhLvlcFmZzc4BXBQURhFFV5J8bKoCsIK3e6QM74BnEj&s",
                "FOUR POINTS BY SHERATON PERTH", "Bharat Army Experience Package with Accommodation Stay.",
                3, "₹ 9000", "Sydney", "India"));


        travelMatchScheduleHotelDetailAdapter = new TravelMatchScheduleHotelDetailAdapter(mContext, travelhotelList, new MorestoryClick() {
            @Override
            public void getmorestoryClick() {
                int addTocartposition = travelMatchScheduleHotelDetailAdapter.adptercartAddPosition();
                Utils.animationAdd(mContext, activityTravelMatchScheduleDetailBinding.addcarticon.cartLayout,
                        activityTravelMatchScheduleDetailBinding.toolbar, activityTravelMatchScheduleDetailBinding.addcarticon.cartImage,
                        activityTravelMatchScheduleDetailBinding.addcarticon.cartCountItemTxt,
                        activityTravelMatchScheduleDetailBinding.htabMaincontent,
                        null, null, addTocartposition, "travelmatchschedulehotel");
            }
        }, new image_click() {
            @Override
            public void image_more_click() {
                Utils.removeCartItemCount(mContext, activityTravelMatchScheduleDetailBinding.addcarticon.cartCountItemTxt);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        activityTravelMatchScheduleDetailBinding.detailRcv.setLayoutManager(mLayoutManager);
        activityTravelMatchScheduleDetailBinding.detailRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchScheduleDetailBinding.detailRcv.setAdapter(travelMatchScheduleHotelDetailAdapter);
        travelMatchScheduleHotelDetailAdapter.notifyDataSetChanged();
    }

    public void setpackageListValue() {
        travelpackageList = new ArrayList<TravelModel>();
        travelpackageList.add(new TravelModel("xyz", "Australian Double Dhamaka: Honeymoon & adventure at one shot", AppConfiguration.IMAGE_URL + "aus1.jpg",
                "Jet Boat Ride from Main Beach.Bungy jumping from 165 ft distance at Cairns.Great Barrier Reef Experience",
                "1k", "900", "true"));

        travelpackageList.add(new TravelModel("xyz", "Explore the best of Australia with your soulmate", AppConfiguration.IMAGE_URL + "aus2.jpg",
                "Grand Barossa Valley Day Tour.Happy day out at the Kangaroo Island with a fun tour amidst natural highlights.Eureka Skydeck 88.Sydney Harbour Jet Boat Thrill Ride: 30 Minutes ",
                "2k", "500", "false"));

        travelpackageList.add(new TravelModel("xyz", "Celebrate love in the Australian lands", AppConfiguration.IMAGE_URL + "aus3.jpg",
                "Delicious dinner cruise during sunset at Sydney Harbour exposed to amazing vistas and views around the arena.Super Pass: Film World, Sea World & Wet'n'Wild Water World.Morning Whale Watching Cruise.Car hire for Great Ocean Road",
                "5k", "1000", "false"));

        travelMatchSchedulePackageDetailAdapter = new TravelMatchSchedulePackageDetailAdapter(mContext, travelpackageList, new MorestoryClick() {
            @Override
            public void getmorestoryClick() {

            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        activityTravelMatchScheduleDetailBinding.detailRcv.setLayoutManager(layoutManager);
        activityTravelMatchScheduleDetailBinding.detailRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchScheduleDetailBinding.detailRcv.setAdapter(travelMatchSchedulePackageDetailAdapter);
        travelMatchSchedulePackageDetailAdapter.notifyDataSetChanged();
    }

    public void sethospitalityListValue() {
        travelhospitalityList = new ArrayList<TravelModel>();

        travelhospitalityList.add(new TravelModel("https://3.imimg.com/data3/VE/IW/MY-16198270/hotel-management-service-500x500.jpg", "Hospitality Category",
                "The Pavilion", "The Pavilion is the ultimate hospitality experience that will deliver a sophisticated, yet relaxed environment to be shared with family, friends or business associates.",
                "", "₹ 475", "3", "hospitality", "0"));

        travelhospitalityList.add(new TravelModel("https://www.morganrichardson.co.uk/wp-content/uploads/2017/11/Hotel-Insurance.jpg", "",
                "Private Suites", "Private Suites provide the ultimate hospitality experience.",
                "Extra 20% off* with Hotel.", "₹ 600", "3", "hospitality", "0"));

        travelhospitalityList.add(new TravelModel("https://www.morganrichardson.co.uk/wp-content/uploads/2017/11/Hotel-Insurance.jpg", "",
                "Open Air Boxes", "Open Air Boxes are a casual entertainment option providing you and your guests everything you need for an effortless day of cricket enjoyment.",
                "Extra 20% off* with Hotel.", "₹ 650", "1", "hospitality", "0"));

        travelhospitalityList.add(new TravelModel("https://i0.wp.com/www.perrygroup.com/wp-content/uploads/2016/01/service-pic3-1.jpg", "",
                "Club 20/20", "Club 20/20 packages suit those seeking an informal entertainment experience that still provides hospitality with outstanding service.",
                "", "₹ 750", "2", "hospitality", "0"));


        travelMatchScheduleHospitalityDetailAdapter = new TravelMatchScheduleHospitalityDetailAdapter(mContext, travelhospitalityList);
        staggeredGridLayoutManager1 = new StaggeredGridLayoutManager(2, 1);
        staggeredGridLayoutManager1.setOrientation(RecyclerView.VERTICAL);
        activityTravelMatchScheduleDetailBinding.detailRcv.setLayoutManager(staggeredGridLayoutManager1);
        activityTravelMatchScheduleDetailBinding.detailRcv.setAdapter(travelMatchScheduleHospitalityDetailAdapter);

        travelMatchScheduleHospitalityDetailAdapter.notifyDataSetChanged();
    }

    public void setsightseensListValue() {
        travelsightseeingList = new ArrayList<TravelModel>();

        travelsightseeingList.add(new TravelModel(AppConfiguration.IMAGE_URL + "Pinnacle_Tour.jpg",
                "1 Barrack Street Jetty, Perth WA 6000, Australia", "Pinnacle Tour",
                "Discover the beauty of Western Australia with ADAMS Pinnacle Tours. We have over 35 years of experience in the industry.",
                "Perth"));

        travelsightseeingList.add(new TravelModel(AppConfiguration.IMAGE_URL + "Kings_Park_and_Botanic_Garden_Tour.jpg",
                "Fraser Ave, Perth WA 6005, Australia", "Kings Park and Botanic Garden",
                "The park is a mixture of grassed parkland, botanical gardens and natural bushland on Mount Eliza with two-thirds of the grounds conserved as native bushland.",
                "Perth"));

        travelsightseeingList.add(new TravelModel(AppConfiguration.IMAGE_URL + "Swan_River_Tour.jpg",
                "Western Australia, Australia", "Swan River",
                "The Swan River is a river in the south west of Western Australia. Its Aboriginal Noongar name is the Derbarl Yerrigan.",
                "Perth"));

        travelsightseeingList.add(new TravelModel(AppConfiguration.IMAGE_URL + "Sydney_Opera_House.jpg",
                "Bennelong Point, Sydney NSW 2000, Australia", "Sydney Opera House",
                "The Sydney Opera House is a multi-venue performing arts centre at Sydney Harbour in Sydney, New South Wales, Australia.",
                "Sydney"));


        travelMatchScheduleSightSeeingDetailAdapter = new TravelMatchScheduleSightSeeingDetailAdapter(mContext, travelsightseeingList);
        sightseenLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        activityTravelMatchScheduleDetailBinding.detailRcv.setLayoutManager(sightseenLayoutManager);
        activityTravelMatchScheduleDetailBinding.detailRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchScheduleDetailBinding.detailRcv.setAdapter(travelMatchScheduleSightSeeingDetailAdapter);
        travelMatchScheduleSightSeeingDetailAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        Utils.addCartItemCount(mContext, activityTravelMatchScheduleDetailBinding.addcarticon.cartCountItemTxt);
        super.onResume();
    }


}
