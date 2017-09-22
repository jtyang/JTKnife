package com.android.jtknife.common.rxjava;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.Iterator;

/**
 * 模拟rxjava实现
 * 缺少map、zip操作符
 * AUTHOR: yangjiantong
 * DATE: 2017/9/20
 */
public class RxJava {

    public static <T> RxJava.Subscribable<T> create(RxJava.OnSubscribe<T> onSubscribe) {
        RxJava.Subscribable s = new RxJava.Subscribable();
        s.onSubscribe = onSubscribe;
        return s;
    }

    public static <T> RxJava.Subscribable<T> just(final T... datas) {
        return create(new QuickSubscribe<T>() {
            @Override
            protected void doNext(Subscriber<T> subscriber) throws Throwable {
                if (datas != null && datas.length > 0) {
                    for (T t : datas) {
                        subscriber.onNext(t);
                    }
                }
            }
        });
    }

    public static <T> RxJava.Subscribable<T> from(final Iterator<T> datas) {
        return create(new QuickSubscribe<T>() {
            @Override
            protected void doNext(Subscriber<T> subscriber) throws Throwable {
                while (datas != null && datas.hasNext()) {
                    subscriber.onNext(datas.next());
                }
            }
        });
    }

    private static final class SubscriberWarpper<T> extends RxJava.Subscriber<T> {
        private RxJava.Subscribable<T> subscribable;
        private RxJava.Subscriber<T> subscriber;

        public SubscriberWarpper(RxJava.Subscribable<T> subscribable, RxJava.Subscriber<T> subscriber) {
            this.subscribable = subscribable;
            this.subscriber = subscriber;
            subscriber.setWarpper(this);
        }

        public void removeSubscriber() {
            this.subscriber = null;
        }

        public void onStart() {
            if (this.subscriber != null) {
                if (this.subscribable.observeThread == RxJava.Thread.UI_THREAD) {
                    long mainThreadId = Looper.getMainLooper().getThread().getId();
                    if (java.lang.Thread.currentThread().getId() == mainThreadId) {
                        if (subscriber != null) subscriber.onStart();
                    } else {
                        UIHandler.sendEmptyMessage(0, new Handler.Callback() {
                            public boolean handleMessage(Message msg) {
                                if (subscriber != null) subscriber.onStart();
                                return false;
                            }
                        });
                    }
                } else if (this.subscribable.observeThread == RxJava.Thread.NEW_THREAD) {
                    (new java.lang.Thread() {
                        public void run() {
                            if (subscriber != null) subscriber.onStart();
                        }
                    }).start();
                } else {
                    if (subscriber != null) subscriber.onStart();
                }
            }
        }

        public void onNext(final T data) {
            if (this.subscriber != null) {
                if (this.subscribable.observeThread == RxJava.Thread.UI_THREAD) {
                    long mainThreadId = Looper.getMainLooper().getThread().getId();
                    if (java.lang.Thread.currentThread().getId() == mainThreadId) {
                        if (subscriber != null) subscriber.onNext(data);
                    } else {
                        UIHandler.sendEmptyMessage(0, new Handler.Callback() {
                            public boolean handleMessage(Message msg) {
                                if (subscriber != null) subscriber.onNext(data);
                                return false;
                            }
                        });
                    }
                } else if (this.subscribable.observeThread == RxJava.Thread.NEW_THREAD) {
                    AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
                        @Override
                        public void run() {
                            if (subscriber != null) subscriber.onNext(data);
                        }
                    });
                } else {
                    if (subscriber != null) subscriber.onNext(data);
                }
            }

        }

        public void onCompleted() {
            if (this.subscriber != null) {
                if (this.subscribable.observeThread == RxJava.Thread.UI_THREAD) {
                    long mainThreadId = Looper.getMainLooper().getThread().getId();
                    if (java.lang.Thread.currentThread().getId() == mainThreadId) {
                        if (subscriber != null) subscriber.onCompleted();
                        this.removeSubscriber();
                    } else {
                        UIHandler.sendEmptyMessage(0, new Handler.Callback() {
                            public boolean handleMessage(Message msg) {
                                if (subscriber != null) subscriber.onCompleted();
                                removeSubscriber();
                                return false;
                            }
                        });
                    }
                } else if (this.subscribable.observeThread == RxJava.Thread.NEW_THREAD) {
                    AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
                        @Override
                        public void run() {
                            if (subscriber != null) subscriber.onCompleted();
                            SubscriberWarpper.this.removeSubscriber();
                        }
                    });
                } else {
                    if (subscriber != null) subscriber.onCompleted();
                    this.removeSubscriber();
                }
            }

        }

        public void onError(final Throwable t) {
            if (this.subscriber != null) {
                if (this.subscribable.observeThread == RxJava.Thread.UI_THREAD) {
                    long mainThreadId = Looper.getMainLooper().getThread().getId();
                    if (java.lang.Thread.currentThread().getId() == mainThreadId) {
                        if (subscriber != null) subscriber.onError(t);
                        this.removeSubscriber();
                    } else {
                        UIHandler.sendEmptyMessage(0, new Handler.Callback() {
                            public boolean handleMessage(Message msg) {
                                if (subscriber != null) subscriber.onError(t);
                                SubscriberWarpper.this.removeSubscriber();
                                return false;
                            }
                        });
                    }
                } else if (this.subscribable.observeThread == RxJava.Thread.NEW_THREAD) {
                    AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
                        @Override
                        public void run() {
                            if (subscriber != null) subscriber.onError(t);
                            SubscriberWarpper.this.removeSubscriber();
                        }
                    });
                } else {
                    if (subscriber != null) subscriber.onError(t);
                    this.removeSubscriber();
                }
            }

        }
    }

    public static class Subscriber<T> {
        private RxJava.SubscriberWarpper<T> warpper;

        private void setWarpper(RxJava.SubscriberWarpper<T> warpper) {
            this.warpper = warpper;
        }

        public void onStart() {
        }

        public void onNext(T data) {
        }

        public void onCompleted() {
        }

        public void onError(Throwable t) {
        }

        public final void unsubscribe() {
            if (this.warpper != null) {
                this.warpper.removeSubscriber();
                this.warpper = null;
            }

        }
    }

    public abstract static class QuickSubscribe<T> implements RxJava.OnSubscribe<T> {

        public final void call(RxJava.Subscriber<T> subscriber) {
            subscriber.onStart();

            try {
                this.doNext(subscriber);
                subscriber.onCompleted();
            } catch (Throwable var3) {
                subscriber.onError(var3);
            }

        }

        protected abstract void doNext(RxJava.Subscriber<T> var1) throws Throwable;
    }

    public interface OnSubscribe<T> {
        void call(RxJava.Subscriber<T> var1);
    }

    public enum Thread {
        IMMEDIATE,
        UI_THREAD,
        NEW_THREAD
    }

    public static final class Subscribable<T> {
        private RxJava.OnSubscribe<T> onSubscribe;
        private RxJava.Thread observeThread;
        private RxJava.Thread subscribeThread;

        private Subscribable() {
        }

        public RxJava.Subscribable<T> subscribeOn(RxJava.Thread thread) {
            this.subscribeThread = thread;
            return this;
        }

        public RxJava.Subscribable<T> observeOn(RxJava.Thread thread) {
            this.observeThread = thread;
            return this;
        }

        public void subscribe(final RxJava.Subscriber<T> subscriber) {
            if (this.onSubscribe != null) {
                if (this.subscribeThread == RxJava.Thread.UI_THREAD) {
                    UIHandler.sendEmptyMessage(0, new Handler.Callback() {
                        public boolean handleMessage(Message msg) {
                            onSubscribe.call(new SubscriberWarpper<T>(Subscribable.this, subscriber));
                            return false;
                        }
                    });
                } else if (this.subscribeThread == RxJava.Thread.NEW_THREAD) {
                    AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
                        @Override
                        public void run() {
                            onSubscribe.call(new SubscriberWarpper<T>(Subscribable.this, subscriber));
                        }
                    });
                } else {
                    onSubscribe.call(new SubscriberWarpper<T>(Subscribable.this, subscriber));
                }
            }

        }

        public void subscribeOnNewThreadAndObserveOnUIThread(RxJava.Subscriber<T> subscriber) {
            this.subscribeOn(RxJava.Thread.NEW_THREAD);
            this.observeOn(RxJava.Thread.UI_THREAD);
            this.subscribe(subscriber);
        }
    }
}
