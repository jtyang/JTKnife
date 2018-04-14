package com.android.jtknife.core.app.activityresult;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * 文件描述:
 * <p>
 * <p>
 * Intent intent = new Intent(this, SecondActivity.class);
 * <p>
 * request.startForResult(intent, new ActResultRequest.Callback() {
 *
 * @Override public void onActivityResult(int resultCode, Intent data) {
 * Toast.makeText(MainActivity.this, "" + resultCode, Toast.LENGTH_SHORT).show();
 * }
 * });
 */
public class ActivityResultRequest {

    private ActivityResultFragment fragment;

    public ActivityResultRequest(AppCompatActivity activity) {
        fragment = initEventDispatchFragment(activity);
    }

    private ActivityResultFragment initEventDispatchFragment(AppCompatActivity activity) {
        final FragmentManager fragmentManager = activity.getSupportFragmentManager();

        ActivityResultFragment fragment = (ActivityResultFragment) fragmentManager.findFragmentByTag(ActivityResultFragment.TAG);
        if (fragment == null) {
            fragment = new ActivityResultFragment();
            fragmentManager
                    .beginTransaction()
                    .add(fragment, ActivityResultFragment.TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return fragment;
    }

    public void startForResult(Intent intent, Callback callback) {
        fragment.startForResult(intent, callback);
    }

    public interface Callback {

        void onActivityResult(int resultCode, Intent data);
    }
}
