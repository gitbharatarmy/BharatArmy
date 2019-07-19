package com.bharatarmy.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bharatarmy.Adapter.AlbumListAdapter;
import com.bharatarmy.Adapter.ImageListAdapter;
import com.bharatarmy.Adapter.LoginDialogAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.LoginDialogItemBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginDialogActivity extends AppCompatActivity {
    LoginDialogItemBinding loginDialogItemBinding;
    Context mContext;
    LoginDialogAdapter loginDialogAdapter;
    List<ImageDetailModel> albumList;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    int pageIndex = 0;
    boolean isLoading = false;
    boolean ispull;
    ArrayList<String> galleryImageUrl = new ArrayList<>();

    final int speedScroll = 1200;
    final Handler handler = new Handler();

    int[] lastPositions;
    int lastVisibleItem;
    ImageListAdapter imageListAdapter;
    GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginDialogItemBinding = DataBindingUtil.setContentView(this, R.layout.login_dialog_item);
        mContext = LoginDialogActivity.this;

        init();
        callImageGalleryData();

    }

    public void init(){
//        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
//        loginDialogItemBinding.rvPosters.setLayoutManager(staggeredGridLayoutManager);
//
//
//        loginDialogItemBinding.rvPosters.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
//                lastPositions = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(lastPositions);
//                lastVisibleItem = Math.max(lastPositions[0], lastPositions[1]);//findMax(lastPositions);
//
//                if (!isLoading) {
//                    if (staggeredGridLayoutManager != null && lastVisibleItem == albumList.size() - 1) {
//                        //bottom of list!
//                        ispull = false;
//                        pageIndex = pageIndex + 1;
//                        loadMore();
//
//                    }
//                }
//            }
//        });

        gridLayoutManager = new GridLayoutManager(mContext, 3);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL); // set Horizontal Orientation
       loginDialogItemBinding.rvPosters.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView


        loginDialogItemBinding.rvPosters.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if (!isLoading) {
                    if (gridLayoutManager != null && gridLayoutManager.findLastCompletelyVisibleItemPosition() == albumList.size() - 1) {
                        //bottom of list!
                        ispull = false;
                        pageIndex = pageIndex + 1;
                        loadMore();

                    }
                }
            }
        });
    }

    // Api calling GetImageGalleryData
    public void callImageGalleryData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error),LoginDialogActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getBAGallery(getImageGalleryData(), new retrofit.Callback<ImageMainModel>() {
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
                        albumList=new ArrayList<>();
                        albumList = imageMainModel.getData();

                        addOldNewValue (albumList);
                        if (loginDialogAdapter != null && albumList.size() > 0) {
                            loginDialogAdapter.addMoreDataToList(albumList);
                            // just append more data to current list
                        } else if(loginDialogAdapter!=null && albumList.size()==0){
                            isLoading = true;
                            addOldNewValue (imageMainModel.getData());
                        }else {
                            fillImageGallery();
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

    private Map<String, String> getImageGalleryData() {
        Map<String, String> map = new HashMap<>();
        map.put("PageIndex", String.valueOf(pageIndex));
        map.put("PageSize", "15");
        return map;
    }

    public void fillImageGallery() {
//        loginDialogAdapter = new LoginDialogAdapter(mContext, albumList);
//        loginDialogItemBinding.rvPosters.setAdapter(loginDialogAdapter);

        imageListAdapter = new ImageListAdapter(mContext, albumList, new image_click() {
            @Override
            public void image_more_click() {

            }
        });
        loginDialogItemBinding.rvPosters.setAdapter(imageListAdapter);


        final Runnable runnable = new Runnable() {
            int count = 0;
            boolean flag = true;
            @Override
            public void run() {
                if(count < imageListAdapter.getItemCount()){
                    if(count==imageListAdapter.getItemCount()-1){
                        flag = false;
                    }else if(count == 0){
                        flag = true;
                    }
                    if(flag) count++;
                    else count--;

                    loginDialogItemBinding.rvPosters.smoothScrollToPosition(count);
                    handler.postDelayed(this,speedScroll);
                }
            }
        };

        handler.postDelayed(runnable,speedScroll);
    }

    public void addOldNewValue(List<ImageDetailModel> result) {
        for (int i = 0; i < result.size(); i++) {
            galleryImageUrl.addAll(Collections.singleton(result.get(i).getGalleryURL()));
        }
        Log.d("galleryImageUrl", "" + galleryImageUrl.size());

    }

    private void loadMore() {
        callImageGalleryData();
    }
}
