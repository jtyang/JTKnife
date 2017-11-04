package com.android.jtknife.modules.gradient;

import android.graphics.Color;
import android.widget.ImageView;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseActivity;
import com.android.jtknife.common.gradient.RevelyGradient;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/11/4
 */
public class OtherGradientActivity extends BaseActivity {

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_other_gradient;
    }

    @Override
    protected void onInitView() {
        RevelyGradient
                .linear()
                .colors(new int[]{Color.parseColor("#FF2525"), Color.parseColor("#6078EA")})
                .alpha(0.3f)
                .on((ImageView) findViewById(R.id.gradient1));

        RevelyGradient
                .radial(0.2f)
                .colors(new int[]{Color.parseColor("#FFFFFF"), Color.parseColor("#FAACA8")})
                .alpha(0.3f)
                .angle(-45f)
                .on((ImageView) findViewById(R.id.gradient2));

        RevelyGradient
                .linear()
                .colors(new int[]{Color.parseColor("#000000"), Color.parseColor("#00FFFFFF")})
                .angle(-90f)
                .on((ImageView) findViewById(R.id.gradient3));

    }

}
