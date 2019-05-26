package com.View;

import com.Config.Config;

import javax.swing.*;
import java.awt.*;

// 首页类,主要负责显示雷区和操作板等
public class IndexFrame {
    private JFrame mainFrame;
    private JLabel ownId; // 本人的游戏id
    private JLabel opponentId; // 对手的游戏id
    private JLabel vsLabel; // vs的标签
    private JPanel ownMineFieldPanel; // 我方雷区面板
    private JPanel operationPanel; // 操作的面板
    private JPanel opponentMineFieldPanel; // 对方雷区面板
    private Container container; // 组件容器
    public IndexFrame(JPanel ownMineFieldPanel,JPanel operationPanel,JPanel opponentMineFieldPanel){
        this.ownMineFieldPanel = ownMineFieldPanel;
        this.operationPanel = operationPanel;
        this.opponentMineFieldPanel = opponentMineFieldPanel;
        init();
    }
    // 初始化
    private void init(){
        instanceComponents(); // 实例化组件
        setComponentsStyle(); // 设置组件样式
        setComponentLayout(); // 设置组件布局
    }

    // 实例化组件
    private void instanceComponents(){
        mainFrame = new JFrame();
        container = mainFrame.getContentPane();
        ownId = new JLabel();
        opponentId = new JLabel();
        vsLabel = new JLabel();
    }

    // 设置组件的样式的文字
    private void  setComponentsStyle(){
        mainFrame.setTitle("游戏界面");
        mainFrame.setLocation(new Point(100,100));
        mainFrame.setSize(Config.getIndexFrameSize());
//        mainFrame.setResizable(false);

        // 标签字体
        Font labelFont = new Font("宋体",Font.BOLD,24);
        Dimension labelSize = new Dimension(Config.getColumn()*40,40); // 标签大小

        ownId.setFont(labelFont);
        ownId.setText("我方:");
        ownId.setPreferredSize(labelSize);

        opponentId.setFont(labelFont);
        opponentId.setText("对方:");
        opponentId.setPreferredSize(labelSize);

        Dimension vsLabelSize = new Dimension(100,40);
        Font vsLabelFont = new Font("黑体",Font.BOLD,28);
        vsLabel.setForeground(Color.RED); // 设置文字颜色
        vsLabel.setFont(vsLabelFont);
        vsLabel.setText("VS");
        vsLabel.setPreferredSize(vsLabelSize);
        vsLabel.setHorizontalAlignment(SwingConstants.CENTER);  // 居中
    }
    // 设置组件的布局
    private void setComponentLayout(){
        container.setLayout(new FlowLayout());
        container.add(ownId);
        container.add(vsLabel);
        container.add(opponentId);
        container.add(ownMineFieldPanel);
        container.add(operationPanel);
        container.add(opponentMineFieldPanel);
    }
    // 设置本人id
    public void setOwnId(String ownId) {
        this.ownId.setText(ownId);
    }
    // 设置对手id
    public void setOpponentId(String opponentId){
        this.opponentId.setText(opponentId);
    }
    // 添加我方雷区

    // 显示首页的界面
    public void showFrame(){
        mainFrame.setVisible(true);
    }
}
