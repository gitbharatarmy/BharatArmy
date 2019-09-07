package com.bharatarmy.Fragment;

import android.content.Context;
import android.content.Intent;
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

import com.bharatarmy.Activity.GalleryImageDetailActivity;
import com.bharatarmy.Activity.VideoDetailActivity;
import com.bharatarmy.Adapter.ImageListAdapter;
import com.bharatarmy.Adapter.VideoListAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.EndlessRecyclerViewScrollListener;
import com.bharatarmy.Utility.GridSpacingItemDecoration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentVideoBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

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

    GridLayoutManager gridLayoutManager;
    int pageIndex = 0;

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
        }
    }

    public void setListiner() {
        gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
        fragmentVideoBinding.videoRcvList.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView


        fragmentVideoBinding.refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callVideoGalleryData();
                fragmentVideoBinding.refreshView.setRefreshing(false);
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


                if (!isLoading) {
                    if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == videoDetailModelsList.size() - 1) {
                        //bottom of list!
                        ispull = false;
                        pageIndex = pageIndex + 1;
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
        return map;
    }

    public void fillVideoGallery() {
        videoListAdapter = new VideoListAdapter(mContext, videoDetailModelsList, new image_click() {
            @Override
            public void image_more_click() {
                imageClickData = "";
                imageClickData = String.valueOf(videoListAdapter.getData());
                imageClickData = imageClickData.replaceAll("\\[", "").replaceAll("\\]", "");
                String[] spiltvalue = imageClickData.split("\\|");


                Log.d("imageClickData :", imageClickData + " spiltvalue :" + spiltvalue[0] + "spiltvalue1:" + spiltvalue[1]);

                Intent videogallerydetailIntent = new Intent(mContext, VideoDetailActivity.class);
                videogallerydetailIntent.putExtra("videoData", spiltvalue[0]);
                videogallerydetailIntent.putExtra("videoName", spiltvalue[1]);
                videogallerydetailIntent.putExtra("WhereToVideoCome", "VideoFragment");
                startActivity(videogallerydetailIntent);
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

        callVideoGalleryPullData();


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
                        videoDetailModelsList = imageMainModel.getData();
                            fillVideoGallery();

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
        map.put("PageSize", "20");
        return map;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        videoDetailModelsList.clear();
    }
}
