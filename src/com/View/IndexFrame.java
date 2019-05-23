package com.View;

import javax.swing.*;
import java.awt.*;

// 首页类,主要负责显示雷区和操作板等
public class IndexFrame {
    private JFrame mainFrame;

    public IndexFrame(){
        init();
    }
    // 初始化
    private void init(){
        mainFrame = new JFrame("游戏界面");
        mainFrame.setLocation(new Point(100,100));
        mainFrame.setSize(new Dimension(540,400));
        mainFrame.setResizable(false); // 关掉最大化按钮
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public void showFrame(){
        mainFrame.setVisible(true);
    }
}
