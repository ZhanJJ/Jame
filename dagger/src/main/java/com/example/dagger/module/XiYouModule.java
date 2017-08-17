package com.example.dagger.module;

import com.example.dagger.info.JinGuBang;
import com.example.dagger.info.WuKong;

import dagger.Module;
import dagger.Provides;

/**
 * Created by James on 2016/10/26.
 */
@Module
public class XiYouModule {
    @Provides
    WuKong provideWuKong() {
        return new WuKong();
    }

    @Provides
    JinGuBang provideJinGuBang() {
        return new JinGuBang();
    }
}
