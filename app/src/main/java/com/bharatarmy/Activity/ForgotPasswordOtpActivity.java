package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.bharatarmy.Interfaces.SmsListener;
import com.bharatarmy.R;
import com.bharatarmy.Utility.SmsReceiver;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityForgotPasswordOtpBinding;

public class ForgotPasswordOtpActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityForgotPasswordOtpBinding activityForgotPasswordOtpBinding;
    Context mContext;
    String otpStr, finalgetOtpStr, memberIdStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityForgotPasswordOtpBinding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password_otp);

        mContext = ForgotPasswordOtpActivity.this;
        init();
        setListiner();
    }

    public void init() {
        otpStr = getIntent().getStringExtra("Forgototp");
        memberIdStr = String.valueOf(getIntent().getIntExtra("MemberId",0));
    }


    public void setListiner() {
        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
//                ed.setText(messageText);
            }
        });

        activityForgotPasswordOtpBinding.edit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength1 = activityForgotPasswordOtpBinding.edit1.getText().length();
                activityForgotPasswordOtpBinding.edit1.setBackgroundResource(R.drawable.rectangle_line);
                if (textlength1 >= 1) {
                    activityForgotPasswordOtpBinding.edit1.setBackgroundResource(R.drawable.fill_rectangle_line);
                    activityForgotPasswordOtpBinding.edit2.requestFocus();
                } else {
                    activityForgotPasswordOtpBinding.edit1.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        activityForgotPasswordOtpBinding.edit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength1 = activityForgotPasswordOtpBinding.edit2.getText().length();
                activityForgotPasswordOtpBinding.edit2.setBackgroundResource(R.drawable.rectangle_line);
                if (textlength1 == 1) {
                    activityForgotPasswordOtpBinding.edit2.setBackgroundResource(R.drawable.fill_rectangle_line);
                    activityForgotPasswordOtpBinding.edit3.requestFocus();
                } else {
                    activityForgotPasswordOtpBinding.edit1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        activityForgotPasswordOtpBinding.edit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength1 = activityForgotPasswordOtpBinding.edit3.getText().length();
                activityForgotPasswordOtpBinding.edit3.setBackgroundResource(R.drawable.rectangle_line);
                if (textlength1 == 1) {
                    activityForgotPasswordOtpBinding.edit3.setBackgroundResource(R.drawable.fill_rectangle_line);
                    activityForgotPasswordOtpBinding.edit4.requestFocus();
                } else {
                    activityForgotPasswordOtpBinding.edit2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        activityForgotPasswordOtpBinding.edit4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength1 = activityForgotPasswordOtpBinding.edit4.getText().length();
                activityForgotPasswordOtpBinding.edit4.setBackgroundResource(R.drawable.fill_rectangle_line);
                if (textlength1 == 0) {
                    activityForgotPasswordOtpBinding.edit4.setBackgroundResource(R.drawable.rectangle_line);
                    activityForgotPasswordOtpBinding.edit3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        activityForgotPasswordOtpBinding.forgotPasswordotpImg.setOnClickListener(this);
        activityForgotPasswordOtpBinding.backLinear.setOnClickListener(this);
        activityForgotPasswordOtpBinding.edit4.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    getForgotPasswordOtpData();
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forgot_passwordotp_img:
                Utils.handleClickEvent(mContext,activityForgotPasswordOtpBinding.forgotPasswordotpImg);
                getForgotPasswordOtpData();
                break;
            case R.id.back_linear:
                finish();
                break;
        }
    }

    public void getForgotPasswordOtpData() {

        finalgetOtpStr = activityForgotPasswordOtpBinding.edit1.getText().toString() +
                activityForgotPasswordOtpBinding.edit2.getText().toString() +
                activityForgotPasswordOtpBinding.edit3.getText().toString() +
                activityForgotPasswordOtpBinding.edit4.getText().toString();
        Log.d("finalOtpStr", finalgetOtpStr);

        if (!finalgetOtpStr.equalsIgnoreCase("")) {
            if (finalgetOtpStr.equalsIgnoreCase(otpStr)) {
                Intent changePasswordIntent = new Intent(mContext, ChangePasswordActivity.class);
                changePasswordIntent.putExtra("memberId",memberIdStr);
                startActivity(changePasswordIntent);
            } else {
                Utils.ping(mContext, "Please enter valid otp");
            }
        } else {
            Utils.ping(mContext, "Please enter otp");
        }

    }


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
