package com.android.jtknife.core.common.stackblur;

import android.graphics.Bitmap;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/1/13
 *
 * https://github.com/kikoso/android-stackblur
 */
public interface BlurProcess {
    /**
     * Process the given image, blurring by the supplied radius.
     * If radius is 0, this will return original
     *
     * @param original the bitmap to be blurred
     * @param radius   the radius in pixels to blur the image
     * @return the blurred version of the image.
     */
    public Bitmap blur(Bitmap original, float radius);
}