package com.android.jtknife.modules.topactivity;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;

import com.elvishew.xlog.XLog;

import java.util.List;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/3/7
 */
public class JTService extends Service {

    private static final String TAG = "JTService";
    private boolean flag = true;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startGetTopActivityNameThread();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        flag = false;
        super.onDestroy();
    }

    private void startGetTopActivityNameThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    getTopActivityName();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private String getTopActivityName() {
        String topActivityPackage = null;
        String topActivityClass = null;
        ActivityManager activityManager = (ActivityManager) (getBaseContext()
                .getSystemService(android.content.Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager
                .getRunningTasks(1);
        if (runningTaskInfos != null) {
            ComponentName f = runningTaskInfos.get(0).topActivity;
            topActivityPackage = f.getPackageName();
            topActivityClass = f.getClassName();
        }
        XLog.e("top activity name = " + topActivityClass);
        return topActivityClass;
    }

}
