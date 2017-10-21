package com.android.jtknife.core.widgets.banner_v2;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import com.android.jtknife.core.widgets.banner.BannerConfig;

/**
 * 调整viewpager的滑动速度
 * 由于ViewPager 默认的切换速度有点快，因此用一个Scroller 来控制切换的速度
 * AUTHOR: yangjiantong
 * DATE: 2017/2/16
 */
public class ViewPageScroller extends Scroller {
    private int mDuration = BannerConfig.DURATION;

    public ViewPageScroller(Context context) {
        super(context);
    }

    public ViewPageScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public ViewPageScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    public void setDuration(int time) {
        this.mDuration = time;
    }
}
