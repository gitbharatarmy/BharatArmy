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

import com.bharatarmy.Activity.ImageVideoUploadActivity;
import com.bharatarmy.Adapter.AlbumListAdapter;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentAlbumBinding;
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


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class AlbumFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentAlbumBinding albumBinding;
    private View rootView;
    private Context mContext;
    AlbumListAdapter albumListAdapter;
    List<ImageDetailModel> albumList;
    ArrayList<String> galleryImageUrl = new ArrayList<>();

    int pageIndex = 0;
    boolean isLoading = false;
    boolean ispull;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    int[] lastPositions;
    int lastVisibleItem;
    String imageorvideoStr;
    SpeedDialOverlayLayout overlayLayout;
    SpeedDialView speedDialView;
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
        albumBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_album, container, false);

        rootView = albumBinding.getRoot();
        mContext = getActivity().getApplicationContext();
        initSpeedDial(savedInstanceState == null);
        setUserVisibleHint(true);
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            // Refresh your fragment here

            if (albumListAdapter == null) {
                callImageGalleryData();
            }
            setListiner();
            speedDialView = (SpeedDialView) getActivity().findViewById(R.id.speedDial);
            overlayLayout = (SpeedDialOverlayLayout) getActivity().findViewById(R.id.overlay);
            speedDialView.setVisibility(View.VISIBLE);
        }
    }


    public void setListiner() {
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        albumBinding.rvPosters.setLayoutManager(staggeredGridLayoutManager);

        albumBinding.rvPosters.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    if (staggeredGridLayoutManager != null && lastVisibleItem == albumList.size() - 1) {
                        //bottom of list!
                        ispull = false;
                        pageIndex = pageIndex + 1;
                        loadMore();

                    }
                }
            }
        });

        albumBinding.refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callImageGalleryPullData();
                albumBinding.refreshView.setRefreshing(false);
            }
        });

    }

    public void initSpeedDial(boolean addActionItems) {
        speedDialView = (SpeedDialView) getActivity().findViewById(R.id.speedDial);
        overlayLayout = (SpeedDialOverlayLayout) getActivity().findViewById(R.id.overlay);
        speedDialView.setVisibility(View.VISIBLE);
        if (addActionItems) {
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
        }

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
                        Dexter.withActivity(getActivity())
                                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                                .withListener(new MultiplePermissionsListener() {
                                    @Override
                                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                                        if (report.areAllPermissionsGranted()) {
                                            imageorvideoStr = "video";
                                            Intent imagevideouploadIntent1 = new Intent(mContext, ImageVideoUploadActivity.class);
                                            imagevideouploadIntent1.putExtra("image/video", imageorvideoStr);

                                            startActivity(imagevideouploadIntent1);
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
                        speedDialView.open(); // To close the Speed Dial with animation
                        return false; // false will close it without animation

                    case R.id.fab_custom_color:
                        Dexter.withActivity(getActivity())
                                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                                .withListener(new MultiplePermissionsListener() {
                                    @Override
                                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                                        if (report.areAllPermissionsGranted()) {
                                        }
                                        imageorvideoStr = "image";
                                        Intent imagevideouploadIntent1 = new Intent(mContext, ImageVideoUploadActivity.class);
                                        imagevideouploadIntent1.putExtra("image/video", imageorvideoStr);
                                        imagevideouploadIntent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        mContext.startActivity(imagevideouploadIntent1);
                                    }

                                    @Override
                                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                        token.continuePermissionRequest();
                                    }
                                }).check();

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
                        albumList=new ArrayList<>();
                        albumList = imageMainModel.getData();

                        addOldNewValue (albumList);
                        if (albumListAdapter != null && albumList.size() > 0) {
                            albumListAdapter.addMoreDataToList(albumList);
                            // just append more data to current list
                        } else if(albumListAdapter!=null && albumList.size()==0){
                            isLoading = true;
                            addOldNewValue (imageMainModel.getData());
                        }else {
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
        return map;
    }

    public void fillImageGallery() {
        albumListAdapter = new AlbumListAdapter(mContext, albumList);
        albumBinding.rvPosters.setAdapter(albumListAdapter);


    }

    private void loadMore() {
        callImageGalleryData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        albumList.clear();
    }

    public void addOldNewValue(List<ImageDetailModel> result) {
        for (int i = 0; i < result.size(); i++) {
            galleryImageUrl.addAll(Collections.singleton(result.get(i).getGalleryURL()));
        }
        Log.d("galleryImageUrl", "" + galleryImageUrl.size());

    }
    public void addOldNewPullValue(List<ImageDetailModel> result) {
        galleryImageUrl.clear();
        for (int i = 0; i < result.size(); i++) {
            galleryImageUrl.addAll(Collections.singleton(result.get(i).getGalleryURL()));
        }
        Log.d("galleryImagepullUrl", "" + galleryImageUrl.size());

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
                        albumList = imageMainModel.getData();

//                        addOldNewPullValue (imageDetailModelsList);
                        albumListAdapter.notifyDataSetChanged();
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
        map.put("PageIndex","0");
        map.put("PageSize", "15");
        return map;
    }
}


