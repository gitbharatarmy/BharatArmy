package com.bharatarmy.Fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;

import com.bharatarmy.R;
import com.bharatarmy.databinding.OffersBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class MyOffersBottomSheetDialogFragment extends BottomSheetDialogFragment {
    OffersBottomSheetBinding offersBottomSheetBinding;
    View rootView;
    Context mContext;



    static MyOffersBottomSheetDialogFragment newInstance() {
        MyOffersBottomSheetDialogFragment f = new MyOffersBottomSheetDialogFragment();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mString = getArguments().getString("string");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        offersBottomSheetBinding = DataBindingUtil.inflate(inflater, R.layout.offers_bottom_sheet, container, false);

        rootView = offersBottomSheetBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        init();

        return rootView;
    }

    public void init(){
        offersBottomSheetBinding.closeDialoglinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
dismiss();
            }
        });
    }

}
