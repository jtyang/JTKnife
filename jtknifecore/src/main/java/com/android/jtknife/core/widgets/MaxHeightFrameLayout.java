package com.android.jtknife.core.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.android.jtknife.core.utils.DrawUtil;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/1/11
 */
public class MaxHeightFrameLayout extends FrameLayout {
    private boolean y = false;
    private Context z;

    public MaxHeightFrameLayout(Context context) {
        super(context);
        z(context);
    }

    public void setIsSpecificHeight(boolean z) {
        this.y = z;
    }

    public MaxHeightFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        z(context);
    }

    public MaxHeightFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        z(context);
    }

    private void z(Context context) {
        this.z = context;
    }

    public void onMeasure(int i, int i2) {
        if (!this.y) {
            i2 = MeasureSpec.makeMeasureSpec((int) (((float) DrawUtil.y(this.z)) * 0.5f), MeasureSpec.AT_MOST);
        }
        super.onMeasure(i, i2);
    }

}
