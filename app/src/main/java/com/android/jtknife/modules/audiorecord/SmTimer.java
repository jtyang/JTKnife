package com.android.jtknife.modules.audiorecord;

import android.os.Handler;
import android.os.Message;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/11/6
 */
public class SmTimer extends Handler {
    private int mInterval;
    private boolean mLoop = false;
    private long totalTime = 0L;

    private SmTimerCallback mCallback;

    public interface SmTimerCallback {
        public void onTimeout();
    }

    public SmTimer(SmTimerCallback callback) {
        mCallback = callback;
    }

    @Override
    public void handleMessage(Message msg) {
        totalTime += mInterval;
        if (null != mCallback) {
            mCallback.onTimeout();
        }

        if (mLoop) {
            sendEmptyMessageDelayed(0, mInterval);
        }
    }

    public void startIntervalTimer(int delay, int interval) {
        stopTimer();

        mInterval = interval;
        mLoop = true;
        sendEmptyMessageDelayed(0, delay);
    }

    public void stopTimer() {
        while (hasMessages(0)) {
            removeMessages(0);
        }
        mLoop = false;
    }

    public long getTotalTime(){
        return totalTime;
    }
}
