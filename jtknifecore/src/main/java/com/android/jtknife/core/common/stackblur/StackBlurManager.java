package com.android.jtknife.core.common.stackblur;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.FileOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/1/13
 */
public class StackBlurManager {
    static final int EXECUTOR_THREADS = Runtime.getRuntime().availableProcessors();
    static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(EXECUTOR_THREADS);

    private static volatile boolean hasRS = true;

    /**
     * Original image
     */
    private final Bitmap _image;

    /**
     * Most recent result of blurring
     */
    private Bitmap _result;

    /**
     * Method of blurring
     */
    private final BlurProcess _blurProcess;

    /**
     * Constructor method (basic initialization and construction of the pixel array)
     * @param image The image that will be analyed
     */
    public StackBlurManager(Bitmap image) {
        _image = image;
        _blurProcess = new JavaBlurProcess();
    }

    /**
     * Process the image on the given radius. Radius must be at least 1
     * @param radius
     */
    public Bitmap process(int radius) {
        _result = _blurProcess.blur(_image, radius);
        return _result;
    }

    /**
     * Returns the blurred image as a bitmap
     * @return blurred image
     */
    public Bitmap returnBlurredImage() {
        return _result;
    }

    /**
     * Save the image into the file system
     * @param path The path where to save the image
     */
    public void saveIntoFile(String path) {
        try {
            FileOutputStream out = new FileOutputStream(path);
            _result.compress(Bitmap.CompressFormat.PNG, 90, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the original image as a bitmap
     * @return the original bitmap image
     */
    public Bitmap getImage() {
        return this._image;
    }

    /**
     * Process the image using a native library
     */
    public Bitmap processNatively(int radius) {
        NativeBlurProcess blur = new NativeBlurProcess();
        _result = blur.blur(_image, radius);
        return _result;
    }

    /**
     * Process the image using renderscript if possible
     * Fall back to native if renderscript is not available
     * @param context renderscript requires an android context
     * @param radius
     */
    public Bitmap processRenderScript(Context context, float radius) {
        BlurProcess blurProcess = null;
        // The renderscript support library doesn't have .so files for ARMv6.
        // Remember if there is an error creating the renderscript context,
        // and fall back to NativeBlurProcess
        if(hasRS) {
//            try {
//                blurProcess = new RSBlurProcess(context);
//            } catch (RSRuntimeException e) {
//                if(BuildConfig.DEBUG) {
//                    Log.i("StackBlurManager", "Falling back to Native Blur", e);
//                }
//                blurProcess = new NativeBlurProcess();
//                hasRS = false;
//            }
        }
        else {
            blurProcess = new NativeBlurProcess();
        }
        _result = blurProcess.blur(_image, radius);
        return _result;
    }
}
