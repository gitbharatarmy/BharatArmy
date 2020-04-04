package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.InputQueue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bharatarmy.Adapter.InquiryAssignAdapter;
import com.bharatarmy.Adapter.InquiryFilterListAdapter;
import com.bharatarmy.Adapter.InquiryListAdapter;
import com.bharatarmy.Fragment.InquiryChildInformationFragment;
import com.bharatarmy.Fragment.InquiryFilterFragment;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.InquiryOtherDataModel;
import com.bharatarmy.Models.MoreDataModel;
import com.bharatarmy.Models.MoreDetailDataModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityInquriyBinding;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

//https://stackoverflow.com/questions/31000964/how-to-implement-setonscrolllistener-in-recyclerview
// use for bottom progressbar link
//https://github.com/kprathap23/Android/blob/master/EndlessRecyclerView/app/src/main/java/com/pratap/endlessrecyclerview/WallpaperActivity.java
public class InquriyActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityInquriyBinding activityInquriyBinding;
    Context mContext;
    String orderstatusStr = "", ordertypeStr = "";
    InquiryListAdapter inquiryListAdapter, inquiryafterfilterListAdapter;

    InquiryFilterListAdapter inquiryFilterListAdapter;
    List<MoreDetailDataModel> moreDetailDataModelList;
    public String isUpdateAvailable, isForceUpdateAvailable, currentVersionStr;

    LinearLayoutManager mLayoutManager;
    BottomSheetDialogFragment bottomSheetDialogFragment, bottomSheet1DialogFragment;
    InquiryAssignAdapter inquiryAssignAdapter;
    List<MoreDetailDataModel> assignmemberlist;
    InquiryOtherDataModel filtertaglist;
    int selectedposition = -1;
    RecyclerView inquiry_assign_rcv;
    ShimmerFrameLayout shimmer_view_containerdialog;
    String assignmemberId;
    List<MoreDetailDataModel> allinquiryData = new ArrayList<>();
    List<MoreDetailDataModel> allfilterinquiryData = new ArrayList<>();

    //    lazy loading variable
    boolean isLoading = false;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityInquriyBinding = DataBindingUtil.setContentView(this, R.layout.activity_inquriy);
        mContext = InquriyActivity.this;

        init();
        callInquriyData();
        setListiner();

    }

    public void init() {
        mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        activityInquriyBinding.inquriyListRcv.setLayoutManager(mLayoutManager);
        activityInquriyBinding.inquriyListRcv.setItemAnimator(new DefaultItemAnimator());
        activityInquriyBinding.shimmerViewContainer.startShimmerAnimation();
        AppConfiguration.whereToCall = "Main";
        AppConfiguration.pageindex = 0;
    }

    public void setListiner() {
        activityInquriyBinding.backImg.setOnClickListener(this);
        activityInquriyBinding.fabLinear.setOnClickListener(this);

        activityInquriyBinding.inquriyListRcv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if (!isLoading) {
                    if (moreDetailDataModelList != null) {
                        if (AppConfiguration.whereToCall.equalsIgnoreCase("Main")) {
                            if (mLayoutManager != null && mLayoutManager.findLastCompletelyVisibleItemPosition() == allinquiryData.size() - 1) {
                                //bottom of list!
                                AppConfiguration.pageindex = AppConfiguration.pageindex + 1;
                                Log.d("pageIndexonScroll :", "" + AppConfiguration.pageindex);
                                activityInquriyBinding.progressBar.setVisibility(View.VISIBLE);
                                loadMore();

                            }
                        } else {
                            if (mLayoutManager != null && mLayoutManager.findLastCompletelyVisibleItemPosition() == allfilterinquiryData.size() - 1) {
                                //bottom of list!
                                AppConfiguration.pageindex = AppConfiguration.pageindex + 1;
                                Log.d("pageIndexonScroll :", "" + AppConfiguration.pageindex);
                                activityInquriyBinding.progressBar.setVisibility(View.VISIBLE);
                                loadMore();

                            }
                        }


                    }
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                AppConfiguration.ordertypefilterarray.clear();
                AppConfiguration.inquirystatusfilterarray.clear();
                InquriyActivity.this.finish();
                break;
            case R.id.fab_linear:
                Utils.handleClickEvent(mContext, activityInquriyBinding.fabLinear);
                bottomSheetDialogFragment = new InquiryFilterFragment(filtertaglist, new image_click() {
                    @Override
                    public void image_more_click() {
                        ordertypeStr = "";
                        orderstatusStr = "";
                        AppConfiguration.pageindex = 0;
                        recreate();
                        bottomSheetDialogFragment.dismiss();
                    }
                }, new MorestoryClick() {
                    @Override
                    public void getmorestoryClick() {
                        orderstatusStr = AppConfiguration.inquirystatusfilterarray.toString().substring(1, AppConfiguration.inquirystatusfilterarray.toString().length() - 1);

                        ordertypeStr = AppConfiguration.ordertypefilterarray.toString().substring(1, AppConfiguration.ordertypefilterarray.toString().length() - 1);

                        Log.d("orderstatusStr :", orderstatusStr + "ordertypeStr" + ordertypeStr);
                        activityInquriyBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                        activityInquriyBinding.inquriyListRcv.setVisibility(View.GONE);
                        activityInquriyBinding.shimmerViewContainer.startShimmerAnimation();
                        moreDetailDataModelList.clear();
                        callInquriyfilterData();
                        bottomSheetDialogFragment.dismiss();
                    }
                });
                //show it
                bottomSheetDialogFragment.setCancelable(false);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                break;
        }

    }

    public void callInquriyData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), InquriyActivity.this);
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
                    isUpdateAvailable = String.valueOf(moreDataModel.getIsUpdateAvailable());
                    isForceUpdateAvailable = String.valueOf(moreDataModel.getIsForceUpdate());
//                    isForceUpdateAvailable = "0";
                    currentVersionStr = String.valueOf(moreDataModel.getCurrentVersion());
                    if (isUpdateAvailable.equalsIgnoreCase("1")) {
                        Utils.checkupdateApplication(mContext, InquriyActivity.this, isForceUpdateAvailable, currentVersionStr);
                    }
                    if (moreDataModel.getData() != null) {

                        moreDetailDataModelList = moreDataModel.getData();
                        filtertaglist = moreDataModel.getOtherData();

                        Log.d("moreDetailDataModelList", "" + moreDetailDataModelList.size());
                        Log.d("filtertaglist", "" + filtertaglist.getTypes().size());
                        activityInquriyBinding.shimmerViewContainer.stopShimmerAnimation();
                        activityInquriyBinding.shimmerViewContainer.setVisibility(View.GONE);
                        activityInquriyBinding.fabLinear.setVisibility(View.VISIBLE);
                        activityInquriyBinding.progressBar.setVisibility(View.GONE);
                        activityInquriyBinding.inquriyListRcv.setVisibility(View.VISIBLE);
                        addOldNewValue(moreDetailDataModelList);
                        if (inquiryListAdapter != null && moreDetailDataModelList.size() > 0) {
                            inquiryListAdapter.addMoreDataToList(moreDetailDataModelList);
                            // just append more data to current list
                        } else if (inquiryListAdapter != null && moreDetailDataModelList.size() == 0) {
//                            Utils.ping(mContext,"No more data available");
                            Log.d("pageIndex", "" + AppConfiguration.pageindex);
                            isLoading = true;
                            addOldNewValue(moreDetailDataModelList);
                        } else {
                            fillInquiryData();
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

    private Map<String, String> getInquriyData() {
        Map<String, String> map = new HashMap<>();
        map.put("PageSize", AppConfiguration.pageSize);
        map.put("PageIndex", String.valueOf(AppConfiguration.pageindex));
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));
        map.put("ordertype", ordertypeStr);
        map.put("orderstatus", "0");

        return map;
    }

    public void fillInquiryData() {

        inquiryListAdapter = new InquiryListAdapter(mContext, moreDetailDataModelList, new image_click() {
            @Override
            public void image_more_click() {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.inquiry_assign_dialog_item, null);
                dialogBuilder.setView(dialogView);
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                shimmer_view_containerdialog = (ShimmerFrameLayout) dialogView.findViewById(R.id.shimmer_view_containerdialog);
                inquiry_assign_rcv = (RecyclerView) dialogView.findViewById(R.id.inquiry_assign_rcv);
                LinearLayout submit_linear = (LinearLayout) dialogView.findViewById(R.id.submit_linear);
                LinearLayout close_linear = (LinearLayout) dialogView.findViewById(R.id.close_linear);
                shimmer_view_containerdialog.startShimmerAnimation();

                close_linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                submit_linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("assignmemberId :", "" + AppConfiguration.selectedposition);
                        callInquriyAssignData();
                        alertDialog.dismiss();
                        showThankyouDialog();
                    }
                });
                callInquriyAssignUserData();

                alertDialog.show();


            }
        }, new MorestoryClick() {
            @Override
            public void getmorestoryClick() {
                bottomSheet1DialogFragment = new InquiryChildInformationFragment();
                //show it
//                bottomSheet1DialogFragment.setCancelable(false);
                bottomSheet1DialogFragment.show(getSupportFragmentManager(), bottomSheet1DialogFragment.getTag());
            }
        });


        activityInquriyBinding.inquriyListRcv.setAdapter(inquiryListAdapter);
        inquiryListAdapter.notifyDataSetChanged();
    }


    private void loadMore() {
        if (AppConfiguration.whereToCall.equalsIgnoreCase("filter")) {
            callInquriyfilterData();
        } else {
            callInquriyData();
        }

    }

    public void addOldNewValue(List<MoreDetailDataModel> result) {
        allinquiryData.addAll(result);
        Log.d("resultSize", "" + allinquiryData.size());

    }

    public void addOldNewValuefilterData(List<MoreDetailDataModel> result) {
        allfilterinquiryData.addAll(result);
        Log.d("filterresultSize", "" + allfilterinquiryData.size());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (bottomSheet1DialogFragment != null) {
            bottomSheet1DialogFragment.dismiss();
        }
    }

    public void callInquriyAssignUserData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), InquriyActivity.this);
            return;
        }

//        Utils.showDialog(mContext);
        ApiHandler.getApiService().getInquiryAssignUser(getInquriyAssignUserData(), new retrofit.Callback<MoreDataModel>() {
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
                    isUpdateAvailable = String.valueOf(moreDataModel.getIsUpdateAvailable());
                    isForceUpdateAvailable = String.valueOf(moreDataModel.getIsForceUpdate());
//                    isForceUpdateAvailable = "0";
                    currentVersionStr = String.valueOf(moreDataModel.getCurrentVersion());
                    if (isUpdateAvailable.equalsIgnoreCase("1")) {
                        Utils.checkupdateApplication(mContext,InquriyActivity.this, isForceUpdateAvailable, currentVersionStr);
                    }
                    if (moreDataModel.getData() != null) {

                        assignmemberlist = moreDataModel.getData();
                        shimmer_view_containerdialog.stopShimmerAnimation();
                        shimmer_view_containerdialog.setVisibility(View.GONE);
                        inquiry_assign_rcv.setVisibility(View.VISIBLE);
                        for (int i = 0; i < assignmemberlist.size(); i++) {
                            if (assignmemberlist.get(i).getIsSelected().equals(1)) {
                                selectedposition = i;
                            }
                        }
                        Log.d("selectedposition : ", "" + selectedposition);
                        inquiryAssignAdapter = new InquiryAssignAdapter(mContext, assignmemberlist, selectedposition, new MorestoryClick() {
                            @Override
                            public void getmorestoryClick() {

                            }

                        });
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
                        inquiry_assign_rcv.setLayoutManager(mLayoutManager);
                        inquiry_assign_rcv.setItemAnimator(new DefaultItemAnimator());
                        inquiry_assign_rcv.setAdapter(inquiryAssignAdapter);


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

    private Map<String, String> getInquriyAssignUserData() {
        Map<String, String> map = new HashMap<>();
        map.put("InquiryId", AppConfiguration.inquiryId);
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));

        return map;
    }

    public void callInquriyAssignData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), InquriyActivity.this);
            return;
        }

//        Utils.showDialog(mContext);
        ApiHandler.getApiService().getAssingUsertoInquiry(getInquriyAssignData(), new retrofit.Callback<MoreDataModel>() {
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
                    isUpdateAvailable = String.valueOf(moreDataModel.getIsUpdateAvailable());
                    isForceUpdateAvailable = String.valueOf(moreDataModel.getIsForceUpdate());
//                    isForceUpdateAvailable = "0";
                    currentVersionStr = String.valueOf(moreDataModel.getCurrentVersion());
                    if (isUpdateAvailable.equalsIgnoreCase("1")) {
                        Utils.checkupdateApplication(mContext, InquriyActivity.this, isForceUpdateAvailable, currentVersionStr);
                    }
                    if (moreDataModel.getData() != null) {

                        assignmemberlist = moreDataModel.getData();
                        shimmer_view_containerdialog.stopShimmerAnimation();
                        shimmer_view_containerdialog.setVisibility(View.GONE);
                        inquiry_assign_rcv.setVisibility(View.VISIBLE);
                        inquiryAssignAdapter = new InquiryAssignAdapter(mContext, assignmemberlist, selectedposition, new MorestoryClick() {
                            @Override
                            public void getmorestoryClick() {

                            }
                        });
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
                        inquiry_assign_rcv.setLayoutManager(mLayoutManager);
                        inquiry_assign_rcv.setItemAnimator(new DefaultItemAnimator());
                        inquiry_assign_rcv.setAdapter(inquiryAssignAdapter);


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

    private Map<String, String> getInquriyAssignData() {
        Map<String, String> map = new HashMap<>();
        map.put("InquiryId", AppConfiguration.inquiryId);
        map.put("AssignMemberId", String.valueOf(AppConfiguration.selectedposition));
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));

        return map;
    }


    public void showThankyouDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.thankyou_dialog_item, null);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView hometxt = (TextView) dialogView.findViewById(R.id.home_txt);
        hometxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                bottomSheet1DialogFragment.dismiss();

            }
        });
        alertDialog.show();
    }


    public void callInquriyfilterData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), InquriyActivity.this);
            return;
        }

//        Utils.showDialog(mContext);
        ApiHandler.getApiService().getTournamntInquiry(getInquriyfilterData(), new retrofit.Callback<MoreDataModel>() {
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
                    isUpdateAvailable = String.valueOf(moreDataModel.getIsUpdateAvailable());
                    isForceUpdateAvailable = String.valueOf(moreDataModel.getIsForceUpdate());
//                    isForceUpdateAvailable = "0";
                    currentVersionStr = String.valueOf(moreDataModel.getCurrentVersion());
                    if (isUpdateAvailable.equalsIgnoreCase("1")) {
                        Utils.checkupdateApplication(mContext, InquriyActivity.this, isForceUpdateAvailable, currentVersionStr);
                    }
                    if (moreDataModel.getData() != null) {

                        moreDetailDataModelList = moreDataModel.getData();

                        activityInquriyBinding.shimmerViewContainer.stopShimmerAnimation();
                        activityInquriyBinding.shimmerViewContainer.setVisibility(View.GONE);
                        activityInquriyBinding.progressBar.setVisibility(View.GONE);
                        activityInquriyBinding.inquriyListRcv.setVisibility(View.VISIBLE);
                        addOldNewValuefilterData(moreDetailDataModelList);

                        if (inquiryFilterListAdapter != null && moreDetailDataModelList.size() > 0) {
                            inquiryFilterListAdapter.addMoreDataToList(moreDetailDataModelList);
                            // just append more data to current list
                        } else if (inquiryFilterListAdapter != null && moreDetailDataModelList.size() == 0) {
                            isLoading = true;
                            addOldNewValuefilterData(moreDetailDataModelList);
                        } else {
                            fillInquiryfilterData();
                        }


                    }

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Log.d("retrofit error :", error.getMessage());
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getInquriyfilterData() {
        Map<String, String> map = new HashMap<>();
        map.put("PageSize", AppConfiguration.pageSize);
        map.put("PageIndex", String.valueOf(AppConfiguration.pageindex));
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));
        map.put("ordertype", ordertypeStr);
        map.put("orderstatus", orderstatusStr);

        return map;
    }

    public void fillInquiryfilterData() {

        inquiryFilterListAdapter = new InquiryFilterListAdapter(mContext, moreDetailDataModelList, new image_click() {
            @Override
            public void image_more_click() {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.inquiry_assign_dialog_item, null);
                dialogBuilder.setView(dialogView);
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                shimmer_view_containerdialog = (ShimmerFrameLayout) dialogView.findViewById(R.id.shimmer_view_containerdialog);
                inquiry_assign_rcv = (RecyclerView) dialogView.findViewById(R.id.inquiry_assign_rcv);
                LinearLayout submit_linear = (LinearLayout) dialogView.findViewById(R.id.submit_linear);
                LinearLayout close_linear = (LinearLayout) dialogView.findViewById(R.id.close_linear);
                shimmer_view_containerdialog.startShimmerAnimation();

                close_linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                submit_linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("assignmemberId :", "" + AppConfiguration.selectedposition);
                        callInquriyAssignData();
                        alertDialog.dismiss();
                        showThankyouDialog();
                    }
                });
                callInquriyAssignUserData();

                alertDialog.show();


            }
        }, new MorestoryClick() {
            @Override
            public void getmorestoryClick() {
                bottomSheet1DialogFragment = new InquiryChildInformationFragment();
                //show it
//                bottomSheet1DialogFragment.setCancelable(false);
                bottomSheet1DialogFragment.show(getSupportFragmentManager(), bottomSheet1DialogFragment.getTag());
            }
        });


        activityInquriyBinding.inquriyListRcv.setAdapter(inquiryFilterListAdapter);
    }
}
