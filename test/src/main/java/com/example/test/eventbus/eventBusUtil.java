package com.example.test.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Kin on 2017/4/21.
 */

public class eventBusUtil {

    public static void register(Object subscriber) {
        if (subscriber != null) {
            EventBus.getDefault().register(subscriber);
        }
    }

    public static void unregister(Object subscriber) {
        if (subscriber != null) {
            EventBus.getDefault().unregister(subscriber);
        }
    }
}
