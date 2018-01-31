package com.android.jtknife.core.common.task;

import io.reactivex.Scheduler;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2018/1/15
 */
public interface SchedulerProvider {

    Scheduler ui();

    Scheduler computation();

    Scheduler newThread();

    Scheduler io();
}
