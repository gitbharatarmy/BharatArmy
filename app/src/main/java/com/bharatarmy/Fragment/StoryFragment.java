package com.bharatarmy.Fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bharatarmy.Activity.GalleryImageDetailActivity;
import com.bharatarmy.Activity.MoreStoryActivity;
import com.bharatarmy.Adapter.ImageListAdapter;
import com.bharatarmy.Adapter.StoryLsitAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.EndlessRecyclerViewScrollListener;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentImageBinding;
import com.bharatarmy.databinding.FragmentStoryBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;


public class StoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentStoryBinding fragmentStoryBinding;
    private View rootView;
    private Context mContext;

    StoryLsitAdapter storyLsitAdapter;
    List<ImageDetailModel> storyDetailModelList;

    int pageIndex = 0;


    boolean isLoading = false;
    GridLayoutManager gridLayoutManager;
    boolean ispull;

    public StoryFragment() {
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
        fragmentStoryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_story, container, false);

        rootView = fragmentStoryBinding.getRoot();
        mContext = getActivity().getApplicationContext();

            callStoryData();

        setListiner();
        return rootView;
    }

    public void setListiner() {
        fragmentStoryBinding.shimmerViewContainer.startShimmerAnimation();
        gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
        fragmentStoryBinding.storyRcyList.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView


        fragmentStoryBinding.refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callStoryPullData();
                fragmentStoryBinding.refreshView.setRefreshing(false);
            }
        });


        fragmentStoryBinding.storyRcyList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if (!isLoading) {
                    if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == storyDetailModelList.size() - 1) {
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


    // Api calling GetStoryData
    public void callStoryData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getBAStories(getStoryData(), new retrofit.Callback<ImageMainModel>() {
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

                        storyDetailModelList = imageMainModel.getData();
                        fragmentStoryBinding.shimmerViewContainer.stopShimmerAnimation();
                        fragmentStoryBinding.shimmerViewContainer.setVisibility(View.GONE);

                        if (storyLsitAdapter != null && storyDetailModelList.size() > 0) {
                            storyLsitAdapter.addMoreDataToList(storyDetailModelList);
                            // just append more data to current list
                        }else if(storyLsitAdapter!=null && storyDetailModelList.size()==0){
                            Utils.ping(mContext,"No more data available");
                            addOldNewValue (imageMainModel.getData());
                        }
                        else {
                            fillStoryGallery();
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

    private Map<String, String> getStoryData() {
        Map<String, String> map = new HashMap<>();
        map.put("PageIndex", String.valueOf(pageIndex));
        map.put("PageSize", "14");
        return map;
    }

    public void fillStoryGallery() {
        storyLsitAdapter = new StoryLsitAdapter(mContext, storyDetailModelList, new image_click() {
            @Override
            public void image_more_click() {

            }
        });
        fragmentStoryBinding.storyRcyList.setItemAnimator(new DefaultItemAnimator());
        fragmentStoryBinding.storyRcyList.setAdapter(storyLsitAdapter);

    }

    private void loadMore() {
        callStoryData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        storyDetailModelList.clear();
    }



    // Api calling GetStoryPullData
    public void callStoryPullData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getBAStories(getStoryPullData(), new retrofit.Callback<ImageMainModel>() {
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
                        storyDetailModelList = imageMainModel.getData();

//                        addOldNewPullValue (imageDetailModelsList);
                        storyLsitAdapter.notifyDataSetChanged();
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

    private Map<String, String> getStoryPullData() {
        Map<String, String> map = new HashMap<>();
        map.put("PageIndex","0");
        map.put("PageSize", "14");
        return map;
    }

    public void addOldNewValue(List<ImageDetailModel> result) {

        storyDetailModelList=result;
        storyLsitAdapter.notifyDataSetChanged();
    }
}


