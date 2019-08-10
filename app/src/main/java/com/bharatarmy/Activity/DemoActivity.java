package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bharatarmy.Adapter.DemoWallPaperDataAdapter;
import com.bharatarmy.Adapter.InquiryListAdapter;
import com.bharatarmy.Adapter.MainPageChildAdapter;
import com.bharatarmy.Adapter.MainPageDealsAdapter;
import com.bharatarmy.Fragment.HomeFragment;
import com.bharatarmy.Fragment.InquiryChildInformationFragment;
import com.bharatarmy.Fragment.PacakgeFragment;
import com.bharatarmy.Fragment.ProductTourFragment;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Interfaces.OnLoadMoreListener;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.MoreDataModel;
import com.bharatarmy.Models.MoreDetailDataModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.MyScrollView;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityDemoBinding;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;


//https://github.com/OCNYang/PageTransformerHelp refre
public class DemoActivity extends AppCompatActivity {

ActivityDemoBinding activityDemoBinding;
    private static final String TAG = "WallpaperActivity";

    private DemoWallPaperDataAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
Context mContext;

    public static int pageNumber;

    private List<MoreDetailDataModel> wallpaperImagesList;

    protected Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDemoBinding=DataBindingUtil.setContentView(this,R.layout.activity_demo);
        mContext=DemoActivity.this;
        pageNumber = 0;
        wallpaperImagesList = new ArrayList<MoreDetailDataModel>();
        handler = new Handler();


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        activityDemoBinding.myRecyclerView.setHasFixedSize(true);


        mLayoutManager = new LinearLayoutManager(mContext);


        // use a linear layout manager
        activityDemoBinding.myRecyclerView.setLayoutManager(mLayoutManager);


        // create an Object for Adapter
        mAdapter = new DemoWallPaperDataAdapter(wallpaperImagesList, activityDemoBinding.myRecyclerView);

        // set the adapter object to the Recyclerview
        activityDemoBinding.myRecyclerView.setAdapter(mAdapter);


        getWebServiceData();


        mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the adapter will check view_type and show progress bar at bottom
                wallpaperImagesList.add(null);
                mAdapter.notifyItemInserted(wallpaperImagesList.size() - 1);
                ++pageNumber;


                getWebServiceData();


            }
        });


    }


    public void getWebServiceData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), DemoActivity.this);
            return;
        }

//        Utils.showDialog(mContext);
        ApiHandler.getApiService().getTournamntInquiry(getInquriyData(), new retrofit.Callback<MoreDataModel>() {
            @Override
            public void success(MoreDataModel moreDataModel, Response response) {
                Utils.dismissDialog();

                if (moreDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (moreDataModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (moreDataModel.getIsValid() == 0) {
                    Utils.ping(mContext, moreDataModel.getMessage());
                    return;
                }
                if (moreDataModel.getIsValid() == 1) {
                    if (moreDataModel.getData() != null) {
//                        if (pageNumber > 0) {
//                            wallpaperImagesList.remove(wallpaperImagesList.size() - 1);
//                            mAdapter.notifyItemRemoved(wallpaperImagesList.size());
//                        }
                        wallpaperImagesList=moreDataModel.getData();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyItemInserted(wallpaperImagesList.size());


                            }
                        });
                        mAdapter.setLoaded();
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

    private Map<String, String> getInquriyData() {
        Map<String, String> map = new HashMap<>();
        map.put("PageSize","14");
        map.put("PageIndex", String.valueOf(pageNumber));
        map.put("MemberId", Utils.getPref(mContext, "AppUserId"));
        map.put("ordertype", "");
        map.put("orderstatus","");
        return map;
    }

}
