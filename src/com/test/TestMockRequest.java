package com.test;

import com.Config.Config;
import com.Config.EvenType;
import com.event.EventRequest;
import com.event.Handle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// 模拟产生请求
public class TestMockRequest extends Handle {
    private JFrame jFrame;
    private JButton loginSuccessBtn; // 提供登陆请求
    private JButton matchSuccessBtn; // 配置成功请求
    private JButton gameOverBtn; // 游戏结束请求

    private Container container;
    public TestMockRequest() {
        init();
    }
    private void init(){
        jFrame = new JFrame("模拟请求提供器");
        jFrame.setSize(new Dimension(100,400));
        jFrame.setLocation(Config.getCenterPoint(jFrame.getWidth(),jFrame.getHeight()));

        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        container = jFrame.getContentPane();
        container.setLayout(new FlowLayout());

        loginSuccessBtn = new JButton("登陆成功请求");
        loginSuccessBtn.setPreferredSize(Config.getOperateComponentSize());

        matchSuccessBtn = new JButton("匹配成功请求");
        matchSuccessBtn.setPreferredSize(Config.getOperateComponentSize());

        gameOverBtn = new JButton("游戏结束请求");
        gameOverBtn.setPreferredSize(Config.getOperateComponentSize());


        addComponents();
        setListener(); // 设置请求

        jFrame.setVisible(true);
    }
    //
    private void addComponents(){
        container.add(loginSuccessBtn);
        container.add(matchSuccessBtn);
        container.add(gameOverBtn);
    }


    private void setListener(){
        // 登陆请求
        loginSuccessBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String data ="12345678";
                throwRequest(new EventRequest(EvenType.LOGINSUCCESS,data));
            }
        });

        // 匹配成功请求
        matchSuccessBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String data = "87654321";
                throwRequest(new EventRequest(EvenType.MATCHSUCCESS,data));
            }
        });

        // 游戏结束请求
        gameOverBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String data ="defeat";
                throwRequest(new EventRequest(EvenType.GAMEOVER,data));
            }
        });

    }

    private void throwRequest(EventRequest eventRequest){
        if (this.successor!=null) successor.handleRequest(eventRequest);
        System.out.println("没有上级");
    }

    @Override
    public void handleRequest(EventRequest request) {

    }
}
