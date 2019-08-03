package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.DemoRevItemBinding;

import java.util.ArrayList;

public class MainPageDealsAdapter extends RecyclerView.Adapter<MainPageDealsAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<TravelModel> mainPageArrayList;
    ArrayList<TravelModel> homedetailList;

    public MainPageDealsAdapter(Context mContext, ArrayList<TravelModel> homedetailList) {
        this.mContext=mContext;
        this.homedetailList=homedetailList;
    }



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
}


