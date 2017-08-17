package com.example.mvp.presenter;

/**
 * Created by Kin on 2017/8/11.
 * 新增p层接口，原本v层是新建一个p层实例，现在更加抽象化，让v层只能持有一个p层的接口实例
 * p层应该实现的2个方法
 */
public interface IUserPresenter {
    void saveUser();

    void loadUser(int id);
}

