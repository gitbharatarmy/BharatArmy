package com.bharatarmy.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bharatarmy.Adapter.VideoDetailVerticalAdapter;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;

import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;


public abstract class BaseActivity extends AppCompatActivity {
    TextView titleText, total_like_txt, title_text_font, total_comment_txt, total_video_view_txt;
    LinearLayout BackBtn;
    ImageView shareBtn;
    String SourceTypeStr, PostIdStr;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        total_like_txt = (TextView) findViewById(R.id.total_like_txt);
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


    // Api calling callPostedViewData
    public void callPostedViewData(Context mContext, Activity activity, String SourceType, String PostId) {
        PostIdStr = PostId;
        SourceTypeStr = SourceType;

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), activity);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getBAPostStatistics(getpostedData(), new retrofit.Callback<LogginModel>() {
            @Override
            public void success(LogginModel postedDataModel, Response response) {
                Utils.dismissDialog();
                if (postedDataModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (postedDataModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (postedDataModel.getIsValid() == 0) {
                    Utils.ping(mContext, getString(R.string.false_msg));
                    return;
                }
                if (postedDataModel.getIsValid() == 1) {

                    if (postedDataModel.getData() != null) {

                        if (postedDataModel.getData().getLikes() != null) {

                            if (postedDataModel.getData().getLikes().equals(0)) {
                                total_like_txt.setText("");
                            } else {
                                total_like_txt.setText(String.valueOf(postedDataModel.getData().getLikes()));
                            }
                        }

                    }

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, getString(R.string.something_wrong));
            }
        });


    }

    private Map<String, String> getpostedData() {
        Map<String, String> map = new HashMap<>();
        map.put("MemberId", String.valueOf(Utils.getAppUserId(getApplicationContext())));
        map.put("PostId", PostIdStr);
        map.put("SourceType", SourceTypeStr);
        return map;
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
