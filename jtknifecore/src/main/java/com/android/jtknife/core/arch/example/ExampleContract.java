package com.android.jtknife.core.arch.example;

import com.android.jtknife.core.arch.mvp.BasePresenter;

import java.util.List;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/24
 */
public interface ExampleContract {

    interface View {
        void showLoading();
        void showData(List<String> datas);
    }

    interface Presenter extends BasePresenter<ExampleContract.View> {
        void loadTestData();
    }
}
