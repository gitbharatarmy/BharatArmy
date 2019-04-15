package com.bharatarmy.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import com.bharatarmy.R;

public class DemoActivity extends AppCompatActivity {


    // Pinch zoom will occurred on this image widget.
    private ImageView imageView = null;

    // Used to detect pinch zoom gesture.
    private ScaleGestureDetector scaleGestureDetector = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);


    }


}
