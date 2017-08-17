package com.example.dagger.component;

import android.app.Activity;

import com.example.dagger.info.WuKong;
import com.example.dagger.module.XiYouModule;

import dagger.Component;

/**
 * Created by James on 2016/10/26.
 */

@Component(modules = {XiYouModule.class})
public interface XiYouComponent {
    void inject(WuKong wuKong);

    void inject(Activity activity);
}
