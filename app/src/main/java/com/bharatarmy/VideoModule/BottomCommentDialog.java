package com.bharatarmy.VideoModule;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.bharatarmy.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomCommentDialog extends BottomSheetDialogFragment {

    public static String TAG = "FullScreenDialog";
    EditText commentTxt;
    LinearLayout comment_SendLinear;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.comment_sheet_item, container, false);


        commentTxt = (EditText) view.findViewById(R.id.comment_edt);
        comment_SendLinear = (LinearLayout) view.findViewById(R.id.comment_send_linear);

        comment_SendLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("commentSTr", commentTxt.getText().toString());
                dismiss();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.WRAP_CONTENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setGravity(Gravity.BOTTOM);
        }
    }



/*extends DialogFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.comment_sheet_item, null);
        getDialog().setTitle("Title");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) (metrics.heightPixels * 0.30));// here i have fragment height 30% of window's height you can set it as per your requirement
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogSlideAnim;

    }*/


}
