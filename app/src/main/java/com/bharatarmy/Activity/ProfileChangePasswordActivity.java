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
import com.bharatarmy.appenum.PasswordStrength;
import com.bharatarmy.databinding.ActivityProfileChangePasswordBinding;

import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class ProfileChangePasswordActivity extends AppCompatActivity {

    ActivityProfileChangePasswordBinding activityProfileChangePasswordBinding;
    Context mContext;
    String newPasswordStr, confirmPasswordStr, memberIdStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileChangePasswordBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile_change_password);

        mContext = ProfileChangePasswordActivity.this;

        init();
        setListiner();
    }

    public void init() {
       memberIdStr= String.valueOf(Utils.getAppUserId(mContext));
        setmarginofservererrorTxtview();
    }
    public void setmarginofservererrorTxtview() {
        if (activityProfileChangePasswordBinding.serverErrorTxt.isShown()) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 0);
            activityProfileChangePasswordBinding.profileChangePassSubmitBtn.setLayoutParams(params);
        } else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, getResources().getDimensionPixelOffset(R.dimen.material_margin_top), 0, 0);
            activityProfileChangePasswordBinding.profileChangePassSubmitBtn.setLayoutParams(params);
        }
    }

    public void setListiner() {
        activityProfileChangePasswordBinding.newpasswordEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityProfileChangePasswordBinding.changepasswordScrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        View lastChild = activityProfileChangePasswordBinding.changepasswordScrollView.getChildAt(activityProfileChangePasswordBinding.changepasswordScrollView.getChildCount() - 1);
                        int bottom = lastChild.getBottom() + activityProfileChangePasswordBinding.changepasswordScrollView.getPaddingBottom();
                        int sy = activityProfileChangePasswordBinding.changepasswordScrollView.getScrollY();
                        int sh = activityProfileChangePasswordBinding.changepasswordScrollView.getHeight();
                        int delta = bottom - (sy + sh);
                        activityProfileChangePasswordBinding.changepasswordScrollView.smoothScrollBy(0, delta);
                    }
                }, 200);
            }
        });

        activityProfileChangePasswordBinding.profileChangePassSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Utils.handleClickEvent(mContext, activityProfileChangePasswordBinding.submitBtn);
                getChangePasswordFillData();
            }
        });
        activityProfileChangePasswordBinding.confirmpasswordEdt.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    getChangePasswordFillData();
                }
                return false;
            }
        });
        activityProfileChangePasswordBinding.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wheretoBack();
            }
        });
        activityProfileChangePasswordBinding.newpasswordEdt.addTextChangedListener(new TextWatcher() {
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
                    setmarginofservererrorTxtview();
                    activityProfileChangePasswordBinding.newpasswordErrorTxt.setVisibility(View.GONE);
                    activityProfileChangePasswordBinding.passwordStrengthLinear.setVisibility(View.VISIBLE);
                } else if (s.toString().equalsIgnoreCase("")) {
                    setmarginofservererrorTxtview();
                    activityProfileChangePasswordBinding.passwordStrengthLinear.setVisibility(View.GONE);
                    activityProfileChangePasswordBinding.passwordStrengthTxtview1.setBackgroundColor(getResources().getColor(R.color.gray));
                    activityProfileChangePasswordBinding.uppercaseImageRel.setVisibility(View.GONE);
                    activityProfileChangePasswordBinding.digitImageRel.setVisibility(View.GONE);
                    activityProfileChangePasswordBinding.minimumCharImageRel.setVisibility(View.GONE);
                    activityProfileChangePasswordBinding.specialCharImageRel.setVisibility(View.GONE);
                }
            }
        });

        activityProfileChangePasswordBinding.confirmpasswordEdt.addTextChangedListener(new TextWatcher() {
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
                    activityProfileChangePasswordBinding.confirmpasswordErrorTxt.setVisibility(View.GONE);
                } else if (s.toString().equalsIgnoreCase("")) {
                    setmarginofservererrorTxtview();
                    activityProfileChangePasswordBinding.confirmpasswordErrorTxt.setVisibility(View.GONE);
                }
            }
        });
    }

    private void calculatePasswordStrength(String str) {
        // Now, we need to define a PasswordStrength enum
        // with a calculate static method returning the password strength
        PasswordStrength passwordStrength = PasswordStrength.calculate(str);
        activityProfileChangePasswordBinding.passwordStrengthTxtview.setText(passwordStrength.msg);
        activityProfileChangePasswordBinding.passwordStrengthTxtview.setTextColor(passwordStrength.color);
        if (activityProfileChangePasswordBinding.passwordStrengthTxtview.getText().toString().equalsIgnoreCase("WEAK")) {
            activityProfileChangePasswordBinding.passwordStrengthTxtview2.setBackgroundColor(getResources().getColor(R.color.gray));
            activityProfileChangePasswordBinding.passwordStrengthTxtview3.setBackgroundColor(getResources().getColor(R.color.gray));
            activityProfileChangePasswordBinding.passwordStrengthTxtview4.setBackgroundColor(getResources().getColor(R.color.gray));
            activityProfileChangePasswordBinding.passwordStrengthTxtview1.setBackgroundColor(passwordStrength.color);
        } else if (activityProfileChangePasswordBinding.passwordStrengthTxtview.getText().toString().equalsIgnoreCase("MEDIUM")) {
            activityProfileChangePasswordBinding.passwordStrengthTxtview3.setBackgroundColor(getResources().getColor(R.color.gray));
            activityProfileChangePasswordBinding.passwordStrengthTxtview4.setBackgroundColor(getResources().getColor(R.color.gray));
            activityProfileChangePasswordBinding.passwordStrengthTxtview1.setBackgroundColor(passwordStrength.color);
            activityProfileChangePasswordBinding.passwordStrengthTxtview2.setBackgroundColor(passwordStrength.color);
        } else if (activityProfileChangePasswordBinding.passwordStrengthTxtview.getText().toString().equalsIgnoreCase("STRONG")) {
            activityProfileChangePasswordBinding.passwordStrengthTxtview4.setBackgroundColor(getResources().getColor(R.color.gray));
            activityProfileChangePasswordBinding.passwordStrengthTxtview1.setBackgroundColor(passwordStrength.color);
            activityProfileChangePasswordBinding.passwordStrengthTxtview2.setBackgroundColor(passwordStrength.color);
            activityProfileChangePasswordBinding.passwordStrengthTxtview3.setBackgroundColor(passwordStrength.color);
        } else if (activityProfileChangePasswordBinding.passwordStrengthTxtview.getText().toString().equalsIgnoreCase("VERY STRONG")) {
            activityProfileChangePasswordBinding.passwordStrengthTxtview1.setBackgroundColor(passwordStrength.color);
            activityProfileChangePasswordBinding.passwordStrengthTxtview2.setBackgroundColor(passwordStrength.color);
            activityProfileChangePasswordBinding.passwordStrengthTxtview3.setBackgroundColor(passwordStrength.color);
            activityProfileChangePasswordBinding.passwordStrengthTxtview4.setBackgroundColor(passwordStrength.color);
        } else {
            activityProfileChangePasswordBinding.passwordStrengthTxtview2.setBackgroundColor(getResources().getColor(R.color.gray));
            activityProfileChangePasswordBinding.passwordStrengthTxtview3.setBackgroundColor(getResources().getColor(R.color.gray));
            activityProfileChangePasswordBinding.passwordStrengthTxtview4.setBackgroundColor(getResources().getColor(R.color.gray));
            activityProfileChangePasswordBinding.passwordStrengthTxtview1.setBackgroundColor(getResources().getColor(R.color.gray));
        }

        if (PasswordStrength.uppercase.equalsIgnoreCase("yes")) {
            activityProfileChangePasswordBinding.uppercaseImageRel.setVisibility(View.VISIBLE);
            activityProfileChangePasswordBinding.uppercaseImageRel.setBackground(getResources().getDrawable(R.drawable.ic_fill_correct_password));
            activityProfileChangePasswordBinding.uppercaseDisplayTxtview.setTextColor(getResources().getColor(R.color.heading_bg));
        } else {
            activityProfileChangePasswordBinding.uppercaseImageRel.setVisibility(View.VISIBLE);
            activityProfileChangePasswordBinding.uppercaseImageRel.setBackground(getResources().getDrawable(R.drawable.ic_incorrect_strength_password));
            activityProfileChangePasswordBinding.uppercaseDisplayTxtview.setTextColor(getResources().getColor(R.color.red));
        }

        if (PasswordStrength.digitcase.equalsIgnoreCase("yes")) {
            activityProfileChangePasswordBinding.digitImageRel.setVisibility(View.VISIBLE);
            activityProfileChangePasswordBinding.digitImageRel.setBackground(getResources().getDrawable(R.drawable.ic_fill_correct_password));
            activityProfileChangePasswordBinding.digitDisplayTxtview.setTextColor(getResources().getColor(R.color.heading_bg));
        } else {
            activityProfileChangePasswordBinding.digitImageRel.setVisibility(View.VISIBLE);
            activityProfileChangePasswordBinding.digitImageRel.setBackground(getResources().getDrawable(R.drawable.ic_incorrect_strength_password));
            activityProfileChangePasswordBinding.digitDisplayTxtview.setTextColor(getResources().getColor(R.color.red));
        }
        if (PasswordStrength.specialcharcase.equalsIgnoreCase("yes")) {
            activityProfileChangePasswordBinding.specialCharImageRel.setVisibility(View.VISIBLE);
            activityProfileChangePasswordBinding.specialCharImageRel.setBackground(getResources().getDrawable(R.drawable.ic_fill_correct_password));
            activityProfileChangePasswordBinding.specialCharDisplayTxtview.setTextColor(getResources().getColor(R.color.heading_bg));
        } else {
            activityProfileChangePasswordBinding.specialCharImageRel.setVisibility(View.VISIBLE);
            activityProfileChangePasswordBinding.specialCharImageRel.setBackground(getResources().getDrawable(R.drawable.ic_incorrect_strength_password));
            activityProfileChangePasswordBinding.specialCharDisplayTxtview.setTextColor(getResources().getColor(R.color.red));
        }
        if (PasswordStrength.minimumcharcase.equalsIgnoreCase("yes")) {
            activityProfileChangePasswordBinding.minimumCharImageRel.setVisibility(View.VISIBLE);
            activityProfileChangePasswordBinding.minimumCharImageRel.setBackground(getResources().getDrawable(R.drawable.ic_fill_correct_password));
            activityProfileChangePasswordBinding.minimumDisplayTxtview.setTextColor(getResources().getColor(R.color.heading_bg));
        } else {
            activityProfileChangePasswordBinding.minimumCharImageRel.setVisibility(View.VISIBLE);
            activityProfileChangePasswordBinding.minimumCharImageRel.setBackground(getResources().getDrawable(R.drawable.ic_incorrect_strength_password));
            activityProfileChangePasswordBinding.minimumDisplayTxtview.setTextColor(getResources().getColor(R.color.red));
        }
    }
    
    @Override
    public void onBackPressed() {
        wheretoBack();
        super.onBackPressed();
    }

    public void wheretoBack(){
        Intent iLogin = new Intent(mContext, ProfileSettingActivity.class);
        startActivity(iLogin);
        finish();
    }

    public void getChangePasswordFillData() {
        newPasswordStr = activityProfileChangePasswordBinding.newpasswordEdt.getText().toString();
        confirmPasswordStr = activityProfileChangePasswordBinding.confirmpasswordEdt.getText().toString();
        if (!newPasswordStr.equalsIgnoreCase("")) {
            if (!confirmPasswordStr.equalsIgnoreCase("")) {
                if (PasswordStrength.uppercase.equalsIgnoreCase("yes") &&
                        PasswordStrength.digitcase.equalsIgnoreCase("yes") &&
                        PasswordStrength.minimumcharcase.equalsIgnoreCase("yes") &&
                        PasswordStrength.specialcharcase.equalsIgnoreCase("yes")) {
                    if (newPasswordStr.equalsIgnoreCase(confirmPasswordStr)) {
                        if (!memberIdStr.equalsIgnoreCase("")) {
                            getChangePassword();
                        } else {
                            Utils.ping(mContext, "memberId blank");
                        }
                    } else {
                        activityProfileChangePasswordBinding.confirmpasswordErrorTxt.setVisibility(View.VISIBLE);
                        activityProfileChangePasswordBinding.confirmpasswordErrorTxt.setText(getResources().getString(R.string.change_pass_error));
                    }
                } else {
                    activityProfileChangePasswordBinding.newpasswordErrorTxt.setVisibility(View.VISIBLE);
                    activityProfileChangePasswordBinding.newpasswordErrorTxt.setText(getResources().getString(R.string.singup_password_error));
                }
            } else {
                activityProfileChangePasswordBinding.confirmpasswordErrorTxt.setVisibility(View.VISIBLE);
                activityProfileChangePasswordBinding.confirmpasswordErrorTxt.setText(getResources().getString(R.string.change_confirmpassword_error));
            }
        } else {
            activityProfileChangePasswordBinding.newpasswordErrorTxt.setVisibility(View.VISIBLE);
            activityProfileChangePasswordBinding.newpasswordErrorTxt.setText(getResources().getString(R.string.change_newpassword_error));
        }
    }

    public void getChangePassword() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), ProfileChangePasswordActivity.this);
            return;
        }

        Utils.showDialog(mContext);
        ApiHandler.getApiService().getChangePassword(getChangePasswordData(), new retrofit.Callback<LogginModel>() {
            @Override
            public void success(LogginModel changeModel, Response response) {
                Utils.dismissDialog();

                if (changeModel == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (changeModel.getIsValid() == null) {
                    Utils.ping(mContext, getString(R.string.something_wrong));
                    return;
                }
                if (changeModel.getIsValid() == 0) {
                    setmarginofservererrorTxtview();
                    return;
                }
                if (changeModel.getIsValid() == 1) {
                    if (changeModel.getData() != null) {

                        Utils.setPref(mContext, "IsLoginUser", "1");
                        Utils.storeLoginData(changeModel.getData(), mContext);
                        Utils.storeCurrentLocationData(changeModel.getCurrentLocation(),mContext);
                        Utils.storeLoginOtherData(changeModel.getOtherData(), mContext);
                            Utils.showThanyouDialog(ProfileChangePasswordActivity.this, "changePassword|finishApp");
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

    private Map<String, String> getChangePasswordData() {
        Map<String, String> map = new HashMap<>();
        map.put("NewPassword", newPasswordStr);
        map.put("MemberId", memberIdStr);
        return map;
    }
}
