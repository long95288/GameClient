package com.JsonData;

import com.Config.SendDataType;

import java.rmi.registry.LocateRegistry;

// 登陆的json数据
public class LoginJson {
    private String type = SendDataType.LOGINDATA; // 登陆数据类型
    private String account ;
    private String password ; // 应该对密码进行hash取值

    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getType() {
        return type;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "登陆数据"+account+"|"+password;
    }
}
