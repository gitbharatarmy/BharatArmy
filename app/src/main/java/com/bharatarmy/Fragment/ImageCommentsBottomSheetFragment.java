package com.bharatarmy.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.bharatarmy.Adapter.CommentListAdapter;
import com.bharatarmy.Models.ImageDetailModel;
import com.bharatarmy.Models.ImageMainModel;
import com.bharatarmy.R;
import com.bharatarmy.Utility.ApiHandler;
import com.bharatarmy.Utility.Utils;
import com.bharatarmy.databinding.FragmentImageCommentsBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.RetrofitError;
import retrofit.client.Response;


public class ImageCommentsBottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    FragmentImageCommentsBottomSheetBinding fragmentImageCommentsBottomSheetBinding;
    View rootView;
    Context mContext;
    String selecteditem, memberIdStr, referenceIdStr, sourceTypeStr, commentNotesStr, titleStr;
    CommentListAdapter commentListAdapter;
    LinearLayoutManager mLayoutManager;
    List<ImageDetailModel> commentList;

    public ImageCommentsBottomSheetFragment(String referenceIdStr, String sourceTypeStr, String titleStr) {
        this.referenceIdStr = referenceIdStr;
        this.sourceTypeStr = sourceTypeStr;
        this.titleStr = titleStr;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mString = getArguments().getString("string");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentImageCommentsBottomSheetBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_image_comments_bottom_sheet, container, false);

        rootView = fragmentImageCommentsBottomSheetBinding.getRoot();
        mContext = getActivity().getApplicationContext();

        init();
        setListiner();

        return rootView;
    }

    public void init() {

        memberIdStr = String.valueOf(Utils.getAppUserId(mContext));


        Log.d("referenceIdStr :", referenceIdStr + "sourcetype :" + sourceTypeStr + "title :" + titleStr);
        fragmentImageCommentsBottomSheetBinding.shimmerViewContainer.startShimmerAnimation();
        fragmentImageCommentsBottomSheetBinding.toolbarTitleTxt.setText(titleStr);  //titleStr
        callGetAddCommentData();

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Recently");
        categories.add("Today");
        categories.add("Previous day");
        categories.add("Last week");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, R.layout.spinner_list_item, categories);
        dataAdapter.setDropDownViewResource(R.layout.spinner_list_item);
        fragmentImageCommentsBottomSheetBinding.commentFilterSpinner.setAdapter(dataAdapter);
    }

    public void setListiner() {
        fragmentImageCommentsBottomSheetBinding.commentFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        fragmentImageCommentsBottomSheetBinding.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        fragmentImageCommentsBottomSheetBinding.commentSentImg.setOnClickListener(this);
        fragmentImageCommentsBottomSheetBinding.commentEdt.setOnClickListener(this);
    }


    public void setCommentList() {
        if (commentList != null && commentList.size() > 0) {
            commentListAdapter = new CommentListAdapter(mContext, commentList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
            fragmentImageCommentsBottomSheetBinding.commentRcv.setLayoutManager(mLayoutManager);
            fragmentImageCommentsBottomSheetBinding.commentRcv.setItemAnimator(new DefaultItemAnimator());
            fragmentImageCommentsBottomSheetBinding.commentRcv.setAdapter(commentListAdapter);
        } else {
            fragmentImageCommentsBottomSheetBinding.commentRcv.setVisibility(View.GONE);
            fragmentImageCommentsBottomSheetBinding.noCommentrel.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_linear:
                break;
            case R.id.comment_sent_img:
//                Utils.handleClickEvent(mContext,fragmentImageCommentsBottomSheetBinding.commentSentImg);
                commentNotesStr = fragmentImageCommentsBottomSheetBinding.commentEdt.getText().toString();
                if (!commentNotesStr.equalsIgnoreCase("")) {
                    InsertComment();
                } else {
                    Utils.ping(mContext, "Please enter comment");
                }

                break;

            case R.id.comment_edt:
//                fragmentImageCommentsBottomSheetBinding.commentScrollView.setEnabled(true);
//                Utils.scrollScreen(fragmentImageCommentsBottomSheetBinding.commentScrollView);
                break;
        }
    }

    public void InsertComment() {
        if (!Utils.checkNetwork(mContext)) {
            Utils.showCustomDialog(mContext.getResources().getString(R.string.internet_error), mContext.getResources().getString(R.string.internet_connection_error), getActivity());
            return;
        }

        Utils.showDialog(getActivity());

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
                    fragmentImageCommentsBottomSheetBinding.commentEdt.setText("");
                    callGetAddCommentData();
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
            Utils.showCustomDialog(mContext.getResources().getString(R.string.internet_error), mContext.getResources().getString(R.string.internet_connection_error), getActivity());
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
                    if (commentaddModel.getData() != null && commentaddModel.getData().size() > 0) {

                        commentList = commentaddModel.getData();
                        fragmentImageCommentsBottomSheetBinding.shimmerViewContainer.stopShimmerAnimation();
                        fragmentImageCommentsBottomSheetBinding.shimmerViewContainer.setVisibility(View.GONE);
                        fragmentImageCommentsBottomSheetBinding.noCommentrel.setVisibility(View.GONE);
                        fragmentImageCommentsBottomSheetBinding.progressBar.setVisibility(View.GONE);
                        fragmentImageCommentsBottomSheetBinding.commentRcv.setVisibility(View.VISIBLE);

                        setCommentList();
                    } else {
                        fragmentImageCommentsBottomSheetBinding.shimmerViewContainer.stopShimmerAnimation();
                        fragmentImageCommentsBottomSheetBinding.shimmerViewContainer.setVisibility(View.GONE);
                        fragmentImageCommentsBottomSheetBinding.noCommentrel.setVisibility(View.VISIBLE);
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
