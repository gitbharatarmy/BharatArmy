package com.bharatarmy.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bharatarmy.Activity.GalleryImageDetailActivity;
import com.bharatarmy.Activity.ImageEditProfilePickerActivity;
import com.bharatarmy.Activity.ImageVideoUploadActivity;
import com.bharatarmy.Activity.VideoDetailActivity;
import com.bharatarmy.Activity.VideoTrimActivity;
import com.bharatarmy.Activity.VideoUploadActivity;
import com.bharatarmy.Adapter.ImageListAdapter;
import com.bharatarmy.Adapter.VideoListAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.EndlessRecyclerViewScrollListener;
import com.bharatarmy.Utility.GridSpacingItemDecoration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentVideoBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.leinardi.android.speeddial.FabWithLabelView;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialOverlayLayout;
import com.leinardi.android.speeddial.SpeedDialView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.app.Activity.RESULT_OK;

// changes code 21/06/2019
public class VideoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentVideoBinding fragmentVideoBinding;
    private View rootView;
    private Context mContext;
    VideoListAdapter videoListAdapter;
    List<ImageDetailModel> videoDetailModelsList;
    ArrayList<String> videoUrl = new ArrayList<>();
    boolean isLoading = false;
    boolean ispull;
    String imageClickData;
    FloatingActionButton fab;
    int[] lastPositions;
    int lastVisibleItem;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    int pageIndex = 0;
    SpeedDialOverlayLayout overlayLayout;
    SpeedDialView speedDialView;
    String imageorvideoStr;

    Uri selectedUri;
    private static final int REQUEST_VIDEO_TRIMMER = 0x01;
    public VideoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoFragment newInstance(String param1, String param2) {
        VideoFragment fragment = new VideoFragment();
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
        fragmentVideoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_video, container, false);

        rootView = fragmentVideoBinding.getRoot();
        mContext = getActivity().getApplicationContext();
        EventBus.getDefault().register(this);
        ImageEditProfilePickerActivity.clearCache(mContext);
        setUserVisibleHint(true);
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            // Refresh your fragment here
            if (videoListAdapter == null) {
                fragmentVideoBinding.shimmerViewContainer.startShimmerAnimation();

                callVideoGalleryData();
            }
            setListiner();
            speedDialView = (SpeedDialView) getActivity().findViewById(R.id.speedDial);
            overlayLayout = (SpeedDialOverlayLayout) getActivity().findViewById(R.id.overlay);
            speedDialView.setVisibility(View.VISIBLE);

            initSpeedDial();
        }
    }
    public void initSpeedDial() {  //boolean addActionItems
        speedDialView = (SpeedDialView) getActivity().findViewById(R.id.speedDial);
        overlayLayout = (SpeedDialOverlayLayout) getActivity().findViewById(R.id.overlay);
        speedDialView.setVisibility(View.VISIBLE);
//        if (addActionItems) {
            Drawable drawable = AppCompatResources.getDrawable(mContext, R.drawable.video_image_d);
            FabWithLabelView fabWithvideoView = speedDialView.addActionItem(new SpeedDialActionItem.Builder(R.id
                    .fab_no_label, drawable)
                    .setLabel("Video Upload")
                    .setLabelBackgroundColor(getResources().getColor(R.color.splash_bg_color))
                    .setFabImageTintColor(getResources().getColor(R.color.splash_bg_color))
                    .create());
            if (fabWithvideoView != null) {
                fabWithvideoView.setSpeedDialActionItem(fabWithvideoView.getSpeedDialActionItemBuilder()
                        .setFabBackgroundColor(getResources().getColor(R.color.heading_bg))
                        .create());
            }
            Drawable drawableimage = AppCompatResources.getDrawable(mContext, R.drawable.image_d);
            FabWithLabelView fabWithLabelView = speedDialView.addActionItem(new SpeedDialActionItem.Builder(R.id
                    .fab_custom_color, drawableimage)
                    .setLabel("Image Upload")
                    .setLabelBackgroundColor(getResources().getColor(R.color.splash_bg_color))
                    .setFabImageTintColor(getResources().getColor(R.color.splash_bg_color))
                    .create());
            if (fabWithLabelView != null) {
                fabWithLabelView.setSpeedDialActionItem(fabWithLabelView.getSpeedDialActionItemBuilder()
                        .setFabBackgroundColor(getResources().getColor(R.color.heading_bg))
                        .create());
            }
//        }

        //Set main action clicklistener.
        speedDialView.setOnChangeListener(new SpeedDialView.OnChangeListener() {
            @Override
            public boolean onMainActionSelected() {
                overlayLayout.setVisibility(View.VISIBLE);
                return false; // True to keep the Speed Dial open
            }

            @Override
            public void onToggleChanged(boolean isOpen) {
                Log.d("Print value :", "Speed dial toggle state changed. Open = " + isOpen);
            }
        });

        //Set option fabs clicklisteners.
        speedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem actionItem) {
                switch (actionItem.getId()) {
                    case R.id.fab_no_label:
                        if (Utils.isMember(mContext,"ImageUpload")) {
                            Dexter.withActivity(getActivity())
                                    .withPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    .withListener(new MultiplePermissionsListener() {
                                        @Override
                                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                                            if (report.areAllPermissionsGranted()) {
                                                imageorvideoStr = "video";
                                                pickVideoFromGallery();
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
                        speedDialView.open(); // To close the Speed Dial with animation
                        return false; // false will close it without animation

                    case R.id.fab_custom_color:
                        if (Utils.isMember(mContext,"ImageUpload")) {
                            Dexter.withActivity(getActivity())
                                    .withPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    .withListener(new MultiplePermissionsListener() {
                                        @Override
                                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                                            if (report.areAllPermissionsGranted()) {

                                                imageorvideoStr = "image";
                                                Intent imagevideouploadIntent1 = new Intent(mContext, ImageVideoUploadActivity.class);
                                                imagevideouploadIntent1.putExtra("image/video", imageorvideoStr);
                                                imagevideouploadIntent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                mContext.startActivity(imagevideouploadIntent1);
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
                        speedDialView.open();
                        return false; // closes without animation (same as speedDialView.close(false); return false;)

                    default:
                        break;
                }
                return true; // To keep the Speed Dial openspeedDial
            }
        });
    }
    public void setListiner() {
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
//        gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
        fragmentVideoBinding.videoRcvList.setLayoutManager(staggeredGridLayoutManager); // set LayoutManager to RecyclerView


        fragmentVideoBinding.refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex=0;
                callVideoGalleryPullData();

            }
        });

        fragmentVideoBinding.videoRcvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                lastPositions = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(lastPositions);
                lastVisibleItem = Math.max(lastPositions[0], lastPositions[1]);//findMax(lastPositions);

                if (!isLoading) {
                    if (staggeredGridLayoutManager != null && lastVisibleItem == videoDetailModelsList.size() - 1) {
                        //bottom of list!
                        ispull = false;
                        pageIndex = pageIndex + 1;
                        fragmentVideoBinding.bottomProgressbarLayout.setVisibility(View.VISIBLE);
                        loadMore();

                    }
                }
            }
        });
    }

    // Api calling GetVideoGalleryData
    public void callVideoGalleryData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getBAVideoGallery(getVideoGalleryData(), new retrofit.Callback<ImageMainModel>() {
            @Override
            public void success(ImageMainModel imageMainModel, Response response) {
                Utils.dismissDialog();
                if (imageMainModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (imageMainModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (imageMainModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (imageMainModel.getIsValid() == 1) {

                    if (imageMainModel.getData() != null) {
                        fragmentVideoBinding.shimmerViewContainer.stopShimmerAnimation();
                        fragmentVideoBinding.shimmerViewContainer.setVisibility(View.GONE);
                        fragmentVideoBinding.bottomProgressbarLayout.setVisibility(View.GONE);
                        videoDetailModelsList = imageMainModel.getData();

                        addOldNewValue(videoDetailModelsList);
                        if (videoListAdapter != null && videoDetailModelsList.size() > 0) {
                            videoListAdapter.addMoreDataToList(videoDetailModelsList);
                            // just append more data to current list
                        } else if (videoListAdapter != null && videoDetailModelsList.size() == 0) {
                            isLoading = true;
                            addOldNewValue(imageMainModel.getData());
                        } else {
                            fillVideoGallery();
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

    private Map<String, String> getVideoGalleryData() {
        Map<String, String> map = new HashMap<>();
        map.put("PageIndex", String.valueOf(pageIndex));
        map.put("PageSize", "20");
        map.put("MemberId",String.valueOf(Utils.getAppUserId(mContext)));
        return map;
    }

    public void fillVideoGallery() {
        videoListAdapter = new VideoListAdapter(mContext,getActivity(), videoDetailModelsList);
        fragmentVideoBinding.videoRcvList.setAdapter(videoListAdapter);

    }

    public void addOldNewValue(List<ImageDetailModel> result) {
        for (int i = 0; i < result.size(); i++) {
            videoUrl.addAll(Collections.singleton(result.get(i).getGalleryURL()));
        }
        Log.d("videoUrl", "" + videoUrl.size());

    }

    private void loadMore() {

       callVideoGalleryData();


    }
    // Api calling GetVideoGalleryPullData
    public void callVideoGalleryPullData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getBAVideoGallery(getVideoGalleryPullData(), new retrofit.Callback<ImageMainModel>() {
            @Override
            public void success(ImageMainModel videoMainModel, Response response) {
                Utils.dismissDialog();
                if (videoMainModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (videoMainModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (videoMainModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (videoMainModel.getIsValid() == 1) {

                    if (videoMainModel.getData() != null) {

                        videoUrl.clear();
                        videoDetailModelsList = videoMainModel.getData();

                         isLoading=false;
                        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
                        fragmentVideoBinding.videoRcvList.setLayoutManager(staggeredGridLayoutManager);
                        videoListAdapter = new VideoListAdapter(mContext,getActivity(), videoDetailModelsList);
                        fragmentVideoBinding.videoRcvList.setAdapter(videoListAdapter);
                        fragmentVideoBinding.refreshView.setRefreshing(false);


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

    private Map<String, String> getVideoGalleryPullData() {
        Map<String, String> map = new HashMap<>();
        map.put("PageIndex",String.valueOf(pageIndex));
        map.put("PageSize", "20");
        map.put("MemberId",String.valueOf(Utils.getAppUserId(mContext)));
        return map;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        videoDetailModelsList.clear();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    //    pick the video in gallery
    private void pickVideoFromGallery() {
        Intent intent = new Intent();
        intent.setTypeAndNormalize("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_video)), REQUEST_VIDEO_TRIMMER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_VIDEO_TRIMMER) {
                selectedUri = data.getData();
                Log.d("selectedVideoUri :",""+selectedUri);

                Intent videoTrimIntent=new Intent(mContext,VideoTrimActivity.class);
                videoTrimIntent.putExtra("videoPath",selectedUri.toString());
                getActivity().startActivity(videoTrimIntent);


            }
        }
    }


    @Subscribe
    public void customEventReceived(MyScreenChnagesModel event) {
        if (!event.getMessage().equalsIgnoreCase("")) {
            for (int i=0;i<videoDetailModelsList.size();i++){
                if (videoDetailModelsList.get(i).getBAVideoGalleryId()== Integer.valueOf(event.getMessage())){
                    videoDetailModelsList.get(i).setIsLike(Utils.LikeStatus);
                }
            }
        }

    }
}
