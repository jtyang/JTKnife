package com.android.jtknife.core.widgets;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.jtknife.core.R;

/**
 * support包Toolbar控件扩展
 * AUTHOR: yangjiantong
 * DATE: 2016/11/11
 */
public class ToolbarEx extends Toolbar {
    private static final String TAG = "ToolbarEx";

    private ImageView ivBack;
    private ImageView ivRight;
    private TextView right;
    private CharSequence title;
    private TextView titleView;

    public ToolbarEx(Context context) {
        super(context);
        init(context);
    }

    public ToolbarEx(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public ToolbarEx(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(final Context context) {
        inflate(context, R.layout.view_toolbar_ex, this);
        setContentInsetsRelative(0, 0);
        this.right = (TextView) findViewById(R.id.tv_right);
        this.ivRight = (ImageView) findViewById(R.id.iv_right);
        this.ivBack = (ImageView) findViewById(R.id.iv_back);
        this.titleView = (TextView) findViewById(R.id.tv_toolbar_title);
        if (context instanceof Activity) {
//            this.ivBack.setOnClickListener(new a(this, context));
            this.ivBack.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) context).finish();
                }
            });
        }
        setTitle(getTitle());
//        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//
//            }
//        });
    }

    public void setTitleTextColor(int i) {
        if (this.titleView != null) {
            this.titleView.setTextColor(i);
        }
    }

    public void hideLeftBackIcon() {
        if (this.ivBack != null) {
            this.ivBack.setVisibility(GONE);
        }
    }

    public View getRightShareView() {
        return this.ivRight;
    }

    public void setRightTabText(int i) {
        this.right.setText(i);
        this.right.setVisibility(VISIBLE);
    }

    public void hideRightTabText() {
        if (this.right != null) {
            this.right.setVisibility(GONE);
        }
    }

    public void showRightTabText() {
        if (this.right != null) {
            this.right.setVisibility(VISIBLE);
        }
    }

    public void setRightTabOnClickListener(OnClickListener onClickListener) {
        this.right.setOnClickListener(onClickListener);
    }

    public void setRightImageTab(int i, OnClickListener onClickListener) {
        this.ivRight.setImageResource(i);
        if (onClickListener != null) {
            this.ivRight.setOnClickListener(onClickListener);
        }
        this.ivRight.setVisibility(VISIBLE);
    }

    public void setTitle(CharSequence charSequence) {
        if (charSequence == null) {
            this.titleView.setVisibility(View.GONE);
        } else if (this.titleView != null) {
            this.titleView.setVisibility(View.VISIBLE);
            this.titleView.setText(charSequence);
        }
        this.title = charSequence;
    }

    public TextView getTitleView() {
        return this.titleView;
    }

    public CharSequence getTitle() {
        return this.title;
    }

    public void setBackButtonOnClickListener(OnClickListener onClickListener) {
        this.ivBack.setOnClickListener(onClickListener);
    }

    public void setTitleOnClickListener(OnClickListener onClickListener) {
        this.titleView.setOnClickListener(onClickListener);
    }

    public void setBackButtonVisiable(int i) {
        this.ivBack.setVisibility(i);
    }
}
