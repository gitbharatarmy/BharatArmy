package com.bharatarmy;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityDemoBinding;
import com.bumptech.glide.Glide;

public class DemoActivity extends AppCompatActivity {
  ActivityDemoBinding demoBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        demoBinding = DataBindingUtil.setContentView(this, R.layout.activity_demo);


//        Animation a = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
//        demoBinding.image.startAnimation(a);
//        float mAngleToRotate = 360f * 12; // rotate 12 rounds
//        RotateAnimation wheelRotation = new RotateAnimation(0.0f, mAngleToRotate,
//                demoBinding.ivWheel.getWidth()/2.0f, demoBinding.ivWheel.getHeight()/2.0f);
//        wheelRotation.setDuration(3000); // rotate 12 rounds in 3 seconds
//        wheelRotation.setInterpolator(this, android.R.interpolator.accelerate_decelerate);
//        demoBinding.ivWheel.startAnimation(wheelRotation);
//
//        wheelRotation.setAnimationListener(new Animation.AnimationListener() {
//            public void onAnimationEnd(Animation animation) {
//                Log.d("RotationActivity", "Roation started...");
//            }
//
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//
//            public void onAnimationStart(Animation animation) {
//                Log.d("RotationActivity", "Roation ended...");
//            }
//        });
    }
}
