package com.bharatarmy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.ExoVideoHorizontalPlayerActivity;
import com.bharatarmy.Activity.ExoVideoVerticalPlayerActivity;
import com.bharatarmy.Activity.VideoDetailHorizontalActivity;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.TargetCallback;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.VideoListBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ItemViewHolder> {

    public List<ImageDetailModel> mItemList;
    Context mContext;
    image_click image_click;
    FragmentActivity activity;
    private ArrayList<String> dataCheck;

    public VideoListAdapter(Context mContext, FragmentActivity activity, List<ImageDetailModel> itemList,
                            image_click imageClick) {
        this.mContext = mContext;
        this.mItemList = itemList;
        this.activity = activity;
        this.image_click = imageClick;
    }

    @NonNull
    @Override
    public VideoListAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VideoListBinding videoListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.video_list, parent, false);
        return new VideoListAdapter.ItemViewHolder(videoListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoListAdapter.ItemViewHolder viewHolder, int position) {


        final ImageDetailModel videodetail = mItemList.get(position);

        viewHolder.videoListBinding.videoPrivacyLinear.setVisibility(View.GONE);
        viewHolder.videoListBinding.privateImgLinear.setVisibility(View.GONE);

//        Utils.setImageInImageView(videodetail.getVideoImageURL(), viewHolder.videoListBinding.videoImg, mContext);

        viewHolder.videoListBinding.videoName.setText(videodetail.getVideoName());
        viewHolder.videoListBinding.videoSizeTxt.setText(videodetail.getVideoLength());


        Picasso.with(mContext).load(videodetail.getVideoImageURL()).placeholder(R.drawable.loader_new)
                .into(viewHolder.videoListBinding.videoImg, new TargetCallback(viewHolder.videoListBinding.videoImg) {
            @Override
            public void onSuccess(ImageView target) {
                if (target != null) {
                    viewHolder.videoListBinding.videoPrivacyLinear.setVisibility(View.VISIBLE);
                    if (videodetail.getIsPrivate().equals(1)) {
                        viewHolder.videoListBinding.privateImgLinear.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.videoListBinding.privateImgLinear.setVisibility(View.GONE);
                    }
                }else{
                    viewHolder.videoListBinding.videoPrivacyLinear.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(ImageView target) {
                viewHolder.videoListBinding.videoPrivacyLinear.setVisibility(View.GONE);
            }
        });


        if (videodetail.getIsBARecommanded().equals(1)) {
            viewHolder.videoListBinding.recommendedImage.setVisibility(View.VISIBLE);
        } else {
            viewHolder.videoListBinding.recommendedImage.setVisibility(View.GONE);
        }
        viewHolder.videoListBinding.videoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.viewsMemberId = String.valueOf(Utils.getAppUserId(mContext));
                Utils.viewsReferenceId = String.valueOf(videodetail.getBAVideoGalleryId());
                Utils.viewsSourceType = "2";
                Utils.viewsTokenId = Utils.getPref(mContext, "registration_id");
                Utils.InsertBAViews(mContext, activity);

                dataCheck = new ArrayList<String>();


                if (videodetail.getWidth() > videodetail.getHeight()) {
                    AppConfiguration.videoType = "horizontal";
                    Intent videogalleryhorizontalvideodetailIntent = new Intent(mContext, ExoVideoHorizontalPlayerActivity.class);
                    videogalleryhorizontalvideodetailIntent.putExtra("videoData", videodetail.getVideoFileURL());
                    videogalleryhorizontalvideodetailIntent.putExtra("videoName", videodetail.getVideoName());
                    videogalleryhorizontalvideodetailIntent.putExtra("videoUserName", videodetail.getUserName());
                    videogalleryhorizontalvideodetailIntent.putExtra("videoLike", String.valueOf(videodetail.getIsLike()));
                    videogalleryhorizontalvideodetailIntent.putExtra("WhereToVideoCome", "VideoFragment");
                    videogalleryhorizontalvideodetailIntent.putExtra("videoId", String.valueOf(videodetail.getBAVideoGalleryId()));
                    videogalleryhorizontalvideodetailIntent.putExtra("videoThumb", videodetail.getVideoImageURL());
                    videogalleryhorizontalvideodetailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(videogalleryhorizontalvideodetailIntent);
                } else if (videodetail.getWidth() < videodetail.getHeight()) {
                    AppConfiguration.videoType = "vertical";
                    Intent videogalleryverticalvideodetailIntent = new Intent(mContext, ExoVideoVerticalPlayerActivity.class); //VideoDetailVerticalActivity
                    videogalleryverticalvideodetailIntent.putExtra("videoData", videodetail.getVideoFileURL());
                    videogalleryverticalvideodetailIntent.putExtra("videoName", videodetail.getVideoName());
                    videogalleryverticalvideodetailIntent.putExtra("videoUserName", videodetail.getUserName());
                    videogalleryverticalvideodetailIntent.putExtra("videoLike", String.valueOf(videodetail.getIsLike()));
                    videogalleryverticalvideodetailIntent.putExtra("WhereToVideoCome", "VideoFragment");
                    videogalleryverticalvideodetailIntent.putExtra("videoId", String.valueOf(videodetail.getBAVideoGalleryId()));
                    videogalleryverticalvideodetailIntent.putExtra("videoThumb", videodetail.getVideoImageURL());
                    videogalleryverticalvideodetailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(videogalleryverticalvideodetailIntent);
                } else if (videodetail.getWidth().equals(videodetail.getHeight())) {
                    AppConfiguration.videoType = "horizontal";
                    Intent videogalleryhorizontalvideodetailIntent = new Intent(mContext, ExoVideoHorizontalPlayerActivity.class);
                    videogalleryhorizontalvideodetailIntent.putExtra("videoData", videodetail.getVideoFileURL());
                    videogalleryhorizontalvideodetailIntent.putExtra("videoName", videodetail.getVideoName());
                    videogalleryhorizontalvideodetailIntent.putExtra("videoUserName", videodetail.getUserName());
                    videogalleryhorizontalvideodetailIntent.putExtra("videoLike", String.valueOf(videodetail.getIsLike()));
                    videogalleryhorizontalvideodetailIntent.putExtra("WhereToVideoCome", "VideoFragment");
                    videogalleryhorizontalvideodetailIntent.putExtra("videoId", String.valueOf(videodetail.getBAVideoGalleryId()));
                    videogalleryhorizontalvideodetailIntent.putExtra("videoThumb", videodetail.getVideoImageURL());
                    videogalleryhorizontalvideodetailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(videogalleryhorizontalvideodetailIntent);
                }


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

        //        ImageView imageView, recommended_image;
//        TextView videoName, video_size_txt;
        VideoListBinding videoListBinding;


        public ItemViewHolder(VideoListBinding videoListBinding) {
            super(videoListBinding.getRoot());

            this.videoListBinding = videoListBinding;
//            imageView = itemView.findViewById(R.id.video_img);
//            videoName = itemView.findViewById(R.id.txtName);
//            video_size_txt = itemView.findViewById(R.id.video_size_txt);
//            recommended_image = itemView.findViewById(R.id.recommended_image);
        }
    }

    public ArrayList<String> getData() {
        return dataCheck;
    }

    // Clean all elements of the recycler
    public void clear() {
        mItemList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addMoreDataToList(List<ImageDetailModel> result) {
        mItemList.addAll(result);
        notifyDataSetChanged();
    }
}

