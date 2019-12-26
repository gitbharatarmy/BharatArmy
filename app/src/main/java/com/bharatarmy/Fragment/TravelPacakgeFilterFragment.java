package com.bharatarmy.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bharatarmy.Adapter.TravelPacakgeFilterAdapter;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.FragmentTravelPacakgeFilterBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;


public class TravelPacakgeFilterFragment extends BottomSheetDialogFragment {
    FragmentTravelPacakgeFilterBinding travelPackageFilterBinding;
    View rootView;
    Context mContext;
    TravelPacakgeFilterAdapter travelPacakgeFilterAdapter;
    ArrayList<TravelModel> travelpackageList;



    static TravelPacakgeFilterFragment newInstance() {
        TravelPacakgeFilterFragment f = new TravelPacakgeFilterFragment();
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

        travelPackageFilterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_travel_pacakge_filter, container, false);

        rootView = travelPackageFilterBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        init();

        return rootView;
    }

    public void init(){
        travelPackageFilterBinding.filtercloseLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        travelPackageFilterBinding.filterapplyLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        travelpackageList = new ArrayList<TravelModel>();
        travelpackageList.add(new TravelModel(R.drawable.flag_afghanistan, "20 nights"));
        travelpackageList.add(new TravelModel(R.drawable.flag_australia, "10 nights"));
        travelpackageList.add(new TravelModel(R.drawable.flag_bangladesh, "5 nights"));
        travelpackageList.add(new TravelModel(R.drawable.flag_united_kingdom, "custom"));


        travelPacakgeFilterAdapter = new TravelPacakgeFilterAdapter(mContext, travelpackageList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        travelPackageFilterBinding.pacakageFilterRcv.setLayoutManager(mLayoutManager);
        travelPackageFilterBinding.pacakageFilterRcv.setItemAnimator(new DefaultItemAnimator());
        travelPackageFilterBinding.pacakageFilterRcv.setAdapter(travelPacakgeFilterAdapter);
    }

}
