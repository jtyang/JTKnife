package com.android.jtknife.modules.feature;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseActivity;
import com.elvishew.xlog.XLog;

import butterknife.Bind;

public class FeatureSampleActivity extends BaseActivity {

    @Bind(R.id.root_view)
    View rootView;

    TestDelegate testDelegate;
    UserModelTest umt;
    Handler mHandler = new Handler();

//    DispatchQueue dispatchQueue = new DispatchQueue("aaa");
//    DispatchQueue dispatchQueue2 = new DispatchQueue("bbb");
//    DispatchQueue dispatchQueue3 = new DispatchQueue("ccc");
//    DispatchQueue dispatchQueue4 = new DispatchQueue("ddd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        dispatchQueue.postRunnable(new Runnable() {
//            @Override
//            public void run() {
//                XLog.i("Thread 1 start");
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                XLog.i("Thread 1 end");
//            }
//        });
//
//        dispatchQueue.postRunnable(new Runnable() {
//            @Override
//            public void run() {
//                XLog.i("Thread 2 start");
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                XLog.i("Thread 2 end");
//            }
//        });

        testDelegate = new TestDelegate(this, rootView);
        registerDelegate(testDelegate);

        umt = ViewModelProviders.of(this).get(UserModelTest.class);
        XLog.e("FeatureActivity onCreate livedata "+umt.testLD.getValue());
        umt.testLD.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                XLog.e("Feature livedata changed="+s);
            }
        });
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                umt.testLD.setValue("bbbb=====456");
            }
        },3000);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_feature_sample;
    }

    @Override
    protected void onInitView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        XLog.e("FeatureActivity onResume");
    }

    @Override
    protected void onDestroy() {
//        featureSampleHost.dispatchOnDestroy();
        XLog.e("FeatureActivity onDestroy 1");
        super.onDestroy();
        XLog.e("FeatureActivity onDestroy 2");
    }
}
