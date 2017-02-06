package com.android.jtknife.modules.live;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.jtknife.R;

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

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public EditText getEditText() {
        return this.chatInputEdit;
    }

    public void setInputVisible(){
        chatInputLayout.setVisibility(View.VISIBLE);
    }

    public void showWithAlpha(){
        ObjectAnimator.ofFloat(this, "alpha", new float[]{0.0f, 1.0f}).start();
    }
}
