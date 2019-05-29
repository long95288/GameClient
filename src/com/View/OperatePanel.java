package com.View;

import com.Config.Config;
import com.Config.EvenType;
import com.event.EventRequest;
import com.event.Handle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
* 操作面板类。时间显示框，游戏开始框，对局双方数据
* */
public class OperatePanel extends Handle {
    private JPanel content = null; // 内容面板
//    private JLabel flagIcon = null;
    private JLabel flagNumbersLbl = null; // 旗子数目标签
    private JLabel timeLbl = null; // 时间标签
    private JButton startBtn = null;

    public OperatePanel() {
        init();
    }

    // 初始化函数
    private void init(){
        //
        content = new JPanel();
        content.setPreferredSize(Config.getOperatePanelSize());

        timeLbl = new JLabel("000");
        timeLbl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        timeLbl.setForeground(Color.RED); // 设置文字颜色
        timeLbl.setFont(new Font("黑体",Font.BOLD,28));
        timeLbl.setHorizontalAlignment(SwingConstants.CENTER); //居中
        timeLbl.setPreferredSize(Config.getOperateComponentSize());

        flagNumbersLbl = new JLabel(String.valueOf(Config.getMineNumber()));
        flagNumbersLbl.setIcon(new ImageIcon(Config.getFlagImagePath()));
        flagNumbersLbl.setFont(Config.getLabelFont());
        flagNumbersLbl.setPreferredSize(Config.getOperateComponentSize());

        startBtn = new JButton("开始匹配");
        startBtn.setPreferredSize(Config.getOperateComponentSize());

//        content.setLayout(null);
//        content.add(flagIcon);
        content.add(flagNumbersLbl);
        content.add(timeLbl);

//        timeLbl.setBounds(0,0,100,40);
        content.add(startBtn);
//        startBtn.setBounds(0,50,100,40);
        startBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON1){
                    // 向责任链发送一个sendData的请求
                    // 数据为:{type:MATCH}
//                    JOptionPane.showMessageDialog(null,"鼠标左键");
                    String matchData = "{\"type\":\"MATCH\"}";
                    startBtn.setText("匹配中.");
                    startBtn.setEnabled(false);
                    if (successor!=null){successor.handleRequest(new EventRequest(EvenType.SENDDATA,matchData));}
                }
            }
        });
    }

    // 获得内容面板
    public JPanel getContent() {
        return content;
    }

    // 获得时间标签
    public JLabel getTimeLbl() {
        return timeLbl;
    }
    // 获得旗子
    public JLabel getFlagLabel(){
        return flagNumbersLbl;
    }

    // 设置旗子数目
//    public void setFlagNumbersLbl(String flags){
////        flagNumbersLbl.setText(flags);
////    }

    // 设置开始匹配按钮的文字
    public void setStartBtnText(String text){
        startBtn.setText(text);
    }

    // 恢复成默认状态
    public void setDefault(){
        // 旗子数量恢复成原来的
        flagNumbersLbl.setText(String.valueOf(Config.getMineNumber()));
        // 时间归零
        timeLbl.setText("000");
        // 按钮回归
        startBtn.setText("开始匹配");
        startBtn.setEnabled(true);
    }
    @Override
    public void handleRequest(EventRequest request) {
        // TODO 接收旗子更新的请求
        if (request.getEventType().equals(EvenType.FLAGEUPDATE)){
            flagNumbersLbl.setText(request.getEventData());
        }else if (successor!=null) {
            successor.handleRequest(request);
        }
    }
}
