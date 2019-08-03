package com.bharatarmy.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bharatarmy.Activity.InquriyActivity;
import com.bharatarmy.Adapter.InquiryAssignAdapter;
import com.bharatarmy.Adapter.TournamentDetailAdapter;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.MoreDataModel;
import com.bharatarmy.Models.MoreDetailDataModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;


public class InquiryChildInformationFragment extends BottomSheetDialogFragment {
    View rootView;
    Context mContext;
    RecyclerView tournament_detailRcv,inquiry_assign_rcv;
    LinearLayout back_img, templete2, templete1,assign_Linear;
    ShimmerFrameLayout shimmer_view_container;
    List<MoreDetailDataModel> moreInquiryDetaildataList;
    TournamentDetailAdapter tournamentDetailAdapter;
    List<MoreDetailDataModel> assignmemberlist;
    InquiryAssignAdapter inquiryAssignAdapter;
    ShimmerFrameLayout shimmer_view_containerdialog;
    @Override
    public void setupDialog(Dialog dialog, int style) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialog;
        bottomSheetDialog.setContentView(R.layout.fragment_inquiry_child_information);

        mContext = getActivity();
        back_img = (LinearLayout) bottomSheetDialog.findViewById(R.id.back_img);

        templete2 = (LinearLayout) bottomSheetDialog.findViewById(R.id.templete2);
        templete1 = (LinearLayout) bottomSheetDialog.findViewById(R.id.templete1);
        shimmer_view_container = (ShimmerFrameLayout) bottomSheetDialog.findViewById(R.id.shimmer_view_container);
        tournament_detailRcv = (RecyclerView) bottomSheetDialog.findViewById(R.id.tournament_detailRcv);
        assign_Linear=(LinearLayout)bottomSheetDialog.findViewById(R.id.assign_Linear);

        shimmer_view_container.startShimmerAnimation();
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        assign_Linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.inquiry_assign_dialog_item, null);
                dialogBuilder.setView(dialogView);
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                shimmer_view_containerdialog = (ShimmerFrameLayout) dialogView.findViewById(R.id.shimmer_view_containerdialog);
                inquiry_assign_rcv=(RecyclerView)dialogView.findViewById(R.id.inquiry_assign_rcv);
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
                        Log.d("assignmemberId :" , ""+AppConfiguration.selectedposition);
                        callInquriyAssignData();

                        alertDialog.dismiss();
                    }
                });
                callInquriyAssignUserData();

                alertDialog.show();
            }
        });
        callInquriyDetailData();
    }
// inquiry deatil data call
    public void callInquriyDetailData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

//        Utils.showDialog(mContext);
        ApiHandler.getApiService().getInquiryDetail(getInquriyDetailData(), new retrofit.Callback<MoreDataModel>() {
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
                    if (moreDataModel.getData().size() != 0) {
                        moreInquiryDetaildataList = moreDataModel.getData();
                        shimmer_view_container.stopShimmerAnimation();
                        shimmer_view_container.setVisibility(View.GONE);
                        templete2.setVisibility(View.GONE);
                        tournament_detailRcv.setVisibility(View.VISIBLE);

                        fillDataValue();

                    } else {
                        shimmer_view_container.stopShimmerAnimation();
                        shimmer_view_container.setVisibility(View.GONE);
                        templete2.setVisibility(View.VISIBLE);
                        tournament_detailRcv.setVisibility(View.GONE);
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

    private Map<String, String> getInquriyDetailData() {
        Map<String, String> map = new HashMap<>();
        map.put("InquiryId", AppConfiguration.inquiryId);
        return map;
    }

    public void fillDataValue() {
        tournamentDetailAdapter = new TournamentDetailAdapter(mContext, moreInquiryDetaildataList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        tournament_detailRcv.setLayoutManager(mLayoutManager);
        tournament_detailRcv.setItemAnimator(new DefaultItemAnimator());
        tournament_detailRcv.setAdapter(tournamentDetailAdapter);
    }


// inquiry assign API
    public void callInquriyAssignUserData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
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
                    if (moreDataModel.getData() != null) {

                        assignmemberlist = moreDataModel.getData();
                        shimmer_view_containerdialog.stopShimmerAnimation();
                        shimmer_view_containerdialog.setVisibility(View.GONE);
                        inquiry_assign_rcv.setVisibility(View.VISIBLE);
                        inquiryAssignAdapter=new InquiryAssignAdapter(mContext,assignmemberlist, new MorestoryClick() {
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

        return map;
    }

    public void callInquriyAssignData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), getActivity());
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
                    if (moreDataModel.getData() != null) {

                        assignmemberlist = moreDataModel.getData();
                        shimmer_view_containerdialog.stopShimmerAnimation();
                        shimmer_view_containerdialog.setVisibility(View.GONE);
                        inquiry_assign_rcv.setVisibility(View.VISIBLE);
                        inquiryAssignAdapter = new InquiryAssignAdapter(mContext, assignmemberlist, new MorestoryClick() {
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
        return map;
    }
}

