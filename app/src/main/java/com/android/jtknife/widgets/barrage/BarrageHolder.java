package com.android.jtknife.widgets.barrage;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

import com.elvishew.xlog.XLog;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/2/8
 */
public abstract class BarrageHolder {

    private BarrageView barrageView;
    private View itemView;
    private TranslateAnimation y;
    private BarrageAnimationListener w;
    private float v;

    @LayoutRes
    public abstract int getLayoutResId();

    public abstract void onBindView(View view);

    public View onCreateViewHolder(Context context, BarrageView barrageView) {
        this.barrageView = barrageView;
        this.itemView = LayoutInflater.from(context).inflate(getLayoutResId(), barrageView, false);
        onBindView(this.itemView);
        return this.itemView;
    }

    public View getItemView() {
        return this.itemView;
    }

    public void z(int i) {
        this.v = 0.0f;
//        this.itemView.getViewTreeObserver().addOnPreDrawListener(new y(this, this.x.z(i)));
        this.itemView.getViewTreeObserver().addOnPreDrawListener(new MyPreDrawListener(this, i));
    }

    void x() {
//            BarrageView.z(this.x, this);
    }

    void w() {
    }

    public int u() {
        return 5;
    }

    class MyPreDrawListener implements ViewTreeObserver.OnPreDrawListener {
        final /* synthetic */ BarrageHolder barrageHolder;
        final /* synthetic */ int z;

        public MyPreDrawListener(BarrageHolder barrageHolder, int i) {
            this.barrageHolder = barrageHolder;
            this.z = i;
        }

        @Override
        public boolean onPreDraw() {
            this.barrageHolder.itemView.getViewTreeObserver().removeOnPreDrawListener(this);
            int width = this.barrageHolder.barrageView.getWidth();
            int width2 = this.barrageHolder.itemView.getWidth();
            XLog.i("width=" + width + ",width2=" + width2);//width=720,width2=253
            this.barrageHolder.y = new MyTranslateAnimation(this, (float) width, (float) (-width2), (float) this.z, (float) this.z, width, width2);
//            int z = (int) (((float) (width + width2)) / ((((float) width) * 1.0f) / ((float) BarrageView.z(this.barrageHolder.barrageView))));
            int z = 5000;//// TODO: 2017/2/8 change calc
            this.barrageHolder.y.setInterpolator(new LinearInterpolator());
            this.barrageHolder.y.setDuration((long) z);
            this.barrageHolder.y.setAnimationListener(new MyAnimationListener(this));
            this.barrageHolder.itemView.startAnimation(this.barrageHolder.y);
            return false;
        }
    }

    class MyTranslateAnimation extends TranslateAnimation {
        final /* synthetic */ MyPreDrawListener x;
        final /* synthetic */ int z;
        final /* synthetic */ int y;

        public MyTranslateAnimation(MyPreDrawListener myPreDrawListener, float fromXDelta, float toXDelta, float fromYDelta, float toYDelta, int i, int i2) {
            super(fromXDelta, toXDelta, fromYDelta, toYDelta);
            this.x = myPreDrawListener;
            this.z = i;
            this.y = i2;
        }

        protected void applyTransformation(float f, Transformation transformation) {
            super.applyTransformation(f, transformation);
            float[] fArr = new float[9];
            transformation.getMatrix().getValues(fArr);
            this.x.barrageHolder.v = ((float) this.z) - (fArr[2] + ((float) this.y));
            XLog.e("MyTranslateAnimation v=" + this.x.barrageHolder.v);
        }
    }

    class MyAnimationListener implements Animation.AnimationListener {
        final /* synthetic */ MyPreDrawListener myPreDrawListener;

        MyAnimationListener(MyPreDrawListener yVar) {
            this.myPreDrawListener = yVar;
        }

        public void onAnimationStart(Animation animation) {
            if (this.myPreDrawListener.barrageHolder.w != null) {
                this.myPreDrawListener.barrageHolder.w.onAnimationStart(this.myPreDrawListener.barrageHolder);
            }
        }

        public void onAnimationEnd(Animation animation) {
            this.myPreDrawListener.barrageHolder.x();
            if (this.myPreDrawListener.barrageHolder.w != null) {
                this.myPreDrawListener.barrageHolder.w.onAnimationEnd(this.myPreDrawListener.barrageHolder);
            }
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    public interface BarrageAnimationListener {

        void onAnimationEnd(BarrageHolder b);

        void onAnimationStart(BarrageHolder b);
    }
}
