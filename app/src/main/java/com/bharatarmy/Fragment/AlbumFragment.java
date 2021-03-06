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
import com.bharatarmy.Adapter.AlbumListAdapter;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentAlbumBinding;
import com.bharatarmy.speeddialView.FabWithLabelView;
import com.bharatarmy.speeddialView.SpeedDialActionItem;
import com.bharatarmy.speeddialView.SpeedDialOverlayLayout;
import com.bharatarmy.speeddialView.SpeedDialView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;



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

// remove & change code 24-01-2020
public class AlbumFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentAlbumBinding fragmentAlbumBinding;
    private View rootView;
    private Context mContext;
    AlbumListAdapter albumListAdapter;
    List<ImageDetailModel> albumModelList;
    List<ImageDetailModel> albumModelList1 = new ArrayList<>();
    ArrayList<String> AlbumUrl = new ArrayList<>();
    public String isUpdateAvailable, isForceUpdateAvailable, currentVersionStr;

    StaggeredGridLayoutManager staggeredGridLayoutManagerAlbum;

    String imageorvideoStr;
    SpeedDialOverlayLayout overlayLayout;
    SpeedDialView speedDialView;

    Uri selectedUri;
    private static final int REQUEST_VIDEO_TRIMMER = 0x01;

    //    lazy loading variable
    int albumpageIndex = 0;
    boolean isAlbumLoading = true;
    int albumpageSize = 15;


    public AlbumFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ImageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlbumFragment newInstance(String param1, String param2) {
        AlbumFragment fragment = new AlbumFragment();
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
        fragmentAlbumBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_album, container, false);

        rootView = fragmentAlbumBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        setUserVisibleHint(true);
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            // Refresh your fragment here

            if (albumListAdapter == null) {
                fragmentAlbumBinding.shimmerViewContainer.startShimmerAnimation();
                callAlbumImageData("Starting");
            }
            setListiner();
            speedDialView = (SpeedDialView) getActivity().findViewById(R.id.speedDial);
            overlayLayout = (SpeedDialOverlayLayout) getActivity().findViewById(R.id.overlay);
            speedDialView.setVisibility(View.VISIBLE);
            initSpeedDial();
        }
    }


    public void setListiner() {
        staggeredGridLayoutManagerAlbum = new StaggeredGridLayoutManager(2, 1);
        fragmentAlbumBinding.rvPosters.setLayoutManager(staggeredGridLayoutManagerAlbum);

        fragmentAlbumBinding.rvPosters.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int[] lastVisibleItemPositions = staggeredGridLayoutManagerAlbum.findLastCompletelyVisibleItemPositions(null);
                int lastVisibleItem = getLastVisibleItemAlbum(lastVisibleItemPositions);
                int totalItemCount = staggeredGridLayoutManagerAlbum.getItemCount();
                Log.d("lastVisibleItem :", "" + lastVisibleItem + " totalItemCount :" + albumModelList1.size());

                if (isAlbumLoading == true) {
                    if (staggeredGridLayoutManagerAlbum != null && (lastVisibleItem + 1) == albumModelList1.size()) {
                        //bottom of list!
                        isAlbumLoading = true;
                        albumpageIndex++;
                        fragmentAlbumBinding.bottomProgressbarLayout.setVisibility(View.VISIBLE);
                        loadMore();

                    }
                }
            }
        });

        fragmentAlbumBinding.refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fragmentAlbumBinding.bottomProgressbarLayout.setVisibility(View.GONE);

                callAlbumImagePullData();

            }
        });

    }

    public int getLastVisibleItemAlbum(int[] lastVisibleItemPositions) {
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
                        if (Utils.isMember(mContext, "ImageUpload")) {
                            Dexter.withActivity(getActivity())
                                    .withPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    .withListener(new MultiplePermissionsListener() {
                                        @Override
                                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                                            if (report.areAllPermissionsGranted()) {
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

    // Api calling GetAlbumImageData
    public void callAlbumImageData(String whereTocall) {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getBAAlbum(getAlbumImageData(), new retrofit.Callback<ImageMainModel>() {
            @Override
            public void success(ImageMainModel albumMainModel, Response response) {
                Utils.dismissDialog();
                if (albumMainModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (albumMainModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (albumMainModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (albumMainModel.getIsValid() == 1) {
                    isUpdateAvailable = String.valueOf(albumMainModel.getIsUpdateAvailable());
                    isForceUpdateAvailable = String.valueOf(albumMainModel.getIsForceUpdate());
//                    isForceUpdateAvailable = "0";
                    currentVersionStr = String.valueOf(albumMainModel.getCurrentVersion());
                    if (isUpdateAvailable.equalsIgnoreCase("1")) {
                        Utils.checkupdateApplication(mContext, getActivity(), isForceUpdateAvailable, currentVersionStr);
                    }
                    if (albumMainModel.getData() != null) {

                        if (whereTocall.equalsIgnoreCase("Starting")) {
                            if (albumListAdapter!=null){
                                albumListAdapter.clear();
                            }
                        }

                        fragmentAlbumBinding.shimmerViewContainer.stopShimmerAnimation();
                        fragmentAlbumBinding.shimmerViewContainer.setVisibility(View.GONE);
                        fragmentAlbumBinding.bottomProgressbarLayout.setVisibility(View.GONE);

                        if (albumMainModel.getData().size() != 0) {
                            if (albumModelList1.size() == 0) {
                                albumModelList1.addAll(0, albumMainModel.getData());
                            } else {
                                albumModelList1.addAll(albumModelList1.size(), albumMainModel.getData());
                            }
                        }
                        if (albumModelList1.size() < albumpageSize) {
                            isAlbumLoading = false;
                        }
                        albumModelList = albumMainModel.getData();

                        addOldNewValue(albumModelList);
                        if (albumListAdapter != null && albumModelList.size() > 0) {
                            // just append more data to current list
                            albumListAdapter.addMoreDataToList(albumModelList);
                        } else if (albumListAdapter != null && albumModelList.size() == 0) {
                            isAlbumLoading = false;
                        } else {
                            fillAlbumImage();
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

    private Map<String, String> getAlbumImageData() {
        Map<String, String> map = new HashMap<>();
        map.put("PageIndex", String.valueOf(albumpageIndex));
        map.put("PageSize", String.valueOf(albumpageSize));
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));

        return map;
    }

    public void fillAlbumImage() {
        albumListAdapter = new AlbumListAdapter(mContext, albumModelList);
        fragmentAlbumBinding.rvPosters.setAdapter(albumListAdapter);


    }

    private void loadMore() {
        callAlbumImageData("load");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }

    public void addOldNewValue(List<ImageDetailModel> result) {
        for (int i = 0; i < result.size(); i++) {
            AlbumUrl.addAll(Collections.singleton(result.get(i).getGalleryURL()));
        }
        Log.d("galleryAlbumUrl", "" + AlbumUrl.size());

    }


    // Api calling GetImageGalleryData
    public void callAlbumImagePullData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getBAAlbum(getAlbumImagePullData(), new retrofit.Callback<ImageMainModel>() {
            @Override
            public void success(ImageMainModel albumMainModel, Response response) {
                Utils.dismissDialog();
                if (albumMainModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (albumMainModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (albumMainModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (albumMainModel.getIsValid() == 1) {
                    isUpdateAvailable = String.valueOf(albumMainModel.getIsUpdateAvailable());
                    isForceUpdateAvailable = String.valueOf(albumMainModel.getIsForceUpdate());
//                    isForceUpdateAvailable = "0";
                    currentVersionStr = String.valueOf(albumMainModel.getCurrentVersion());
                    if (isUpdateAvailable.equalsIgnoreCase("1")) {
                        Utils.checkupdateApplication(mContext, getActivity(), isForceUpdateAvailable, currentVersionStr);
                    }
                    if (albumMainModel.getData() != null) {


                        AlbumUrl.clear();
                        albumModelList1.addAll(0, albumMainModel.getData());
                        Set<ImageDetailModel> unique = new LinkedHashSet<ImageDetailModel>(albumModelList1);
                        albumModelList1 = new ArrayList<ImageDetailModel>(unique);
                        albumListAdapter.clear();
                        albumListAdapter.addMoreDataToList(albumModelList1);

                        addOldNewValue(albumModelList1);

                        fragmentAlbumBinding.refreshView.setRefreshing(false);
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

    private Map<String, String> getAlbumImagePullData() {
        Map<String, String> map = new HashMap<>();
        map.put("PageIndex", "0");
        map.put("PageSize", String.valueOf(albumpageSize));
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));

        return map;
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


