package com.android.jtknife.core.widgets.banner.loader;

import android.content.Context;
import android.view.View;

import java.io.Serializable;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/2/16
 */
public interface ImageLoaderInterface<T extends View> extends Serializable {

    void displayImage(Context context, Object path, T imageView);

    T createImageView(Context context);
}
