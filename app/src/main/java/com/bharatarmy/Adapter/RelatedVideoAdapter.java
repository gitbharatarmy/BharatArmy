package com.bharatarmy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.CommentActivity;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.like.LikeButton;
import java.util.ArrayList;
import java.util.List;

public class RelatedVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER = 0;
    private static final int ITEM = 1;

    Context mContext;
    List<ImageDetailModel> relatedVideoList;
    image_click morestoryClick;
    private ArrayList<String> dataCheck;
    String videoName;

    public RelatedVideoAdapter(Context mContext, List<ImageDetailModel> relatedVideoList, String videoNameStr, image_click morestoryClick) {
        this.mContext = mContext;
        this.relatedVideoList = relatedVideoList;
        this.morestoryClick = morestoryClick;
        this.videoName=videoNameStr;
    }


    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADER : ITEM;
    }

    @Override
    public int getItemCount() {
        return relatedVideoList.size() + 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case HEADER:
                v = layoutInflater.inflate(R.layout.related_video_header, parent, false);
                return new HeaderViewHolder(v);
            default:
                v = layoutInflater.inflate(R.layout.related_video_adapter_item, parent, false);
                return new ItemViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM) {
            final ImageDetailModel relatedVideoDetail = relatedVideoList.get(position - 1);


            Utils.setImageInImageView(relatedVideoDetail.getVideoImageURL(), ((ItemViewHolder) holder).related_video_img, mContext);

            ((ItemViewHolder) holder).video_size_txt.setText(relatedVideoDetail.getVideoLength());
            ((ItemViewHolder) holder).show_video_title_txt.setText(relatedVideoDetail.getVideoName());


            if (relatedVideoDetail.getIsBARecommanded().equals(1)){
                ((ItemViewHolder) holder).recommended_image.setVisibility(View.VISIBLE);
            }else{
                ((ItemViewHolder) holder).recommended_image.setVisibility(View.GONE);
            }

            ((ItemViewHolder) holder).related_video_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoName=relatedVideoDetail.getVideoName();
                    dataCheck = new ArrayList<String>();
                    dataCheck.add(relatedVideoDetail.getVideoFileURL() + "|" + relatedVideoDetail.getVideoName());
                    morestoryClick.image_more_click();

                    notifyDataSetChanged();
                }
            });

        } else if (holder.getItemViewType() == HEADER) {
            ((HeaderViewHolder) holder).show_video_Main_title_txt.setText(videoName);
            ((HeaderViewHolder)holder).type_txt.setText("Bharat Ke Saath");
            ((HeaderViewHolder) holder).video_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent videoIntent=new Intent(mContext, CommentActivity.class);
                    mContext.startActivity(videoIntent);
                }
            });
        }
    }


    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView show_video_Main_title_txt, total_like_txt, total_comment_txt, total_video_view_txt,type_txt;
        LikeButton video_like_btn;
        LinearLayout video_comment;

        HeaderViewHolder(View itemView) {
            super(itemView);
            show_video_Main_title_txt = (TextView) itemView.findViewById(R.id.show_video_Main_title_txt);


            total_like_txt = (TextView) itemView.findViewById(R.id.total_like_txt);
            video_like_btn = (LikeButton) itemView.findViewById(R.id.video_like_btn);

            video_comment = (LinearLayout) itemView.findViewById(R.id.video_comment);
            total_comment_txt = (TextView) itemView.findViewById(R.id.total_comment_txt);

            total_video_view_txt = (TextView) itemView.findViewById(R.id.total_video_view_txt);
            type_txt=(TextView)itemView.findViewById(R.id.type_txt);

        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView related_video_img,recommended_image;
        TextView video_size_txt, show_video_title_txt;
        LinearLayout ba_recommended_linear;


        ItemViewHolder(View itemView) {
            super(itemView);
            related_video_img = (ImageView) itemView.findViewById(R.id.related_video_img);
            video_size_txt = (TextView) itemView.findViewById(R.id.video_size_txt);
            show_video_title_txt = (TextView) itemView.findViewById(R.id.show_video_title_txt);
            ba_recommended_linear = (LinearLayout) itemView.findViewById(R.id.ba_recommended_linear);
            recommended_image=(ImageView)itemView.findViewById(R.id.recommended_image);
        }

    }

    public ArrayList<String> getData() {
        return dataCheck;
    }
}






/*RecyclerView.Adapter<RelatedVideoAdapter.MyViewHolder> {
    Context mcontext;
    List<ImageDetailModel> relatedVideoList;
    image_click morestoryClick;
    private ArrayList<String> dataCheck;

    private final int VIEW_TYPE_HEADER = 0;
    private final int VIEW_TYPE_ITEM = 1;


    public RelatedVideoAdapter(Context mContext, List<ImageDetailModel> relatedVideoList, image_click morestoryClick) {
        this.mcontext = mContext;
        this.relatedVideoList = relatedVideoList;
        this.morestoryClick = morestoryClick;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView related_video_img;
        TextView video_size_txt, show_video_title_txt;
        LinearLayout ba_recommended_linear;

        public MyViewHolder(View view) {
            super(view);
            related_video_img = (ImageView) view.findViewById(R.id.related_video_img);
            video_size_txt = (TextView) view.findViewById(R.id.video_size_txt);
            show_video_title_txt = (TextView) view.findViewById(R.id.show_video_title_txt);
            ba_recommended_linear = (LinearLayout) view.findViewById(R.id.ba_recommended_linear);
        }
    }


    @Override
    public RelatedVideoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.related_video_adapter_item, parent, false);

        return new RelatedVideoAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(RelatedVideoAdapter.MyViewHolder holder, int position) {

        final ImageDetailModel relatedVideoDetail = relatedVideoList.get(position);

     if (relatedVideoDetail.getbARelated().equalsIgnoreCase("true")){
         holder.ba_recommended_linear.setVisibility(View.VISIBLE);
     }else{
         holder.ba_recommended_linear.setVisibility(View.GONE);
     }

        Utils.setImageInImageView(relatedVideoDetail.getVideoImageURL(), holder.related_video_img, mcontext);


        holder.video_size_txt.setText(relatedVideoDetail.getVideoLength());
        holder.show_video_title_txt.setText(relatedVideoDetail.getVideoName());


        holder.related_video_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataCheck = new ArrayList<String>();
                dataCheck.add(relatedVideoDetail.getVideoFileURL() + "|" + relatedVideoDetail.getVideoName());
                morestoryClick.image_more_click();
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
        return relatedVideoList.size();
    }


    public ArrayList<String> getData() {
        return dataCheck;
    }
}*/


