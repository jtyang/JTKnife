package com.android.jtknife.modules.live;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.jtknife.R;
import com.elvishew.xlog.XLog;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/2/6
 */
public class RoomEditChatView extends LinearLayout {

    @Bind(R.id.chat_input_edit)
    EditText chatInputEdit;
    @Bind(R.id.chat_input_layout)
    RelativeLayout chatInputLayout;
    @Bind(R.id.bottom_space_view)
    View bottomSpaceView;

    public RoomEditChatView(Context context) {
        super(context);
    }

    public RoomEditChatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RoomEditChatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        chatInputLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (this.activity != null) {
            this.d = WatchActivity.a(activity);
        }
    }

    int d = 0;
    private boolean e = false;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View childAt = getChildAt(0);
        View childAt2 = getChildAt(1);
        int[] iArr = new int[2];
        childAt.getLocationInWindow(iArr);
        int height = iArr[1] + childAt.getHeight();
        Rect rect = new Rect();
        childAt.getWindowVisibleDisplayFrame(rect);
        LayoutParams layoutParams;
        XLog.i("onMeasuer=====>>>this.d=" + WatchActivity.a(activity) + ",height=" + height + ",rectb=" + rect.bottom);
        if (height > rect.bottom) {
            this.d = WatchActivity.a(activity);
            layoutParams = (LayoutParams) childAt2.getLayoutParams();
            if (layoutParams.height != this.d) {
                layoutParams.height = this.d;
                childAt2.setLayoutParams(layoutParams);
                this.chatInputLayout.setVisibility(View.VISIBLE);
//                this.BarrageItemModel.c(new com.kitty.android.a.f.BarrageItemModel(this.d));
                this.e = true;
//                this.f.BarrageHolder();
            }
        }
        else if (height < rect.bottom && this.e) {
            int a = WatchActivity.a(activity);
            if (a == this.d || a == a(266.0f)) {
                layoutParams = (LayoutParams) childAt2.getLayoutParams();
                layoutParams.height = 0;
                childAt2.setLayoutParams(layoutParams);
//                this.BarrageItemModel.c(new com.kitty.android.a.f.a());
//                if (this.g.BarrageItemModel()) {
//                    this.g.c();
//                }
                this.e = false;
                this.chatInputLayout.setVisibility(View.INVISIBLE);
//                this.f.A();
            } else {
                LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
                if (layoutParams2.height != a) {
                    layoutParams2.height = a;
                    childAt2.setLayoutParams(layoutParams2);
//                    this.chatInputLayout.setVisibility(View.VISIBLE);
                }
            }
        }


        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    public int a(float f) {
        return (int) TypedValue.applyDimension(1, f, getResources().getDisplayMetrics());
    }

    public EditText getEditText() {
        return this.chatInputEdit;
    }

    public void setInputVisible() {
        chatInputLayout.setVisibility(View.VISIBLE);
    }

    public void showWithAlpha() {
        ObjectAnimator.ofFloat(this, "alpha", new float[]{0.0f, 1.0f}).start();
    }

    Activity activity;

    public void setF(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }
}
