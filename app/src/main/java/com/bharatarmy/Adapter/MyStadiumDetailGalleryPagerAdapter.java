package com.bharatarmy.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.TrackSelectionView;

import java.util.ArrayList;

public class MyStadiumDetailGalleryPagerAdapter extends PagerAdapter implements View.OnClickListener, PlaybackPreparer, PlayerControlView.VisibilityListener {
    ImageView startPauseMediaButton, volmue_video_button, mute_video_button,detailGalleryImage;
    RelativeLayout baVideoRlv;
    LinearLayout volmue_linear;
    LayoutInflater layoutInflater;
    String videopathStr;
    Context mContext;
    ArrayList<TravelModel> stadiumDetailGalleryList;


    public MyStadiumDetailGalleryPagerAdapter(Context mContext, ArrayList<TravelModel> stadiumDetailGalleryList) {
        this.mContext=mContext;
        this.stadiumDetailGalleryList=stadiumDetailGalleryList;
    }

    @Override
    public Object instantiateItem(ViewGroup parent, int position) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.detail_page_gallery_pager_list_item, parent, false);

        startPauseMediaButton = (ImageView)itemView.findViewById(R.id.start_pause_media_button);
        detailGalleryImage = (ImageView)itemView.findViewById(R.id.detail_gallery_image);
        baVideoRlv = (RelativeLayout)itemView.findViewById(R.id.ba_video_rlv);
        volmue_linear=(LinearLayout)itemView.findViewById(R.id.volmue_linear);
        volmue_video_button=(ImageView)itemView.findViewById(R.id.volmue_video_button);
        mute_video_button=(ImageView)itemView.findViewById(R.id.mute_video_button);

        if (stadiumDetailGalleryList.get(position).getCityHotelAmenitiesName().equalsIgnoreCase("Image")) {
            detailGalleryImage.setVisibility(View.VISIBLE);
            baVideoRlv.setVisibility(View.GONE);
            Utils.setImageInImageView(stadiumDetailGalleryList.get(position).getCityHotelAmenitiesImage(),detailGalleryImage, mContext);

            Log.d("HotelGalleeryAdapter : ", stadiumDetailGalleryList.get(position).getCityHotelAmenitiesImage());
        } else {
            detailGalleryImage.setVisibility(View.GONE);
            baVideoRlv.setVisibility(View.VISIBLE);


            videopathStr = stadiumDetailGalleryList.get(position).getCityHotelAmenitiesImage();

            startPauseMediaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Utils.videoPlayinSlider(mContext, baVideo, imageProgress,
//                            startPauseMediaButton, volmueVideoButton,
//                            muteVideoButton, videoThumbnailImage,
//                            volmueLinear, videopathStr, videoplayposition);
                }
            });
            volmue_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.voluesetting(mContext,volmue_video_button,mute_video_button);
                }
            });
//            baVideo.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (baVideo.isPlaying()) {
//                        videoplayposition = baVideo.getCurrentPosition();
//                        baVideo.pause();
//                        Log.d("videorunposition :", "" + videoplayposition);
//                        startPauseMediaButton.setVisibility(View.VISIBLE);
//                        volmueLinear.setVisibility(View.GONE);
//                    } else {
//                        Utils.videoPlayinSlider(mContext, baVideo, imageProgress,
//                                startPauseMediaButton, volmueVideoButton,
//                                muteVideoButton, videoThumbnailImage,
//                                volmueLinear, videopathStr, videoplayposition);
//                    }
//                }
//            });

        }


        parent.addView(itemView);

        return itemView;
    }

    @Override
    public int getCount() {
        return stadiumDetailGalleryList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void onClick(View v) {
        
    }

    @Override
    public void preparePlayback() {

    }

    @Override
    public void onVisibilityChange(int visibility) {

    }
}
