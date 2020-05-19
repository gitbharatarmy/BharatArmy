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
import com.bharatarmy.databinding.ActivityChangePasswordBinding;

import java.util.HashMap;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    ActivityChangePasswordBinding activityChangePasswordBinding;
    Context mContext;
    String newPasswordStr, confirmPasswordStr, memberIdStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChangePasswordBinding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);

        mContext = ChangePasswordActivity.this;

        init();
        setListiner();
    }

    public void init() {
        if (getIntent().getStringExtra("memberId") != null) {
            memberIdStr = getIntent().getStringExtra("memberId");
        }
        setmarginofservererrorTxtview();
    }

    public void setmarginofservererrorTxtview() {
        if (activityChangePasswordBinding.serverErrorTxt.isShown()) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 0);
            activityChangePasswordBinding.changePasswordSubmitBtn.setLayoutParams(params);
        } else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, getResources().getDimensionPixelOffset(R.dimen.material_margin_top), 0, 0);
            activityChangePasswordBinding.changePasswordSubmitBtn.setLayoutParams(params);
        }
    }

    public void setListiner() {
        activityChangePasswordBinding.newpasswordEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityChangePasswordBinding.changepasswordScrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        View lastChild = activityChangePasswordBinding.changepasswordScrollView.getChildAt(activityChangePasswordBinding.changepasswordScrollView.getChildCount() - 1);
                        int bottom = lastChild.getBottom() + activityChangePasswordBinding.changepasswordScrollView.getPaddingBottom();
                        int sy = activityChangePasswordBinding.changepasswordScrollView.getScrollY();
                        int sh = activityChangePasswordBinding.changepasswordScrollView.getHeight();
                        int delta = bottom - (sy + sh);
                        activityChangePasswordBinding.changepasswordScrollView.smoothScrollBy(0, delta);
                    }
                }, 200);
            }
        });

        activityChangePasswordBinding.changePasswordSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Utils.handleClickEvent(mContext, activityChangePasswordBinding.submitBtn);
                getChangePasswordFillData();
            }
        });
        activityChangePasswordBinding.confirmpasswordEdt.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    getChangePasswordFillData();
                }
                return false;
            }
        });
        activityChangePasswordBinding.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wheretoBack();
            }
        });

        activityChangePasswordBinding.newpasswordEdt.addTextChangedListener(new TextWatcher() {
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
                    activityChangePasswordBinding.newpasswordErrorTxt.setVisibility(View.GONE);
                    activityChangePasswordBinding.passwordStrengthLinear.setVisibility(View.VISIBLE);
                    setmarginofservererrorTxtview();
                } else if (s.toString().equalsIgnoreCase("")) {
                    setmarginofservererrorTxtview();
                    activityChangePasswordBinding.passwordStrengthLinear.setVisibility(View.GONE);
                    activityChangePasswordBinding.passwordStrengthTxtview1.setBackgroundColor(getResources().getColor(R.color.gray));
                    activityChangePasswordBinding.uppercaseImageRel.setVisibility(View.GONE);
                    activityChangePasswordBinding.digitImageRel.setVisibility(View.GONE);
                    activityChangePasswordBinding.minimumCharImageRel.setVisibility(View.GONE);
                    activityChangePasswordBinding.specialCharImageRel.setVisibility(View.GONE);
                }
            }
        });

        activityChangePasswordBinding.confirmpasswordEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    activityChangePasswordBinding.confirmpasswordErrorTxt.setVisibility(View.GONE);
                    setmarginofservererrorTxtview();
                } else if (s.toString().equalsIgnoreCase("")) {
                    activityChangePasswordBinding.confirmpasswordErrorTxt.setVisibility(View.GONE);
                    setmarginofservererrorTxtview();
                }
            }
        });
    }

    private void calculatePasswordStrength(String str) {
        // Now, we need to define a PasswordStrength enum
        // with a calculate static method returning the password strength
        PasswordStrength passwordStrength = PasswordStrength.calculate(str);
        activityChangePasswordBinding.passwordStrengthTxtview.setText(passwordStrength.msg);
        activityChangePasswordBinding.passwordStrengthTxtview.setTextColor(passwordStrength.color);
        if (activityChangePasswordBinding.passwordStrengthTxtview.getText().toString().equalsIgnoreCase("WEAK")) {
            activityChangePasswordBinding.passwordStrengthTxtview2.setBackgroundColor(getResources().getColor(R.color.gray));
            activityChangePasswordBinding.passwordStrengthTxtview3.setBackgroundColor(getResources().getColor(R.color.gray));
            activityChangePasswordBinding.passwordStrengthTxtview4.setBackgroundColor(getResources().getColor(R.color.gray));
            activityChangePasswordBinding.passwordStrengthTxtview1.setBackgroundColor(passwordStrength.color);
        } else if (activityChangePasswordBinding.passwordStrengthTxtview.getText().toString().equalsIgnoreCase("MEDIUM")) {
            activityChangePasswordBinding.passwordStrengthTxtview3.setBackgroundColor(getResources().getColor(R.color.gray));
            activityChangePasswordBinding.passwordStrengthTxtview4.setBackgroundColor(getResources().getColor(R.color.gray));
            activityChangePasswordBinding.passwordStrengthTxtview1.setBackgroundColor(passwordStrength.color);
            activityChangePasswordBinding.passwordStrengthTxtview2.setBackgroundColor(passwordStrength.color);
        } else if (activityChangePasswordBinding.passwordStrengthTxtview.getText().toString().equalsIgnoreCase("STRONG")) {
            activityChangePasswordBinding.passwordStrengthTxtview4.setBackgroundColor(getResources().getColor(R.color.gray));
            activityChangePasswordBinding.passwordStrengthTxtview1.setBackgroundColor(passwordStrength.color);
            activityChangePasswordBinding.passwordStrengthTxtview2.setBackgroundColor(passwordStrength.color);
            activityChangePasswordBinding.passwordStrengthTxtview3.setBackgroundColor(passwordStrength.color);
        } else if (activityChangePasswordBinding.passwordStrengthTxtview.getText().toString().equalsIgnoreCase("VERY STRONG")) {
            activityChangePasswordBinding.passwordStrengthTxtview1.setBackgroundColor(passwordStrength.color);
            activityChangePasswordBinding.passwordStrengthTxtview2.setBackgroundColor(passwordStrength.color);
            activityChangePasswordBinding.passwordStrengthTxtview3.setBackgroundColor(passwordStrength.color);
            activityChangePasswordBinding.passwordStrengthTxtview4.setBackgroundColor(passwordStrength.color);
        } else {
            activityChangePasswordBinding.passwordStrengthTxtview2.setBackgroundColor(getResources().getColor(R.color.gray));
            activityChangePasswordBinding.passwordStrengthTxtview3.setBackgroundColor(getResources().getColor(R.color.gray));
            activityChangePasswordBinding.passwordStrengthTxtview4.setBackgroundColor(getResources().getColor(R.color.gray));
            activityChangePasswordBinding.passwordStrengthTxtview1.setBackgroundColor(getResources().getColor(R.color.gray));
        }

        if (PasswordStrength.uppercase.equalsIgnoreCase("yes")) {
            activityChangePasswordBinding.uppercaseImageRel.setVisibility(View.VISIBLE);
            activityChangePasswordBinding.uppercaseImageRel.setBackground(getResources().getDrawable(R.drawable.ic_fill_correct_password));
            activityChangePasswordBinding.uppercaseDisplayTxtview.setTextColor(getResources().getColor(R.color.heading_bg));
        } else {
            activityChangePasswordBinding.uppercaseImageRel.setVisibility(View.VISIBLE);
            activityChangePasswordBinding.uppercaseImageRel.setBackground(getResources().getDrawable(R.drawable.ic_incorrect_strength_password));
            activityChangePasswordBinding.uppercaseDisplayTxtview.setTextColor(getResources().getColor(R.color.red));
        }

        if (PasswordStrength.digitcase.equalsIgnoreCase("yes")) {
            activityChangePasswordBinding.digitImageRel.setVisibility(View.VISIBLE);
            activityChangePasswordBinding.digitImageRel.setBackground(getResources().getDrawable(R.drawable.ic_fill_correct_password));
            activityChangePasswordBinding.digitDisplayTxtview.setTextColor(getResources().getColor(R.color.heading_bg));
        } else {
            activityChangePasswordBinding.digitImageRel.setVisibility(View.VISIBLE);
            activityChangePasswordBinding.digitImageRel.setBackground(getResources().getDrawable(R.drawable.ic_incorrect_strength_password));
            activityChangePasswordBinding.digitDisplayTxtview.setTextColor(getResources().getColor(R.color.red));
        }
        if (PasswordStrength.specialcharcase.equalsIgnoreCase("yes")) {
            activityChangePasswordBinding.specialCharImageRel.setVisibility(View.VISIBLE);
            activityChangePasswordBinding.specialCharImageRel.setBackground(getResources().getDrawable(R.drawable.ic_fill_correct_password));
            activityChangePasswordBinding.specialCharDisplayTxtview.setTextColor(getResources().getColor(R.color.heading_bg));
        } else {
            activityChangePasswordBinding.specialCharImageRel.setVisibility(View.VISIBLE);
            activityChangePasswordBinding.specialCharImageRel.setBackground(getResources().getDrawable(R.drawable.ic_incorrect_strength_password));
            activityChangePasswordBinding.specialCharDisplayTxtview.setTextColor(getResources().getColor(R.color.red));
        }
        if (PasswordStrength.minimumcharcase.equalsIgnoreCase("yes")) {
            activityChangePasswordBinding.minimumCharImageRel.setVisibility(View.VISIBLE);
            activityChangePasswordBinding.minimumCharImageRel.setBackground(getResources().getDrawable(R.drawable.ic_fill_correct_password));
            activityChangePasswordBinding.minimumDisplayTxtview.setTextColor(getResources().getColor(R.color.heading_bg));
        } else {
            activityChangePasswordBinding.minimumCharImageRel.setVisibility(View.VISIBLE);
            activityChangePasswordBinding.minimumCharImageRel.setBackground(getResources().getDrawable(R.drawable.ic_incorrect_strength_password));
            activityChangePasswordBinding.minimumDisplayTxtview.setTextColor(getResources().getColor(R.color.red));
        }
    }

    @Override
    public void onBackPressed() {
        wheretoBack();
        super.onBackPressed();
    }

    public void wheretoBack() {
//        Intent iLogin = new Intent(mContext, ForgotActivity.class);
//        iLogin.putExtra("whereTocomeLogin", getIntent().getStringExtra("whereTocomeLogin"));
//        startActivity(iLogin);
        finish();
    }

    public void getChangePasswordFillData() {
        newPasswordStr = activityChangePasswordBinding.newpasswordEdt.getText().toString();
        confirmPasswordStr = activityChangePasswordBinding.confirmpasswordEdt.getText().toString();
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
                        activityChangePasswordBinding.confirmpasswordErrorTxt.setVisibility(View.VISIBLE);
                        activityChangePasswordBinding.confirmpasswordErrorTxt.setText(getResources().getString(R.string.change_pass_error));
                    }
                } else {
                    activityChangePasswordBinding.newpasswordErrorTxt.setVisibility(View.VISIBLE);
                    activityChangePasswordBinding.newpasswordErrorTxt.setText(getResources().getString(R.string.singup_password_error));
                }
            } else {
                activityChangePasswordBinding.confirmpasswordErrorTxt.setVisibility(View.VISIBLE);
                activityChangePasswordBinding.confirmpasswordErrorTxt.setText(getResources().getString(R.string.change_confirmpassword_error));
            }
        } else {
            activityChangePasswordBinding.newpasswordErrorTxt.setVisibility(View.VISIBLE);
            activityChangePasswordBinding.newpasswordErrorTxt.setText(getResources().getString(R.string.change_newpassword_error));
        }
    }

    public void getChangePassword() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(getResources().getString(R.string.internet_error), getResources().getString(R.string.internet_connection_error), ChangePasswordActivity.this);
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
                        Utils.setPref(mContext, "LoginType", "Email");
                        Utils.setPref(mContext, "IsLoginUser", "1");
                        Utils.storeLoginData(changeModel.getData(), mContext);
                        Utils.storeCurrentLocationData(changeModel.getCurrentLocation(), mContext);
                        Utils.storeLoginOtherData(changeModel.getOtherData(), mContext);
                        if (Utils.whereTocomeLogin != null) {
                            if (Utils.whereTocomeLogin.equalsIgnoreCase("more")) {
                                Utils.showThanyouDialog(ChangePasswordActivity.this, "changePassword|InApp");
                            } else {
                                Utils.showThanyouDialog(ChangePasswordActivity.this, "changePassword|finishApp");
                            }
                        } else {
                            Utils.showThanyouDialog(ChangePasswordActivity.this, "changePassword");
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

    private Map<String, String> getChangePasswordData() {
        Map<String, String> map = new HashMap<>();
        map.put("NewPassword", newPasswordStr);
        map.put("MemberId", memberIdStr);
        map.put("TokenId", Utils.getPref(mContext, "registration_id"));
        map.put("ModelName", Utils.getDeviceName());

        return map;
    }
}
