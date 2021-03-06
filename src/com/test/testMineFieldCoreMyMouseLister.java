package com.test;

import com.Config.Config;
import com.View.LoginPanel;
import com.conection.Connection;
import com.event.MyMouseListener;
import com.View.MineField;
import javax.swing.*;
import java.awt.*;
import com.event.Core;
// 测试地图类 核心类 鼠标监听类的
public class testMineFieldCoreMyMouseLister {
    public static void main(String[] argv){
        JFrame mainFram = new JFrame("测试面板");
        LoginPanel loginPanel = new LoginPanel();


        Core core = new Core();
        MyMouseListener myMouseListener = new MyMouseListener();

        mainFram.setLayout(null);
        mainFram.setSize(new Dimension(1000,1000));

        MineField mineField = new MineField();
        MineField mineField1 = new MineField(); // 第二个面板

        Container container = mainFram.getContentPane();
        mineField.getMineFieldJpanel().setLocation(0,0);
        mineField1.getMineFieldJpanel().setLocation(Config.getColumn()*40+300,0);

        container.add(mineField.getMineFieldJpanel());
        container.add(mineField1.getMineFieldJpanel());
        // mineField.getMineFieldJpanel().setBounds(0,0,500,500);


        mainFram.setVisible(true);
        // 设置监听器
        mineField.getMineFieldJpanel().addMouseListener(myMouseListener.getListener());

        // 连接
        Connection connection = new Connection();
        // 设置职责链
        myMouseListener.setSuccessor(core);
        core.setSuccessor(mineField);
        mineField.setSuccessor(connection);
        connection.setSuccessor(mineField1);
        loginPanel.setSuccessor(myMouseListener);
    }
}
