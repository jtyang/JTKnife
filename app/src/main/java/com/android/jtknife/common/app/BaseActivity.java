package com.android.jtknife.common.app;

import android.content.Intent;

import com.android.jtknife.R;
import com.android.jtknife.core.app.BaseAppCompatActivity;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 16/7/17
 */
public abstract class BaseActivity extends BaseAppCompatActivity {

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (isOpenActivityAnimation()) {
            overridePendingTransitionEnter();
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (isOpenActivityAnimation()) {
            overridePendingTransitionExit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isOpenActivityAnimation()) {
            overridePendingTransitionExit();
        }
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    public boolean isOpenActivityAnimation() {
        return true;
    }
}
