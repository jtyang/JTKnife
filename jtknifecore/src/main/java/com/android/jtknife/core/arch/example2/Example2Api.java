package com.android.jtknife.core.arch.example2;


import android.arch.lifecycle.LiveData;

import java.util.List;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/24
 */
public class Example2Api {

    public LiveData<List<String>> getTestData(){
//        LiveData<String> ld = new MutableLiveData<>();
        LiveData<List<String>> ld = AbsentLiveData.create();

        return ld;
    }

}
