package com.bharatarmy.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bharatarmy.Adapter.MatchFilterVenuesAdapter;
import com.bharatarmy.Adapter.TravelCityFilterAdapter;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.databinding.FragmentTravelCityFilterBinding;
import com.bharatarmy.databinding.OffersBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;


public class TravelCityFilterFragment extends BottomSheetDialogFragment {
    FragmentTravelCityFilterBinding travelCityFilterBinding;
    View rootView;
    Context mContext;
TravelCityFilterAdapter travelCityFilterAdapter;
    ArrayList<TravelModel> travelcityList;



    static TravelCityFilterFragment newInstance() {
        TravelCityFilterFragment f = new TravelCityFilterFragment();
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

        travelCityFilterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_travel_city_filter, container, false);

        rootView = travelCityFilterBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        init();

        return rootView;
    }

    public void init(){
        travelCityFilterBinding.filtercloseLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        travelCityFilterBinding.filterapplyLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        travelcityList = new ArrayList<TravelModel>();
        travelcityList.add(new TravelModel(R.drawable.flag_afghanistan, "Mumbai"));
        travelcityList.add(new TravelModel(R.drawable.flag_australia, "Dehli"));
        travelcityList.add(new TravelModel(R.drawable.flag_bangladesh, "Ahemedabad"));
        travelcityList.add(new TravelModel(R.drawable.flag_united_kingdom, "Indore"));
        travelcityList.add(new TravelModel(R.drawable.in_flag, "Dehradun"));

        travelCityFilterAdapter = new TravelCityFilterAdapter(mContext, travelcityList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        travelCityFilterBinding.cityFilterRcv.setLayoutManager(mLayoutManager);
        travelCityFilterBinding.cityFilterRcv.setItemAnimator(new DefaultItemAnimator());
        travelCityFilterBinding.cityFilterRcv.setAdapter(travelCityFilterAdapter);
    }

}
