package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.util.Rational;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bharatarmy.Adapter.ExoVideoVerticalPlayerAdapter;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.Models.LoginDataModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.MyApplication;
import com.bharatarmy.Utility.Utils;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.PlaybackPreparer;
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
import com.google.android.exoplayer2.ui.DefaultTimeBar;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.TrackSelectionView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.util.ErrorMessageProvider;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class ExoVideoVerticalPlayerActivity extends AppCompatActivity implements View.OnClickListener, PlaybackPreparer, PlayerControlView.VisibilityListener {
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
    private FrameLayout frameLayout;
    private ImageView imgBwd;
    private ImageView exoPlay;
    private ImageView exoPause;
    private ImageView imgFwd;
    private TextView tvPlayerCurrentTime;
    private DefaultTimeBar exoProgress;
    private TextView tvPlayerEndTime;
    private ImageView imgSetting;
    private ImageView imgFullScreenEnterExit;
    private Handler handler;
    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;

    //    Screen other variable
    Context mContext;
    String videoUrlStr, videoNameStr, videoUserNameStr, whereToComeStr, videoLike, videoThumb;
    LinearLayout backImg, picturemode_linear, mBottomLayout, bottom_gradiant_line;
    RecyclerView related_vertical_video_rcyList;
    ShimmerFrameLayout shimmerFrameLayout;
    ExoVideoVerticalPlayerAdapter exoVideoPlayerAdapter;
    List<ImageDetailModel> videoDetailModelsList;
    String imageClickData, videoIdStr;
    AppBarLayout appbar;
    //    Toolbar toolbar;
    CollapsingToolbarLayout collapsing_toolbar;
    ImageView vertical_backimage;
    LoginDataModel postedDataList;

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

        setContentView(R.layout.activity_exo_video_vertical_player);
        mContext = ExoVideoVerticalPlayerActivity.this;

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

        setProgress();
    }


    public void init() {
        frameLayout = (FrameLayout) findViewById(R.id.frame_layout_main);
        tvPlaybackSpeed = (TextView) findViewById(R.id.tv_play_back_speed);
        tvPlaybackSpeed.setOnClickListener(this);
        tvPlaybackSpeed.setText("" + tapCount);
        tvPlaybackSpeedSymbol = (TextView) findViewById(R.id.tv_play_back_speed_symbol);
        tvPlaybackSpeedSymbol.setOnClickListener(this);
        imgBwd = (ImageView) findViewById(R.id.img_bwd);
        exoPlay = (ImageView) findViewById(R.id.exo_play);
        exoPause = (ImageView) findViewById(R.id.exo_pause);
        imgFwd = (ImageView) findViewById(R.id.img_fwd);
        tvPlayerCurrentTime = (TextView) findViewById(R.id.tv_player_current_time);
        exoProgress = (DefaultTimeBar) findViewById(R.id.exo_progress);
        tvPlayerEndTime = (TextView) findViewById(R.id.tv_player_end_time);
        imgSetting = (ImageView) findViewById(R.id.img_setting);
        imgFullScreenEnterExit = (ImageView) findViewById(R.id.img_full_screen_enter_exit);
        imgFullScreenEnterExit.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.loading);
        playerView = findViewById(R.id.player_view);
        /* other controller */
        backImg = (LinearLayout) findViewById(R.id.back_img);
        related_vertical_video_rcyList = (RecyclerView) findViewById(R.id.related_vertical_video_rcyList);
        shimmerFrameLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        picturemode_linear = (LinearLayout) findViewById(R.id.picturemode_linear);
        mBottomLayout = (LinearLayout) findViewById(R.id.bottom_layout);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        collapsing_toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        bottom_gradiant_line = (LinearLayout) findViewById(R.id.bottom_gradiant_line);
        vertical_backimage = (ImageView) findViewById(R.id.vertical_backimage);

        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmerAnimation();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            picturemode_linear.setVisibility(View.VISIBLE);
        } else {
            picturemode_linear.setVisibility(View.GONE);
        }
        stopScroll();

        /* Get the Related video information*/
        videoUrlStr = getIntent().getStringExtra("videoData");
        videoNameStr = getIntent().getStringExtra("videoName");
        videoUserNameStr = getIntent().getStringExtra("videoUserName");
        whereToComeStr = getIntent().getStringExtra("WhereToVideoCome");
        videoLike = getIntent().getStringExtra("videoLike");
        videoIdStr = getIntent().getStringExtra("videoId");
        videoThumb = getIntent().getStringExtra("videoThumb");
        AppConfiguration.videoThumbStr = videoThumb;

        callRelatedVideoGalleryData();
    }

    public void setListiner() {
        imgSetting.setOnClickListener(this);
        playerView.setControllerVisibilityListener(this);
        playerView.setErrorMessageProvider(new PlayerErrorMessageProvider());
        playerView.requestFocus();

        backImg.setOnClickListener(this);
        picturemode_linear.setOnClickListener(this);
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_setting) {
            MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
            if (mappedTrackInfo != null) {

//                int rendererType = mappedTrackInfo.getRendererType(2);
//                boolean allowAdaptiveSelections =
//                        rendererType == C.TRACK_TYPE_VIDEO
//                                || (rendererType == C.TRACK_TYPE_AUDIO
//                                && mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO)
//                                == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_NO_TRACKS);
                Pair<AlertDialog, TrackSelectionView> dialogPair =
                        TrackSelectionView.getDialog(this, "Select Video Resolution", trackSelector, 0);
                dialogPair.second.setShowDisableOption(false);
                dialogPair.second.setAllowAdaptiveSelections(false);
                dialogPair.first.show();
            }


        } else if (v.getId() == R.id.img_full_screen_enter_exit) {
          if (related_vertical_video_rcyList.isShown()){
              related_vertical_video_rcyList.setVisibility(View.GONE);
              bottom_gradiant_line.setVisibility(View.GONE);
              imgFullScreenEnterExit.setImageResource(R.drawable.ic_video_full_screen_exit);
              CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
              AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
              behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                  @Override
                  public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                      return false;
                  }
              });
              CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
              lp.height = CoordinatorLayout.LayoutParams.MATCH_PARENT;
            } else {
              imgFullScreenEnterExit.setImageResource(R.drawable.ic_video_full_screen_enter);
              related_vertical_video_rcyList.setVisibility(View.VISIBLE);
              bottom_gradiant_line.setVisibility(View.VISIBLE);
              float heightDp = (float) (getResources().getDisplayMetrics().heightPixels / 1.3);
              CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
              lp.height = (int) heightDp;
            }
        } else if (v.getId() == R.id.tv_play_back_speed || v.getId() == R.id.tv_play_back_speed_symbol) {
            if (tvPlaybackSpeed.getText().equals("1")) {
                tapCount++;
                PlaybackParameters param = new PlaybackParameters(2f);
                player.setPlaybackParameters(param);
                tvPlaybackSpeed.setText("" + 2);
            } else if (tvPlaybackSpeed.getText().equals("2")) {
                tapCount++;
                PlaybackParameters param = new PlaybackParameters(3f);
                player.setPlaybackParameters(param);
                tvPlaybackSpeed.setText("" + 3);

            } else if (tvPlaybackSpeed.getText().equals("3")) {
                tapCount++;
                PlaybackParameters param = new PlaybackParameters(4f);
                player.setPlaybackParameters(param);
                tvPlaybackSpeed.setText("" + 4);
            } else if (tvPlaybackSpeed.getText().equals("4")) {
                tapCount++;
                PlaybackParameters param = new PlaybackParameters(5f);
                player.setPlaybackParameters(param);
                tvPlaybackSpeed.setText("" + 5);
            } else {
                tapCount = 0;
                player.setPlaybackParameters(null);
                tvPlaybackSpeed.setText("" + 1);

            }
        } else if (v.getId() == R.id.back_img) {
            ExoVideoVerticalPlayerActivity.this.finish();
        } else if (v.getId() == R.id.picturemode_linear) {
            pictureInPictureMode();
        }
    }

    private void pictureInPictureMode() {

        backImg.setVisibility(View.GONE);

        Rational aspectRatio = new Rational(appbar.getWidth(), appbar.getHeight());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            PictureInPictureParams.Builder pictureInPictureParamsBuilder =
                    new PictureInPictureParams.Builder();
            pictureInPictureParamsBuilder.setAspectRatio(aspectRatio).build();
            enterPictureInPictureMode(pictureInPictureParamsBuilder.build());
        }

    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
//        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
        if (isInPictureInPictureMode) {
            picturemode_linear.setVisibility(View.GONE);
            backImg.setVisibility(View.GONE);
            related_vertical_video_rcyList.setVisibility(View.GONE);
        } else {
            picturemode_linear.setVisibility(View.VISIBLE);
            backImg.setVisibility(View.VISIBLE);
            related_vertical_video_rcyList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void preparePlayback() {
        initializePlayer();
    }

    @Override
    public void onVisibilityChange(int visibility) {

    }

    private void setProgress() {


        handler = new Handler();
        //Make sure you update Seekbar on UI thread
        handler.post(new Runnable() {

            @Override
            public void run() {
                if (player != null) {
                    tvPlayerCurrentTime.setText(stringForTime((int) player.getCurrentPosition()));
                    tvPlayerEndTime.setText(stringForTime((int) player.getDuration()));

                    handler.postDelayed(this, 1000);
                }
            }
        });
    }

    @Override
    public void onNewIntent(Intent intent) {
        releasePlayer();
        releaseAdsLoader();
        clearStartPosition();
        setIntent(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
            if (playerView != null) {
                playerView.onResume();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer();
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

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // See whether the player view wants to handle media or DPAD keys events.
        return playerView.dispatchKeyEvent(event) || super.dispatchKeyEvent(event);
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

        player = ExoPlayerFactory.newSimpleInstance(/* context= */ ExoVideoVerticalPlayerActivity.this, renderersFactory, trackSelector, defaultLoadControl);
        player.addListener(new PlayerEventListener());
        player.setPlayWhenReady(startAutoPlay);
        player.addAnalyticsListener(new EventLogger(trackSelector));
        playerView.setPlayer(player);
        playerView.setPlaybackPreparer(this);

        mediaSource = buildMediaSource(Uri.parse(videoUrlStr));
//        https://baappvideo.s3.ap-south-1.amazonaws.com/74425094_140387590620474_1342703700878427324_n.mp4
//                https://www.bharatarmy.com//Docs/74425094_140387590620474_1342703700878427324_n.mp4
        player.prepare(mediaSource);
        updateButtonVisibilities();
        initBwd();
        initFwd();
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

    private void initBwd() {
        imgBwd.requestFocus();
        imgBwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.seekTo(player.getCurrentPosition() - 10000);
            }
        });
    }

    private void initFwd() {
        imgFwd.requestFocus();
        imgFwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.seekTo(player.getCurrentPosition() + 10000);
            }
        });

    }

    private String stringForTime(int timeMs) {
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
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

    // Api calling GetVideoGalleryData
    public void callRelatedVideoGalleryData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), ExoVideoVerticalPlayerActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getBARelatedVideos(getVideoGalleryData(), new retrofit.Callback<ImageMainModel>() {
            @Override
            public void success(ImageMainModel relatedVerticalVideoModel, Response response) {
                Utils.dismissDialog();
                if (relatedVerticalVideoModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (relatedVerticalVideoModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (relatedVerticalVideoModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (relatedVerticalVideoModel.getIsValid() == 1) {

                    if (relatedVerticalVideoModel.getData() != null) {
                        videoDetailModelsList = relatedVerticalVideoModel.getData();

                        callPostedViewData();

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

    private Map<String, String> getVideoGalleryData() {
        Map<String, String> map = new HashMap<>();
        map.put("CurrentVideoId", videoIdStr);
        map.put("PageIndex", "0");
        map.put("PageSize", "20");
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));

        return map;
    }

    public void fillVideoDetailGallery() {
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) collapsing_toolbar.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        exoVideoPlayerAdapter = new ExoVideoVerticalPlayerAdapter(mContext, ExoVideoVerticalPlayerActivity.this, videoDetailModelsList,
                videoNameStr, videoUserNameStr, videoLike, postedDataList, videoIdStr);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        related_vertical_video_rcyList.setLayoutManager(mLayoutManager);
        related_vertical_video_rcyList.setItemAnimator(new DefaultItemAnimator());
        related_vertical_video_rcyList.setAdapter(exoVideoPlayerAdapter);

    }


    // Api calling GetVideoGalleryData
    public void callPostedViewData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), ExoVideoVerticalPlayerActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getBAPostStatistics(getPostedData(), new retrofit.Callback<LogginModel>() {
            @Override
            public void success(LogginModel postedDataModel, Response response) {
                Utils.dismissDialog();
                if (postedDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (postedDataModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (postedDataModel.getIsValid() == 0) {
//                    Utils.ping(mContext, getString(R.string.false_msg));
                    postedDataList = postedDataModel.getData();
                    shimmerFrameLayout.stopShimmerAnimation();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    related_vertical_video_rcyList.setVisibility(View.VISIBLE);

                    fillVideoDetailGallery();
                    return;
                }
                if (postedDataModel.getIsValid() == 1) {

                    if (postedDataModel.getData() != null) {

                        postedDataList = postedDataModel.getData();
                        shimmerFrameLayout.stopShimmerAnimation();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        related_vertical_video_rcyList.setVisibility(View.VISIBLE);
                        startScroll();

                        fillVideoDetailGallery();
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

    private Map<String, String> getPostedData() {
        Map<String, String> map = new HashMap<>();
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));
        map.put("PostId", videoIdStr);
        map.put("SourceType", "2");

        return map;
    }

    public void stopScroll() {
        AppBarLayout.LayoutParams toolbarLayoutParams = (AppBarLayout.LayoutParams) collapsing_toolbar.getLayoutParams();
        toolbarLayoutParams.setScrollFlags(0);
        collapsing_toolbar.setLayoutParams(toolbarLayoutParams);

        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        appBarLayoutParams.setBehavior(null);
        appbar.setLayoutParams(appBarLayoutParams);
    }

    public void startScroll() {
        AppBarLayout.LayoutParams toolbarLayoutParams = (AppBarLayout.LayoutParams) collapsing_toolbar.getLayoutParams();
        toolbarLayoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        collapsing_toolbar.setLayoutParams(toolbarLayoutParams);

        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        appBarLayoutParams.setBehavior(new AppBarLayout.Behavior());
        appbar.setLayoutParams(appBarLayoutParams);
    }

    @Override
    public void onBackPressed() {
        ExoVideoVerticalPlayerActivity.this.finish();
    }
}
