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

import com.bharatarmy.Adapter.AlbumDetailListAdapter;
import com.bharatarmy.Adapter.WishListShowAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.FeedbackModel;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
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

//        wishlistshowList = new ArrayList<>();


//        wishlistshowList.add(new WatchListModel(1, 1, "http://devenv.bharatarmy.com//Docs/Media/bharatarmy_app_a9791fb5-899f-4736-944a-37217e14801b.jpg",
//                "http://devenv.bharatarmy.com//Docs/Media/Thumb/5c4f282e-ec4a-4fab-a95c-3ca56a61f842-Thumb_20200210_BA064410.jpg",
//                "23h ago", 0, "Dhaval Shah", 1, "50", ""));
//        wishlistshowList.add(new WatchListModel(2, 1, "http://devenv.bharatarmy.com//Docs/Media/bharatarmy_app_03535bfb-0bc9-442c-83a8-c22fde946dcd.jpg",
//                "http://devenv.bharatarmy.com//Docs/Media/Thumb/e320802f-0381-4ff0-b51f-9c503865f3ee-Thumb_20200210_BA043107.jpg",
//                "1d ago", 1, "Megha Shah", 1, "30", ""));
//        wishlistshowList.add(new WatchListModel(3, 1, "http://devenv.bharatarmy.com//Docs/Media/bharatarmy_app_9a7a595f-d28e-45c6-8b1a-5ea759bd0d2d.jpg",
//                "http://devenv.bharatarmy.com//Docs/Media/Thumb/3b759a2a-beb6-4073-b849-5fa3ed4e86e3-Thumb_20200210_BA043104.jpg",
//                "1d ago", 0, "Dhaval Shah", 1, "10", ""));
//        wishlistshowList.add(new WatchListModel(4, 1, "http://devenv.bharatarmy.com//Docs/Media/bharatarmy_app_2a07427c-8fba-4be9-baf0-969dfb84fdb4.mp4",
//                "http://devenv.bharatarmy.com//Docs/Media/Thumb/3b484b79-ad6f-4db2-838a-478b117fabf7-Thumb_20200210_BA121034.jpg",
//                "1d ago", 1, "Dhaval Shah", 2, "10", "0:00:10"));
//        wishlistshowList.add(new WatchListModel(5, 1, "http://devenv.bharatarmy.com//Docs/Media/bharatarmy_app_ed5b3032-05ab-4d16-8a46-052c24a60051.mp4",
//                "http://devenv.bharatarmy.com//Docs/Media/Thumb/b4bc2aaf-e5e3-4e95-a8f2-7583dd74206a-Thumb_20200205_BA025019.jpg",
//                "1d ago", 1, "Dhaval Shah", 2, "10", "0:00:03"));
//        wishlistshowList.add(new WatchListModel(6, 1, "http://devenv.bharatarmy.com//Docs/Media/bharatarmy_app_4687c0ea-0d2f-4d38-b6b4-0eef44e67974.mp4",
//                "http://devenv.bharatarmy.com//Docs/Media/Thumb/183103b1-6da5-4f4b-92b7-c5dcc9b7f6d5-Thumb_20200205_BA024509.jpg",
//                "2d ago", 0, "Dhaval Shah", 2, "10", "0:00:13"));
//        wishlistshowList.add(new WatchListModel(7, 1, "http://devenv.bharatarmy.com//Docs/Media/bharatarmy_app_bdad52bb-a687-486e-9162-c8334f46e4b1.mp4",
//                "http://devenv.bharatarmy.com//Docs/Media/Thumb/9b8b2c61-1d2b-46d7-9614-c5a6cf61a8cb-Thumb_20200129_BA111537.jpg",
//                "2d ago", 0, "Dhaval Shah", 2, "10", "0:00:04"));
//        wishlistshowList.add(new WatchListModel(8, 1, "http://devenv.bharatarmy.com//Docs/Media/bharatarmy_app_df4fd1de-3692-46b8-879b-74bb75c06749.jpg",
//                "http://devenv.bharatarmy.com//Docs/Media/Thumb/45e4859e-eda1-4b25-8766-5c989359dc5b-Thumb_20200210_BA035219.jpg",
//                "1w ago", 1, "Megha Shah", 1, "30", ""));


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


