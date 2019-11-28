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

import com.bharatarmy.Adapter.ImageVideoPrivacyAdapter;
import com.bharatarmy.Adapter.InquiryAssignAdapter;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.ActivityImageVideoPrivacyBinding;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ImageVideoPrivacyActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityImageVideoPrivacyBinding activityImageVideoPrivacyBinding;
    Context mContext;
    ImageVideoPrivacyAdapter imageVideoPrivacyAdapter;
    List<GalleryImageModel> privacyoptionList;
    int selectedposition = -1;
    String privacyStr,headertxt,subtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityImageVideoPrivacyBinding = DataBindingUtil.setContentView(this, R.layout.activity_image_video_privacy);
        mContext = ImageVideoPrivacyActivity.this;

        init();
        setListiner();
    }

    public void init() {
        privacyStr=getIntent().getStringExtra("privacytxt");

        privacyoptionList = new ArrayList<GalleryImageModel>();
        privacyoptionList.add(new GalleryImageModel(getResources().getString(R.string.photo_public_option_header_txt), getResources().getString(R.string.photo_public_option_sub_txt),
                R.drawable.ic_aboutus, "1"));
        privacyoptionList.add(new GalleryImageModel(getResources().getString(R.string.photo_private_option_header_txt), getResources().getString(R.string.photo_private_option_sub_txt),
                R.drawable.ic_private_user, "0"));


        for (int i = 0; i < privacyoptionList.size(); i++) {
            if (privacyoptionList.get(i).getHeadertxt().equalsIgnoreCase(privacyStr)) {
                selectedposition = i;
            }
        }
        Log.d("selectedposition : ", "" + selectedposition);
        imageVideoPrivacyAdapter = new ImageVideoPrivacyAdapter(mContext, privacyoptionList, selectedposition, new MorestoryClick() {
            @Override
            public void getmorestoryClick() {
                String getData = String.valueOf(imageVideoPrivacyAdapter.getDatas());

                getData=getData.substring(1,getData.length()-1);
                String[] splitString = getData.split("\\|");

                headertxt = splitString[0];
                subtxt = splitString[1];

                Log.d("headertxt :", headertxt + " subtxt :" + subtxt);
            }

        });
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        activityImageVideoPrivacyBinding.privacyRcv.setLayoutManager(mLayoutManager);
        activityImageVideoPrivacyBinding.privacyRcv.setItemAnimator(new DefaultItemAnimator());
        activityImageVideoPrivacyBinding.privacyRcv.setAdapter(imageVideoPrivacyAdapter);


    }

    public void setListiner() {
        activityImageVideoPrivacyBinding.backImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_img:
                EventBus.getDefault().post(new MyScreenChnagesModel(headertxt,subtxt));
                finish();
                break;
        }
    }
}
