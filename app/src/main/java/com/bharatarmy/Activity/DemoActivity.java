package com.bharatarmy.Activity;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import com.bharatarmy.R;

public class DemoActivity extends AppCompatActivity {

ImageSwitcher imageSwitcher;
    private int animationCounter = 1;
    private Handler imageSwitcherHandler;
    // Pinch zoom will occurred on this image widget.
    private ImageView imageView = null;

    // Used to detect pinch zoom gesture.
    private ScaleGestureDetector scaleGestureDetector = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        imageSwitcher=(ImageSwitcher)findViewById(R.id.imageSwitcher);

        Animation in  = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        imageSwitcher.setInAnimation(in);
        imageSwitcher.setOutAnimation(out);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getApplicationContext());
                myView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                myView.setLayoutParams(new
                        ImageSwitcher.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                return myView;
            }
        });
        imageSwitcherHandler = new Handler(Looper.getMainLooper());
        imageSwitcherHandler.post(new Runnable() {
            @Override
            public void run() {
                switch (animationCounter++) {
                    case 1:

                        imageSwitcher.setImageResource(R.drawable.ad);
                        break;
                    case 2:
                        imageSwitcher.setImageResource(R.drawable.ad2);
                        break;

                }
                animationCounter %= 4;
                if(animationCounter == 0 ) animationCounter = 1;

                imageSwitcherHandler.postDelayed(this, 3000);
            }
        });

    }


}
