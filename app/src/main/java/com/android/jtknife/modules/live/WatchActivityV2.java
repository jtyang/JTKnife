package com.android.jtknife.modules.live;

import android.view.View;
import android.widget.TextView;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseActivity;

import butterknife.Bind;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/2/6
 */
public class WatchActivityV2 extends BaseActivity {

    @Bind(R.id.input_say_text)
    TextView inputSayTextView;

    InputDialog inputDialog;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_watchv2;
    }

    @Override
    protected void onInitView() {
        inputSayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDialog = new InputDialog();
                inputDialog.show(getSupportFragmentManager(),"inputdialog");
//                inputDialog.showInput();
            }
        });
    }


}
