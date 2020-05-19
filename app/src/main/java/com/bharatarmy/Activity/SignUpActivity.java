package com.bharatarmy.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bharatarmy.Country;
import com.bharatarmy.CountryCodePicker;
import com.bharatarmy.Models.LogginModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.appenum.PasswordStrength;
import com.bharatarmy.databinding.ActivitySignUpBinding;

import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    ActivitySignUpBinding activitySignUpBinding;
    Context mContext;
    String strFirstName, strLastName, strEmail, strCountrycode, strMobileno, strPassword, strCheck = "0",
            strbckFirstName, strbckLastName, strbckEmail, strbckCountrycode, strbckMobileno, strbckPassword, strbckCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

        mContext = SignUpActivity.this;
        init();
        setListiner();
    }

    public void init() {
        if (AppConfiguration.currentCountryISOCode != null) {
            if (!AppConfiguration.currentCountryISOCode.equalsIgnoreCase("")) {
                activitySignUpBinding.ccp.setCountryForNameCode(AppConfiguration.currentCountryISOCode);
            }
        }

        if (getIntent().getStringExtra("wheretocome") != null) {
            if (getIntent().getStringExtra("wheretocome").equalsIgnoreCase("OTP")) {
                strbckFirstName = getIntent().getStringExtra("signupFirstname");
                strbckLastName = getIntent().getStringExtra("signupLastname");
                strbckEmail = getIntent().getStringExtra("signupEmail");
                strbckCountrycode = getIntent().getStringExtra("signupCountryCode");
                strbckMobileno = getIntent().getStringExtra("signupMobileno");
                strbckPassword = getIntent().getStringExtra("signupPassword");
                strbckCheck = getIntent().getStringExtra("signupCheck");


                activitySignUpBinding.firstNameEdt.setText(strbckFirstName);
                activitySignUpBinding.secondNameEdt.setText(strbckLastName);
                activitySignUpBinding.emailEdt.setText(strbckEmail);
                activitySignUpBinding.ccp.setCountryForNameCode(strbckCountrycode);
                activitySignUpBinding.mobileEdt.setText(strbckMobileno);
                activitySignUpBinding.userPasswordEdt.setText(strbckPassword);

                if (strbckCheck.equalsIgnoreCase("1")) {
                    strCheck = "1";
                    activitySignUpBinding.termsChk.setChecked(true);
                }
            }
        }

        setmarginofservererrorTxtview();
    }

    public void setmarginofservererrorTxtview(){
        if (activitySignUpBinding.serverErrorTxt.isShown()){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 0);
            activitySignUpBinding.signupBtn.setLayoutParams(params);
        }else{
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, getResources().getDimensionPixelOffset(R.dimen.material_margin_top), 0, 0);
            activitySignUpBinding.signupBtn.setLayoutParams(params);
        }
    }

    // set the All Listiner and Data
    public void setListiner() {
        activitySignUpBinding.termConditionTxt.setOnClickListener(this);
        activitySignUpBinding.signupBtn.setOnClickListener(this);
        activitySignUpBinding.closeTxt.setOnClickListener(this);


        activitySignUpBinding.firstNameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    activitySignUpBinding.firstnameErrorTxt.setVisibility(View.GONE);
                    setmarginofservererrorTxtview();
                } else if (s.toString().equalsIgnoreCase("")) {
                    activitySignUpBinding.firstnameErrorTxt.setVisibility(View.GONE);
                    setmarginofservererrorTxtview();
                }
            }
        });

        activitySignUpBinding.secondNameEdt.addTextChangedListener(new TextWatcher() {
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
                    activitySignUpBinding.secondnameErrorTxt.setVisibility(View.GONE);
                } else if (s.toString().equalsIgnoreCase("")) {
                    setmarginofservererrorTxtview();
                    activitySignUpBinding.secondnameErrorTxt.setVisibility(View.GONE);
                }
            }
        });

        activitySignUpBinding.emailEdt.addTextChangedListener(new TextWatcher() {
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
                    activitySignUpBinding.emailErrorTxt.setVisibility(View.GONE);
                } else if (s.toString().equalsIgnoreCase("")) {
                    setmarginofservererrorTxtview();
                    activitySignUpBinding.emailErrorTxt.setVisibility(View.GONE);
                }
            }
        });

        activitySignUpBinding.mobileEdt.addTextChangedListener(new TextWatcher() {
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
                    activitySignUpBinding.phoneNumberErrorTxt.setVisibility(View.GONE);
                } else if (s.toString().equalsIgnoreCase("")) {
                    setmarginofservererrorTxtview();
                    activitySignUpBinding.phoneNumberErrorTxt.setVisibility(View.GONE);
                }
            }
        });

        activitySignUpBinding.userPasswordEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculatePasswordStrength(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    activitySignUpBinding.passwordErrorTxt.setVisibility(View.GONE);
                    setmarginofservererrorTxtview();
                    activitySignUpBinding.passwordStrengthLinear.setVisibility(View.VISIBLE);
                } else if (s.toString().equalsIgnoreCase("")) {
                    activitySignUpBinding.passwordStrengthLinear.setVisibility(View.GONE);
                    activitySignUpBinding.passwordStrengthTxtview1.setBackgroundColor(getResources().getColor(R.color.gray));
                    activitySignUpBinding.uppercaseImageRel.setVisibility(View.GONE);
                    activitySignUpBinding.digitImageRel.setVisibility(View.GONE);
                    activitySignUpBinding.minimumCharImageRel.setVisibility(View.GONE);
                    activitySignUpBinding.specialCharImageRel.setVisibility(View.GONE);
                    setmarginofservererrorTxtview();
                }
            }
        });

        activitySignUpBinding.ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                AppConfiguration.currentCountryISOCode = activitySignUpBinding.ccp.getSelectedCountryNameCode();
            }
        });

        activitySignUpBinding.termsChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    strCheck = "1";
                } else {
                    strCheck = "0";
                }
            }
        });

        activitySignUpBinding.userPasswordEdt.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    Utils.hideKeyboard(SignUpActivity.this);
                }
                return false;
            }
        });
    }

    // get the data user fill for singup
    public void getDataValue() {
        strFirstName = activitySignUpBinding.firstNameEdt.getText().toString();
        strLastName = activitySignUpBinding.secondNameEdt.getText().toString();
        strEmail = activitySignUpBinding.emailEdt.getText().toString();
        strCountrycode = activitySignUpBinding.ccp.getSelectedCountryCode();
        strMobileno = activitySignUpBinding.mobileEdt.getText().toString();
        strPassword = activitySignUpBinding.userPasswordEdt.getText().toString();
        AppConfiguration.currentCountryISOCode = activitySignUpBinding.ccp.getSelectedCountryNameCode();

        Log.d("selectedcode", strCountrycode);

        if (!strFirstName.equalsIgnoreCase("")) {
            if (!strLastName.equalsIgnoreCase("")) {
                if (!strEmail.equalsIgnoreCase("")) {
                    if (Utils.isValidEmailId(strEmail)) {
                        if (strCountrycode.length() > 0) {
                            if (strMobileno.length() > 0) {
                                if (Utils.isValidPhoneNumber(strMobileno)) {
//                                    boolean status = Utils.validateUsing_libphonenumber(mContext, strCountrycode, strMobileno);
//                                if (status) {
                                    if (!strPassword.equalsIgnoreCase("")) {
                                        if (PasswordStrength.uppercase.equalsIgnoreCase("yes") &&
                                                PasswordStrength.digitcase.equalsIgnoreCase("yes") &&
                                                PasswordStrength.minimumcharcase.equalsIgnoreCase("yes") &&
                                                PasswordStrength.specialcharcase.equalsIgnoreCase("yes")) {
                                            if (!strCheck.equalsIgnoreCase("0")) {
                                                getOtpVerification();
                                            } else {
                                                Utils.ping(mContext, getResources().getString(R.string.signup_privacy_error));
                                            }

                                        } else {
                                            activitySignUpBinding.passwordErrorTxt.setVisibility(View.VISIBLE);
                                            activitySignUpBinding.passwordErrorTxt.setText(getResources().getString(R.string.singup_password_error));
                                        }
                                    } else {
                                        activitySignUpBinding.passwordErrorTxt.setVisibility(View.VISIBLE);
                                        activitySignUpBinding.passwordErrorTxt.setText(getResources().getString(R.string.signup_blankpassword_error));
                                    }
//                                } else {
//                                    activitySignUpBinding.mobileEdt.setError("Invalid Phone Number");
//                                }
                                } else {
                                    activitySignUpBinding.phoneNumberErrorTxt.setVisibility(View.VISIBLE);
                                    activitySignUpBinding.phoneNumberErrorTxt.setText(getResources().getString(R.string.signup_phone_number_error));
                                }
                            } else {
                                activitySignUpBinding.phoneNumberErrorTxt.setVisibility(View.VISIBLE);
                                activitySignUpBinding.phoneNumberErrorTxt.setText(getResources().getString(R.string.signup_blankphone_number_error));
                            }
                        } else {
                            Utils.ping(mContext, getResources().getString(R.string.signup_county_code_error));
                        }
                    } else {
                        activitySignUpBinding.emailErrorTxt.setVisibility(View.VISIBLE);
                        activitySignUpBinding.emailErrorTxt.setText(getResources().getString(R.string.signup_email_error));
                    }
                } else {
                    activitySignUpBinding.emailErrorTxt.setVisibility(View.VISIBLE);
                    activitySignUpBinding.emailErrorTxt.setText(getResources().getString(R.string.signup_blankemail_error));
                }
            } else {
                activitySignUpBinding.secondnameErrorTxt.setVisibility(View.VISIBLE);
                activitySignUpBinding.secondnameErrorTxt.setText(getResources().getString(R.string.signup_secondname_error));
            }
        } else {
            activitySignUpBinding.firstnameErrorTxt.setVisibility(View.VISIBLE);
            activitySignUpBinding.firstnameErrorTxt.setText(getResources().getString(R.string.signup_firstname_error));
        }


    }

    @Override

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.term_condition_txt:
//                Utils.handleClickEvent(mContext, activitySignUpBinding.termConditionTxt);
                Intent privacypolicyIntent = new Intent(mContext, MoreInformationActivity.class);
                privacypolicyIntent.putExtra("Story Heading", "Privacy Policy");
                privacypolicyIntent.putExtra("StroyUrl", AppConfiguration.TERMSURL);
                privacypolicyIntent.putExtra("whereTocome", "aboutus");
                privacypolicyIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(privacypolicyIntent);
                break;
            case R.id.signup_btn:
//                Utils.handleClickEvent(mContext, activitySignUpBinding.signupBtn);
                getDataValue();
                break;
            case R.id.close_txt:
                whereToBack();
                break;

        }
    }

    private void calculatePasswordStrength(String str) {
        // Now, we need to define a PasswordStrength enum
        // with a calculate static method returning the password strength
        PasswordStrength passwordStrength = PasswordStrength.calculate(str);
        activitySignUpBinding.passwordStrengthTxtview.setText(passwordStrength.msg);
        activitySignUpBinding.passwordStrengthTxtview.setTextColor(passwordStrength.color);
        if (activitySignUpBinding.passwordStrengthTxtview.getText().toString().equalsIgnoreCase("WEAK")) {
            activitySignUpBinding.passwordStrengthTxtview2.setBackgroundColor(getResources().getColor(R.color.gray));
            activitySignUpBinding.passwordStrengthTxtview3.setBackgroundColor(getResources().getColor(R.color.gray));
            activitySignUpBinding.passwordStrengthTxtview4.setBackgroundColor(getResources().getColor(R.color.gray));
            activitySignUpBinding.passwordStrengthTxtview1.setBackgroundColor(passwordStrength.color);
        } else if (activitySignUpBinding.passwordStrengthTxtview.getText().toString().equalsIgnoreCase("MEDIUM")) {
            activitySignUpBinding.passwordStrengthTxtview3.setBackgroundColor(getResources().getColor(R.color.gray));
            activitySignUpBinding.passwordStrengthTxtview4.setBackgroundColor(getResources().getColor(R.color.gray));
            activitySignUpBinding.passwordStrengthTxtview1.setBackgroundColor(passwordStrength.color);
            activitySignUpBinding.passwordStrengthTxtview2.setBackgroundColor(passwordStrength.color);
        } else if (activitySignUpBinding.passwordStrengthTxtview.getText().toString().equalsIgnoreCase("STRONG")) {
            activitySignUpBinding.passwordStrengthTxtview4.setBackgroundColor(getResources().getColor(R.color.gray));
            activitySignUpBinding.passwordStrengthTxtview1.setBackgroundColor(passwordStrength.color);
            activitySignUpBinding.passwordStrengthTxtview2.setBackgroundColor(passwordStrength.color);
            activitySignUpBinding.passwordStrengthTxtview3.setBackgroundColor(passwordStrength.color);
        } else if (activitySignUpBinding.passwordStrengthTxtview.getText().toString().equalsIgnoreCase("VERY STRONG")) {
            activitySignUpBinding.passwordStrengthTxtview1.setBackgroundColor(passwordStrength.color);
            activitySignUpBinding.passwordStrengthTxtview2.setBackgroundColor(passwordStrength.color);
            activitySignUpBinding.passwordStrengthTxtview3.setBackgroundColor(passwordStrength.color);
            activitySignUpBinding.passwordStrengthTxtview4.setBackgroundColor(passwordStrength.color);
        } else {
            activitySignUpBinding.passwordStrengthTxtview2.setBackgroundColor(getResources().getColor(R.color.gray));
            activitySignUpBinding.passwordStrengthTxtview3.setBackgroundColor(getResources().getColor(R.color.gray));
            activitySignUpBinding.passwordStrengthTxtview4.setBackgroundColor(getResources().getColor(R.color.gray));
            activitySignUpBinding.passwordStrengthTxtview1.setBackgroundColor(getResources().getColor(R.color.gray));
        }

        if (PasswordStrength.uppercase.equalsIgnoreCase("yes")) {
            activitySignUpBinding.uppercaseImageRel.setVisibility(View.VISIBLE);
            activitySignUpBinding.uppercaseImageRel.setBackground(getResources().getDrawable(R.drawable.ic_fill_correct_password));
            activitySignUpBinding.uppercaseDisplayTxtview.setTextColor(getResources().getColor(R.color.heading_bg));
        } else {
            activitySignUpBinding.uppercaseImageRel.setVisibility(View.VISIBLE);
            activitySignUpBinding.uppercaseImageRel.setBackground(getResources().getDrawable(R.drawable.ic_incorrect_strength_password));
            activitySignUpBinding.uppercaseDisplayTxtview.setTextColor(getResources().getColor(R.color.red));
        }

        if (PasswordStrength.digitcase.equalsIgnoreCase("yes")) {
            activitySignUpBinding.digitImageRel.setVisibility(View.VISIBLE);
            activitySignUpBinding.digitImageRel.setBackground(getResources().getDrawable(R.drawable.ic_fill_correct_password));
            activitySignUpBinding.digitDisplayTxtview.setTextColor(getResources().getColor(R.color.heading_bg));
        } else {
            activitySignUpBinding.digitImageRel.setVisibility(View.VISIBLE);
            activitySignUpBinding.digitImageRel.setBackground(getResources().getDrawable(R.drawable.ic_incorrect_strength_password));
            activitySignUpBinding.digitDisplayTxtview.setTextColor(getResources().getColor(R.color.red));
        }
        if (PasswordStrength.specialcharcase.equalsIgnoreCase("yes")) {
            activitySignUpBinding.specialCharImageRel.setVisibility(View.VISIBLE);
            activitySignUpBinding.specialCharImageRel.setBackground(getResources().getDrawable(R.drawable.ic_fill_correct_password));
            activitySignUpBinding.specialCharDisplayTxtview.setTextColor(getResources().getColor(R.color.heading_bg));
        } else {
            activitySignUpBinding.specialCharImageRel.setVisibility(View.VISIBLE);
            activitySignUpBinding.specialCharImageRel.setBackground(getResources().getDrawable(R.drawable.ic_incorrect_strength_password));
            activitySignUpBinding.specialCharDisplayTxtview.setTextColor(getResources().getColor(R.color.red));
        }
        if (PasswordStrength.minimumcharcase.equalsIgnoreCase("yes")) {
            activitySignUpBinding.minimumCharImageRel.setVisibility(View.VISIBLE);
            activitySignUpBinding.minimumCharImageRel.setBackground(getResources().getDrawable(R.drawable.ic_fill_correct_password));
            activitySignUpBinding.minimumDisplayTxtview.setTextColor(getResources().getColor(R.color.heading_bg));
        } else {
            activitySignUpBinding.minimumCharImageRel.setVisibility(View.VISIBLE);
            activitySignUpBinding.minimumCharImageRel.setBackground(getResources().getDrawable(R.drawable.ic_incorrect_strength_password));
            activitySignUpBinding.minimumDisplayTxtview.setTextColor(getResources().getColor(R.color.red));
        }
    }

    public void getOtpVerification() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), SignUpActivity.this);
            return;
        }

        Utils.showDialog(mContext);
        ApiHandler.getApiService().getValidatedBAMember(getOtpVerificationData(), new retrofit.Callback<LogginModel>() {
            @Override
            public void success(LogginModel loginModel, Response response) {
                Utils.dismissDialog();
                if (loginModel == null) {
                    Utils.dismissDialog();
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (loginModel.getIsValid() == null) {
                    Utils.dismissDialog();
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (loginModel.getIsValid() == 0) {
                    Utils.dismissDialog();
                    setmarginofservererrorTxtview();
                    return;
                }
                if (loginModel.getIsValid() == 1) {
                    Intent otpIntent = new Intent(mContext, OTPActivity.class);
                    otpIntent.putExtra("OTP", loginModel.getData().getOTP());
                    otpIntent.putExtra("OTPmobileno", strMobileno);
                    otpIntent.putExtra("countrycode", strCountrycode);
                    otpIntent.putExtra("wheretocome", "Signup");
                    otpIntent.putExtra("signupFirstname", strFirstName);
                    otpIntent.putExtra("signupLastname", strLastName);
                    otpIntent.putExtra("signupEmail", strEmail);
                    otpIntent.putExtra("signupCountryCode", strCountrycode);
                    otpIntent.putExtra("signupMobileno", strMobileno);
                    otpIntent.putExtra("signupPassword", strPassword);
                    otpIntent.putExtra("signupCheck", strCheck);
//                    otpIntent.putExtra("whereTocomeLogin", getIntent().getStringExtra("whereTocomeLogin"));
                    startActivity(otpIntent);
//                    finish();
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

    private Map<String, String> getOtpVerificationData() {
        Map<String, String> map = new HashMap<>();
        map.put("AppUserId", String.valueOf(Utils.getAppUserId(mContext)));
        map.put("Email", strEmail);
        map.put("PhoneNo", strMobileno);
        map.put("CountryPhoneNo", strCountrycode);

        return map;
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
}

