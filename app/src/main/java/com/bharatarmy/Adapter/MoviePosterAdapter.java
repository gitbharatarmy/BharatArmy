package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bharatarmy.Models.MoviePoster;
import com.bharatarmy.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

public class MoviePosterAdapter extends RecyclerView.Adapter {
    private final ConstraintSet set = new ConstraintSet();
    private final RequestOptions requestOptions = (new RequestOptions()).placeholder(R.drawable.ic_avatar);
    @NotNull
    private List mMoviePosters = (List)(new ArrayList());

    @NotNull
    public final List getMMoviePosters() {
        return this.mMoviePosters;
    }

    public final void setMMoviePosters(@NotNull List value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        this.mMoviePosters = value;
        this.notifyDataSetChanged();
    }

    @NotNull
    public MoviePosterAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        Intrinsics.checkParameterIsNotNull(parent, "parent");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_poster, parent, false);
        Intrinsics.checkExpressionValueIsNotNull(view, "view");
        return new MoviePosterAdapter.ViewHolder(view);
    }

    @SuppressLint({"CheckResult"})
    public void onBindViewHolder(@NotNull MoviePosterAdapter.ViewHolder holder, int position) {
        Intrinsics.checkParameterIsNotNull(holder, "holder");
        Object var3 = this.mMoviePosters.get(position);
        MoviePoster $receiver = (MoviePoster)var3;
        boolean var5 = false;
        holder.getMMovieName().setText((CharSequence)$receiver.getName());
        View var10000 = holder.itemView;
        Intrinsics.checkExpressionValueIsNotNull(var10000, "holder.itemView");
        Glide.with(var10000.getContext()).setDefaultRequestOptions(this.requestOptions)
                .load($receiver.getImageUrl())
                .into(holder.getMImgPoster());
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
    public void onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder var1, int var2) {
        this.onBindViewHolder((MoviePosterAdapter.ViewHolder)var1, var2);
    }

    public int getItemCount() {
        return this.mMoviePosters.size();
    }

    public final class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        @NotNull
        private final ConstraintLayout mConstraintLayout;
        @NotNull
        private final ImageView mImgPoster;
        @NotNull
        private final TextView mMovieName;

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
        }
    }
}
