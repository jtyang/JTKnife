package com.android.jtknife.core.common.task;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * Description:轻量级的异步任务处理类
 * Author：yangjiantong
 * Date: 2015/12/22 16:42
 */
public class LightTaskManager {

    private static LightTaskManager instance;
    private Handler mHandler;

    private LightTaskManager() {
        HandlerThread workerThread = new HandlerThread("LightTaskThread");
        workerThread.start();
        mHandler = new Handler(workerThread.getLooper());
    }

    public static LightTaskManager getInstance() {
        if (instance == null) {
            synchronized (LightTaskManager.class) {
                if (instance == null) {
                    instance = new LightTaskManager();
                }
            }
        }
        return instance;
    }

    /**
     * 提交优先级一般的任务
     *
     * @param run
     */
    public void post(Runnable run) {
        if (mHandler != null)
            mHandler.post(run);
    }

    /**
     * 提交优先级一般的任务
     *
     * @param runnable
     */
    public void postAtFrontOfQueue(Runnable runnable) {
        if (mHandler != null)
            mHandler.postAtFrontOfQueue(runnable);
    }

    public void postDelayed(Runnable runnable, long delay) {
        if (mHandler != null)
            mHandler.postDelayed(runnable, delay);
    }

    public void postAtTime(Runnable runnable, long time) {
        if (mHandler != null)
            mHandler.postAtTime(runnable, time);
    }

}
