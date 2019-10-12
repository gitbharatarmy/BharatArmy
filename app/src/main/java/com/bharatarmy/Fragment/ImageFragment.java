package com.bharatarmy.Fragment;

import android.Manifest;
import android.app.Activity;
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
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bharatarmy.Activity.GalleryImageDetailActivity;
import com.bharatarmy.Activity.ImageEditProfilePickerActivity;
import com.bharatarmy.Activity.ImageVideoUploadActivity;
import com.bharatarmy.Activity.VideoTrimActivity;
import com.bharatarmy.Activity.VideoUploadActivity;
import com.bharatarmy.Adapter.ImageListAdapter;
import com.bharatarmy.Interfaces.FragmentRefreshListener;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.EndlessRecyclerViewScrollListener;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentImageBinding;
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

// remove comment code 05-06-2019
public class ImageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentImageBinding fragmentImageBinding;
    private View rootView;
    private Context mContext;
    ImageListAdapter imageListAdapter;
    List<ImageDetailModel> imageDetailModelsList;
    ArrayList<String> galleryImageUrl = new ArrayList<>();
    ArrayList<String> galleryImageUrlName = new ArrayList<>();
    ArrayList<String> galleryImageDuration=new ArrayList<>();
    ArrayList<String> galleryImageLike=new ArrayList<>();
    ArrayList<String> galleryImageId=new ArrayList<>();
    boolean isMoreDataAvailable = true;
    String imageClickData;
    int pageIndex = 0;
    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;
    boolean isLoading = false;
    GridLayoutManager gridLayoutManager;
    boolean ispull;

    String imageorvideoStr;
    SpeedDialOverlayLayout overlayLayout;
    SpeedDialView speedDialView;

  Uri selectedUri;
    private static final int REQUEST_VIDEO_TRIMMER = 0x01;
    public ImageFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ImageFragment newInstance(String param1, String param2) {
        ImageFragment fragment = new ImageFragment();
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
        fragmentImageBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_image, container, false);

        rootView = fragmentImageBinding.getRoot();
        mContext = getActivity().getApplicationContext();
        EventBus.getDefault().register(this);
        ImageEditProfilePickerActivity.clearCache(mContext);

//        initSpeedDial(savedInstanceState == null);

        setUserVisibleHint(true);
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            // Refresh your fragment here
            if (imageListAdapter == null) {
                fragmentImageBinding.shimmerViewContainer.startShimmerAnimation();

                callImageGalleryData();
            }
            setListiner();
            speedDialView = (SpeedDialView) getActivity().findViewById(R.id.speedDial);
            overlayLayout = (SpeedDialOverlayLayout) getActivity().findViewById(R.id.overlay);
            speedDialView.setVisibility(View.VISIBLE);
            initSpeedDial();

        }
    }

    public void initSpeedDial() { //boolean addActionItems
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
        gridLayoutManager = new GridLayoutManager(mContext, 3);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
        fragmentImageBinding.imageRcyList.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView


        fragmentImageBinding.refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageIndex=0;
         callImageGalleryPullData();

            }
        });

        fragmentImageBinding.imageRcyList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if (!isLoading) {
                    if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == imageDetailModelsList.size() - 1) {
                        //bottom of list!
                        isLoading = false;
                        pageIndex = pageIndex + 1;
                        fragmentImageBinding.bottomProgressbarLayout.setVisibility(View.VISIBLE);

                        loadMore();
                    }
                }
            }
        });
    }


    // Api calling GetImageGalleryData
    public void callImageGalleryData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getBAGallery(getImageGalleryData(), new retrofit.Callback<ImageMainModel>() {
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
                        fragmentImageBinding.shimmerViewContainer.stopShimmerAnimation();
                        fragmentImageBinding.shimmerViewContainer.setVisibility(View.GONE);
                        fragmentImageBinding.bottomProgressbarLayout.setVisibility(View.GONE);
                        imageDetailModelsList = imageMainModel.getData();
Log.d("list : ",""+imageDetailModelsList.size());
                        addOldNewValue(imageDetailModelsList);
                        if (imageListAdapter != null && imageDetailModelsList.size() > 0) {
                            imageListAdapter.addMoreDataToList(imageDetailModelsList);
                            // just append more data to current list
                        } else if (imageListAdapter != null && imageDetailModelsList.size() == 0) {
                            isLoading = true;
                            addOldNewValue(imageMainModel.getData());
                        } else {
                            fillImageGallery();
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

    private Map<String, String> getImageGalleryData() {
        Map<String, String> map = new HashMap<>();
        map.put("PageIndex", String.valueOf(pageIndex));
        map.put("PageSize", "15");
        map.put("MemberId",String.valueOf(Utils.getAppUserId(mContext)));
        return map;
    }

    public void fillImageGallery() {
        imageListAdapter = new ImageListAdapter(mContext, imageDetailModelsList,getActivity(), new image_click() {
            @Override
            public void image_more_click() {
                imageClickData = "";
                imageClickData = String.valueOf(imageListAdapter.getData());
                imageClickData = imageClickData.replaceAll("\\[", "").replaceAll("\\]", "");
                Log.d("imageClickData", "" + imageClickData);
                Intent gallerydetailIntent = new Intent(mContext, GalleryImageDetailActivity.class);
                gallerydetailIntent.putExtra("positon", imageClickData);
                gallerydetailIntent.putStringArrayListExtra("data", galleryImageUrl);
                gallerydetailIntent.putStringArrayListExtra("dataName",galleryImageUrlName);
                gallerydetailIntent.putStringArrayListExtra("dataDuration",galleryImageDuration);
                gallerydetailIntent.putStringArrayListExtra("dataId",galleryImageId);
                gallerydetailIntent.putStringArrayListExtra("dataLike",galleryImageLike);
                gallerydetailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(gallerydetailIntent);
            }
        });
        fragmentImageBinding.imageRcyList.setAdapter(imageListAdapter);

    }

    private void loadMore() {

        callImageGalleryData();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        imageDetailModelsList.clear();
    }

    public void addOldNewValue(List<ImageDetailModel> result) {
        for (int i = 0; i < result.size(); i++) {
            galleryImageUrl.addAll(Collections.singleton(result.get(i).getGalleryURL()));
            galleryImageUrlName.addAll(Collections.singleton(result.get(i).getAddedUserName()));
            galleryImageDuration.addAll(Collections.singleton(result.get(i).getStrAddedDuration()));
            galleryImageId.addAll(Collections.singleton(String.valueOf(result.get(i).getBAGalleryId())));
            galleryImageLike.addAll(Collections.singleton(String.valueOf(result.get(i).getIsLike())));
        }
        Log.d("galleryImageUrl", "" + galleryImageUrl.size() +"galleryImageId :"+galleryImageId.size());

    }

    // Api calling GetImageGalleryData
    public void callImageGalleryPullData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getBAGallery(getImageGalleryPullData(), new retrofit.Callback<ImageMainModel>() {
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

                      galleryImageUrl.clear();
                      galleryImageUrlName.clear();
                        galleryImageDuration.clear();
                        galleryImageId.clear();
                        galleryImageLike.clear();
                        imageDetailModelsList = imageMainModel.getData();
Log.d("pullDataList : ",""+imageDetailModelsList.size());
                        addOldNewValue(imageDetailModelsList);

                      fillImageGallery();
                        fragmentImageBinding.refreshView.setRefreshing(false);
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

    private Map<String, String> getImageGalleryPullData() {
        Map<String, String> map = new HashMap<>();
        map.put("PageIndex", String.valueOf(pageIndex));
        map.put("PageSize", "15");
        map.put("MemberId",String.valueOf(Utils.getAppUserId(mContext)));
        return map;
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
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
        Log.d("imageId :", event.getMessage());
        Log.d("mainmodelValue :", imageDetailModelsList.toString());
        if (!event.getMessage().equalsIgnoreCase("")) {
           for (int i=0;i<galleryImageLike.size();i++){
               if (i == Integer.parseInt(event.getMessage())) {
                  galleryImageLike.add(i, String.valueOf(Utils.LikeStatus));
               }
           }
        }

    }
}


