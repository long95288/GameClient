package com.View;

import com.event.EventRequest;
import com.event.Handle;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;

/*
* 操作面板类。时间显示框，游戏开始框，对局双方数据
* */
public class OperatePanel extends Handle {
    private JPanel content = null; // 内容面板
    private JLabel timeLbl = null; // 时间标签
    private JButton startBtn = null;
    public OperatePanel() {
        init();
    }

    // 初始化函数
    private void init(){
        //
        content = new JPanel();
        content.setPreferredSize(new Dimension(100,100));

        timeLbl = new JLabel("000:00");
        timeLbl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        timeLbl.setForeground(Color.RED); // 设置文字颜色
        timeLbl.setFont(new Font("黑体",Font.BOLD,28));

        startBtn = new JButton("开始匹配");

        content.setLayout(null);
        content.add(timeLbl);
        timeLbl.setBounds(0,0,100,40);
        content.add(startBtn);
        startBtn.setBounds(0,50,100,40);
    }

    // 获得内容面板
    public JPanel getContent() {
        return content;
    }

    @Override
    public void handleRequest(EventRequest request) {

    }
}
