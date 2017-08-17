package com.example.dagger.info;

import javax.inject.Inject;

/**
 * Created by James on 2016/10/26.
 */

public class WuKong {
    @Inject
    public JinGuBang mJinGuBang;

    @Inject
    public WuKong() {

    }

    public String useJinGuBang() {
        return "This is a test";
    }

}
