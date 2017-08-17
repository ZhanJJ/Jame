package com.example.test.dao;

import com.example.test.JameApplication;

/**
 * dao管理类
 * Created by Kin on 2017/6/30.
 */

public class DaoManager {
    private DaoMaster mDaoMaster; //顶层操作类，管理所有表
    private DaoSession mDaoSession;

    public static DaoManager getInstance() {
        return DaoManagerHolder.manager;
    }

    private static class DaoManagerHolder {
        private static final DaoManager manager = new DaoManager();
    }

    private DaoManager() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(JameApplication.getContext(), "Jame");
        mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
