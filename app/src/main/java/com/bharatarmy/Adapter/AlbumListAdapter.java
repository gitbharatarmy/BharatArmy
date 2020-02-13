package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.AlbumDetailActivity;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.TargetCallback;
import com.bharatarmy.Utility.Utils;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

public class AlbumListAdapter extends RecyclerView.Adapter {
    private final ConstraintSet set = new ConstraintSet();
Context mContext;
List<ImageDetailModel> imageDetailModelList;

    public AlbumListAdapter(Context mContext, List<ImageDetailModel> imageDetailModelsList) {
        this.mContext=mContext;
        this.imageDetailModelList=imageDetailModelsList;
    }



    @NotNull
    public AlbumListAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        Intrinsics.checkParameterIsNotNull(parent, "parent");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_poster, parent, false);
        Intrinsics.checkExpressionValueIsNotNull(view, "view");
        return new AlbumListAdapter.ViewHolder(view);
    }

    @SuppressLint({"CheckResult"})
    public void onBindViewHolder(@NotNull AlbumListAdapter.ViewHolder holder, int position) {
        Intrinsics.checkParameterIsNotNull(holder, "holder");
        Object var3 = this.imageDetailModelList.get(position);
        ImageDetailModel $receiver = (ImageDetailModel) var3;
        boolean var5 = false;
        holder.getMMovieName().setText($receiver.getGalleryName());  //(CharSequence)$receiver.getGalleryName()
        View var10000 = holder.itemView;
        Intrinsics.checkExpressionValueIsNotNull(var10000, "holder.itemView");

        Utils.setImageInImageView($receiver.getGalleryThumbURL(),holder.getMImgPoster(),var10000.getContext());


        ConstraintSet var6 = this.set;
        String var10 = "%d:%d";
        Object[] var11 = new Object[]{$receiver.getWidth(), $receiver.getHeight()};
        String var13 = String.format(var10, Arrays.copyOf(var11, var11.length));
        Intrinsics.checkExpressionValueIsNotNull(var13, "java.lang.String.format(format, *args)");
        String posterRatio = var13;
        var6.clone(holder.getMConstraintLayout());
        var6.setDimensionRatio(holder.getMImgPoster().getId(), posterRatio);
        var6.applyTo(holder.getMConstraintLayout());


        holder.getMImgPoster().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent albumdeatilInatent=new Intent(mContext, AlbumDetailActivity.class);
                albumdeatilInatent.putExtra("albumId",String.valueOf($receiver.getBAGalleryId()));
                albumdeatilInatent.putExtra("albumName",$receiver.getGalleryName());
                albumdeatilInatent.putExtra("albumThumb",$receiver.getGalleryThumbURL());
                albumdeatilInatent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(albumdeatilInatent);
            }
        });
    }

    // $FF: synthetic method
    // $FF: bridge method
    public void onBindViewHolder(RecyclerView.ViewHolder var1, int var2) {
        this.onBindViewHolder((AlbumListAdapter.ViewHolder)var1, var2);
    }

    public int getItemCount() {
        return this.imageDetailModelList.size();
    }

    public final class ViewHolder extends RecyclerView.ViewHolder {
        @NotNull
        private final ConstraintLayout mConstraintLayout;
        @NotNull
        private final ImageView mImgPoster;
        @NotNull
        private final TextView mMovieName;

        @NotNull
        private final View mtopView;

        @NotNull
        public View getMtopView() {
            return mtopView;
        }

        @NotNull
        public final ConstraintLayout getMConstraintLayout() {
            return this.mConstraintLayout;
        }

        @NotNull
        public final ImageView getMImgPoster() {
            return this.mImgPoster;
        }

        @NotNull
        public final TextView getMMovieName() {
            return this.mMovieName;
        }


        public ViewHolder(@NotNull View itemView) {
            super(itemView);
            Intrinsics.checkParameterIsNotNull(itemView, "itemView");
            View var10001 = itemView.findViewById(R.id.parentContsraint);
            Intrinsics.checkExpressionValueIsNotNull(var10001, "itemView.findViewById(R.id.parentContsraint)");
            this.mConstraintLayout = (ConstraintLayout)var10001;
            var10001 = itemView.findViewById(R.id.imgSource);
            Intrinsics.checkExpressionValueIsNotNull(var10001, "itemView.findViewById(R.id.imgSource)");
            this.mImgPoster = (ImageView)var10001;
            var10001 = itemView.findViewById(R.id.txtName);
            Intrinsics.checkExpressionValueIsNotNull(var10001, "itemView.findViewById(R.id.txtName)");
            this.mMovieName = (TextView)var10001;

//            var10001 = itemView.findViewById(R.id.textLinear);
//            Intrinsics.checkExpressionValueIsNotNull(var10001, "itemView.findViewById(R.id.txtName)");
//            this.mTextName = (LinearLayout)var10001;

            var10001 = itemView.findViewById(R.id.topView);
            Intrinsics.checkExpressionValueIsNotNull(var10001, "itemView.findViewById(R.id.txtName)");
            this.mtopView = (View)var10001;
        }
    }
    // Clean all elements of the recycler
    public void clear() {
        imageDetailModelList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addMoreDataToList(List<ImageDetailModel> result){
        imageDetailModelList.addAll(result);
        notifyDataSetChanged();
    }
}
