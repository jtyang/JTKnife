package com.android.jtknife.widgets.barrage;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.android.jtknife.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/2/8
 */
public class BarrageView extends FrameLayout {
    private int rowHeight;
    private int rowSpace;
    private int duration;
    private BarrageData z;

    public BarrageView(Context context) {
        super(context);
        init(context, null);
    }

    public BarrageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public BarrageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    public BarrageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.z = new BarrageData();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BarrageView);
        this.rowHeight = obtainStyledAttributes.getDimensionPixelSize(R.styleable.BarrageView_barrage_row_height, getResources().getDimensionPixelOffset(R.dimen.barrage_row_height));
        this.rowSpace = obtainStyledAttributes.getDimensionPixelSize(R.styleable.BarrageView_barrage_row_space, getResources().getDimensionPixelOffset(R.dimen.barrage_row_space));
        this.duration = obtainStyledAttributes.getInt(R.styleable.BarrageView_barrage_duration, 4000);
        obtainStyledAttributes.recycle();
    }

    public int z(int i) {
        return (this.rowHeight + this.rowSpace) * i;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.UNSPECIFIED);
        int makeMeasureSpec2 = MeasureSpec.makeMeasureSpec(this.rowHeight, MeasureSpec.EXACTLY);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(makeMeasureSpec, makeMeasureSpec2);
        }
    }

    private void z(BarrageHolder barrageHolder) {
        removeView(barrageHolder.getItemView());
        this.z.z(barrageHolder);
    }

    public void z(BarrageHolder barrageHolder, int i) {
        View view = barrageHolder.getItemView();
        view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, this.rowHeight));
        addView(view);
        barrageHolder.z(i);
    }

    public void release() {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).clearAnimation();
        }
        removeAllViews();
        this.z.z();
    }

    public <T extends BarrageHolder> T z(Class<? extends BarrageHolder> cls) {
        T z = (T) this.z.z(cls);
        if (z != null) {
            return z;
        }
        T t = null;
        try {
            t = (T) cls.newInstance();
//            t.z(t.onCreateViewHolder(getContext(), this));
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return t;
    }

    public class BarrageData {
        private Map<Class<? extends BarrageHolder>, ArrayList<BarrageHolder>> y = new HashMap();
        private Map<Class<? extends BarrageHolder>, Integer> z = new HashMap();

        public void z() {
            this.y.clear();
        }

        public BarrageHolder z(Class<? extends BarrageHolder> cls) {
            ArrayList arrayList = (ArrayList) this.y.get(cls);
            if (arrayList == null || arrayList.isEmpty()) {
                return null;
            }
            int size = arrayList.size() - 1;
            BarrageHolder barrageView$z = (BarrageHolder) arrayList.get(size);
            arrayList.remove(size);
            return barrageView$z;
        }

        public void z(BarrageHolder barrageView$z) {
            Class cls = barrageView$z.getClass();
            ArrayList z = z(cls, barrageView$z.u());
            if (((Integer) this.z.get(cls)).intValue() > z.size()) {
                barrageView$z.w();
                z.add(barrageView$z);
            }
        }

        private ArrayList<BarrageHolder> z(Class<? extends BarrageHolder> cls, int i) {
            ArrayList<BarrageHolder> arrayList = (ArrayList) this.y.get(cls);
            if (arrayList == null) {
                arrayList = new ArrayList();
                this.y.put(cls, arrayList);
                if (!this.z.containsKey(cls)) {
                    this.z.put(cls, Integer.valueOf(i));
                }
            }
            return arrayList;
        }
    }

}
