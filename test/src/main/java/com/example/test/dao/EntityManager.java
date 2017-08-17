package com.example.test.dao;

/**
 * Created by Kin on 2017/6/30.
 */

public class EntityManager {
    UserInfoDao userInfoDao;
    OrderInfoDao orderInfoDao;

    private EntityManager() {
    }

    public static EntityManager getInstance() {
        return EntityManagerHolder.entityManager;
    }

    private static class EntityManagerHolder {
        private static EntityManager entityManager = new EntityManager();
    }

//    private volatile static EntityManager instance;
//
//    public static EntityManager getInstance2() {
//        if (instance == null) {
//            synchronized (EntityManager.class) {
//                if (instance == null) {
//                    instance = new EntityManager();
//                }
//            }
//        }
//        return instance;
//    }

    /**
     * 创建UserInfo表实例
     *
     * @return
     */
    public UserInfoDao getUserInfoDao() {
        userInfoDao = DaoManager.getInstance().getDaoSession().getUserInfoDao();
        return userInfoDao;
    }

    /**
     * 创建OrderInfo表实例
     *
     * @return
     */
    public OrderInfoDao getOrderInfoDao() {
        orderInfoDao = DaoManager.getInstance().getDaoSession().getOrderInfoDao();
        return orderInfoDao;
    }
}
