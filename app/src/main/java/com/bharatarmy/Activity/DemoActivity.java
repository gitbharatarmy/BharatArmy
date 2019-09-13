package com.bharatarmy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.bharatarmy.Adapter.SignupAdapter;
import com.bharatarmy.Adapter.UpcomingDashboardAdapter;
import com.bharatarmy.R;
import com.bharatarmy.databinding.ActivityDemoBinding;

import java.util.ArrayList;

/*initVivoBgFallback
* /Background Fallback: initVivoB Fallback callstack*/
public class DemoActivity extends AppCompatActivity {
ActivityDemoBinding activityDemoBinding;
Context mContext;
    SignupAdapter signupAdapter;
    ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDemoBinding= DataBindingUtil. setContentView(this,R.layout.activity_demo);

        mContext=DemoActivity.this;
        list=new ArrayList<>();
        list.add("1");
//        signupAdapter=new SignupAdapter(mContext,list);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
//        activityDemoBinding.signupRcv.setLayoutManager(mLayoutManager);
//        activityDemoBinding.signupRcv.setItemAnimator(new DefaultItemAnimator());
//        activityDemoBinding.signupRcv.setAdapter(signupAdapter);


        activityDemoBinding.fulluserNameEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityDemoBinding.scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        View lastChild =  activityDemoBinding.scrollView.getChildAt( activityDemoBinding.scrollView.getChildCount() - 1);
                        int bottom = lastChild.getBottom() +  activityDemoBinding.scrollView.getPaddingBottom();
                        int sy =  activityDemoBinding.scrollView.getScrollY();
                        int sh =  activityDemoBinding.scrollView.getHeight();
                        int delta = bottom - (sy + sh);
                        activityDemoBinding.scrollView.smoothScrollBy(0, delta);
                    }
                }, 200);
            }
        });
    }
}
