package com.bharatarmy.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bharatarmy.R;



public abstract class BaseActivity extends AppCompatActivity {
    TextView titleText,text_font;
    LinearLayout BackBtn;
    ImageView shareBtn;

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

    public void setShareBtn(final Activity activity, final String url,final String whereTocome) {
        if(shareBtn==null)
            shareBtn=findViewById(R.id.share_img);
        if (shareBtn!=null)
            if (whereTocome.equalsIgnoreCase("storylistadp")) {
                shareBtn.setVisibility(View.VISIBLE);
                shareBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //share image from other application
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "shared from Bharat Army");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, url);
                        shareIntent.setType("text/plain");
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(Intent.createChooser(shareIntent, "Share It"));
                    }
                });
            }
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
