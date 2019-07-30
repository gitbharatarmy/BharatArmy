package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

public class LoginDialogAdapter extends RecyclerView.Adapter {
    private final ConstraintSet set = new ConstraintSet();
    private final RequestOptions requestOptions = (new RequestOptions()).placeholder(R.drawable.progress_animation);
    Context mContext;
    List<ImageDetailModel> imageDetailModelList;

    public LoginDialogAdapter(Context mContext, List<ImageDetailModel> imageDetailModelsList) {
        this.mContext=mContext;
        this.imageDetailModelList=imageDetailModelsList;
    }



    @NotNull
    public LoginDialogAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        Intrinsics.checkParameterIsNotNull(parent, "parent");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_poster, parent, false);
        Intrinsics.checkExpressionValueIsNotNull(view, "view");
        return new LoginDialogAdapter.ViewHolder(view);
    }

    @SuppressLint({"CheckResult"})
    public void onBindViewHolder(@NotNull LoginDialogAdapter.ViewHolder holder, int position) {
        Intrinsics.checkParameterIsNotNull(holder, "holder");
        Object var3 = this.imageDetailModelList.get(position);
        ImageDetailModel $receiver = (ImageDetailModel) var3;
        boolean var5 = false;
holder.getMMovieName().setVisibility(View.GONE);
        View var10000 = holder.itemView;
        Intrinsics.checkExpressionValueIsNotNull(var10000, "holder.itemView");
//        Picasso.with(var10000.getContext())
//                .load($receiver.getGalleryURL())
//                .placeholder(R.drawable.progress_animation)
//                .into(holder.getMImgPoster());
        Utils.setImageInImageView($receiver.getGalleryURL(),holder.getMImgPoster(),var10000.getContext());

        ConstraintSet var6 = this.set;
        boolean var8 = false;
        StringCompanionObject var9 = StringCompanionObject.INSTANCE;
        String var10 = "%d:%d";
        Object[] var11 = new Object[]{$receiver.getWidth(), $receiver.getHeight()};
        String var13 = String.format(var10, Arrays.copyOf(var11, var11.length));
        Intrinsics.checkExpressionValueIsNotNull(var13, "java.lang.String.format(format, *args)");
        String posterRatio = var13;
        var6.clone(holder.getMConstraintLayout());
        var6.setDimensionRatio(holder.getMImgPoster().getId(), posterRatio);
        var6.applyTo(holder.getMConstraintLayout());
    }

    // $FF: synthetic method
    // $FF: bridge method
    public void onBindViewHolder(RecyclerView.ViewHolder var1, int var2) {
        this.onBindViewHolder((LoginDialogAdapter.ViewHolder)var1, var2);
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
//        @NotNull
//        private final LinearLayout mTextName;

//        @NotNull
//        public LinearLayout getmTextName() {
//            return this.mTextName;
//        }

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

    public void addMoreDataToList(List<ImageDetailModel> result){
        imageDetailModelList.addAll(result);
        notifyDataSetChanged();
    }
}
