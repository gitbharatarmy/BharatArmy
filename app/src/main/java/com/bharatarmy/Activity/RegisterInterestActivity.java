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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bharatarmy.Adapter.RegisterIntrestAdapter;
import com.bharatarmy.Adapter.TravelMatchDetailHotelRecyclerAdapter;
import com.bharatarmy.Adapter.TravelMatchDetailPackageRecyclerAdapter;
import com.bharatarmy.Adapter.TravelMatchDetailRecyclerAdapter;
import com.bharatarmy.Adapter.TravelMatchDetailSightseensRecyclerAdapter;
import com.bharatarmy.Adapter.TravelMatchDetailTicketsRecyclerAdapter;
import com.bharatarmy.Fragment.MatchFilterFragment;
import com.bharatarmy.Fragment.RegisterInterestFilterFragment;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityRegisterInterestBinding;
import com.bharatarmy.databinding.ActivityTravelMatchDetailNewBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class RegisterInterestActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityRegisterInterestBinding activityRegisterInterestBinding;
    Context mContext;
    String bgImageStr, tourMatchNameStr = "", titleNameStr;
    BottomSheetDialogFragment bottomSheetDialogFragment;

    RegisterIntrestAdapter registerIntrestAdapter;
    List<String> listDataHeader = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRegisterInterestBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_interest);

        mContext = RegisterInterestActivity.this;


        init();
        setListiner();
        setscheduleListValue();
    }

    public void init() {
        Utils.setImageInImageView("https://cdn.drivebird.com/user-content/140000000001/2017/09/627c6d094ccd59cdcf10035482d7497f.jpg",
                activityRegisterInterestBinding.mainPageBgImage, mContext);

        titleNameStr = "schedule";
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
                bottomSheetDialogFragment = new RegisterInterestFilterFragment();
                //show it
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                break;
            case  R.id.submit_linear:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.thankyou_dialog_item, null);
                dialogBuilder.setView(dialogView);
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                TextView hometxt=(TextView)dialogView.findViewById(R.id.home_txt);
                hometxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        finish();
                    }
                });
                alertDialog.show();
                break;
        }

    }

    public void setscheduleListValue() {
        listDataHeader = new ArrayList<String>();

        for (int j = 0; j < 6; j++) {
            listDataHeader.add(String.valueOf(j));

        }
        registerIntrestAdapter = new RegisterIntrestAdapter(mContext, listDataHeader, titleNameStr);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        activityRegisterInterestBinding.registerinterestRcv.setLayoutManager(mLayoutManager);
        activityRegisterInterestBinding.registerinterestRcv.setItemAnimator(new DefaultItemAnimator());
        activityRegisterInterestBinding.registerinterestRcv.setAdapter(registerIntrestAdapter);

    }

}
