package com.bharatarmy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.bharatarmy.Models.HomeTemplateDetailModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
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
    public Object instantiateItem(ViewGroup container, int position) {
        View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.main_page_deals_item, container, false);
        ImageView imageView1 = (ImageView) inflate.findViewById(R.id.background_image);
        TextView title_text_font=(TextView)inflate.findViewById(R.id.viewpagerheader_txt);


        Utils.setImageInImageView(mainPageArrayList.get(position).getMainHeaderImageUrl(),imageView1,mContext);

        title_text_font.setText(mainPageArrayList.get(position).getMainHeaderText());

        container.addView(inflate);
        return inflate;
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


