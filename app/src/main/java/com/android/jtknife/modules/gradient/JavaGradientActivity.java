package com.android.jtknife.modules.gradient;

import android.graphics.Color;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseActivity;
import com.android.jtknife.common.gradient.RevelyGradient;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/11/4
 */
public class JavaGradientActivity extends BaseActivity {

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_java_gradient;
    }

    @Override
    protected void onInitView() {

        RevelyGradient
                .linear()
                .colors(new int[] {Color.parseColor("#FF2525"), Color.parseColor("#6078EA")})
                .onBackgroundOf(findViewById(R.id.container));

        RevelyGradient
                .linear()
                .colors(new int[] {Color.parseColor("#AA000000"), Color.parseColor("#00000000")})
                .angle(90)
                .onBackgroundOf(findViewById(R.id.view));
    }
}
