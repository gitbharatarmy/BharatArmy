package com.bharatarmy.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bharatarmy.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.lang.reflect.Field;



public class PacakgeSummaryBottomSheetDialogFragment extends BottomSheetDialogFragment {
    View rootView;
    Context mContext;
    LinearLayout back_img;
    TextView privacypolicy_txt, cancellationpolicy_txt, inclusiondetail_txt, exclusiondetail_txt;
    ImageView privacyexpand_collapse_img,exlusionexpand_collapse_img,inclusionexpand_collapse_img,cancelexpand_collapse_img;
LinearLayout privacypoilcy_linear,cancelpoilcy_linear,inclusionpoilcy_linear,exclusionpoilcy_linear;

    @Override
    public void setupDialog(Dialog dialog, int style) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialog;
        bottomSheetDialog.setContentView(R.layout.summary_bottom_sheet);

        privacypolicy_txt = (TextView) bottomSheetDialog.findViewById(R.id.privacypolicy_txt);
        cancellationpolicy_txt = (TextView) bottomSheetDialog.findViewById(R.id.cancellationpolicy_txt);
        inclusiondetail_txt = (TextView) bottomSheetDialog.findViewById(R.id.inclusiondetail_txt);
        exclusiondetail_txt = (TextView) bottomSheetDialog.findViewById(R.id.exclusiondetail_txt);

        privacyexpand_collapse_img=(ImageView)bottomSheetDialog.findViewById(R.id.privacyexpand_collapse_img);
        exlusionexpand_collapse_img=(ImageView)bottomSheetDialog.findViewById(R.id.exlusionexpand_collapse_img);
        inclusionexpand_collapse_img=(ImageView)bottomSheetDialog.findViewById(R.id.inclusionexpand_collapse_img);
        cancelexpand_collapse_img=(ImageView)bottomSheetDialog.findViewById(R.id.cancelexpand_collapse_img);

        privacypoilcy_linear=(LinearLayout)bottomSheetDialog.findViewById(R.id.privacypoilcy_linear);
        cancelpoilcy_linear=(LinearLayout)bottomSheetDialog.findViewById(R.id.cancelpoilcy_linear);
        inclusionpoilcy_linear=(LinearLayout)bottomSheetDialog.findViewById(R.id.inclusionpoilcy_linear);
        exclusionpoilcy_linear=(LinearLayout)bottomSheetDialog.findViewById(R.id.exclusionpoilcy_linear);
        back_img = (LinearLayout) bottomSheetDialog.findViewById(R.id.back_img);

        String privacyDescription = "Please note that after the finalization of the Tour service Cost.\n" +
                "if there are any Hike in entrance fees of monuments  museums, Taxes, fuel cost or guide charges – by Govt of India, the same would be charged as extra.\n" +
                "TourMyIndia.com act only in the capacity of agent for the hotels, airlines, transporters, railways &amp; contractors providing other services &amp; all exchange orders, receipts, contracts &amp; tickets issued by us are issued subject to terms &amp; conditions under which these services are provided by them.";

        String arr[] = privacyDescription.split("\n");

        int bulletGap = (int) dp(10);

        SpannableStringBuilder ssb = new SpannableStringBuilder();
        for (int i = 0; i < arr.length; i++) {
            String line = arr[i];
            SpannableString ss = new SpannableString(line);
            ss.setSpan(new BulletSpan(bulletGap), 0, line.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.append(ss);

            //avoid last "\n"
            if (i + 1 < arr.length)
                ssb.append("\n");

        }

        privacypolicy_txt.setText(ssb);
        String cancellationDescription = "In the event of cancellation of tour  travel services due to any avoidable  unavoidable reasons we must be notified of the same in writing. Cancellation charges will be effective from the date we receive advice in writing, and cancellation charges would be as follows:\n" +
                "45 days prior to arrival: 10% of the Tour / service cost.\n" +
                "15 days prior to arrival: 25% of the Tour / service cost.\n" +
                "07 days prior to arrival: 50% of the Tour / service cost.\n" +
                "48 hours prior to arrival OR No Show: No Refund.";

        String arr1[] = cancellationDescription.split("\n");

        int bulletGap1 = (int) dp(10);

        SpannableStringBuilder ssb1 = new SpannableStringBuilder();
        for (int i = 0; i < arr1.length; i++) {
            String line = arr1[i];
            SpannableString ss = new SpannableString(line);
            ss.setSpan(new BulletSpan(bulletGap1), 0, line.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb1.append(ss);

            //avoid last "\n"
            if (i + 1 < arr1.length)
                ssb1.append("\n");

        }

        cancellationpolicy_txt.setText(ssb1);

        String inclusionsDescription = "10 nights accommodations.\n" +
                "Half board (full breakfast and dinner).\n" +
                "Transfer from the airport to the first hotel and from the last hotel back to the airport (subject to arrival on the 11th of July and departure on the 21st of July).\n" +
                "All touring, entrance fees, outreach activities and lecture material.\n" +
                "Air-conditioned bus and licensed tour guide.\n" +
                "Arise welcome pack.";

        String arr2[] = inclusionsDescription.split("\n");

        int bulletGap2 = (int) dp(10);

        SpannableStringBuilder ssb2 = new SpannableStringBuilder();
        for (int i = 0; i < arr2.length; i++) {
            String line = arr2[i];
            SpannableString ss = new SpannableString(line);
            ss.setSpan(new BulletSpan(bulletGap2), 0, line.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb2.append(ss);

            //avoid last "\n"
            if (i + 1 < arr2.length)
                ssb2.append("\n");

        }

        inclusiondetail_txt.setText(ssb2);

        String exclusionDescription = "Airfare to and from Israel.\n" +
                "Travel/health insurance (highly recommended).\n" +
                "Entry visa if needed.\n" +
                "Personal spending money.\n" +
                "Lunches (except where specified).\n" +
                "100 USD for tipping bus driver, guide and hotel staff (will be collected on the first evening).";

        String arr3[] = exclusionDescription.split("\n");

        int bulletGap3 = (int) dp(10);

        SpannableStringBuilder ssb3 = new SpannableStringBuilder();
        for (int i = 0; i < arr3.length; i++) {
            String line = arr3[i];
            SpannableString ss = new SpannableString(line);
            ss.setSpan(new BulletSpan(bulletGap3), 0, line.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb3.append(ss);

            //avoid last "\n"
            if (i + 1 < arr3.length)
                ssb3.append("\n");

        }

        exclusiondetail_txt.setText(ssb3);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        privacypoilcy_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (privacypolicy_txt.isShown()){
                    privacypolicy_txt.setVisibility(View.GONE);
                    privacyexpand_collapse_img.setImageDrawable(getResources().getDrawable(R.drawable.collapse));
                }else{
                    privacypolicy_txt.setVisibility(View.VISIBLE);
                    privacyexpand_collapse_img.setImageDrawable(getResources().getDrawable(R.drawable.expands));
                }
            }
        });
        cancelpoilcy_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancellationpolicy_txt.isShown()){
                    cancellationpolicy_txt.setVisibility(View.GONE);
                    cancelexpand_collapse_img.setImageDrawable(getResources().getDrawable(R.drawable.collapse));
                }else{
                    cancellationpolicy_txt.setVisibility(View.VISIBLE);
                    cancelexpand_collapse_img.setImageDrawable(getResources().getDrawable(R.drawable.expands));
                }
            }
        });
        inclusionpoilcy_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inclusiondetail_txt.isShown()){
                    inclusiondetail_txt.setVisibility(View.GONE);
                    inclusionexpand_collapse_img.setImageDrawable(getResources().getDrawable(R.drawable.collapse));
                }else{
                    inclusiondetail_txt.setVisibility(View.VISIBLE);
                    inclusionexpand_collapse_img.setImageDrawable(getResources().getDrawable(R.drawable.expands));
                }
            }
        });
        exclusionpoilcy_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exclusiondetail_txt.isShown()){
                    exclusiondetail_txt.setVisibility(View.GONE);
                    LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                    params.setMargins(0,50,0,70);
                    exclusionpoilcy_linear.setLayoutParams(params);
                    exlusionexpand_collapse_img.setImageDrawable(getResources().getDrawable(R.drawable.collapse));
                }else{
                    exclusiondetail_txt.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                    params.setMargins(0,50,0,10);
                    exclusionpoilcy_linear.setLayoutParams(params);
                    exlusionexpand_collapse_img.setImageDrawable(getResources().getDrawable(R.drawable.expands));
                }
            }
        });
        try {
            Field behaviorField = bottomSheetDialog.getClass().getDeclaredField("behavior");
            behaviorField.setAccessible(true);
            final BottomSheetBehavior behavior = (BottomSheetBehavior) behaviorField.get(bottomSheetDialog);
            behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private float dp(int dp) {
        return getResources().getDisplayMetrics().density * dp;
    }
}


//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        summaryBottomSheetBinding = DataBindingUtil.inflate(inflater, R.layout.summary_bottom_sheet, container, false);
//
//        rootView = summaryBottomSheetBinding.getRoot();
//        mContext = getActivity().getApplicationContext();
//
//        init();
//        setLisitner();
//
//        return rootView;
//    }
//
//    public void init(){
//
//
//        summaryBottomSheetBinding.privacypolicyTxt.setText(Html.fromHtml(getResources().getString(R.string.privacy_detail)));
//        summaryBottomSheetBinding.cancellationpolicyTxt.setText(Html.fromHtml(getResources().getString(R.string.cancellation_detail)));
//        summaryBottomSheetBinding.inclusiondetailTxt.setText(Html.fromHtml(getResources().getString(R.string.inclusions_detail)));
//        summaryBottomSheetBinding.exclusiondetailTxt.setText(Html.fromHtml(getResources().getString(R.string.exclusion_detail)));
//
//
//
//    }
//    public void setLisitner(){
//        summaryBottomSheetBinding.backImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//    }
//
//}
