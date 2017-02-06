package com.android.jtknife.modules.live;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseActivity;

import butterknife.Bind;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/2/6
 */
public class WatchActivity extends BaseActivity {

    @Bind(R.id.input_say_text)
    TextView inputSayTextView;
    @Bind(R.id.bottom_input_layout)
    RoomEditChatView roomEditChatView;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_watch;
    }

    @Override
    protected void onInitView() {
        inputSayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomEditChatView.setInputVisible();
                roomEditChatView.showWithAlpha();
                toggleInput(roomEditChatView.getEditText());
            }
        });
    }

    protected void toggleInput(EditText editText) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);// 48 不调整 输入法完全直接覆盖住
        toggleInput(editText, this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);// 34
    }

    public void toggleInput(EditText editText, Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_FORCED);//2
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, InputMethodManager.HIDE_IMPLICIT_ONLY);//(2,1)
    }
}
