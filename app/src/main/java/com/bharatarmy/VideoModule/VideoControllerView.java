package com.bharatarmy.VideoModule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;

import com.bharatarmy.R;

import java.lang.ref.WeakReference;
import java.util.Locale;

public class VideoControllerView extends FrameLayout {
    private static final String TAG = "VideoControllerView";
    private static final int DEFAULT_TIMEOUT = 3000;
    private static final int FADE_OUT = 1;
    private static final int SHOW_PROGRESS = 2;

    @Nullable
    private VideoMediaPlayer videoMediaPlayer;
    private TextView endTime;
    private TextView currentTime;
    private boolean isDragging;
    private PlaybackSpeedPopupMenu popupMenu;
    @Nullable
    private Handler handler = new VideoControllerView.MessageHandler(this);
    private SeekBar progress;
    private ImageButton startPauseButton;
    private ImageButton ffwdButton;
    private ImageButton rewButton;
    private TextView playbackSpeedButton;
    @Nullable
    private View.OnClickListener pauseListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            doPauseResume();
            show(DEFAULT_TIMEOUT);
        }
    };
    @Nullable
    private View.OnClickListener fullscreenListener = new OnClickListener() {
        @Override
        public void onClick(View view) {


            doToggleFullscreen();
            show(DEFAULT_TIMEOUT);
        }
    };
    // There are two scenarios that can trigger the SeekBar listener to trigger:
    //
    // The first is the user using the TouchPad to adjust the position of the
    // SeekBar's thumb. In this case onStartTrackingTouch is called followed by
    // a number of onProgressChanged notifications, concluded by onStopTrackingTouch.
    // We're setting the field "isDragging" to true for the duration of the dragging
    // session to avoid jumps in the position in case of ongoing playback.
    //
    // The second scenario involves the user operating the scroll ball, in this
    // case there WON'T BE onStartTrackingTouch/onStopTrackingTouch notifications,
    // we will simply apply the updated position without suspending regular updates.
    @Nullable
    private SeekBar.OnSeekBarChangeListener seekListener = new OnSeekChangeListener();
    @Nullable
    private View.OnClickListener rewListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (videoMediaPlayer == null) {
                return;
            }

            int pos = videoMediaPlayer.getCurrentPosition();
            pos -= rewindDuration; // milliseconds
            videoMediaPlayer.seekTo(pos);
            setProgress();

            show(DEFAULT_TIMEOUT);
        }
    };
    @Nullable
    private View.OnClickListener ffwdListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (videoMediaPlayer == null) {
                return;
            }

            int pos = videoMediaPlayer.getCurrentPosition();
            pos += fastForwardDuration; // milliseconds
            videoMediaPlayer.seekTo(pos);
            setProgress();

            show(DEFAULT_TIMEOUT);
        }
    };
    @Nullable
    private View.OnClickListener playbackSpeedListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // Inflate the PopupMenu
//            popupMenu.getMenuInflater()
//                    .inflate(R.menu.playback_speed_popup_menu, popupMenu.getMenu());

            popupMenu.setOnSpeedSelectedListener(new PlaybackSpeedPopupMenu.OnSpeedSelectedListener() {
                @Override
                public void onSpeedSelected(float speed, String text) {
                    // Update the Playback Speed Drawable according to the clicked menu item
                    buttonHelper.updatePlaybackSpeedText(text);
                    // Change the Playback Speed of the VideoMediaPlayer
                    videoMediaPlayer.changePlaybackSpeed(speed);
                    // Hide the VideoControllerView
                    hide();
                }
            });

            popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
                @Override
                public void onDismiss(PopupMenu menu) {
                    show();
                }
            });

            // Show the PopupMenu
            popupMenu.show();

            // Show the VideoControllerView and until hide is called
            show(0);
        }
    };

    private ButtonHelper buttonHelper;
    private int progressBarColor = Color.WHITE;

    private int fastForwardDuration = Constants.FAST_FORWARD_DURATION;
    private int rewindDuration = Constants.REWIND_DURATION;

    public VideoControllerView(Context context) {
        super(context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.video_controller, this, true);
        initControllerView();
    }

    public VideoControllerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        layoutInflater.inflate(R.layout.video_controller, this, true);
        initControllerView();
        setupXmlAttributes(attrs);
    }

    private void setupXmlAttributes(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.VideoControllerView, 0, 0);
        buttonHelper.setupDrawables(typedArray);
        setupProgressBar(typedArray);
        // Recycle the TypedArray
        typedArray.recycle();
    }

    private void setupProgressBar(TypedArray a) {
        int color = a.getColor(R.styleable.VideoControllerView_progress_color, 0);
        if (color != 0) {
            // Set the default color
            progressBarColor = color;
        }
        progress.getProgressDrawable().setColorFilter(progressBarColor, PorterDuff.Mode.SRC_IN);
        progress.getThumb().setColorFilter(progressBarColor, PorterDuff.Mode.SRC_IN);
    }

    private void initControllerView() {
        if (!isInEditMode()) {
            setVisibility(INVISIBLE);
        }

        startPauseButton = findViewById(R.id.start_pause_media_button);
        if (startPauseButton != null) {
            startPauseButton.requestFocus();
            startPauseButton.setOnClickListener(pauseListener);
        }

        ImageButton fullscreenButton = findViewById(R.id.fullscreen_media_button);
        if (fullscreenButton != null) {
            fullscreenButton.requestFocus();
            fullscreenButton.setOnClickListener(fullscreenListener);
        }

        ffwdButton = findViewById(R.id.forward_media_button);
        if (ffwdButton != null) {
            ffwdButton.setOnClickListener(ffwdListener);
        }

        rewButton = findViewById(R.id.rewind_media_button);
        if (rewButton != null) {
            rewButton.setOnClickListener(rewListener);
        }

        playbackSpeedButton = findViewById(R.id.playback_speed_button);
        if (playbackSpeedButton != null) {
            playbackSpeedButton.setOnClickListener(playbackSpeedListener);
        }

        buttonHelper = new ButtonHelper(getContext(), startPauseButton, ffwdButton, rewButton,
                fullscreenButton, playbackSpeedButton);

        progress = findViewById(R.id.progress_seek_bar);
        if (progress != null) {
            progress.getProgressDrawable().setColorFilter(progressBarColor, PorterDuff.Mode.SRC_IN);
            progress.getThumb().setColorFilter(progressBarColor, PorterDuff.Mode.SRC_IN);
            progress.setOnSeekBarChangeListener(seekListener);
            progress.setMax(1000);
        }

        endTime = findViewById(R.id.time);
        currentTime = findViewById(R.id.time_current);
    }

    /**
     * Show the controller on screen. It will go away
     * automatically after 3 seconds of inactivity.
     */
    public void show() {
        show(DEFAULT_TIMEOUT);
    }

    /**
     * Change the buttons visibility according to the flags in {@link #videoMediaPlayer}.
     */
    private void setupButtonsVisibility() {
        if (videoMediaPlayer == null) {
            return;
        }

        try {
            if (startPauseButton != null && !videoMediaPlayer.canPause()) {
                startPauseButton.setEnabled(false);
                startPauseButton.setVisibility(INVISIBLE);
            }
            if (rewButton != null && !videoMediaPlayer.showSeekBackwardButton()) {
                rewButton.setEnabled(false);
                rewButton.setVisibility(INVISIBLE);
            }
            if (ffwdButton != null && !videoMediaPlayer.showSeekForwardButton()) {
                ffwdButton.setEnabled(false);
                ffwdButton.setVisibility(INVISIBLE);
            }
            if (playbackSpeedButton != null && !videoMediaPlayer.showPlaybackSpeedButton()) {
                playbackSpeedButton.setEnabled(false);
                playbackSpeedButton.setVisibility(INVISIBLE);
            }
        } catch (IncompatibleClassChangeError ex) {
            // We were given an old version of the interface, that doesn't have
            // the canPause/canSeekXYZ methods. This is OK, it just means we
            // assume the media can be paused and seeked, and so we don't disable
            // the buttons.
            ex.printStackTrace();
        }
    }

    /**
     * Show the controller on screen. It will go away
     * automatically after 'timeout' milliseconds of inactivity.
     *
     * @param timeout The timeout in milliseconds. Use 0 to show
     *                the controller until hide() is called.
     */
    private void show(int timeout) {
        if (!isShowing()) {
            setProgress();
            if (startPauseButton != null) {
                startPauseButton.requestFocus();
            }
            setupButtonsVisibility();
            setVisibility(VISIBLE);
        }

        buttonHelper.updatePausePlay();
        buttonHelper.updateFullScreenDrawable();

        // Cause the progress bar to be updated even if it's showing.
        // This happens, for example, if we're
        // paused with the progress bar showing the user hits play.
        if (handler == null) {
            return;
        }

        handler.sendEmptyMessage(SHOW_PROGRESS);

        Message msg = handler.obtainMessage(FADE_OUT);
        if (timeout != 0) {
            handler.removeMessages(FADE_OUT);
            handler.sendMessageDelayed(msg, timeout);
        } else {
            handler.removeMessages(FADE_OUT);
        }
    }

    public void updateFullScreenDrawable() {
        buttonHelper.updateFullScreenDrawable();
    }

    private boolean isShowing() {
        return getVisibility() == VISIBLE;
    }

    /**
     * Remove the controller from the screen.
     */
    private void hide() {
        try {
            setVisibility(INVISIBLE);
            if (handler != null) {
                handler.removeMessages(SHOW_PROGRESS);
            }
        } catch (IllegalArgumentException ignored) {
            Log.w("MediaController", "already removed");
        }
    }

    private static CharSequence stringForTime(int timeMs) {
        int totalSeconds = timeMs / Constants.ONE_SECOND_MILLISECONDS;
        int seconds = totalSeconds % Constants.ONE_MINUTE_SECONDS;
        int minutes = (totalSeconds / Constants.ONE_MINUTE_SECONDS) % Constants.ONE_MINUTE_SECONDS;
        int hours = totalSeconds / Constants.ONE_HOUR_SECONDS;
        return String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds);
    }

    private int setProgress() {
        if (videoMediaPlayer == null || isDragging) {
            return 0;
        }

        int position = videoMediaPlayer.getCurrentPosition();
        int duration = videoMediaPlayer.getDuration();
        if (progress != null) {
            if (duration > 0) {
                // Use long to avoid overflow
                long pos = Constants.ONE_MILLISECOND * position / duration;
                progress.setProgress((int) pos);
            }
            int percent = videoMediaPlayer.getBufferPercentage();
            progress.setSecondaryProgress(percent * 10);
        }

        if (endTime != null) {
            endTime.setText(stringForTime(duration));
        }

        if (currentTime != null) {
            currentTime.setText(stringForTime(position));
        }

        return position;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        show(DEFAULT_TIMEOUT);
        return true;
    }

    private void doPauseResume() {
        if (videoMediaPlayer == null) {
            return;
        }
        videoMediaPlayer.onPauseResume();
        buttonHelper.updatePausePlay();
    }

    private void doToggleFullscreen() {
        if (videoMediaPlayer == null) {
            return;
        }

        videoMediaPlayer.toggleFullScreen();
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (startPauseButton != null) {
            startPauseButton.setEnabled(enabled);
        }
        if (ffwdButton != null) {
            ffwdButton.setEnabled(enabled);
        }
        if (rewButton != null) {
            rewButton.setEnabled(enabled);
        }
        if (playbackSpeedButton != null) {
            playbackSpeedButton.setEnabled(enabled);
        }
        if (progress != null) {
            progress.setEnabled(enabled);
        }
        setupButtonsVisibility();
        super.setEnabled(enabled);
    }

    public void onDetach() {
        ffwdListener = null;
        fullscreenListener = null;
        pauseListener = null;
        rewListener = null;
        seekListener = null;
        playbackSpeedListener = null;
        handler = null;
        videoMediaPlayer = null;
    }

    public void setEnterFullscreenDrawable(Drawable enterFullscreenDrawable) {
        buttonHelper.setEnterFullscreenDrawable(enterFullscreenDrawable);
    }

    public void setExitFullscreenDrawable(Drawable exitFullscreenDrawable) {
        buttonHelper.setExitFullscreenDrawable(exitFullscreenDrawable);
    }

    public void setProgressBarColor(int progressBarColor) {
        this.progressBarColor = ContextCompat.getColor(getContext(), progressBarColor);
    }

    public void setPlayDrawable(Drawable playDrawable) {
        buttonHelper.setPlayDrawable(playDrawable);
    }

    public void setPauseDrawable(Drawable pauseDrawable) {
        buttonHelper.setPauseDrawable(pauseDrawable);
    }

    public void setFastForwardDuration(int fastForwardDuration) {
        this.fastForwardDuration = fastForwardDuration * 1000;
    }

    public void setRewindDuration(int rewindDuration) {
        this.rewindDuration = rewindDuration * 1000;
    }

    public void setFastForwardDrawable(Drawable fastForwardDrawable) {
        buttonHelper.setFastForwardDrawable(fastForwardDrawable);
    }

    public void setRewindDrawable(Drawable rewindDrawable) {
        buttonHelper.setRewindDrawable(rewindDrawable);
    }

    public void setPlaybackSpeedOptions(PlaybackSpeedOptions playbackSpeedOptions) {
        popupMenu.setPlaybackSpeedOptions(playbackSpeedOptions);
    }

    public void init(final OrientationHelper orientationHelper, VideoMediaPlayer videoMediaPlayer,
                     AttributeSet attrs) {
        setupXmlAttributes(attrs);
        this.videoMediaPlayer = videoMediaPlayer;

        buttonHelper.setOrientationHelper(orientationHelper);
        buttonHelper.setVideoMediaPlayer(videoMediaPlayer);
        buttonHelper.updatePausePlay();
        buttonHelper.updateFullScreenDrawable();
        buttonHelper.updateFastForwardDrawable();
        buttonHelper.updateRewindDrawable();

        // Initialize the PopupMenu
        popupMenu = new PlaybackSpeedPopupMenu(getContext(), playbackSpeedButton);

        getViewTreeObserver().addOnWindowFocusChangeListener(new ViewTreeObserver.OnWindowFocusChangeListener() {
            @Override
            public void onWindowFocusChanged(boolean hasFocus) {
                if (orientationHelper.isLandscape()) {
                    ((Activity) getContext()).getWindow().getDecorView()
                            .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            );
                }
            }
        });
    }

    private static class MessageHandler extends Handler {
        private final WeakReference<VideoControllerView> view;

        MessageHandler(VideoControllerView view) {
            this.view = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            VideoControllerView view = this.view.get();
            if (view == null || view.videoMediaPlayer == null) {
                return;
            }

            if (msg.what == FADE_OUT) {
                view.hide();
            } else { // SHOW_PROGRESS
                int position = view.setProgress();
                if (!view.isDragging && view.isShowing() && view.videoMediaPlayer.isPlaying()) {
                    Message message = obtainMessage(SHOW_PROGRESS);
                    sendMessageDelayed(message, 1000 - (position % 1000));
                }
            }
        }
    }

    private class OnSeekChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            show(Constants.ONE_HOUR_MILLISECONDS);

            isDragging = true;

            // By removing these pending progress messages we make sure
            // that a) we won't update the progress while the user adjusts
            // the seekbar and b) once the user is done dragging the thumb
            // we will post one of these messages to the queue again and
            // this ensures that there will be exactly one message queued up.
            if (handler != null) {
                handler.removeMessages(SHOW_PROGRESS);
            }
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (videoMediaPlayer == null) {
                return;
            }

            if (!fromUser) {
                // We're not interested in programmatically generated changes to
                // the progress bar's position.
                return;
            }

            long duration = videoMediaPlayer.getDuration();
            long newPosition = (duration * progress) / Constants.ONE_MILLISECOND;
            videoMediaPlayer.seekTo((int) newPosition);
            if (currentTime != null) {
                currentTime.setText(stringForTime((int) newPosition));
            }
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            isDragging = false;
            setProgress();
            buttonHelper.updatePausePlay();
            show(DEFAULT_TIMEOUT);

            // Ensure that progress is properly updated in the future,
            // the call to show() does not guarantee this because it is a
            // no-op if we are already showing.
            if (handler != null) {
                handler.sendEmptyMessage(SHOW_PROGRESS);
            }
        }
    }
}
