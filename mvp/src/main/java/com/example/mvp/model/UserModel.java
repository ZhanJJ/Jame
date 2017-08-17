package com.example.mvp.model;

import android.util.SparseArray;

import com.example.mvp.bean.UserBean;

/**
 * Created by Kin on 2017/8/11.
 *  数据存储的模型层，只需要考虑怎么把数据存起来
 */

public class UserModel implements IUserModel {
    private SparseArray<UserBean> userBeanMap = new SparseArray<>();

    @Override
    public void saveUser(int id, String firstName, String lastName) {
        userBeanMap.put(id, new UserBean(id, firstName, lastName));
    }

    @Override
    public UserBean loadUser(int id) {
        if (userBeanMap.indexOfKey(id) >= 0) {
            return userBeanMap.get(id);
        }
        return null;
    }
}
