package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bharatarmy.Adapter.StadiumDetailRelatedMatchesAdapter;
import com.bharatarmy.LinePagerIndicatorDecoration;
import com.bharatarmy.Models.HomeTemplateDetailModel;
import com.bharatarmy.Models.TravelDataModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.MyApplication;
import com.bharatarmy.Utility.SnapHelperOneByOne;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.Utility.WebServices;
import com.bharatarmy.databinding.ActivityTravelMatchStadiumDetailBinding;
import com.bharatarmy.databinding.DetailPageGalleryPagerListItemBinding;
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

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import me.relex.circleindicator.CircleIndicator2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TravelMatchStadiumDetailActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTravelMatchStadiumDetailBinding activityTravelMatchStadiumDetailBinding;
    Context mContext;
    /*Stadium Detail variable*/
    String stadiumNameStr, stadiumDescriptionStr;

    /*    stadium inclusion list and adapter*/
    ArrayList<TravelModel> travelStadiumInclusionList;

    /* stadium related other matches*/
    StadiumDetailRelatedMatchesAdapter stadiumDetailRelatedMatchesAdapter;
    List<HomeTemplateDetailModel> travelStadiumOtherMatchesList;

    /*Use for stadium gallery*/
//    private TravelMatchStadiumDetailActivity.MyStadiumDetailGalleryPagerAdapter myStadiumDetailGalleryPagerAdapter;
    //    DetailPageGalleryPagerListItemBinding detailPageGalleryPagerListItemBinding;
    private TextView[] dots;
    ArrayList<TravelModel> stadiumDetailGalleryList;
    TravelMatchStadiumGalleryAdapter travelMatchStadiumGalleryAdapter;
    LinearLayoutManager linearLayoutManager;

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
    private LinearLayout volumeLinear;
    private FrameLayout mainframeLayout;
    private AppCompatImageView videoThumbnailImage;
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

        activityTravelMatchStadiumDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_match_stadium_detail);
        mContext = TravelMatchStadiumDetailActivity.this;
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
        stadiumNameStr = getIntent().getStringExtra("stadiumName");
        stadiumDescriptionStr = getIntent().getStringExtra("stadiumDescription");
        activityTravelMatchStadiumDetailBinding.shimmerViewContainerMain.startShimmerAnimation();
        activityTravelMatchStadiumDetailBinding.mainDetailLinear.setVisibility(View.GONE);

        //        stadium gallery List
        stadiumDetailGalleryList = new ArrayList<TravelModel>();
        stadiumDetailGalleryList.add(new TravelModel("https://cdn.shopify.com/s/files/1/0031/6656/8493/files/Venue-Perth-Banner.png?v=1559025136", "Image"));
        stadiumDetailGalleryList.add(new TravelModel("https://www.cricket.com.au/~/-/media/Brightcove/Cricket%20Australia/2017/11/21/New-Perth-Stadium-still.ashx?w=1600?w=1600&h=1200", "Image"));
        stadiumDetailGalleryList.add(new TravelModel("https://i.ytimg.com/vi/kd-qKUXcjY0/maxresdefault.jpg", "Image"));
        stadiumDetailGalleryList.add(new TravelModel("https://upload.wikimedia.org/wikipedia/commons/thumb/c/c6/Rose_bowl3.JPG/300px-Rose_bowl3.JPG", "Image"));
        stadiumDetailGalleryList.add(new TravelModel("https://baappvideo.s3.ap-south-1.amazonaws.com/appvideo.mp4", "Video"));
        stadiumDetailGalleryList.add(new TravelModel("https://images.immediate.co.uk/production/volatile/sites/3/2019/05/GettyImages-809641044-3d448c5.jpg?quality=45&resize=620,413", "Image"));
        stadiumDetailGalleryList.add(new TravelModel("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTOoK5jxwSYxPaujnLqPAFoEBH2yOOpWWVxQ2Qlom1pGQqCttwq", "Image"));


        travelMatchStadiumGalleryAdapter = new TravelMatchStadiumGalleryAdapter(mContext, stadiumDetailGalleryList);
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        activityTravelMatchStadiumDetailBinding.stadiumDetailGalleryViewpager.setLayoutManager(linearLayoutManager);
        activityTravelMatchStadiumDetailBinding.stadiumDetailGalleryViewpager.setAdapter(travelMatchStadiumGalleryAdapter);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(activityTravelMatchStadiumDetailBinding.stadiumDetailGalleryViewpager);

        CircleIndicator2 indicator = activityTravelMatchStadiumDetailBinding.indicator;
        indicator.attachToRecyclerView(activityTravelMatchStadiumDetailBinding.stadiumDetailGalleryViewpager, pagerSnapHelper);
        GetStadiumDetailListData();
    }

    public void setListiner() {
        setSupportActionBar(activityTravelMatchStadiumDetailBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        activityTravelMatchStadiumDetailBinding.backImg.setOnClickListener(this);
        activityTravelMatchStadiumDetailBinding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
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
                    activityTravelMatchStadiumDetailBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.heading_bg));
                    Typeface typeface = ResourcesCompat.getFont(mContext, R.font.helveticaneueltstdbdcn);
                    activityTravelMatchStadiumDetailBinding.collapsingToolbar.setCollapsedTitleTypeface(typeface);
                    activityTravelMatchStadiumDetailBinding.collapsingToolbar.setExpandedTitleTypeface(typeface);
                    activityTravelMatchStadiumDetailBinding.collapsingToolbar.setCollapsedTitleGravity(Gravity.START);
                    activityTravelMatchStadiumDetailBinding.collapsingToolbar.setExpandedTitleGravity(Gravity.START);
                    activityTravelMatchStadiumDetailBinding.toolbarHeaderLinear.setVisibility(View.VISIBLE);

                    isShow = true;
                } else if (isShow) {
                    activityTravelMatchStadiumDetailBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                    activityTravelMatchStadiumDetailBinding.collapsingToolbar.setTitle(" ");
                    activityTravelMatchStadiumDetailBinding.toolbarHeaderLinear.setVisibility(View.GONE);
                    isShow = false;
                }

            }
        });
        activityTravelMatchStadiumDetailBinding.stadiumDetailGalleryViewpager.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (playerView != null) {
                    if (player != null) {
                        if (isPlaying()) {
                            player.setPlayWhenReady(false);
                        } else if (player.getPlaybackState() == Player.STATE_ENDED) {
                            player.seekTo(0);
                            player.setPlayWhenReady(false);
                        } else if(player.getPlaybackState() == Player.STATE_BUFFERING){
                            player.seekTo(0);
                            player.setPlayWhenReady(false);
                        }
                    }

                }
            }
        });

    }

    // Api calling GetStadiumDetailListData
    public void GetStadiumDetailListData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), TravelMatchStadiumDetailActivity.this);
            return;

        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfiguration.BASEURL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        WebServices api = retrofit.create(WebServices.class);

        Call<TravelDataModel> call = api.getHospitalityList("http://www.mocky.io/v2/5e53b9282e00007c002dae2b");

        call.enqueue(new Callback<TravelDataModel>() {
            @Override
            public void onResponse(Call<TravelDataModel> call, retrofit2.Response<TravelDataModel> response) {

                if (response.body().getFixturesData() != null && response.body().getRelatedHospitalityData() != null) {
                    travelStadiumOtherMatchesList = response.body().getFixturesData();
                    setDataList();
                }
            }

            @Override
            public void onFailure(Call<TravelDataModel> call, Throwable t) {
                Log.d("WatchList Error:", t.getLocalizedMessage());
            }
        });


    }

    public void setDataList() {
        activityTravelMatchStadiumDetailBinding.shimmerViewContainerMain.stopShimmerAnimation();
        activityTravelMatchStadiumDetailBinding.shimmerViewContainerMain.setVisibility(View.GONE);

//        activityTravelMatchStadiumDetailBinding.viewPagerDotlinear.setVisibility(View.VISIBLE);
        activityTravelMatchStadiumDetailBinding.mainDetailLinear.setVisibility(View.VISIBLE);
        activityTravelMatchStadiumDetailBinding.relatedStadiumMatchesRcv.setVisibility(View.VISIBLE);

        activityTravelMatchStadiumDetailBinding.stadiumNameTxt.setText(stadiumNameStr);
        activityTravelMatchStadiumDetailBinding.stadiumDescTxt.setText(stadiumDescriptionStr);


        //    inclusion list
        travelStadiumInclusionList = new ArrayList<TravelModel>();
        travelStadiumInclusionList.add(new TravelModel(1, "Live entertainment throughout the day"));
        travelStadiumInclusionList.add(new TravelModel(1, "Extensive menu with in room live chef sation"));
        travelStadiumInclusionList.add(new TravelModel(1, "5 hour premium beverage package"));
        travelStadiumInclusionList.add(new TravelModel(1, "Premium seating"));
        travelStadiumInclusionList.add(new TravelModel(1, "Access to win money can't buy experiences"));


        for (int i = 0; i < travelStadiumInclusionList.size(); i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.hospitality_inclusion_list, null);

            TextView txt = (TextView) view.findViewById(R.id.inclusion_txt);

            txt.setText(travelStadiumInclusionList.get(i).getMatchteamVenues());

            activityTravelMatchStadiumDetailBinding.inclusionLinear.addView(view);
        }

        /*OtherMatch list*/

        activityTravelMatchStadiumDetailBinding.relatedStadiumMatchesRcv.setVisibility(View.VISIBLE);
        stadiumDetailRelatedMatchesAdapter = new StadiumDetailRelatedMatchesAdapter(mContext, travelStadiumOtherMatchesList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        activityTravelMatchStadiumDetailBinding.relatedStadiumMatchesRcv.setLayoutManager(layoutManager);
        activityTravelMatchStadiumDetailBinding.relatedStadiumMatchesRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchStadiumDetailBinding.relatedStadiumMatchesRcv.setAdapter(stadiumDetailRelatedMatchesAdapter);

    }


    @Override

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                TravelMatchStadiumDetailActivity.this.finish();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                TravelMatchStadiumDetailActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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

    public boolean isPlaying() {
        return player.getPlaybackState() == Player.STATE_READY && player.getPlayWhenReady();
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
                    if (player.getPlayWhenReady() == true){
                        volumeLinear.setVisibility(View.VISIBLE);
                    }else if (player.getPlayWhenReady() == false){
                        volumeLinear.setVisibility(View.GONE);
                    }
                    break;
                case ExoPlayer.STATE_BUFFERING:
                    progressBar.setVisibility(View.VISIBLE);
                    volumeLinear.setVisibility(View.GONE);
                    break;
                case ExoPlayer.STATE_ENDED:
                    if (player.getPlayWhenReady() == true){
                        volumeLinear.setVisibility(View.VISIBLE);
                    }else if (player.getPlayWhenReady() == false){
                        volumeLinear.setVisibility(View.GONE);
                    }
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
    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || player == null) {
            if (playerView != null) {
                mainframeLayout.setVisibility(View.GONE);
                volumeLinear.setVisibility(View.GONE);
                videoThumbnailImage.setVisibility(View.VISIBLE);
//            initializePlayer();
            }
            if (playerView != null) {
                playerView.onResume();
            }
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

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        // See whether the player view wants to handle media or DPAD keys events.
//        return playerView.dispatchKeyEvent(event) || super.dispatchKeyEvent(event);
//    }


    /*Stadium detail gallery adapter*/  // https://github.com/ongakuer/CircleIndicator
    public class TravelMatchStadiumGalleryAdapter extends RecyclerView.Adapter<TravelMatchStadiumGalleryAdapter.MyViewHolder> {
        Context mContext;
        ArrayList<TravelModel> stadiumDetailGalleryList;

        public TravelMatchStadiumGalleryAdapter(Context mContext, ArrayList<TravelModel> stadiumDetailGalleryList) {
            this.mContext = mContext;
            this.stadiumDetailGalleryList = stadiumDetailGalleryList;
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            DetailPageGalleryPagerListItemBinding detailPageGalleryPagerListItemBinding;

            public MyViewHolder(DetailPageGalleryPagerListItemBinding detailPageGalleryPagerListItemBinding) {
                super(detailPageGalleryPagerListItemBinding.getRoot());
                this.detailPageGalleryPagerListItemBinding = detailPageGalleryPagerListItemBinding;

            }
        }


        @Override
        public TravelMatchStadiumGalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            DetailPageGalleryPagerListItemBinding detailPageGalleryPagerListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.detail_page_gallery_pager_list_item, parent, false);
            return new TravelMatchStadiumGalleryAdapter.MyViewHolder(detailPageGalleryPagerListItemBinding);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(TravelMatchStadiumGalleryAdapter.MyViewHolder holder, int position) {
            exoPlay = (ImageView) findViewById(R.id.exo_play);
            AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
            audioManager.adjustVolume(AudioManager.ADJUST_MUTE, AudioManager.ADJUST_MUTE);

            if (stadiumDetailGalleryList.get(position).getCityHotelAmenitiesName().equalsIgnoreCase("Image")) {
                holder.detailPageGalleryPagerListItemBinding.detailGalleryImage.setVisibility(View.VISIBLE);
                holder.detailPageGalleryPagerListItemBinding.baVideoRlv.setVisibility(View.GONE);
                holder.detailPageGalleryPagerListItemBinding.volumeLinear.setVisibility(View.GONE);
                Utils.setImageInImageView(stadiumDetailGalleryList.get(position).getCityHotelAmenitiesImage(), holder.detailPageGalleryPagerListItemBinding.detailGalleryImage, mContext);

                Log.d("HotelGalleeryAdapter : ", stadiumDetailGalleryList.get(position).getCityHotelAmenitiesImage());
            } else {
                holder.detailPageGalleryPagerListItemBinding.detailGalleryImage.setVisibility(View.GONE);
                holder.detailPageGalleryPagerListItemBinding.baVideoRlv.setVisibility(View.VISIBLE);

                Utils.setImageInImageView("http://devenv.bharatarmy.com//Docs/Media/Thumb/3b484b79-ad6f-4db2-838a-478b117fabf7-Thumb_20200210_BA121034.jpg", holder.detailPageGalleryPagerListItemBinding.videoThumbnailImage, mContext);
                videopathStr = stadiumDetailGalleryList.get(position).getCityHotelAmenitiesImage();

                holder.detailPageGalleryPagerListItemBinding.videoPlayImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.detailPageGalleryPagerListItemBinding.videoThumbnailImage.setVisibility(View.GONE);
                        holder.detailPageGalleryPagerListItemBinding.frameLayoutMain.setVisibility(View.VISIBLE);
                        holder.detailPageGalleryPagerListItemBinding.loading.setVisibility(View.VISIBLE);

                        holder.detailPageGalleryPagerListItemBinding.sliderPlayerView.setVisibility(View.VISIBLE);
                        videoThumbnailImage = holder.detailPageGalleryPagerListItemBinding.videoThumbnailImage;
                        mainframeLayout = holder.detailPageGalleryPagerListItemBinding.frameLayoutMain;
                        volumeLinear = holder.detailPageGalleryPagerListItemBinding.volumeLinear;
                        playerView = holder.detailPageGalleryPagerListItemBinding.sliderPlayerView;
                        progressBar = holder.detailPageGalleryPagerListItemBinding.loading;
                        initializePlayer();
                    }
                });
                    holder.detailPageGalleryPagerListItemBinding.volumeLinear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (holder.detailPageGalleryPagerListItemBinding.volmueVideoButton.isShown()) {
                                holder.detailPageGalleryPagerListItemBinding.volmueVideoButton.setVisibility(View.GONE);
                                holder.detailPageGalleryPagerListItemBinding.muteVideoButton.setVisibility(View.VISIBLE);
                                AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
                                audioManager.adjustVolume(AudioManager.ADJUST_MUTE, AudioManager.ADJUST_MUTE);
                            } else {
                                holder.detailPageGalleryPagerListItemBinding.volmueVideoButton.setVisibility(View.VISIBLE);
                                holder.detailPageGalleryPagerListItemBinding.muteVideoButton.setVisibility(View.GONE);
                                AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
                                audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
                            }
                        }
                    });
                holder.detailPageGalleryPagerListItemBinding.muteVideoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });
                holder.detailPageGalleryPagerListItemBinding.volmueVideoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }


        @Override
        public long getItemId(int position) {
// return specific item's id here
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return stadiumDetailGalleryList.size();
        }


    }


}
