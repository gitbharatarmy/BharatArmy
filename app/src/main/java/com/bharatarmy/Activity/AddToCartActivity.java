package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityAddToCartBinding;

public class AddToCartActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityAddToCartBinding activityAddToCartBinding;
    Context mContext;
    int adultcount=0;
    int childcount=0;
    int infantscount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddToCartBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_to_cart);
        mContext = AddToCartActivity.this;
        init();
        setListiner();

    }

    public void init() {
        if (Utils.retriveLoginData(mContext)!=null){
            activityAddToCartBinding.userNameEdt.setText(Utils.retriveLoginData(mContext).getName());
            activityAddToCartBinding.userEmailEdt.setText(Utils.retriveLoginData(mContext).getEmail());
            activityAddToCartBinding.phoneNoEdt.setText("+ "+Utils.retriveLoginData(mContext).getCountryPhoneNo()+" "+Utils.retriveLoginData(mContext).getPhoneNo());
//            Log.d("code :",Utils.retriveLoginData(mContext).getCountryPhoneNo());
        }
    }

    public void setListiner() {
        activityAddToCartBinding.backImg.setOnClickListener(this);
        activityAddToCartBinding.userEmailEdt.setOnClickListener(this);
        activityAddToCartBinding.adultaddTxt.setOnClickListener(this);
        activityAddToCartBinding.adultremoveTxt.setOnClickListener(this);
        activityAddToCartBinding.childaddTxt.setOnClickListener(this);
        activityAddToCartBinding.childremoveTxt.setOnClickListener(this);
        activityAddToCartBinding.infantsaddTxt.setOnClickListener(this);
        activityAddToCartBinding.infantsremoveTxt.setOnClickListener(this);
        activityAddToCartBinding.callLinear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_img:
                finish();
                break;
            case R.id.user_name_edt:
//                Utils.scrollScreen(activityAddToCartBinding.addtocartScroll);
                break;
            case R.id.adultremove_txt:
                if (adultcount!=1){
                    adultcount=adultcount-1;
                    if (adultcount<=9){
                        activityAddToCartBinding.adultcountTxt.setText("0"+String.valueOf(adultcount));
                    }else{
                        activityAddToCartBinding.adultcountTxt.setText(String.valueOf(adultcount));
                    }

                }else{
                    activityAddToCartBinding.adultremoveTxt.setClickable(false);
                }

                break;
            case R.id.adultadd_txt:
                activityAddToCartBinding.adultremoveTxt.setClickable(true);
                adultcount=adultcount+1;
                if (adultcount<=9){
                    activityAddToCartBinding.adultcountTxt.setText("0"+String.valueOf(adultcount));
                }else{
                    activityAddToCartBinding.adultcountTxt.setText(String.valueOf(adultcount));
                }

                break;
            case R.id.childremove_txt:
                if (childcount!=0){
                    childcount=childcount-1;
                    if (childcount<=9){
                        activityAddToCartBinding.childcountTxt.setText("0"+String.valueOf(childcount));
                    }else{
                        activityAddToCartBinding.childcountTxt.setText(String.valueOf(childcount));
                    }

                }else{
                    activityAddToCartBinding.childremoveTxt.setClickable(false);
                }
                break;
            case R.id.childadd_txt:
                activityAddToCartBinding.childremoveTxt.setClickable(true);
                childcount=childcount+1;
                if (childcount<=9){
                    activityAddToCartBinding.childcountTxt.setText("0"+String.valueOf(childcount));
                }else{
                    activityAddToCartBinding.childcountTxt.setText(String.valueOf(childcount));
                }
                break;
            case R.id.infantsremove_txt:
                if (infantscount!=0){
                    infantscount=infantscount-1;
                    if (infantscount<=9){
                        activityAddToCartBinding.infantscountTxt.setText("0"+String.valueOf(infantscount));
                    }else{
                        activityAddToCartBinding.infantscountTxt.setText(String.valueOf(infantscount));
                    }

                }else{
                    activityAddToCartBinding.infantsremoveTxt.setClickable(false);
                }
                break;
            case R.id.infantsadd_txt:
                activityAddToCartBinding.infantsremoveTxt.setClickable(true);
                infantscount=infantscount+1;
                if (infantscount<=9){
                    activityAddToCartBinding.infantscountTxt.setText("0"+String.valueOf(infantscount));
                }else{
                    activityAddToCartBinding.infantscountTxt.setText(String.valueOf(infantscount));
                }
                break;
        }
    }
}
