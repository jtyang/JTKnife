package com.android.jtknife.core.app;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.view.View;

import com.android.jtknife.core.common.eventbus.MessageEvent;
import com.elvishew.xlog.XLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;

/**
 * 文件描述:BaseDelegate for Activity
 * <p>
 * UseAge:
 * <p>
 * public class TestDelegate extends BaseDelegate{}
 * <p>
 * XxActivity:
 * onCreate
 * TestDelegate testDelegate = new TestDelegate();
 * registerDelegate(testDelegate);//delegate will auto unregister on the Activity onDestroy
 *
 * @author yangjiantong
 * @date 2017/12/20
 */
public abstract class BaseDelegate implements LifecycleObserver {

    private WeakReference<Activity> mActivityRef;
    private WeakReference<View> mRootViewRef;

    public boolean isShow = false;

    public BaseDelegate(Activity activity, View rootView) {
        this.mActivityRef = new WeakReference<Activity>(activity);
        this.mRootViewRef = new WeakReference<View>(rootView);
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
        EventBus.getDefault().register(this);
        onInitView();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        XLog.e("BaseDelegate onStart=====");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        XLog.e("BaseDelegate onResume=====");
        isShow = true;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        XLog.e("BaseDelegate onPause=====");
        isShow = false;
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
        EventBus.getDefault().unregister(this);
    }

    protected Activity getActivity() {
        if (mActivityRef != null) {
            return mActivityRef.get();
        }
        return null;
    }

    protected View getRootView() {
        if (mRootViewRef != null) {
            return mRootViewRef.get();
        }
        return null;
    }


    /**
     * EventBus事件处理--主线程处理
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event == null) return;
        handleBusMessage(event);
        if (isShow) {
            handleBusMessageOnActivityShow(event);
        }
    }

    /**
     * 子类重写处理消息
     *
     * @param event
     */
    protected void handleBusMessage(MessageEvent event) {
    }

    protected void handleBusMessageOnActivityShow(MessageEvent event) {
    }

    //************* 子类必须实现的抽象方法 start **********
    protected abstract void onInitView();

}
