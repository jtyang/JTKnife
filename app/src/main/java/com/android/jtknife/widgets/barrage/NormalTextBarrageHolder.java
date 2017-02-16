package com.android.jtknife.widgets.barrage;

import android.view.View;
import android.widget.TextView;

import com.android.jtknife.R;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/2/8
 */
public class NormalTextBarrageHolder extends BarrageHolder {

    private TextView mTextView;

    @Override
    public int getLayoutResId() {
        return R.layout.layout_barrage_normal_item;
    }

    @Override
    public void onBindView(View view) {
        mTextView = (TextView) view.findViewById(R.id.text);
        mTextView.setText("aaa"+System.currentTimeMillis());
    }

    @Override
    public int u() {
        return 8;
    }
}
