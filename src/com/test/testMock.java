package com.test;

import com.Utils.TopHandler;
import com.View.IndexFrame;
import com.View.LoginPanel;
import com.View.MineField;
import com.View.OperatePanel;

import javax.swing.*;

public class testMock {
    public static void main(String[] argv){
        TestMockRequest testMockRequest = new TestMockRequest();
        TopHandler topHandler = new TopHandler();
        MineField ownMineField = new MineField();
//        JOptionPane jOptionPane = new JOptionPane();
        OperatePanel operatePanel = new OperatePanel();
        LoginPanel loginPanel = new LoginPanel();
        MineField opponentMineField = new MineField();
        IndexFrame indexFrame = new IndexFrame(
                ownMineField.getMineFieldJpanel(),
                operatePanel.getContent(),
                opponentMineField.getMineFieldJpanel()
        );

        topHandler.setIndexFrame(indexFrame);
        topHandler.setLoginPanel(loginPanel);
        topHandler.setOperatePanel(operatePanel);

        // 请求都发送到topHandler中
        testMockRequest.setSuccessor(topHandler);
    }
}
