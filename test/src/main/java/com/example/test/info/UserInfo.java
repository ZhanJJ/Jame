package com.example.test.info;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Kin on 2017/6/29.
 */
@Entity
public class UserInfo {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String tel;
    @Generated(hash = 1334510866)
    public UserInfo(Long id, String name, String tel) {
        this.id = id;
        this.name = name;
        this.tel = tel;
    }
    @Generated(hash = 1279772520)
    public UserInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTel() {
        return this.tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
}
