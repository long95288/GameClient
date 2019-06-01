package com.View;

import com.Config.Config;
import com.Store.Store;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

// 首页类,主要负责显示雷区和操作板等
public class IndexFrame {
    private JFrame mainFrame;
    private JLabel ownId; // 本人的游戏id
    private JLabel opponentId; // 对手的游戏id
    private JLabel vsLabel; // vs的标签
    private MineField ownMineField; // 我方雷区面板
    private MineField opponentMineField; // 对方游戏面板
    private OperatePanel operatePanel; // 操作面板
    private Container container; // 组件容器
    public IndexFrame(MineField ownMineFieldPanel,OperatePanel operationPanel,MineField opponentMineFieldPanel){
        this.ownMineField = ownMineFieldPanel;
        this.operatePanel = operationPanel;
        this.opponentMineField = opponentMineFieldPanel;
        init();
    }
    // 初始化
    private void init(){
        instanceComponents(); // 实例化组件
        setComponentsStyle(); // 设置组件样式
        setComponentLayout(); // 设置组件布局
        setComponentEventListener(); // 设置组件的时间监听
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
        mainFrame.setSize(Config.getIndexFrameSize());
        mainFrame.setLocation(Config.getCenterPoint(mainFrame.getWidth(),mainFrame.getHeight()));
//        mainFrame.setResizable(false);

        // 标签字体
        Font labelFont = new Font("宋体",Font.BOLD,24);
        Dimension labelSize = new Dimension(Config.getColumn()*40,40); // 标签大小

        ownId.setFont(labelFont);
        ownId.setIcon(Config.getIdImg());
        ownId.setText(Store.getAccount());
        ownId.setPreferredSize(labelSize);

        opponentId.setFont(labelFont);
        opponentId.setIcon(Config.getIdImg());
        opponentId.setText(Store.getOpponentId());
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
        container.add(ownMineField.getMineFieldJpanel());
        container.add(operatePanel.getContent());
        container.add(opponentMineField.getMineFieldJpanel());
    }
    // 设置组件的监听
    private void setComponentEventListener(){
        // TODO 为页面的关闭设置确定的逻辑
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                int n = JOptionPane.showConfirmDialog(mainFrame,"是否退出游戏","提示",JOptionPane.YES_NO_OPTION);
                if (n == JOptionPane.YES_OPTION){
                    // 确认退出
                    // System.out.println("关闭窗口");
                    mainFrame.dispose();
                    System.exit(0);
                }
              // 选择No不做任何处理
            }
        });
    }

    // 设置本人id
    public void setOwnId(String ownId) {
        this.ownId.setText(ownId);
    }
    // 设置对手id
    public void setOpponentId(String opponentId){
        this.opponentId.setText(opponentId);
    }
    // 获得对手id
    public String getOpponentId() {
        return opponentId.getText();
    }
    // 重新设置界面
    public void setDefault(){
        // 将对手id设置未匹配
        setOpponentId(Store.getOpponentId());
        // 将棋盘设置为初始状态
        ownMineField.setDefault();
//        ownMineField.getMineFieldJpanel().repaint();
        opponentMineField.setDefault();
        // 操作面板设置成默认状态
        operatePanel.setDefault();
    }

    // 设置按钮
    public void setStartBtnText(String text){
        operatePanel.setStartBtnText(text);
    }
    // 获得时间标签的引用
    public JLabel getTimeLabel(){
        return operatePanel.getTimeLbl();
    }
    // 获得旗子标签的引用
    public JLabel getFlagLabel(){
        return operatePanel.getFlagLabel();
    }
    // 显示首页的界面
    public void showFrame(){
        mainFrame.setVisible(true);
    }
}
