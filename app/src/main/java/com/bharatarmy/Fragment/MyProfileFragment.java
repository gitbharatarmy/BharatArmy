package com.bharatarmy.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bharatarmy.Activity.EditProfileActivity;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentMyProfileBinding;


public class MyProfileFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    FragmentMyProfileBinding myProfileBinding;
    private View rootView;
    private Context mContext;

    public MyProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyProfileFragment newInstance(String param1, String param2) {
        MyProfileFragment fragment = new MyProfileFragment();
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

    /* for image uploading https://www.simplifiedcoding.net/retrofit-upload-file-tutorial/
     *  https://android.jlelse.eu/how-to-upload-image-to-mysql-server-using-retrofit-and-ion-in-android-observer-pattern-part-1-51c2340241c0
     *  https://www.androidhive.info/2019/02/android-choosing-image-camera-gallery-crop-functionality/*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        myProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_profile, container, false);

        rootView = myProfileBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListiner();
        setDataValue();

    }

    public void setListiner() {
        myProfileBinding.editTxt.setOnClickListener(this);
    }

    public void setDataValue() {
            myProfileBinding.userShowTxt.setText(Utils.getPref(mContext,"LoginUserName"));
            myProfileBinding.emailShowTxt.setText(Utils.getPref(mContext,"LoginEmailId"));
            myProfileBinding.phoneShowTxt.setText(Utils.getPref(mContext,"LoginPhoneNo"));
        Log.d("emailid",Utils.getPref(mContext,"LoginEmailId"));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_txt:
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
                break;
        }
    }
}
