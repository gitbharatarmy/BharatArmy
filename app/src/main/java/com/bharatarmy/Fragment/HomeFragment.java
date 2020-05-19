package com.bharatarmy.Fragment;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Outline;
import android.graphics.drawable.TransitionDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bharatarmy.Activity.AllVideoShowInFullScreenActivity;
import com.bharatarmy.Activity.BAQuizDetailActivity;
import com.bharatarmy.Activity.BaPollActivity;
import com.bharatarmy.Activity.FeedbackActivity;
import com.bharatarmy.Activity.MoreInformationActivity;
import com.bharatarmy.Activity.MyProfileActivity;
import com.bharatarmy.Adapter.BharatArmyStoriesAdapter;
import com.bharatarmy.Adapter.ExploreHomeCategoryAdapter;
import com.bharatarmy.Adapter.MyBgpageAdapter;
import com.bharatarmy.Adapter.MyPagerAdapter;
import com.bharatarmy.Adapter.PartnersAdapter;
import com.bharatarmy.Adapter.UpcomingDashboardAdapter;
import com.bharatarmy.AlphaPageTransformer;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.DashboardDataModel;
import com.bharatarmy.Models.DashboardModel;
import com.bharatarmy.Models.ExpolringCategoryModel;
import com.bharatarmy.Models.HomeTemplateDetailModel;
import com.bharatarmy.Models.HomeTemplateModel;
import com.bharatarmy.Models.StoryDashboardData;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.Models.UpcommingDashboardModel;
import com.bharatarmy.R;
import com.bharatarmy.CountDownClockHome;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.MyApplication;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.CustomerGalleryItemBinding;
import com.bharatarmy.databinding.FragmentHomeBinding;
import com.bharatarmy.speeddialView.SpeedDialView;
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


import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
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
    MyCustomerGalleryViewPagerAdapter customerGalleryViewPagerAdapter;
    PartnersAdapter partnersAdapter;
    ExploreHomeCategoryAdapter exploreHomeCategoryAdapter;
    private TextView[] dots;
    List<TravelModel> customergalleryList;
    List<TravelModel> partnerlist;
    List<ExpolringCategoryModel> exploringcategorylist;
    List<HomeTemplateDetailModel> homeTemplateDetailModelList;
    String categoryIdStr, categoryNameStr, wheretocome;
    List<UpcommingDashboardModel> upcommingDashboardModelList;
    List<StoryDashboardData> storyDashboardDataList;
    private TransitionDrawable mTransition;
    private int animationCounter = 1;
    private Handler imageSwitcherHandler;
    SpeedDialView speedDial;
    int mNextSelectedScreen, mCurrentSelectedScreen = 0;
    public static OnItemClick mListener;
    String videopathStr, videoImagePathStr;
    AudioManager audioManager;
    int musicVolume, maxVolume;
    int position = 0;
    private MediaPlayer mediaPlayer;

    //    autostart
    String manufacturer;

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

    public String isUpdateAvailable, isForceUpdateAvailable, currentVersionStr;

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
        dataSourceFactory = buildDataSourceFactory();
        if (CookieHandler.getDefault() != DEFAULT_COOKIE_MANAGER) {
            CookieHandler.setDefault(DEFAULT_COOKIE_MANAGER);
        }

        // Inflate the layout for this fragment
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        rootView = fragmentHomeBinding.getRoot();
        mContext = getActivity().getApplicationContext();
        AppConfiguration.position = 0;

        speedDial = getActivity().findViewById(R.id.speedDial);
        speedDial.setVisibility(View.GONE);


        if (savedInstanceState != null) {
            trackSelectorParameters = savedInstanceState.getParcelable(KEY_TRACK_SELECTOR_PARAMETERS);
            startAutoPlay = savedInstanceState.getBoolean(KEY_AUTO_PLAY);
            startWindow = savedInstanceState.getInt(KEY_WINDOW);
            startPosition = savedInstanceState.getLong(KEY_POSITION);
        } else {
            trackSelectorParameters = new DefaultTrackSelector.ParametersBuilder().build();
            clearStartPosition();
        }

        return rootView;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
        } else {
            fragmentHomeBinding.shimmerViewContainerhome.startShimmerAnimation();


            callHomeBannerData();
            callDashboardData();
        }
        init();
        setListiner();

    }

    @SuppressLint("ResourceType")
    public void init() {


//        display offers page
       /* Intent displayoffersIntent=new Intent(mContext, DisplayOffersActivity.class);
        displayoffersIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(displayoffersIntent);*/


        fragmentHomeBinding.scrollHome.fullScroll(ScrollView.FOCUS_UP);

//      fill user profile section data
        if (Utils.retriveLoginData(mContext) != null) {
            fragmentHomeBinding.displayUserprofileLinear.setVisibility(View.VISIBLE);
            if (Utils.retriveLoginData(mContext).getName() != null) {
                fragmentHomeBinding.userprofileNameTxt.setText(Utils.retriveLoginData(mContext).getName());
                fragmentHomeBinding.userprofileNumberViewtxt.setText(Utils.retriveLoginData(mContext).getPhoneNo());
            }
        } else {
            fragmentHomeBinding.displayUserprofileLinear.setVisibility(View.GONE);
        }

//      Countdown Timer
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

//      use for Advertisement image
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

//     fill BA video
        audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);

        videoImagePathStr = "http://devenv.bharatarmy.com//Docs/Media/Thumb/a983346f-b0ac-4a49-91c6-f7196efd4629-1570705345206.jpg";
        Utils.setImageInImageView(videoImagePathStr, fragmentHomeBinding.videoThumbnailImage, mContext);

        musicVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

//        Uri video = Uri.parse("http://devenv.bharatarmy.com//Docs/Media/e83c8278-f1f8-4aa6-b618-1d2302b80416-MP4_20191010_163200.mp4");
        videopathStr = "http://devenv.bharatarmy.com//Docs/Media/e83c8278-f1f8-4aa6-b618-1d2302b80416-MP4_20191010_163200.mp4";


//     Partners List
        partnerlist = new ArrayList<>();

        partnerlist.add(new TravelModel("https://www.bharatarmy.com/Docs/Partner-9.png",
                "HighLights", "Bharat Army Highlights is the first of it’s kind sports fan ..."));

        partnerlist.add(new TravelModel("https://www.bharatarmy.com/Docs/Partner-8.png",
                "Red FM 93.5", "This World Cup; Bharat Army Travel & Red FM have a big ..."));

        partnerlist.add(new TravelModel("https://www.bharatarmy.com/Docs/hotstar-logo.png",
                "Hotstar", "The Bharat Army are pleased to announce that we ..."));

        partnerlist.add(new TravelModel("https://www.bharatarmy.com/Docs/puma_logo.png",
                "Puma", "PUMA sits at the top table of global sports brands ..."));

        partnerlist.add(new TravelModel("https://www.bharatarmy.com/Docs/hublot_logo.png",
                "Hublot", "When it comes to luxury watch brands, Hublot ..."));

        partnerlist.add(new TravelModel("https://www.bharatarmy.com/Docs/oceanone8-logo.png",
                "Ocean one8", "Ocean one8 takes the philosophy that Virat Kohli ..."));

        partnerlist.add(new TravelModel("https://www.bharatarmy.com/Docs/uber_logo.png",
                "Uber Eats", "Uber Eats is the top online food ordering and delivery platform in the world ..."));

        partnerlist.add(new TravelModel("https://www.bharatarmy.com/Docs/prideview_logo.png",
                "Prideview", "As a leading figure in the acquisition, management and sale of commercial ..."));


        partnersAdapter = new PartnersAdapter(mContext, partnerlist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        fragmentHomeBinding.partnersRcyList.setLayoutManager(mLayoutManager);
        fragmentHomeBinding.partnersRcyList.setItemAnimator(new DefaultItemAnimator());
        fragmentHomeBinding.partnersRcyList.setAdapter(partnersAdapter);

        //     Exploring category List
        exploringcategorylist = new ArrayList<>();

        exploringcategorylist.add(new ExpolringCategoryModel(R.drawable.ic_poll,"BA Poll",
                "1 poll", ContextCompat.getColor(mContext,R.color.orange)));

        exploringcategorylist.add(new ExpolringCategoryModel(R.drawable.ic_quiz,"BA Quiz",
                "2 quiz", ContextCompat.getColor(mContext,R.color.heading_bg)));


        exploreHomeCategoryAdapter = new ExploreHomeCategoryAdapter(mContext, exploringcategorylist);
        RecyclerView.LayoutManager mLayoutManagerexplore = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        fragmentHomeBinding.exploreCategoryRcyList.setLayoutManager(mLayoutManagerexplore);
        fragmentHomeBinding.exploreCategoryRcyList.setItemAnimator(new DefaultItemAnimator());
        fragmentHomeBinding.exploreCategoryRcyList.setAdapter(exploreHomeCategoryAdapter);



//      Customer Gallery
        customergalleryList = new ArrayList<>();
        customergalleryList.add(new TravelModel("Sri Ram", "http://devenv.bharatarmy.com//Docs/16636938-9.jpg",
                "1", "Fantastic Service, beautiful thing they are doing for the culture long may it continue \uD83D\uDC4F\uD83C\uDFFD\uD83D\uDC4F\uD83C\uDFFD\uD83D\uDC4F\uD83C\uDFFD.",
                "", "", "5")); //Application Designer

        customergalleryList.add(new TravelModel("Ricky Chand", "http://devenv.bharatarmy.com//Docs/16636938-9.jpg",
                "2", "",
                "http://devenv.bharatarmy.com//Docs/Media/Thumb/a983346f-b0ac-4a49-91c6-f7196efd4629-1570705345206.jpg",
                "http://devenv.bharatarmy.com//Docs/Media/e83c8278-f1f8-4aa6-b618-1d2302b80416-MP4_20191010_163200.mp4",
                "5")); //Software Engg

        customergalleryList.add(new TravelModel("Ajay GUPTA", "http://devenv.bharatarmy.com//Docs/16636938-9.jpg",
                "1", "Great service and offers. Certainly recommend the service and membership.",
                "", "", "4"));

        customergalleryList.add(new TravelModel("Balu Ramachandran", "http://devenv.bharatarmy.com//Docs/16636938-9.jpg",
                "2", "",
                "http://devenv.bharatarmy.com//Docs/Media/Thumb/acdb7074-8588-4059-a5f4-67d09730785a-1570441108244.jpg",
                "http://devenv.bharatarmy.com//Docs/Media/11f98532-8171-4c81-b8e1-60a33ccf193f-MP4_20191007_150748.mp4",
                "5"));

        addBottomDots(0);
        customerGalleryViewPagerAdapter = new MyCustomerGalleryViewPagerAdapter();
        fragmentHomeBinding.customerGalleryViewpager.setAdapter(customerGalleryViewPagerAdapter);
        fragmentHomeBinding.customerGalleryViewpager.addOnPageChangeListener(viewPagerPageChangeListener);

//     Autostart
        manufacturer = android.os.Build.MANUFACTURER;
        if ("xiaomi".equalsIgnoreCase(manufacturer) || "oppo".equalsIgnoreCase(manufacturer)
                || "vivo".equalsIgnoreCase(manufacturer) || "oneplus".equalsIgnoreCase(manufacturer)
                || "asus".equalsIgnoreCase(manufacturer) /*|| "samsung".equalsIgnoreCase(manufacturer)*/
                || "Letv".equalsIgnoreCase(manufacturer) || "Honor".equalsIgnoreCase(manufacturer)) {

            fragmentHomeBinding.settingLinear.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 200);
            fragmentHomeBinding.feedbackLinear.setLayoutParams(params);
        } else {
            fragmentHomeBinding.settingLinear.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 200);
            fragmentHomeBinding.feedbackLinear.setLayoutParams(params);
        }
    }

    public void setListiner() {

        playerView = fragmentHomeBinding.sliderPlayerView;
        progressBar = fragmentHomeBinding.loading;
        playerView.setErrorMessageProvider(new PlayerErrorMessageProvider());
        playerView.requestFocus();
        playerView.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 60);
            }
        });

        playerView.setClipToOutline(true);

        fragmentHomeBinding.subTitleTxt.setVisibility(View.GONE);
        fragmentHomeBinding.titleTxt.setVisibility(View.GONE);
        fragmentHomeBinding.knowMore.setVisibility(View.GONE);
        fragmentHomeBinding.shimmerViewContainer.startShimmerAnimation();
        fragmentHomeBinding.upcomingRcyList.showShimmerAdapter();
        fragmentHomeBinding.armyStoryRcyList.showShimmerAdapter();

        fragmentHomeBinding.knowMore.setOnClickListener(this);
        fragmentHomeBinding.advImg.setOnClickListener(this);
        fragmentHomeBinding.userprofileViewtxt.setOnClickListener(this);
        fragmentHomeBinding.startPauseMediaButton.setOnClickListener(this);
        fragmentHomeBinding.fullScreenButton.setOnClickListener(this);
        fragmentHomeBinding.volmueLinear.setOnClickListener(this);
        fragmentHomeBinding.faqLinear.setOnClickListener(this);
        fragmentHomeBinding.termsConditionLinear.setOnClickListener(this);
        fragmentHomeBinding.feedbackLinear.setOnClickListener(this);
        fragmentHomeBinding.settingLinear.setOnClickListener(this);
//        fragmentHomeBinding.baquizLinear.setOnClickListener(this);
        fragmentHomeBinding.submitPollBtn.setOnClickListener(this);
        fragmentHomeBinding.submitQuizBtn.setOnClickListener(this);
//        fragmentHomeBinding.baVideo.setOnClickListener(this);
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
                    isUpdateAvailable = String.valueOf(getDashboardModel.getIsUpdateAvailable());
                    isForceUpdateAvailable = String.valueOf(getDashboardModel.getIsForceUpdate());
//                    isForceUpdateAvailable = "0";
                    currentVersionStr = String.valueOf(getDashboardModel.getCurrentVersion());
                    if (isUpdateAvailable.equalsIgnoreCase("1")) {
                        Utils.checkupdateApplication(mContext, getActivity(), isForceUpdateAvailable, currentVersionStr);
                    }
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
        map.put("AppUserId", String.valueOf(Utils.getAppUserId(mContext)));

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
                        if (homeTemplateModel.getData().size() != 0) {
                            homeTemplateDetailModelList = homeTemplateModel.getData();
                            fragmentHomeBinding.shimmerViewContainerhome.stopShimmerAnimation();
                            fragmentHomeBinding.shimmerViewContainerhome.setVisibility(View.GONE);
                            fragmentHomeBinding.homebannerLayout.setVisibility(View.VISIBLE);
                            fillHomeBanner();
                        }
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
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));

        return map;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.know_more:
                Intent webviewIntent = new Intent(mContext, MoreInformationActivity.class);
                webviewIntent.putExtra("Story Heading", "Ab Jeetega India");
                webviewIntent.putExtra("StroyUrl", "http://ajif.in/");
                webviewIntent.putExtra("whereTocome", "aboutus");
                webviewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(webviewIntent);
                break;
            case R.id.adv_img:

                break;
            case R.id.userprofile_viewtxt:
                Intent profileintent = new Intent(mContext, MyProfileActivity.class);
                profileintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(profileintent);
                break;
            case R.id.start_pause_media_button:
                fragmentHomeBinding.videoThumbnailImage.setVisibility(View.GONE);
                fragmentHomeBinding.frameLayoutMain.setVisibility(View.VISIBLE);
                fragmentHomeBinding.loading.setVisibility(View.VISIBLE);
                fragmentHomeBinding.sliderPlayerView.setVisibility(View.VISIBLE);

                initializePlayer();
//                playvideo();
                break;
            case R.id.full_screen_button:
                fragmentHomeBinding.fullScreenButton.setVisibility(View.GONE);
                fragmentHomeBinding.volmueLinear.setVisibility(View.GONE);
                fragmentHomeBinding.frameLayoutMain.setVisibility(View.GONE);
                fragmentHomeBinding.videoThumbnailImage.setVisibility(View.VISIBLE);
//                fragmentHomeBinding.startPauseMediaButton.setVisibility(View.VISIBLE);
                Intent showImageVideoIntent = new Intent(mContext, AllVideoShowInFullScreenActivity.class);
                showImageVideoIntent.putExtra("AlbumImageThumb", videoImagePathStr);
                showImageVideoIntent.putExtra("AlbumImageVideoPath", videopathStr);
                showImageVideoIntent.putExtra("MediaType", "2");
                showImageVideoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(showImageVideoIntent);
                break;
            case R.id.volmue_linear:
                if (fragmentHomeBinding.volmueVideoButton.isShown()) {
                    fragmentHomeBinding.volmueVideoButton.setVisibility(View.GONE);
                    fragmentHomeBinding.muteVideoButton.setVisibility(View.VISIBLE);
                    AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
                    audioManager.adjustVolume(AudioManager.ADJUST_MUTE, AudioManager.ADJUST_MUTE);
                } else {
                    fragmentHomeBinding.volmueVideoButton.setVisibility(View.VISIBLE);
                    fragmentHomeBinding.muteVideoButton.setVisibility(View.GONE);
                    AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
                    audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
                }
//                voluesetting();
                break;
            case R.id.faq_linear:
                Intent faqIntent = new Intent(mContext, MoreInformationActivity.class);
                faqIntent.putExtra("Story Heading", "FAQ");
                faqIntent.putExtra("StroyUrl", AppConfiguration.TERMSURL);
                faqIntent.putExtra("whereTocome", "aboutus");
                faqIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(faqIntent);
                break;
            case R.id.terms_condition_linear:
                Intent privacypolicyIntent = new Intent(mContext, MoreInformationActivity.class);
                privacypolicyIntent.putExtra("Story Heading", "Privacy Policy");
                privacypolicyIntent.putExtra("StroyUrl", AppConfiguration.TERMSURL);
                privacypolicyIntent.putExtra("whereTocome", "aboutus");
                privacypolicyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(privacypolicyIntent);
                break;
            case R.id.setting_linear:
                autoStartNotificationPermissionDialog(mContext);
                break;
            case R.id.feedback_linear:
                Intent feedbackIntent=new Intent(mContext, FeedbackActivity.class);
                feedbackIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(feedbackIntent);
                break;
            case R.id.submit_quiz_btn:
                Intent bapollIntent=new Intent(mContext, BAQuizDetailActivity.class);
                bapollIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(bapollIntent);
                break;
            case R.id.submit_poll_btn:
                Intent pollIntent=new Intent(mContext, BaPollActivity.class);
                pollIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(pollIntent);
                break;
            case R.id.ba_video:
//                if (fragmentHomeBinding.baVideo.isPlaying()) {
//                    position = fragmentHomeBinding.baVideo.getCurrentPosition();
//                    fragmentHomeBinding.baVideo.pause();
//                    Log.d("videorunposition :", "" + position);
//                    fragmentHomeBinding.startPauseMediaButton.setVisibility(View.VISIBLE);
//                    fragmentHomeBinding.fullScreenButton.setVisibility(View.GONE);
//                    fragmentHomeBinding.volmueLinear.setVisibility(View.GONE);
//                } else {
//                    playvideo();
//                }
                break;

        }
    }

    private void autoStartNotificationPermissionDialog(Context context) {
        try {
            Intent intent = new Intent();
            if ("xiaomi".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
            } else if ("oppo".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity"));
            } else if ("vivo".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
            } else if ("Letv".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity"));
            } else if ("Honor".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
            } else if ("oneplus".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.oneplus.security", "com.oneplus.security.chainlaunch.view.ChainLaunchAppListAct‌​ivity"));
            } else if ("asus".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.asus.mobilemanager", "com.asus.mobilemanager.entry.FunctionActivity")).setData(android.net.Uri.parse("mobilemanager://function/entry/AutoStart"));
            }
//            else if("samsung".equalsIgnoreCase(manufacturer)) {
//                intent.setComponent(new ComponentName("com.samsung.android.lool", "com.samsung.android.sm.ui.battery.BatteryActivity"));
//            }

            List<ResolveInfo> list = mContext.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (list.size() > 0) {
                startActivity(intent);
            }

        } catch (Exception e) {
            e.printStackTrace();
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

    private void setVolume(int amount) {
        final int max = 100;
        final double numerator = max - amount > 0 ? Math.log(max - amount) : 0;
        final float volume = (float) (1 - (numerator / Math.log(max)));
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume, volume);
        }
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

    public void voluesetting() {
        if (mediaPlayer != null) {
            if (fragmentHomeBinding.muteVideoButton.isShown()) {
                audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
                setVolume(100);
                fragmentHomeBinding.muteVideoButton.setVisibility(View.GONE);
                fragmentHomeBinding.volmueVideoButton.setVisibility(View.VISIBLE);

            } else {
                setVolume(0);
                fragmentHomeBinding.muteVideoButton.setVisibility(View.VISIBLE);
                fragmentHomeBinding.volmueVideoButton.setVisibility(View.GONE);
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private void addBottomDots(int currentPage) {
        dots = new TextView[customergalleryList.size()];
        List<String> colorsActiveList = new ArrayList<>();
        List<String> colorsInactive = new ArrayList<>();
        for (int i = 0; i < customergalleryList.size(); i++) {
            colorsActiveList.add(String.valueOf(getResources().getColor(R.color.gray)));
            colorsInactive.add(String.valueOf(getResources().getColor(R.color.black)));
        }


        fragmentHomeBinding.viewPagerDotlinear.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(mContext);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            fragmentHomeBinding.viewPagerDotlinear.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Integer.parseInt(colorsActiveList.get(currentPage)));//colorsActive[currentPage]
    }

    public class MyCustomerGalleryViewPagerAdapter extends PagerAdapter {

        public MyCustomerGalleryViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup parent, int position) {

            CustomerGalleryItemBinding customerGalleryItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.customer_gallery_item, parent, false);

            customerGalleryItemBinding.usernameTxt.setText(customergalleryList.get(position).getTourCityName());
            customerGalleryItemBinding.userDesignationTxt.setText(customergalleryList.get(position).getbAImage());

            customerGalleryItemBinding.username1Txt.setText(customergalleryList.get(position).getTourCityName());

            customerGalleryItemBinding.ratingBar.setCount(Integer.parseInt(customergalleryList.get(position).getbAImage()));
            customerGalleryItemBinding.ratingBar1.setCount(Integer.parseInt(customergalleryList.get(position).getbAImage()));
            if (customergalleryList.get(position).getTourImage().equalsIgnoreCase("1")) {
                customerGalleryItemBinding.template1.setVisibility(View.VISIBLE);
                customerGalleryItemBinding.template2.setVisibility(View.GONE);
                customerGalleryItemBinding.customerDescTxt.setText(customergalleryList.get(position).getTourDescription());

//                makeTextViewResizable(customer_desc_txt,4,"Read More",true);
            } else if (customergalleryList.get(position).getTourImage().equalsIgnoreCase("2")) {
                customerGalleryItemBinding.startPauseMediaButton.setVisibility(View.VISIBLE);
                customerGalleryItemBinding.template1.setVisibility(View.GONE);
                customerGalleryItemBinding.template2.setVisibility(View.VISIBLE);
                Utils.setImageInImageView(customergalleryList.get(position).getTourTotalView(), customerGalleryItemBinding.customerImg, mContext);
            }


            customerGalleryItemBinding.customerImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent showImageVideoIntent = new Intent(mContext, AllVideoShowInFullScreenActivity.class);
                    showImageVideoIntent.putExtra("AlbumImageThumb", customergalleryList.get(position).getTourTotalView());
                    showImageVideoIntent.putExtra("AlbumImageVideoPath", customergalleryList.get(position).getTourTotalComment());
                    showImageVideoIntent.putExtra("MediaType", "2");
                    showImageVideoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(showImageVideoIntent);
                }
            });
            customerGalleryItemBinding.startPauseMediaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent showImageVideoIntent = new Intent(mContext, AllVideoShowInFullScreenActivity.class);
                    showImageVideoIntent.putExtra("AlbumImageThumb", customergalleryList.get(position).getTourTotalView());
                    showImageVideoIntent.putExtra("AlbumImageVideoPath", customergalleryList.get(position).getTourTotalComment());
                    showImageVideoIntent.putExtra("MediaType", "2");
                    showImageVideoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(showImageVideoIntent);
                }
            });

            parent.addView(customerGalleryItemBinding.getRoot());

            return customerGalleryItemBinding.getRoot();
        }

        @Override
        public int getCount() {
            return customergalleryList.size();
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

    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                String text;
                int lineEndIndex;
                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    lineEndIndex = tv.getLayout().getLineEnd(0);
                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                } else {
                    lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                }
                tv.setText(text);
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                tv.setText(
                        addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                viewMore), TextView.BufferType.SPANNABLE);
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {
            ssb.setSpan(new ClickableSpan() {

                @Override
                public void onClick(View widget) {
                    tv.setLayoutParams(tv.getLayoutParams());
                    tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                    tv.invalidate();
                    if (viewMore) {
                        makeTextViewResizable(tv, -1, "View Less", false);
                    } else {
                        makeTextViewResizable(tv, 3, "View More", true);
                    }

                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Utils.dismissDialog();
        releaseAdsLoader();
    }


    // Internal methods
    private void initializePlayer() {

        TrackSelection.Factory trackSelectionFactory;
        trackSelectionFactory = new AdaptiveTrackSelection.Factory();


        @DefaultRenderersFactory.ExtensionRendererMode int extensionRendererMode =
                ((MyApplication) getActivity().getApplication()).useExtensionRenderers()
                        ? (true ? DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
                        : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON)
                        : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF;

        DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(mContext, null,
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

        player = ExoPlayerFactory.newSimpleInstance(/* context= */ mContext, renderersFactory, trackSelector, defaultLoadControl);
        player.addListener(new PlayerEventListener());
        player.setPlayWhenReady(startAutoPlay);
        player.addAnalyticsListener(new EventLogger(trackSelector));
        playerView.setPlayer(player);


        mediaSource = buildMediaSource(Uri.parse("https://baappvideo.s3.ap-south-1.amazonaws.com/appvideo.mp4")); // videoUrlStr
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
        return ((MyApplication) getActivity().getApplication()).buildDataSourceFactory();
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
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    private class PlayerEventListener implements Player.EventListener {

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            switch (playbackState) {
                case ExoPlayer.STATE_READY:
                    progressBar.setVisibility(View.GONE);
                    fragmentHomeBinding.volmueLinear.setVisibility(View.VISIBLE);
                    fragmentHomeBinding.fullScreenButton.setVisibility(View.VISIBLE);
                    break;
                case ExoPlayer.STATE_BUFFERING:
                    progressBar.setVisibility(View.VISIBLE);
                    fragmentHomeBinding.volmueLinear.setVisibility(View.GONE);
                    fragmentHomeBinding.fullScreenButton.setVisibility(View.GONE);
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


//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//
//        // Check if the fragment is an instance of the right fragment
//        if (fragment instanceof MyNFCFragment) {
//            MyNFCFragment my = (MyNFCFragment) fragment;
//            // Pass intent or its data to the fragment's method
//            my.processNFC(intent.getStringExtra());
//        }
//
//    }


    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || player == null) {
            fragmentHomeBinding.videoThumbnailImage.setVisibility(View.VISIBLE);
            fragmentHomeBinding.fullScreenButton.setVisibility(View.GONE);
            fragmentHomeBinding.volmueLinear.setVisibility(View.GONE);
            fragmentHomeBinding.frameLayoutMain.setVisibility(View.GONE);
        }
    }

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
            getActivity().finish();
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


}
