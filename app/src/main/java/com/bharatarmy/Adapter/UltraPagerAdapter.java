package com.bharatarmy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;

import java.util.List;

public class UltraPagerAdapter extends PagerAdapter {
    private boolean isMultiScr;
    private LayoutInflater layoutInflater;
    Context mContext;
    List<TravelModel> content;
    ImageView travel_main_banner_img;
    TextView travel_main_title_txt,travel_sub_title_txt;

    public UltraPagerAdapter(boolean isMultiScr, Context mContext, List<TravelModel> content) {
        this.isMultiScr = isMultiScr;
        this.mContext=mContext;
        this.content=content;
    }

    @Override
    public int getCount() {
        return content.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.travel_ultrapage_list_item, container, false);

        TravelModel traveldetail=content.get(position);

        travel_main_banner_img=(ImageView)view.findViewById(R.id.travel_main_banner_img);
        travel_main_title_txt=(TextView)view.findViewById(R.id.travel_main_title_txt);
        travel_sub_title_txt=(TextView)view.findViewById(R.id.travel_sub_title_txt);


        Utils.setImageInImageView(traveldetail.getMatch_image(),travel_main_banner_img,mContext);

        travel_main_title_txt.setText(traveldetail.getMatch_title());
        travel_sub_title_txt.setText(traveldetail.getMatch_shortDesc());

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
