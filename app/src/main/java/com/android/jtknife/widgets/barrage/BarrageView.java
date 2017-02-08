//package com.android.jtknife.widgets.barrage;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.support.annotation.LayoutRes;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewTreeObserver;
//import android.view.animation.Animation;
//import android.view.animation.LinearInterpolator;
//import android.view.animation.Transformation;
//import android.view.animation.TranslateAnimation;
//import android.widget.FrameLayout;
//
//import com.android.jtknife.R;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//
///**
// * 文件描述
// * AUTHOR: yangjiantong
// * DATE: 2017/2/8
// */
//public class BarrageView extends FrameLayout {
//    private int w;
//    private int x;
//    private int y;
//    private y2 z;
//
//    public BarrageView(Context context) {
//        super(context);
//        init(context, null);
//    }
//
//    public BarrageView(Context context, AttributeSet attributeSet) {
//        super(context, attributeSet);
//        init(context, attributeSet);
//    }
//
//    public BarrageView(Context context, AttributeSet attributeSet, int i) {
//        super(context, attributeSet, i);
//        init(context, attributeSet);
//    }
//
//    public BarrageView(Context context, AttributeSet attributeSet, int i, int i2) {
//        super(context, attributeSet, i, i2);
//        init(context, attributeSet);
//    }
//
//    private void init(Context context, AttributeSet attributeSet) {
//        this.z = new y2();
//        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BarrageView);
//        this.y = obtainStyledAttributes.getDimensionPixelSize(R.styleable.BarrageView_barrage_row_height, getResources().getDimensionPixelOffset(R.dimen.barrage_row_height));
//        this.x = obtainStyledAttributes.getDimensionPixelSize(R.styleable.BarrageView_barrage_row_space, getResources().getDimensionPixelOffset(R.dimen.barrage_row_space));
//        this.w = obtainStyledAttributes.getInt(R.styleable.BarrageView_barrage_duration, 4000);
//        obtainStyledAttributes.recycle();
//    }
//
//    public <T extends BarrageHolder> T z(Class<? extends BarrageHolder> cls) {
//        Exception e;
//        T z = this.z.z(cls);
//        if (z != null) {
//            return z;
//        }
//        T t;
//        try {
//            t = (BarrageHolder) cls.newInstance();
//            try {
//                t.z(t.z(getContext(), this));
//                return t;
//            } catch (Exception e2) {
//                e = e2;
//            }
//        } catch (Exception e3) {
//            Exception exception = e3;
//            t = z;
//            e = exception;
//            e.printStackTrace();
//            return t;
//        }
//    }
//
//    public int z(int i) {
//        return (this.y + this.x) * i;
//    }
//
//    protected void onMeasure(int i, int i2) {
//        int i3 = 0;
//        super.onMeasure(i, i2);
//        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 0);
//        int makeMeasureSpec2 = MeasureSpec.makeMeasureSpec(this.y, 1073741824);
//        while (i3 < getChildCount()) {
//            getChildAt(i3).measure(makeMeasureSpec, makeMeasureSpec2);
//            i3++;
//        }
//    }
//
//    private void z(BarrageHolder zVar) {
//        removeView(zVar.z());
//        this.z.z(zVar);
//    }
//
//    public void z() {
//        for (int i = 0; i < getChildCount(); i++) {
//            getChildAt(i).clearAnimation();
//        }
//        removeAllViews();
//        this.z.z();
//    }
//
//    public void z(BarrageHolder zVar, int i) {
//        View z = zVar.z();
//        z.setLayoutParams(new LayoutParams(-2, this.y));
//        addView(z);
//        zVar.z(i);
//    }
//
//
//    public class y {
//        private Map<Class<? extends BarrageHolder>, ArrayList<BarrageHolder>> y = new HashMap();
//        private Map<Class<? extends BarrageHolder>, Integer> z = new HashMap();
//
//        public void z() {
//            this.y.clear();
//        }
//
//        public BarrageHolder z(Class<? extends BarrageHolder> cls) {
//            ArrayList arrayList = (ArrayList) this.y.get(cls);
//            if (arrayList == null || arrayList.isEmpty()) {
//                return null;
//            }
//            int size = arrayList.size() - 1;
//            BarrageHolder barrageView$z = (BarrageHolder) arrayList.get(size);
//            arrayList.remove(size);
//            return barrageView$z;
//        }
//
//        public void z(BarrageHolder barrageView$z) {
//            Class cls = barrageView$z.getClass();
//            ArrayList z = z(cls, barrageView$z.u());
//            if (((Integer) this.z.get(cls)).intValue() > z.size()) {
//                barrageView$z.w();
//                z.add(barrageView$z);
//            }
//        }
//
//        private ArrayList<BarrageHolder> z(Class<? extends BarrageHolder> cls, int i) {
//            ArrayList<BarrageHolder> arrayList = (ArrayList) this.y.get(cls);
//            if (arrayList == null) {
//                arrayList = new ArrayList();
//                this.y.put(cls, arrayList);
//                if (!this.z.containsKey(cls)) {
//                    this.z.put(cls, Integer.valueOf(i));
//                }
//            }
//            return arrayList;
//        }
//    }
//
//    public abstract class BarrageHolder {
//        private float v;
//        private BarrageAnimationListener w;
//        private BarrageView x;
//        private TranslateAnimation y;
//        private View z;
//
//        @LayoutRes
//        public abstract int v();
//
//        public abstract void z(View view);
//
//        View z(Context context, BarrageView barrageView) {
//            this.x = barrageView;
//            this.z = LayoutInflater.from(context).inflate(v(), barrageView, false);
//            z(this.z);
//            return this.z;
//        }
//
//        public View z() {
//            return this.z;
//        }
//
//        void z(int i) {
//            this.v = 0.0f;
//            this.z.getViewTreeObserver().addOnPreDrawListener(new y(this, this.x.z(i)));
//        }
//
//        public boolean y() {
//            return false;
//        }
//
//        void x() {
//            BarrageView.z(this.x, this);
//        }
//
//        void w() {
//        }
//
//        public void z(BarrageAnimationListener zVar) {
//            this.w = zVar;
//        }
//
//        public int u() {
//            return 5;
//        }
//
//        public int a() {
//            return (int) this.v;
//        }
//    }
//
//    class y2 implements ViewTreeObserver.OnPreDrawListener {
//        final /* synthetic */ BarrageHolder y;
//        final /* synthetic */ int z;
//
//        y2(BarrageHolder barrageView$z, int i) {
//            this.y = barrageView$z;
//            this.z = i;
//        }
//
//        public boolean onPreDraw() {
//            this.y.z.getViewTreeObserver().removeOnPreDrawListener(this);
//            int width = this.y.x.getWidth();
//            int width2 = this.y.z.getWidth();
//            this.y.y = new x(this, (float) width, (float) (-width2), (float) this.z, (float) this.z, width, width2);
//            int z = (int) (((float) (width + width2)) / ((((float) width) * 1.0f) / ((float) BarrageView.z(this.y.x))));
//            this.y.y.setInterpolator(new LinearInterpolator());
//            this.y.y.setDuration((long) z);
//            this.y.y.setAnimationListener(new w(this));
//            this.y.z.startAnimation(this.y.y);
//            return false;
//        }
//    }
//
//
//    class w implements Animation.AnimationListener {
//        final /* synthetic */ y2 z;
//
//        w(y2 yVar) {
//            this.z = yVar;
//        }
//
//        public void onAnimationStart(Animation animation) {
//            if (this.z.y.w != null) {
//                this.z.y.w.z(this.z.y);
//            }
//        }
//
//        public void onAnimationEnd(Animation animation) {
//            this.z.y.x();
//            if (this.z.y.w != null) {
//                this.z.y.w.y(this.z.y);
//            }
//        }
//
//        public void onAnimationRepeat(Animation animation) {
//        }
//    }
//
//    class x extends TranslateAnimation {
//        final /* synthetic */ y2 x;
//        final /* synthetic */ int y;
//        final /* synthetic */ int z;
//
//        x(y2 yVar, float f, float f2, float f3, float f4, int i, int i2) {
//            this.x = yVar;
//            this.z = i;
//            this.y = i2;
//            super(f, f2, f3, f4);
//        }
//
//        protected void applyTransformation(float f, Transformation transformation) {
//            super.applyTransformation(f, transformation);
//            float[] fArr = new float[9];
//            transformation.getMatrix().getValues(fArr);
//            this.x.y.v = ((float) this.z) - (fArr[2] + ((float) this.y));
//        }
//    }
//
//}
