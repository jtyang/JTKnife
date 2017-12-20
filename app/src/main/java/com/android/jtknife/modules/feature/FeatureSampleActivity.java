package com.android.jtknife.modules.feature;

import android.os.Bundle;
import android.view.View;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseActivity;
import com.elvishew.xlog.XLog;

import butterknife.Bind;

public class FeatureSampleActivity extends BaseActivity {

    @Bind(R.id.root_view)
    View rootView;

    TestDelegate testDelegate;

//    FeatureHostManager featureSampleHost;
//    DispatchQueue dispatchQueue = new DispatchQueue("aaa");
//    DispatchQueue dispatchQueue2 = new DispatchQueue("bbb");
//    DispatchQueue dispatchQueue3 = new DispatchQueue("ccc");
//    DispatchQueue dispatchQueue4 = new DispatchQueue("ddd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        featureSampleHost = new FeatureHostManager(mContext);
//        featureSampleHost.with(new Feature1())
//                .with(new Feature2())
//                .dispatchOnCreate(rootView, savedInstanceState);
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

        testDelegate = new TestDelegate(this,rootView);
//        getLifecycle().addObserver(testDelegate);
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
        getLifecycle().addObserver(testDelegate);
    }

    @Override
    protected void onDestroy() {
//        featureSampleHost.dispatchOnDestroy();
        XLog.e("FeatureActivity onDestroy 1");
        super.onDestroy();
        XLog.e("FeatureActivity onDestroy 2");
        getLifecycle().removeObserver(testDelegate);
    }
}
