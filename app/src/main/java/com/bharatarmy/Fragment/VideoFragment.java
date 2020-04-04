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
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bharatarmy.Activity.ImageUploadActivity;
import com.bharatarmy.Activity.VideoTrimActivity;
import com.bharatarmy.Adapter.VideoListAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentVideoBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.leinardi.android.speeddial.FabWithLabelView;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialOverlayLayout;
import com.leinardi.android.speeddial.SpeedDialView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.app.Activity.RESULT_OK;

// changes code 21/06/2019
// changes code 23/01/2020
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
    List<ImageDetailModel> videoDetailModelsList = new ArrayList<>();
    List<ImageDetailModel> videoDetailModelsList1 = new ArrayList<>();


    ArrayList<String> videoUrl = new ArrayList<>();
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    SpeedDialOverlayLayout overlayLayout;
    SpeedDialView speedDialView;
    String imageorvideoStr;

    Uri selectedUri;
    private static final int REQUEST_VIDEO_TRIMMER = 0x01;
    public String isUpdateAvailable, isForceUpdateAvailable, currentVersionStr;

    //    lazy loading variable
    int videopageIndex = 0;
    private boolean isVideoLoading = true;
    int videopageSize = 15;


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
//        EventBus.getDefault().register(this);

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

                callVideoGalleryData("first");
            }
            setListiner();
            speedDialView = (SpeedDialView) getActivity().findViewById(R.id.speedDial);
            overlayLayout = (SpeedDialOverlayLayout) getActivity().findViewById(R.id.overlay);
            speedDialView.setVisibility(View.VISIBLE);

            initSpeedDial();
        } else {

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
                        if (Utils.isMember(mContext, "ImageUpload")) {
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
                        if (Utils.isMember(mContext, "ImageUpload")) {
                            Dexter.withActivity(getActivity())
                                    .withPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    .withListener(new MultiplePermissionsListener() {
                                        @Override
                                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                                            if (report.areAllPermissionsGranted()) {

                                                imageorvideoStr = "image";
                                                Intent imagevideouploadIntent1 = new Intent(mContext, ImageUploadActivity.class);
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

                /*visibleItemCount = staggeredGridLayoutManager.getChildCount();
                totalItemCount = staggeredGridLayoutManager.getItemCount();
                totalItemCount = videoDetailModelsList1.size() ;
                int[] firstVisibleItems = null;
                firstVisibleItems = staggeredGridLayoutManager.findFirstVisibleItemPositions(firstVisibleItems);
                if (firstVisibleItems != null && firstVisibleItems.length > 0) {
                    pastVisibleItems = firstVisibleItems[0];
                }
                Log.d("totalItemCount :", "" + totalItemCount + " visibleItemCount :" + visibleItemCount + "pastvisibleItem :" + pastVisibleItems);
                if (isVideoLoading == true) {
//                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                    if (staggeredGridLayoutManager != null && (visibleItemCount -1) == totalItemCount) {
                        isVideoLoading = true;
                        Log.d("tag", "LOAD NEXT ITEM");
                        pageIndex++;
                        fragmentVideoBinding.bottomProgressbarLayout.setVisibility(View.VISIBLE);
                        loadMore();
                    }
                }*/
                int[] lastVisibleItemPositions = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(null);
                int lastVisibleItem = getLastVisibleItemofVideo(lastVisibleItemPositions);
                int totalItemCount = staggeredGridLayoutManager.getItemCount();
                Log.d("lastVisibleItem :", "" + lastVisibleItem + " totalItemCount :" + videoDetailModelsList1.size());

//                lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
//                lastPositions = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(lastPositions);
//                lastVisibleItem = Math.max(lastPositions[0], lastPositions[1]);//findMax(lastPositions);
//
//                Log.d("lastVisibleItem :", "" + lastVisibleItem + " videoDetailModelsList1 :" + videoDetailModelsList1.size());
                if (isVideoLoading == true) {
                    if (staggeredGridLayoutManager != null && (lastVisibleItem + 1) == videoDetailModelsList1.size()) {
                        //bottom of list!
                        isVideoLoading = true;
                        videopageIndex++;
                        fragmentVideoBinding.bottomProgressbarLayout.setVisibility(View.VISIBLE);
                        loadMore();

                    }
                }
            }
        });
    }

    public int getLastVisibleItemofVideo(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    // Api calling GetVideoGalleryData
    public void callVideoGalleryData(String wherecall) {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getBAVideoGallery(getVideoGalleryData(), new retrofit.Callback<ImageMainModel>() {
            @Override
            public void success(ImageMainModel videoMainModel, Response response) {
                Utils.dismissDialog();
                if (videoMainModel == null) {
//                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (videoMainModel.getIsValid() == null) {
//                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (videoMainModel.getIsValid() == 0) {
//                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (videoMainModel.getIsValid() == 1) {
                    isUpdateAvailable = String.valueOf(videoMainModel.getIsUpdateAvailable());
                    isForceUpdateAvailable = String.valueOf(videoMainModel.getIsForceUpdate());
//                    isForceUpdateAvailable = "0";
                    currentVersionStr = String.valueOf(videoMainModel.getCurrentVersion());
                    if (isUpdateAvailable.equalsIgnoreCase("1")) {
                        Utils.checkupdateApplication(mContext, getActivity(), isForceUpdateAvailable, currentVersionStr);
                    }
                    if (videoMainModel.getData() != null) {
                        if (wherecall.equalsIgnoreCase("first")) {
                         if (videoListAdapter!=null){
                             videoListAdapter.clear();
                         }
                        }

                        fragmentVideoBinding.shimmerViewContainer.stopShimmerAnimation();
                        fragmentVideoBinding.videoRcvList.setVisibility(View.VISIBLE);
                        fragmentVideoBinding.shimmerViewContainer.setVisibility(View.GONE);
                        fragmentVideoBinding.bottomProgressbarLayout.setVisibility(View.GONE);

                        if (videoMainModel.getData().size() != 0) {
                            if (videoDetailModelsList1.size() == 0) {
                                videoDetailModelsList1.addAll(0, videoMainModel.getData());
                            } else {
                                videoDetailModelsList1.addAll(videoDetailModelsList1.size(), videoMainModel.getData());
                            }
                        }
                        if (videoDetailModelsList1.size() < videopageSize) {
                            isVideoLoading = false;
                        }

                        videoDetailModelsList = videoMainModel.getData();

                        addOldNewValue(videoDetailModelsList);
                        if (videoListAdapter != null && videoDetailModelsList.size() > 0) {
                            // just append more data to current list
                            videoListAdapter.addMoreDataToList(videoDetailModelsList);

                        } else if (videoListAdapter != null && videoDetailModelsList.size() == 0) {
                            isVideoLoading = false;

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
        map.put("PageIndex", String.valueOf(videopageIndex));
        map.put("PageSize", String.valueOf(videopageSize));
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));

        return map;
    }

    public void fillVideoGallery() {
        videoListAdapter = new VideoListAdapter(mContext, getActivity(), videoDetailModelsList, new image_click() {
            @Override
            public void image_more_click() {
//               videoDataDirection();

            }
        });
        fragmentVideoBinding.videoRcvList.setAdapter(videoListAdapter);

    }

    public void addOldNewValue(List<ImageDetailModel> result) {
        for (int i = 0; i < result.size(); i++) {
            videoUrl.addAll(Collections.singleton(result.get(i).getGalleryURL()));
        }
        Log.d("videoUrl", "" + videoUrl.size());

    }

    private void loadMore() {

        callVideoGalleryData("second");


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
                    isUpdateAvailable = String.valueOf(videoMainModel.getIsUpdateAvailable());
                    isForceUpdateAvailable = String.valueOf(videoMainModel.getIsForceUpdate());
//                    isForceUpdateAvailable = "0";
                    currentVersionStr = String.valueOf(videoMainModel.getCurrentVersion());
                    if (isUpdateAvailable.equalsIgnoreCase("1")) {
                        Utils.checkupdateApplication(mContext, getActivity(), isForceUpdateAvailable, currentVersionStr);
                    }
                    if (videoMainModel.getData() != null) {


                        videoUrl.clear();
                        videoDetailModelsList1.addAll(0, videoMainModel.getData());
                        Set<ImageDetailModel> unique = new LinkedHashSet<ImageDetailModel>(videoDetailModelsList1);
                        videoDetailModelsList1 = new ArrayList<ImageDetailModel>(unique);
                        videoListAdapter.clear();
                        videoListAdapter.addMoreDataToList(videoDetailModelsList1);


                        addOldNewValue(videoDetailModelsList1);

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
        map.put("PageIndex", "0");
        map.put("PageSize", String.valueOf(videopageSize));
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));

        return map;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (videoDetailModelsList != null) {
            videoDetailModelsList.clear();
        }
        if (videoDetailModelsList1 != null) {
            videoDetailModelsList1.clear();
        }

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
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("video/*");
        startActivityForResult(galleryIntent, REQUEST_VIDEO_TRIMMER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_VIDEO_TRIMMER) {
                selectedUri = data.getData();
                Log.d("selectedVideoUri :", "" + selectedUri);

                Intent videoTrimIntent = new Intent(mContext, VideoTrimActivity.class);
                videoTrimIntent.putExtra("videoPath", selectedUri.toString());
                getActivity().startActivity(videoTrimIntent);
            }
        }
    }


}
