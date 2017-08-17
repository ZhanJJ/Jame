package com.example.test.info;

import com.example.test.dao.DaoSession;
import com.example.test.dao.OrderInfoDao;
import com.example.test.dao.UserInfoDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

/**
 * Created by Kin on 2017/7/3.
 */
@Entity
public class OrderInfo {
    @Id(autoincrement = true)
    private Long id;
    private double price;

    private Long customerId;
    @ToOne(joinProperty = "customerId")
    private UserInfo userInfo;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 2087209612)
    private transient OrderInfoDao myDao;
    @Generated(hash = 972825431)
    public OrderInfo(Long id, double price, Long customerId) {
        this.id = id;
        this.price = price;
        this.customerId = customerId;
    }
    @Generated(hash = 1695813404)
    public OrderInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public Long getCustomerId() {
        return this.customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    @Generated(hash = 2066097151)
    private transient Long userInfo__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 261433904)
    public UserInfo getUserInfo() {
        Long __key = this.customerId;
        if (userInfo__resolvedKey == null || !userInfo__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserInfoDao targetDao = daoSession.getUserInfoDao();
            UserInfo userInfoNew = targetDao.load(__key);
            synchronized (this) {
                userInfo = userInfoNew;
                userInfo__resolvedKey = __key;
            }
        }
        return userInfo;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1200683386)
    public void setUserInfo(UserInfo userInfo) {
        synchronized (this) {
            this.userInfo = userInfo;
            customerId = userInfo == null ? null : userInfo.getId();
            userInfo__resolvedKey = customerId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1732476797)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getOrderInfoDao() : null;
    }
}
