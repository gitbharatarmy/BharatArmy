package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.bharatarmy.Adapter.PackagePageAdapter;
import com.bharatarmy.Adapter.TravelPacakgeTabAdapter;
import com.bharatarmy.Fragment.MyOffersBottomSheetDialogFragment;
import com.bharatarmy.Fragment.PacakgeSummaryBottomSheetDialogFragment;
import com.bharatarmy.Models.TravelDetailModel;
import com.bharatarmy.Models.TravelMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityTravelPackageBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class TravelPackageActivity extends AppCompatActivity {
    ActivityTravelPackageBinding activityTravelPackageBinding;
    Context mContext;
    PackagePageAdapter packagePageAdapter;
    List<TravelDetailModel> travelPacakgeTabList;
    BottomSheetDialogFragment bottomSheetDialogFragment;
    private BottomSheetBehavior mBottomSheetBehavior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelPackageBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_package);
        mContext = TravelPackageActivity.this;
        init();
        setLisitner();
        setTabLayoutList();

    }
    public void init() {
        setSupportActionBar(activityTravelPackageBinding.tabToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        activityTravelPackageBinding.shimmerViewContainer.startShimmerAnimation();
        activityTravelPackageBinding.oldpriceTxt.setPaintFlags(activityTravelPackageBinding.oldpriceTxt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

//        mBottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomSheetLayout));
    }

    public void setLisitner() {
        activityTravelPackageBinding.htabAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
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
                    activityTravelPackageBinding.tabToolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.heading_bg));
                    activityTravelPackageBinding.tablayoutLinear.setBackgroundColor(ContextCompat.getColor(mContext, R.color.splash_bg_color));
                    activityTravelPackageBinding.tabToolbar.setTitle("Australian Double Dhamaka: Honeymoon and adventure at one shot");
                    Typeface typeface = ResourcesCompat.getFont(mContext, R.font.helveticaneueltstdbdcn);
                    activityTravelPackageBinding.htabCollapseToolbar.setCollapsedTitleTypeface(typeface);
                    activityTravelPackageBinding.htabCollapseToolbar.setExpandedTitleTypeface(typeface);
                    activityTravelPackageBinding.htabCollapseToolbar.setCollapsedTitleGravity(Gravity.START);
                    activityTravelPackageBinding.htabCollapseToolbar.setExpandedTitleGravity(Gravity.START);
                    activityTravelPackageBinding.toolbarBottomLeftView.setVisibility(View.VISIBLE);
                    isShow = true;
                } else if (isShow) {
                    activityTravelPackageBinding.tabToolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
                    activityTravelPackageBinding.tablayoutLinear.setBackgroundColor(ContextCompat.getColor(mContext, R.color.splash_bg_color));
                    activityTravelPackageBinding.tabToolbar.setTitle(" ");
                    activityTravelPackageBinding.toolbarBottomLeftView.setVisibility(View.GONE);
                    isShow = false;
                }

            }
        });

        activityTravelPackageBinding.bookTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookIntent=new Intent(mContext,TravelBookActivity.class);
                bookIntent.putExtra("pacakgeName","Australian Double Dhamaka: Honeymoon and adventure at one shot");
                bookIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(bookIntent);
            }
        });
        activityTravelPackageBinding.summaryLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialogFragment = new PacakgeSummaryBottomSheetDialogFragment();
                //show it
                bottomSheetDialogFragment.setCancelable(false);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });


    }

    public void setTabLayoutList() {
        for (int i = 0; i < 10; i++) {
            if (i == 0) {
                Spannable wordtoSpan = new SpannableString(" DAY" + "\n" + "0" + String.valueOf(i + 1));
                Log.d("wordspanlenght :", "" + wordtoSpan.length());
                wordtoSpan.setSpan(new RelativeSizeSpan(1.5f), 5, wordtoSpan.length(), 0); // set size
                wordtoSpan.setSpan(new StyleSpan(Typeface.NORMAL), 0, wordtoSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                wordtoSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.heading_bg)), 5, wordtoSpan.length(), 0);// set color
                activityTravelPackageBinding.tabLayoutDays.addTab(activityTravelPackageBinding.tabLayoutDays.newTab().setText(wordtoSpan));
            } else if(i<9){
                activityTravelPackageBinding.tabLayoutDays.addTab(activityTravelPackageBinding.tabLayoutDays.newTab().setText(" 0" + String.valueOf(i + 1)));
            }else{
                activityTravelPackageBinding.tabLayoutDays.addTab(activityTravelPackageBinding.tabLayoutDays.newTab().setText(String.valueOf(i + 1)));
            }
        }
        AppConfiguration.tabPosition="1";
        callTabPacakgeDetailData();

        activityTravelPackageBinding.daytabViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(
                activityTravelPackageBinding.tabLayoutDays));

        activityTravelPackageBinding.tabLayoutDays.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                AppConfiguration.tabPosition= String.valueOf(tab.getPosition()+1);
                callTabPacakgeDetailData();
                tab.setText(String.valueOf(" DAY" + "\n" + tab.getText()));
                activityTravelPackageBinding.daytabViewpager.setCurrentItem(tab.getPosition());
                Spannable wordtoSpan = new SpannableString(String.valueOf(tab.getText()));
                Log.d("wordspanlenght :", "" + wordtoSpan.length());
                wordtoSpan.setSpan(new RelativeSizeSpan(1.5f), 5, wordtoSpan.length(), 0); // set size
                wordtoSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.heading_bg)), 5, wordtoSpan.length(), 0);// set color
                wordtoSpan.setSpan(new StyleSpan(Typeface.NORMAL), 0, wordtoSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tab.setText(wordtoSpan);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d("mainSTring :", "" + tab.getText());
                String tabtxt = String.valueOf(tab.getText());
                String[] splited = tabtxt.split("\\s+");
                Log.d("tabStirng", splited[2]);
                tab.setText(splited[2]);
                Spannable wordtoSpan = new SpannableString(String.valueOf(tab.getText()));
                wordtoSpan.setSpan(new StyleSpan(Typeface.NORMAL), 0, wordtoSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tab.setText(wordtoSpan);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                TravelPackageActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Api calling GetTabPacakgeDetailData
    public void callTabPacakgeDetailData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error),TravelPackageActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getItineraryDetailByDayNo(getTabDetailData(), new retrofit.Callback<TravelMainModel>() {
            @Override
            public void success(TravelMainModel travelMainModel, Response response) {
                Utils.dismissDialog();
                if (travelMainModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (travelMainModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (travelMainModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (travelMainModel.getIsValid() == 1) {

                    if (travelMainModel.getData() != null) {
                        travelPacakgeTabList = travelMainModel.getData();
//                        fragmentPackageTabBinding.pacakgeDayRcv.hideShimmerAdapter();
//                        setDataList();
                   if (packagePageAdapter==null){
                       packagePageAdapter = new PackagePageAdapter(getSupportFragmentManager(),
                               activityTravelPackageBinding.tabLayoutDays.getTabCount(),travelPacakgeTabList);
                       activityTravelPackageBinding.daytabViewpager.setAdapter(packagePageAdapter);
                   }else{
                       packagePageAdapter.notifyDataSetChanged();

                   }
activityTravelPackageBinding.shimmerViewContainer.stopShimmerAnimation();
                   activityTravelPackageBinding.shimmerViewContainer.setVisibility(View.GONE);
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

    private Map<String, String> getTabDetailData() {
        Map<String, String> map = new HashMap<>();
        map.put("ItineraryId", "1");
        map.put("DayNo", AppConfiguration.tabPosition);
        return map;
    }
}
