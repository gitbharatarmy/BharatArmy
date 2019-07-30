package com.bharatarmy.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bharatarmy.Adapter.StoryCategoryAdapter;
import com.bharatarmy.Adapter.StoryLsitAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentStoryCategoryBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.leinardi.android.speeddial.SpeedDialView;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;


public class StoryCategoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String CategoryName="categoryName";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentStoryCategoryBinding fragmentStoryCategoryBinding;
    private View rootView;
    private Context mContext;

    List<ImageDetailModel> storyDetailModelList;
    StoryCategoryAdapter storyCategoryAdapter;
    public static OnItemClick mListener;


    FloatingActionButton fab;
    SpeedDialView speedDial;

    GridLayoutManager gridLayoutManager;

    private static final long DURATION = 500;
    String categoryIdStr, categoryNameStr,wheretocome;

    public StoryCategoryFragment() {
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
        fragmentStoryCategoryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_story_category, container, false);

        rootView = fragmentStoryCategoryBinding.getRoot();
        mContext = getActivity().getApplicationContext();
        fab = getActivity().findViewById(R.id.fab);
        fab.hide();
        speedDial=getActivity().findViewById(R.id.speedDial);
        speedDial.setVisibility(View.GONE);
        callStoryCategoryData();

        setListiner();
        return rootView;
    }

    public void setListiner() {
        Bundle arguments = getArguments();
        categoryIdStr=arguments.getString("categoryId");
        categoryNameStr = arguments.getString("categoryName");
        wheretocome=arguments.getString("wheretocome");
        fragmentStoryCategoryBinding.categoryName.setText(categoryNameStr);

        fragmentStoryCategoryBinding.shimmerViewContainer.startShimmerAnimation();
        gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
        fragmentStoryCategoryBinding.storyCategoryRcyList.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

        fragmentStoryCategoryBinding.closeFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation slideUp = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_right);
                fragmentStoryCategoryBinding.categoryLinear.setVisibility(View.GONE);
                fragmentStoryCategoryBinding.categoryLinear.startAnimation(slideUp);
                fragmentStoryCategoryBinding.storyCategoryRcyList.startAnimation(slideUp);
                fragmentStoryCategoryBinding.storyCategoryRcyList.setVisibility(View.GONE);
                mListener.onStoryCategoryBack(wheretocome);
            }
        });


    }


    // Api calling GetStoryData
    public void callStoryCategoryData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getBAStories(getStoryCategoryData(), new retrofit.Callback<ImageMainModel>() {
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
                        fragmentStoryCategoryBinding.shimmerViewContainer.stopShimmerAnimation();
                        fragmentStoryCategoryBinding.shimmerViewContainer.setVisibility(View.GONE);

                        Log.d("categoryData", "" + storyDetailModelList.size());
                        storyCategoryAdapter = new StoryCategoryAdapter(mContext,categoryNameStr, storyDetailModelList, new image_click() {
                            @Override
                            public void image_more_click() {


                            }
                        });
                        gridLayoutManager = new GridLayoutManager(mContext, 2);
                        gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
                        fragmentStoryCategoryBinding.storyCategoryRcyList.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
                        fragmentStoryCategoryBinding.storyCategoryRcyList.setItemAnimator(new DefaultItemAnimator());
                        fragmentStoryCategoryBinding.storyCategoryRcyList.setAdapter(storyCategoryAdapter);


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

    private Map<String, String> getStoryCategoryData() {
        Map<String, String> map = new HashMap<>();
        map.put("PageIndex", "0");
        map.put("PageSize", "14");
        return map;
    }

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
        void onStoryCategoryBack(String wheretocome);


    }
}


