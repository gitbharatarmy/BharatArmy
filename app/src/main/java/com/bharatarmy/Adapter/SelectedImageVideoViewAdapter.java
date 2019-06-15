package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.Models.TravelDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SelectedImageVideoViewAdapter extends RecyclerView.Adapter<SelectedImageVideoViewAdapter.MyViewHolder> {
    Context mContext;
    List<Uri> urlList;
    image_click image_click;
    private ArrayList<String> dataCheck;
    List<GalleryImageModel> imageDetailModel;

    public SelectedImageVideoViewAdapter(Context mContext, List<GalleryImageModel> imageDetailModel, image_click image_click) {
        this.mContext = mContext;
        this.urlList = urlList;
        this.image_click = image_click;
        this.imageDetailModel = imageDetailModel;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView selected_image;
        TextView selected_image_name_txt, selected_image_remove_txt, selected_image_size_txt;

        public MyViewHolder(View view) {
            super(view);

            selected_image = (ImageView) view.findViewById(R.id.selected_image);
            selected_image_name_txt = (TextView) view.findViewById(R.id.selected_image_name_txt);
            selected_image_remove_txt = (TextView) view.findViewById(R.id.selected_image_remove_txt);
            selected_image_size_txt = (TextView) view.findViewById(R.id.selected_image_size_txt);
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
    public void onBindViewHolder(SelectedImageVideoViewAdapter.MyViewHolder holder, final int position) {
//        file:///data/user/0/com.bharatarmy/cache/1559995995507.jpg
//        Log.d("DisplayImages", urlList.get(position).toString());
        Utils.setImageInImageView(imageDetailModel.get(position).getImageUri(), holder.selected_image, mContext);

        File file = new File(imageDetailModel.get(position).getImageUri());
        String name = file.getName();

        holder.selected_image_size_txt.setText(imageDetailModel.get(position).getImageSize());
        holder.selected_image_name_txt.setText(name);

        holder.selected_image_remove_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataCheck = new ArrayList<>();
                dataCheck.add(imageDetailModel.get(position).toString());
                image_click.image_more_click();
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
        return imageDetailModel.size();
    }

    public ArrayList<String> getDatas() {
        return dataCheck;
    }


}


