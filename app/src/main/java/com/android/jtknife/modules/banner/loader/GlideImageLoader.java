package com.android.jtknife.modules.banner.loader;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.android.jtknife.core.widgets.banner.loader.BannerImageLoader;
import com.bumptech.glide.Glide;
import com.elvishew.xlog.XLog;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/2/16
 */
public class GlideImageLoader extends BannerImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Uri uri = Uri.parse((String) path);
        XLog.i("uri=" + uri);
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
        Glide.with(context.getApplicationContext())
                .load(uri)
                .crossFade()
                .into(imageView);
    }
}
