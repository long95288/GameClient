package com.test;

import com.event.Counter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class testCounter {
    public static void main(String[] argv){
        JFrame frame = new JFrame("测试计数器");
        JLabel jLabel = new JLabel("000");
        JButton getTimeBtn = new JButton("获得时间");
        JButton stopTimeBtn = new JButton("停止计数");
        JButton startTimeBtn = new JButton("开始计数");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(300,300));
        Container container = frame.getContentPane();
        container.setLayout(new FlowLayout());
        container.add(jLabel);
        container.add(getTimeBtn);
        container.add(stopTimeBtn);
        container.add(startTimeBtn);
        frame.setVisible(true);

        Counter couter = new Counter();
        couter.setShowTimeLabel(jLabel);
        couter.start(); // 开启线程
        // couter.StartCount();
        getTimeBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("时间已经过:"+couter.getCount());
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

        stopTimeBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                couter.StopCount();
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

        startTimeBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                couter.StartCount();
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
