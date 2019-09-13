package com.bharatarmy.Adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ItemViewHolder> {

    public List<ImageDetailModel> mItemList;
    Context mContext;
    image_click image_click;
    private ArrayList<String> dataCheck;


    public VideoListAdapter(Context mContext, List<ImageDetailModel> itemList, image_click image_click) {
        this.mContext = mContext;
        this.mItemList = itemList;
        this.image_click = image_click;
    }

    @NonNull
    @Override
    public VideoListAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list, parent, false);
            return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull VideoListAdapter.ItemViewHolder viewHolder, int position) {


        final ImageDetailModel detail = mItemList.get(position);

        Utils.setImageInImageView(detail.getVideoImageURL(), viewHolder.imageView, mContext);
        viewHolder.videoName.setText(detail.getVideoName());
        viewHolder.video_size_txt.setText(detail.getVideoLength());

        if (detail.getIsBARecommanded().equals(1)){
            viewHolder.recommended_image.setVisibility(View.VISIBLE);
        }else{
            viewHolder.recommended_image.setVisibility(View.GONE);
        }

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataCheck = new ArrayList<String>();

                    dataCheck.add(detail.getVideoFileURL()+"|"+detail.getVideoName()); //

                image_click.image_more_click();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView,recommended_image;
        TextView videoName, video_size_txt;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.video_img);
            videoName = itemView.findViewById(R.id.txtName);
            video_size_txt = itemView.findViewById(R.id.video_size_txt);
            recommended_image=itemView.findViewById(R.id.recommended_image);
        }
    }

    public ArrayList<String> getData() {
        return dataCheck;
    }

    public void addMoreDataToList(List<ImageDetailModel> result) {
        mItemList.addAll(result);
        notifyDataSetChanged();
    }
}

