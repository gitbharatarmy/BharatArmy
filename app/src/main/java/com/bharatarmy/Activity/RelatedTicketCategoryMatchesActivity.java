package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bharatarmy.Adapter.TravelMatchTicketHospitalityAdapter;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityRelatedTicketCategoryMatchesBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class RelatedTicketCategoryMatchesActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityRelatedTicketCategoryMatchesBinding activityRelatedTicketCategoryMatchesBinding;
    Context mContext;
    TravelMatchTicketHospitalityAdapter travelMatchTicketHospitalityAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    ArrayList<TravelModel> tickethospitalityList;
    boolean isUp;
    int selectedposition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRelatedTicketCategoryMatchesBinding = DataBindingUtil.setContentView(this, R.layout.activity_related_ticket_category_matches);
        mContext = RelatedTicketCategoryMatchesActivity.this;
        EventBus.getDefault().register(this);


        init();
        setListiner();
        setDataValue();

    }

    public void init() {
        isUp = false;
//        activityRelatedTicketCategoryMatchesBinding.toolbarTitleTxt.setText("Tickets and Hospitality");

        Utils.addCartItemCount(mContext, activityRelatedTicketCategoryMatchesBinding.addcarticon.cartCountItemTxt);
    }

    public void setListiner() {
        activityRelatedTicketCategoryMatchesBinding.backImg.setOnClickListener(this);
//        activityRelatedTicketCategoryMatchesBinding.closeFilter.setOnClickListener(this);

        activityRelatedTicketCategoryMatchesBinding.addcarticon.cartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addcartItemIntent = new Intent(mContext, CartItemShowActivity.class);
                startActivity(addcartItemIntent);
            }
        });
    }

    public void setDataValue() {
        activityRelatedTicketCategoryMatchesBinding.shimmerViewContainer.setVisibility(View.GONE);
        tickethospitalityList = new ArrayList<>();

        tickethospitalityList.add(new TravelModel("http://devenv.bharatarmy.com/Docs/Mobile/25cf4087-b.jpg", "Ticket",
                "Category A", "Lorem Ipsum is simply dummy text.", "Extra 10% off* with Hotel.",
                "₹ 500", "1", "ticket", "0"));

        tickethospitalityList.add(new TravelModel("https://3.imimg.com/data3/VE/IW/MY-16198270/hotel-management-service-500x500.jpg", "Hospitality",
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

        travelMatchTicketHospitalityAdapter = new TravelMatchTicketHospitalityAdapter(mContext, tickethospitalityList, selectedposition, new MorestoryClick() {
            @Override
            public void getmorestoryClick() {
                int addTocartposition = travelMatchTicketHospitalityAdapter.adptercartAddPosition();
                Utils.animationAdd(mContext, activityRelatedTicketCategoryMatchesBinding.addcarticon.cartLayout,
                        activityRelatedTicketCategoryMatchesBinding.toolbar, activityRelatedTicketCategoryMatchesBinding.addcarticon.cartImage,
                        activityRelatedTicketCategoryMatchesBinding.addcarticon.cartCountItemTxt,
                        null, activityRelatedTicketCategoryMatchesBinding.mainLinear,
                        null, addTocartposition,"relatedMatchTicketHospitalityadapter");
            }
        }, new image_click() {
            @Override
            public void image_more_click() {
                Utils.removeCartItemCount(mContext, activityRelatedTicketCategoryMatchesBinding.addcarticon.cartCountItemTxt);
            }
        });
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        activityRelatedTicketCategoryMatchesBinding.ticketHospitalityRcv.setLayoutManager(staggeredGridLayoutManager);
        activityRelatedTicketCategoryMatchesBinding.ticketHospitalityRcv.setAdapter(travelMatchTicketHospitalityAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                finish();
                break;

        }
    }

    @Override
    protected void onResume() {
        Utils.addCartItemCount(mContext, activityRelatedTicketCategoryMatchesBinding.addcarticon.cartCountItemTxt);
        super.onResume();
    }

    @Subscribe
    public void customEventReceived(MyScreenChnagesModel event) {
        Log.d("eventBusPosition :", "" + event.getAdapterremvoePosition());

       if (tickethospitalityList!=null && tickethospitalityList.size()!=0){
           for (int i=0;i<tickethospitalityList.size();i++){
               if (event.getAdapterremvoePosition() == i){
                   travelMatchTicketHospitalityAdapter.notifyItemChanged(i,"remove");
               }
           }
       }

    }
}
