package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import com.bharatarmy.Adapter.TravelMatchTicketHospitalityAdapter;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.ActivityTravelMatchTicketAndHospitalityBinding;

import java.util.ArrayList;

/*https://stackoverflow.com/questions/35082794/animation-transitions-during-move-from-one-activity-to-another-activity-in-andro
* https://stackoverflow.com/questions/10243557/how-to-apply-slide-animation-between-two-activities-in-android*/

public class TravelMatchTicketAndHospitalityActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityTravelMatchTicketAndHospitalityBinding activityTravelMatchTicketAndHospitalityBinding;
    Context mContext;
    TravelMatchTicketHospitalityAdapter travelMatchTicketHospitalityAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    ArrayList<TravelModel> tickethospitalityList;
    boolean isUp;
    int selectedposition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTravelMatchTicketAndHospitalityBinding = DataBindingUtil.setContentView(this, R.layout.activity_travel_match_ticket_and_hospitality);
        mContext = TravelMatchTicketAndHospitalityActivity.this;

        init();
        setListiner();
        setDataValue();

    }

    public void init() {
        isUp = false;
        activityTravelMatchTicketAndHospitalityBinding.toolbarTitleTxt.setText("Tickets and Hospitality");

    }

    public void setListiner() {
        activityTravelMatchTicketAndHospitalityBinding.backImg.setOnClickListener(this);
//        activityTravelMatchTicketAndHospitalityBinding.closeFilter.setOnClickListener(this);
    }

    public void setDataValue() {
        activityTravelMatchTicketAndHospitalityBinding.shimmerViewContainer.setVisibility(View.GONE);
        tickethospitalityList = new ArrayList<>();

        tickethospitalityList.add(new TravelModel("http://devenv.bharatarmy.com/Docs/Mobile/25cf4087-b.jpg", "Ticket Category",
                "Category A", "Lorem Ipsum is simply dummy text.", "Extra 10% off* with Hotel.",
                "₹ 500", "1", "ticket", "0"));

        tickethospitalityList.add(new TravelModel("https://3.imimg.com/data3/VE/IW/MY-16198270/hotel-management-service-500x500.jpg", "Hospitality Category",
                "The Pavilion", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Dolor sed viverra ipsum nunc aliquet bibendum enim.",
                "", "₹ 475", "3", "hospitality", "0"));

        tickethospitalityList.add(new TravelModel("http://devenv.bharatarmy.com//Docs/e35eee60-7.jpg", "",
                "Category B", "Lorem Ipsum is simply dummy text of the printing.", "",
                "₹ 450", "1", "ticket", "0"));

        tickethospitalityList.add(new TravelModel("https://i0.wp.com/www.perrygroup.com/wp-content/uploads/2016/01/service-pic3-1.jpg", "",
                "Private Suites", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "Extra 20% off* with Hotel.", "₹ 600", "3", "hospitality", "0"));

        tickethospitalityList.add(new TravelModel("http://devenv.bharatarmy.com//Docs/5c6783ff-d.jpg", "",
                "Category C", "Lorem Ipsum is simply dummy text of the printing.", "Extra 20% off* with Hotel.",
                "₹ 350", "3", "ticket", "0"));

        tickethospitalityList.add(new TravelModel("https://www.morganrichardson.co.uk/wp-content/uploads/2017/11/Hotel-Insurance.jpg", "",
                "Open Air Boxes", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                "", "₹ 650", "1", "hospitality", "0"));

        for (int i = 0; i < tickethospitalityList.size(); i++) {
            if (tickethospitalityList.get(i).getTicket_hospitality_selected().equalsIgnoreCase("1")) {
                selectedposition = i;
            }
        }

        travelMatchTicketHospitalityAdapter = new TravelMatchTicketHospitalityAdapter(mContext, tickethospitalityList, selectedposition);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        activityTravelMatchTicketAndHospitalityBinding.ticketHospitalityRcv.setLayoutManager(staggeredGridLayoutManager);
        activityTravelMatchTicketAndHospitalityBinding.ticketHospitalityRcv.setAdapter(travelMatchTicketHospitalityAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;

        }
    }


}
