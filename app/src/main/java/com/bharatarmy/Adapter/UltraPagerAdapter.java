package com.bharatarmy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.bharatarmy.Activity.TravelMatchDetailActivity;
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
    CardView card_click;

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
        card_click=(CardView)view.findViewById(R.id.card_click);

        Utils.setImageInImageView(traveldetail.getPopularcity_image(),travel_main_banner_img,mContext);

        travel_main_title_txt.setText(traveldetail.getPopularcity_name());
        travel_sub_title_txt.setText(traveldetail.getPopularcity_image_count());



        card_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, TravelMatchDetailActivity.class);
                intent.putExtra("bgImage",traveldetail.getMatch_image());
                intent.putExtra("tourName",traveldetail.getMatch_title());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
