package com.android.jtknife.modules.live.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.android.jtknife.core.utils.DeviceUtils;
import com.elvishew.xlog.XLog;


public class ResizeLayout extends RelativeLayout {
    public static final byte KEYBOARD_STATE_SHOW = -3;
    public static final byte KEYBOARD_STATE_HIDE = -2;

    private boolean mHasInit;
    private boolean mHasKeybord;
    private int mHeight;
    private int mDisplayHeigt;
    private int mKeyboardHeight;

    public ResizeLayout(Context context) {
        super(context);
    }

    public ResizeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ResizeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnResizeListener(OnResizeListener l) {
        mListener = l;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (h == 0 || w == 0) {
            return;
        }
        if (!mHasInit) {
            mHasInit = true;
            mHeight = h;
            mDisplayHeigt = DeviceUtils.getScreenHeight(getContext());
            return;
        }

        int displayHeight = DeviceUtils.getScreenHeight(getContext());
        if (mDisplayHeigt != displayHeight) {    // 对华为隐藏显示底部虚拟导航栏做兼容
            mDisplayHeigt = displayHeight;
            if (!mHasKeybord) {
                mHeight = h;
            } else {
                mHeight = h + mKeyboardHeight;
            }
            return;
        }

        if (w != oldw) {//橫竖屏切换时不回调
            return;
        }

        //FxLog.d("ResizeLayout", "h:" + h + "  oldh:" + oldh + "  mHeight:" + mHeight);
        if (oldh == mHeight) {
            mHasKeybord = true;
            mKeyboardHeight = oldh - h;
            if (mListener != null) {
//                int height = oldh - h;
                //FxLog.d("ResizeLayout", "键盘显示:" + height);
                mListener.onKeyBoardStateChange(KEYBOARD_STATE_SHOW, mKeyboardHeight);
            }
        } else if (mHasKeybord && mHeight == h) {
            mHasKeybord = false;
            mKeyboardHeight = h - oldh;
            if (mListener != null) {
//                int height = h - oldh;
                //FxLog.d("ResizeLayout", "键盘关闭:" + height);
                mListener.onKeyBoardStateChange(KEYBOARD_STATE_HIDE, mKeyboardHeight);
            }
        } else if (mHasKeybord) {
            mKeyboardHeight = mHeight - h;
            if (mListener != null) {
//                int height = mHeight - h;
                //FxLog.d("ResizeLayout", "输入法大小更改的情况:" + height);
                mListener.onKeyBoardStateChange(KEYBOARD_STATE_SHOW, mKeyboardHeight);
            }
        } else {
            XLog.d("ResizeLayout", "未知情况");
        }
    }

    private OnResizeListener mListener;

    public interface OnResizeListener {
        void onKeyBoardStateChange(int state, int keyboardHeight);
    }

}
