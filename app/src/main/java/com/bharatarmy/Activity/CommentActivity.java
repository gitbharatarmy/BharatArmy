package com.bharatarmy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.bharatarmy.Adapter.CommentListAdapter;
import com.bharatarmy.Models.GalleryImageModel;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.Models.MoreDetailDataModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.AppConfiguration;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.ActivityCommentBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityCommentBinding activityCommentBinding;
    Context mContext;
    String selecteditem, memberIdStr, referenceIdStr, sourceTypeStr, commentNotesStr;
    CommentListAdapter commentListAdapter;
    boolean isLoading = false;
    LinearLayoutManager mLayoutManager;
    List<ImageDetailModel> commentList;
    int pageIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCommentBinding = DataBindingUtil.setContentView(this, R.layout.activity_comment);

        mContext = CommentActivity.this;

        init();
        setListiner();

    }

    public void init() {

        memberIdStr = String.valueOf(Utils.getAppUserId(mContext));
        referenceIdStr = getIntent().getStringExtra("referenceId");
        sourceTypeStr = getIntent().getStringExtra("sourceType");

        activityCommentBinding.shimmerViewContainer.startShimmerAnimation();
        activityCommentBinding.toolbarTitleTxt.setText("Comment");
        callGetAddCommentData();

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Recently");
        categories.add("Today");
        categories.add("Previous day");
        categories.add("Last week");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_list_item, categories);
        dataAdapter.setDropDownViewResource(R.layout.spinner_list_item);
        activityCommentBinding.commentFilterSpinner.setAdapter(dataAdapter);
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

        activityCommentBinding.commentSentImg.setOnClickListener(this);
        activityCommentBinding.commentEdt.setOnClickListener(this);

//        activityCommentBinding.commentScrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//               activityCommentBinding.commentRcv.setEnabled(false);
//activityCommentBinding.commentScrollView.setEnabled(true);
//                return false;
//            }
//        });
//
//
//        activityCommentBinding.commentRcv.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                activityCommentBinding.commentScrollView.setEnabled(false);
//activityCommentBinding.commentRcv.setEnabled(true);
//                return false;
//            }
//        });
//        activityCommentBinding.commentRcv.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (!isLoading) {
//                    if (mLayoutManager != null && mLayoutManager.findLastCompletelyVisibleItemPosition() == commentList.size() - 1) {
//                        //bottom of list!
//                       pageIndex=pageIndex+1;
//                        activityCommentBinding.progressBar.setVisibility(View.VISIBLE);
//                        loadMore();
//                    }
//                }
//            }
//
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//
//
//            }
//        });
    }


    public void setCommentList() {
        if (commentList != null && commentList.size() > 0) {
            commentListAdapter = new CommentListAdapter(mContext, commentList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
            activityCommentBinding.commentRcv.setLayoutManager(mLayoutManager);
            activityCommentBinding.commentRcv.setItemAnimator(new DefaultItemAnimator());
            activityCommentBinding.commentRcv.setAdapter(commentListAdapter);
        } else {
            activityCommentBinding.commentRcv.setVisibility(View.GONE);
            activityCommentBinding.norecordTxt.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_linear:
                break;
            case R.id.comment_sent_img:
                commentNotesStr = activityCommentBinding.commentEdt.getText().toString();
                if (!commentNotesStr.equalsIgnoreCase("")) {
                    InsertComment();
                } else {
                    Utils.ping(mContext, "Please enter comment");
                }

                break;

            case R.id.comment_edt:
//                activityCommentBinding.commentScrollView.setEnabled(true);
//                Utils.scrollScreen(activityCommentBinding.commentScrollView);
                break;
        }
    }
    private void loadMore() {


    }
    public void InsertComment() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(mContext.getResources().getString(R.string.internet_error), mContext.getResources().getString(R.string.internet_connection_error), CommentActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getInsertBAComments(getCommentData(), new retrofit.Callback<ImageMainModel>() {
            @Override
            public void success(ImageMainModel commentModel, Response response) {
                Utils.dismissDialog();
                if (commentModel == null) {
                    Utils.ping(mContext, mContext.getString(R.string.something_wrong));
                    return;
                }
                if (commentModel.getIsValid() == null) {
                    Utils.ping(mContext, mContext.getString(R.string.something_wrong));
                    return;
                }
                if (commentModel.getIsValid() == 0) {
                    Utils.ping(mContext, mContext.getString(R.string.false_msg));
                    return;
                }
                if (commentModel.getIsValid() == 1) {
                    if (commentModel.getData() != null) {
                        activityCommentBinding.commentEdt.setText("");
                        callGetAddCommentData();
                    }

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, mContext.getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getCommentData() {
        Map<String, String> map = new HashMap<>();
        map.put("MemberId", memberIdStr);
        map.put("ReferenceId", referenceIdStr);
        map.put("CommentNotes", commentNotesStr);
        map.put("SourceType", sourceTypeStr);
        return map;

    }


    public void callGetAddCommentData() {

        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(mContext.getResources().getString(R.string.internet_error), mContext.getResources().getString(R.string.internet_connection_error), CommentActivity.this);
            return;
        }

//        Utils.showDialog(mContext);

        ApiHandler.getApiService().getCommentDataByPost(getAddCommentData(), new retrofit.Callback<ImageMainModel>() {
            @Override
            public void success(ImageMainModel commentaddModel, Response response) {
                Utils.dismissDialog();
                if (commentaddModel == null) {
                    Utils.ping(mContext, mContext.getString(R.string.something_wrong));
                    return;
                }
                if (commentaddModel.getIsValid() == null) {
                    Utils.ping(mContext, mContext.getString(R.string.something_wrong));
                    return;
                }
                if (commentaddModel.getIsValid() == 0) {
                    Utils.ping(mContext, mContext.getString(R.string.false_msg));
                    return;
                }
                if (commentaddModel.getIsValid() == 1) {
                    if (commentaddModel.getData() != null && commentaddModel.getData().size()>0) {

                        commentList=commentaddModel.getData();
                        activityCommentBinding.shimmerViewContainer.stopShimmerAnimation();
                        activityCommentBinding.shimmerViewContainer.setVisibility(View.GONE);
                        activityCommentBinding.norecordTxt.setVisibility(View.GONE);
                        activityCommentBinding.progressBar.setVisibility(View.GONE);
                        activityCommentBinding.commentRcv.setVisibility(View.VISIBLE);
//                        if (commentListAdapter != null && commentList.size() > 0) {
//                            commentListAdapter.addMoreDataToList(commentList);
//                            // just append more data to current list
//                        } else if (commentListAdapter != null && commentList.size() == 0) {
////                            Utils.ping(mContext,"No more data available");
//                            Log.d("pageIndex", "" + AppConfiguration.pageindex);
//                            isLoading = true;
//                        } else {
//                            setCommentList();
//                        }
                        setCommentList();
                    }else{
                        activityCommentBinding.shimmerViewContainer.stopShimmerAnimation();
                        activityCommentBinding.shimmerViewContainer.setVisibility(View.GONE);
                        activityCommentBinding.norecordTxt.setVisibility(View.VISIBLE);
                    }

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.dismissDialog();
                error.printStackTrace();
                error.getMessage();
                Utils.ping(mContext, mContext.getString(R.string.something_wrong));
            }
        });

    }

    private Map<String, String> getAddCommentData() {
        Map<String, String> map = new HashMap<>();
        map.put("MemberId", memberIdStr);
        map.put("PostId", referenceIdStr);
        map.put("SourceType", sourceTypeStr);
        return map;

    }
}
