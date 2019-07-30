package com.bharatarmy.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.bharatarmy.Models.HomeTemplateDetailModel;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MyPagerAdapter extends PagerAdapter {

    List<HomeTemplateDetailModel> homeTemplateDetailModelList;
    private Context mContext;


    public MyPagerAdapter(List<HomeTemplateDetailModel> homeTemplateDetailModelList, Context mContext) {
        this.homeTemplateDetailModelList = homeTemplateDetailModelList;
        this.mContext = mContext;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.cardviewpager_item, container, false);
        LinearLayout template2 = (LinearLayout) inflate.findViewById(R.id.template2);
        LinearLayout template1 = (LinearLayout) inflate.findViewById(R.id.template1);
        ImageView image = (ImageView) inflate.findViewById(R.id.image);
        TextView tag_txt = (TextView) inflate.findViewById(R.id.tag_txt);
        TextView item_heading_txt = (TextView) inflate.findViewById(R.id.item_heading_txt);
        TextView item_desc_txt = (TextView) inflate.findViewById(R.id.item_desc_txt);
        TextView book_txt=(TextView)inflate.findViewById(R.id.book_txt);
        LinearLayout book_linear=(LinearLayout)inflate.findViewById(R.id.book_linear);
        View bottom_gradiant_view=(View)inflate.findViewById(R.id.bottom_gradiant_view);

        if (homeTemplateDetailModelList.get(position).getSecondHeaderType().equals(1)) {
            template1.setVisibility(View.GONE);
            template2.setVisibility(View.VISIBLE);
            book_linear.setVisibility(View.GONE);
            bottom_gradiant_view.setVisibility(View.GONE);
            Utils.setImageInImageView(homeTemplateDetailModelList.get(position).getSecondHeaderImageUrl(), image, mContext);

        } else {
            template1.setVisibility(View.VISIBLE);
            template2.setVisibility(View.GONE);
            book_linear.setVisibility(View.VISIBLE);
            bottom_gradiant_view.setVisibility(View.VISIBLE);
            item_desc_txt.setText(homeTemplateDetailModelList.get(position).getSecondHeaderSubText());
            item_heading_txt.setText(homeTemplateDetailModelList.get(position).getSecondHeaderText());
            book_txt.setText(homeTemplateDetailModelList.get(position).getSecondHeaderButtonText());
            book_txt.setTextColor(Color.parseColor(homeTemplateDetailModelList.get(position).getSecondHaderButtonFontColor()));
            GradientDrawable bgShape = (GradientDrawable)book_linear.getBackground();
            bgShape.setColor(Color.parseColor(homeTemplateDetailModelList.get(position).getSecondHaderButtonColor()));
            if (homeTemplateDetailModelList.get(position).getSecondHeaderTag().equalsIgnoreCase("")) {
                tag_txt.setVisibility(View.GONE);
            } else {
                tag_txt.setVisibility(View.VISIBLE);
                tag_txt.setText(homeTemplateDetailModelList.get(position).getSecondHeaderTag());

            }


        }

        container.addView(inflate);
        return inflate;
    }

    @Override
    public int getCount() {
        return homeTemplateDetailModelList.size();
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


