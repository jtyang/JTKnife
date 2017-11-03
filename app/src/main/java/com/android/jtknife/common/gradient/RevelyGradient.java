package com.android.jtknife.common.gradient;

import android.animation.ValueAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.jtknife.common.gradient.drawables.Gradient;

import java.util.List;

/**
 * 文件描述
 * source from:https://github.com/revely-inc/co.revely.gradient
 * AUTHOR: yangjiantong
 * DATE: 2017/11/3
 */
public class RevelyGradient {

    private Gradient.Type type;
    private float radius;
    public Gradient gradient;

    public RevelyGradient(Gradient.Type type, float radius) {
        this.type = type;
        this.radius = radius;
        gradient = new Gradient(type, radius);
    }

//    private lateinit var applyGradient: (() -> Unit)
//    private var onUpdate: ((valueAnimator: ValueAnimator, gradient: Gradient) -> Unit)? = null


    public interface IAnimatorUpdateListener {
        void onUpdate(ValueAnimator valueAnimator, Gradient gradient);
    }

    public static RevelyGradient linear() {
        return new RevelyGradient(Gradient.Type.LINEAR, 0.0f);
    }

    public static RevelyGradient radial(float radius) {
        return new RevelyGradient(Gradient.Type.RADIAL, radius);
    }

    public static RevelyGradient sweep() {
        return new RevelyGradient(Gradient.Type.SWEEP, 0.0f);
    }

    public static LayerGradient layer(List<RevelyGradient> gradients) {
        return new LayerGradient(gradients);
    }

//    companion object
//    {
//        @JvmStatic
//        fun linear(): RevelyGradient = RevelyGradient(Gradient.Type.LINEAR)
//        @JvmStatic
//        fun radial(radius: Float? = null): RevelyGradient = RevelyGradient(Gradient.Type.RADIAL, radius)
//        @JvmStatic
//        fun sweep(): RevelyGradient = RevelyGradient(Gradient.Type.SWEEP)
//        @JvmStatic
//        fun layer(vararg gradients: RevelyGradient): LayerGradient = LayerGradient(gradients)
//    }

    public void on(final TextView view) {
        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                gradient.getPaint(view.getWidth(), view.getHeight(), true);
                view.getPaint().setShader(gradient.shader);
                view.invalidate();
            }
        });
//        view.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ -> applyGradient() }
//        applyGradient = {
//                gradient.getPaint(view.width, view.height, true)
//                view.paint.shader = gradient.shader
//                view.invalidate()
//        }
    }

    public void on(ImageView view) {
//        applyGradient = { view.setImageDrawable(gradient) }
//        applyGradient()
        view.setImageDrawable(gradient);
    }

//    public void on(applyGradient: ((gradient: Gradient) -> Unit))
//    {
//        this.applyGradient = { applyGradient(gradient) }
//        this.applyGradient()
//    }

    public void onBackgroundOf(View view) {
//        applyGradient = {
//                @Suppress("DEPRECATION")
//                        view.setBackgroundDrawable(gradient)
//        }
//        applyGradient()
        view.setBackgroundDrawable(gradient);
    }

    public RevelyGradient colors(int[] colors) {
        gradient.setClolors(colors);
        return this;
    }

    public RevelyGradient offsets(float[] offsets) {
        gradient.setOffsets(offsets);
        return this;
    }

    public RevelyGradient alpha(float alpha) {
        gradient.setAlpha((int) (alpha * 255));
        return this;
    }

    public RevelyGradient center(int x, int y) {
        gradient.center(x, y);
        return this;
    }

    public RevelyGradient angle(float angle) {
        gradient.setAngle(angle);
        return this;
    }

    private RevelyGradient scale(float x, float y) {
        gradient.scale(x, y);
        return this;
    }

    //    public RevelyGradient animate(ValueAnimator valueAnimator, onUpdate: (ValueAnimator valueAnimator2, Gradient gradient) -> Unit)
    public RevelyGradient animate(ValueAnimator valueAnimator, final IAnimatorUpdateListener animatorUpdateListener) {
//        this.onUpdate = onUpdate
//        valueAnimator.addUpdateListener { _valueAnimator ->
//            this.onUpdate!!(_valueAnimator, gradient)
//        gradient.rebuild()

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (animatorUpdateListener != null) {
                    animatorUpdateListener.onUpdate(animation, gradient);
                }
                gradient.rebuild();
            }
        });
        return this;
    }

}
