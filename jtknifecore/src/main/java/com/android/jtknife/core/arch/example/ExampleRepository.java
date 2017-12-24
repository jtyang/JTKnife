package com.android.jtknife.core.arch.example;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * 文件描述:
 *
 * @author yangjiantong
 * @date 2017/12/24
 */
public class ExampleRepository {

    public Flowable<List<String>> loadTestData() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        return Flowable.just(list);
    }


}
