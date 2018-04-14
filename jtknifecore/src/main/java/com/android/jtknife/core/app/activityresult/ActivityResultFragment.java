package com.android.jtknife.core.app.activityresult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2018/4/14
 */
public class ActivityResultFragment extends Fragment {

    public static final String TAG = "on_act_result_event_dispatcher";

    private SparseArray<ActivityResultRequest.Callback> mCallbacks = new SparseArray<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void startForResult(Intent intent, ActivityResultRequest.Callback callback) {
        mCallbacks.put(callback.hashCode(), callback);
        startActivityForResult(intent, callback.hashCode());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ActivityResultRequest.Callback callback = mCallbacks.get(requestCode);
        mCallbacks.remove(requestCode);

        if (callback != null) {
            callback.onActivityResult(resultCode, data);
        }
    }

}
