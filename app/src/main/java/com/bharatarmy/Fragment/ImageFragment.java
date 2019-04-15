package com.bharatarmy.Fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
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
import com.bharatarmy.R;
import com.bharatarmy.Utility.EndlessRecyclerViewScrollListener;
import com.bharatarmy.Utility.EqualSpacingItemDecoration;
import com.bharatarmy.Utility.GridSpacingItemDecoration;
import com.bharatarmy.databinding.FragmentImageBinding;

import java.util.ArrayList;
import java.util.List;


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
    List<String> imageList = new ArrayList<>();
    String imageClickData;
    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;

    GridLayoutManager gridLayoutManager;

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

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null) {
            setDataValue();
            setListiner();
        }
    }


    public void setDataValue() {
        if (imageList!=null)
        {
            imageList.clear();
        }
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/1.jpg");
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/2.jpg");
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/3.jpg");
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/4.jpeg");
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/5.jpeg");
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/6.jpeg");
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/7.jpeg");
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/8.jpeg");
        imageList.add("https://www.bharatarmy.com/Docs/Gallery/9.jpeg");

        imageListAdapter = new ImageListAdapter(mContext, imageList, new image_click() {
            @Override
            public void image_more_click() {
                imageClickData="";
                imageClickData = String.valueOf(imageListAdapter.getData());
                imageClickData = imageClickData.replaceAll("\\[", "").replaceAll("\\]","");
                Log.d("imageClickData", "" + imageClickData);

                Intent gallerydetailIntent = new Intent(mContext, GalleryImageDetailActivity.class);
                gallerydetailIntent.putExtra("positon", imageClickData);
                startActivity(gallerydetailIntent);
            }
        });
        gridLayoutManager = new GridLayoutManager(mContext, 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
        fragmentImageBinding.imageRcyList.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        fragmentImageBinding.imageRcyList.addItemDecoration(new GridSpacingItemDecoration(3, 10, true));
        fragmentImageBinding.imageRcyList.setAdapter(imageListAdapter);

    }

    public void setListiner() {
        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        fragmentImageBinding.imageRcyList.addOnScrollListener(scrollListener);

        fragmentImageBinding.refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fragmentImageBinding.refreshView.setRefreshing(false);
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

        // 1. First, clear the array of data
//        imageList.clear();
//// 2. Notify the adapter of the update
//        imageListAdapter.notifyDataSetChanged(); // or notifyItemRangeRemoved
//// 3. Reset endless scroll listener when performing a new search
//        scrollListener.resetState();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        imageList.clear();
    }
}


