package com.android.jtknife.modules.gradient;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseActivity;
import com.android.jtknife.common.gradient.RevelyGradient;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/11/3
 */
public class TextGradientActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.app_bar)
    AppBarLayout appBar;
    @Bind(R.id.title_text)
    TextView titleText;
    @Bind(R.id.title_card)
    CardView titleCard;
    @Bind(R.id.title_text2)
    TextView titleText2;
    @Bind(R.id.title_card2)
    CardView titleCard2;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_text_gradient;
    }

    @Override
    protected void onInitView() {
        setSupportActionBar(toolbar);

//        RevelyGradient.linear()
//                .colors(resources.getIntArray(R.array.colorTest2))
//                .angle(40.0f)
//                .onBackgroundOf(app_bar)


        int[] colors1 = {Color.parseColor("#FFE78E"), Color.parseColor("#F28381")};
        RevelyGradient.linear()
                .colors(colors1)
                .on(titleText);

        int[] colors2 = {Color.parseColor("#00FF83"), Color.parseColor("#69FFFE")};
        RevelyGradient.linear()
                .angle(90f)
                .colors(colors2)
                .on(titleText2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
