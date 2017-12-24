package com.android.jtknife.core.arch.example;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/24
 */
public class ExamplePresenter implements ExampleContract.Presenter {

    private ExampleRepository repository;
    private ExampleContract.View view;

    private CompositeDisposable disposeBag;

    public ExamplePresenter(ExampleContract.View view) {
        this.view = view;
        disposeBag = new CompositeDisposable();
        repository = new ExampleRepository();

        // Initialize this presenter as a lifecycle-aware when a view is a lifecycle owner.
        if (view != null && view instanceof LifecycleOwner) {
            ((LifecycleOwner) view).getLifecycle().addObserver(this);
        }
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDetach() {
        // Clean up any no-longer-use resources here
        disposeBag.clear();

        //is need ???
        if (view != null && view instanceof LifecycleOwner) {
            ((LifecycleOwner) view).getLifecycle().removeObserver(this);
        }
    }

    @Override
    public void loadTestData() {
        Disposable disposable = repository.loadTestData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        //data...
                        if (view != null) {
                            view.showData(strings);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //onError
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //onComplete
                    }
                });//参数实现1~3个均可
        disposeBag.add(disposable);
    }


}
