package com.conection;

import com.Config.Config;
import com.Config.EvenType;
import com.Config.RecieveDataType;
import com.JsonData.JsonData;
import com.View.IndexFrame;
import com.View.LoginPanel;
import com.event.EventRequest;
import com.event.Handle;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

import java.util.Vector;

// 连接服务器类，
public class Connection extends Handle {
    private Socket socket = null; // 连接服务器的socket
    private String ip = null;
    private int port = -1;
    private PrintWriter outStream = null;  // 输出流，用于发送数据到服务器
    private BufferedReader inStream = null; // 输入流，用于接收从服务器返回的数据

    // 线程不安全 改用Vector
     //  ArrayList<String> senddatas = new ArrayList<>();
    // Vector 线程安全
    private Vector<String> sendDatas = new Vector<>();
    private LoginPanel loginPanel; // 引用的登陆组件
    private IndexFrame indexFrame; // 引用的首页组件
    public Connection(){
        init();
    }
    private void init(){
        this.ip = Config.getIp();
        this.port = Config.getPort();
        // 连接服务器
        connectServer();
    }
//    // 设置ip函数
//    private void setIp(String ip){
//        this.ip = ip;
//    }
//    // 设置port函数
//    private void setPort(int port){this.port = port;}
    private void connectServer(){
        try{
            socket = new Socket(ip,port);
            // 连接成功后执行的逻辑
            // 创建接收数据线程
            ReceiveDataThread receiveDataThread = new ReceiveDataThread();
            receiveDataThread.start(); // 开启监听线程

            // 创建数据发送线程
            SendDataThread sendDataThread = new SendDataThread();
            sendDataThread.start();

        }catch (ConnectException e){
//            e.getMessage();
            System.out.println(e.getMessage());
            if (e.getMessage().equals("Connection timed out: connect")){
                JOptionPane.showMessageDialog(null,"网络超时！");}
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {

        }
    }

    // 发送数据函数
    public void sendData(String s) {
        // 向发送数据列表添加数据
        sendDatas.addElement(s);
    }

    // 处理请求
    public void handleRequest(EventRequest request){
        // 如果是发送数据的请求则能够处理，否则放到下一个去
        if (request.getEventType().equals(EvenType.SENDDATA)){
            String message = request.getEventData();
            sendData(message); // 发送数据
        }else if (request.getEventType().equals(EvenType.LOGIN)){
            // 发送登陆信息
            // 登陆拦截，直接关闭界面
            // closeLoginPanel();
            sendData(request.getEventData());
        }
        else{
            if (this.successor != null){
                // 传递请求
                this.successor.handleRequest(request);
            }
        }
    }

    // 接收数据传递函数
    private void receiveData(String data){
        // 向职责链中发送处理请求,更新面板数据
        // 获得服务器返回的数据类型
        String type = JsonData.getServerResponseType(data);
        // if (type.equa)
        if (type.equals(RecieveDataType.LOGINSUCCESS)){
            // 登陆成功,关闭登陆界面,显示主页
            closeLoginPanel();
        }else if(type.equals(RecieveDataType.UPDATEOPPONENTGAMEMAP)){
            // 接收到更新对方地图数据请求
            if (this.successor!=null){
                this.successor.handleRequest(new EventRequest(EvenType.UPDATE, data));
            }
            else{
                System.out.println("没有successor传递请求");
            }
        }

    }
    // 引入login界面和index界面
    public void addLoginPanelAndIndexFrame(LoginPanel loginPanel, IndexFrame indexFrame){
        this.loginPanel = loginPanel;
        this.indexFrame = indexFrame;
    }
    // 关闭登陆界面,显示主页面函数
    private void closeLoginPanel(){
        loginPanel.delFrame(); // 销毁登陆界面
        indexFrame.showFrame(); // 显示首界面
    }

    // 接收数据线程类
    class ReceiveDataThread extends Thread {
        @Override
        public void run() {
            try{
                // 获得输出流
                outStream = new PrintWriter(socket.getOutputStream());
                // 获得输入流
                inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true) {
                    Thread.sleep(10);
                    // 有数据从服务器发送过来了
                    if (inStream.ready()) {
                        // 处理服务器接收到的数据
                        String cmd = inStream.readLine(); // 读取数据
                        System.out.println("接到服务器数据:"+cmd);
                        if (cmd != null){
                            receiveData(cmd); // 调用接收数据函数处理消息
                        }
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // 发送数据线程类
    class SendDataThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (true){
                try {
                    // 使用30ms间隔发送数据,避免被覆盖
                    Thread.sleep(30);
                    if (sendDatas.size()>0){
                        String s = sendDatas.firstElement();// 取第一个元素
                        // System.out.println("取出并删除"+s);
                        if (outStream != null){
                            outStream.println(s);
                            outStream.flush();
                            // 发送之后把该元素删除
                            sendDatas.remove(0);
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
