package com.conection;

import com.event.EventRequest;
import com.event.Handle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

// 连接服务器类，
public class Connection extends Handle {
    Socket socket = null; // 连接服务器的socket
    String ip = null;
    int port = -1;
    PrintWriter outStream = null;  // 输出流，用于发送数据到服务器
    BufferedReader inStream = null; // 输入流，用于接收从服务器返回的数据
    Connection(){
    }
    Connection(String ip,int port){
        this.ip = ip;
        this.port = port;
    }
    // 设置ip函数
    private void setIp(String ip){
        this.ip = ip;
    }
    // 设置port函数
    private void setPort(int port){this.port = port;}
    public void connectServer(String ip,int port){
        try{
            socket = new Socket(ip,port);
            // TODO 连接成功后执行的逻辑
            // 创建接收数据线程
            ReceiveDataThread receiveDataThread = new ReceiveDataThread();
            receiveDataThread.start(); // 开启监听线程
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // 发送数据函数
    public int sendData(String s) {
        if (outStream != null){
            outStream.println(s);
            outStream.flush();
            return 1;
        }
            return -1;
    }

    // 处理请求
    public void handleRequest(EventRequest request){
        // 如果是发送数据的请求则能够处理，否则放到下一个去
        if (request.getEventType().equals("SENDDATA")){
            String message = request.getEventData();
            sendData(message); // 发送数据
        }else
        {
            if (this.successor != null){
                // 传递请求
                this.successor.handleRequest(request);
            }
        }
    }

    // 接收数据传递函数
    private void recieveData(String data){
        // 向职责链中发送处理请求
        this.successor.handleRequest(new EventRequest("RECIEVEDATA",data));
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
                    this.sleep(100);

                    // 有数据从服务器发送过来了
                    if (inStream.ready()) {
                        // TODO 处理服务器接收到的数据
                        String cmd = inStream.readLine(); // 读取数据
                        System.out.println("服务器命令"+cmd);
                        recieveData(cmd); // 调用接收数据函数处理消息
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] argv){
        System.out.println("hello world");
    }
}
