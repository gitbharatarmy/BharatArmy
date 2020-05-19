package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;

import com.bharatarmy.R;
import com.bharatarmy.databinding.ActivityDemoBinding;

public class DemoActivity extends AppCompatActivity {
    ActivityDemoBinding activityDemoBinding;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDemoBinding = DataBindingUtil.setContentView(this, R.layout.activity_demo);
        mContext = DemoActivity.this;

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) activityDemoBinding.progressVoteImg.getLayoutParams();
        params.horizontalBias = 0.4f; // here is one modification for example. modify anything else you want :)
        activityDemoBinding.progressVoteImg.setLayoutParams(params); // request the view to use the new modified params


        ConstraintLayout.LayoutParams params1 = (ConstraintLayout.LayoutParams) activityDemoBinding.voteAnserText.getLayoutParams();
        params.horizontalWeight = 0.4f; // here is one modification for example. modify anything else you want :)
        activityDemoBinding.voteAnserText.setLayoutParams(params1); // request the view to use the new modified params

        activityDemoBinding.progressBar.setProgress(40);
    }
}
