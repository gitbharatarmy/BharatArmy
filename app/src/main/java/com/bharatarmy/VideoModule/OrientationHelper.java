package com.bharatarmy.VideoModule;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bharatarmy.Interfaces.MorestoryClick;
import com.bharatarmy.Interfaces.MyLayoutChanges;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.VideoTrimmer.interfaces.OnTrimVideoListener;

import org.greenrobot.eventbus.EventBus;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;

public class OrientationHelper extends OrientationEventListener {
    private static final int LEFT_LANDSCAPE = 90;
    private static final int RIGHT_LANDSCAPE = 270;
    private static final int PORTRAIT = 0;
    private static final int ROTATE_THRESHOLD = 10;

    private final FullscreenVideoView videoView;
    private int originalWidth;
    private int originalHeight;
    private boolean isLandscape;
    private final ContentResolver contentResolver;
    // Orientation
    private LandscapeOrientation landscapeOrientation = LandscapeOrientation.SENSOR;
    private PortraitOrientation portraitOrientation = PortraitOrientation.DEFAULT;
    private boolean shouldEnterPortrait;
    private VideoFullViewCallback videoViewCallback;
    private MyLayoutChanges myLayoutChanges;

    public OrientationHelper(Context context, FullscreenVideoView fullscreenVideoView) {
        super(context);
        videoView = fullscreenVideoView;
        contentResolver = context.getContentResolver();

    }

    public void activateFullscreen() {
        // Update isLandscape flag
        isLandscape = true;

        if (videoViewCallback != null) {
            videoViewCallback.onFullScaleChange();
        }
        // Fullscreen active
        videoView.onOrientationChanged();

        // Change the screen orientation to SENSOR_LANDSCAPE
        setOrientation(landscapeOrientation.getValue());


//        UiUtils.hideOtherViews(getParent());

        // Save the video player original width and height
        originalWidth = videoView.getWidth();
        originalHeight = videoView.getHeight();
        Log.d("height ,width", "originalHeight :" + originalHeight + "originalWidth :" + originalWidth);
        updateLayoutParams();

        // Hide the supportToolbar
        toggleToolbarVisibility(false);

        // Hide status bar
        toggleSystemUiVisibility();
    }

    private void updateLayoutParams() {
        ViewGroup.LayoutParams params = videoView.getLayoutParams();
        Context context = videoView.getContext();

//        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        if (windowManager == null) {
//            return;
//        }
//        Display display = windowManager.getDefaultDisplay();
//        DisplayMetrics realMetrics = new DisplayMetrics();
//        display.getRealMetrics(realMetrics);
////
//        params.width =realMetrics.widthPixels;
//        params.height = realMetrics.heightPixels;
        params.width = params.MATCH_PARENT;
        params.height = params.MATCH_PARENT;
        videoView.setLayoutParams(params);


    }

    public void exitFullscreen() {
        // Update isLandscape flag
        isLandscape = false;

        // Update the fullscreen button drawable
        videoView.onOrientationChanged();

        // Change the screen orientation to PORTRAIT
        setOrientation(portraitOrientation.getValue());

        UiUtils.showOtherViews(getParent());

        ViewGroup.LayoutParams params = videoView.getLayoutParams();
        Log.d("height ,width", "originalHeight :" + originalHeight + "originalWidth :" + originalWidth);
        params.width = originalWidth;
        params.height = originalHeight;
        videoView.setLayoutParams(params);

        toggleToolbarVisibility(true);
        toggleSystemUiVisibility();

    }

    private ViewGroup getParent() {
        Window window = ((Activity) videoView.getContext()).getWindow();
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        return decorView.findViewById(android.R.id.content);
    }

    private void toggleSystemUiVisibility() {
        Window activityWindow = ((Activity) videoView.getContext()).getWindow();
        View decorView = activityWindow.getDecorView();
        int newUiOptions = decorView.getSystemUiVisibility();
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(newUiOptions);
    }

    private void toggleToolbarVisibility(boolean visible) {
        if (videoView.getContext() instanceof AppCompatActivity) {
            toggleSupportActionBarVisibility(visible);

        }
        if (videoView.getContext() instanceof Activity) {
            toggleActionBarVisibility(visible);
        }
    }

    private void toggleActionBarVisibility(boolean visible) {
        // Activity action bar
        android.app.ActionBar actionBar = ((Activity) videoView.getContext()).getActionBar();
        if (actionBar != null) {
            if (visible) {
                actionBar.show();
            } else {
                actionBar.hide();
            }
        }
    }

    private void toggleSupportActionBarVisibility(boolean visible) {
        // AppCompatActivity support action bar
        ActionBar supportActionBar = ((AppCompatActivity) videoView.getContext())
                .getSupportActionBar();
        if (supportActionBar != null) {
            if (visible) {
                supportActionBar.show();
            } else {
                supportActionBar.hide();
            }
        }
    }

    private void setOrientation(int orientation) {
        ((Activity) videoView.getContext()).setRequestedOrientation(orientation);
//        Activity a= (Activity)(videoView.getContext());
//        a.runOnUiThread(a.setRequestedOrientation(orientation);

        Utils.unwrap(videoView.getContext()).setRequestedOrientation(orientation);

    }

    public boolean shouldHandleOnBackPressed() {
        if (isLandscape) {
            // Locks the screen orientation to portrait
            setOrientation(portraitOrientation.getValue());
            videoView.onOrientationChanged();
            return true;
        }

        return false;
    }

    // change the screen orientation
    public void toggleFullscreen() {
        if (AppConfiguration.videoType.equalsIgnoreCase("horizontal")) {
            isLandscape = !isLandscape;
            int newOrientation = portraitOrientation.getValue();
            if (isLandscape) {
                newOrientation = landscapeOrientation.getValue();
            }
            setOrientation(newOrientation);
        } else {
            isLandscape = !isLandscape;
            int newOrientation = portraitOrientation.getValue();
            if (isLandscape) {
                newOrientation = portraitOrientation.getValue();
            }
            setOrientation(newOrientation);
            if (AppConfiguration.screenType.equalsIgnoreCase("")) {
                AppConfiguration.screenType = "Full";
                EventBus.getDefault().post(new MyScreenChnagesModel(AppConfiguration.screenType));
            }else{
                AppConfiguration.screenType = "Half";
                EventBus.getDefault().post(new MyScreenChnagesModel(AppConfiguration.screenType));
            }




        }

    }


    public void setLandscapeOrientation(LandscapeOrientation landscapeOrientation) {
        this.landscapeOrientation = landscapeOrientation;
    }

    public void setPortraitOrientation(PortraitOrientation portraitOrientation) {
        this.portraitOrientation = portraitOrientation;
    }

    private boolean shouldChangeOrientation(int a, int b) {
        return a > b - ROTATE_THRESHOLD && a < b + ROTATE_THRESHOLD;
    }

    @Override
    public void onOrientationChanged(int orientation) {
        // If the device's rotation is not enabled do not proceed further with the logic
        if (!isRotationEnabled(contentResolver)) {
            return;
        }

        if ((shouldChangeOrientation(orientation, LEFT_LANDSCAPE)
                || shouldChangeOrientation(orientation, RIGHT_LANDSCAPE))
                && !shouldEnterPortrait) {
            shouldEnterPortrait = true;
            setOrientation(SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }

        if (shouldChangeOrientation(orientation, PORTRAIT)
                && shouldEnterPortrait) {
            shouldEnterPortrait = false;
            setOrientation(SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    /**
     * Check if the device's rotation is enabled
     *
     * @param contentResolver from the app's context
     * @return true or false according to whether the rotation is enabled or disabled
     */
    private boolean isRotationEnabled(ContentResolver contentResolver) {
        return Settings.System.getInt(contentResolver, Settings.System.ACCELEROMETER_ROTATION,
                0) == 1;
    }

    public boolean isLandscape() {
        return isLandscape;
    }


    public interface VideoFullViewCallback {
        void onFullScaleChange();

    }

    public void setVideoViewCallback(VideoFullViewCallback callback) {
        this.videoViewCallback = callback;
    }


}
