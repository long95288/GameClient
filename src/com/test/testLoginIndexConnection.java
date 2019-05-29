package com.test;

import com.View.IndexFrame;
import com.View.LoginPanel;
import com.View.MineField;
import com.View.OperatePanel;
import com.conection.Connection;

public class testLoginIndexConnection {
    public static void main(String[] argv){
        MineField ownMineField = new MineField();
        MineField opponentMineField = new MineField();
        OperatePanel operatePanel = new OperatePanel();
        // 首页
        IndexFrame indexFrame = new IndexFrame(ownMineField,operatePanel,opponentMineField);
        indexFrame.showFrame();
        // 登陆面板
        LoginPanel loginPanel = new LoginPanel();
        loginPanel.showFrame();

        // 连接模块
        Connection connection = new Connection();
        // 设置操作对象

        // 设置职责链 login的事件让connection处理
        loginPanel.setSuccessor(connection);
    }
}
