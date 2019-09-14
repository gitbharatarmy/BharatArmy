package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.MoreDetailDataModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.CommentListItemBinding;

import java.util.List;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.MyViewHolder> {
    Context mcontext;
    List<ImageDetailModel> commentList;

    public CommentListAdapter(Context mContext, List<ImageDetailModel> arrayList) {
        this.mcontext = mContext;
        this.commentList = arrayList;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CommentListItemBinding commentListItemBinding;

        public MyViewHolder(CommentListItemBinding commentListItemBinding) {
            super(commentListItemBinding.getRoot());


            this.commentListItemBinding=commentListItemBinding;
        }
    }


    @Override
    public CommentListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CommentListItemBinding commentListItemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.comment_list_item,parent,false);
        return new CommentListAdapter.MyViewHolder(commentListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(CommentListAdapter.MyViewHolder holder, int position) {
        ImageDetailModel commentdetail=commentList.get(position);

        holder.commentListItemBinding.commentedUserNameTxt.setText(commentdetail.getMemberName());
        Utils.setImageInImageView(commentdetail.getMemberProfileURL(),holder.commentListItemBinding.userImage,mcontext);
        holder.commentListItemBinding.commentViewTxt.setText(commentdetail.getCommentNotes());
        holder.commentListItemBinding.uploadimageDurationtxt.setText(commentdetail.getStrDuration());

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
        return commentList.size();
    }

    public void addMoreDataToList (List<ImageDetailModel> result) {
        commentList.addAll(result);
        notifyDataSetChanged();
    }
}


