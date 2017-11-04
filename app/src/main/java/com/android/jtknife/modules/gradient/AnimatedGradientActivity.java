package com.android.jtknife.modules.gradient;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseActivity;
import com.android.jtknife.common.gradient.RevelyGradient;
import com.android.jtknife.common.gradient.drawables.Gradient;

import butterknife.Bind;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/11/4
 */
public class AnimatedGradientActivity extends BaseActivity {

    @Bind(R.id.container)
    View container;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_animated_gradient;
    }

    @Override
    protected void onInitView() {

        int color1 = Color.parseColor("#00c6ff");
        int color2 = Color.parseColor("#0072ff");

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 360f);
        valueAnimator.setDuration(15000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        RevelyGradient.sweep()
                .colors(new int[]{color1, color2, color1})
                .center(540, 400)
//                .animate(valueAnimator, { _valueAnimator, _gradientDrawable ->
//                        _gradientDrawable.angle = _valueAnimator.animatedValue as Float
//                })
                .animate(valueAnimator, new RevelyGradient.IAnimatorUpdateListener() {
                    @Override
                    public void onUpdate(ValueAnimator valueAnimator, Gradient gradient) {
                        gradient.setAngle((Float) valueAnimator.getAnimatedValue());
                    }
                })
                .onBackgroundOf(container);
        valueAnimator.start();
    }

}
