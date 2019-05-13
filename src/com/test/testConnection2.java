package com.test;

import com.conection.Connection;

public class testConnection2 {
    public static void main(String[] argv){
        System.out.println("Hello World");
        Connection connection = new Connection();
        //connection.connectServer("192.168.1.104",8080);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                connection.sendData("nice to meet you");
                try{
                Thread.sleep(1000);}catch (Exception e){e.printStackTrace();}
                }
            }
        }).start();

    }
}
