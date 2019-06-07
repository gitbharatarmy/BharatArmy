package com.bharatarmy.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bharatarmy.Adapter.StoryAuthorAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.R;
import com.bharatarmy.TravelDesignModule.ParallaxRecyclerAdapter;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityStoryAuthorNewBinding;
import com.google.android.material.appbar.AppBarLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class StoryAuthorActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityStoryAuthorNewBinding activityStoryAuthorNewBinding;
    Context mContext;
    StoryAuthorAdapter storyLsitAdapter;
    List<ImageDetailModel> storyDetailModelList;
    int pageIndex = 0;
    boolean isLoading = false;
    GridLayoutManager gridLayoutManager;
    boolean ispull;
    int authorIdStr;

    ParallaxRecyclerAdapter<ImageDetailModel> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityStoryAuthorNewBinding = DataBindingUtil.setContentView(this, R.layout.activity_story_author_new);
        mContext = StoryAuthorActivity.this;
        authorIdStr=getIntent().getIntExtra("StoryauthorId",0);
        Log.d("authorId",""+authorIdStr);
        callStoryData();
        setListiner();

    }

    public void setListiner() {
        setSupportActionBar(activityStoryAuthorNewBinding.toolbarAndroid);

        activityStoryAuthorNewBinding.shimmerViewContainer.startShimmerAnimation();
        gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
        activityStoryAuthorNewBinding.storyAuthorRcvList.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView


        activityStoryAuthorNewBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callStoryPullData();
                activityStoryAuthorNewBinding.swipeRefreshLayout.setRefreshing(false);
            }
        });


        activityStoryAuthorNewBinding.storyAuthorRcvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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


        activityStoryAuthorNewBinding.androidAppbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(final AppBarLayout appBarLayout, int verticalOffset) {
                //Initialize the size of the scroll
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                //Check if the view is collapsed
                if (scrollRange + verticalOffset == 0) {
                    activityStoryAuthorNewBinding.toolbarAndroid.setBackgroundColor(ContextCompat.getColor(mContext, R.color.heading_bg));
//                    activityStoryAuthorNewBinding.collapsingToolbarLayoutAndroidExample.setTitle("Yash Mittal Story");
//                    Typeface typeface = ResourcesCompat.getFont(mContext, R.font.helveticaneueltstdbdcn);
//                    activityStoryAuthorNewBinding.collapsingToolbarLayoutAndroidExample.setCollapsedTitleTypeface(typeface);
//                    activityStoryAuthorNewBinding.collapsingToolbarLayoutAndroidExample.setExpandedTitleTypeface(typeface);
//                    activityStoryAuthorNewBinding.collapsingToolbarLayoutAndroidExample.setCollapsedTitleGravity(Gravity.START);
//                    activityStoryAuthorNewBinding.collapsingToolbarLayoutAndroidExample.setExpandedTitleGravity(Gravity.START);

                    activityStoryAuthorNewBinding.followLinearToolbar.setVisibility(View.VISIBLE);
                    activityStoryAuthorNewBinding.storyTitleTxt.setVisibility(View.VISIBLE);
                    isShow = true;
                } else if (isShow) {
                    activityStoryAuthorNewBinding.toolbarAndroid.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                    activityStoryAuthorNewBinding.collapsingToolbarLayoutAndroidExample.setTitle(" ");
//                    activityStoryAuthorNewBinding.followLinearToolbar.setVisibility(View.GONE);
//                    activityStoryAuthorNewBinding.followLinearToolbar.setVisibility(View.GONE);

                    activityStoryAuthorNewBinding.storyTitleTxt.setVisibility(View.GONE);
                    isShow = false;
                }
            }
        });

        activityStoryAuthorNewBinding.backImg.setOnClickListener(this);
        activityStoryAuthorNewBinding.facebookLinear.setOnClickListener(this);
        activityStoryAuthorNewBinding.tiwtterLinear.setOnClickListener(this);
        activityStoryAuthorNewBinding.instagramLinear.setOnClickListener(this);
        activityStoryAuthorNewBinding.youtubeLinear.setOnClickListener(this);

    }


    //     Api calling GetStoryData
    public void callStoryData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), StoryAuthorActivity.this);
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
                        activityStoryAuthorNewBinding.shimmerViewContainer.stopShimmerAnimation();
                        activityStoryAuthorNewBinding.shimmerViewContainer.setVisibility(View.GONE);
                        activityStoryAuthorNewBinding.storyAuthorRcvList.setVisibility(View.VISIBLE);

                        if (storyLsitAdapter != null && storyDetailModelList.size() > 0) {
                            storyLsitAdapter.addMoreDataToList(storyDetailModelList);
                            // just append more data to current list
                        } else if (storyLsitAdapter != null && storyDetailModelList.size() == 0) {
//                            Utils.ping(mContext,"No more data available");
                            Log.d("pageIndex", "" + pageIndex);
                            isLoading = true;
                            addOldNewValue(imageMainModel.getData());
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
        map.put("PageSize", "14");
        map.put("AuthorId",String.valueOf(authorIdStr));
        return map;
    }

    public void fillStoryGallery() {
        Utils.setImageInImageView(storyDetailModelList.get(0).getAuthorImageURL(),activityStoryAuthorNewBinding.profileImage,mContext);
activityStoryAuthorNewBinding.userNameTxt.setText(storyDetailModelList.get(0).getAuthorName());
activityStoryAuthorNewBinding.storyTitleTxt.setText(storyDetailModelList.get(0).getAuthorName());
        storyLsitAdapter = new StoryAuthorAdapter(mContext, storyDetailModelList, new image_click() {
            @Override
            public void image_more_click() {

            }
        });
        activityStoryAuthorNewBinding.storyAuthorRcvList.setItemAnimator(new DefaultItemAnimator());
        activityStoryAuthorNewBinding.storyAuthorRcvList.setAdapter(storyLsitAdapter);


    }

    private void loadMore() {
        callStoryData();
    }


    // Api calling GetStoryPullData
    public void callStoryPullData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), StoryAuthorActivity.this);
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
                        activityStoryAuthorNewBinding.storyAuthorRcvList.setVisibility(View.VISIBLE);

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
        map.put("PageIndex", "0");
        map.put("PageSize", "14");
        map.put("AuthorId",String.valueOf(authorIdStr));
        return map;
    }

    public void addOldNewValue(List<ImageDetailModel> result) {
        storyDetailModelList = result;
        storyLsitAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                StoryAuthorActivity.this.finish();
                break;
            case R.id.facebook_linear:
                boolean installed_whatsapp = Utils.appInstalledOrNot("com.facebook.katana", mContext);
                if (installed_whatsapp) {
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
                    startActivity(launchIntent);
                } else {
                    Utils.ping(mContext, getResources().getString(R.string.app_not_installed));
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.facebook.katana")));
                }
                break;
            case R.id.tiwtter_linear:
                boolean installed_twitter = Utils.appInstalledOrNot("com.twitter.android", mContext);
                if (installed_twitter) {
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.twitter.android");
                    startActivity(launchIntent);
                } else {
                    Utils.ping(mContext, getResources().getString(R.string.app_not_installed));
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.twitter.android")));
                }
                break;
            case R.id.instagram_linear:
                boolean installed_instagram = Utils.appInstalledOrNot("com.instagram.android", mContext);
                if (installed_instagram) {
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.instagram.android");
                    startActivity(launchIntent);
                } else {
                    Utils.ping(mContext, getResources().getString(R.string.app_not_installed));
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.instagram.android")));
                }
                break;
            case R.id.youtube_linear:
                boolean installed_youtube = Utils.appInstalledOrNot("com.google.android.youtube", mContext);
                if (installed_youtube) {
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
                    startActivity(launchIntent);
                } else {
                    Utils.ping(mContext, getResources().getString(R.string.app_not_installed));
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + "com.google.android.youtube")));
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                StoryAuthorActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
