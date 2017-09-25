package com.android.jtknife.modules.live;

import com.android.jtknife.R;
import com.android.jtknife.common.app.BaseFragment;
import com.android.jtknife.core.utils.DeviceUtils;
import com.android.jtknife.modules.live.widget.MyScrollView;
import com.elvishew.xlog.XLog;

import butterknife.Bind;

/**
 * 文件描述
 * AUTHOR: yangjiantong
 * DATE: 2017/8/1
 */
public class TestInputFragmentV2 extends BaseFragment {

    @Bind(R.id.scroll_layout)
    MyScrollView fixSizeLayout;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_testinput_v2;
    }

    @Override
    protected void onInitView() {
        XLog.e("TestInputFragment ==================== " + DeviceUtils.getScreenWidth(getActivity()));
        fixSizeLayout.setScrollingEnabled(false);
    }

}
