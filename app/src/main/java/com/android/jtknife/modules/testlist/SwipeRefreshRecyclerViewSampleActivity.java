package com.android.jtknife.modules.testlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseActivity;
import com.android.jtknife.core.widgets.refreshlayout.IRefreshLayout;
import com.android.jtknife.core.widgets.refreshlayout.SwipeRefreshLayoutEx;
import com.elvishew.xlog.XLog;

import butterknife.Bind;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2016/12/25
 */
public class SwipeRefreshRecyclerViewSampleActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayoutEx swipeRefreshLayout;
    @Bind(R.id.loading_btn)
    Button loadingBtn;
    @Bind(R.id.content_btn)
    Button contentBtn;
    @Bind(R.id.empty_btn)
    Button emptyBtn;
    @Bind(R.id.error_btn)
    Button errorBtn;

    MyRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XLog.i("SwipeRefreshRecyclerViewSampleActivity onCreate");
        swipeRefreshLayout.showLoadingView();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_swiperefresh_demo;
    }

    @Override
    protected void onInitView() {
        loadingBtn.setOnClickListener(this);
        contentBtn.setOnClickListener(this);
        emptyBtn.setOnClickListener(this);
        errorBtn.setOnClickListener(this);
        mAdapter = new MyRecyclerViewAdapter(mContext);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(mAdapter);

        swipeRefreshLayout.setCallBack(new IRefreshLayout.IRefreshCallback() {
            @Override
            public void onLoadMore() {
                XLog.i("onLoadMore==========");
            }

            @Override
            public void onRefresh() {
                XLog.i("onRefresh==========");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loading_btn:
                swipeRefreshLayout.showLoadingView();
                break;
            case R.id.content_btn:
                swipeRefreshLayout.showContentView();
                break;
            case R.id.empty_btn:
                swipeRefreshLayout.showEmptyView();
                break;
            case R.id.error_btn:
                swipeRefreshLayout.showErrorView();
                break;
            default:break;

        }
    }
}
