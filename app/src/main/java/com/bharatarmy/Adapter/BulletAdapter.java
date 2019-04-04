package com.bharatarmy.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bharatarmy.Models.BulletsPoint;
import com.bharatarmy.Models.WalkthroughData;
import com.bharatarmy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BulletAdapter extends RecyclerView.Adapter<BulletAdapter.MyViewHolder> {
    Context mcontext;
    List<BulletsPoint> bulletList;



    public BulletAdapter(Context mContext, List<BulletsPoint> bulletList) {
        this.mcontext=mContext;
        this.bulletList=bulletList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView bullet_txt;
        ImageView bullet_img;

        public MyViewHolder(View view) {
            super(view);
            bullet_txt = (TextView) view.findViewById(R.id.bullet_txt);
            bullet_img=(ImageView) view.findViewById(R.id.bullet_img);
        }
    }



    @Override
    public BulletAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bullet_list, parent, false);

        return new BulletAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BulletAdapter.MyViewHolder holder, int position) {
        holder.bullet_txt.setText(bulletList.get(position).getBulletName());
        Picasso.with(mcontext)
                .load(bulletList.get(position).getBulletImageURL())
                .into(holder.bullet_img);
    }

    @Override
    public int getItemCount() {
        return bulletList.size();
    }
}
