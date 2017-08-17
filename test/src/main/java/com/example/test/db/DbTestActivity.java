package com.example.test.db;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.test.R;
import com.example.test.dao.EntityManager;
import com.example.test.dao.OrderInfoDao;
import com.example.test.dao.UserInfoDao;
import com.example.test.info.OrderInfo;
import com.example.test.info.UserInfo;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DbTestActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();

    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.btnDelete)
    Button btnDelete;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;
    @BindView(R.id.btnQuary)
    Button btnQuary;

    UserInfoDao userInfoDao;
    OrderInfoDao orderInfoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_test);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(DbTestActivity.this, "Jame");
//        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getReadableDatabase());
//        daoSession = daoMaster.newSession();
//        userInfoDao = daoSession.getUserInfoDao();
        userInfoDao = EntityManager.getInstance().getUserInfoDao();
        orderInfoDao = EntityManager.getInstance().getOrderInfoDao();
    }

    @OnClick({R.id.btnAdd, R.id.btnDelete, R.id.btnUpdate, R.id.btnQuary})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAdd: {
                UserInfo userInfo = new UserInfo(Long.valueOf(10), "Jack", "123456");
                if (userInfoDao.queryBuilder().where(UserInfoDao.Properties.Id.eq(userInfo.getId())).unique() != null) {
                    userInfoDao.deleteByKey(userInfo.getId());
                }
                userInfoDao.insert(userInfo);
                OrderInfo orderInfo = new OrderInfo(null, 100.00, Long.valueOf(10));
                orderInfoDao.insert(orderInfo);
                Snackbar.make(view, "新增成功", Snackbar.LENGTH_SHORT).show();
                break;
            }
            case R.id.btnDelete: {
                UserInfo userInfo = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Name.eq("Jack"))
                        .build()
                        .unique();
                if (userInfo != null) {
                    userInfoDao.deleteByKey(userInfo.getId());
                    Snackbar.make(view, "删除成功", Snackbar.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.btnUpdate: {
                userInfoDao.update(new UserInfo(null, "Jack2", "123452"));
                Snackbar.make(view, "更新成功", Snackbar.LENGTH_SHORT).show();
                break;
            }
            case R.id.btnQuary: {
                UserInfo userInfo = userInfoDao.queryBuilder()
                        .where(UserInfoDao.Properties.Id.eq(10))
                        .orderAsc(UserInfoDao.Properties.Id) //升序
                        .build()
                        .unique();

                OrderInfo orderInfo = orderInfoDao.queryBuilder()
                        .where(OrderInfoDao.Properties.Id.eq("1"))
                        .build()
                        .unique();

                if (orderInfo != null) {
                    UserInfo userInfo1 = orderInfo.getUserInfo();
                    if (userInfo1 != null) {
                        Logger.json(new Gson().toJson(userInfo1));
                    } else {
                        Logger.i("无数据");
                }
                }

                if (userInfo != null) {
                    Snackbar.make(view, "查询成功", Snackbar.LENGTH_SHORT).show();
                    Logger.json(new Gson().toJson(userInfo));
                } else {
                    Snackbar.make(view, "无数据", Snackbar.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }
}
