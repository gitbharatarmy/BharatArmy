package com.bharatarmy.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bharatarmy.R;



public abstract class BaseActivity extends AppCompatActivity {
    TextView titleText;
    LinearLayout BackBtn;
    ImageView shareBtn;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
    @Override
    protected void onRestoreInstanceState(Bundle in) {
        super.onRestoreInstanceState(in);

    }
}
