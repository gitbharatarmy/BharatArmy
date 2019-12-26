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

import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityProfileChangePasswordOtpBinding;

public class ProfileChangePasswordOtpActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityProfileChangePasswordOtpBinding activityProfileChangePasswordOtpBinding;
    Context mContext;
String userEnterOtpStr,accessOtpStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    activityProfileChangePasswordOtpBinding= DataBindingUtil.setContentView(this,R.layout.activity_profile_change_password_otp);
    mContext=ProfileChangePasswordOtpActivity.this;
    
    init();
    setListiner();
    
    }
    
    public void init(){
        accessOtpStr=getIntent().getStringExtra("otpStr");
    }
    
    public void setListiner(){

        activityProfileChangePasswordOtpBinding.edit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength1 = activityProfileChangePasswordOtpBinding.edit1.getText().length();
                activityProfileChangePasswordOtpBinding.edit1.setBackgroundResource(R.drawable.rectangle_line);
                if (textlength1 == 1) {
                    activityProfileChangePasswordOtpBinding.edit1.setBackgroundResource(R.drawable.fill_rectangle_line);
                    activityProfileChangePasswordOtpBinding.edit2.requestFocus();
                }
                /*else {
                    activityProfileChangePasswordOtpBinding.edit1.requestFocus();

                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        activityProfileChangePasswordOtpBinding.edit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength1 = activityProfileChangePasswordOtpBinding.edit2.getText().length();
                activityProfileChangePasswordOtpBinding.edit2.setBackgroundResource(R.drawable.rectangle_line);
                if (textlength1 == 1) {
                    activityProfileChangePasswordOtpBinding.edit2.setBackgroundResource(R.drawable.fill_rectangle_line);
                    activityProfileChangePasswordOtpBinding.edit3.requestFocus();
                }
                /*else {
                    activityProfileChangePasswordOtpBinding.edit1.requestFocus();
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        activityProfileChangePasswordOtpBinding.edit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength1 = activityProfileChangePasswordOtpBinding.edit3.getText().length();
                activityProfileChangePasswordOtpBinding.edit3.setBackgroundResource(R.drawable.rectangle_line);
                if (textlength1 == 1) {
                    activityProfileChangePasswordOtpBinding.edit3.setBackgroundResource(R.drawable.fill_rectangle_line);
                    activityProfileChangePasswordOtpBinding.edit4.requestFocus();
                }
                /*else {
                    activityProfileChangePasswordOtpBinding.edit2.requestFocus();
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        activityProfileChangePasswordOtpBinding.edit4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength1 = activityProfileChangePasswordOtpBinding.edit4.getText().length();
                activityProfileChangePasswordOtpBinding.edit4.setBackgroundResource(R.drawable.fill_rectangle_line);
                if (textlength1 == 0) {
                    activityProfileChangePasswordOtpBinding.edit4.setBackgroundResource(R.drawable.rectangle_line);
                    activityProfileChangePasswordOtpBinding.edit3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        activityProfileChangePasswordOtpBinding.edit2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    activityProfileChangePasswordOtpBinding.edit1.requestFocus();
                }
                return false;
            }
        });
        activityProfileChangePasswordOtpBinding.edit3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    activityProfileChangePasswordOtpBinding.edit2.requestFocus();
                }
                return false;
            }
        });
        activityProfileChangePasswordOtpBinding.edit4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    activityProfileChangePasswordOtpBinding.edit3.requestFocus();
                }
                return false;
            }
        });
        activityProfileChangePasswordOtpBinding.forgotPasswordotpImg.setOnClickListener(this);
        activityProfileChangePasswordOtpBinding.backImg.setOnClickListener(this);
        activityProfileChangePasswordOtpBinding.edit4.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    changePasswordOtpData();
                }
                return false;
            }
        });
    }

    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forgot_passwordotp_img:
                Utils.handleClickEvent(mContext,activityProfileChangePasswordOtpBinding.forgotPasswordotpImg);
                changePasswordOtpData();
                break;
            case R.id.back_img:
                whereToBack();
                break;
        }
    }


    public void whereToBack(){
        Intent iLogin = new Intent(mContext, ProfileChangePasswordAlertActivity.class);
        startActivity(iLogin);
        finish();
    }
    
    public void changePasswordOtpData() {

        userEnterOtpStr = activityProfileChangePasswordOtpBinding.edit1.getText().toString() +
                activityProfileChangePasswordOtpBinding.edit2.getText().toString() +
                activityProfileChangePasswordOtpBinding.edit3.getText().toString() +
                activityProfileChangePasswordOtpBinding.edit4.getText().toString();
        Log.d("userEnterOtpStr", userEnterOtpStr);

        if (!userEnterOtpStr.equalsIgnoreCase("")) {
            if (userEnterOtpStr.equalsIgnoreCase(accessOtpStr)) {
                Intent changePasswordIntent = new Intent(mContext, ProfileChangePasswordActivity.class);
                startActivity(changePasswordIntent);
                finish();
            } else {
                Utils.ping(mContext, "Please enter valid otp");
            }
        } else {
            Utils.ping(mContext, "Please enter otp");
        }

    }
    
    @Override
    public void onBackPressed() {
        whereToBack();
        super.onBackPressed();
    }
}
