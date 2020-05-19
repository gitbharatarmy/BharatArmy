package com.bharatarmy.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bharatarmy.Adapter.StoryCategoryAdapter;
import com.bharatarmy.Adapter.StoryLsitAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentStoryBinding;
import com.bharatarmy.speeddialView.SpeedDialView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public String isUpdateAvailable, isForceUpdateAvailable, currentVersionStr;
    StoryLsitAdapter storyLsitAdapter;
    List<ImageDetailModel> storyDetailModelList;
    StoryCategoryAdapter storyCategoryAdapter;

    List<ImageDetailModel> storyDetailModelList1 = new ArrayList<>();



    SpeedDialView speedDial;

    int storypagesize=14;
    int pageIndex = 0;
    boolean isStoryLoading = true;
    GridLayoutManager gridLayoutManager;


    String categoryIdStr, categoryNameStr, wheretocome;
    public static OnItemClick mListener;

    public StoryFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static StoryFragment newInstance(String param1, String param2) {
        StoryFragment fragment = new StoryFragment();
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

        speedDial = getActivity().findViewById(R.id.speedDial);
        speedDial.setVisibility(View.GONE);
        callStoryData();

        setListiner();
        return rootView;
    }

    public void setListiner() {
        fragmentStoryBinding.shimmerViewContainer.startShimmerAnimation();
        gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
        fragmentStoryBinding.storyRcyList.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView


        fragmentStoryBinding.refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                callStoryPullData();

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


                if (isStoryLoading == true) {
                    if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == storyDetailModelList1.size() - 1) {
                        //bottom of list!
                        isStoryLoading = true;
                        pageIndex++;
                        fragmentStoryBinding.bottomProgressbarLayout.setVisibility(View.VISIBLE);
                        loadMore();

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
            public void success(ImageMainModel storyMainModel, Response response) {
                Utils.dismissDialog();
                if (storyMainModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (storyMainModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (storyMainModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (storyMainModel.getIsValid() == 1) {
                    isUpdateAvailable = String.valueOf(storyMainModel.getIsUpdateAvailable());
                    isForceUpdateAvailable = String.valueOf(storyMainModel.getIsForceUpdate());
//                    isForceUpdateAvailable = "0";
                    currentVersionStr = String.valueOf(storyMainModel.getCurrentVersion());
                    if (isUpdateAvailable.equalsIgnoreCase("1")) {
                        Utils.checkupdateApplication(mContext, getActivity(), isForceUpdateAvailable, currentVersionStr);
                    }
                    if (storyMainModel.getData() != null) {
                        fragmentStoryBinding.shimmerViewContainer.stopShimmerAnimation();
                        fragmentStoryBinding.shimmerViewContainer.setVisibility(View.GONE);
                        fragmentStoryBinding.bottomProgressbarLayout.setVisibility(View.GONE);

                        if (storyMainModel.getData().size() != 0) {
                            if (storyDetailModelList1.size() == 0) {
                                storyDetailModelList1.addAll(0, storyMainModel.getData());
                            } else {
                                storyDetailModelList1.addAll(storyDetailModelList1.size(), storyMainModel.getData());
                            }
                        }
                        storyDetailModelList = storyMainModel.getData();


                        if (storyDetailModelList1.size() < storypagesize) {
                            isStoryLoading = false;
                        }

//                        addOldNewValue(storyDetailModelList1);

                        if (storyLsitAdapter != null && storyDetailModelList.size() > 0) {
                            storyLsitAdapter.addMoreDataToList(storyDetailModelList);
                            // just append more data to current list
                        } else if (storyLsitAdapter != null && storyDetailModelList.size() == 0) {
//                            Utils.ping(mContext,"No more data available");
                            Log.d("pageIndex", "" + pageIndex);
                            isStoryLoading = false;

                        } else {
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
        map.put("PageSize",String.valueOf(storypagesize) );
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));

        return map;
    }

    public void fillStoryGallery() {
        storyLsitAdapter = new StoryLsitAdapter(mContext, storyDetailModelList, getActivity(), new image_click() {
            @Override
            public void image_more_click() {


                String getCategoryData = String.valueOf(storyLsitAdapter.getData());

                String[] splitString = getCategoryData.split("\\|");

                categoryIdStr = splitString[0];
                categoryNameStr = splitString[1].substring(0, splitString[1].length() - 1);

                Log.d("categoryIdSTr :", categoryIdStr + " categoryNameStr :" + categoryNameStr);

                // slide-up animation
                Animation slideUp = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_right);
                fragmentStoryBinding.storyRcyList.startAnimation(slideUp);
                fragmentStoryBinding.storyRcyList.setVisibility(View.GONE);
                wheretocome = "Story";
                mListener.onStoryCategory(categoryIdStr, categoryNameStr, wheretocome);

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
        if (storyDetailModelList!=null){
            if (storyDetailModelList.size() != 0) {
                storyDetailModelList.clear();
            }
        }


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
            public void success(ImageMainModel storyMainModel, Response response) {
                Utils.dismissDialog();
                if (storyMainModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (storyMainModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (storyMainModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (storyMainModel.getIsValid() == 1) {
                    isUpdateAvailable = String.valueOf(storyMainModel.getIsUpdateAvailable());
                    isForceUpdateAvailable = String.valueOf(storyMainModel.getIsForceUpdate());
//                    isForceUpdateAvailable = "0";
                    currentVersionStr = String.valueOf(storyMainModel.getCurrentVersion());
                    if (isUpdateAvailable.equalsIgnoreCase("1")) {
                        Utils.checkupdateApplication(mContext, getActivity(), isForceUpdateAvailable, currentVersionStr);
                    }
                    if (storyMainModel.getData() != null) {
                        storyDetailModelList1.addAll(0, storyMainModel.getData());
                        Set<ImageDetailModel> unique = new LinkedHashSet<ImageDetailModel>(storyDetailModelList1);
                        storyDetailModelList1 = new ArrayList<ImageDetailModel>(unique);
                        storyLsitAdapter.clear();
                        storyLsitAdapter.addMoreDataToList(storyDetailModelList1);

                        Log.d("pullDataList : ", "" + storyDetailModelList1.size());
//
                        fragmentStoryBinding.refreshView.setRefreshing(false);
                        isStoryLoading = true;

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
        map.put("PageIndex", "0");
        map.put("PageSize", String.valueOf(storypagesize));
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));

        return map;
    }

//    public void addOldNewValue(List<ImageDetailModel> result) {
//
//        storyDetailModelList = result;
//        storyLsitAdapter.notifyDataSetChanged();
//    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemClick) {
            mListener = (OnItemClick) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnItemClick {
        void onStoryCategory(String categoryId, String categoryName, String wheretocome);


    }
}


