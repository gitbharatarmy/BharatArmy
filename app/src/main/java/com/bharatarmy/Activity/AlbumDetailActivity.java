package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bharatarmy.Adapter.AlbumDetailListAdapter;
import com.bharatarmy.Adapter.ImageListAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityAlbumDetailBinding;
import com.bharatarmy.databinding.ActivityStoryDetailBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class AlbumDetailActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityAlbumDetailBinding activityAlbumDetailBinding;
    Context mContext;
    List<ImageDetailModel> albumdetailDataList;
    AlbumDetailListAdapter albumDetailListAdapter;
String albumHeadingStr,albumIdStr, albumThumbStr;
    int pageIndex = 0;
    boolean isLoading = false;
    GridLayoutManager gridLayoutManager;
    boolean ispull;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAlbumDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_album_detail);

        mContext = AlbumDetailActivity.this;

        init();

        setListiner();


    }


    public void init() {
        albumIdStr=getIntent().getStringExtra("albumId");
        albumHeadingStr=getIntent().getStringExtra("albumName");
        albumThumbStr=getIntent().getStringExtra("albumThumb");
        setSupportActionBar(activityAlbumDetailBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        Utils.setImageInImageView(albumThumbStr,activityAlbumDetailBinding.backdrop,mContext);
//        activityAlbumDetailBinding.showAlbumTitleTxt.setText(albumHeadingStr);
        activityAlbumDetailBinding.shimmerViewContainer.startShimmerAnimation();

        gridLayoutManager = new GridLayoutManager(mContext, 3);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
        activityAlbumDetailBinding.albumDetailRcvList.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

        callAlbumDetailData();
    }


    public void setListiner() {
        activityAlbumDetailBinding.shimmerViewContainer.startShimmerAnimation();
        activityAlbumDetailBinding.showAlbumNameTxt.setText(albumHeadingStr);
        setSupportActionBar(activityAlbumDetailBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);


        activityAlbumDetailBinding.backImg.setOnClickListener(this);


        activityAlbumDetailBinding.albumRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callAlbumPullDetailData();
                activityAlbumDetailBinding.albumRefreshLayout.setRefreshing(false);
            }
        });

        activityAlbumDetailBinding.albumDetailRcvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoading) {
                    if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == albumdetailDataList.size() - 1) {
                        //bottom of list!
                        isLoading = false;
                        pageIndex = pageIndex + 1;
                        activityAlbumDetailBinding.progressBar.setVisibility(View.VISIBLE);

                        loadMore();
                    }
                }
            }
        });

        activityAlbumDetailBinding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
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
                    activityAlbumDetailBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.heading_bg));
                    activityAlbumDetailBinding.collapsingToolbar.setTitle(albumHeadingStr);
                    Typeface typeface = ResourcesCompat.getFont(mContext, R.font.helveticaneueltstdbdcn);
                    activityAlbumDetailBinding.collapsingToolbar.setCollapsedTitleTypeface(typeface);
                    activityAlbumDetailBinding.collapsingToolbar.setExpandedTitleTypeface(typeface);
                    activityAlbumDetailBinding.collapsingToolbar.setCollapsedTitleGravity(Gravity.START);
                    activityAlbumDetailBinding.collapsingToolbar.setExpandedTitleGravity(Gravity.START);
                    activityAlbumDetailBinding.toolbarBottomLeftView.setVisibility(View.VISIBLE);
                    isShow = true;
                } else if (isShow) {
                    activityAlbumDetailBinding.toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                    activityAlbumDetailBinding.toolbar.setTitle(" ");
                    activityAlbumDetailBinding.collapsingToolbar.setTitle(" ");
                    activityAlbumDetailBinding.toolbarBottomLeftView.setVisibility(View.VISIBLE);
                    isShow = false;
                }

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AlbumDetailActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                AlbumDetailActivity.this.finish();
                break;
        }
    }

    // Api calling GetAlbumDetailData
    public void callAlbumDetailData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), AlbumDetailActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getBAAlbumDetail(getAlbumDetailData(), new retrofit.Callback<ImageMainModel>() {
            @Override
            public void success(ImageMainModel albumDeatilMainModel, Response response) {
                Utils.dismissDialog();
                if (albumDeatilMainModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (albumDeatilMainModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (albumDeatilMainModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (albumDeatilMainModel.getIsValid() == 1) {

                    if (albumDeatilMainModel.getData() != null) {
                        activityAlbumDetailBinding.shimmerViewContainer.stopShimmerAnimation();
                        activityAlbumDetailBinding.shimmerViewContainer.setVisibility(View.GONE);
                        activityAlbumDetailBinding.progressBar.setVisibility(View.GONE);
                        albumdetailDataList = albumDeatilMainModel.getData();
                        Log.d("list : ",""+albumdetailDataList.size());
                        addOldNewValue(albumdetailDataList);
                        if (albumDetailListAdapter != null && albumdetailDataList.size() > 0) {
                            albumDetailListAdapter.addMoreDataToList(albumdetailDataList);

                            // just append more data to current list
                        } else if (albumDetailListAdapter != null && albumdetailDataList.size() == 0) {
                            isLoading = true;
                            addOldNewValue(albumdetailDataList);

                        } else {
                            fillAlbumDetailImageList();
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

    private Map<String, String> getAlbumDetailData() {
        Map<String, String> map = new HashMap<>();
        map.put("AlbumId",albumIdStr);
        map.put("PageIndex",String.valueOf(pageIndex));
        map.put("PageSize","15");
        map.put("MemberId",String.valueOf(Utils.getAppUserId(mContext)));
        return map;
    }

    public void fillAlbumDetailImageList(){


        albumDetailListAdapter = new AlbumDetailListAdapter(mContext, albumdetailDataList);
        activityAlbumDetailBinding.albumDetailRcvList.setAdapter(albumDetailListAdapter);

    }

    private void loadMore() {
        callAlbumDetailData();
    }


    public void addOldNewValue(List<ImageDetailModel> result) {
        for (int i = 0; i < result.size(); i++) {
//            galleryImageUrl.addAll(Collections.singleton(result.get(i).getGalleryURL()));
//            galleryImageUrlName.addAll(Collections.singleton(result.get(i).getAddedUserName()));
//            galleryImageDuration.addAll(Collections.singleton(result.get(i).getStrAddedDuration()));
//            galleryImageId.addAll(Collections.singleton(String.valueOf(result.get(i).getBAGalleryId())));
        }
//        Log.d("galleryImageUrl", "" + galleryImageUrl.size());

    }




    // Api calling GetAlbumDetailData
    public void callAlbumPullDetailData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), AlbumDetailActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getBAAlbumDetail(getAlbumPullDetailData(), new retrofit.Callback<ImageMainModel>() {
            @Override
            public void success(ImageMainModel albumdetailMainModel, Response response) {
                Utils.dismissDialog();
                if (albumdetailMainModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (albumdetailMainModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (albumdetailMainModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (albumdetailMainModel.getIsValid() == 1) {

                    if (albumdetailMainModel.getData() != null) {

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

    private Map<String, String> getAlbumPullDetailData() {
        Map<String, String> map = new HashMap<>();
        map.put("AlbumId",albumIdStr);
        map.put("PageIndex","0");
        map.put("PageSize","15");
        map.put("MemberId",String.valueOf(Utils.getAppUserId(mContext)));
        return map;
    }



}
