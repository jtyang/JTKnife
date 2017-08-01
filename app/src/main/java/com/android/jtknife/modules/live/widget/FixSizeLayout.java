package com.android.jtknife.modules.live.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class FixSizeLayout extends LinearLayout {
    private int fixWidth = 0;
    private int fixHeight = 0;

    public FixSizeLayout(Context context) {
        super(context);
    }

    public FixSizeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixSizeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FixSizeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(fixWidth > 0 && fixHeight > 0) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(fixWidth, MeasureSpec.AT_MOST);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(fixHeight, MeasureSpec.AT_MOST);
//            setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setFixSize(int width, int height) {
        fixWidth = width;
        fixHeight = height;
        requestLayout();
    }

}
