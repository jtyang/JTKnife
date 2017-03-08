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
        overridePendingTransitionEnter();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransitionExit();
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected void overridePendingTransitionEnter() {
        ActivityAnimType activityAnimType = getActivityAnimType();
        if (activityAnimType == ActivityAnimType.RIGHT) {
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        } else if (activityAnimType == ActivityAnimType.BOTTOM) {
            overridePendingTransition(R.anim.activity_bottom_in, R.anim.activity_still);
        } else {
        }

    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected void overridePendingTransitionExit() {
        ActivityAnimType activityAnimType = getActivityAnimType();
        if (activityAnimType == ActivityAnimType.RIGHT) {
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        } else if (activityAnimType == ActivityAnimType.BOTTOM) {
            overridePendingTransition(R.anim.activity_still, R.anim.activity_bottom_out);
        } else {
        }
    }

    public ActivityAnimType getActivityAnimType() {
        return ActivityAnimType.RIGHT;
    }

    public enum ActivityAnimType {
        NONE,
        RIGHT,
        BOTTOM,
        TOP,
        LEFT
    }
}
