package com.android.jtknife.modules.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseActivity;
import com.elvishew.xlog.XLog;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * rxjava demo
 * AUTHOR: yangjiantong
 * DATE: 2017/6/13
 */
public class RxJavaDemoActivity extends BaseActivity {

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_rxjava;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        test();
    }

    @Override
    protected void onInitView() {

    }

    private void test() {
        Observable o = Observable.fromCallable(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                XLog.e("callable thread name=" + Thread.currentThread().getName());
                return null;
            }
        });
        o.subscribe(new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

}
