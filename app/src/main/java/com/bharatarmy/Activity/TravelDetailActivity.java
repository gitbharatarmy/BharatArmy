package com.bharatarmy.Activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import com.bharatarmy.Adapter.TravelHospitalityListAdapter;
import com.bharatarmy.Adapter.TravelTicketListAdapter;
import com.bharatarmy.Adapter.TravelTransferListAdapter;
import com.bharatarmy.CountDownClockTravel;
import com.bharatarmy.Models.TravelDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.ActivityTravelDetailBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//sonicwal id/password   Megha Developer@123

//https://www.behance.net/gallery/69985261/Hotel-Booking-Android-App-Design-Concept
public class TravelDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityTravelDetailBinding activityTravelDetailBinding;
    Context mContext;

    public List<TravelDetailModel> ticketArray, hospitalityArray, transferArray;

    TravelTicketListAdapter travelTicketListAdapter;
    TravelHospitalityListAdapter travelHospitalityListAdapter;
    TravelTransferListAdapter travelTransferListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_detail);


        mContext = TravelDetailActivity.this;

        activityTravelDetailBinding.toolbar.setTitle("Match 1");
        setSupportActionBar(activityTravelDetailBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setDataValue();
        setListiner();


    }

    public void setListiner() {
      activityTravelDetailBinding.thirdLocationLinear.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.third_location_linear:
                Intent locationMap=new Intent(mContext,LocationMapActivity.class);
                startActivity(locationMap);
                break;
        }

    }

    public void setDataValue() {
        //Countdown Timer
        Date endDate = new Date();
        final long[] diffInMilis = new long[1];
        final Date startDate = new Date();
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
            String dateToStr = format.format(startDate);
            Log.d("Todaytime", dateToStr);
            SimpleDateFormat formatendDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");

            endDate = formatendDate.parse("30/05/2019 03:00:00 PM");


            final Date finalEndDate = endDate;
//                    Calculate the difference in millisecond between two dates
            diffInMilis[0] = finalEndDate.getTime() - startDate.getTime();
        }catch (ParseException ex){

        }
        activityTravelDetailBinding.timerProgramCountdown.startCountDown(diffInMilis[0]);
        activityTravelDetailBinding.timerProgramCountdown.setCountdownListener(new CountDownClockTravel.CountdownCallBack() {
            @Override
            public void countdownAboutToFinish() {
                activityTravelDetailBinding.timerProgramCountdown.resetCountdownTimer();
            }

            @Override
            public void countdownFinished() {

            }
        });


        ticketArray = new ArrayList<TravelDetailModel>();
        ticketArray.add(new TravelDetailModel(R.drawable.silver_tickets,"Silver",755));
        ticketArray.add(new TravelDetailModel(R.drawable.gold_tickets,"Gold",855));

        travelTicketListAdapter = new TravelTicketListAdapter(mContext, ticketArray);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        activityTravelDetailBinding.ticketDetailRcvList.setLayoutManager(mLayoutManager);
        activityTravelDetailBinding.ticketDetailRcvList.setItemAnimator(new DefaultItemAnimator());
        activityTravelDetailBinding.ticketDetailRcvList.setAdapter(travelTicketListAdapter);


        hospitalityArray = new ArrayList<TravelDetailModel>();
        hospitalityArray.add(new TravelDetailModel(R.drawable.hospital, "HGI Terrace (Exclusive Private Area)","Old Trafford Manchester",
                1895,"Inclusions","Meal",R.drawable.meal));
        hospitalityArray.add(new TravelDetailModel(R.drawable.hospital1, "HGI 101 Suite","Old Trafford Manchester",
                4577,"Inclusions","Meal",R.drawable.meal));

        travelHospitalityListAdapter = new TravelHospitalityListAdapter(mContext, hospitalityArray);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        activityTravelDetailBinding.hospitalityDetailRcvList.setLayoutManager(mLayoutManager1);
        activityTravelDetailBinding.hospitalityDetailRcvList.setItemAnimator(new DefaultItemAnimator());
        activityTravelDetailBinding.hospitalityDetailRcvList.setAdapter(travelHospitalityListAdapter);


        transferArray = new ArrayList<TravelDetailModel>();
        transferArray.add(new TravelDetailModel("London to Manchester on 15th June - 17th June Return"));

        travelTransferListAdapter = new TravelTransferListAdapter(mContext, transferArray);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        activityTravelDetailBinding.transferDetailRcvList.setLayoutManager(mLayoutManager2);
        activityTravelDetailBinding.transferDetailRcvList.setItemAnimator(new DefaultItemAnimator());
        activityTravelDetailBinding.transferDetailRcvList.setAdapter(travelTransferListAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                TravelDetailActivity.this.finish();
                break;
        }
        return true;
    }

}
