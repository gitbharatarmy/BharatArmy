package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.R;

import java.util.List;

public class MatchHistoryAdapter extends RecyclerView.Adapter<MatchHistoryAdapter.MyViewHolder> {
    Context mcontext;
    List<String> matchList;

    public MatchHistoryAdapter(Context mContext, List<String> matchList) {
        this.mcontext = mContext;
        this.matchList = matchList;


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {



        public MyViewHolder(View view) {
            super(view);



        }
    }


    @Override
    public MatchHistoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.match_history_detail_list, parent, false);

        return new MatchHistoryAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MatchHistoryAdapter.MyViewHolder holder, int position) {


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
        return matchList.size();
    }


}




