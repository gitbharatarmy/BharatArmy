package com.bharatarmy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.VideoDetailHorizontalActivity;
import com.bharatarmy.Activity.VideoDetailVerticalActivity;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;

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
        this.activity=activity;
        this.image_click=imageClick;
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

        if (detail.getIsBARecommanded().equals(1)) {
            viewHolder.recommended_image.setVisibility(View.VISIBLE);
        } else {
            viewHolder.recommended_image.setVisibility(View.GONE);
        }
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.viewsMemberId=String.valueOf(Utils.getAppUserId(mContext));
                Utils.viewsReferenceId=String.valueOf(detail.getBAVideoGalleryId());
                Utils.viewsSourceType="2";
                Utils.viewsTokenId= Utils.getPref(mContext, "registration_id");
                Utils.InsertBAViews(mContext,activity);

                dataCheck = new ArrayList<String>();
//                dataCheck.add(String.valueOf(detail.getBAVideoGalleryId())+ "|" +
//                        detail.getVideoImageURL()+"|"+String.valueOf(detail.getIsLike())+"|"+
//                        detail.getUserName()+"|"+detail.getVideoName()+"|"+ detail.getVideoFileURL()+"|"+
//                        detail.getWidth()+"|"+detail.getHeight());
//                image_click.image_more_click();

                if (detail.getWidth()> detail.getHeight()){
                    AppConfiguration.videoType="horizontal";
                    Intent videogalleryhorizontaldetailIntent = new Intent(mContext, VideoDetailHorizontalActivity.class);
                    videogalleryhorizontaldetailIntent.putExtra("videoData", detail.getVideoFileURL());
                    videogalleryhorizontaldetailIntent.putExtra("videoName", detail.getVideoName());
                    videogalleryhorizontaldetailIntent.putExtra("videoUserName", detail.getUserName());
                    videogalleryhorizontaldetailIntent.putExtra("videoLike", String.valueOf(detail.getIsLike()));
                    videogalleryhorizontaldetailIntent.putExtra("WhereToVideoCome", "VideoFragment");
                    videogalleryhorizontaldetailIntent.putExtra("videoId",String.valueOf(detail.getBAVideoGalleryId()));
                    videogalleryhorizontaldetailIntent.putExtra("videoThumb",detail.getVideoImageURL());
                    videogalleryhorizontaldetailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(videogalleryhorizontaldetailIntent);
                }else if(detail.getWidth()<detail.getHeight()){
                    AppConfiguration.videoType="vertical";
                    Intent videogalleryverticaldetailIntent = new Intent(mContext, VideoDetailVerticalActivity.class);
                    videogalleryverticaldetailIntent.putExtra("videoData", detail.getVideoFileURL());
                    videogalleryverticaldetailIntent.putExtra("videoName", detail.getVideoName());
                    videogalleryverticaldetailIntent.putExtra("videoUserName", detail.getUserName());
                    videogalleryverticaldetailIntent.putExtra("videoLike", String.valueOf(detail.getIsLike()));
                    videogalleryverticaldetailIntent.putExtra("WhereToVideoCome", "VideoFragment");
                    videogalleryverticaldetailIntent.putExtra("videoId",String.valueOf(detail.getBAVideoGalleryId()));
                    videogalleryverticaldetailIntent.putExtra("videoThumb",detail.getVideoImageURL());
                    videogalleryverticaldetailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(videogalleryverticaldetailIntent);
                } else if(detail.getWidth().equals(detail.getHeight())){
                    AppConfiguration.videoType="horizontal";
                    Intent videogalleryhorizontaldetailIntent = new Intent(mContext, VideoDetailHorizontalActivity.class);
                    videogalleryhorizontaldetailIntent.putExtra("videoData", detail.getVideoFileURL());
                    videogalleryhorizontaldetailIntent.putExtra("videoName", detail.getVideoName());
                    videogalleryhorizontaldetailIntent.putExtra("videoUserName", detail.getUserName());
                    videogalleryhorizontaldetailIntent.putExtra("videoLike", String.valueOf(detail.getIsLike()));
                    videogalleryhorizontaldetailIntent.putExtra("WhereToVideoCome", "VideoFragment");
                    videogalleryhorizontaldetailIntent.putExtra("videoId",String.valueOf(detail.getBAVideoGalleryId()));
                    videogalleryhorizontaldetailIntent.putExtra("videoThumb",detail.getVideoImageURL());
                    videogalleryhorizontaldetailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(videogalleryhorizontaldetailIntent);
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

        ImageView imageView, recommended_image;
        TextView videoName, video_size_txt;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.video_img);
            videoName = itemView.findViewById(R.id.txtName);
            video_size_txt = itemView.findViewById(R.id.video_size_txt);
            recommended_image = itemView.findViewById(R.id.recommended_image);
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

