package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.TravelDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SelectedImageVideoViewAdapter extends RecyclerView.Adapter<SelectedImageVideoViewAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<String> urlList;

    public SelectedImageVideoViewAdapter(Context mContext, ArrayList<String> urlList) {
        this.mContext = mContext;
        this.urlList = urlList;


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView selected_image;
        TextView selected_image_name_txt, selected_image_remove_txt;

        public MyViewHolder(View view) {
            super(view);

            selected_image = (ImageView) view.findViewById(R.id.selected_image);
            selected_image_name_txt = (TextView) view.findViewById(R.id.selected_image_name_txt);
            selected_image_remove_txt = (TextView) view.findViewById(R.id.selected_image_remove_txt);

        }
    }


    @Override
    public SelectedImageVideoViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.selected_image_video_list_item, parent, false);

        return new SelectedImageVideoViewAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(SelectedImageVideoViewAdapter.MyViewHolder holder, int position) {
//        file:///data/user/0/com.bharatarmy/cache/1559995995507.jpg
        Utils.setImageInImageView(urlList.get(position),holder.selected_image,mContext);

        File file = new File(urlList.get(position));
        String name = file.getName();
        holder.selected_image_name_txt.setText(name);

        holder.selected_image_remove_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
        return urlList.size();
    }
}


