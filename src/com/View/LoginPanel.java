package com.View;

import com.Config.EvenType;
import com.event.EventRequest;
import com.event.Handle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LoginPanel extends Handle {


    private JFrame loginfm = null;
    private Container container = null;
    private JLabel accoutLabel = null;
    private JLabel passwordLabel = null;
    private JTextField accountTextField = null;
    private JPasswordField passwordTextFile = null;
    private JButton loginBtn = null;
    public LoginPanel(){
        init();
    }
    private void init(){
        instanceComponent();
        setComponentStyle();
        addComponent();
        setFrameCloseEvent();
        setButtonListener();
        showFrame();
    }
    // 实例化组件
    private void instanceComponent(){
        loginfm = new JFrame();
        container = loginfm.getContentPane();
        accoutLabel = new JLabel();
        passwordLabel = new JLabel();
        accountTextField = new JTextField();
        passwordTextFile = new JPasswordField();
        loginBtn = new JButton();
    }
    // 设置组件样式和显示的文字
    private void setComponentStyle(){
        loginfm.setTitle("登录游戏");
        loginfm.setSize(new Dimension(400,300));
        loginfm.setLocation(100,100);
        loginfm.setResizable(false); // 关闭最大化功能
        container.setLayout(new FlowLayout());

        Font lableFont = new Font("黑体",Font.BOLD,28);
        Dimension labelSize = new Dimension(100,40);
        Dimension textFieldSize = new Dimension(200,40);

        accoutLabel.setText("账号:");
        accoutLabel.setFont(lableFont);
        accoutLabel.setPreferredSize(labelSize);
        passwordLabel.setText("密码:");
        passwordLabel.setFont(lableFont);
        passwordLabel.setPreferredSize(labelSize);

        accountTextField.setPreferredSize(textFieldSize);
        accountTextField.setFont(lableFont);
        passwordTextFile.setPreferredSize(textFieldSize);
        passwordTextFile.setFont(lableFont);
        passwordTextFile.setEchoChar('*');

        loginBtn.setText("登陆");
        loginBtn.setFont(lableFont);
        loginBtn.setSize(labelSize);

    }
    // 添加组件
    private void addComponent(){
        container.add(accoutLabel);
        container.add(accountTextField);
        container.add(passwordLabel);
        container.add(passwordTextFile);
        container.add(loginBtn);
    }
    // 设置按钮鼠标监听
    private void setButtonListener(){
        loginBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1)
                {
                    String account = accountTextField.getText();
                    String password = new String(passwordTextFile.getPassword());
                    System.out.println("账号:"+account);
                    System.out.println("密码:"+password);
                    // 抛出登陆请求
                    throwLoginRequest(account,password);
                }
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
    // 设置界面的关闭逻辑
    private void setFrameCloseEvent(){
        // 关闭并退出
        loginfm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    // 登陆请求
    private void throwLoginRequest(String account,String password){
        String data = account+"|"+password;
        if (this.successor != null){
            this.successor.handleRequest(new EventRequest(EvenType.LOGIN,data));
        }
        //
        System.out.print("登入事件没有处理者");
    }
    // 显示界面
    public void showFrame(){
        loginfm.setVisible(true);
    }
    // 销毁登入界面
    public void delFrame(){
        this.loginfm.dispose();
    }
    @Override
    public void handleRequest(EventRequest request) {

    }
}
