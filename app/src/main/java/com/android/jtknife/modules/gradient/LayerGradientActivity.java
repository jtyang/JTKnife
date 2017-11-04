package com.android.jtknife.modules.gradient;

import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseActivity;
import com.android.jtknife.common.gradient.RevelyGradient;
import com.android.jtknife.common.gradient.drawables.Gradient;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/11/4
 */
public class LayerGradientActivity extends BaseActivity {

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_layer_gradient;
    }

    @Override
    protected void onInitView() {

        // view_instagram
        List<RevelyGradient> list1 = new ArrayList<>();
        RevelyGradient rg1 = RevelyGradient
                .radial(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150f, Resources.getSystem().getDisplayMetrics()))
                .colors(new int[]{Color.parseColor("#ffdd55"), Color.parseColor("#ffdd55"), Color.parseColor("#ff543e"), Color.parseColor("#c837ab")})
                .offsets(new float[]{0f, 0.1f, 0.5f, 1f})
                .center(50, 400);
        RevelyGradient rg2 = RevelyGradient
                .radial(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 170f, Resources.getSystem().getDisplayMetrics()))
                .colors(new int[]{Color.parseColor("#3771c8"), Color.parseColor("#3771c8"), Color.parseColor("#006600ff")})
                .offsets(new float[]{0f, 0.128f, 1f})
                .angle(-15f)
                .scale(1f, 0.4f)
                .center(0, 0);
        list1.add(rg1);
        list1.add(rg2);
        RevelyGradient.layer(list1).onBackgroundOf(findViewById(R.id.view_instagram));

        // view_instagram_animated
        ValueAnimator valueAnimator1 = ValueAnimator.ofFloat(-1f, 1f);
        valueAnimator1.setDuration(1000);
        valueAnimator1.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator1.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator1.setInterpolator(new BounceInterpolator());

        ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(0f, 360f);
        valueAnimator1.setDuration(15000);
        valueAnimator1.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator1.setInterpolator(new LinearInterpolator());

        List<RevelyGradient> list2 = new ArrayList<>();
        RevelyGradient rg3 = RevelyGradient
                .radial(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150f, Resources.getSystem().getDisplayMetrics()))
                .colors(new int[]{Color.parseColor("#ffdd55"), Color.parseColor("#ffdd55"), Color.parseColor("#ff543e"), Color.parseColor("#c837ab")})
                .offsets(new float[]{0f, 0.1f, 0.5f, 1f})
                .center(50, 400)
//                .animate(valueAnimator1, {_valueAnimator, _gradientDrawable ->
//                        val x = (50 + Math.cos((_valueAnimator.animatedValue as Float).toDouble()) * 300).toFloat()
//        val y = (200 + Math.sin((_valueAnimator.animatedValue as Float).toDouble()) * 200).toFloat()
//        _gradientDrawable.center(x, y)
//                        } );
                .animate(valueAnimator1, new RevelyGradient.IAnimatorUpdateListener() {
                    @Override
                    public void onUpdate(ValueAnimator _valueAnimator, Gradient _gradientDrawable) {
                        float x = (float) (50 + Math.cos((float) _valueAnimator.getAnimatedValue()) * 300);
                        float y = (float) (200 + Math.sin((float) _valueAnimator.getAnimatedValue()) * 200);
                        _gradientDrawable.center(x, y);
                    }
                });
        RevelyGradient rg4 = RevelyGradient
                .radial(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 170f, Resources.getSystem().getDisplayMetrics()))
                .colors(new int[]{Color.parseColor("#3771c8"), Color.parseColor("#3771c8"), Color.parseColor("#006600ff")})
                .offsets(new float[]{0f, 0.128f, 1f})
                .angle(-15f)
                .scale(1f, 0.4f)
                .center(0, 0)
//                .animate(valueAnimator2, {_valueAnimator, _gradientDrawable ->
//                        _gradientDrawable.angle = _valueAnimator.animatedValue as Float
                .animate(valueAnimator2, new RevelyGradient.IAnimatorUpdateListener() {
                    @Override
                    public void onUpdate(ValueAnimator _valueAnimator, Gradient _gradientDrawable) {
                        _gradientDrawable.setAngle((Float) _valueAnimator.getAnimatedValue());
                    }
                });
        list2.add(rg3);
        list2.add(rg4);
        RevelyGradient.layer(
                list2
        ).onBackgroundOf(findViewById(R.id.view_instagram_animated));
        valueAnimator1.start();
        valueAnimator2.start();
    }
}
