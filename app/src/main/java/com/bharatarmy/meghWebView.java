package com.bharatarmy;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

public class meghWebView extends WebView {
    public meghWebView(Context context)
    {
        this(context, null);
    }

    public meghWebView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public meghWebView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public OnBottomReachedListener mOnBottomReachedListener = null;
    private int mMinDistance = 0;

    /**
     * Set the listener which will be called when the WebView is scrolled to within some
     * margin of the bottom.
     * @param bottomReachedListener
     * @param allowedDifference
     */
    public void setOnBottomReachedListener(OnBottomReachedListener bottomReachedListener, int allowedDifference ) {
        mOnBottomReachedListener = bottomReachedListener;
        mMinDistance = allowedDifference;
    }

    /**
     * Implement this interface if you want to be notified when the WebView has scrolled to the bottom.
     */
    public interface OnBottomReachedListener {
        void onBottomReached(View v);

//        void onScroll(int l, int t);
    }

    @Override
    protected void onScrollChanged(int left, int top, int oldLeft, int oldTop) {
        if ( mOnBottomReachedListener != null ) {
//            if ( (getContentHeight() - (top + getHeight())) <= mMinDistance )
//                mOnBottomReachedListener.onScroll();
            int height = (int) Math.floor(this.getContentHeight() * this.getScale());
            int webViewHeight = this.getMeasuredHeight();
            if(this.getScrollY() + webViewHeight >= height){
                Log.i("THE END", "reached");
                mOnBottomReachedListener.onBottomReached(this);
            }
        }
        super.onScrollChanged(left, top, oldLeft, oldTop);
    }

}
