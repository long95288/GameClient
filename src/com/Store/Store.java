package com.Store;

import com.Config.Config;

// 存储信息类
public class Store {
    // 鼠标的监听类是否可点击
    private static boolean mouseClickable=false;
    // 用户id
    private static String account="未登陆";
    // 对手id
    private static String opponentId = "未匹配";

    // 游戏结束，输了
    public static String DEFEAT = "defeat";
    // 游戏结束,赢了
    public static String WIN = "win";

    public static boolean isMouseClickable() {
        return mouseClickable;
    }
    public static void setMouseClickable(boolean mouseClickable) {
        Store.mouseClickable = mouseClickable;
    }

    public static String getAccount() {
        return account;
    }

    public static void setAccount(String account) {
        Store.account = account;
    }

    public static String getOpponentId() {
        return opponentId;
    }

    public static void setOpponentId(String opponentId) {
        Store.opponentId = opponentId;
    }
}
