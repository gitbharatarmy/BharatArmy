package com.bharatarmy.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bharatarmy.R;


public abstract class BaseActivity extends AppCompatActivity {
    TextView titleText;
    ImageView BackBtn,shareBtn;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        activityBaseBinding= DataBindingUtil.setContentView(this,R.layout.activity_base);
    }

    public void setTitleText(String title) {
        if (titleText == null)
            titleText = findViewById(R.id.toolbar_title_txt);
        if (titleText != null)
            titleText.setText(title);
    }

    public void setBackButton(final Activity activity) {
        if (BackBtn == null)
            BackBtn = findViewById(R.id.back_img);
        if (BackBtn != null)
            BackBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.finish();
                }
            });
    }


}
