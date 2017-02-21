package com.android.jtknife.core.common.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.android.jtknife.core.common.stackblur.NativeBlurProcess;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.imagepipeline.request.BasePostprocessor;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/2/21
 */
public class BlurPostprocessor extends BasePostprocessor {

    private static int MAX_RADIUS = 25;
    private static int DEFAULT_DOWN_SAMPLING = 1;

    private Context context;
    private int radius;
    private int sampling;
    private NativeBlurProcess nativeBlurProcess;

    public BlurPostprocessor(Context context) {
        this(context, MAX_RADIUS, DEFAULT_DOWN_SAMPLING);
    }

    public BlurPostprocessor(Context context, int radius) {
        this(context, radius, DEFAULT_DOWN_SAMPLING);
    }

    public BlurPostprocessor(Context context, int radius, int sampling) {
        this.context = context.getApplicationContext();
        this.radius = radius;
        this.sampling = sampling;
        nativeBlurProcess = new NativeBlurProcess();
    }

    @Override
    public void process(Bitmap dest, Bitmap source) {

        int width = source.getWidth();
        int height = source.getHeight();
        int scaledWidth = width / sampling;
        int scaledHeight = height / sampling;

        Bitmap blurredBitmap = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(blurredBitmap);
        canvas.scale(1 / (float) sampling, 1 / (float) sampling);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(source, 0, 0, paint);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            try {
//                blurredBitmap = RSBlur.blur(context, blurredBitmap, radius);
//            } catch (android.renderscript.RSRuntimeException e) {
//                blurredBitmap = FastBlur.blur(blurredBitmap, radius, true);
//            }
//        } else {
//            blurredBitmap = FastBlur.blur(blurredBitmap, radius, true);
//        }
        blurredBitmap = nativeBlurProcess.blur(blurredBitmap, radius);

        Bitmap scaledBitmap =
                Bitmap.createScaledBitmap(blurredBitmap, dest.getWidth(), dest.getHeight(), true);
        blurredBitmap.recycle();

        super.process(dest, scaledBitmap);
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public CacheKey getPostprocessorCacheKey() {
        return new SimpleCacheKey("radius=" + radius + ",sampling=" + sampling);
    }
}
