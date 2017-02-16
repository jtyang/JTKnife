package com.android.jtknife.modules.banner.loader;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.android.jtknife.core.widgets.banner.loader.BannerImageLoader;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/2/16
 */
public class FrescoImageLoader extends BannerImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //用fresco加载图片
        Uri uri = Uri.parse((String) path);
        imageView.setImageURI(uri);
    }

    //提供createImageView 方法，方便fresco自定义ImageView
    @Override
    public ImageView createImageView(Context context) {
        SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
        return simpleDraweeView;
    }
}