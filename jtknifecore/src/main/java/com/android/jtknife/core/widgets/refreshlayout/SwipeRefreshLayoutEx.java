package com.android.jtknife.core.widgets.refreshlayout;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.jtknife.core.R;

/**
 * SwipeRefreshLayout扩展
 * AUTHOR: yangjiantong
 * DATE: 2016/11/11
 */
public class SwipeRefreshLayoutEx extends SwipeRefreshLayout implements IRefreshLayout {

    private IRefreshLayout.IRefreshCallback mCallBack;
    private boolean mCanLoadMore;
    private View mEmptyView;
    private View mErrorView;
    private View mLoadingView;
    private boolean mIsLoadingMore;
    private boolean mIsRefreshing;
    private boolean mIsRvScrollUp;

    public SwipeRefreshLayoutEx(Context context) {
        super(context);
        this.mCanLoadMore = true;
        this.mIsRvScrollUp = false;
        init(context);
    }

    public SwipeRefreshLayoutEx(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCanLoadMore = true;
        this.mIsRvScrollUp = false;
        init(context);
    }

    private void init(Context context) {
//        setColorSchemeResources();
        this.mEmptyView = LayoutInflater.from(context).inflate(R.layout.layout_empty_view, null);
        this.mEmptyView.setClickable(true);
        this.mErrorView = LayoutInflater.from(context).inflate(R.layout.layout_error_view, null);
        this.mErrorView.setClickable(true);
        this.mLoadingView = LayoutInflater.from(context).inflate(R.layout.layout_loading_view, null);
    }

    private void addScrollListener() {
        View childAt = getChildAt(1);
        if (childAt instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) childAt;
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                private LinearLayoutManager layoutManager;
                private int itemCount, lastPosition, lastItemCount;

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                        layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                        itemCount = layoutManager.getItemCount();
                        lastPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                    } else {
                        Log.e("OnLoadMoreListener", "The OnLoadMoreListener only support LinearLayoutManager");
                        return;
                    }

                    if (lastItemCount != itemCount && lastPosition == itemCount - 1) {
                        lastItemCount = itemCount;
                        if (mCallBack != null) {
                            mCallBack.onLoadMore();
                        }
                    }
                }
            });
        } else if (childAt instanceof ListView) {
            ListView listView = (ListView) childAt;
//            listView.setOnScrollListener(new c(this, listView));
        } else if (childAt instanceof GridView) {
            GridView gridView = (GridView) childAt;
//            gridView.setOnScrollListener(new d(this, gridView));
        }
    }


    @Override
    public void completeLoadMore() {
        this.mIsLoadingMore = false;
    }

    @Override
    public void completeRefresh() {
        setRefreshing(false);
    }

    @Override
    public void manualRefresh() {
        post(new Runnable() {
            @Override
            public void run() {
                callRefresh();
            }
        });
    }

    @Override
    public void setCallBack(final IRefreshLayout.IRefreshCallback callBack) {
        this.mCallBack = callBack;
        setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (callBack != null) {
                    callBack.onRefresh();
                }
            }
        });
        addScrollListener();
    }

    @Override
    public void setCanLoadMore(boolean z) {
        this.mCanLoadMore = z;
    }

    @Override
    public void setEmptyText(String str) {
        if (!TextUtils.isEmpty(str)) {
            ((TextView) this.mEmptyView.findViewById(R.id.tv_empty)).setText(str);
        }
    }

    @Override
    public void setEmptyView(View view) {
        this.mEmptyView = view;
    }

    @Override
    public void setEmptyViewListener(OnClickListener onClickListener) {
        if (this.mEmptyView != null) {
            this.mEmptyView.setOnClickListener(onClickListener);
        }
    }

    @Override
    public void setErrorText(String str) {
        if (!TextUtils.isEmpty(str)) {
            ((TextView) this.mErrorView.findViewById(R.id.tv_error)).setText(str);
        }
    }

    @Override
    public void setErrorView(View view) {
        this.mErrorView = view;
    }

    @Override
    public void setErrorViewListener(OnClickListener onClickListener) {
        if (this.mErrorView != null) {
            this.mErrorView.setOnClickListener(onClickListener);
        }
    }

    @Override
    public void showEmptyView() {
        removeEmptyErrorView();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        ViewGroup viewGroup = (ViewGroup) getParent();
        viewGroup.removeView(this.mEmptyView);
        viewGroup.addView(this.mEmptyView, layoutParams);
    }

    @Override
    public void showErrorView() {
        removeEmptyErrorView();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        addView(this.mErrorView, layoutParams);
    }

    @Override
    public void showLoadingView(){
        removeEmptyErrorView();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        addView(this.mLoadingView, layoutParams);
    }

    private void removeEmptyErrorView() {
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (this.mErrorView != null) {
            viewGroup.removeView(this.mErrorView);
        }
        if (this.mEmptyView != null) {
            viewGroup.removeView(this.mEmptyView);
        }
        if(this.mLoadingView != null){
            viewGroup.removeView(this.mLoadingView);
        }
    }

    private void callRefresh() {
        removeEmptyErrorView();
        setRefreshing(true);
        this.mCallBack.onRefresh();
    }

    public void setRefreshing(boolean isRefreshing) {
        super.setRefreshing(isRefreshing);
        this.mIsRefreshing = isRefreshing;
    }


}
