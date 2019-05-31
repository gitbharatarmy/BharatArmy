package com.bharatarmy.Fragment;


import java.util.ArrayList;

//public class MyBottomSheetDialogFragment extends BottomSheetDialogFragment {
//    BottomSheetListBinding bottomSheetListBinding;
//    String countriesDataString, monthDataString, groundDataString;
//    View rootView;
//    Context mContext;
//
//    MultiSelectDialog multiSelectDialog, monthSelectDialog, groundSelectDialog;
//
//
//    static MyBottomSheetDialogFragment newInstance() {
//        MyBottomSheetDialogFragment f = new MyBottomSheetDialogFragment();
//        Bundle args = new Bundle();
//        f.setArguments(args);
//        return f;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        mString = getArguments().getString("string");
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        bottomSheetListBinding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_list, container, false);
//
//        rootView = bottomSheetListBinding.getRoot();
//        mContext = getActivity().getApplicationContext();
//
//
//        setTravelNavigation();
//
//        bottomSheetListBinding.searchTxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                countriesDataString= bottomSheetListBinding.teamSelectedTxt.getText().toString();
//                monthDataString=bottomSheetListBinding.monthSelectedTxt.getText().toString();
//                groundDataString=bottomSheetListBinding.groundSelectedTxt.getText().toString();
//// Set data to pass
//                TravelFragment fragment = new TravelFragment(); //Your Fragment
//                Bundle args = new Bundle();
//                args.putString("countriesString", countriesDataString);
//                args.putString("monthString", monthDataString);
//                args.putString("groundString", groundDataString);
//                fragment.setArguments(args);
//
//            }
//        });
//
//        return rootView;
//    }
//
//    public void setTravelNavigation() {
//
//        bottomSheetListBinding.teamSelectedLinear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                multiSelectDialog.show(getFragmentManager(), "multiSelectDialog");
//            }
//        });
//
//        bottomSheetListBinding.monthSelectedLinear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                monthSelectDialog.show(getFragmentManager(), "multiSelectDialog");
//            }
//        });
//
//        bottomSheetListBinding.groundSelectedLinear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                groundSelectDialog.show(getFragmentManager(), "multiSelectDialog");
//            }
//        });
//        //preselected Ids of Country List
//        final ArrayList<Integer> alreadySelectedCountries = new ArrayList<>();
//        alreadySelectedCountries.add(1);
//        alreadySelectedCountries.add(3);
//        alreadySelectedCountries.add(4);
//        alreadySelectedCountries.add(7);
//
//        //List of Countries with Name and Id
//        ArrayList<MultiSelectModel> listOfCountries = new ArrayList<>();
//        listOfCountries.add(new MultiSelectModel(1, "INDIA"));
//        listOfCountries.add(new MultiSelectModel(2, "USA"));
//        listOfCountries.add(new MultiSelectModel(3, "UK"));
//        listOfCountries.add(new MultiSelectModel(4, "UAE"));
//        listOfCountries.add(new MultiSelectModel(5, "JAPAN"));
//        listOfCountries.add(new MultiSelectModel(6, "SINGAPORE"));
//        listOfCountries.add(new MultiSelectModel(7, "CHINA"));
//        listOfCountries.add(new MultiSelectModel(8, "RUSSIA"));
//        listOfCountries.add(new MultiSelectModel(9, "BANGLADESH"));
//        listOfCountries.add(new MultiSelectModel(10, "BELGIUM"));
//        listOfCountries.add(new MultiSelectModel(11, "DENMARK"));
//        listOfCountries.add(new MultiSelectModel(12, "GERMANY"));
//        listOfCountries.add(new MultiSelectModel(13, "HONG KONG"));
//        listOfCountries.add(new MultiSelectModel(14, "INDONESIA"));
//        listOfCountries.add(new MultiSelectModel(15, "NETHERLAND"));
//        listOfCountries.add(new MultiSelectModel(16, "NEW ZEALAND"));
//        listOfCountries.add(new MultiSelectModel(17, "PORTUGAL"));
//        listOfCountries.add(new MultiSelectModel(18, "KUWAIT"));
//        listOfCountries.add(new MultiSelectModel(19, "QATAR"));
//        listOfCountries.add(new MultiSelectModel(20, "SAUDI ARABIA"));
//        listOfCountries.add(new MultiSelectModel(21, "SRI LANKA"));
//        listOfCountries.add(new MultiSelectModel(130, "CANADA"));
//
//
//        //MultiSelectModel
//        multiSelectDialog = new MultiSelectDialog()
//                .title(getResources().getString(R.string.multi_select_dialog_title)) //setting title for dialog
//                .titleSize(25)
//                .positiveText("Done")
//                .negativeText("Cancel")
//                .setMinSelectionLimit(0)
//                .setMaxSelectionLimit(listOfCountries.size())
//                .preSelectIDsList(alreadySelectedCountries) //List of ids that you need to be selected
//                .multiSelectList(listOfCountries) // the multi select model list with ids and name
//                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
//                    @Override
//                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
//                        //will return list of selected IDS
//                        for (int i = 0; i < selectedIds.size(); i++) {
////                            Toast.makeText(getContext(), "Selected Ids : " + selectedIds.get(i) + "\n" +
////                                    "Selected Names : " + selectedNames.get(i) + "\n" +
////                                    "DataString : " + dataString, Toast.LENGTH_SHORT).show();
//                            bottomSheetListBinding.teamSelectedTxt.setText(dataString);
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        Log.d("", "Dialog cancelled");
//
//                    }
//                });
//
//
////        Month Dialog
//        //preselected Ids of Month List
//        final ArrayList<Integer> alreadySelectedMonth = new ArrayList<>();
//        alreadySelectedMonth.add(1);
//        alreadySelectedMonth.add(3);
//        alreadySelectedMonth.add(4);
//        alreadySelectedMonth.add(7);
//
//        //List of Countries with Name and Id
//        ArrayList<MultiSelectModel> listOfMonth = new ArrayList<>();
//        listOfMonth.add(new MultiSelectModel(1, "January"));
//        listOfMonth.add(new MultiSelectModel(2, "February"));
//        listOfMonth.add(new MultiSelectModel(3, "March"));
//        listOfMonth.add(new MultiSelectModel(4, "April"));
//        listOfMonth.add(new MultiSelectModel(5, "May"));
//        listOfMonth.add(new MultiSelectModel(6, "June"));
//        listOfMonth.add(new MultiSelectModel(7, "July"));
//        listOfMonth.add(new MultiSelectModel(8, "August"));
//        listOfMonth.add(new MultiSelectModel(9, "September"));
//        listOfMonth.add(new MultiSelectModel(10, "Octomber"));
//        listOfMonth.add(new MultiSelectModel(11, "November"));
//        listOfMonth.add(new MultiSelectModel(12, "December"));
//
//
//        //MultiSelectModel
//        monthSelectDialog = new MultiSelectDialog()
//                .title("Select Month") //setting title for dialog
//                .titleSize(25)
//                .positiveText("Done")
//                .negativeText("Cancel")
//                .setMinSelectionLimit(0)
//                .setMaxSelectionLimit(listOfMonth.size())
//                .preSelectIDsList(alreadySelectedMonth) //List of ids that you need to be selected
//                .multiSelectList(listOfMonth) // the multi select model list with ids and name
//                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
//                    @Override
//                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
//                        //will return list of selected IDS
//                        for (int i = 0; i < selectedIds.size(); i++) {
////                            Toast.makeText(getContext(), "Selected Ids : " + selectedIds.get(i) + "\n" +
////                                    "Selected Names : " + selectedNames.get(i) + "\n" +
////                                    "DataString : " + dataString, Toast.LENGTH_SHORT).show();
//                            bottomSheetListBinding.monthSelectedTxt.setText(dataString);
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        Log.d("", "Dialog cancelled");
//
//                    }
//                });
//
//
////        ground Dialog
//        //preselected Ids of Ground List
//        final ArrayList<Integer> alreadySelectedGround = new ArrayList<>();
//        alreadySelectedGround.add(1);
//        alreadySelectedGround.add(3);
//        alreadySelectedGround.add(4);
//        alreadySelectedGround.add(7);
//
//        //List of Countries with Name and Id
//        ArrayList<MultiSelectModel> listOfGround = new ArrayList<>();
//        listOfGround.add(new MultiSelectModel(1, "The Oval,London"));
//        listOfGround.add(new MultiSelectModel(2, "Trent Bridge,Nottingham"));
//        listOfGround.add(new MultiSelectModel(3, "Cardiff Wales Stadium,Cardiff"));
//        listOfGround.add(new MultiSelectModel(4, "Harmpshire Bowl,Southampton"));
//        listOfGround.add(new MultiSelectModel(5, "Old Trafford,Manchester"));
//        listOfGround.add(new MultiSelectModel(6, "Headingley,Leeds"));
//        listOfGround.add(new MultiSelectModel(7, "Lords,London"));
//        listOfGround.add(new MultiSelectModel(8, "Edgbaston,Birmingham"));
//        listOfGround.add(new MultiSelectModel(9, "The Riverside,Durtham"));
//        listOfGround.add(new MultiSelectModel(10, "Bristol Country listOfGround,Bristol"));
//        listOfGround.add(new MultiSelectModel(11, "Country listOfGround Taunton,Taunton"));
//
//
//        //MultiSelectModel
//        groundSelectDialog = new MultiSelectDialog()
//                .title("Select Ground") //setting title for dialog
//                .titleSize(25)
//                .positiveText("Done")
//                .negativeText("Cancel")
//                .setMinSelectionLimit(0)
//                .setMaxSelectionLimit(listOfGround.size())
//                .preSelectIDsList(alreadySelectedGround) //List of ids that you need to be selected
//                .multiSelectList(listOfGround) // the multi select model list with ids and name
//                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
//                    @Override
//                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
//                        //will return list of selected IDS
//                        for (int i = 0; i < selectedIds.size(); i++) {
////                            Toast.makeText(getContext(), "Selected Ids : " + selectedIds.get(i) + "\n" +
////                                    "Selected Names : " + selectedNames.get(i) + "\n" +
////                                    "DataString : " + dataString, Toast.LENGTH_SHORT).show();
//
//                            bottomSheetListBinding.groundSelectedTxt.setText(dataString);
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        Log.d("", "Dialog cancelled");
//
//                    }
//                });
//
////         set listener
//        bottomSheetListBinding.rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
//            @Override
//            public void valueChanged(Number minValue, Number maxValue) {
//                bottomSheetListBinding.textMin.setText(String.valueOf(minValue));
//                bottomSheetListBinding.textMax.setText(String.valueOf(maxValue));
//            }
//        });
//        // set final value listener
//        bottomSheetListBinding.rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
//            @Override
//            public void finalValue(Number minValue, Number maxValue) {
//                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
//            }
//        });
//
////        bottomSheetListBinding.closeTxt.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                dismiss();
////            }
////        });
////
////        // Team Spinner
////        List team = new ArrayList();
////        team.add("All");
////        team.add("India");
////        team.add("Nepal");
////        team.add("China");
////        team.add("Bhutan");
////// Creating array adapter for spinner
////        ArrayAdapter dataAdapter = new ArrayAdapter(mContext, R.layout.spinner_list_item, team);
////
////        // Drop down style will be listview with radio button
////        dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
////
////        // attaching data adapter to spinner
////        bottomSheetListBinding.teamSpinner.setAdapter(dataAdapter);
////
////        bottomSheetListBinding.teamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////            @Override
////            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                // getting selected item
////                String item = parent.getItemAtPosition(position).toString();
////
////                // Showing selected spinner item in toast
//////                Toast.makeText(parent.getContext(), "Selected Country: " + item, Toast.LENGTH_LONG).show();
////            }
////
////            @Override
////            public void onNothingSelected(AdapterView<?> parent) {
////
////            }
////        });
//////        Month Spinner
////        // Spinner Drop down elements
////        List month = new ArrayList();
////        month.add("All");
////        month.add("Jan");
////        month.add("Feb");
////        month.add("March");
////        month.add("April");
////        month.add("May");
////        month.add("Jun");
////        month.add("July");
////        month.add("August");
////        month.add("September");
////        month.add("Octomber");
////        month.add("November");
////        month.add("December");
////
////// Creating array adapter for spinner
////        ArrayAdapter monthAdapter = new ArrayAdapter(mContext, R.layout.spinner_list_item, month);
////
////        // Drop down style will be listview with radio button
////        monthAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
////
////        // attaching data adapter to spinner
////        bottomSheetListBinding.monthSpinner.setAdapter(monthAdapter);
////
////        bottomSheetListBinding.monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////            @Override
////            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                // getting selected item
////                String item = parent.getItemAtPosition(position).toString();
////
////                // Showing selected spinner item in toast
//////                Toast.makeText(parent.getContext(), "Selected Month: " + item, Toast.LENGTH_LONG).show();
////            }
////
////            @Override
////            public void onNothingSelected(AdapterView<?> parent) {
////
////            }
////        });
////        //        Ground Spinner
////        // Spinner Drop down elements
////        List ground = new ArrayList();
////        ground.add("All");
////        ground.add("The Oval,London");
////        ground.add("Trent Bridge,Nottingham");
////        ground.add("Cardiff Wales Stadium,Cardiff");
////        ground.add("Harmpshire Bowl,Southampton");
////        ground.add("Old Trafford,Manchester");
////        ground.add("Headingley,Leeds");
////        ground.add("Lords,London");
////        ground.add("Edgbaston,Birmingham");
////        ground.add("The Riverside,Durtham");
////        ground.add("Bristol Country Ground,Bristol");
////        ground.add("Country Ground Taunton,Taunton");
////
////// Creating array adapter for spinner
////        ArrayAdapter groundAdapter = new ArrayAdapter(mContext, R.layout.spinner_list_item, ground);
////
////        // Drop down style will be listview with radio button
////        groundAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
////
////        // attaching data adapter to spinner
////        bottomSheetListBinding.groundSpinner.setAdapter(groundAdapter);
////
////        bottomSheetListBinding.groundSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////            @Override
////            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                // getting selected item
////                String item = parent.getItemAtPosition(position).toString();
////
////                // Showing selected spinner item in toast
//////                Toast.makeText(parent.getContext(), "Selected Ground: " + item, Toast.LENGTH_LONG).show();
////            }
////
////            @Override
////            public void onNothingSelected(AdapterView<?> parent) {
////
////            }
////        });
////
////
////        // set listener
////        bottomSheetListBinding.rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
////            @Override
////            public void valueChanged(Number minValue, Number maxValue) {
////                bottomSheetListBinding.textMin.setText(String.valueOf(minValue));
////                bottomSheetListBinding.textMax.setText(String.valueOf(maxValue));
////            }
////        });
////        // set final value listener
////        bottomSheetListBinding.rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
////            @Override
////            public void finalValue(Number minValue, Number maxValue) {
////                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
////            }
////        });
//
//
//    }


//}
