package com.View;

import com.event.EventRequest;
import com.event.Handle;

import javax.swing.*;
import java.awt.*;

/*
* 操作面板类。时间显示框，游戏开始框，对局双方数据
* */
public class OperatePanel extends Handle {
    JPanel content = null; // 内容面板
    JLabel timeLbl = null; // 时间标签
    JButton startBtn = null;
    public OperatePanel() {
        init();
    }

    // 初始化函数
    private void init(){
        //
        content = new JPanel();
        content.setSize(new Dimension(100,100));

        timeLbl = new JLabel("000:00");
    }

    // 获得内容面板
    public JPanel getContent() {
        return content;
    }

    @Override
    public void handleRequest(EventRequest request) {

    }
}
