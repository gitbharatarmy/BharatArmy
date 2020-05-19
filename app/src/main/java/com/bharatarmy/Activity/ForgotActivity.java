package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityForgotBinding;

import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class ForgotActivity extends AppCompatActivity {

    ActivityForgotBinding activityForgotBinding;
    Context mContext;
    String emailStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityForgotBinding = DataBindingUtil.setContentView(this, R.layout.activity_forgot);

        mContext = ForgotActivity.this;

        init();
        setListiner();
    }

    public void init() {
        setmarginofservererrorTxtview();
    }
    public void setmarginofservererrorTxtview(){
        if (activityForgotBinding.serverErrorTxt.isShown()){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 0);
            activityForgotBinding.forgotSubmitBtn.setLayoutParams(params);
        }else{
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, getResources().getDimensionPixelOffset(R.dimen.material_margin_top), 0, 0);
            activityForgotBinding.forgotSubmitBtn.setLayoutParams(params);
        }
    }
    public void setListiner() {
        activityForgotBinding.forgotPasswordemailEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.scrollScreen(activityForgotBinding.forgotScrollView);
            }
        });

        activityForgotBinding.forgotPasswordemailEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    setmarginofservererrorTxtview();
                    activityForgotBinding.forgotemailErrorTxt.setVisibility(View.GONE);
                } else if (s.toString().equalsIgnoreCase("")) {
                    setmarginofservererrorTxtview();
                    activityForgotBinding.forgotemailErrorTxt.setVisibility(View.GONE);
                }
            }
        });

        activityForgotBinding.forgotSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Utils.handleClickEvent(mContext,activityForgotBinding.submitBtn);
                emailStr = activityForgotBinding.forgotPasswordemailEdt.getText().toString();
                if (!emailStr.equalsIgnoreCase("") && Utils.isValidEmailId(emailStr)) {
                    getForgotPassword();
                } else {
                    activityForgotBinding.forgotemailErrorTxt.setVisibility(View.VISIBLE);
                    activityForgotBinding.forgotemailErrorTxt.setText(getResources().getString(R.string.forgot_password_email_error));
                }


            }
        });

        activityForgotBinding.forgotPasswordemailEdt.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    emailStr = activityForgotBinding.forgotPasswordemailEdt.getText().toString();
                    if (!emailStr.equalsIgnoreCase("") && Utils.isValidEmailId(emailStr)) {
                        getForgotPassword();
                    } else {
                        activityForgotBinding.forgotemailErrorTxt.setVisibility(View.VISIBLE);
                        activityForgotBinding.forgotemailErrorTxt.setText(getResources().getString(R.string.forgot_password_email_error));
                    }
                }
                return false;
            }
        });
        activityForgotBinding.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whereToBack();
            }
        });
    }

    @Override
    public void onBackPressed() {
        whereToBack();
        super.onBackPressed();
    }


    public void whereToBack() {
//        Intent iLogin = new Intent(mContext, LoginwithEmailActivity.class);
//        iLogin.putExtra("whereTocomeLogin",getIntent().getStringExtra("whereTocomeLogin"));
//        startActivity(iLogin);
        finish();
    }

    public void getForgotPassword() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), ForgotActivity.this);
            return;
        }

        Utils.showDialog(mContext);
        ApiHandler.getApiService().getForgotPassword(getForgotData(), new retrofit.Callback<LogginModel>() {
            @Override
            public void success(LogginModel forgotModel, Response response) {
                Utils.dismissDialog();

                if (forgotModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (forgotModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (forgotModel.getIsValid() == 0) {
                    setmarginofservererrorTxtview();
                    return;
                }
                if (forgotModel.getIsValid() == 1) {
                    if (forgotModel.getData() != null) {
                        Intent otpIntent = new Intent(mContext, ForgotPasswordOtpActivity.class);
                        otpIntent.putExtra("Forgototp", forgotModel.getData().getOTP());
                        otpIntent.putExtra("MemberId", forgotModel.getData().getId());
//                        otpIntent.putExtra( "whereTocomeLogin",getIntent().getStringExtra("whereTocomeLogin"));
                        startActivity(otpIntent);
//                        finish();
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

    private Map<String, String> getForgotData() {
        Map<String, String> map = new HashMap<>();
        map.put("EmailId", emailStr);
        map.put("MemberId", String.valueOf(Utils.getAppUserId(mContext)));

        return map;
    }
}
