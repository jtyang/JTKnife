package com.android.jtknife.core.app;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.view.View;

/**
 * 文件描述:
 *
 * UseAge:
 *
 * public class TestDelegate extends BaseDelegate{}
 *
 * XxActivity:
 * onCreate
 * TestDelegate testDelegate = new TestDelegate();
 * getLifecycle().addObserver(testDelegate);
 *
 * onDestroy:
 * getLifecycle().removeObserver(testDelegate);
 *
 * @author yangjiantong
 * @date 2017/12/20
 */
public abstract class BaseDelegate implements LifecycleObserver {

    private Context mContext;
    private View mRootView;

    public BaseDelegate(Context context, View rootView) {
        this.mContext = context;
        this.mRootView = rootView;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        //需要测试如果在activity的onStart执行
        // void onStart() {
        //     mRegistry.removeObserver(this);
        //     mRegistry.add(newObserver);
        // }
        //请问：newObserver会依次执行onCreate和onStart吗？还是只是执行onStart？
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        //疑问：Activity的onDestroy应该何时执行getLifecycle().removeObserver(testDelegate)比较合适？
        //或者说如何能保证Delegate能够一定执行到onDestroy并且又能够removeObserver(delegate)
    }


}
