package com.android.jtknife.modules.softinput;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseActivity;
import com.android.jtknife.modules.live.widget.SoftKeyboardSizeWatchLayout;
import com.elvishew.xlog.XLog;

import butterknife.Bind;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/11/8
 */
public class SoftInputTestActivity extends BaseActivity {

    @Bind(R.id.root_view)
    SoftKeyboardSizeWatchLayout rootView;
    @Bind(R.id.chat_btn)
    Button chatBtn;
    @Bind(R.id.chat_edit)
    EditText chatEdit;
    @Bind(R.id.edit_layout)
    RelativeLayout editLayout;
    @Bind(R.id.space_view)
    View spaceView;
    @Bind(R.id.chat_input_layout)
    LinearLayout chatInputLayout;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_soft_input;
    }

    @Override
    protected void onInitView() {
        rootView.addOnResizeListener(new SoftKeyboardSizeWatchLayout.OnResizeListener() {
            @Override
            public void OnSoftPop(int height) {
                XLog.e("OnSoftPop height=" + height);
            }

            @Override
            public void OnSoftClose() {
                XLog.e("OnSoftClose");
            }
        });
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatInputLayout.setVisibility(View.VISIBLE);
                chatEdit.requestFocus();
                showInputMethod(chatEdit);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    protected void showInputMethod(EditText editText) {
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);// 48 不调整 输入法完全直接覆盖住
        toggleInput(editText, this);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);// 34
    }

    public void toggleInput(EditText editText, Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_FORCED);//2
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);//(2,1)
    }

    public void hiddenInputMethod(EditText editText, Context context) {
        try {
            InputMethodManager inputManager = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        } catch (Exception e) {
        }
    }

}
