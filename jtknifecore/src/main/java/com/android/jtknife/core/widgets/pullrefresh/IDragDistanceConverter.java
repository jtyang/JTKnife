package com.android.jtknife.core.widgets.pullrefresh;

/**
 * 为 RecyclerRefreshLayout 定义拖动距离的转换器
 * AUTHOR: yangjiantong
 * DATE: 2017/2/22
 */
public interface IDragDistanceConverter {
    /**
     * @param scrollDistance  ACTION_DOWN触发的Y坐标和当前ACTION_MOVE所在Y坐标的距离
     * @param refreshDistance 刷新点和起始点之间的距离
     * @return 返回下拉刷新动画实际滑动的距离
     */
    float convert(float scrollDistance, float refreshDistance);
}
