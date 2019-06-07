package com.bharatarmy.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bharatarmy.Activity.EditProfileActivity;
import com.bharatarmy.Activity.GalleryImageDetailActivity;
import com.bharatarmy.Activity.ImagePickerActivity;
import com.bharatarmy.Adapter.ImageListAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
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

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

// remove comment code 05-06-2019
public class ImageFragment extends Fragment implements View.OnClickListener {
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
    boolean isMoreDataAvailable = true;
    String imageClickData;
    int pageIndex = 0;
    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;
    boolean isLoading = false;
    GridLayoutManager gridLayoutManager;
    boolean ispull;
    FloatingActionButton fab;

    /* Upload image parameter*/
    public static final int REQUEST_IMAGE = 100;
    Uri uri;
    File file = null;

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

        setUserVisibleHint(true);
        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            // Refresh your fragment here
            if (imageListAdapter == null) {
                callImageGalleryData();

            }
            fab = getActivity().findViewById(R.id.fab);
            fab.setBackgroundResource(R.drawable.ic_share_arrow);
            fab.show();

            setListiner();


            ImagePickerActivity.clearCache(mContext);
        }
    }


    public void setListiner() {
        gridLayoutManager = new GridLayoutManager(mContext, 3);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
        fragmentImageBinding.imageRcyList.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView


        fragmentImageBinding.refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callImageGalleryPullData();
                fragmentImageBinding.refreshView.setRefreshing(false);
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
                        ispull = false;
                        pageIndex = pageIndex + 1;
                        loadMore();

                    }
                }
            }
        });

       fab.setOnClickListener(this);

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
                        imageDetailModelsList = imageMainModel.getData();

                        addOldNewValue(imageDetailModelsList);
                        if (imageListAdapter != null && imageDetailModelsList.size() > 0) {
                            imageListAdapter.addMoreDataToList(imageDetailModelsList);
                            // just append more data to current list
                        } else if(imageListAdapter!=null && imageDetailModelsList.size()==0){
                            isLoading = true;
                            addOldNewValue (imageMainModel.getData());
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
        return map;
    }

    public void fillImageGallery() {
        imageListAdapter = new ImageListAdapter(mContext, imageDetailModelsList, new image_click() {
            @Override
            public void image_more_click() {
                imageClickData = "";
                imageClickData = String.valueOf(imageListAdapter.getData());
                imageClickData = imageClickData.replaceAll("\\[", "").replaceAll("\\]", "");
                Log.d("imageClickData", "" + imageClickData);
                Intent gallerydetailIntent = new Intent(mContext, GalleryImageDetailActivity.class);
                gallerydetailIntent.putExtra("positon", imageClickData);
                gallerydetailIntent.putStringArrayListExtra("data", galleryImageUrl);
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
        imageDetailModelsList.clear();
    }

    public void addOldNewValue(List<ImageDetailModel> result) {
        for (int i = 0; i < result.size(); i++) {
            galleryImageUrl.addAll(Collections.singleton(result.get(i).getGalleryURL()));
        }
        Log.d("galleryImageUrl", "" + galleryImageUrl.size());

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
                        imageDetailModelsList = imageMainModel.getData();

//                        addOldNewPullValue (imageDetailModelsList);
                        imageListAdapter.notifyDataSetChanged();
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
        map.put("PageIndex", "0");
        map.put("PageSize", "15");
        return map;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:

                break;

        }
    }

    public void uploadImage(){
//        Dexter.withActivity(mContext)
//                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .withListener(new MultiplePermissionsListener() {
//                    @Override
//                    public void onPermissionsChecked(MultiplePermissionsReport report) {
//                        if (report.areAllPermissionsGranted()) {
//                            showImagePickerOptions();
//                        }
//
//                        if (report.isAnyPermissionPermanentlyDenied()) {
//                            showSettingsDialog();
//                        }
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
//                        token.continuePermissionRequest();
//                    }
//                }).check();
    }
}


