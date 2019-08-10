package com.bharatarmy.Activity;

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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bharatarmy.Adapter.RegisterInterestFilterAdapter;
import com.bharatarmy.Adapter.RegisterIntrestAdapter;
import com.bharatarmy.Fragment.RegisterInterestFilterFragment;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Interfaces.submit_click;
import com.bharatarmy.Models.HomeTemplateDetailModel;
import com.bharatarmy.Models.HomeTemplateModel;
import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityRegisterInterestBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterInterestActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityRegisterInterestBinding activityRegisterInterestBinding;
    Context mContext;
    String titleNameStr, tournamentIdStr, nooftestStr, noofodiStr, nooft20Str,
       memberIdStr,nameStr,emailStr,mobilenoStr,countrydialcodeStr,countrycodeStr,matchidStr;
    BottomSheetDialogFragment bottomSheetDialogFragment;
    int countT20, countODI, countTEST;

    List<HomeTemplateDetailModel> tournamentDetailModel;
    ArrayList<HomeTemplateDetailModel> tournamentfilterDetailModel,tournamentfiltervenuewithteamDetailModel;

    RegisterIntrestAdapter registerIntrestAdapter;
    RegisterInterestFilterAdapter registerInterestFilterAdapter;
    RegisterIntrestFilterDataModel registerIntrestFilterDataModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterInterestBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_interest);

        mContext = RegisterInterestActivity.this;


        init();
        setListiner();

    }

    public void init() {
        tournamentIdStr = getIntent().getStringExtra("tournamentId");


        Utils.setImageInImageView("https://cdn.drivebird.com/user-content/140000000001/2017/09/627c6d094ccd59cdcf10035482d7497f.jpg",
                activityRegisterInterestBinding.mainPageBgImage, mContext);

        titleNameStr = "schedule";

        callRegisterTournamentInterestDetailData();
    }

    public void setListiner() {

        activityRegisterInterestBinding.backImg.setOnClickListener(this);
        activityRegisterInterestBinding.fabLinear.setOnClickListener(this);
        activityRegisterInterestBinding.submitLinear.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                RegisterInterestActivity.this.finish();
                break;
            case R.id.fab_linear:
                bottomSheetDialogFragment = new RegisterInterestFilterFragment(registerIntrestFilterDataModel, new MorestoryClick() {
                    @Override
                    public void getmorestoryClick() {
                        activityRegisterInterestBinding.registerinterestRcv.setVisibility(View.GONE);
                        activityRegisterInterestBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                        setDataOnFilter();

                    }
                });
                //show it
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                break;
            case R.id.submit_linear:
                ArrayList<String> selectedtournamentId = new ArrayList<>();
                for (int i = 0; i < tournamentDetailModel.size(); i++) {
                    if (tournamentDetailModel.get(i).getCheck().equalsIgnoreCase("1")) {
                        selectedtournamentId.add(String.valueOf(tournamentDetailModel.get(i).getTournamentId()));
                        Log.d("selectedtournamentId :", selectedtournamentId.toString());
                    }
                }
                if (selectedtournamentId.size()!=0){
                    String tournamentIdStr;
                    tournamentIdStr = "";
                    for (String s : selectedtournamentId) {
                        tournamentIdStr = tournamentIdStr + "," + s;
                    }
                    Log.d("tournamentIdStr", tournamentIdStr);
                    tournamentIdStr = tournamentIdStr.substring(1, tournamentIdStr.length());
                    Log.d("finalstatusStr", tournamentIdStr);
                    matchidStr=tournamentIdStr;
                    if (!Utils.getPref(mContext,"AppUserId").equalsIgnoreCase("")){
                        memberIdStr=Utils.getPref(mContext,"AppUserId");
                        nameStr=Utils.getPref(mContext, "LoginUserName");
                        emailStr=Utils.getPref(mContext, "LoginEmailId");
                        mobilenoStr=Utils.getPref(mContext, "LoginPhoneNo");
                        countrycodeStr=Utils.getPref(mContext, "CountryISOCode");
                        countrydialcodeStr= Utils.getPref(mContext, "CountryPhoneNo");

                       if (!memberIdStr.equalsIgnoreCase("")&& !nameStr.equalsIgnoreCase("")&&
                           !emailStr.equalsIgnoreCase("")&&!mobilenoStr.equalsIgnoreCase("")&&
                           !mobilenoStr.equalsIgnoreCase("")&&!countrydialcodeStr.equalsIgnoreCase("")&&
                           !countrycodeStr.equalsIgnoreCase("")){
                           callSaveInterestDetailData();
                       }else{
                           Utils.ping(mContext,"blank filed not allowed");
                       }

                    }else{
                        Utils.showSubmitRegisterDialog(RegisterInterestActivity.this, "RegisterInterest", new submit_click() {
                            @Override
                            public void getsubmitClick() {
                                memberIdStr="0";
                                nameStr= AppConfiguration.registerNameStr;
                                emailStr=AppConfiguration.registerEmailStr;
                                mobilenoStr=AppConfiguration.registerMobileStr;
                                countrycodeStr=AppConfiguration.registerCountryCodeStr;
                                countrydialcodeStr= AppConfiguration.registerCountryDialcodeStr;
                                if (!memberIdStr.equalsIgnoreCase("")&& !nameStr.equalsIgnoreCase("")&&
                                        !emailStr.equalsIgnoreCase("")&&!mobilenoStr.equalsIgnoreCase("")&&
                                        !mobilenoStr.equalsIgnoreCase("")&&!countrydialcodeStr.equalsIgnoreCase("")&&
                                        !countrycodeStr.equalsIgnoreCase("")){
                                    callSaveInterestDetailData();
                                }else{
                                    Utils.ping(mContext,"blank filed not allowed");
                                }

                            }
                        });
                    }

                }else{
                    Utils.ping(mContext,"Please select atleast one match");
                }

                break;
        }

    }


    // Api calling GetTournamentInterestDetailData
    public void callRegisterTournamentInterestDetailData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), RegisterInterestActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getTournamentFixtures(getTournamentInterestDetailData(), new retrofit.Callback<HomeTemplateModel>() {
            @Override
            public void success(HomeTemplateModel tournamentmodel, Response response) {
                Utils.dismissDialog();
                if (tournamentmodel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (tournamentmodel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (tournamentmodel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (tournamentmodel.getIsValid() == 1) {

                    if (tournamentmodel.getData() != null) {
                        tournamentDetailModel = tournamentmodel.getData();
                        activityRegisterInterestBinding.shimmerViewContainer.setVisibility(View.GONE);
                        activityRegisterInterestBinding.registerinterestRcv.setVisibility(View.VISIBLE);
                        activityRegisterInterestBinding.fabLinear.setVisibility(View.VISIBLE);
                        activityRegisterInterestBinding.submitLinear.setVisibility(View.VISIBLE);
                        settournamentscheduleListValue();
                    }

                    if (tournamentmodel.getOtherData() != null) {
                        registerIntrestFilterDataModel = tournamentmodel.getOtherData();
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

    private Map<String, String> getTournamentInterestDetailData() {
        Map<String, String> map = new HashMap<>();
        map.put("TournamentId", tournamentIdStr);
        return map;
    }

    public void settournamentscheduleListValue() {
        for (int i = 0; i < tournamentDetailModel.size(); i++) {
            if (tournamentDetailModel.get(i).getStrMatchType().equalsIgnoreCase("T20")) {
                countT20++;
            }
            if (tournamentDetailModel.get(i).getStrMatchType().equalsIgnoreCase("TEST")) {
                countTEST++;
            }
            if (tournamentDetailModel.get(i).getStrMatchType().equalsIgnoreCase("ODI")) {
                countODI++;
            }
        }

        nooftestStr = String.valueOf(countTEST);
        noofodiStr = String.valueOf(countODI);
        nooft20Str = String.valueOf(countT20);

        Log.d("T20 : ", nooft20Str + "ODI : " + noofodiStr + "TEST : " + nooftestStr);


        for (int k = 0; k < tournamentDetailModel.size(); k++) {
            tournamentDetailModel.get(k).setCheck("0");
        }

        registerIntrestAdapter = new RegisterIntrestAdapter(mContext, tournamentDetailModel,
                titleNameStr, nooft20Str, noofodiStr, nooftestStr, new MorestoryClick() {
            @Override
            public void getmorestoryClick() {

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        activityRegisterInterestBinding.registerinterestRcv.setLayoutManager(mLayoutManager);
        activityRegisterInterestBinding.registerinterestRcv.setItemAnimator(new DefaultItemAnimator());
        activityRegisterInterestBinding.registerinterestRcv.setAdapter(registerIntrestAdapter);

    }


    public void setDataOnFilter() {
        ArrayList<String> selectedtournamentteamId = new ArrayList<>();
        for (int i = 0; i < registerIntrestFilterDataModel.getCountries().size(); i++) {
            if (registerIntrestFilterDataModel.getCountries().get(i).getTeamSelected().equalsIgnoreCase("1")) {
                selectedtournamentteamId.add(String.valueOf(registerIntrestFilterDataModel.getCountries().get(i).getCountryId()));
                Log.d("selectedteamId :", selectedtournamentteamId.toString());
            }
        }
        String tournamentIdStr;
        tournamentIdStr = "";
        if (selectedtournamentteamId.size() != 0) {
            for (String s : selectedtournamentteamId) {
                tournamentIdStr = tournamentIdStr + "," + s;
            }
            Log.d("tournamentIdStr", tournamentIdStr);
            tournamentIdStr = tournamentIdStr.substring(1, tournamentIdStr.length());
            Log.d("finalstatusStr", tournamentIdStr);
        }

        ArrayList<String> selectedtournamentVenuename = new ArrayList<>();
        for (int i = 0; i < registerIntrestFilterDataModel.getStadiums().size(); i++) {
            if (registerIntrestFilterDataModel.getStadiums().get(i).getVenueSelected().equalsIgnoreCase("1")) {
                selectedtournamentVenuename.add(registerIntrestFilterDataModel.getStadiums().get(i).getLabel());
                Log.d("selectedVenueName :", selectedtournamentVenuename.toString());
            }
        }

        tournamentfilterDetailModel = new ArrayList<>();
        if (selectedtournamentteamId.size() != 0) {
            for (int k = 0; k < selectedtournamentteamId.size(); k++) {
                for (int j = 0; j < tournamentDetailModel.size(); j++) {
                    if (selectedtournamentteamId.get(k).trim().equalsIgnoreCase(String.valueOf(tournamentDetailModel.get(j).getFromCountryId()).trim())
                            || selectedtournamentteamId.get(k).trim().equalsIgnoreCase(String.valueOf(tournamentDetailModel.get(j).getToCountryId()).trim())) {
                        tournamentfilterDetailModel.add(tournamentDetailModel.get(j));
                    }
                }
            }
        }
        tournamentfiltervenuewithteamDetailModel=new ArrayList<>();
        if (selectedtournamentVenuename.size()!=0) {
            for (int k = 0; k < selectedtournamentVenuename.size(); k++) {
                if (tournamentfilterDetailModel.size()!=0) {
                    for (int i = 0; i < tournamentfilterDetailModel.size(); i++) {
                        if (selectedtournamentVenuename.get(k).trim().equalsIgnoreCase(tournamentfilterDetailModel.get(i).getStadiumName().trim())) {
                            tournamentfiltervenuewithteamDetailModel.add(tournamentfilterDetailModel.get(i));
                        }
                    }
                }else{
                    for (int i = 0; i < tournamentDetailModel.size(); i++) {
                        if (selectedtournamentVenuename.get(k).trim().equalsIgnoreCase(tournamentDetailModel.get(i).getStadiumName().trim())) {
                            tournamentfiltervenuewithteamDetailModel.add(tournamentDetailModel.get(i));
                        }
                    }
                }
            }
        }else {
            if (tournamentfilterDetailModel.size()!=0){
                for (int k = 0; k < tournamentfilterDetailModel.size(); k++) {
                    tournamentfilterDetailModel.get(k).setCheck("0");
                }
                activityRegisterInterestBinding.registerinterestRcv.setVisibility(View.VISIBLE);
                activityRegisterInterestBinding.shimmerViewContainer.setVisibility(View.GONE);
                registerInterestFilterAdapter = new RegisterInterestFilterAdapter(mContext, tournamentfilterDetailModel,
                        titleNameStr, nooft20Str, noofodiStr, nooftestStr, new MorestoryClick() {
                    @Override
                    public void getmorestoryClick() {

                    }
                });
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
                activityRegisterInterestBinding.registerinterestRcv.setLayoutManager(mLayoutManager);
                activityRegisterInterestBinding.registerinterestRcv.setItemAnimator(new DefaultItemAnimator());
                activityRegisterInterestBinding.registerinterestRcv.setAdapter(registerInterestFilterAdapter);
                registerInterestFilterAdapter.notifyDataSetChanged();
            }
        }
        Log.d("filterteamData :", "" + tournamentfilterDetailModel.size());
        Log.d("filterteamvenueData:", "" + tournamentfiltervenuewithteamDetailModel.size());

        if (tournamentfiltervenuewithteamDetailModel.size() != 0) {
            for (int k = 0; k < tournamentfiltervenuewithteamDetailModel.size(); k++) {
                tournamentfiltervenuewithteamDetailModel.get(k).setCheck("0");
            }
            activityRegisterInterestBinding.registerinterestRcv.setVisibility(View.VISIBLE);
            activityRegisterInterestBinding.shimmerViewContainer.setVisibility(View.GONE);
            registerInterestFilterAdapter = new RegisterInterestFilterAdapter(mContext, tournamentfiltervenuewithteamDetailModel,
                    titleNameStr, nooft20Str, noofodiStr, nooftestStr, new MorestoryClick() {
                @Override
                public void getmorestoryClick() {

                }
            });
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
            activityRegisterInterestBinding.registerinterestRcv.setLayoutManager(mLayoutManager);
            activityRegisterInterestBinding.registerinterestRcv.setItemAnimator(new DefaultItemAnimator());
            activityRegisterInterestBinding.registerinterestRcv.setAdapter(registerInterestFilterAdapter);
            registerInterestFilterAdapter.notifyDataSetChanged();

        }else {
        }

    }



    // Api calling SaveInterestDetailData
    public void callSaveInterestDetailData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), RegisterInterestActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getSaveRegisterInterest(getSaveInterestDetailData(), new retrofit.Callback<LogginModel>() {
            @Override
            public void success(LogginModel tournamentmodel, Response response) {
                Utils.dismissDialog();
                if (tournamentmodel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (tournamentmodel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (tournamentmodel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (tournamentmodel.getIsValid() == 1) {

                    if (tournamentmodel.getData() != null) {
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
finish();
                            }
                        });
                        alertDialog.show();
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

    private Map<String, String> getSaveInterestDetailData() {
        Map<String, String> map = new HashMap<>();
        map.put("TournamentId", tournamentIdStr);
        map.put("MemberId",memberIdStr);
        map.put("Name",nameStr);
        map.put("Email",emailStr);
        map.put("MobileNo",mobilenoStr);
        map.put("CountryDialcode",countrydialcodeStr);
        map.put("CountryCode",countrycodeStr);
        map.put("MatchIds",matchidStr);
        return map;
    }
}
