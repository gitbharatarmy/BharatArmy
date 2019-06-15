package com.bharatarmy.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Adapter.TravelListAdapter;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.TravelModel;
import com.bharatarmy.R;
import com.bharatarmy.TravelDesignModule.MultiSelectDialog;
import com.bharatarmy.TravelDesignModule.MultiSelectModel;
import com.bharatarmy.databinding.BottomSheetListBinding;
import com.bharatarmy.databinding.FragmentTravelBinding;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.leinardi.android.speeddial.SpeedDialView;

import java.util.ArrayList;
import java.util.List;

public class TravelFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View rootView;
    public static Context mContext;
    public static FragmentTravelBinding travelBinding;
    private RecyclerView mRecyclerView;
    public static OnItemClick mListener;
    TextView filter_txt;
    public static TravelListAdapter travelListAdapter;
    boolean isOpen = false;
    FloatingActionButton fab;
    SpeedDialView speedDial;
    static String countriesDataString = "", monthDataString = "", groundDataString = "";
    public static List<TravelModel> content;
    //    final BottomSheetDialogFragment myBottomSheet = MyBottomSheetDialogFragment.newInstance("Modal Bottom Sheet");
    AlertDialog alertDialogAndroid;

    public TravelFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TravelFragment newInstance(String param1, String param2) {
        TravelFragment fragment = new TravelFragment();
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
//        travelBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_travel, container, false);
        travelBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_travel,container,false);

        rootView = travelBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        fab = getActivity().findViewById(R.id.fab);
        fab.show();
        speedDial=getActivity().findViewById(R.id.speedDial);
        speedDial.setVisibility(View.GONE);
        content = new ArrayList<TravelModel>();
        content.add(new TravelModel("ODI", "Wed 05 Jun, 10:30 AM(Local)",
                "India", "South Africa", R.drawable.in_flag,
                R.drawable.south_flag, "Hampshire Bowl,Southampton", R.drawable.first_match_map));

        content.add(new TravelModel("ODI", "Wed 05 Jun, 10:30 AM(Local)",
                "New Zealand", "Bangladesh", R.drawable.nz_flag,
                R.drawable.bangladesh_flag, "The Oval,London", R.drawable.second_match_map));

        content.add(new TravelModel("ODI", "Thu 13 Jun, 10:30 AM(Local)",
                "India", "New Zealand", R.drawable.in_flag,
                R.drawable.nz_flag, "Trent Bridge,Nottingham", R.drawable.third_match_map));

        content.add(new TravelModel("ODI", "Sun 16 Jun, 10:30 AM(Local)",
                "India", "Pakistan", R.drawable.in_flag,
                R.drawable.pk_flag, "Old Trafford,Manchester", R.drawable.forth_match_map));

        content.add(new TravelModel("ODI", "Sat 22 Jun, 10:30 AM(Local)",
                "India", "Afghanistan", R.drawable.in_flag,
                R.drawable.af_flag, "Hampshire Bowl,Southampton", R.drawable.forth_match_map));

        content.add(new TravelModel("ODI", "Tue 25 Jun, 10:30 AM(Local)",
                "England", "Australlia", R.drawable.england_flag,
                R.drawable.au_flag, "Lords,London", R.drawable.second_match_map));

        createAdapter(content);
        setDataValue();
        return rootView;
    }

    public void setDataValue() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialogFragment bottomSheetDialogFragment = new MyBottomSheetDialogFragment();
                //show it
                bottomSheetDialogFragment.show(getFragmentManager(), bottomSheetDialogFragment.getTag());
//                bottomSheetDialogFragment.setCancelable(false);

//                BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
//                View sheetView = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet_list, null);
//                mBottomSheetDialog.setContentView(sheetView);
//                mBottomSheetDialog.show();
            }
        });


    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemClick) {
            mListener = (OnItemClick) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnItemClick {
        void onTrave();


    }

    public static void createAdapter(List<TravelModel> content) {
        travelListAdapter = new TravelListAdapter(mContext, content, new image_click() {
            @Override
            public void image_more_click() {
                mListener.onTrave();
            }
        });
        @SuppressLint("WrongConstant")
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        travelBinding.recyclerView.setLayoutManager(mLayoutManager);
        travelBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        travelBinding.recyclerView.setAdapter(travelListAdapter);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public static class MyBottomSheetDialogFragment extends BottomSheetDialogFragment {
        BottomSheetListBinding bottomSheetListBinding;

        View rootView;
        Context mContext;

        MultiSelectDialog multiSelectDialog, monthSelectDialog, groundSelectDialog;


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
//        mString = getArguments().getString("string");
        }

        @Override
        public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {

            bottomSheetListBinding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_list, container, false);

            rootView = bottomSheetListBinding.getRoot();
            mContext = getActivity().getApplicationContext();


            setTravelNavigation();

            bottomSheetListBinding.searchTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    countriesDataString = bottomSheetListBinding.teamSelectedTxt.getText().toString();
                    monthDataString = bottomSheetListBinding.monthSelectedTxt.getText().toString();
                    groundDataString = bottomSheetListBinding.groundSelectedTxt.getText().toString();
                    if (!countriesDataString.equalsIgnoreCase("All")) {
                        Log.d("Selected Value", "Country :" + countriesDataString + "Month :" + monthDataString + "Ground :" + groundDataString);

                        List<TravelModel> filterFinalArray = new ArrayList<TravelModel>();
//                        for (TravelModel arrayObj : dataResponse.getData()) {
//                            if (arrayObj.getAddressCity().trim().equalsIgnoreCase(locationStr.trim()) &&
//                                    arrayObj.getSessionName().trim().toLowerCase().contains(classNameStr.trim().toLowerCase())) {
//                                filterFinalArray.add(arrayObj);
//                            }
//                        }
                        if (countriesDataString.contains(",")) {
                            String[] spiltstr = countriesDataString.split(",");

                            for (int j = 0; j < spiltstr.length; j++) {
                                for (int i = 0; i < content.size(); i++) {
                                    if (content.get(i).getMatch_first_Country().trim().equalsIgnoreCase(spiltstr[j].trim())) {
                                        filterFinalArray.add(content.get(i));
                                    }
                                }
                            }
                            createAdapter(filterFinalArray);
                        } else {
                            for (int i = 0; i < content.size(); i++) {
                                if (content.get(i).getMatch_first_Country().trim().equalsIgnoreCase(countriesDataString.trim())) {
                                    filterFinalArray.add(content.get(i));
                                }
                            }
                            createAdapter(filterFinalArray);
                        }

                    } else {
                        createAdapter(content);
                    }

                    dismiss();

                }
            });

            return rootView;
        }

        public void setTravelNavigation() {

            bottomSheetListBinding.teamSelectedLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    multiSelectDialog.show(getFragmentManager(), "multiSelectDialog");
                }
            });

            bottomSheetListBinding.monthSelectedLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    monthSelectDialog.show(getFragmentManager(), "multiSelectDialog");
                }
            });

            bottomSheetListBinding.groundSelectedLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    groundSelectDialog.show(getFragmentManager(), "multiSelectDialog");
                }
            });
            //preselected Ids of Country List
            final ArrayList<Integer> alreadySelectedCountries = new ArrayList<>();
            alreadySelectedCountries.add(1);
            alreadySelectedCountries.add(3);
            alreadySelectedCountries.add(4);
            alreadySelectedCountries.add(7);

            //List of Countries with Name and Id
            ArrayList<MultiSelectModel> listOfCountries = new ArrayList<>();
            listOfCountries.add(new MultiSelectModel(1, "INDIA"));
            listOfCountries.add(new MultiSelectModel(2, "USA"));
            listOfCountries.add(new MultiSelectModel(3, "UK"));
            listOfCountries.add(new MultiSelectModel(4, "UAE"));
            listOfCountries.add(new MultiSelectModel(5, "ENGLAND"));
            listOfCountries.add(new MultiSelectModel(6, "SINGAPORE"));
            listOfCountries.add(new MultiSelectModel(7, "CHINA"));
            listOfCountries.add(new MultiSelectModel(8, "RUSSIA"));
            listOfCountries.add(new MultiSelectModel(9, "BANGLADESH"));
            listOfCountries.add(new MultiSelectModel(10, "BELGIUM"));
            listOfCountries.add(new MultiSelectModel(11, "DENMARK"));
            listOfCountries.add(new MultiSelectModel(12, "GERMANY"));
            listOfCountries.add(new MultiSelectModel(13, "HONG KONG"));
            listOfCountries.add(new MultiSelectModel(14, "INDONESIA"));
            listOfCountries.add(new MultiSelectModel(15, "NETHERLAND"));
            listOfCountries.add(new MultiSelectModel(16, "NEW ZEALAND"));
            listOfCountries.add(new MultiSelectModel(17, "PORTUGAL"));
            listOfCountries.add(new MultiSelectModel(18, "KUWAIT"));
            listOfCountries.add(new MultiSelectModel(19, "QATAR"));
            listOfCountries.add(new MultiSelectModel(20, "SAUDI ARABIA"));
            listOfCountries.add(new MultiSelectModel(21, "SRI LANKA"));
            listOfCountries.add(new MultiSelectModel(130, "CANADA"));


            //MultiSelectModel
            multiSelectDialog = new MultiSelectDialog()
                    .title(getResources().getString(R.string.multi_select_dialog_title)) //setting title for dialog
                    .titleSize(25)
                    .positiveText("Done")
                    .negativeText("Cancel")
                    .setMinSelectionLimit(0)
                    .setMaxSelectionLimit(listOfCountries.size())
                    .preSelectIDsList(alreadySelectedCountries) //List of ids that you need to be selected
                    .multiSelectList(listOfCountries) // the multi select model list with ids and name
                    .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                        @Override
                        public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                            //will return list of selected IDS
                            for (int i = 0; i < selectedIds.size(); i++) {
//                            Toast.makeText(getContext(), "Selected Ids : " + selectedIds.get(i) + "\n" +
//                                    "Selected Names : " + selectedNames.get(i) + "\n" +
//                                    "DataString : " + dataString, Toast.LENGTH_SHORT).show();
                                bottomSheetListBinding.teamSelectedTxt.setText(dataString);
                            }
                        }

                        @Override
                        public void onCancel() {
                            Log.d("", "Dialog cancelled");

                        }
                    });


//        Month Dialog
            //preselected Ids of Month List
            final ArrayList<Integer> alreadySelectedMonth = new ArrayList<>();
            alreadySelectedMonth.add(1);
            alreadySelectedMonth.add(3);
            alreadySelectedMonth.add(4);
            alreadySelectedMonth.add(7);

            //List of Countries with Name and Id
            ArrayList<MultiSelectModel> listOfMonth = new ArrayList<>();
            listOfMonth.add(new MultiSelectModel(1, "January"));
            listOfMonth.add(new MultiSelectModel(2, "February"));
            listOfMonth.add(new MultiSelectModel(3, "March"));
            listOfMonth.add(new MultiSelectModel(4, "April"));
            listOfMonth.add(new MultiSelectModel(5, "May"));
            listOfMonth.add(new MultiSelectModel(6, "June"));
            listOfMonth.add(new MultiSelectModel(7, "July"));
            listOfMonth.add(new MultiSelectModel(8, "August"));
            listOfMonth.add(new MultiSelectModel(9, "September"));
            listOfMonth.add(new MultiSelectModel(10, "Octomber"));
            listOfMonth.add(new MultiSelectModel(11, "November"));
            listOfMonth.add(new MultiSelectModel(12, "December"));


            //MultiSelectModel
            monthSelectDialog = new MultiSelectDialog()
                    .title("Select Month") //setting title for dialog
                    .titleSize(25)
                    .positiveText("Done")
                    .negativeText("Cancel")
                    .setMinSelectionLimit(0)
                    .setMaxSelectionLimit(listOfMonth.size())
                    .preSelectIDsList(alreadySelectedMonth) //List of ids that you need to be selected
                    .multiSelectList(listOfMonth) // the multi select model list with ids and name
                    .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                        @Override
                        public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                            //will return list of selected IDS
                            for (int i = 0; i < selectedIds.size(); i++) {
//                            Toast.makeText(getContext(), "Selected Ids : " + selectedIds.get(i) + "\n" +
//                                    "Selected Names : " + selectedNames.get(i) + "\n" +
//                                    "DataString : " + dataString, Toast.LENGTH_SHORT).show();
                                bottomSheetListBinding.monthSelectedTxt.setText(dataString);
                            }


                        }

                        @Override
                        public void onCancel() {
                            Log.d("", "Dialog cancelled");

                        }
                    });


//        ground Dialog
            //preselected Ids of Ground List
            final ArrayList<Integer> alreadySelectedGround = new ArrayList<>();
            alreadySelectedGround.add(1);
            alreadySelectedGround.add(3);
            alreadySelectedGround.add(4);
            alreadySelectedGround.add(7);

            //List of Countries with Name and Id
            ArrayList<MultiSelectModel> listOfGround = new ArrayList<>();
            listOfGround.add(new MultiSelectModel(1, "The Oval,London"));
            listOfGround.add(new MultiSelectModel(2, "Trent Bridge,Nottingham"));
            listOfGround.add(new MultiSelectModel(3, "Cardiff Wales Stadium,Cardiff"));
            listOfGround.add(new MultiSelectModel(4, "Harmpshire Bowl,Southampton"));
            listOfGround.add(new MultiSelectModel(5, "Old Trafford,Manchester"));
            listOfGround.add(new MultiSelectModel(6, "Headingley,Leeds"));
            listOfGround.add(new MultiSelectModel(7, "Lords,London"));
            listOfGround.add(new MultiSelectModel(8, "Edgbaston,Birmingham"));
            listOfGround.add(new MultiSelectModel(9, "The Riverside,Durtham"));
            listOfGround.add(new MultiSelectModel(10, "Bristol Country listOfGround,Bristol"));
            listOfGround.add(new MultiSelectModel(11, "Country listOfGround Taunton,Taunton"));


            //MultiSelectModel
            groundSelectDialog = new MultiSelectDialog()
                    .title("Select Ground") //setting title for dialog
                    .titleSize(25)
                    .positiveText("Done")
                    .negativeText("Cancel")
                    .setMinSelectionLimit(0)
                    .setMaxSelectionLimit(listOfGround.size())
                    .preSelectIDsList(alreadySelectedGround) //List of ids that you need to be selected
                    .multiSelectList(listOfGround) // the multi select model list with ids and name
                    .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                        @Override
                        public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                            //will return list of selected IDS
                            for (int i = 0; i < selectedIds.size(); i++) {
//                            Toast.makeText(getContext(), "Selected Ids : " + selectedIds.get(i) + "\n" +
//                                    "Selected Names : " + selectedNames.get(i) + "\n" +
//                                    "DataString : " + dataString, Toast.LENGTH_SHORT).show();

                                bottomSheetListBinding.groundSelectedTxt.setText(dataString);
                            }


                        }

                        @Override
                        public void onCancel() {
                            Log.d("", "Dialog cancelled");

                        }
                    });

//         set listener
            bottomSheetListBinding.rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                @Override
                public void valueChanged(Number minValue, Number maxValue) {
                    bottomSheetListBinding.textMin.setText(String.valueOf(minValue));
                    bottomSheetListBinding.textMax.setText(String.valueOf(maxValue));
                }
            });
            // set final value listener
            bottomSheetListBinding.rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
                @Override
                public void finalValue(Number minValue, Number maxValue) {
                    Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
                }
            });


        }



    }

}
