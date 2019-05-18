package com.test;

import com.View.LoginPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class testLoginFrame {
    public static void main(String[] argv){
        LoginPanel loginPanel = new LoginPanel();
        loginPanel.showFrame();
        JFrame testFm = new JFrame("测试窗口");
        testFm.setSize(100,100);
        JButton button = new JButton("销毁登陆界面");
        Container container = testFm.getContentPane();
        container.add(button);

        testFm.setVisible(true);
        testFm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1){
                    loginPanel.delFrame(); // 销毁窗口
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }
}
