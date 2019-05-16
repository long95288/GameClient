package com.test;

import com.View.OperatePanel;

import javax.swing.*;
import java.awt.*;

public class testOperatePanel {
    public static void main(String[] argv){
        JFrame frame = new JFrame("控制面板");
        Container container = frame.getContentPane();


        OperatePanel operatePanel = new OperatePanel();
        container.add(operatePanel.getContent());
        frame.setSize(new Dimension(300,300));
        frame.setVisible(true);
    }
}
