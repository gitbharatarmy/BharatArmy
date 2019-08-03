package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.CommentListItemBinding;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.MyViewHolder> {
    Context mcontext;
    List<GalleryImageModel> arrayList;

    public CommentListAdapter(Context mContext, List<GalleryImageModel> arrayList) {
        this.mcontext = mContext;
        this.arrayList = arrayList;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CommentListItemBinding commentListItemBinding;
//         CircleImageView user_image;
//         TextView commented_user_name_txt,commented_user_time_txt,
//                 comment_view_txt,comment_reply_txt,comment_likes_txt,
//                 total_likes_txt;
        public MyViewHolder(CommentListItemBinding commentListItemBinding) {
            super(commentListItemBinding.getRoot());
//            user_image=(CircleImageView)view.findViewById(R.id.user_image);
//            commented_user_name_txt=(TextView)view.findViewById(R.id.commented_user_name_txt);
//            commented_user_time_txt=(TextView)view.findViewById(R.id.commented_user_time_txt);
//            comment_view_txt=(TextView)view.findViewById(R.id.comment_view_txt);
//            comment_reply_txt=(TextView)view.findViewById(R.id.comment_reply_txt);
//            comment_likes_txt=(TextView)view.findViewById(R.id.comment_likes_txt);
//            total_likes_txt=(TextView)view.findViewById(R.id.total_likes_txt);


            this.commentListItemBinding=commentListItemBinding;
        }
    }


    @Override
    public CommentListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.comment_list_item, parent, false);
//
//        return new CommentListAdapter.MyViewHolder(itemView);
        CommentListItemBinding commentListItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.comment_list_item,parent,false);
        return new CommentListAdapter.MyViewHolder(commentListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(CommentListAdapter.MyViewHolder holder, int position) {
        holder.commentListItemBinding.totalLikesTxt.setText(arrayList.get(position).getCommentLikesTotal());
        if (arrayList.get(position).getCommentLikes().equalsIgnoreCase("You like")){
            holder.commentListItemBinding.commentLikesTxt.setText(arrayList.get(position).getCommentLikes());
            holder.commentListItemBinding.commentLikesTxt.setTextColor(Color.parseColor("#f05123"));
        }
         holder.commentListItemBinding.commentViewTxt.setText(Html.fromHtml(arrayList.get(position).getCommentText()));
        holder.commentListItemBinding.commentedUserNameTxt.setText(arrayList.get(position).getCommentuserName());
        holder.commentListItemBinding.commentedUserTimeTxt.setText(arrayList.get(position).getCommenttime());
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
        return arrayList.size();
    }


}


