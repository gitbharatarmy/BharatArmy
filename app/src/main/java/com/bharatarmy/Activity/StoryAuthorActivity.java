package com.bharatarmy.Activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bharatarmy.Adapter.StoryAuthorAdapter;
import com.bharatarmy.Adapter.StoryLsitAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityStoryAuthorBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class StoryAuthorActivity extends AppCompatActivity {

    ActivityStoryAuthorBinding activityStoryAuthorBinding;
    Context mContext;
    StoryAuthorAdapter storyLsitAdapter;
    List<ImageDetailModel> storyDetailModelList;

    int pageIndex = 0;
    boolean isLoading = false;
    GridLayoutManager gridLayoutManager;
    boolean ispull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityStoryAuthorBinding= DataBindingUtil.setContentView(this,R.layout.activity_story_author);
        mContext=StoryAuthorActivity.this;
        setSupportActionBar(activityStoryAuthorBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activityStoryAuthorBinding.collapsingToolbar.setTitle("Yash Mittal");

        loadBackdrop();
        callStoryData();
        setListiner();
    }

    private void loadBackdrop() {
        final ImageView imageView = findViewById(R.id.backdrop);
        Glide.with(mContext).load(R.drawable.yashmittal).into(imageView);
    }
    public void setListiner() {
//        activityStoryAuthorBinding.shimmerViewContainer.startShimmerAnimation();
        gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // set Horizontal Orientation
        activityStoryAuthorBinding.storyAuthorRcvList.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView


        activityStoryAuthorBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callStoryPullData();
                activityStoryAuthorBinding.swipeRefreshLayout.setRefreshing(false);
            }
        });


        activityStoryAuthorBinding.storyAuthorRcvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

                    }
                }
            }
        });

    }


//     Api calling GetStoryData
    public void callStoryData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error),StoryAuthorActivity.this);
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
//                        activityStoryAuthorBinding.shimmerViewContainer.stopShimmerAnimation();
//                        activityStoryAuthorBinding.shimmerViewContainer.setVisibility(View.GONE);

                        if (storyLsitAdapter != null && storyDetailModelList.size() > 0) {
                            storyLsitAdapter.addMoreDataToList(storyDetailModelList);
                            // just append more data to current list
                        }else if(storyLsitAdapter!=null && storyDetailModelList.size()==0){
//                            Utils.ping(mContext,"No more data available");
                            Log.d("pageIndex",""+pageIndex);
                            isLoading = true;
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
        storyLsitAdapter = new StoryAuthorAdapter(mContext, storyDetailModelList, new image_click() {
            @Override
            public void image_more_click() {

            }
        });
        activityStoryAuthorBinding.storyAuthorRcvList.setItemAnimator(new DefaultItemAnimator());
        activityStoryAuthorBinding.storyAuthorRcvList.setAdapter(storyLsitAdapter);

    }

    private void loadMore() {
        callStoryData();
    }


    // Api calling GetStoryPullData
    public void callStoryPullData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error),StoryAuthorActivity.this);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               StoryAuthorActivity.this.finish();
                break;
        }
        return true;
    }



}
