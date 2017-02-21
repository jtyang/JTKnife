package com.android.jtknife.core.widgets.pullrefresh;

/**
 * 为 RecyclerRefreshLayout 自定义刷新动画 (需要实现接口 IRefreshStatus)
 * {@link RecyclerRefreshLayout#mRefreshView} all the custom drop-down refresh view need to
 * implements the interface
 */
public interface IRefreshStatus {
    /**
     * 当手势操作完成且刷新动画滑动到起始位置
     * When the content view has reached to the start point and refresh has been completed, view will be reset.
     */
    void reset();

    /**
     * 正在刷新
     * Refresh View is refreshing
     */
    void refreshing();

    /**
     * 刷新动画被下拉到刷新位置
     * Refresh View is dropped down to the refresh point
     */
    void pullToRefresh();

    /**
     * 刷新动画被释放到刷新位置
     * Refresh View is released into the refresh point
     */
    void releaseToRefresh();

    /**
     * @param pullDistance The drop-down distance of the refresh View 刷新动画被拖动的距离
     * @param pullProgress The drop-down progress of the refresh View and the pullProgress may be more than 1.0f
     *                     pullProgress = pullDistance / refreshTargetOffset
     *                     刷新动画被拖动的进度，当大于触发刷新的距离会大于1.0f
     */
    void pullProgress(float pullDistance, float pullProgress);
}
