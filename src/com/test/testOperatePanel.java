package com.test;

import com.Config.Config;
import com.View.OperatePanel;
import com.sun.javaws.util.JfxHelper;

import javax.swing.*;
import java.awt.*;

public class testOperatePanel {
    public static void main(String[] argv){
        JFrame frame = new JFrame("控制面板");
        Container container = frame.getContentPane();
//        Toolkit toolkit = Toolkit.getDefaultToolkit();
//        Dimension screenSize = toolkit.getScreenSize();

        OperatePanel operatePanel = new OperatePanel();
        container.add(operatePanel.getContent());
        frame.setSize(new Dimension(100,300));
//        frame.setLocation(new Point((int)(screenSize.getWidth()-frame.getWidth())/2,
//                (int)(screenSize.getHeight()-frame.getHeight())/2));
        frame.setLocation(Config.getCenterPoint(frame.getWidth(),frame.getHeight()));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
