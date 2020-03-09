package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvasilkov.gestures.views.GestureImageView;
import com.bharatarmy.Adapter.AlbumImageVideoAdapter;
import com.bharatarmy.Adapter.ExoVideoVerticalPlayerAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.LoginDataModel;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.R;
import com.bharatarmy.TargetCallback;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.MyApplication;
import com.bharatarmy.Utility.SnapHelperOneByOne;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityAlbumImageVideoShowBinding;
import com.bharatarmy.databinding.AlbumImageVideoListItemBinding;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import static androidx.core.content.FileProvider.getUriForFile;

public class AlbumImageVideoShowActivity extends AppCompatActivity implements View.OnClickListener {
    //    Screen other variable
    ActivityAlbumImageVideoShowBinding activityAlbumImageVideoShowBinding;
    Context mContext;
    ArrayList<String> albumImageUrl = new ArrayList<>();
    ArrayList<String> albumImageThumbUrl = new ArrayList<>();
    ArrayList<String> albumMediaType = new ArrayList<>();
    ArrayList<String> albumLike = new ArrayList<>();
    ArrayList<String> albumDuration = new ArrayList<>();
    ArrayList<String> albumId = new ArrayList<>();
    ArrayList<String> albumAddUser = new ArrayList<>();
    ArrayList<String> albumImageViews = new ArrayList<>();

    AlbumImageVideoAdapter albumImageVideoAdapter;
    LinearLayoutManager linearLayoutManager;
    String selectedItem;
    int positon;
    String imageUriStr = "";
    String videoUrlStr = "", selectedmediaTypeStr = "";

    int showPositionImage;
    Uri uri;

    /*Video player in Adapter controller*/
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
    DataSource.Factory dataSourceFactory;
    SimpleExoPlayer player;
    FrameworkMediaDrm mediaDrm;
    MediaSource mediaSource;
    DefaultTrackSelector trackSelector;
    DefaultTrackSelector.Parameters trackSelectorParameters;
    TrackGroupArray lastSeenTrackGroupArray;
    TextView tvPlaybackSpeed, tvPlaybackSpeedSymbol;
    boolean startAutoPlay;
    int startWindow;

    ImageView imgBwd;
    ImageView exoPlay;
    ImageView exoPause;
    ImageView imgFwd;
    TextView tvPlayerCurrentTime;
    DefaultTimeBar exoProgress;
    TextView tvPlayerEndTime;
    ImageView imgSetting;
    ImageView imgFullScreenEnterExit;
    // Fields used only for ad playback. The ads loader is loaded via reflection.
    private long startPosition;
    private AdsLoader adsLoader;
    private Uri loadedAdTagUri;

    private Handler handler;
    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;
    String playVideoPathStr;

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
        activityAlbumImageVideoShowBinding = DataBindingUtil.setContentView(this, R.layout.activity_album_image_video_show);

        mContext = AlbumImageVideoShowActivity.this;
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
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

        final Bundle stringArrayList = getIntent().getExtras();
        albumImageUrl = stringArrayList.getStringArrayList("AlbumUrldata");
        albumImageThumbUrl = stringArrayList.getStringArrayList("AlbumThumbUrldata");
        albumMediaType = stringArrayList.getStringArrayList("AlbumMediaType");
        albumLike = stringArrayList.getStringArrayList("AlbumLike");
        albumImageViews = stringArrayList.getStringArrayList("AlbumViews");
        albumDuration = stringArrayList.getStringArrayList("AlbumDuration");
        albumAddUser = stringArrayList.getStringArrayList("AlbumAddUser");
        albumId = stringArrayList.getStringArrayList("AlbumId");
        selectedItem = getIntent().getStringExtra("positon");

        setImageandVideo();


    }


    public void setListiner() {
        activityAlbumImageVideoShowBinding.backImg.setOnClickListener(this);

    }


    public void setImageandVideo() {

        activityAlbumImageVideoShowBinding.albumDetailRcvList.setVisibility(View.VISIBLE);

        for (int i = 0; i < albumImageUrl.size(); i++) {
            if (selectedItem.equalsIgnoreCase(albumImageUrl.get(i))) {
                positon = i;
            }
        }


        albumImageVideoAdapter = new AlbumImageVideoAdapter(mContext, AlbumImageVideoShowActivity.this, albumImageUrl,
                albumImageThumbUrl, albumMediaType, albumLike, albumImageViews, albumDuration, albumAddUser, albumId, new image_click() {
            @Override
            public void image_more_click() {
                Dexter.withActivity(AlbumImageVideoShowActivity.this)
                        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    selectedmediaTypeStr = albumImageVideoAdapter.MediaTypeId();
                                    videoUrlStr = albumImageVideoAdapter.VideoUrlStr();
                                    if (selectedmediaTypeStr.equalsIgnoreCase("1")) {
                                        shareImage();
                                    } else {
                                        shareVideo();
                                    }

                                }

                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    showSettingsDialog();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();

            }
        }/*, new PlayViedoCallback() {
            @Override
            public void play_video_callback() {
                videoUrlStr = albumImageVideoAdapter.VideoUrlStr();

            }
        }*/);
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(activityAlbumImageVideoShowBinding.albumDetailRcvList);

        activityAlbumImageVideoShowBinding.albumDetailRcvList.setLayoutManager(linearLayoutManager);
        activityAlbumImageVideoShowBinding.albumDetailRcvList.getLayoutManager().scrollToPosition(positon);

        activityAlbumImageVideoShowBinding.albumDetailRcvList.setItemAnimator(new DefaultItemAnimator());
        activityAlbumImageVideoShowBinding.albumDetailRcvList.setAdapter(albumImageVideoAdapter);
    }


    public void shareImage() {
        showPositionImage = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        for (int i = 0; i < albumImageUrl.size(); i++) {
            if (showPositionImage == i) {
                imageUriStr = albumImageUrl.get(showPositionImage);
                break;
            }
        }
        Log.d("imageUriStr :", imageUriStr);
        //Use for Internal Storage file

        File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "BharatArmy");
        if (!myDir.exists()) myDir.mkdirs();
        String fname = "IMG_" + Utils.imagesaveDate() + "_BA" + Utils.imagesavetime() + ".jpg";
        File file = new File(myDir, fname); //Utils.camerafilesavepath()
        Log.i("file", "" + file);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            Utils.StringToBitMap(imageUriStr).compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //share image from other application
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, AppConfiguration.SHARETEXT);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri = getUriForFile(mContext, getPackageName() + ".provider", file));
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Share It"));
    }

    public void shareVideo() {

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, AppConfiguration.SHARETEXT);
        shareIntent.putExtra(Intent.EXTRA_TEXT, videoUrlStr + "\n\n" + AppConfiguration.SHARETEXT);
        shareIntent.setType("text/plain");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Share It"));

    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AlbumImageVideoShowActivity.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_img) {
            AlbumImageVideoShowActivity.this.finish();
        }
    }

    /*Image and video play adapter*/
    public class AlbumImageVideoAdapter extends RecyclerView.Adapter<AlbumImageVideoAdapter.MyViewHolder> {
        Context mContext;

        private int lastPosition = -1;

        ArrayList<String> albumImageUrl;
        ArrayList<String> albumImageThumbUrl;
        ArrayList<String> albumMediaType;
        MediaController mediaController;
        ArrayList<String> albumLike;
        ArrayList<String> albumImageViews;
        ArrayList<String> albumDuration;
        ArrayList<String> albumAddUser;
        ArrayList<String> albumId;
        Activity activity;
        image_click imageClick;
        private Animation fadein, fadeout;
        String mediaTypeStr, galleryVideoStr;


        public AlbumImageVideoAdapter(Context mContext, Activity activity, ArrayList<String> albumImageUrl, ArrayList<String> albumImageThumbUrl,
                                      ArrayList<String> albumMediaType, ArrayList<String> albumLike, ArrayList<String> albumImageViews,
                                      ArrayList<String> albumDuration, ArrayList<String> albumAddUser, ArrayList<String> albumId,
                                      image_click imageClick) {

            this.mContext = mContext;
            this.albumImageUrl = albumImageUrl;
            this.albumImageThumbUrl = albumImageThumbUrl;
            this.albumMediaType = albumMediaType;
            this.albumLike = albumLike;
            this.albumImageViews = albumImageViews;
            this.albumDuration = albumDuration;
            this.albumAddUser = albumAddUser;
            this.albumId = albumId;
            this.activity = activity;
            this.imageClick = imageClick;
        }



        public class MyViewHolder extends RecyclerView.ViewHolder {

            AlbumImageVideoListItemBinding albumImageVideoListItemBinding;

            public MyViewHolder(AlbumImageVideoListItemBinding albumImageVideoListItemBinding) {
                super(albumImageVideoListItemBinding.getRoot());
                this.albumImageVideoListItemBinding = albumImageVideoListItemBinding;

            }
        }


        @Override
        public AlbumImageVideoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            AlbumImageVideoListItemBinding albumImageVideoListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.album_image_video_list_item, parent, false);
            return new AlbumImageVideoAdapter.MyViewHolder(albumImageVideoListItemBinding);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onBindViewHolder(AlbumImageVideoAdapter.MyViewHolder holder, int position) {
            setAnimation(holder.itemView, position);
            fadein = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
            fadeout = AnimationUtils.loadAnimation(mContext, R.anim.fade_out);

            if (albumMediaType.get(position).equalsIgnoreCase("1")) {
                holder.albumImageVideoListItemBinding.showAlbumImage.setVisibility(View.VISIBLE);
                holder.albumImageVideoListItemBinding.baVideoRlv.setVisibility(View.GONE);
                Utils.setImageInImageView(albumImageUrl.get(position), holder.albumImageVideoListItemBinding.showAlbumImage, mContext);
                Picasso.with(mContext).load(albumImageUrl.get(position)).placeholder(R.drawable.loader_new)
                        .into(holder.albumImageVideoListItemBinding.showAlbumImage,
                                new TargetCallback(holder.albumImageVideoListItemBinding.showAlbumImage) {
                                    @Override
                                    public void onSuccess(ImageView target) {
                                        if (target != null) {
                                            holder.albumImageVideoListItemBinding.imageBottomLinear.setVisibility(View.VISIBLE);
                                            holder.albumImageVideoListItemBinding.imageBottomLinear.startAnimation(fadein);
                                            holder.albumImageVideoListItemBinding.showAlbumImage.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if (holder.albumImageVideoListItemBinding.imageBottomLinear.isShown()) {
                                                        holder.albumImageVideoListItemBinding.imageBottomLinear.setVisibility(View.GONE);
                                                        holder.albumImageVideoListItemBinding.imageBottomLinear.startAnimation(fadeout);
                                                    } else {
                                                        holder.albumImageVideoListItemBinding.imageBottomLinear.setVisibility(View.VISIBLE);
                                                        holder.albumImageVideoListItemBinding.imageBottomLinear.startAnimation(fadein);
                                                    }
                                                }
                                            });
                                        } else {
                                            holder.albumImageVideoListItemBinding.imageBottomLinear.setVisibility(View.GONE);
                                        }
                                    }

                                    @Override
                                    public void onError(ImageView target) {
                                        holder.albumImageVideoListItemBinding.imageBottomLinear.setVisibility(View.GONE);
                                    }
                                });


            } else if (albumMediaType.get(position).equalsIgnoreCase("2")) {
                holder.albumImageVideoListItemBinding.showAlbumImage.setVisibility(View.GONE);
                holder.albumImageVideoListItemBinding.baVideoRlv.setVisibility(View.VISIBLE);
                Utils.setImageInImageView(albumImageThumbUrl.get(position), holder.albumImageVideoListItemBinding.videoThumbnailImage, mContext);
                playVideoPathStr = albumImageUrl.get(position);


                holder.albumImageVideoListItemBinding.baVideoRlv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.albumImageVideoListItemBinding.videoThumbnailImage.setVisibility(View.GONE);
                        holder.albumImageVideoListItemBinding.frameLayoutMain.setVisibility(View.VISIBLE);
                        holder.albumImageVideoListItemBinding.loading.setVisibility(View.VISIBLE);
                        playerView = holder.albumImageVideoListItemBinding.playerView;
                        progressBar = holder.albumImageVideoListItemBinding.loading;
                        exoPlay = holder.albumImageVideoListItemBinding.exoPlay;
                        exoPause = holder.albumImageVideoListItemBinding.exoPause;

                        exoPlay.setVisibility(View.GONE);
                        exoPause.setVisibility(View.GONE);

                        initializePlayer();
//                        playerView.setControllerVisibilityListener(this);
                        playerView.setErrorMessageProvider(new PlayerErrorMessageProvider());
                        playerView.requestFocus();

                        holder.albumImageVideoListItemBinding.imageBottomLinear.setVisibility(View.VISIBLE);
                        holder.albumImageVideoListItemBinding.imageBottomLinear.startAnimation(fadein);
                        holder.albumImageVideoListItemBinding.playerView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (holder.albumImageVideoListItemBinding.imageBottomLinear.isShown()) {
                                    holder.albumImageVideoListItemBinding.imageBottomLinear.setVisibility(View.GONE);
                                    holder.albumImageVideoListItemBinding.imageBottomLinear.startAnimation(fadeout);
                                } else {
                                    holder.albumImageVideoListItemBinding.imageBottomLinear.setVisibility(View.VISIBLE);
                                    holder.albumImageVideoListItemBinding.imageBottomLinear.startAnimation(fadein);
                                }
                            }
                        });
                    }
                });
//                imgBwd = holder.albumImageVideoListItemBinding.imgBwd;
//                imgFwd = holder.albumImageVideoListItemBinding.imgFwd;
//                tvPlaybackSpeed = holder.albumImageVideoListItemBinding.tvPlayBackSpeed;
//                tvPlayerCurrentTime = holder.albumImageVideoListItemBinding.tvPlayerCurrentTime;
//                tvPlayerEndTime = holder.albumImageVideoListItemBinding.tvPlayerEndTime;
//                imgSetting = holder.albumImageVideoListItemBinding.imgSetting;
//                tvPlaybackSpeedSymbol = holder.albumImageVideoListItemBinding.tvPlayBackSpeedSymbol;
//                imgFullScreenEnterExit = holder.albumImageVideoListItemBinding.imgFullScreenEnterExit;
//
//                imgFullScreenEnterExit.setVisibility(View.GONE);
//                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)holder.albumImageVideoListItemBinding.imgSetting.getLayoutParams();
//                params.setMargins(5, 0, 20, 0); //substitute parameters for left, top, right, bottom
//                holder.albumImageVideoListItemBinding.imgSetting.setLayoutParams(params);
//                tvPlaybackSpeed.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        tvbackSpeedfuncation();
//                    }
//                });
//                tvPlaybackSpeed.setText("" + tapCount);
//                tvPlaybackSpeedSymbol.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        tvbackSpeedfuncation();
//                    }
//                });
//                imgSetting.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
//                        if (mappedTrackInfo != null) {
//
//                            Pair<android.app.AlertDialog, TrackSelectionView> dialogPair =
//                                    TrackSelectionView.getDialog(activity, "Select Video Resolution", trackSelector, 0);
//                            dialogPair.second.setShowDisableOption(false);
//                            dialogPair.second.setAllowAdaptiveSelections(false);
//                            dialogPair.first.show();
//                        }
//                    }
//                });
//
//                setProgress();
//                initializePlayer();



//                if (playerView !=null){
//                    holder.albumImageVideoListItemBinding.imageBottomLinear.setVisibility(View.VISIBLE);
//                    holder.albumImageVideoListItemBinding.imageBottomLinear.startAnimation(fadein);
//                    holder.albumImageVideoListItemBinding.playerView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (holder.albumImageVideoListItemBinding.imageBottomLinear.isShown()) {
//                                holder.albumImageVideoListItemBinding.imageBottomLinear.setVisibility(View.GONE);
//                                holder.albumImageVideoListItemBinding.imageBottomLinear.startAnimation(fadeout);
//                            } else {
//                                holder.albumImageVideoListItemBinding.imageBottomLinear.setVisibility(View.VISIBLE);
//                                holder.albumImageVideoListItemBinding.imageBottomLinear.startAnimation(fadein);
//                            }
//                        }
//                    });
//                }
            }

            holder.albumImageVideoListItemBinding.uploadimageViewstxt.setText(albumImageViews.get(position));
            holder.albumImageVideoListItemBinding.uploadimageUserNametxt.setText(albumAddUser.get(position));
            holder.albumImageVideoListItemBinding.uploadimageDurationtxt.setText(albumDuration.get(position));
            Log.d("userName :", albumAddUser.get(position));


            if (albumLike.get(position).equalsIgnoreCase("1")) {
                holder.albumImageVideoListItemBinding.bottomImageLikeBtn.setLiked(true);
            } else {
                holder.albumImageVideoListItemBinding.bottomImageLikeBtn.setLiked(false);
            }
            holder.albumImageVideoListItemBinding.bottomImageLikeBtn.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    if (Utils.isMember(mContext, "ImageUpload")) {
                        Utils.LikeMemberId = String.valueOf(Utils.getAppUserId(mContext));
                        Utils.LikeReferenceId = albumId.get(position);
                        Utils.LikeSourceType = albumMediaType.get(position);
                        Utils.LikeStatus = "1";
                        Utils.InsertLike(mContext, activity);

                        EventBus.getDefault().post(new MyScreenChnagesModel(position));
                    } else {
                        holder.albumImageVideoListItemBinding.bottomImageLikeBtn.setLiked(false);
                    }

                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    if (Utils.isMember(mContext, "ImageUpload")) {
                        Utils.LikeMemberId = String.valueOf(Utils.getAppUserId(mContext));
                        Utils.LikeReferenceId = albumId.get(position);
                        Utils.LikeSourceType = albumMediaType.get(position);
                        Utils.LikeStatus = "0";
                        Utils.InsertLike(mContext, activity);
                        EventBus.getDefault().post(new MyScreenChnagesModel(position));
                    } else {
                        holder.albumImageVideoListItemBinding.bottomImageLikeBtn.setLiked(true);
                    }

                }
            });


            holder.albumImageVideoListItemBinding.commentLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.isMember(mContext, "ImageUpload")) {
                        Intent commentIntent = new Intent(mContext, CommentActivity.class);
                        commentIntent.putExtra("referenceId", albumId.get(position));
                        commentIntent.putExtra("sourceType", albumMediaType.get(position));
                        commentIntent.putExtra("pageTitle", "Album Comments");
                        mContext.startActivity(commentIntent);
                    }
                }
            });

            holder.albumImageVideoListItemBinding.shareArticle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.handleClickEvent(mContext, holder.albumImageVideoListItemBinding.shareArticle);
                    if (Utils.isMember(mContext, "ImageUpload")) {
                        galleryVideoStr = albumImageUrl.get(position);
                        mediaTypeStr = albumMediaType.get(position);
                        imageClick.image_more_click();
                    }

                }
            });
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
            return albumImageUrl.size();
        }

        private void setAnimation(View viewToAnimate, int position) {

            if (position > lastPosition) {
                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right_new);
                animation.setDuration(500);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            } else if (position < lastPosition) {
                Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_left_new);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }
        }

        public String MediaTypeId() {
            return mediaTypeStr;
        }

        public String VideoUrlStr() {
            return galleryVideoStr;
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

        player = ExoPlayerFactory.newSimpleInstance(/* context= */ mContext, renderersFactory, trackSelector, defaultLoadControl);
        player.addListener(new PlayerEventListener());
        player.setPlayWhenReady(startAutoPlay);
        player.addAnalyticsListener(new EventLogger(trackSelector));
        playerView.setPlayer(player);


        mediaSource = buildMediaSource(Uri.parse(playVideoPathStr)); // videoUrlStr
//        https://baappvideo.s3.ap-south-1.amazonaws.com/74425094_140387590620474_1342703700878427324_n.mp4
//                https://www.bharatarmy.com//Docs/74425094_140387590620474_1342703700878427324_n.mp4
//        "https://baappvideo.s3.ap-south-1.amazonaws.com/appvideo_1.mp4"
        player.prepare(mediaSource);
        updateButtonVisibilities();
//        initBwd();
//        initFwd();
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
                    exoPlay.setVisibility(View.VISIBLE);
                    exoPause.setVisibility(View.VISIBLE);

                    break;
                case ExoPlayer.STATE_BUFFERING:
                    progressBar.setVisibility(View.VISIBLE);
                    exoPlay.setVisibility(View.GONE);
                    exoPause.setVisibility(View.GONE);
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
    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || player == null) {
            if (playerView !=null){
                initializePlayer();
            }

//            if (playerView != null) {
//                playerView.onResume();
//            }
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


}
