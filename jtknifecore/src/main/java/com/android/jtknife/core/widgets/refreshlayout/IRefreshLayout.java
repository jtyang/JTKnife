package com.android.jtknife.core.widgets.refreshlayout;

import android.view.View;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/11/11
 */
public interface IRefreshLayout {

    interface IRefreshCallback {
        void onLoadMore();

        void onRefresh();
    }

    void completeLoadMore();

    void completeRefresh();

    void manualRefresh();

    void setCallBack(IRefreshCallback callBack);

    void setCanLoadMore(boolean z);

    void setEmptyText(String str);

    void setEmptyView(View view);

    void setEmptyViewListener(View.OnClickListener onClickListener);

    void setErrorText(String str);

    void setErrorView(View view);

    void setErrorViewListener(View.OnClickListener onClickListener);

    void showEmptyView();

    void showErrorView();

    void showLoadingView();
}
