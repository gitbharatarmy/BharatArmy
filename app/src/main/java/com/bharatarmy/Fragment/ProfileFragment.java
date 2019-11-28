package com.bharatarmy.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bharatarmy.Activity.EditProfileActivity;
import com.bharatarmy.Models.MyScreenChnagesModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentProfileBinding;
import com.leinardi.android.speeddial.SpeedDialView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class ProfileFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentProfileBinding fragmentProfileBinding;
    private View rootView;
    private Context mContext;
    SpeedDialView speedDial;
    String countryFlagStr;
   
    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        rootView = fragmentProfileBinding.getRoot();
        mContext = getActivity().getApplicationContext();
        EventBus.getDefault().register(this);

        speedDial = getActivity().findViewById(R.id.speedDial);
        speedDial.setVisibility(View.GONE);
        return rootView;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

 
        init();
        setListiner();

    }

    public void init() {
        if (Utils.retriveLoginData(mContext) != null) {
            if (Utils.retriveLoginData(mContext).getName() != null) {
                fragmentProfileBinding.userShowTxt.setText(Utils.retriveLoginData(mContext).getName());
            }
            if (Utils.retriveLoginData(mContext).getEmail() != null) {
                fragmentProfileBinding.emailShowTxt.setText(Utils.retriveLoginData(mContext).getEmail());
            }
            if(Utils.retriveLoginData(mContext).getCountryISOCode()!=null){
                if (!Utils.retriveLoginData(mContext).getCountryISOCode().equalsIgnoreCase("")){
                    if (Utils.retriveLoginData(mContext).getPhoneNo() != null && !Utils.retriveLoginData(mContext).getPhoneNo().equalsIgnoreCase("") ) {
                        fragmentProfileBinding.countryFlagImg.setVisibility(View.VISIBLE);
                        countryFlagStr = AppConfiguration.FLAG_URL + Utils.retriveLoginData(mContext).getCountryISOCode() + ".png";
                        Utils.setImageInImageView(countryFlagStr, fragmentProfileBinding.countryFlagImg, mContext);
                    }
                }else{
                    fragmentProfileBinding.countryFlagImg.setVisibility(View.GONE);
                }
            }
            if (Utils.retriveLoginData(mContext).getPhoneNo() != null && !Utils.retriveLoginData(mContext).getPhoneNo().equalsIgnoreCase("") ) {
                fragmentProfileBinding.phoneShowTxt.setText(Utils.retriveLoginData(mContext).getPhoneNo());
            }
            if (Utils.retriveLoginData(mContext).getProfilePicUrl() != null) {
                Utils.setImageInImageView(Utils.retriveLoginData(mContext).getProfilePicUrl(), fragmentProfileBinding.profileImage, mContext);
            }
            if (Utils.retriveLoginData(mContext).getGender() != null) {
                if (Utils.retriveLoginData(mContext).getGender().equals(1)) {
                    fragmentProfileBinding.genderShowTxt.setText("Male");
                } else if (Utils.retriveLoginData(mContext).getGender().equals(2)) {
                    fragmentProfileBinding.genderShowTxt.setText("Female");
                } else {
                    fragmentProfileBinding.genderShowTxt.setText("-");
                }
            }

            if (Utils.retriveLoginData(mContext).getAddressline1()!=null){
                if (Utils.retriveLoginData(mContext).getAddressline2()!=null){
                    if (Utils.retriveLoginData(mContext).getArea()!=null){
                        if(Utils.retriveLoginData(mContext).getStrCityName()!=null){
                            if (Utils.retriveLoginData(mContext).getStrState()!=null) {
                                if (Utils.retriveLoginData(mContext).getCountryISOCode()!=null){
                                    if (Utils.retriveLoginData(mContext).getPincode()!=null){
                                        if (!Utils.retriveLoginData(mContext).getAddressline1().equalsIgnoreCase("")) {
                                            fragmentProfileBinding.addressShowTxt.setText(Utils.retriveLoginData(mContext).getAddressline1() +
                                                    ", " + Utils.retriveLoginData(mContext).getAddressline2() +
                                                    ", " + Utils.retriveLoginData(mContext).getArea() +
                                                    ", " + Utils.retriveLoginData(mContext).getStrCityName() +
                                                    ", " + Utils.retriveLoginData(mContext).getStrState() +
                                                    ", " + Utils.getCountryNameUsingCountryCode(Utils.retriveLoginData(mContext).getCountryISOCode()) +
                                                    ", " + Utils.retriveLoginData(mContext).getPincode());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }



            }

        }

    }

    public void setListiner() {
     fragmentProfileBinding.editProfileLinear.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_profile_linear:
                Utils.handleClickEvent(mContext, fragmentProfileBinding.editProfileLinear);
                Intent intent = new Intent(mContext, EditProfileActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }

    @Subscribe
    public void customEventReceived(MyScreenChnagesModel event) {
        if (!event.getMessage().equalsIgnoreCase("")) {
           init();
        }

    }
}
