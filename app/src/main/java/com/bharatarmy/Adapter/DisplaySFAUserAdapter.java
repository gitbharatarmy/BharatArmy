package com.bharatarmy.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharatarmy.Activity.SportsInterestActivity;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.DisplaySfaUserItemListBinding;

import java.util.List;

public class DisplaySFAUserAdapter extends RecyclerView.Adapter<DisplaySFAUserAdapter.MyViewHolder> {
    Context mcontext;
    List<ImageDetailModel> sfausermodellist;

    public DisplaySFAUserAdapter(Context mContext, List<ImageDetailModel> sfausermodellist) {
        this.mcontext = mContext;
        this.sfausermodellist = sfausermodellist;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        DisplaySfaUserItemListBinding displaySfaUserItemListBinding;

        public MyViewHolder(DisplaySfaUserItemListBinding displaySfaUserItemListBinding) {
            super(displaySfaUserItemListBinding.getRoot());

            this.displaySfaUserItemListBinding = displaySfaUserItemListBinding;

        }
    }


    @Override
    public DisplaySFAUserAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DisplaySfaUserItemListBinding displaySfaUserItemListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.display_sfa_user_item_list, parent, false);
        return new DisplaySFAUserAdapter.MyViewHolder(displaySfaUserItemListBinding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(DisplaySFAUserAdapter.MyViewHolder holder, int position) {
        ImageDetailModel detail = sfausermodellist.get(position);

        if (Utils.getPref(mcontext, "entryType").equalsIgnoreCase("1")) {
            holder.displaySfaUserItemListBinding.downloadPdf.setVisibility(View.GONE);
        } else {
            holder.displaySfaUserItemListBinding.downloadPdf.setVisibility(View.VISIBLE);
        }

        holder.displaySfaUserItemListBinding.downloadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("pdfPath:", AppConfiguration.BASEURL + "DownloadCertificate?" + "Id=" + detail.getDataBankId());

                String pdfpath =AppConfiguration.BASEURL + "DownloadCertificate?Id="+detail.getDataBankId();
                Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pdfOpenintent.setDataAndType(Uri.parse(pdfpath), "application/pdf");
                try {
                    mcontext.startActivity(pdfOpenintent);
                } catch (ActivityNotFoundException e) {

                }
            }
        });

        holder.displaySfaUserItemListBinding.displayUserNameTxt.setText(detail.getFirstName());
        if (!detail.getEmailId().equalsIgnoreCase("")) {
            holder.displaySfaUserItemListBinding.displayUserEmailTxt.setText(detail.getEmailId());
        } else {
            holder.displaySfaUserItemListBinding.displayUserEmailTxt.setVisibility(View.GONE);
        }

        if (!detail.getPhoneNo().equalsIgnoreCase("")) {
            holder.displaySfaUserItemListBinding.displayUserphoneTxt.setText(detail.getPhoneNo());
        } else {
            holder.displaySfaUserItemListBinding.displayUserphoneTxt.setText(detail.getPhoneNo());
        }
        Utils.setImageInImageView(detail.getProfilePicUrl(), holder.displaySfaUserItemListBinding.userImage, mcontext);


        if (detail.getPhoneNo().equalsIgnoreCase("") && detail.getEmailId().equalsIgnoreCase("")) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 10, 0, 0);
            holder.displaySfaUserItemListBinding.displayUserNameTxt.setLayoutParams(params);

        }
    }

    @Override
    public long getItemId(int position) {
// return specific item's id here
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return sfausermodellist.size();
    }


}



