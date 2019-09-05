package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.bharatarmy.Adapter.CommentListAdapter;
import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityCommentBinding;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityCommentBinding activityCommentBinding;
    Context mContext;
    String selecteditem;
    CommentListAdapter commentListAdapter;
    public List<GalleryImageModel> content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCommentBinding = DataBindingUtil.setContentView(this, R.layout.activity_comment);

        mContext = CommentActivity.this;

        setDataValue();
        setListiner();

    }

    public void setListiner() {
        activityCommentBinding.commentFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                selecteditem = parent.getItemAtPosition(position).toString();

                Utils.ping(mContext, "Selected item :" + selecteditem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        activityCommentBinding.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentActivity.this.finish();
            }
        });
    }


    public void setDataValue() {
        activityCommentBinding.toolbarTitleTxt.setText("Comment");


        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Recently");
        categories.add("Today");
        categories.add("Previous day");
        categories.add("Last week");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_list_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_list_item);

        // attaching data adapter to spinner
        activityCommentBinding.commentFilterSpinner.setAdapter(dataAdapter);

        String sourceString = "I completely agree with" + "<b>" + "@DJ Dhaval Shah" + "</b> ";
        //commentList fill
        content = new ArrayList<>();
        content.add(new GalleryImageModel("DJ Dhaval Shah", "5 minit ago",
                "I really would like to suggest the following application is very nice.It is very usefull and user friendly.",
                "You like", "200"));

        content.add(new GalleryImageModel("Viral Mavani", "15 minit ago",
                sourceString,
                "Like ?", "20"));

        content.add(new GalleryImageModel("Designer Dharshan", "Last week",
                "I really would like to suggest the following application is very nice.It is very usefull and user friendly.",
                "You like", "2k"));

        content.add(new GalleryImageModel("Mohit Oberoi", "yesterday",
                "Very Nice and usefull application.",
                "You like", "1k"));

        commentListAdapter = new CommentListAdapter(mContext, content);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        activityCommentBinding.commentRcv.setLayoutManager(mLayoutManager);
        activityCommentBinding.commentRcv.setItemAnimator(new DefaultItemAnimator());
        activityCommentBinding.commentRcv.setAdapter(commentListAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_linear:
                break;
        }
    }
}
