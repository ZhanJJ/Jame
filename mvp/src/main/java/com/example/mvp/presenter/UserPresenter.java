package com.example.mvp.presenter;

import com.example.mvp.bean.UserBean;
import com.example.mvp.model.IUserModel;
import com.example.mvp.model.UserModel;
import com.example.mvp.view.IUserView;

/**
 * Created by Kin on 2017/8/11.
 * (4)Presenter:它能拥有m和v层的接口实例
 * Presenter就能通过接口与View及Model进行交互了：
 * 主要就是save和load两个方法的具体逻辑，在两个方法中调用m和v层的接口中规定好的方法
 */

public class UserPresenter implements IUserPresenter{
    private IUserView mUserView;
    private IUserModel mUserModel;

    public UserPresenter(IUserView userView) {
        this.mUserView = userView;
        this.mUserModel = new UserModel();
    }

    public void saveUser() {
        mUserModel.saveUser(mUserView.getId(), mUserView.getFirstName(), mUserView.getLastName());
    }

    public void loadUser(int id) {
        UserBean userBean = mUserModel.loadUser(id);
        mUserView.setFirstName(userBean.getFirstName());
        mUserView.setLastName(userBean.getLastName());
    }
}
