package com.android.jtknife.common.gradient;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;

import java.util.List;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/11/3
 */
public class LayerGradient {
//    var gradients: Array<out RevelyGradient>

    private List<RevelyGradient> gradients;
    public LayerDrawable drawable;

    public LayerGradient(List<RevelyGradient> gradients) {
        this.gradients = gradients;
        if (gradients != null) {
            Drawable[] drawables = new Drawable[gradients.size()];
            for (int i = 0; i < gradients.size(); i++) {
                drawables[i] = gradients.get(i).gradient;
            }
            drawable = new LayerDrawable(drawables);
        }
    }

    //    var drawable: LayerDrawable = LayerDrawable(gradients.map {revelyGradient -> revelyGradient.gradient }.toTypedArray())
//    public LayerDrawable drawable = new LayerDrawable(gradients.toArray());

    public void onBackgroundOf(View view) {
        view.setBackgroundDrawable(drawable);
    }
}
