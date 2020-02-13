package com.bharatarmy;

import android.widget.ImageView;

import com.squareup.picasso.Callback;


public abstract class TargetCallback implements Callback {
    private ImageView mTarget;

    public abstract void onSuccess(ImageView target);
    public abstract void onError(ImageView target);
    public TargetCallback(ImageView imageView){
        mTarget = imageView;
    }
    @Override
    public void onSuccess() {
        onSuccess(mTarget);
    }

    @Override
    public void onError() {
        onError(mTarget);
    }

}
