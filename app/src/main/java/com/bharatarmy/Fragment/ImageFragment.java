package com.bharatarmy.Fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bharatarmy.Activity.GalleryImageDetailActivity;
import com.bharatarmy.Adapter.ImageListAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.EndlessRecyclerViewScrollListener;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentImageBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;


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
    List<ImageDetailModel> imageDetailModelsListforadapter;
    ArrayList<String> galleryImageUrl = new ArrayList<>();
    ImageDetailModel imageDetailModel;
    boolean isMoreDataAvailable = true;
    String imageClickData;
    int pageIndex = 0;
    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;
    boolean isLoading = false;
    GridLayoutManager gridLayoutManager;
    boolean ispull;

    public ImageFragment() {
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
        // Inflate the layout for this fragment
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
            setListiner();
        }
    }


    public void setListiner() {
        gridLayoutManager = new GridLayoutManager(mContext, 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
        fragmentImageBinding.imageRcyList.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
//         Retain an instance so that you can call `resetState()` for fresh searches
//        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                // Triggered only when new data needs to be appended to the list
//                // Add whatever code is needed to append new items to the bottom of the list
////                if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == imageDetailModelsList.size() - 1) {
//                    //bottom of list!
//                    loadNextDataFromApi(page);
////                }
//
//
//            }
//        };
//        // Adds the scroll listener to RecyclerView
//        fragmentImageBinding.imageRcyList.addOnScrollListener(scrollListener);

        fragmentImageBinding.refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callImageGalleryPullData();
                fragmentImageBinding.refreshView.setRefreshing(false);
            }
        });

//        fragmentImageBinding.imageRcyList.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
////                pageIndex= String.valueOf(newState);
////                callImageGalleryData();
//
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
////                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
////                if (dy > 0) {
//                    if (!isLoading) {
//                        if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == imageDetailModelsList.size() - 1) {
//                            //bottom of list!
//                            pageIndex++;
//                            callImageGalleryData();
//                            isLoading = true;
//                        loadMore();
//
//                        }
//                    }
////                } else if (dy < 0) {
////                    if (!isLoading) {
////                        if (gridLayoutManager != null && gridLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
////                            //bottom of list!
////                            callImageGalleryData();
////                            isLoading = true;
////                        loadMore();
////
////                        }
////                    }
////                }
//
//            }
//        });

//        fragmentImageBinding.imageRcyList.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (isLoading)
//                    return;
//                int visibleItemCount = gridLayoutManager.getChildCount();
//                int totalItemCount = gridLayoutManager.getItemCount();
//                int pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();
//                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
//                    //Scrolled to End of list
//                    if (imageListAdapter != null && imageListAdapter.getItemCount() == imageDetailModelsList.size() && isMoreDataAvailable) {
//
//                       callImageGalleryData();
//
//
//                    }
//                }
//            }
//
//        });


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
                        isLoading = true;
                    }
                }
            }
        });


    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
        pageIndex = offset;
        callImageGalleryData();
        // 1. First, clear the array of data
        imageDetailModelsList.clear();
        // 2. Notify the adapter of the update

//      imageListAdapter.notifyDataSetChanged();

//        imageListAdapter.notifyItemRangeChanged(imageDetailModelsList.size() - 1, imageDetailModelsList.size()); // or notifyItemRangeRemoved
//      // 3. Reset endless scroll listener when performing a new search
        scrollListener.resetState();

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

                        addOldNewValue (imageDetailModelsList);
                        if (imageListAdapter != null && imageDetailModelsList.size() > 0) {
                            imageListAdapter.addMoreDataToList(imageDetailModelsList);
                            // just append more data to current list
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
        map.put("PageIndex","0");
        map.put("PageSize", "15");
        return map;
    }
}


