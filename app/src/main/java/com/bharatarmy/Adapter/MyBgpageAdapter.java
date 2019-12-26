package com.bharatarmy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.bharatarmy.Models.HomeTemplateDetailModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.MainPageDealsItemBinding;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MyBgpageAdapter extends PagerAdapter {

   List<HomeTemplateDetailModel> mainPageArrayList;
    private Context mContext;


    public MyBgpageAdapter(List<HomeTemplateDetailModel> mainPageArrayList, Context mContext) {
        this.mainPageArrayList=mainPageArrayList;
        this.mContext=mContext;
    }

    @Override
    public Object instantiateItem(ViewGroup parent, int position) {
        MainPageDealsItemBinding mainPageDealsItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.main_page_deals_item,parent,false);




        Utils.setImageInImageView(mainPageArrayList.get(position).getMainHeaderImageUrl(),mainPageDealsItemBinding.backgroundImage,mContext);

        mainPageDealsItemBinding.viewpagerheaderTxt.setText(mainPageArrayList.get(position).getMainHeaderText());

        parent.addView(mainPageDealsItemBinding.getRoot());
        return mainPageDealsItemBinding.getRoot();
    }

    @Override
    public int getCount() {
        return mainPageArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(((View) object));
    }
}


