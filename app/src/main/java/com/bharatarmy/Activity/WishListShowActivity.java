package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bharatarmy.Adapter.WishListShowAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.Models.WatchListDetailModel;
import com.bharatarmy.Models.WatchListModel;
import com.bharatarmy.Models.WatchListModelDemo;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.Utility.WebServices;
import com.bharatarmy.databinding.ActivityWishListShowBinding;
import com.squareup.okhttp.OkHttpClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WishListShowActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityWishListShowBinding activityWishListShowBinding;
    Context mContext;
    WishListShowAdapter wishListShowAdapter;
    ArrayList<WatchListModel> wishlistshowList;
    GridLayoutManager wishlistgridLayoutManager;

    /*Detail Image video variable*/
    ArrayList<String> galleryImageUrl = new ArrayList<>();
    ArrayList<String> galleryImageThumbUrl = new ArrayList<>();
    ArrayList<String> galleryMediaType = new ArrayList<>();
    ArrayList<String> galleryImageLike = new ArrayList<>();
    ArrayList<String> galleryUserName = new ArrayList<>();
    ArrayList<String> galleryImageDuration = new ArrayList<>();
    ArrayList<String> galleryImageId = new ArrayList<>();
    ArrayList<String> galleryImageView = new ArrayList<>();
    String wishlistClickData;

    List<WatchListDetailModel> watchlist1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWishListShowBinding = DataBindingUtil.setContentView(this, R.layout.activity_wish_list_show);

        mContext = WishListShowActivity.this;

        EventBus.getDefault().register(this);

        init();
        setListiner();
    }

    public void init() {
        GetWatchListDetailData();

        activityWishListShowBinding.toolbarTitleTxt.setText("WishList");
        activityWishListShowBinding.shimmerViewContainer.startShimmerAnimation();
        activityWishListShowBinding.wishlistDetailRcv.setVisibility(View.GONE);

    }

    public void setListiner() {
        activityWishListShowBinding.backImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
        }

    }

    public void addOldNewValue(List<WatchListDetailModel> result) {
        for (int i = 0; i < result.size(); i++) {
            galleryImageUrl.addAll(Collections.singleton(result.get(i).getWatchGalleryURL()));
            galleryImageThumbUrl.addAll(Collections.singleton(result.get(i).getWatchGalleryThumbURL()));
            galleryMediaType.addAll(Collections.singleton(String.valueOf(result.get(i).getIsMediaTypeId())));
            galleryImageLike.addAll(Collections.singleton(String.valueOf(result.get(i).getIsLike())));
            galleryImageDuration.addAll(Collections.singleton(String.valueOf(result.get(i).getStrAddedDuration())));
            galleryUserName.addAll(Collections.singleton(String.valueOf(result.get(i).getAddedUserName())));
            galleryImageId.addAll(Collections.singleton(String.valueOf(result.get(i).getBAWatchListImageId())));
            galleryImageView.addAll(Collections.singleton(result.get(i).getTotalViews()));
        }
//        Log.d("galleryImageUrl", "" + galleryImageUrl.size());

    }

    @Subscribe
    public void customEventReceived(MyScreenChnagesModel event) {
        Log.d("albumId :", String.valueOf(event.getImageLikeposition()));
        Log.d("mainmodelValue :", wishlistshowList.toString());
        for (int i = 0; i < galleryImageLike.size(); i++) {
            if (i == event.getImageLikeposition()) {
                galleryImageLike.set(i, String.valueOf(Utils.LikeStatus));
            }
        }
    }

    // Api calling GetWatchListDetailData
    public void GetWatchListDetailData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), WishListShowActivity.this);
            return;

        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfiguration.BASEURL)
                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
                .build();

        WebServices api = retrofit.create(WebServices.class);

        Call<WatchListModelDemo> call = api.getWatchList(AppConfiguration.URL);

        call.enqueue(new Callback<WatchListModelDemo>() {
            @Override
            public void onResponse(Call<WatchListModelDemo> call, retrofit2.Response<WatchListModelDemo> response) {

                if (response.body().getData()!=null ){
                    watchlist1  = response.body().getData();
                    setData();
                    addOldNewValue(watchlist1);
                }
            }

            @Override
            public void onFailure(Call<WatchListModelDemo> call, Throwable t) {
                Log.d("WatchList Error:", t.getLocalizedMessage());
            }
        });


    }

    public void setData(){

        activityWishListShowBinding.shimmerViewContainer.stopShimmerAnimation();
        activityWishListShowBinding.shimmerViewContainer.setVisibility(View.GONE);
        activityWishListShowBinding.wishlistDetailRcv.setVisibility(View.VISIBLE);


        wishlistgridLayoutManager = new GridLayoutManager(mContext, 3);
        wishlistgridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
        activityWishListShowBinding.wishlistDetailRcv.setLayoutManager(wishlistgridLayoutManager);
        wishListShowAdapter = new WishListShowAdapter(mContext, watchlist1, new image_click() {
            @Override
            public void image_more_click() {
                wishlistClickData = "";
                wishlistClickData = String.valueOf(wishListShowAdapter.getData());
                wishlistClickData = wishlistClickData.replaceAll("\\[", "").replaceAll("\\]", "");
                Log.d("albumClickData", "" + wishlistClickData);
                Intent watchlistdetailIntent = new Intent(mContext, AlbumImageVideoShowActivity.class);
                watchlistdetailIntent.putExtra("positon", wishlistClickData);
                watchlistdetailIntent.putStringArrayListExtra("AlbumUrldata", galleryImageUrl);
                watchlistdetailIntent.putStringArrayListExtra("AlbumThumbUrldata", galleryImageThumbUrl);
                watchlistdetailIntent.putStringArrayListExtra("AlbumMediaType", galleryMediaType);
                watchlistdetailIntent.putStringArrayListExtra("AlbumLike", galleryImageLike);
                watchlistdetailIntent.putStringArrayListExtra("AlbumDuration", galleryImageDuration);
                watchlistdetailIntent.putStringArrayListExtra("AlbumAddUser", galleryUserName);
                watchlistdetailIntent.putStringArrayListExtra("AlbumId", galleryImageId);
                watchlistdetailIntent.putStringArrayListExtra("AlbumViews",galleryImageView);
                watchlistdetailIntent.putExtra("MediaType", "Album");
                watchlistdetailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(watchlistdetailIntent);
            }
        });
        activityWishListShowBinding.wishlistDetailRcv.setAdapter(wishListShowAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}


