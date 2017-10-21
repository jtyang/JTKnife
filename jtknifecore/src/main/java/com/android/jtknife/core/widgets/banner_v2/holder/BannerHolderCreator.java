package com.android.jtknife.core.widgets.banner_v2.holder;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/10/21
 */
public interface BannerHolderCreator<VH extends BannerViewHolder> {
    /**
     * 创建ViewHolder
     *
     * @return
     */
    VH createViewHolder();
}
