package com.coolweather.wifiqian.Model;

import cn.bmob.v3.BmobObject;

/**
 * Created by ZongJie on 2016/6/18.
 */
public class QianDao extends BmobObject {
    private String id;
    private String account;
    private String name;
    private String MAC;
    private String IP;
    private String DaoTime;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getDaoTime() {
        return DaoTime;
    }

    public void setDaoTime(String daoTime) {
        DaoTime = daoTime;
    }


}
