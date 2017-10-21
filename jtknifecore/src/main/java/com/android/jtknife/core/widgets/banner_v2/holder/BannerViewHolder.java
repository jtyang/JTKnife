package com.android.jtknife.core.widgets.banner_v2.holder;

import android.content.Context;
import android.view.View;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/10/21
 */
public interface BannerViewHolder<T> {
    /**
     * 创建View
     *
     * @param context
     * @return
     */
    View createView(Context context);

    /**
     * 绑定数据
     *
     * @param context
     * @param position
     * @param data
     */
    void onBind(Context context, int position, T data);
}
