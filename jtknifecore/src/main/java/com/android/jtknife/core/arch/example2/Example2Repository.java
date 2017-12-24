package com.android.jtknife.core.arch.example2;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import java.util.concurrent.TimeUnit;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/24
 */
public class Example2Repository {

    MediatorLiveData<String> result = new MediatorLiveData<>();
    //限制接口请求频率
    RateLimiter<String> rateLimiter = new RateLimiter(5, TimeUnit.MINUTES);

    Example2Api api;
    LifecycleOwner owner;

    public Example2Repository() {
        api = new Example2Api();
    }

    public void getTestData() {
        LiveData<String> ld = api.getTestData();
        ld.observe(owner, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
    }

    private boolean shouldFetch(String owner) {
        return rateLimiter.shouldFetch(owner);
    }
}
