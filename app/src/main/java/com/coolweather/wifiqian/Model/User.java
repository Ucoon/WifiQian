package com.coolweather.wifiqian.Model;

import cn.bmob.v3.BmobObject;

/**
 * Created by ZongJie on 2016/6/18.
 */
public class User extends BmobObject {
    private String account;
    private String password;
    private String name;
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
