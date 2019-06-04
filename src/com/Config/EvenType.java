package com.Config;

// 事件类型
public class EvenType {
    public static String UPDATE = "UPDATE"; // 更新请求
    public static String CLICK = "CLICK"; // 鼠标监听
    public static String RECIEVEDATA = "RECIEVEDATA"; // 接收数据事件
    public static String SENDDATA = "SENDTATA"; // 发送数据事件
    public static String LOGIN = "LOGIN";
    public static String LOGINSUCCESS = "LOGINSUCCESS"; // 登入成功事件类型
    public static String LOGINFAILURE = "LOGINFAILURE"; // 登陆失败
    public static String MATCHSUCCESS = "MATCHSUCCESS"; // 匹配成功
    public static String GAMEOVER = "GAMEOVER"; // 游戏结束
    public static String FLAGEUPDATE = "GLAGEUPDATE";// 旗子更新的数据
    public static String LOGOUT = "LOGOUT"; // 退出游戏请求

    EvenType(){}
}
