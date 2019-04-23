package com.bharatarmy.Fragment;

import android.content.Context;
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

import com.bharatarmy.Adapter.FTPListAdapter;
import com.bharatarmy.Adapter.StoryLsitAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentFtBinding;
import com.bharatarmy.databinding.FragmentStoryBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;


public class FTPFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentFtBinding fragmentFtBinding;
    private View rootView;
    private Context mContext;

    FTPListAdapter ftpListAdapter;
    List<ImageDetailModel> ftpDetailModelList;
    List<ImageDetailModel> galleryImageUrl;
    int pageIndex = 0;


    boolean isLoading = false;
    GridLayoutManager gridLayoutManager;
    boolean ispull;

    public FTPFragment() {
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
        fragmentFtBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ft, container, false);

        rootView = fragmentFtBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        callFTPData();

        setListiner();
        return rootView;
    }

    public void setListiner() {
        fragmentFtBinding.shimmerViewContainer.startShimmerAnimation();
        gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
        fragmentFtBinding.ftpRcyList.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView


        fragmentFtBinding.refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callFTPPullData();
                fragmentFtBinding.refreshView.setRefreshing(false);
            }
        });


        fragmentFtBinding.ftpRcyList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if (!isLoading) {
                    if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == ftpDetailModelList.size() - 1) {
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


    // Api calling GetFTPData
    public void callFTPData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getBAFTP(getFTPData(), new retrofit.Callback<ImageMainModel>() {
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
                        fragmentFtBinding.shimmerViewContainer.stopShimmerAnimation();
                        fragmentFtBinding.shimmerViewContainer.setVisibility(View.GONE);
                        ftpDetailModelList = imageMainModel.getData();
                        for (int i = 0; i < imageMainModel.getData().size(); i++) {
                            String data = imageMainModel.getData().get(i).getCategoryTypes();
                            if (!data.equalsIgnoreCase("")) {
                                if (data.contains(",")) {
                                    String[] splitStr = data.split(",");
                                    imageMainModel.getData().get(i).setStr1(splitStr[0]);
                                    imageMainModel.getData().get(i).setStr2(splitStr[1]);
                                    imageMainModel.getData().get(i).setStr3(splitStr[2]);
                                } else {
                                    imageMainModel.getData().get(i).setStr1(data);
                                    imageMainModel.getData().get(i).setStr2("1");
                                    imageMainModel.getData().get(i).setStr3("1");
                                }
                            }
                        }

                        if (ftpListAdapter != null && ftpDetailModelList.size() > 0) {
                            ftpListAdapter.addMoreDataToList(ftpDetailModelList);
                            // just append more data to current list
                        } else if(ftpListAdapter!=null && ftpDetailModelList.size()==0){
                            Utils.ping(mContext,"No more data available");
                            addOldNewValue (imageMainModel.getData());
                        } else {
                            fillFTPGallery();
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

    private Map<String, String> getFTPData() {
        Map<String, String> map = new HashMap<>();
        map.put("PageIndex", String.valueOf(pageIndex));
        map.put("PageSize", "14");
        return map;
    }

    public void fillFTPGallery() {

        ftpListAdapter = new FTPListAdapter(mContext, ftpDetailModelList, new image_click() {
            @Override
            public void image_more_click() {
            }
        });
        fragmentFtBinding.ftpRcyList.setItemAnimator(new DefaultItemAnimator());
        fragmentFtBinding.ftpRcyList.setAdapter(ftpListAdapter);

    }

    private void loadMore() {
        callFTPData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ftpDetailModelList.clear();
    }



    // Api calling GetFTPPullData
    public void callFTPPullData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getBAFTP(getFTPPullData(), new retrofit.Callback<ImageMainModel>() {
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
                        ftpDetailModelList = imageMainModel.getData();

                        ftpListAdapter.notifyDataSetChanged();
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

    private Map<String, String> getFTPPullData() {
        Map<String, String> map = new HashMap<>();
        map.put("PageIndex","0");
        map.put("PageSize", "14");
        return map;
    }

    public void addOldNewValue(List<ImageDetailModel> result) {

        ftpDetailModelList=result;
       ftpListAdapter.notifyDataSetChanged();
    }
}


