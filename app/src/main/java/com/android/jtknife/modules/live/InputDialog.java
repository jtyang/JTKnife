package com.android.jtknife.modules.live;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.android.jtknife.R;
import com.android.jtknife.widgets.ResizeLayout;
import com.elvishew.xlog.XLog;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/2/7
 * https://blog.piasy.com/2016/02/24/Android-Multiple-Fragment-Layer/
 * http://blog.csdn.net/android_freshman/article/details/51713183
 */
public class InputDialog extends DialogFragment {

    ResizeLayout dialogRootView;
    EditText chatInputEdit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_input_dialog, container);
        // 设置宽度为屏宽、靠近屏幕底部。
//        final Window window = getDialog().getWindow();
//        window.setBackgroundDrawableResource(R.color.transparent);
//        window.getDecorView().setPadding(0, 0, 0, 0);
//        WindowManager.LayoutParams wlp = window.getAttributes();
//        wlp.gravity = Gravity.BOTTOM;
//        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        window.setAttributes(wlp);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        getDialog().setCanceledOnTouchOutside(true);
        //防止软键盘遮挡住Dialog的输入框
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialogRootView = (ResizeLayout) view.findViewById(R.id.dialog_root_layout);
        dialogRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hidden soft input
                dismiss();
            }
        });
        dialogRootView.setOnResizeListener(new ResizeLayout.OnResizeListener() {
            @Override
            public void onKeyBoardStateChange(int state, int keyboardHeight) {
                XLog.i("onKeyBoardStateChange: state=" + state + ",keyboardHeight=" + keyboardHeight);
                if (state == ResizeLayout.KEYBOARD_STATE_HIDE) {
                    dismiss();
                } else {

                }
            }
        });
        chatInputEdit = (EditText) view.findViewById(R.id.chat_input_et);
        chatInputEdit.requestFocus();
        chatInputEdit.post(new Runnable() {

            @Override
            public void run() {
                ((InputMethodManager) getActivity().getSystemService(Context
                        .INPUT_METHOD_SERVICE)).showSoftInput(chatInputEdit, 0);
            }
        });

//        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    XLog.e("dialog back");
//                    dismiss();
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawableResource(R.color.transparent);
    }


}
