package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bharatarmy.Adapter.RegisterInterestFilterAdapter;
import com.bharatarmy.Adapter.TravelMatchScheduleAdapter;
import com.bharatarmy.Adapter.TravelMatchTicketMAinAdapter;
import com.bharatarmy.Fragment.MatchScheduleAdvanceFragment;
import com.bharatarmy.Fragment.MatchTicketFilterFragment;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.HomeTemplateDetailModel;
import com.bharatarmy.Models.HomeTemplateModel;
import com.bharatarmy.Models.InquiryStatusModel;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.Models.RegisterIntrestFilterDataModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityTravelMatchTicketBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class TravelMatchTicketActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTravelMatchTicketBinding activityTravelMatchTicketBinding;
    Context mContext;

    /*Schedule List*/
    ArrayList<HomeTemplateDetailModel> tournamentticketModel;
    RegisterIntrestFilterDataModel tournamentotherDataModel;
    ArrayList<TravelModel> cityarraylist;

    /*Adapter List*/
    TravelMatchTicketMAinAdapter travelMatchTicketMAinAdapter;
    LinearLayoutManager ticketmLayoutManager;

    /*Advance Filter*/
    BottomSheetDialogFragment bottomSheetDialogFragment;
    ArrayList<HomeTemplateDetailModel> tournamentadvancefilterteamDetailModel;
    ArrayList<HomeTemplateDetailModel> tournamentfiltervenuewithteamDetailModel;
    ArrayList<HomeTemplateDetailModel> tournamentfiltercityDetailModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelMatchTicketBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_match_ticket);
        mContext = TravelMatchTicketActivity.this;



        init();
        setListiner();
    }

    public void init() {
        if (getIntent().getStringExtra("tourtitle")!=null){
            activityTravelMatchTicketBinding.toolbarTitleTxt.setText(getIntent().getStringExtra("tourtitle"));
        }

        activityTravelMatchTicketBinding.shimmerViewContainerTicket.startShimmerAnimation();

        callTravelMatchTicketData();
    }

    public void setListiner() {
        activityTravelMatchTicketBinding.backImg.setOnClickListener(this);
        activityTravelMatchTicketBinding.fabLinear.setOnClickListener(this);
    }

    // Api calling GetTravelMatchTicketData
    public void callTravelMatchTicketData() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), TravelMatchTicketActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getTournamentFixtures(getTravelMatchTicketData(), new retrofit.Callback<HomeTemplateModel>() {
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
//                        tournamentDetailModel = tournamentmodel.getData();
                        tournamentticketModel = new ArrayList<>();
                        for (int i = 0; i < tournamentmodel.getData().size(); i++) {

                            if (tournamentmodel.getData().get(i).getObjFromCountry().getCountryName().equalsIgnoreCase("India") ||
                                    tournamentmodel.getData().get(i).getObjToCountry().getCountryName().equalsIgnoreCase("India")) {
                                tournamentticketModel.add(tournamentmodel.getData().get(i));
                            }
                        }
                        if (tournamentticketModel.size() != 0) {
                            activityTravelMatchTicketBinding.shimmerViewContainerTicket.stopShimmerAnimation();
                            activityTravelMatchTicketBinding.shimmerViewContainerTicket.setVisibility(View.GONE);
                            activityTravelMatchTicketBinding.travelMatchTicketRcv.setVisibility(View.VISIBLE);
                            activityTravelMatchTicketBinding.fabLinear.setVisibility(View.VISIBLE);
                        }
                        if (tournamentmodel.getOtherData() != null) {
//                            cityarraylist = new ArrayList<>();
//                            cityarraylist.add(new TravelModel("1", "Geelong", "Geelong", "graphbackcolor"));
//                            tournamentmodel.getOtherData().setCity(cityarraylist);
                            tournamentotherDataModel = tournamentmodel.getOtherData();
                        }
                        setDataInList();
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

    private Map<String, String> getTravelMatchTicketData() {
        Map<String, String> map = new HashMap<>();
        map.put("TournamentId", "11");
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));
        return map;
    }


    public void setDataInList() {
        /*fill match schedule list*/
        travelMatchTicketMAinAdapter = new TravelMatchTicketMAinAdapter(mContext, tournamentticketModel);
        ticketmLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        activityTravelMatchTicketBinding.travelMatchTicketRcv.setLayoutManager(ticketmLayoutManager);
        activityTravelMatchTicketBinding.travelMatchTicketRcv.setItemAnimator(new DefaultItemAnimator());
        activityTravelMatchTicketBinding.travelMatchTicketRcv.setAdapter(travelMatchTicketMAinAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;
            case R.id.fab_linear:
                bottomSheetDialogFragment = new MatchTicketFilterFragment(tournamentotherDataModel, new MorestoryClick() {
                    @Override
                    public void getmorestoryClick() {
                        activityTravelMatchTicketBinding.noRecordrel.setVisibility(View.GONE);
                        activityTravelMatchTicketBinding.travelMatchTicketRcv.setVisibility(View.GONE);
                        activityTravelMatchTicketBinding.shimmerViewContainerTicket.setVisibility(View.VISIBLE);
                        activityTravelMatchTicketBinding.shimmerViewContainerTicket.startShimmerAnimation();
                        setAdvanceFilter();

                    }
                });
                //show it
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                break;
        }
    }

    public void setAdvanceFilter() {
        ArrayList<String> selectedtournamentteamId = new ArrayList<>();
        for (int i = 0; i < tournamentotherDataModel.getCountries().size(); i++) {
            if (tournamentotherDataModel.getCountries().get(i).getTeamSelected().equalsIgnoreCase("1")) {
                selectedtournamentteamId.add(String.valueOf(tournamentotherDataModel.getCountries().get(i).getCountryId()));
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
        for (int i = 0; i < tournamentotherDataModel.getStadiums().size(); i++) {
            if (tournamentotherDataModel.getStadiums().get(i).getVenueSelected().equalsIgnoreCase("1")) {
                selectedtournamentVenuename.add(tournamentotherDataModel.getStadiums().get(i).getLabel());
                Log.d("selectedVenueName :", selectedtournamentVenuename.toString());
            }
        }

        tournamentadvancefilterteamDetailModel = new ArrayList<>();
        if (selectedtournamentteamId.size() != 0) {
            for (int k = 0; k < selectedtournamentteamId.size(); k++) {
                for (int j = 0; j < tournamentticketModel.size(); j++) {
                    if (selectedtournamentteamId.get(k).trim().equalsIgnoreCase(String.valueOf(tournamentticketModel.get(j).getFromCountryId()).trim())
                            || selectedtournamentteamId.get(k).trim().equalsIgnoreCase(String.valueOf(tournamentticketModel.get(j).getToCountryId()).trim())) {
                        tournamentadvancefilterteamDetailModel.add(tournamentticketModel.get(j));
                    }
                }
            }
        }
        tournamentfiltervenuewithteamDetailModel = new ArrayList<>();
        if (selectedtournamentVenuename.size() != 0) {
            for (int k = 0; k < selectedtournamentVenuename.size(); k++) {
                if (tournamentadvancefilterteamDetailModel.size() != 0) {
                    for (int i = 0; i < tournamentadvancefilterteamDetailModel.size(); i++) {
                        if (selectedtournamentVenuename.get(k).trim().equalsIgnoreCase(tournamentadvancefilterteamDetailModel.get(i).getStadiumName().trim())) {
                            tournamentfiltervenuewithteamDetailModel.add(tournamentadvancefilterteamDetailModel.get(i));
                        }
                    }
                } else {
                    for (int i = 0; i < tournamentticketModel.size(); i++) {
                        if (selectedtournamentVenuename.get(k).trim().equalsIgnoreCase(tournamentticketModel.get(i).getStadiumName().trim())) {
                            tournamentfiltervenuewithteamDetailModel.add(tournamentticketModel.get(i));
                        }
                    }
                }
            }
        } else {
            if (tournamentadvancefilterteamDetailModel.size() != 0) {
                activityTravelMatchTicketBinding.noRecordrel.setVisibility(View.GONE);
                activityTravelMatchTicketBinding.travelMatchTicketRcv.setVisibility(View.VISIBLE);
                activityTravelMatchTicketBinding.shimmerViewContainerTicket.setVisibility(View.GONE);
                travelMatchTicketMAinAdapter = new TravelMatchTicketMAinAdapter(mContext, tournamentadvancefilterteamDetailModel);
                ticketmLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                activityTravelMatchTicketBinding.travelMatchTicketRcv.setLayoutManager(ticketmLayoutManager);
                activityTravelMatchTicketBinding.travelMatchTicketRcv.setItemAnimator(new DefaultItemAnimator());
                activityTravelMatchTicketBinding.travelMatchTicketRcv.setAdapter(travelMatchTicketMAinAdapter);
            }
        }
        Log.d("filterteamData :", "" + tournamentadvancefilterteamDetailModel.size());
        Log.d("filterteamvenueData:", "" + tournamentfiltervenuewithteamDetailModel.size());
        if (selectedtournamentVenuename.size() != 0) {
            if (tournamentfiltervenuewithteamDetailModel.size() != 0) {
                activityTravelMatchTicketBinding.noRecordrel.setVisibility(View.GONE);
                activityTravelMatchTicketBinding.travelMatchTicketRcv.setVisibility(View.VISIBLE);
                activityTravelMatchTicketBinding.shimmerViewContainerTicket.setVisibility(View.GONE);
                travelMatchTicketMAinAdapter = new TravelMatchTicketMAinAdapter(mContext, tournamentfiltervenuewithteamDetailModel);
                ticketmLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                activityTravelMatchTicketBinding.travelMatchTicketRcv.setLayoutManager(ticketmLayoutManager);
                activityTravelMatchTicketBinding.travelMatchTicketRcv.setItemAnimator(new DefaultItemAnimator());
                activityTravelMatchTicketBinding.travelMatchTicketRcv.setAdapter(travelMatchTicketMAinAdapter);

            } else {
                activityTravelMatchTicketBinding.noRecordrel.setVisibility(View.VISIBLE);
                activityTravelMatchTicketBinding.shimmerViewContainerTicket.setVisibility(View.GONE);
                activityTravelMatchTicketBinding.travelMatchTicketRcv.setVisibility(View.GONE);
            }
        }

        if (tournamentadvancefilterteamDetailModel.size() == 0 && tournamentfiltervenuewithteamDetailModel.size() == 0) {
            if (selectedtournamentteamId.size() == 0 && selectedtournamentVenuename.size() == 0) {
                activityTravelMatchTicketBinding.shimmerViewContainerTicket.setVisibility(View.GONE);
                activityTravelMatchTicketBinding.travelMatchTicketRcv.setVisibility(View.VISIBLE);
                activityTravelMatchTicketBinding.noRecordrel.setVisibility(View.GONE);
                travelMatchTicketMAinAdapter = new TravelMatchTicketMAinAdapter(mContext, tournamentticketModel);
                ticketmLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                activityTravelMatchTicketBinding.travelMatchTicketRcv.setLayoutManager(ticketmLayoutManager);
                activityTravelMatchTicketBinding.travelMatchTicketRcv.setItemAnimator(new DefaultItemAnimator());
                activityTravelMatchTicketBinding.travelMatchTicketRcv.setAdapter(travelMatchTicketMAinAdapter);
            } else {
                activityTravelMatchTicketBinding.noRecordrel.setVisibility(View.VISIBLE);
                activityTravelMatchTicketBinding.shimmerViewContainerTicket.setVisibility(View.GONE);
                activityTravelMatchTicketBinding.travelMatchTicketRcv.setVisibility(View.GONE);
            }
        }
    }
}
