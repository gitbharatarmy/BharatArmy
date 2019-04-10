package com.bharatarmy.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;



public class VectorDrawableUtils {
    public static final VectorDrawableUtils INSTANCE;

    @Nullable
    public final Drawable getDrawable(Context context, int drawableResId) {

        return (Drawable) VectorDrawableCompat.create(context.getResources(), drawableResId, context.getTheme());
    }


    public final Drawable getDrawable(Context context, int drawableResId, int colorFilter) {
        Drawable drawable = this.getDrawable(context, drawableResId);
        if (drawable == null) {

        }

        drawable.setColorFilter(colorFilter, PorterDuff.Mode.SRC_IN);
        return drawable;
    }


    public final Bitmap getBitmap(Context context, int drawableId) {

        Drawable drawable = this.getDrawable(context, drawableId);
        if (drawable == null) {

        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    static {
        VectorDrawableUtils var0 = new VectorDrawableUtils();
        INSTANCE = var0;
    }
}
