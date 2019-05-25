package com.bharatarmy.TravelDesignModule;

public interface ObservableScrollViewCallbacks {
    void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging);

    /**
     * Called when the down motion event occurred.
     */
    void onDownMotionEvent();

    /**
     * Called when the dragging ended or canceled.
     *
     * @param scrollState State to indicate the scroll direction.
     */
    void onUpOrCancelMotionEvent(ScrollState scrollState);
}
