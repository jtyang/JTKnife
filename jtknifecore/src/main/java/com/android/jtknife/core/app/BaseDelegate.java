package com.android.jtknife.core.app;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.view.View;

import com.elvishew.xlog.XLog;

/**
 * 文件描述:BaseDelegate for Activity
 *
 * UseAge:
 *
 * public class TestDelegate extends BaseDelegate{}
 *
 * XxActivity:
 * onCreate
 * TestDelegate testDelegate = new TestDelegate();
 * registerDelegate(testDelegate);//delegate will auto unregister on the Activity onDestroy
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
        //答案是：newObserver会依次执行onCreate和onStart
        XLog.e("BaseDelegate onCreate=====");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        XLog.e("BaseDelegate onStart=====");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        XLog.e("BaseDelegate onResume=====");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        XLog.e("BaseDelegate onPause=====");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        XLog.e("BaseDelegate onStop=====");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        //疑问：Activity的onDestroy应该何时执行getLifecycle().removeObserver(testDelegate)比较合适？
        //或者说如何能保证Delegate能够一定执行到onDestroy并且又能够removeObserver(delegate)
        //答案：BaseDelegate的onDestroy会比Activity的onDestroy先执行，比Activity的onDestroy的super.onDestroy()都要早执行
        XLog.e("BaseDelegate onDestroy=====");
    }


}
