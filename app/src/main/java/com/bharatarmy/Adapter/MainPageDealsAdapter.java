package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.DemoRevItemBinding;

import java.util.ArrayList;

public class MainPageDealsAdapter extends RecyclerView.Adapter<MainPageDealsAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TravelModel> mainPageArrayList;
    ArrayList<TravelModel> homedetailList;
    MainPageChildAdapter mainPageChildAdapter;
    MorestoryClick morestoryClick;
    int review_position=0;
    ArrayList<String> scrollposition=new ArrayList<>();

    public MainPageDealsAdapter(Context mContext, ArrayList<TravelModel> homedetailList) {
        this.mContext=mContext;
        this.homedetailList=homedetailList;
    }

//    public MainPageDealsAdapter(Context mContext, ArrayList<TravelModel> mainPageArrayList, ArrayList<TravelModel> homedetailList, MorestoryClick morestoryClick) {
//        this.mContext = mContext;
//        this.mainPageArrayList = mainPageArrayList;
//        this.homedetailList = homedetailList;
//        this.morestoryClick=morestoryClick;
//    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        DemoRevItemBinding demoRevItemBinding;

        public MyViewHolder(DemoRevItemBinding demoRevItemBinding) {
            super(demoRevItemBinding.getRoot());

            this.demoRevItemBinding = demoRevItemBinding;

        }
    }


    @Override
    public MainPageDealsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DemoRevItemBinding demoRevItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.demo_rev_item, parent, false);
        return new MainPageDealsAdapter.MyViewHolder(demoRevItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MainPageDealsAdapter.MyViewHolder holder, int position) {

        TravelModel detail = mainPageArrayList.get(position);

        Utils.setImageInImageView(detail.getCityHotelAmenitiesImage(), holder.demoRevItemBinding.viewpageBg, mContext);

        holder.demoRevItemBinding.headerTxt.setText(detail.getBg_name());
        holder.demoRevItemBinding.itemHeadingTxt.setText(detail.getMain_titleName());
        holder.demoRevItemBinding.itemDescTxt.setText(detail.getMain_desc());
        holder.demoRevItemBinding.bookTxt.setText(detail.getButton_name());

//        mainPageChildAdapter = new MainPageChildAdapter(mContext, homedetailList);
//        SnapHelper mSnapHelper = new LinearSnapHelper();
//        mSnapHelper.attachToRecyclerView(holder.demoRevItemBinding.dealsDetailRcv);
//        holder.demoRevItemBinding.dealsDetailRcv.setOnFlingListener(null);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
//        holder.demoRevItemBinding.dealsDetailRcv.setLayoutManager(layoutManager);
//        holder.demoRevItemBinding.dealsDetailRcv.setAdapter(mainPageChildAdapter);
//
//        holder.demoRevItemBinding.dealsDetailRcv.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
//                    //Dragging
//                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    review_position = layoutManager.findFirstCompletelyVisibleItemPosition();
//                    Log.d("currentposition", ""+review_position);
////                        int viewposition=review_position;
////                        fragmentHomeBinding.mainPageDealsRcv.setCurrentItem(review_position,true);
//                    scrollposition.add(String.valueOf(review_position));
//                    morestoryClick.getmorestoryClick();
//                }
//            }
//
////                @Override
////                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
////                    super.onScrolled(recyclerView, dx, dy);
////                    int firstVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition();
////                    Log.d("firstVisibleItem", ""+firstVisibleItem);
////                    fragmentHomeBinding.mainPageDealsRcv.setCurrentItem(firstVisibleItem,true);
////                }
//        });


    }

    @Override
    public long getItemId(int position) {
// return specific item's id here
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mainPageArrayList.size();
    }
    public ArrayList<String> getDatas() {
        return scrollposition;
    }
}


