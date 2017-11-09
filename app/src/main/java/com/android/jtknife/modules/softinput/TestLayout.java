package com.android.jtknife.modules.softinput;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/11/9
 */
public class TestLayout  extends FrameLayout {
    private int mL,mT,mR,mB;
    private boolean isFist = true;

    public TestLayout(Context context) {
        super(context);
    }

    public TestLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (isFist){
            mL = l;
            mT=t;
            mR=r;
            mB=b;
            isFist =false;
        }
        getChildAt(0).layout(mL, mT, mR, mB);
    }
}