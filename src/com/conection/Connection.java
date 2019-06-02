package com.conection;

import com.Config.Config;
import com.Config.EvenType;
import com.Config.RecieveDataType;
import com.JsonData.JsonData;
import com.JsonData.UpdateGameBlockJson;
import com.event.EventRequest;
import com.event.Handle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
    public Connection(){
        init();
    }
    private void init(){
        this.ip = Config.getIp();
        this.port = Config.getPort();
        // 连接服务器
        connectServer();
    }
    private void connectServer(){
        // 连接线程

        Thread connectThread = new Thread(new Runnable() {
            boolean isConnected = false; // 未连接
            @Override
            public void run() {
                 while (!isConnected) {
                    try {
                        socket = new Socket(ip, port);
                        //
                        System.out.println("正在连接");
                        // 连接成功后执行的逻辑
                        // 退出死循环
                        isConnected = true;
                        // 创建接收数据线程
                        ReceiveDataThread receiveDataThread = new ReceiveDataThread();
                        receiveDataThread.start(); // 开启监听线程

                        // 创建数据发送线程
                        SendDataThread sendDataThread = new SendDataThread();
                        sendDataThread.start();
                    }catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (!isConnected)
                        {
                            System.out.println("未连接到服务器,正在尝试重新连接");
                        }
                        else {
                            System.out.println("连接成功!!");
                        }
                    }
                    try {
                        // 睡眠1秒后再重试
                        Thread.sleep(1000);
                    }catch (InterruptedException e){e.printStackTrace();}

                }
            }
        });
        connectThread.start();
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
        }else{
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
        String type="ERROR";
        try {
            type = JsonData.getServerResponseType(data);
        }catch (IOException e){e.printStackTrace();};
        // if (type.equa)
        if (type.equals(RecieveDataType.LOGINSUCCESS)){
            // 抛出登陆成功请求
            throwLoginSuccessRequest(data);
        }else if(type.equals(RecieveDataType.LOGINFAILURE)){
            // 登陆失败请求
            throwLoginFailureRequest(data);
        }
        else if(type.equals(RecieveDataType.UPDATEGAMEBLOCK)){
//            // 接收到更新对方地图数据请求
            throwUpdateRequest(data);
        }else if (type.equals(RecieveDataType.MATCHSUCCESS)){
            // 匹配成功请求
            throwMatchSuccessRequest(data);
        }else if (type.equals(RecieveDataType.GAMEOVER)){
            // 抛出游戏结束请求
            System.out.println("服务器发送游戏结束数据:"+data);
            throwGameOverRequest(data);
        }
    }
    // 抛出登陆成功请求
    private void throwLoginSuccessRequest(String date){
        try {
            String value = JsonData.getJsonMap(date).get("value").toString();
            this.successor.handleRequest(new EventRequest(EvenType.LOGINSUCCESS, value));
        }catch (IOException e){e.printStackTrace();}
    }

    // 抛出登陆失败请求
    private void throwLoginFailureRequest(String data){
        try{
            String value = JsonData.getJsonMap(data).get("value").toString();
            this.successor.handleRequest(new EventRequest(EvenType.LOGINFAILURE,value));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 抛出更新对方雷区请求
    private void throwUpdateRequest(String data){
        // 获得x,y,value的值
       try {
           UpdateGameBlockJson updateGameBlockJson = JsonData.getUpdateGameBlockObject(data);
           int x = Integer.parseInt(updateGameBlockJson.getX());
           int y = Integer.parseInt(updateGameBlockJson.getY());
           int value = Integer.parseInt(updateGameBlockJson.getValue());
           String update = x+"|"+y+"|"+value;
           this.successor.handleRequest(new EventRequest(EvenType.UPDATE, update));
       }catch (IOException e){
           System.err.println("IO错误"+e.getMessage());
       }
    }

    // 抛出匹配成功请求
    private void throwMatchSuccessRequest(String data){
        try {
            // 取出匹配成功,对手的id
            String value = JsonData.getJsonMap(data).get("value").toString();
            this.successor.handleRequest(new EventRequest(EvenType.MATCHSUCCESS,value));
        }catch (IOException e){e.printStackTrace();}
    }

    // 抛出游戏结束请求
    private void throwGameOverRequest(String data){
        try {
            String value = JsonData.getJsonMap(data).get("value").toString();
            this.successor.handleRequest(new EventRequest(EvenType.GAMEOVER,value));
        }catch (IOException e){e.printStackTrace();}
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
                        }else{
                            System.out.println("连接服务器出错");
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
