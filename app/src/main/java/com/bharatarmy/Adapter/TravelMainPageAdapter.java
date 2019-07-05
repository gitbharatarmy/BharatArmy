package com.bharatarmy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraScaleTransformer;

import java.util.List;

public class TravelMainPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER = 0;
    private static final int ITEM = 1;
    private static final int FOOTER =2;
    List<TravelModel> popularcityarrayList;
    List<TravelModel> popularPackageList;
    List<TravelModel> content;
    Context mContext;
    UltraPagerAdapter ultraPagerAdapter;
    TravelPopularCItyAdapter popularCItyAdapter;
    PopularPackageAdapter popularPackageAdapter;

    public TravelMainPageAdapter(Context mContext, List<TravelModel> content, List<TravelModel> popularcityarrayList, List<TravelModel> popularPackageList) {
        this.mContext = mContext;
        this.content = content;
        this.popularcityarrayList = popularcityarrayList;
        this.popularPackageList=popularPackageList;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return HEADER;
        }else if (position==1){
            return ITEM;
        }else {
            return FOOTER;
        }

//        return position == 0 ? HEADER : ITEM;
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case HEADER:
                v = layoutInflater.inflate(R.layout.travel_ultrapage_main, parent, false);
                return new TravelMainPageAdapter.HeaderViewHolder(v);
            case ITEM:
                v = layoutInflater.inflate(R.layout.travel_popularcity_main_item, parent, false);
                return new TravelMainPageAdapter.ItemViewHolder(v);
            default:
                v = layoutInflater.inflate(R.layout.travel_popular_package, parent, false);
                return new TravelMainPageAdapter.FooterViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM) {
            popularCItyAdapter = new TravelPopularCItyAdapter(mContext, popularcityarrayList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
            ((ItemViewHolder) holder).tour_city_list_rcv.setLayoutManager(mLayoutManager);
            ((ItemViewHolder) holder).tour_city_list_rcv.setItemAnimator(new DefaultItemAnimator());
            ((ItemViewHolder) holder).tour_city_list_rcv.setAdapter(popularCItyAdapter);



        } else if (holder.getItemViewType() == HEADER) {

            ((HeaderViewHolder) holder).ultra_viewpager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
            ultraPagerAdapter = new UltraPagerAdapter(true, mContext, content);
            ((HeaderViewHolder) holder).ultra_viewpager.setAdapter(ultraPagerAdapter);
            ((HeaderViewHolder) holder).ultra_viewpager.setCurrentItem(1);
            ((HeaderViewHolder) holder).ultra_viewpager.setMultiScreen(0.77f);
            ((HeaderViewHolder) holder).ultra_viewpager.setItemRatio(1.0f);
            ((HeaderViewHolder) holder).ultra_viewpager.setRatio(1.5f);
            ((HeaderViewHolder) holder).ultra_viewpager.setMaxHeight(600);
            ((HeaderViewHolder) holder).ultra_viewpager.setAutoMeasureHeight(false);
            ((HeaderViewHolder) holder).ultra_viewpager.setPageTransformer(false, new UltraScaleTransformer());
            ((HeaderViewHolder) holder).ultra_viewpager.initIndicator();
            //set style of indicators
            ((HeaderViewHolder) holder).ultra_viewpager.getIndicator()
                    .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                    .setFocusIcon(Utils.DrawableToBitMap(R.drawable.selected_new, mContext))
                    .setNormalIcon(Utils.DrawableToBitMap(R.drawable.unselected_new, mContext))
                    .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, mContext.getResources().getDisplayMetrics()));
            //set the alignment
            ((HeaderViewHolder) holder).ultra_viewpager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
            //construct built-in indicator, and add it to  UltraViewPager
            ((HeaderViewHolder) holder).ultra_viewpager.getIndicator().build();

        } else if (holder.getItemViewType()==FOOTER){
            ((FooterViewHolder) holder).tour_package_list_rcv.setHasFixedSize(true);
            ((FooterViewHolder) holder).tour_package_list_rcv.setNestedScrollingEnabled(false);
            popularPackageAdapter = new PopularPackageAdapter(mContext, popularPackageList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
            ((FooterViewHolder) holder).tour_package_list_rcv.setLayoutManager(mLayoutManager);
            ((FooterViewHolder) holder).tour_package_list_rcv.setItemAnimator(new DefaultItemAnimator());
            ((FooterViewHolder) holder).tour_package_list_rcv.setAdapter(popularPackageAdapter);
        }
    }


    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        UltraViewPager ultra_viewpager;

        HeaderViewHolder(View itemView) {
            super(itemView);
            ultra_viewpager = (UltraViewPager) itemView.findViewById(R.id.ultra_viewpager);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

       RecyclerView tour_city_list_rcv;

        ItemViewHolder(View itemView) {
            super(itemView);
            tour_city_list_rcv=(RecyclerView)itemView.findViewById(R.id.tour_city_list_rcv);
        }

    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {

        RecyclerView tour_package_list_rcv;

        FooterViewHolder(View itemView) {
            super(itemView);
            tour_package_list_rcv=(RecyclerView)itemView.findViewById(R.id.tour_package_list_rcv);
        }

    }
}