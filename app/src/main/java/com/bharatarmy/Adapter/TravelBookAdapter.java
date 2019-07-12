package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.MoreStoryActivity;
import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Models.StoryDashboardData;
import com.bharatarmy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TravelBookAdapter extends RecyclerView.Adapter<TravelBookAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<String> bookRecyclerItemList;
    String titleNameStr;
    private int mYear, mMonth, mDay, mHour, mMinute,storemonth,storeyear;
    DatePickerDialog datePickerDialog;
    String selectedDateStr;

    public TravelBookAdapter(Context mContext, ArrayList<String> bookRecyclerItemList, String titleNameStr) {
        this.mContext=mContext;
        this.bookRecyclerItemList=bookRecyclerItemList;
        this.titleNameStr=titleNameStr;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view) {
            super(view);


        }
    }


    @Override
    public TravelBookAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_recycler_item, parent, false);

        return new TravelBookAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelBookAdapter.MyViewHolder holder, int position) {
        // Get Current Date

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


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
        return bookRecyclerItemList.size();
    }


}


