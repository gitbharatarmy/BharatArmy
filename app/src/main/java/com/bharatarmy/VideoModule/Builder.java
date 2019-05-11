package com.bharatarmy.VideoModule;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;

import java.io.File;

@SuppressWarnings({"unused", "ClassWithTooManyMethods", "ClassNamingConvention"})
public class Builder {
    private final FullscreenVideoView fullscreenVideoView;
    private final VideoControllerView controller;
    private final OrientationHelper orientationHelper;
    private final VideoMediaPlayer videoMediaPlayer;

    Builder(FullscreenVideoView fullscreenVideoView,
            VideoControllerView controller,
            OrientationHelper orientationHelper,
            VideoMediaPlayer videoMediaPlayer) {
        this.fullscreenVideoView = fullscreenVideoView;
        this.controller = controller;
        this.orientationHelper = orientationHelper;
        this.videoMediaPlayer = videoMediaPlayer;
    }

    public Builder videoFile(@NonNull File videoFile) {
        fullscreenVideoView.setupMediaPlayer(videoFile.getPath());
        return this;
    }

    public Builder videoUrl(@NonNull String videoUrl) {
        fullscreenVideoView.setupMediaPlayer(videoUrl);
        return this;
    }

    public Builder enableAutoStart() {
        fullscreenVideoView.enableAutoStart();
        return this;
    }

    public Builder enterFullscreenDrawable(@NonNull Drawable drawable) {
        controller.setEnterFullscreenDrawable(drawable);
        return this;
    }

    public Builder enterFullscreenDrawable(@DrawableRes int drawableResId) {
        controller.setEnterFullscreenDrawable(getDrawable(drawableResId));
        return this;
    }

    public Builder exitFullscreenDrawable(@NonNull Drawable drawable) {
        controller.setExitFullscreenDrawable(drawable);
        return this;
    }

    public Builder exitFullscreenDrawable(@DrawableRes int drawableResId) {
        controller.setExitFullscreenDrawable(getDrawable(drawableResId));
        return this;
    }

    public Builder playDrawable(@NonNull Drawable drawable) {
        controller.setPlayDrawable(drawable);
        return this;
    }

    public Builder playDrawable(@DrawableRes int drawableResId) {
        controller.setPlayDrawable(getDrawable(drawableResId));
        return this;
    }

    public Builder pauseDrawable(@NonNull Drawable drawable) {
        controller.setPauseDrawable(drawable);
        return this;
    }

    public Builder pauseDrawable(@DrawableRes int drawableResId) {
        controller.setPauseDrawable(getDrawable(drawableResId));
        return this;
    }

    public Builder fastForwardDrawable(@NonNull Drawable drawable) {
        controller.setFastForwardDrawable(drawable);
        return this;
    }

    public Builder fastForwardDrawable(@DrawableRes int drawableResId) {
        controller.setFastForwardDrawable(getDrawable(drawableResId));
        return this;
    }

    public Builder rewindDrawable(@NonNull Drawable drawable) {
        controller.setRewindDrawable(drawable);
        return this;
    }

    public Builder rewindDrawable(@DrawableRes int drawableResId) {
        controller.setRewindDrawable(getDrawable(drawableResId));
        return this;
    }

    public Builder progressBarColor(int progressBarColor) {
        controller.setProgressBarColor(progressBarColor);
        return this;
    }

    public Builder fastForwardSeconds(int fastForwardSeconds) {
        controller.setFastForwardDuration(fastForwardSeconds);
        return this;
    }

    public Builder rewindSeconds(int rewindSeconds) {
        controller.setRewindDuration(rewindSeconds);
        return this;
    }

    public Builder landscapeOrientation(LandscapeOrientation landscapeOrientation) {
        orientationHelper.setLandscapeOrientation(landscapeOrientation);
        return this;
    }

    public Builder portraitOrientation(PortraitOrientation portraitOrientation) {
        orientationHelper.setPortraitOrientation(portraitOrientation);
        return this;
    }

    public Builder disablePause() {
        videoMediaPlayer.disablePause();
        return this;
    }

    public Builder addSeekForwardButton() {
        videoMediaPlayer.addSeekForwardButton();
        return this;
    }

    public Builder addSeekBackwardButton() {
        videoMediaPlayer.addSeekBackwardButton();
        return this;
    }

    @RequiresApi(Build.VERSION_CODES.M)
    public Builder addPlaybackSpeedButton() {
        videoMediaPlayer.addPlaybackSpeedButton();
        return this;
    }

    /**
     * Method implementation: pass 'true' to enable and pass 'false' to disable the play/pause
     * button. It's enabled by default and to disable it the user passes 'false' to the method
     * or just does not use it. In this case passing 'true' or 'false' is confusing.
     *
     * @deprecated As of release 1.0.0, replaced by {@link #disablePause()}
     */
    @Deprecated
    public Builder canPause(boolean canPause) {
        videoMediaPlayer.setPauseEnabled(canPause);
        return this;
    }

    /**
     * Method implementation: pass 'true' to enable and pass 'false' to disable the
     * seek backward button. It's is disabled by default and to enable it the user passes 'true'
     * to the method or just does not use it. In this case passing 'true' or 'false' is confusing.
     *
     * @deprecated As of release 1.0.0, replaced by {@link #addSeekBackwardButton()}
     */
    @Deprecated
    public Builder canSeekBackward(boolean canSeekBackward) {
        videoMediaPlayer.setCanSeekBackward(canSeekBackward);
        return this;
    }

    /**
     * Method implementation: pass 'true' to enable and pass 'false' to disable the
     * seek forward button. It's is disabled by default and to enable it the user passes 'true'
     * to the method or just does not use it. In this case passing 'true' or 'false' is confusing.
     *
     * @deprecated As of release 1.0.0, replaced by {@link #addSeekForwardButton()}
     */
    @Deprecated
    public Builder canSeekForward(boolean canSeekForward) {
        videoMediaPlayer.setCanSeekForward(canSeekForward);
        return this;
    }

    private Drawable getDrawable(int drawableResId) {
        Context context = fullscreenVideoView.getContext();
        return ContextCompat.getDrawable(context, drawableResId);
    }

    public Builder playbackSpeedOptions(PlaybackSpeedOptions playbackSpeedOptions) {
        controller.setPlaybackSpeedOptions(playbackSpeedOptions);
        return this;
    }
}
