package com.Utils;

import com.Config.Config;
import com.Config.EvenType;
import com.Store.Store;
import com.View.IndexFrame;
import com.View.LoginPanel;
import com.View.OperatePanel;
import com.event.Core;
import com.event.Counter;
import com.event.EventRequest;
import com.event.Handle;

import javax.swing.*;


// 最高级别的处理者，将处理责任链中各个部分不能处理的事情
public class TopHandler extends Handle {
    private LoginPanel loginPanel;
    private IndexFrame indexFrame;
    private Counter counter;
    private Core core;
    public TopHandler(Core core,IndexFrame indexFrame,LoginPanel loginPanel) {
        this.core = core;
        this.indexFrame = indexFrame;
        this.loginPanel = loginPanel;
        init();
    }

    // 初始化
    private void init(){
        counter = new Counter();
        counter.start(); // 开启计数线程
        // 获得首页时间标签的引用
        counter.setShowTimeLabel(indexFrame.getTimeLabel());
    }
    @Override
    public void handleRequest(EventRequest request) {
       System.out.println("请求类型:"+request.getEventType());
       System.out.println("请求内容:"+request.getEventData());
       String requestType = request.getEventType();
       String data = request.getEventData();
       if (requestType.equals(EvenType.LOGINSUCCESS)){
           // 处理登陆成功
           handleLoginSuccess(data);
       }else if (requestType.equals(EvenType.LOGINFAILURE)){
           // 处理登陆失败
           handleLoginFailure(data);
       }else if (requestType.equals(EvenType.MATCHSUCCESS)){
           // 匹配成功
           handleMatchSuccess(data);
       }else if (requestType.equals(EvenType.GAMEOVER)){
           handleGameOver(data);
       }
    }

    // TODO 处理登陆成功请求、
    private void handleLoginSuccess(String data){
        // 关闭登陆窗口
        loginPanel.delFrame();
        // 设置我方id
        indexFrame.setOwnId(data);
        // 显示棋局信息
        indexFrame.showFrame(); // 显示首页

    }

    // 处理登陆失败请求
    private void handleLoginFailure(String data){
        // 显示提示信息
        JOptionPane.showMessageDialog(null,"登陆失败");
    }

    //TODO 处理匹配成功请求
    private void handleMatchSuccess(String data){
        // 设置可以点击
        Store.setMouseClickable(true);
        // 设置对手id
        indexFrame.setOpponentId(data);
        // 设置操作面板的按钮
        indexFrame.setStartBtnText("对战中..");
        // 启动计时


        counter.StartCount(); // 开始计时

    }
    // TODO 处理游戏结束请求
    private void handleGameOver(String data){
        // 停止计时
        counter.StopCount();
        // 设置雷区不可点击
        Store.setMouseClickable(false);
        // 提示信息
        if (data.equals(Store.WIN)){
            JOptionPane.showMessageDialog(null,"游戏结束,你赢得本此对局");
        }else {
            JOptionPane.showMessageDialog(null,"游戏结束,你踩到雷了");
        }
        // 将面板恢复成默认状态
        indexFrame.setDefault();
        // 重新设置雷区
        core.resetMines();
    }
}
