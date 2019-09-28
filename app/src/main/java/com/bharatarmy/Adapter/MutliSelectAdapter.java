package com.bharatarmy.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Interfaces.buttonclick_result;
import com.bharatarmy.Interfaces.image_click;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.MultiSelectDialog;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;

import java.util.ArrayList;

public class MutliSelectAdapter extends RecyclerView.Adapter<MutliSelectAdapter.MultiSelectDialogViewHolder> {

    private ArrayList<ImageDetailModel> mDataSet = new ArrayList<>();
    private String mSearchQuery = "";
    private Context mContext;
    int selectedposition=-1;
    buttonclick_result buttonclick_result;

     public MutliSelectAdapter(ArrayList<ImageDetailModel> dataSet, Context context, buttonclick_result buttonclick_result) {
        this.mDataSet = dataSet;
        this.mContext = context;
        this.buttonclick_result=buttonclick_result;
    }

    @Override
    public MultiSelectDialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.multi_select_item, parent, false);
        return new MultiSelectDialogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MultiSelectDialogViewHolder holder, int position) {

        if (!mSearchQuery.equals("") && mSearchQuery.length() > 1) {
            setHighlightedText(position, holder.dialog_name_item);
        } else {
            holder.dialog_name_item.setText(mDataSet.get(position).getName());
        }

        if (mDataSet.get(position).isSelected()) {
            if (!MultiSelectDialog.selectedIdsForCallback.contains(mDataSet.get(position).getId())) {
                MultiSelectDialog.selectedIdsForCallback.add(mDataSet.get(position).getId());
            }
        }

//        if (checkForSelection(mDataSet.get(position).getId())) {
//            holder.dialog_item_checkbox.setChecked(true);
//        } else {
//            holder.dialog_item_checkbox.setChecked(false);
//        }
        if (AppConfiguration.dialogType.equalsIgnoreCase("states")){
            if (!AppConfiguration.selectedStatesIdStr.equalsIgnoreCase("")) {
                checkForSelection(Integer.valueOf(AppConfiguration.selectedStatesIdStr));
            }
        }else if(AppConfiguration.dialogType.equalsIgnoreCase("cities")){
            if (!AppConfiguration.selectedCitiesIdStr.equalsIgnoreCase("")){
                checkForSelection(Integer.valueOf(AppConfiguration.selectedCitiesIdStr))  ;
            }
        }




        if (selectedposition==position){
            holder.dialog_item_checkbox.setChecked(true);
            MultiSelectDialog.selectedIdsForCallback.add(mDataSet.get(holder.getAdapterPosition()).getId());
            mDataSet.get(holder.getAdapterPosition()).setSelected(true);

            buttonclick_result.getResultandshow("done");
        }else {
            holder.dialog_item_checkbox.setChecked(false);
            removeFromSelection(mDataSet.get(holder.getAdapterPosition()).getId());
            mDataSet.get(holder.getAdapterPosition()).setSelected(false);
//            buttonclick_result.getResultandshow("cancel");
        }


        holder.main_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedposition=position;
                if (AppConfiguration.dialogType.equalsIgnoreCase("states")){
                    if (!AppConfiguration.selectedStatesIdStr.equalsIgnoreCase("")) {
                        removeFromSelection(Integer.valueOf(AppConfiguration.selectedStatesIdStr));
                    }
                    AppConfiguration.selectedStatesIdStr= String.valueOf(mDataSet.get(position).getId());

                }else if(AppConfiguration.dialogType.equalsIgnoreCase("cities")){
                    if (!AppConfiguration.selectedCitiesIdStr.equalsIgnoreCase("")) {
                        removeFromSelection(Integer.valueOf(AppConfiguration.selectedCitiesIdStr));
                    }
                    AppConfiguration.selectedCitiesIdStr= String.valueOf(mDataSet.get(position).getId());
                }
                notifyDataSetChanged();

            }
        });
    }

    private void setHighlightedText(int position, TextView textview) {
        String name = mDataSet.get(position).getName();
        SpannableString str = new SpannableString(name);
        int endLength = name.toLowerCase().indexOf(mSearchQuery) + mSearchQuery.length();
        ColorStateList highlightedColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{ContextCompat.getColor(mContext, R.color.colorAccent)});
        TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(null, Typeface.NORMAL, -1, highlightedColor, null);
        str.setSpan(textAppearanceSpan, name.toLowerCase().indexOf(mSearchQuery), endLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textview.setText(str);
    }

    private void removeFromSelection(Integer id) {
        for (int i = 0; i < MultiSelectDialog.selectedIdsForCallback.size(); i++) {
            if (id.equals(MultiSelectDialog.selectedIdsForCallback.get(i))) {
                MultiSelectDialog.selectedIdsForCallback.remove(i);
            }
        }
    }


    private void checkForSelection(Integer id) {
        for (int i = 0; i < mDataSet.size(); i++) {
            if (id.equals(mDataSet.get(i).getId())) {
                selectedposition=i;
                Log.d("selectedposition ",""+selectedposition);
                break;

            }
        }
    }


    /*//get selected name string seperated by coma
    public String getDataString() {
        String data = "";
        for (int i = 0; i < mDataSet.size(); i++) {
            if (checkForSelection(mDataSet.get(i).getId())) {
                data = data + ", " + mDataSet.get(i).getName();
            }
        }
        if (data.length() > 0) {
            return data.substring(1);
        } else {
            return "";
        }
    }
    //get selected name ararylist
    public ArrayList<String> getSelectedNameList() {
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < mDataSet.size(); i++) {
            if (checkForSelection(mDataSet.get(i).getId())) {
                names.add(mDataSet.get(i).getName());
            }
        }
        //  return names.toArray(new String[names.size()]);
        return names;
    }*/


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

   public void setData(ArrayList<ImageDetailModel> data, String query, MutliSelectAdapter mutliSelectAdapter) {
        this.mDataSet = data;
        this.mSearchQuery = query;
        mutliSelectAdapter.notifyDataSetChanged();
    }

    class MultiSelectDialogViewHolder extends RecyclerView.ViewHolder {
        private TextView dialog_name_item;
        private CheckBox dialog_item_checkbox;
        private LinearLayout main_container;

        MultiSelectDialogViewHolder(View view) {
            super(view);
            dialog_name_item = (TextView) view.findViewById(R.id.dialog_item_name);
            dialog_item_checkbox = (CheckBox) view.findViewById(R.id.dialog_item_checkbox);
            main_container = (LinearLayout) view.findViewById(R.id.main_container);
        }
    }
}
