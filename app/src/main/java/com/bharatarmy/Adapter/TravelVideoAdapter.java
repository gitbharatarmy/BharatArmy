package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.AllVideoShowInFullScreenActivity;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.TravelNewUpdateItemBinding;
import com.bharatarmy.databinding.TravelVideoListItemBinding;

import java.util.List;

public class TravelVideoAdapter extends RecyclerView.Adapter<TravelVideoAdapter.MyViewHolder> {
    Context mcontext;
    List<TravelModel> travelvideoList;
    int videoseekposition = 0;
    AudioManager audioManager;
    int musicVolume, maxVolume;
    private MediaPlayer mediaPlayer;

    public TravelVideoAdapter(Context mContext, List<TravelModel> travelvideoList) {
        this.mcontext = mContext;
        this.travelvideoList = travelvideoList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TravelVideoListItemBinding travelVideoListItemBinding;

        public MyViewHolder(TravelVideoListItemBinding travelVideoListItemBinding) {
            super(travelVideoListItemBinding.getRoot());

            this.travelVideoListItemBinding = travelVideoListItemBinding;

        }
    }


    @Override
    public TravelVideoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TravelVideoListItemBinding travelVideoListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.travel_video_list_item, parent, false);
        return new TravelVideoAdapter.MyViewHolder(travelVideoListItemBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(TravelVideoAdapter.MyViewHolder holder, int position) {
        TravelModel videodetail = travelvideoList.get(position);


        Utils.setImageInImageView(videodetail.getCityHotelAmenitiesImage(),holder.travelVideoListItemBinding.image,mcontext);
//        holder.travelVideoListItemBinding.baVideo.setVideoPath(videodetail.getCityHotelAmenitiesName());
//        audioManager = (AudioManager) mcontext.getSystemService(Context.AUDIO_SERVICE);
//        musicVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        holder.travelVideoListItemBinding.baVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showImageVideoIntent = new Intent(mcontext, AllVideoShowInFullScreenActivity.class);
                showImageVideoIntent.putExtra("AlbumImageThumb", travelvideoList.get(position).getCityHotelAmenitiesImage());
                showImageVideoIntent.putExtra("AlbumImageVideoPath", travelvideoList.get(position).getCityHotelAmenitiesName());
                showImageVideoIntent.putExtra("MediaType", "2");
                showImageVideoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(showImageVideoIntent);
//                if (holder.travelVideoListItemBinding.baVideo.isPlaying()) {
//                    videoseekposition = holder.travelVideoListItemBinding.baVideo.getCurrentPosition();
//                    holder.travelVideoListItemBinding.baVideo.pause();
//                    Log.d("videorunposition :", "" + position);
//                    holder.travelVideoListItemBinding.startPauseMediaButton.setVisibility(View.VISIBLE);
//                    holder.travelVideoListItemBinding.fullScreenButton.setVisibility(View.GONE);
//                    holder.travelVideoListItemBinding.volmueLinear.setVisibility(View.GONE);
//                } else {
//                    if (videoseekposition == 0) {
//                        holder.travelVideoListItemBinding.startPauseMediaButton.setVisibility(View.GONE);
//                        holder.travelVideoListItemBinding.imageProgress.setVisibility(View.VISIBLE);
//
//                        holder.travelVideoListItemBinding.baVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                            @Override
//                            public void onPrepared(MediaPlayer mp) {
//                                mp.setLooping(true);
//                                holder.travelVideoListItemBinding.baVideo.start();
//                                mediaPlayer = mp;
//                                if (musicVolume == 0) {
//                                    maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
//                                    audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
//                                }
//                                setVolume(0);
//                                holder.travelVideoListItemBinding.fullScreenButton.setVisibility(View.VISIBLE);
//                                holder.travelVideoListItemBinding.volmueLinear.setVisibility(View.VISIBLE);
//                                holder.travelVideoListItemBinding.image.setVisibility(View.GONE);
//                                holder.travelVideoListItemBinding.imageProgress.setVisibility(View.GONE);
//                            }
//                        });
//
//                        holder.travelVideoListItemBinding.baVideo.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//                            @Override
//                            public boolean onError(MediaPlayer mp, int what, int extra) {
//                                holder.travelVideoListItemBinding.startPauseMediaButton.setVisibility(View.VISIBLE);
//                                holder.travelVideoListItemBinding.fullScreenButton.setVisibility(View.GONE);
//                                holder.travelVideoListItemBinding.volmueLinear.setVisibility(View.GONE);
//                                holder.travelVideoListItemBinding.image.setVisibility(View.VISIBLE);
//                                holder.travelVideoListItemBinding.imageProgress.setVisibility(View.GONE);
//                                return false;
//                            }
//                        });
//                    } else {
//                        holder.travelVideoListItemBinding.startPauseMediaButton.setVisibility(View.GONE);
//                        holder.travelVideoListItemBinding.fullScreenButton.setVisibility(View.VISIBLE);
//                        holder.travelVideoListItemBinding.volmueLinear.setVisibility(View.VISIBLE);
//                        holder.travelVideoListItemBinding.imageProgress.setVisibility(View.VISIBLE);
//                        holder.travelVideoListItemBinding.baVideo.seekTo(position);
//                        holder.travelVideoListItemBinding.baVideo.start();
//                    }
//                }
            }
        });



    }
    private void setVolume(int amount) {
        final int max = 100;
        final double numerator = max - amount > 0 ? Math.log(max - amount) : 0;
        final float volume = (float) (1 - (numerator / Math.log(max)));
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume, volume);
        }
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
        return travelvideoList.size();
    }
}




