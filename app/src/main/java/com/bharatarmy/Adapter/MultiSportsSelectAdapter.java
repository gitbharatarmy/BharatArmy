package com.bharatarmy.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.MultiSportsSelectDialog;
import com.bharatarmy.R;

import java.util.ArrayList;

public class MultiSportsSelectAdapter extends RecyclerView.Adapter<MultiSportsSelectAdapter.MultiSelectDialogViewHolder> {

    private ArrayList<ImageDetailModel> mDataSet = new ArrayList<>();
    private String mSearchQuery = "";
    private Context mContext;

    public MultiSportsSelectAdapter(ArrayList<ImageDetailModel> dataSet, Context context) {
        this.mDataSet = dataSet;
        this.mContext = context;
    }

    @Override
    public MultiSelectDialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sports_multi_select_item, parent, false);
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

            if (!MultiSportsSelectDialog.selectedIdsForCallback.contains(mDataSet.get(position).getId())) {
                MultiSportsSelectDialog.selectedIdsForCallback.add(mDataSet.get(position).getId());
            }
        }

        if (checkForSelection(mDataSet.get(position).getId())) {
            holder.dialog_item_checkbox.setChecked(true);
        } else {
            holder.dialog_item_checkbox.setChecked(false);
        }

        /*holder.dialog_item_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.dialog_item_checkbox.isChecked()) {
                    MultiSelectDialog.selectedIdsForCallback.add(mDataSet.get(holder.getAdapterPosition()).getId());
                    holder.dialog_item_checkbox.setChecked(true);
                } else {
                    removeFromSelection(mDataSet.get(holder.getAdapterPosition()).getId());
                    holder.dialog_item_checkbox.setChecked(false);
                }
            }
        });*/


        holder.main_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!holder.dialog_item_checkbox.isChecked()) {
                    MultiSportsSelectDialog.selectedIdsForCallback.add(mDataSet.get(holder.getAdapterPosition()).getId());
                    holder.dialog_item_checkbox.setChecked(true);
                    mDataSet.get(holder.getAdapterPosition()).setSelected(true);
                    notifyItemChanged(holder.getAdapterPosition());
                } else {
                    removeFromSelection(mDataSet.get(holder.getAdapterPosition()).getId());
                    holder.dialog_item_checkbox.setChecked(false);
                    mDataSet.get(holder.getAdapterPosition()).setSelected(false);
                    notifyItemChanged(holder.getAdapterPosition());
                }
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
        for (int i = 0; i < MultiSportsSelectDialog.selectedIdsForCallback.size(); i++) {
            if (id.equals(MultiSportsSelectDialog.selectedIdsForCallback.get(i))) {
                MultiSportsSelectDialog.selectedIdsForCallback.remove(i);
            }
        }
    }


    private boolean checkForSelection(Integer id) {
        for (int i = 0; i < MultiSportsSelectDialog.selectedIdsForCallback.size(); i++) {
            if (id.equals(MultiSportsSelectDialog.selectedIdsForCallback.get(i))) {
                return true;
            }
        }
        return false;
    }



    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void setData(ArrayList<ImageDetailModel> data, String query, MultiSportsSelectAdapter MutliSportsSelectAdapter) {
        this.mDataSet = data;
        this.mSearchQuery = query;
        MutliSportsSelectAdapter.notifyDataSetChanged();
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
