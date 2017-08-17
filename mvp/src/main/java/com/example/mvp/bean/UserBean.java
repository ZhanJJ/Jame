package com.example.mvp.bean;

/**
 * Created by Kin on 2017/8/11.
 *  (1)首先我们需要一个UserBean，用来保存用户信息
 */

public class UserBean {

    private int id;
    private String firstName;
    private String lastName;

    public UserBean(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
