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
 * 直播间聊天view
 * 原理，底部加一个空白view，通过监听键盘高度，设置空白view的高度，撑起聊天输入框
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

    private int keyboardHeight = 0;
    private boolean isKeyboardShow = false;

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
            this.keyboardHeight = getKeyboardHeight(activity);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View chatInputLayout = getChildAt(0);
        View bottomSpaceView = getChildAt(1);
        int[] chatInputLocation = new int[2];
        chatInputLayout.getLocationInWindow(chatInputLocation);
        int height = chatInputLocation[1] + chatInputLayout.getHeight();
        XLog.i("chatInputLocation y=" + chatInputLocation[1] + ",chatInputLayout height=" + chatInputLayout.getHeight());
        Rect chatInputRect = new Rect();
        chatInputLayout.getWindowVisibleDisplayFrame(chatInputRect);
        LayoutParams layoutParams;
        XLog.i("onMeasure=====>>>this.keyboardHeight=" + getKeyboardHeight(activity) + ",height=" + height + ",chatInputLayoutBottom=" + chatInputRect.bottom);
        if (height > chatInputRect.bottom) {
            this.keyboardHeight = getKeyboardHeight(activity);
            layoutParams = (LayoutParams) bottomSpaceView.getLayoutParams();
            if (layoutParams.height != this.keyboardHeight) {
                layoutParams.height = this.keyboardHeight;
                bottomSpaceView.setLayoutParams(layoutParams);
                this.chatInputLayout.setVisibility(View.VISIBLE);
                this.isKeyboardShow = true;
            }
        } else if (height < chatInputRect.bottom && this.isKeyboardShow) {
            int newKeyboardHeight = getKeyboardHeight(activity);
            if (newKeyboardHeight == this.keyboardHeight || newKeyboardHeight == dp2px(266.0f)) {
                layoutParams = (LayoutParams) bottomSpaceView.getLayoutParams();
                layoutParams.height = 0;
                bottomSpaceView.setLayoutParams(layoutParams);
                this.isKeyboardShow = false;
                this.chatInputLayout.setVisibility(View.INVISIBLE);
            } else {
                LayoutParams layoutParams2 = (LayoutParams) bottomSpaceView.getLayoutParams();
                if (layoutParams2.height != newKeyboardHeight) {
                    layoutParams2.height = newKeyboardHeight;
                    bottomSpaceView.setLayoutParams(layoutParams2);
//                    this.chatInputLayout.setVisibility(View.VISIBLE);
                }
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public int dp2px(float f) {
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

    private Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public int getKeyboardHeight(Activity activity) {
        XLog.d("screenHeight=" + DisplayUtils.getScreenHeight(activity) + "，screenWidth=" + DisplayUtils.getScreenWidth(activity));
        XLog.d("statusBarHeight=" + DisplayUtils.getWindowVisibleDisplayFrameTop(activity) + "，windowVisibleHeight=" + DisplayUtils.getWindowVisibleDisplayFrameHeight(activity));
        int keyboaryHeight = (DisplayUtils.getScreenHeight(activity) - DisplayUtils.getWindowVisibleDisplayFrameTop(activity)) - DisplayUtils.getWindowVisibleDisplayFrameHeight(activity);
        XLog.d("keyboard height=" + keyboaryHeight);
        if (keyboaryHeight == 0) {
            return dp2px((Context) activity, 266.0f);
        }
        return keyboaryHeight;
    }

    public static int dp2px(Context context, float f) {
        return (int) TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics());
    }

}
